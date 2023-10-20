package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDto;
import com.nnk.springboot.services.RuleNameService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RuleNameController.class)
public class RuleControllerTest {

    @InjectMocks
    RuleNameController controller;

    @MockBean
    RuleNameService ruleNameService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    Model model;

    @Mock
    BindingResult result;

    @Test
    public void testViewHomePage() {
        when(ruleNameService.getAllRuleName()).thenReturn(new ArrayList<>());
        String view = controller.viewHomePage(model);
        verify(ruleNameService, times(1)).getAllRuleName();
        verify(model, times(1)).addAttribute("ruleNameList", ruleNameService.getAllRuleName());
        assertEquals("ruleNameIndex", view);
    }

    @Test
    public void testShowNewRuleNameDto() {
        String view = controller.showNewRuleNameForm(model);
        verify(model, times(1)).addAttribute(any(String.class), any(RuleNameDto.class));
        assertEquals("newRuleName", view);
    }

    @Test
    public void testCreateRuleName() {
        RuleNameDto dto = new RuleNameDto();
        when(result.hasErrors()).thenReturn(false);
        String view = controller.saveRuleName(dto, result, model);
        verify(ruleNameService, times(1)).createRuleName(any(RuleName.class));
        assertEquals("redirect:/ruleNameHomePage", view);
    }

    @Test
    public void updateRuleNameWhenResultHasErrorsReturnsUpdateRuleNameView() {
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = controller.updateRuleName(1, new RuleNameDto(), bindingResult);

        assertEquals("updateRuleName", viewName);
    }

    @Test
    public void updateRuleNameWhenResultHasNoErrorsAndNoExceptionsReturnsRedirectRuleNameHomePageView() {
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = controller.updateRuleName(1, new RuleNameDto(), bindingResult);

        assertEquals("redirect:/ruleNameHomePage", viewName);
    }

    @Test
    public void showFormForRuleNameUpdateWhenRuleNameExistsReturnsUpdateRuleNameView() {
        // Given
        RuleName ruleName = new RuleName();
        ruleName.setName("RuleName1");
        when(ruleNameService.getRuleNameById(anyInt())).thenReturn(Optional.of(ruleName));

        String viewName = controller.showFormForRuleNameUpdate(1, model);

        assertEquals("updateRuleName", viewName);
    }

    @Test
    public void testDeleteRuleName() {
        String view = controller.deleteRuleName(1);
        verify(ruleNameService, times(1)).deleteRuleName(1);
        assertEquals("redirect:/ruleNameHomePage", view);
    }
}
