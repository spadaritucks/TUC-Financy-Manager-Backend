package com.tucfinancymanager.backend.services;

import com.tucfinancymanager.backend.DTOs.transaction.TransactionResponseDTO;
import com.tucfinancymanager.backend.DTOs.user.UserRequestDTO;
import com.tucfinancymanager.backend.DTOs.user.UserRequestUpdateDTO;
import com.tucfinancymanager.backend.DTOs.user.UserResponseDTO;
import com.tucfinancymanager.backend.entities.Transaction;
import com.tucfinancymanager.backend.entities.User;
import com.tucfinancymanager.backend.exceptions.ConflictException;
import com.tucfinancymanager.backend.exceptions.NotFoundException;
import com.tucfinancymanager.backend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    private UserResponseDTO newResponseService (User user){
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


    public UserResponseDTO createUsers (UserRequestDTO userRequestDTO){

        var userExists = this.usersRepository.findUserByEmail(userRequestDTO.getEmail());
        if(userExists.isPresent()){
            throw new ConflictException("O Usuario já existe no sistema");
        }

        User user = new User();
        user.setUserPhoto(userRequestDTO.getUserPhoto());
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhone(userRequestDTO.getPhone());
        user.setMonthlyIncome(userRequestDTO.getMonthlyIncome());
        user.setPassword(userRequestDTO.getPassword());

        usersRepository.save(user);

        return newResponseService(user);

    }

    public UserResponseDTO updateUsers (UUID id, UserRequestUpdateDTO userRequestUpdateDTO){
        var user = this.usersRepository.findById(id).orElseThrow(
                () -> new NotFoundException("O usuario não existe")
        );
        if (userRequestUpdateDTO.getUserPhoto() != null) user.setUserPhoto(userRequestUpdateDTO.getUserPhoto());
        if (userRequestUpdateDTO.getName() != null) user.setName(userRequestUpdateDTO.getName());
        if (userRequestUpdateDTO.getEmail() != null) user.setEmail(userRequestUpdateDTO.getEmail());
        if (userRequestUpdateDTO.getPhone() != null) user.setPhone(userRequestUpdateDTO.getPhone());
        if (userRequestUpdateDTO.getMonthlyIncome() != null) user.setMonthlyIncome(userRequestUpdateDTO.getMonthlyIncome());
        usersRepository.save(user);

        return newResponseService(user);
    }

    public UserResponseDTO deleteUsers (UUID id){
        User user = this.usersRepository.findById(id).orElseThrow(
                () -> new NotFoundException("O Usuario não existe")
        );
        usersRepository.delete(user);
        return newResponseService(user);
    }
}
