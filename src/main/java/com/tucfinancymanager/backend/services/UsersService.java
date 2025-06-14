package com.tucfinancymanager.backend.services;

import com.tucfinancymanager.backend.DTOs.user.UserRequestDTO;
import com.tucfinancymanager.backend.entities.User;
import com.tucfinancymanager.backend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public List<User> getAllUsers () {
        return this.usersRepository.findAll();
    }

    public User createUsers (UserRequestDTO userRequestDTO){
        User user = new User();
        user.setUserPhoto(userRequestDTO.getUserPhoto());
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhone(userRequestDTO.getPhone());
        user.setMonthlyIncome(userRequestDTO.getMonthlyIncome());
        user.setPassword(userRequestDTO.getPassword());

        usersRepository.save(user);

        return user;

    }

    public void updateUsers (UUID id, UserRequestDTO userRequestDTO){

    }

    public void deleteUsers (UUID id){

    }
}
