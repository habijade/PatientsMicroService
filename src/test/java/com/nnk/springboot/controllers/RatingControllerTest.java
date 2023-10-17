package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @InjectMocks
    RatingController controller;

    @MockBean
    RatingService ratingService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    Model model;

    @Mock
    BindingResult result;

    @Test
    public void testViewHomePage() {
        when(ratingService.getAllRating()).thenReturn(new ArrayList<>());
        String view = controller.viewHomePage(model);
        verify(ratingService, times(1)).getAllRating();
        verify(model, times(1)).addAttribute("ratingList", ratingService.getAllRating());
        assertEquals("ratingIndex", view);
    }

    @Test
    public void testShowNewRatingDto() {
        String view = controller.showNewRatingForm(model);
        verify(model, times(1)).addAttribute(any(String.class), any(RatingDto.class));
        assertEquals("newRating", view);
    }

    @Test
    public void testSaveRating() {
        RatingDto dto = new RatingDto();
        dto.setOrderNumber("123");
        when(result.hasErrors()).thenReturn(false);
        String view = controller.saveRating(dto, result, model);
        verify(ratingService, times(1)).createRating(any(Rating.class));
        assertEquals("redirect:/ratingHomePage", view);
    }

    @Test
    public void updateRating_whenNoErrorsInForm_returnsRedirectRatingHomePageView() {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setFitchRating("FitchRating");
        ratingDto.setOrderNumber("2");
        ratingDto.setMoodysRating("5");
        ratingDto.setId("1L");
        ratingDto.setSandPRating("5");

        String viewName = controller.updateRating(1, ratingDto, bindingResult);

        assertEquals("redirect:/ratingHomePage", viewName);
    }



    @Test
    public void testDeleteRating() {
        String view = controller.deleteRating(1);
        verify(ratingService, times(1)).deleteRating(1);
        assertEquals("redirect:/ratingHomePage",view);
    }
}
