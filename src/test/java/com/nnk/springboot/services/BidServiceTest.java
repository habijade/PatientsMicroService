package com.nnk.springboot.services;

import com.nnk.springboot.services.impl.BidListServiceImpl;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidServiceTest {
    @InjectMocks
    private BidListServiceImpl bidListServiceImpl;

    @Mock
    private BidListRepository bidListRepository;


    @Captor
    private ArgumentCaptor<BidList> bidListArgumentCaptor;

    private BidList createBidList(int id) {
        LocalDateTime now = LocalDateTime.now();
        return new BidList(
                id, "Account1", "Type1", 20.0, 50.0, 60.0,
                70.0, "Benchmark1", now, "Commentary1", "Security1", "Status1",
                "BidListr1", "Book1", "Creation1", now,
                "Revision1", now, "Deal1", "DealType1", "SourceListId1", "Side1"
        );
    }

    @Test
    public void testNotAddNewBidList() {
        Integer id = 1;
        BidList updateBidList = createBidList(id);

        when(bidListRepository.findById(id)).thenReturn(Optional.empty());

        Executable executable = () -> bidListServiceImpl.addNewBidList(id, updateBidList);
        assertThrows(RuntimeException.class, executable, "BidList not found with id " + id);
        verify(bidListRepository).findById(id);
        verifyNoMoreInteractions(bidListRepository);
    }

    @Test
    public void testGetBidListId() {
        Integer id = 1;
        BidList expectedBidList = createBidList(id);
        when(bidListRepository.findById(id)).thenReturn(Optional.of(expectedBidList));
        Optional<BidList> result = bidListServiceImpl.findById(id);
        verify(bidListRepository).findById(id);
        assertTrue(result.isPresent());
        assertEquals(expectedBidList, result.get());

    }

    @Test
    public void testAddNewBidList() {
        Integer id = 1;
        BidList updateBidList = createBidList(id);
        when(bidListRepository.findById(id)).thenReturn(Optional.of(updateBidList));

        BidList result = bidListServiceImpl.addNewBidList(id, updateBidList);
        verify(bidListRepository).findById(1);
        verify(bidListRepository).save(bidListArgumentCaptor.capture());
    }

    @Test
    public void testSaveBidList() {
        BidList bidList = createBidList(1);

        bidListServiceImpl.createBidList(bidList);

        verify(bidListRepository).save(bidListArgumentCaptor.capture());
        BidList capturedBidList = bidListArgumentCaptor.getValue();
        assertThat(capturedBidList).isEqualTo(bidList);
    }

    @Test
    public void testListAllBidList() {
        List<BidList> expectedBidList = new ArrayList<>();
        expectedBidList.add(createBidList(1));
        expectedBidList.add(createBidList(2));

        when(bidListRepository.findAll()).thenReturn(expectedBidList);

        List<BidList> actualBidList = bidListServiceImpl.getAllBidLists();

        verify(bidListRepository).findAll();
        assertThat(actualBidList).isEqualTo(expectedBidList);
    }

    @Test
    public void testUpdateBidList() {
        Integer id = 1;
        BidList expectedBidList = createBidList(id);
        Optional<BidList> expectedOptional = Optional.of(expectedBidList);
        when(bidListRepository.findById(id)).thenReturn(expectedOptional);

        BidList actualBidList = bidListServiceImpl.updateBidList(id);

        verify(bidListRepository).findById(id);
        assertThat(actualBidList).isEqualTo(expectedBidList);
    }

    @Test
    public void testDeleteBidList() {
        Integer id = 1;
        BidList expectedBidList = createBidList(id);

        bidListServiceImpl.deleteBidList(expectedBidList.getBidListId());

        verify(bidListRepository, times(1)).deleteById(id);
    }

}
