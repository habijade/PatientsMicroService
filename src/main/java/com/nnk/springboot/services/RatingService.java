package com.nnk.springboot.services;


import com.nnk.springboot.domain.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingService {
    Rating createRating(Rating rating);
    Optional<Rating> getRatingById(Integer id);
    List<Rating> getAllRating();
    Rating updateRating(Integer id);
    void deleteRating(Integer id);
    Rating addNewRating(Integer id, Rating updatedRating);
}
