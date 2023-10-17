package com.nnk.springboot.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RatingDto {

    @Pattern(message = "id must be a number", regexp = "^\\d+$")
    private String id;

    @Pattern(message = " moodysRating must be a number", regexp = "^\\d+$")
    @NotBlank(message = "moodysRating is required")
    private String moodysRating;

    @Pattern(message = "fitchRating must be a number", regexp = "^\\d+$")
    @NotBlank(message = "fitchRating is required")
    private String fitchRating;

    @Pattern(message = "orderNumber must be a number", regexp = "^\\d+$")
    @NotBlank(message = "orderNumber is required")
    private String orderNumber;

    @Pattern(message = " sandPRating must be a number", regexp = "^\\d+$")
    @NotBlank(message = "sandPRating is required")
    private String sandPRating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoodysRating() {
        return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {
        this.moodysRating = moodysRating;
    }

    public String getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
        this.fitchRating = fitchRating;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
    }
}
