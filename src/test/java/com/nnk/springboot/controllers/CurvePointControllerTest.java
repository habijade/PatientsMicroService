package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDto;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CurveController.class)
public class CurvePointControllerTest {
    @MockBean
    CurvePointService curvePointService;
    @InjectMocks
    CurveController controller;

    @Mock
    private BindingResult bindingResult;

    @Mock
    Model model;

    @Mock
    BindingResult result;

    @Test
    public void testViewHomePage() {
        when(curvePointService.getAllCurvePoint()).thenReturn(new ArrayList<>());
        String view = controller.viewHomePage(model);
        verify(curvePointService, times(1)).getAllCurvePoint();
        verify(model, times(1)).addAttribute("curvePointList", curvePointService.getAllCurvePoint());
        assertEquals("curvePointIndex", view);
    }

    @Test
    public void testShowNewCurvePointForm() {
        String view = controller.newCurvePointForm(model);
        verify(model, times(1)).addAttribute(any(String.class), any(CurvePointDto.class));
        assertEquals("newCurvePoint", view);
    }

    @Test
    public void testCreateCurvePoint() {
        CurvePointDto form = new CurvePointDto();
        form.setCurveId("22");
        form.setValue("30");
        form.setTerm("40");
        when(result.hasErrors()).thenReturn(false);
        String view = controller.createCurvePoint(form, result, model);
        verify(curvePointService, times(1)).createCurvePoint(any(CurvePoint.class));
        assertEquals("redirect:/curvePointHomePage", view);
    }

    @Test
    public void showFormForCurvePointListUpdateWhenCurvePointPresentReturnsUpdateCurvePointView() {
        when(curvePointService.getCurvePointById(anyInt())).thenReturn(Optional.of(new CurvePoint()));

        String viewName = controller.formForCurvePointListUpdate(1, model);

        assertEquals("updateCurvePoint", viewName);
    }

    @Test
    public void updateCurvePointWhenFormHasErrorsReturnsUpdateCurvePointView() {
        when(bindingResult.hasErrors()).thenReturn(true);
        String viewName = controller.updateCurvePoint(1, new CurvePointDto(), bindingResult);
        assertEquals("updateCurvePoint", viewName);
    }


    @Test
    public void updateCurvePointWhenNoErrorsAndExceptionOccursReturnsUpdateCurvePointView() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(curvePointService.updateCurvePoint(anyInt())).thenThrow(RuntimeException.class);

        String viewName = controller.updateCurvePoint(1, new CurvePointDto(), bindingResult);

        assertEquals("updateCurvePoint", viewName);
    }
    @Test
    public void testDeleteCurvePoint() {
        String view = controller.deleteCurvePoint(1);
        verify(curvePointService, times(1)).deleteCurvePoint(1);
        assertEquals("redirect:/curvePointHomePage", view);
    }

}
