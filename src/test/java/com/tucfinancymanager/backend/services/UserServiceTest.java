package com.tucfinancymanager.backend.services;

import com.tucfinancymanager.backend.DTOs.user.UserRequestDTO;
import com.tucfinancymanager.backend.DTOs.user.UserRequestUpdateDTO;
import com.tucfinancymanager.backend.DTOs.user.UserResponseDTO;
import com.tucfinancymanager.backend.entities.User;
import com.tucfinancymanager.backend.exceptions.ConflictException;
import com.tucfinancymanager.backend.exceptions.NotFoundException;
import com.tucfinancymanager.backend.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private FileService fileService;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private UsersService usersService;

    private User user;
    private UserRequestDTO userRequestDTO;
    private UserRequestUpdateDTO userRequestUpdateDTO;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        
        user = new User();
        user.setId(userId);
        user.setUserPhoto("https://example.com/photo.jpg");
        user.setName("João Silva");
        user.setEmail("joao@email.com");
        user.setPhone("(11) 99999-9999");
        user.setMonthlyIncome(5000.0);
        user.setPassword("encodedPassword");
        user.setCreatedAt(Timestamp.from(Instant.now()));
        user.setUpdatedAt(Timestamp.from(Instant.now()));

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUserPhoto(multipartFile);
        userRequestDTO.setName("João Silva");
        userRequestDTO.setEmail("joao@email.com");
        userRequestDTO.setPhone("(11) 99999-9999");
        userRequestDTO.setMonthlyIncome(5000.0);
        userRequestDTO.setPassword("Password@123");

        userRequestUpdateDTO = new UserRequestUpdateDTO();
        userRequestUpdateDTO.setUserPhoto(multipartFile);
        userRequestUpdateDTO.setName("João Silva Atualizado");
        userRequestUpdateDTO.setEmail("joao.novo@email.com");
        userRequestUpdateDTO.setPhone("(11) 88888-8888");
        userRequestUpdateDTO.setMonthlyIncome(6000.0);
    }

    @Test
    @DisplayName("Should be able return users paged list")
    void getAllUsers_ShouldReturnPagedUsers() {
        // Arrange
        List<User> users = Arrays.asList(user);
        Page<User> userPage = new PageImpl<>(users);
        
        when(usersRepository.findAll(any(PageRequest.class))).thenReturn(userPage);

        // Act
        List<UserResponseDTO> result = usersService.getAllUsers(0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user.getId(), result.get(0).getId());
        assertEquals(user.getName(), result.get(0).getName());
        assertEquals(user.getEmail(), result.get(0).getEmail());
        
        verify(usersRepository).findAll(PageRequest.of(0, 10));
    }

    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void createUsers_ShouldCreateUserSuccessfully() {
        // Arrange
        when(usersRepository.findByEmail(userRequestDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userRequestDTO.getPassword())).thenReturn("encodedPassword");
        when(fileService.uploadImage(multipartFile)).thenReturn("https://example.com/uploaded-photo.jpg");
        
        // Mock do save para retornar o user com ID setado
        when(usersRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(userId); // Simula o comportamento do JPA que seta o ID
            return savedUser;
        });

        // Act
        UserResponseDTO result = usersService.createUsers(userRequestDTO);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(userRequestDTO.getName(), result.getName());
        assertEquals(userRequestDTO.getEmail(), result.getEmail());
        assertEquals(userRequestDTO.getPhone(), result.getPhone());
        assertEquals(userRequestDTO.getMonthlyIncome(), result.getMonthlyIncome());
        
        verify(usersRepository).findByEmail(userRequestDTO.getEmail());
        verify(passwordEncoder).encode(userRequestDTO.getPassword());
        verify(fileService).uploadImage(multipartFile);
        verify(usersRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Deve criar usuário sem foto")
    void createUsers_ShouldCreateUserWithoutPhoto() {
        // Arrange
        userRequestDTO.setUserPhoto(null);
        when(usersRepository.findByEmail(userRequestDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userRequestDTO.getPassword())).thenReturn("encodedPassword");
        
        when(usersRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(userId);
            return savedUser;
        });

        // Act
        UserResponseDTO result = usersService.createUsers(userRequestDTO);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        verify(fileService, never()).uploadImage(any());
        verify(usersRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar ConflictException quando usuário já existe")
    void createUsers_ShouldThrowConflictException_WhenUserAlreadyExists() {
        // Arrange
        when(usersRepository.findByEmail(userRequestDTO.getEmail())).thenReturn(Optional.of(user));

        // Act & Assert
        ConflictException exception = assertThrows(ConflictException.class, 
            () -> usersService.createUsers(userRequestDTO));
        
        assertEquals("O Usuario já existe no sistema", exception.getMessage());
        verify(usersRepository).findByEmail(userRequestDTO.getEmail());
        verify(usersRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void updateUsers_ShouldUpdateUserSuccessfully() {
        // Arrange
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(fileService.uploadImage(multipartFile)).thenReturn("https://example.com/new-photo.jpg");
        when(usersRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserResponseDTO result = usersService.updateUsers(userId, userRequestUpdateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        
        verify(usersRepository).findById(userId);
        verify(fileService).uploadImage(multipartFile);
        verify(usersRepository).save(user);
    }

    @Test
    @DisplayName("Deve atualizar usuário sem foto")
    void updateUsers_ShouldUpdateUserWithoutPhoto() {
        // Arrange
        userRequestUpdateDTO.setUserPhoto(null);
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(usersRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserResponseDTO result = usersService.updateUsers(userId, userRequestUpdateDTO);

        // Assert
        assertNotNull(result);
        verify(fileService, never()).uploadImage(any());
        verify(usersRepository).save(user);
    }

    @Test
    @DisplayName("Deve atualizar apenas campos não nulos")
    void updateUsers_ShouldUpdateOnlyNonNullFields() {
        // Arrange
        UserRequestUpdateDTO partialUpdate = new UserRequestUpdateDTO();
        partialUpdate.setName("Novo Nome");
        // Outros campos ficam null
        
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(usersRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserResponseDTO result = usersService.updateUsers(userId, partialUpdate);

        // Assert
        assertNotNull(result);
        verify(usersRepository).findById(userId);
        verify(usersRepository).save(user);
        verify(fileService, never()).uploadImage(any());
    }

    @Test
    @DisplayName("Deve lançar NotFoundException quando usuário não existe para atualização")
    void updateUsers_ShouldThrowNotFoundException_WhenUserNotExists() {
        // Arrange
        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> usersService.updateUsers(userId, userRequestUpdateDTO));
        
        assertEquals("O usuario não existe", exception.getMessage());
        verify(usersRepository).findById(userId);
        verify(usersRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void deleteUsers_ShouldDeleteUserSuccessfully() {
        // Arrange
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        UserResponseDTO result = usersService.deleteUsers(userId);

        // Assert
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        
        verify(usersRepository).findById(userId);
        verify(usersRepository).delete(user);
    }

    @Test
    @DisplayName("Deve lançar NotFoundException quando usuário não existe para deleção")
    void deleteUsers_ShouldThrowNotFoundException_WhenUserNotExists() {
        // Arrange
        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> usersService.deleteUsers(userId));
        
        assertEquals("O Usuario não existe", exception.getMessage());
        verify(usersRepository).findById(userId);
        verify(usersRepository, never()).delete(any(User.class));
    }

    @Test
    @DisplayName("Deve criar UserResponseDTO corretamente")
    void newResponseService_ShouldCreateCorrectUserResponseDTO() {
        // Arrange
        when(usersRepository.findByEmail(userRequestDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userRequestDTO.getPassword())).thenReturn("encodedPassword");
        when(fileService.uploadImage(multipartFile)).thenReturn("https://example.com/uploaded-photo.jpg");
        
        // Mock do save para retornar o user com todos os dados setados
        when(usersRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(userId);
            savedUser.setCreatedAt(user.getCreatedAt());
            savedUser.setUpdatedAt(user.getUpdatedAt());
            return savedUser;
        });

        // Act (testando indiretamente o método privado através do createUsers)
        UserResponseDTO result = usersService.createUsers(userRequestDTO);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(userRequestDTO.getName(), result.getName());
        assertEquals(userRequestDTO.getEmail(), result.getEmail());
        assertEquals(userRequestDTO.getPhone(), result.getPhone());
        assertEquals(userRequestDTO.getMonthlyIncome(), result.getMonthlyIncome());
        assertNotNull(result.getCreated_at());
        assertNotNull(result.getUpdated_at());
    }
}
