package com.nnk.springboot.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CurvePointDto {

    @NotBlank(message = "curveId is required")
    @Pattern(message = "id must be a number", regexp = "^\\d+$")
    private String curveId;

    @NotBlank(message = "term is required")
    private String term;

    @NotBlank(message = "value is required")
    private String value;

    public String getCurveId() {
        return curveId;
    }

    public void setCurveId(String curveId) {
        this.curveId = curveId;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
