package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.services.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/ratingHomePage")
    public String viewHomePage(Model model) {
        RatingDto ratingList = new RatingDto();
        model.addAttribute("ratingList", ratingService.getAllRating());
        return "ratingIndex"; // Cela renvoie vers la vue "ratings" pour afficher les résultats
    }

    @GetMapping("/showNewRatingForm")
    public String showNewRatingForm(Model model) {
        RatingDto ratingDto = new RatingDto();
        model.addAttribute("ratingDto", ratingDto);
        return "newRating";
    }

    @PostMapping("/saveRating")
    public String saveRating(@Valid @ModelAttribute("ratingDto") RatingDto ratingDto,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "newRating";
        }

        try {
            Rating rating = new Rating();
            rating.setFitchRating(ratingDto.getFitchRating());
            rating.setMoodysRating(ratingDto.getMoodysRating());
            rating.setSandPRating(ratingDto.getSandPRating());
            rating.setOrderNumber(Integer.valueOf(ratingDto.getOrderNumber()));

            ratingService.createRating(rating);

            return "redirect:/ratingHomePage";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "newRating";
        }
    }

    @GetMapping("/showFormForRatingListUpdate/{id}")
    public String showFormForRatingListUpdate(@PathVariable(value = "id") Integer id, Model model) {
        try {
            Optional<Rating> ratingOpt = ratingService.getRatingById(id);
            if (ratingOpt.isPresent()) {
                Rating rating = ratingOpt.get();
                model.addAttribute("ratingDto", rating);
                return "updateRating";
            } else {
                return "redirect:/ratingHomePage";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "redirect:/ratingHomePage";
        }
    }

    @PostMapping("/updateRating/{id}")
    public String updateRating(@PathVariable(value = "id") Integer id, @Valid @ModelAttribute("ratingDto") RatingDto ratingDto, BindingResult result) {
        if (result.hasErrors()) {
            return "updateRating";
        }
        try {
            Rating updatedRating = new Rating();
            updatedRating.setFitchRating(ratingDto.getFitchRating());
            updatedRating.setMoodysRating(ratingDto.getMoodysRating());
            updatedRating.setSandPRating(ratingDto.getSandPRating());
            updatedRating.setOrderNumber(Integer.valueOf(ratingDto.getOrderNumber()));

            ratingService.addNewRating(id, updatedRating);
            return "redirect:/ratingHomePage";
        } catch (Exception exception) {
            result.rejectValue("fitchRating", "", "error : " + exception.getMessage());
            return "updateRating";
        }
    }


    @GetMapping("/deleteRatingList/{id}")
    public String deleteRating(@PathVariable(value = "id") Integer id) {
        this.ratingService.deleteRating(id);
        return "redirect:/ratingHomePage";
    }
}
