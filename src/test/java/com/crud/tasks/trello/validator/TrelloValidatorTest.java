package com.crud.tasks.trello.validator;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class TrelloValidatorTest {
    private TrelloValidator trelloValidator;



    @BeforeEach
    void setUp() {
        trelloValidator = new TrelloValidator();
    }

    @Test
    void shouldFilterOutTestBoard() {
        //Given
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        TrelloBoard trelloBoard = new TrelloBoard("1L", "test", List.of(new TrelloList("1L", "test", false)));
        trelloBoardList.add(trelloBoard);

        //When
        List<TrelloBoard> result = trelloValidator.validateTrelloBoards(trelloBoardList);

        //Then
        assertEquals(0, result.size());
    }

    @Test
    void validateTrelloBoards() {
        //Given
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        TrelloBoard trelloBoard = new TrelloBoard("1L", "task", List.of(new TrelloList("1L", "test", false)));
        trelloBoardList.add(trelloBoard);

        //When
        List<TrelloBoard> result = trelloValidator.validateTrelloBoards(trelloBoardList);

        //Then
        assertEquals(1, result.size());
    }


    //Nie wiem jak przetestować czy logger loguje dobrą wiadomość
    @Test
    void logsSomeoneIsTesting() {
        //Given
        TrelloCard trelloCard = new TrelloCard("test", "test", "test", "1L");

        //When & Then
        trelloValidator.validateCard(trelloCard);
    }
    @Test
    void validateCard() {
        //Given
        TrelloCard trelloCard = new TrelloCard("ra", "test", "test", "1L");

        //When & Then
        trelloValidator.validateCard(trelloCard);

    }
}