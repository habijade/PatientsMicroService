package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.services.CurvePointService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;


@Controller
public class CurveController {

    @Autowired
    private CurvePointService curvePointService;


    @GetMapping("/curvePointHomePage")
    public String viewHomePage(Model model) {

        model.addAttribute("curvePointList", curvePointService.getAllCurvePoint());
        return "curvePointIndex";
    }

    @GetMapping("/newCurvePointForm")
    public String newCurvePointForm(Model model) {
        CurvePointDto curvePointDto = new CurvePointDto();
        model.addAttribute("curvePointDto", curvePointDto);
        return "newCurvePoint";
    }

    @PostMapping("/createCurvePoint")
    public String createCurvePoint(@Valid @ModelAttribute("curvePointDto") CurvePointDto curvePointDto,
                                   BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            return "newCurvePoint";
        }

        try {
            CurvePoint curvePoint = new CurvePoint();
            curvePoint.setCurveId(Integer.valueOf(curvePointDto.getCurveId()));
            curvePoint.setValue(Double.valueOf(curvePointDto.getValue()));
            curvePoint.setTerm(Double.valueOf(curvePointDto.getTerm()));
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            curvePoint.setAsOfDate(now);
            curvePoint.setCreationDate(now);

            curvePointService.createCurvePoint(curvePoint);

            return "redirect:/curvePointHomePage";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "newCurvePoint";
        }
    }

    @GetMapping("/formForCurvePointUpdate/{id}")
    public String formForCurvePointListUpdate(@PathVariable(value = "id") Integer id, Model model) {
        try {
            Optional<CurvePoint> curvePointOpt = curvePointService.getCurvePointById(id);
            if (curvePointOpt.isPresent()) {
                CurvePoint curvePoint = curvePointOpt.get();
                model.addAttribute("curvePointDto", curvePoint);
                return "updateCurvePoint";
            } else {
                return "redirect:/curvePointHomePage";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "redirect:/curvePointHomePage";
        }
    }

    @PostMapping("/updateCurvePoint/{id}")
    public String updateCurvePoint(@PathVariable(value = "id") Integer id, @Valid @ModelAttribute("curvePointDto") CurvePointDto curvePointDto, BindingResult result) {
        if (result.hasErrors()) {
            return "updateCurvePoint";
        }
        try {
            CurvePoint updatedCurvePoint = new CurvePoint();
            updatedCurvePoint.setCurveId(Integer.valueOf(curvePointDto.getCurveId()));
            updatedCurvePoint.setValue(Double.valueOf(curvePointDto.getValue()));
            updatedCurvePoint.setTerm(Double.valueOf(curvePointDto.getTerm()));
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            updatedCurvePoint.setAsOfDate(now);
            updatedCurvePoint.setCreationDate(now);

            curvePointService.addNewCurvePoint(id, updatedCurvePoint);
            return "redirect:/curvePointHomePage";
        } catch (Exception exception) {
            result.rejectValue("value", "", "error : " + exception.getMessage());
            return "updateCurvePoint";
        }
    }


    @GetMapping("/deleteCurvePoint/{id}")
    public String deleteCurvePoint(@PathVariable(value = "id") Integer id) {
        this.curvePointService.deleteCurvePoint(id);
        return "redirect:/curvePointHomePage";
    }
}
