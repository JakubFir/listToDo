package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrelloServiceTest {
    @Mock
    private TrelloClient trelloClient;
    @Mock
    private SimpleEmailService emailService;
    @Mock
    private AdminConfig adminConfig;
    private TrelloService trelloService;

    @BeforeEach
    void setUp() {
        trelloService = new TrelloService(trelloClient, emailService, adminConfig);
    }

    @Test
    void fetchTrelloBoards() {
        //Given
        List<TrelloBoardDto> list = new ArrayList<>();
        list.add(new TrelloBoardDto("1L", "test", List.of(new TrelloListDto("1L", "test", false))));
        when(trelloClient.getTrelloBoards()).thenReturn(list);

        //When
        List<TrelloBoardDto> result = trelloService.fetchTrelloBoards();

        //Then
        assertEquals(1, result.size());
        assertEquals(result.get(0).getName(),list.get(0).getName());
    }

    @Test
    void createTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test", "test", "test", "1L");
        TrelloBadgesDto trelloBadgesDto = new TrelloBadgesDto();
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("test", "test", "test", trelloBadgesDto);
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(adminConfig.getAdminMail()).thenReturn("test@gmail.com");

        //When
        CreatedTrelloCardDto trelloCard = trelloService.createTrelloCard(trelloCardDto);

        //Then
        verify(emailService, times(1)).send(Mail.builder()
                .mailTo(adminConfig.getAdminMail())
                .subject("Tasks: New Trello card")
                .message("New card: " + trelloCardDto.getName() + " has been created on your Trello account")
                .build(),
                SimpleEmailService.TRELLO_CARD);

        assertEquals(trelloCard.getName(), createdTrelloCardDto.getName());
        assertEquals(trelloCard.getId(), createdTrelloCardDto.getId());
    }
}