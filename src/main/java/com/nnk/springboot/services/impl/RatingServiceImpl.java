package com.nnk.springboot.services.impl;

import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Optional<Rating> getRatingById(Integer id) {
        return ratingRepository.findById(id);
    }

    @Override
    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating updateRating(Integer id) {
        return ratingRepository.findById(id).get();

    }

    @Override
    public void deleteRating(Integer id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public Rating addNewRating(Integer id, Rating updatedRating) {
        Optional<Rating> existingRatingOpt = ratingRepository.findById(id);
        if (existingRatingOpt.isPresent()) {
            Rating existingRating = existingRatingOpt.get();
            existingRating.setFitchRating(updatedRating.getFitchRating());
            existingRating.setMoodysRating(updatedRating.getMoodysRating());
            existingRating.setSandPRating(updatedRating.getSandPRating());
            existingRating.setOrderNumber(updatedRating.getOrderNumber());
            return ratingRepository.save(existingRating);
        } else {
            throw new RuntimeException("Rating not found with id " + id);
        }
    }
}
