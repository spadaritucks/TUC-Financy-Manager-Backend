package com.tucfinancymanager.backend.repositories;

import com.tucfinancymanager.backend.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
