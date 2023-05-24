package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrelloMapperTest {
    private TrelloMapper trelloMapper;
    private List<TrelloListDto> trelloListsDto;
    private List<TrelloList> trelloLists;

    @BeforeEach
    void setUp() {
        trelloMapper = new TrelloMapper();
        trelloListsDto = new ArrayList<>();
        trelloLists = new ArrayList<>();
    }

    @Test
    void mapToBoards() {
        //Given
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1L", "TestBoardDto", trelloListsDto);
        trelloBoardDtoList.add(trelloBoardDto);
        trelloBoardDtoList.add(new TrelloBoardDto("2L", "new Task", trelloListsDto));

        //When
        List<TrelloBoard> result = trelloMapper.mapToBoards(trelloBoardDtoList);

        //Then
        assertEquals(trelloBoardDtoList.get(0).getName(), result.get(0).getName());
        assertEquals(trelloBoardDtoList.get(0).getId(), result.get(0).getId());
        assertEquals(trelloBoardDtoList.get(1).getName(), result.get(1).getName());
    }

    @Test
    void mapToBoardsDto() {
        //Given
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        TrelloBoard trelloBoard = new TrelloBoard("1L", "TestBoard", trelloLists);
        trelloBoardList.add(trelloBoard);

        //When
        List<TrelloBoardDto> result = trelloMapper.mapToBoardsDto(trelloBoardList);

        //Then
        assertEquals((trelloBoardList.get(0).getId()), result.get(0).getId());
        assertEquals((trelloBoardList.get(0).getName()), result.get(0).getName());
    }

    @Test
    void mapToList() {
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("1L", "trello list dto", false);
        trelloListsDto.add(trelloListDto);

        //When
        List<TrelloList> result = trelloMapper.mapToList(trelloListsDto);

        //Then
        assertEquals((trelloListsDto.get(0).getId()), result.get(0).getId());
        assertEquals((trelloListsDto.get(0).getName()), result.get(0).getName());
    }

    @Test
    void mapToListDto() {
        //Given
        TrelloList trelloList = new TrelloList("1L", "test List", false);
        trelloLists.add(trelloList);

        //When
        List<TrelloListDto> result = trelloMapper.mapToListDto(trelloLists);

        //Then
        assertEquals((trelloLists.get(0).getId()), result.get(0).getId());
        assertEquals((trelloLists.get(0).getName()), result.get(0).getName());
    }

    @Test
    void mapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard(
                "test card",
                "test card",
                "test card",
                "1L");

        //When
        TrelloCardDto result = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals(trelloCard.getName(), result.getName());
        assertEquals(trelloCard.getDescription(), result.getDescription());
        assertEquals(trelloCard.getPos(), result.getPos());
        assertEquals(trelloCard.getListId(), result.getListId());
    }

    @Test
    void mapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "test card",
                "test card",
                "test card",
                "1L");

        //When
        TrelloCard result = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals(trelloCardDto.getName(), result.getName());
        assertEquals(trelloCardDto.getDescription(), result.getDescription());
        assertEquals(trelloCardDto.getPos(), result.getPos());
        assertEquals(trelloCardDto.getListId(), result.getListId());
    }
}