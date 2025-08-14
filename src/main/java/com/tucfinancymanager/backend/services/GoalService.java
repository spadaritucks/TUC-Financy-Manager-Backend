package com.tucfinancymanager.backend.services;

import com.tucfinancymanager.backend.DTOs.goal.GoalRequestUpdateDTO;
import com.tucfinancymanager.backend.DTOs.goal.GoalResponseDTO;
import com.tucfinancymanager.backend.DTOs.pagination.PageResponseDTO;
import com.tucfinancymanager.backend.DTOs.subcategory.SubCategoryResponseDTO;
import com.tucfinancymanager.backend.ENUMs.GoalStatus;
import com.tucfinancymanager.backend.DTOs.category.CategoryResponseDTO;
import com.tucfinancymanager.backend.DTOs.goal.GoalCountDTO;
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

import java.util.UUID;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    private GoalResponseDTO newResponseService(Goal goal) {

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(
                goal.getSubCategory().getCategory().getId(),
                goal.getSubCategory().getCategory().getCategoryName(),
                goal.getSubCategory().getCategory().getCreatedAt(),
                goal.getSubCategory().getCategory().getUpdatedAt());

        SubCategoryResponseDTO subCategoryResponseDTO = new SubCategoryResponseDTO(
                goal.getSubCategory().getId(),
                goal.getSubCategory().getCategory().getId(),
                goal.getSubCategory().getSubcategoryName(),
                goal.getSubCategory().getCreatedAt(),
                goal.getSubCategory().getUpdatedAt(),
                categoryResponseDTO);

        return new GoalResponseDTO(
                goal.getId(),
                goal.getUser().getId(),
                goal.getSubCategory().getId(),
                goal.getGoalName(),
                goal.getGoalType(),
                goal.getTargetValue(),
                goal.getStartDate(),
                goal.getEndDate(),
                goal.getGoalStatus(),
                goal.getCreatedAt(),
                goal.getUpdatedAt(),
                subCategoryResponseDTO);
    }

    public PageResponseDTO<GoalResponseDTO> getGoalsByUserId(
            UUID userId,
            String subcategory,
            String goalName,
            String goalStatus,
            int page,
            int size) {

        String concatenedSubcategory = subcategory != null ? "%" + subcategory + "%" : null;
        String concatenedGoalName = goalName != null ? "%" + goalName + "%" : null;
        GoalStatus status = goalStatus != null ? GoalStatus.valueOf(goalStatus) : null;

        var goals = this.goalRepository.findByuserId(userId, concatenedSubcategory, concatenedGoalName, status,
                PageRequest.of(page, size));
        var result = goals.stream().map(this::newResponseService).toList();

        PageResponseDTO<GoalResponseDTO> pageResponseDTO = new PageResponseDTO<>(
                goals.getNumber(),
                goals.getSize(),
                goals.getTotalElements(),
                goals.getTotalPages(),
                goals.isLast(),
                result);

        return pageResponseDTO;
    }

    public GoalCountDTO getGoalsCountByStatus(UUID userId) {
        var result = this.goalRepository.findGoalsCountByStatus(userId);
        GoalCountDTO goalCountDTO = new GoalCountDTO();

        goalCountDTO.setInProgress(result.get("inProgress"));
        goalCountDTO.setCompleted(result.get("completed"));
        goalCountDTO.setExpired(result.get("expired"));

        return goalCountDTO;
    }

    public GoalResponseDTO createGoal(GoalRequestDTO goalRequestDTO) {
        var user = this.usersRepository.findById(goalRequestDTO.getUserId())
                .orElseThrow(
                        () -> new NotFoundException("O usuario não existe"));

        var subcategory = this.subCategoryRepository.findById(goalRequestDTO.getSubCategoryId())
                .orElseThrow(
                        () -> new NotFoundException("A subcategoria não existe"));
        var goalExists = this.goalRepository.findBygoalName(goalRequestDTO.getGoalName());
        if (goalExists.isPresent()) {
            throw new ConflictException("A meta já existe");
        }

        Goal goal = new Goal();

        goal.setUser(user);
        goal.setSubCategory(subcategory);
        goal.setGoalName(goalRequestDTO.getGoalName());
        goal.setGoalType(goalRequestDTO.getGoalType());
        goal.setTargetValue(goalRequestDTO.getTargetValue());
        goal.setStartDate(goalRequestDTO.getStartDate());
        goal.setEndDate(goalRequestDTO.getEndDate());
        goal.setGoalStatus(goalRequestDTO.getGoalStatus());

        goalRepository.save(goal);

        return newResponseService(goal);
    }

    public GoalResponseDTO updateGoalStatus(UUID id, GoalRequestUpdateDTO goalRequestUpdateDTO) {
        var goal = this.goalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("A Meta não existe"));

        if (goalRequestUpdateDTO.getGoalName() != null)
            goal.setGoalName(goalRequestUpdateDTO.getGoalName());
        if (goalRequestUpdateDTO.getTargetValue() != null)
            goal.setTargetValue(goalRequestUpdateDTO.getTargetValue());
        if (goalRequestUpdateDTO.getStartDate() != null)
            goal.setStartDate(goalRequestUpdateDTO.getStartDate());
        if (goalRequestUpdateDTO.getEndDate() != null)
            goal.setEndDate(goalRequestUpdateDTO.getEndDate());
        if (goalRequestUpdateDTO.getGoalStatus() != null)
            goal.setGoalStatus(goalRequestUpdateDTO.getGoalStatus());
        goalRepository.save(goal);

        return newResponseService(goal);
    }

    public GoalResponseDTO deleteGoal(UUID id) {
        var goal = this.goalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("A Transação não existe"));

        goalRepository.delete(goal);

        return newResponseService(goal);
    }
}
