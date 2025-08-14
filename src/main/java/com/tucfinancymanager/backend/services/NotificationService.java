package com.tucfinancymanager.backend.services;

import com.tucfinancymanager.backend.DTOs.notification.NotificationRequestDTO;
import com.tucfinancymanager.backend.ENUMs.NotificationTypeEnum;
import com.tucfinancymanager.backend.entities.Goal;
import com.tucfinancymanager.backend.repositories.GoalRepository;
import com.tucfinancymanager.backend.repositories.NotificationRepository;
import com.tucfinancymanager.backend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class NotificationService {

  @Autowired
  private NotificationRepository notificationRepository;

  @Autowired
  private GoalRepository goalRepository;

  @Autowired
  private EmailService emailService;

  LocalDateTime today = LocalDateTime.now();

  // @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
  public void expireGoal() {

    var goals = this.goalRepository.findExpiredEndDate(today);
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt", "BR"));

    for (Goal goal : goals) {
      String subject = "Sua meta " + goal.getGoalName() + " expirou!";
      String body = "A sua meta de " + "<strong>" + goal.getGoalName()  + "</strong>" + " expirou no dia " +
          dateFormat.format(goal.getEndDate());

      emailService.sendMail(goal.getUser().getEmail(), goal.getUser().getName(), subject, body);
    }
  }

 

}

