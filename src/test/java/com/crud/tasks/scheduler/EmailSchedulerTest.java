package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSchedulerTest {
    @Mock
    private SimpleEmailService simpleEmailService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private AdminConfig adminConfig;
    private EmailScheduler emailScheduler;

    @BeforeEach
    void setUp() {
        emailScheduler = new EmailScheduler(simpleEmailService, taskRepository, adminConfig);
    }

    @Test
    void sendInformationEmail() {
        //Given
        long taskCount = 1;
        String email = "test@gmail.com";
        when(taskRepository.count()).thenReturn(taskCount);
        when(adminConfig.getAdminMail()).thenReturn(email);

        //When
        emailScheduler.sendInformationEmail();

        //Then
        verify(simpleEmailService, times(1)).send(
                Mail.builder()
                        .mailTo(email)
                        .subject("Tasks: Once a day email")
                        .message("Currently in database you got: " + taskCount + " task")
                        .build(),
                SimpleEmailService.DAILY_MAIL
        );
    }

    @Test
    void sendsPluralEmail() {
        //Given
        long taskCount = 2;
        String email = "test@gmail.com";
        when(taskRepository.count()).thenReturn(taskCount);
        when(adminConfig.getAdminMail()).thenReturn(email);

        //When
        emailScheduler.sendInformationEmail();

        //Then
        verify(simpleEmailService, times(1)).send(
                Mail.builder()
                        .mailTo(email)
                        .subject("Tasks: Once a day email")
                        .message("Currently in database you got: " + taskCount + " tasks")
                        .build(),
                SimpleEmailService.DAILY_MAIL);
    }
}