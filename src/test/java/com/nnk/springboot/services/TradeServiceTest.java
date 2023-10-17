package com.nnk.springboot.services;

import com.nnk.springboot.services.impl.TradeServiceImpl;
import com.nnk.springboot.domain.Trade
;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @InjectMocks
    private TradeServiceImpl tradeServiceImpl;

    @Mock
    private TradeRepository tradeRepository;


    @Captor
    private ArgumentCaptor<Trade> TradeArgumentCaptor;

    private Trade createTrade(int id) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return new Trade(1, "account1", "Admin", 20.0, 40.0, 50.0, 60.0, timestamp, "secured", "high", "ben", "high", "book1",
                "Creation1", timestamp, "Revision1", timestamp, "Deal1", "DealType1", "SourceListId1", "Side1");
    }

    @Test
    public void testNotAddNewTrade() {Integer id = 1;
        Trade updateTrade = createTrade(id);
        when(tradeRepository.findById(id)).thenReturn(Optional.empty());
        Executable executable = () -> tradeServiceImpl.addNewTrade(id, updateTrade);
        assertThrows(RuntimeException.class, executable, "Trade not found with id " + id);
        verify(tradeRepository).findById(id);
        verifyNoMoreInteractions(tradeRepository);
    }

        @Test
    public void testGetTradeById(){
        Integer id = 1;
        Trade expectedTrade = createTrade(id);
        when(tradeRepository.findById(id)).thenReturn(Optional.of(expectedTrade));
        Optional<Trade> result = tradeServiceImpl.getTradeById(id);
        verify(tradeRepository).findById(id);
        assertTrue(result.isPresent());
        assertEquals(expectedTrade, result.get());
    }


    @Test
    public void testAddNewTrade() {
        Integer id = 1;
        Trade updateTrade = createTrade(id);
        when(tradeRepository.findById(id)).thenReturn(Optional.of(updateTrade));

        Trade result = tradeServiceImpl.addNewTrade(id, updateTrade);
        verify(tradeRepository).findById(1);
        verify(tradeRepository).save(TradeArgumentCaptor.capture());
    }

    @Test
    public void testSaveTrade() {
        Trade trade = createTrade(1);
        tradeServiceImpl.createTrade(trade);

        verify(tradeRepository).save(TradeArgumentCaptor.capture());
        Trade capturedTrade = TradeArgumentCaptor.getValue();
        assertThat(capturedTrade).isEqualTo(trade);
    }

        @Test
    public void testListAllTrade() {
        List<Trade> expectedTrade = new ArrayList<>();
        expectedTrade.add(createTrade(1));

        expectedTrade.add(createTrade(2));

        when(tradeRepository.findAll()).thenReturn(expectedTrade);

        List<Trade> actualTrade = tradeServiceImpl.getAllTrade();

        verify(tradeRepository).findAll();
        assertThat(actualTrade).isEqualTo(expectedTrade);
    }

    @Test
    public void testUpdateRating(){
        Integer id = 1;
        Trade expectedTrade = createTrade(id);
        Optional<Trade> expectedOptional = Optional.of(expectedTrade);

        when(tradeRepository.findById(id)).thenReturn(expectedOptional);
        Trade actualTrade = tradeServiceImpl.updateTrade(id);

        verify(tradeRepository).findById(id);
        assertThat(actualTrade).isEqualTo(expectedTrade);
    }

    @Test
    public void testDelete() {

        Trade expectedTrade = createTrade(1);
        tradeServiceImpl.deleteTrade(expectedTrade.getTradeId());

        verify(tradeRepository, times(1)).deleteById(expectedTrade.getTradeId());
    }
}
