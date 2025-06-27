package com.tucfinancymanager.backend.services;

import com.tucfinancymanager.backend.DTOs.notification.NotificationRequestDTO;
import com.tucfinancymanager.backend.ENUMs.NotificationTypeEnum;
import com.tucfinancymanager.backend.entities.Goal;
import com.tucfinancymanager.backend.repositories.GoalRepository;
import com.tucfinancymanager.backend.repositories.NotificationRepository;
import com.tucfinancymanager.backend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    LocalDateTime today = LocalDateTime.now();


    @Scheduled(fixedDelay = 120)
    public void expireGoal() {


        var goals = this.goalRepository.findExpiredEndDate(today);

        for (Goal goal : goals) {
            NotificationRequestDTO notificationRequestDTO = new NotificationRequestDTO();
            notificationRequestDTO.setUserId(goal.getUser().getId());
            notificationRequestDTO.setNotificationType(NotificationTypeEnum.Goal);
            notificationRequestDTO.setTitle("Sua meta expirou!");
            notificationRequestDTO.setMessage("Sua meta " + goal.getGoalName() + " expirou, acompanhe seu desempenho!");

            String email = goal.getUser().getEmail();

            messagingTemplate.convertAndSendToUser(
                    email,
                    "/user/queue/notifications",
                    notificationRequestDTO
            );

        }
    }


}
