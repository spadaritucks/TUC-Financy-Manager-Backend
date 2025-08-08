package com.tucfinancymanager.backend.services;


import com.tucfinancymanager.backend.DTOs.user.UserRequestDTO;
import com.tucfinancymanager.backend.DTOs.user.UserRequestUpdateDTO;
import com.tucfinancymanager.backend.DTOs.user.UserResponseDTO;
import com.tucfinancymanager.backend.entities.User;
import com.tucfinancymanager.backend.exceptions.ConflictException;
import com.tucfinancymanager.backend.exceptions.NotFoundException;
import com.tucfinancymanager.backend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FileService fileService;

    private UserResponseDTO newResponseService(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUserPhoto(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getMonthlyIncome(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public List<UserResponseDTO> getAllUsers(int page, int size) {
        var users = this.usersRepository.findAll(PageRequest.of(page, size));

        return users.stream()
                .map(this::newResponseService)
                .toList(); // Java 16+ (ou use .collect(Collectors.toList()) se for Java 8)
    }


    public UserResponseDTO createUsers(UserRequestDTO userRequestDTO) {

        String image = null;


        if(userRequestDTO.getUserPhoto() != null){
            image = this.fileService.uploadImage(userRequestDTO.getUserPhoto());

        }
        var userExists = this.usersRepository.findByEmail(userRequestDTO.getEmail());
        if (userExists.isPresent()) {
            throw new ConflictException("O Usuario já existe no sistema");
        }

        String passwordEncoded = passwordEncoder.encode(userRequestDTO.getPassword());


        User user = new User();
        user.setUserPhoto(image);
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhone(userRequestDTO.getPhone());
        user.setMonthlyIncome(userRequestDTO.getMonthlyIncome());
        user.setPassword(passwordEncoded);

        usersRepository.save(user);

        return newResponseService(user);

    }

    public UserResponseDTO updateUsers(UUID id, UserRequestUpdateDTO userRequestUpdateDTO) {
        var user = this.usersRepository.findById(id).orElseThrow(
                () -> new NotFoundException("O usuario não existe")
        );

        String image = null;


        if(userRequestUpdateDTO.getUserPhoto() != null){
            image = this.fileService.uploadImage(userRequestUpdateDTO.getUserPhoto());
        }


        if (userRequestUpdateDTO.getUserPhoto() != null) user.setUserPhoto(image);
        if (userRequestUpdateDTO.getName() != null) user.setName(userRequestUpdateDTO.getName());
        if (userRequestUpdateDTO.getEmail() != null) user.setEmail(userRequestUpdateDTO.getEmail());
        if (userRequestUpdateDTO.getPhone() != null) user.setPhone(userRequestUpdateDTO.getPhone());
        if (userRequestUpdateDTO.getMonthlyIncome() != null)
            user.setMonthlyIncome(userRequestUpdateDTO.getMonthlyIncome());
        usersRepository.save(user);

        return newResponseService(user);
    }

    public UserResponseDTO deleteUsers(UUID id) {
        User user = this.usersRepository.findById(id).orElseThrow(
                () -> new NotFoundException("O Usuario não existe")
        );
        usersRepository.delete(user);
        return newResponseService(user);
    }
}
