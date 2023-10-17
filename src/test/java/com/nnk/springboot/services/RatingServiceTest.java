package com.nnk.springboot.services;

import com.nnk.springboot.services.impl.RatingServiceImpl;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {
    @InjectMocks
    private RatingServiceImpl ratingServiceImpl;

    @Mock
    private RatingRepository ratingRepository;


    @Captor
    private ArgumentCaptor<Rating> RatingArgumentCaptor;

    private Rating createRating(int id) {
        return new Rating(1, "high", "high", "low", 1);
    }

    @Test
    public void testNotAddNewRating() {
        Integer id = 1;
        Rating updateRating = createRating(id);

        when(ratingRepository.findById(id)).thenReturn(Optional.empty());

        Executable executable = () -> ratingServiceImpl.addNewRating(id, updateRating);
        assertThrows(RuntimeException.class, executable, "Rating not found with id " + id);
        verify(ratingRepository).findById(id);
        verifyNoMoreInteractions(ratingRepository);
    }

        @Test
    public void testGetRatingById(){
        Integer id = 1;
        Rating expectedRating = createRating(id);
        when(ratingRepository.findById(id)).thenReturn(Optional.of(expectedRating));
        Optional<Rating> result = ratingServiceImpl.getRatingById(id);
        verify(ratingRepository).findById(id);
        assertTrue(result.isPresent());
        assertEquals(expectedRating, result.get());
    }


    @Test
    public void testAddNewRating() {
        Integer id = 1;
        Rating updateRating = createRating(id);
        when(ratingRepository.findById(id)).thenReturn(Optional.of(updateRating));

        Rating result = ratingServiceImpl.addNewRating(id, updateRating);
        verify(ratingRepository).findById(1);
        verify(ratingRepository).save(RatingArgumentCaptor.capture());
    }


    @Test
    public void testSaveRating() {
        Rating rating = createRating(1);
        ratingServiceImpl.createRating(rating);

        verify(ratingRepository).save(RatingArgumentCaptor.capture());
        Rating capturedRating = RatingArgumentCaptor.getValue();
        assertThat(capturedRating).isEqualTo(rating);
    }

    @Test
    public void testListAllRating() {
        List<Rating> expectedRating = new ArrayList<>();
        expectedRating.add(new Rating(1, "high", "high", "low", 1));
        expectedRating.add(new Rating(9, "Low", "Low", "low", 9));

        when(ratingRepository.findAll()).thenReturn(expectedRating);
        List<Rating> actualRating = ratingServiceImpl.getAllRating();

        verify(ratingRepository).findAll();
        assertThat(actualRating).isEqualTo(expectedRating);
    }

    @Test
    public void testUpdateRating() {
        Integer id = 1;
        Rating expectedRating = createRating(id);
        Optional<Rating> expectedOptional = Optional.of(expectedRating);

        when(ratingRepository.findById(id)).thenReturn(expectedOptional);

        Rating actualRating = ratingServiceImpl.updateRating(id);

        verify(ratingRepository).findById(id);
        assertThat(actualRating).isEqualTo(expectedRating);
    }

    @Test
    public void testDelete() {
        Integer id = 1;
        Rating expectedRating = createRating(id);
        ratingServiceImpl.deleteRating(expectedRating.getId());

        verify(ratingRepository, times(1)).deleteById(id);

    }
}
