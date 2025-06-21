package com.tucfinancymanager.backend.services;

import com.tucfinancymanager.backend.DTOs.goal.GoalRequestUpdateDTO;
import com.tucfinancymanager.backend.DTOs.goal.GoalResponseDTO;
import com.tucfinancymanager.backend.DTOs.goal.GoalRequestDTO;
import com.tucfinancymanager.backend.entities.Goal;
import com.tucfinancymanager.backend.exceptions.ConflictException;
import com.tucfinancymanager.backend.exceptions.NotFoundException;
import com.tucfinancymanager.backend.repositories.GoalRepository;
import com.tucfinancymanager.backend.repositories.SubCategoryRepository;
import com.tucfinancymanager.backend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    private GoalResponseDTO newResponseService (Goal goal){
        return new GoalResponseDTO(
                goal.getId(),
                goal.getUser().getId(),
                goal.getSubCategory().getId(),
                goal.getGoalName(),
                goal.getTargetValue(),
                goal.getStartDate(),
                goal.getEndDate(),
                goal.getGoalStatus(),
                goal.getCreatedAt(),
                goal.getUpdatedAt()
        );
    }

    public List<GoalResponseDTO> getAllGoals(int page, int size) {
        var goals = this.goalRepository.findAll(PageRequest.of(page, size));

        return goals.stream().map(this::newResponseService).toList();
    }

    public List<GoalResponseDTO> getGoalsByUserId (UUID userId, int page, int size) {
        var goals = this.goalRepository.findByuserId(userId, PageRequest.of(page, size));
        return goals.stream().map(this::newResponseService).toList();
    }

    public GoalResponseDTO createGoal(GoalRequestDTO goalRequestDTO) {
        var user = this.usersRepository.findById(goalRequestDTO.getUserId())
                .orElseThrow(
                        () -> new NotFoundException("O usuario não existe")
                );

        var subcategory = this.subCategoryRepository.findById(goalRequestDTO.getSubCategoryId())
                .orElseThrow(
                        () -> new NotFoundException("A subcategoria não existe")
                );
        var goalExists = this.goalRepository.findBygoalName(goalRequestDTO.getGoalName());
        if(goalExists.isPresent()){
            throw new ConflictException("A meta já existe");
        }

        Goal goal = new Goal();

        goal.setUser(user);
        goal.setSubCategory(subcategory);
        goal.setGoalName(goalRequestDTO.getGoalName());
        goal.setTargetValue(goalRequestDTO.getTargetValue());
        goal.setStartDate(goalRequestDTO.getStartDate());
        goal.setEndDate(goalRequestDTO.getEndDate());
        goal.setGoalStatus(goalRequestDTO.getGoalStatus());

        goalRepository.save(goal);

        return newResponseService(goal);
    }

    public GoalResponseDTO updateGoalStatus (UUID id, GoalRequestUpdateDTO goalRequestUpdateDTO){
        var goal = this.goalRepository.findById(id)
                .orElseThrow( () -> new NotFoundException("A Meta não existe"));

        if (goalRequestUpdateDTO.getGoalName() != null) goal.setGoalName(goalRequestUpdateDTO.getGoalName());
        if (goalRequestUpdateDTO.getTargetValue() != null) goal.setTargetValue(goalRequestUpdateDTO.getTargetValue());
        if (goalRequestUpdateDTO.getStartDate() != null) goal.setStartDate(goalRequestUpdateDTO.getStartDate());
        if (goalRequestUpdateDTO.getEndDate() != null) goal.setEndDate(goalRequestUpdateDTO.getEndDate());
        if (goalRequestUpdateDTO.getGoalStatus() != null) goal.setGoalStatus(goalRequestUpdateDTO.getGoalStatus());
        goalRepository.save(goal);

        return newResponseService(goal);
    }

    public GoalResponseDTO deleteGoal(UUID id) {
        var goal = this.goalRepository.findById(id)
                .orElseThrow( () -> new NotFoundException("A Transação não existe"));

        goalRepository.delete(goal);

        return newResponseService(goal);
    }
}
