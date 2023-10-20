package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
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
@WebMvcTest(TradeController.class)
public class TradeControllerTest {
        @InjectMocks
    TradeController controller;

    @MockBean
    TradeService tradeService;

    @Mock
    private TradeRepository tradeRepository;


    @Mock
    private BindingResult bindingResult;

    @Mock
    Model model;

    @Mock
    BindingResult result;

    @Test
    public void testViewHomePage() {
        when(tradeService.getAllTrade()).thenReturn(new ArrayList<>());
        String view = controller.viewHomePage(model);
        verify(tradeService, times(1)).getAllTrade();
        verify(model, times(1)).addAttribute("tradeList", tradeService.getAllTrade());
        assertEquals("tradeIndex", view);
    }

    @Test
    public void testShowNewTradeDto() {
        String view = controller.showNewTradeForm(model);
        verify(model, times(1)).addAttribute(any(String.class), any(TradeDto.class));
        assertEquals("newTrade", view);
    }
    @Test
    public void saveTradeWhenTradeDtoValidReturnsRedirectTradeHomePageView() {
        // Given
        TradeDto tradeDto = new TradeDto();
        tradeDto.setAccount("Account");
        tradeDto.setBuyQuantity("10");
        tradeDto.setType("Type");
        tradeDto.setTradeId("1");

        Trade trade = new Trade();
        trade.setAccount(tradeDto.getAccount());
        trade.setBuyQuantity(Double.valueOf(tradeDto.getBuyQuantity()));
        trade.setType(tradeDto.getType());

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = controller.saveTrade(tradeDto, bindingResult, model);

        assertEquals("redirect:/tradeHomePage", viewName);
    }

    @Test
    public void showFormForTradeUpdateWhenTradeExistsReturnsUpdateTradeView() {
        Trade trade = new Trade();
        trade.setAccount("TradeAccount");
        when(tradeService.getTradeById(anyInt())).thenReturn(Optional.of(trade));

        String viewName = controller.showFormForTradeUpdate(1, model);

        assertEquals("updateTrade", viewName);
    }

    @Test
    public void updateTradeWhenNoErrorsInFormReturnsRedirectTradeHomePageView() {
        TradeDto tradeDto = new TradeDto();
        tradeDto.setAccount("TradeAccount");
        tradeDto.setTradeId("1");
        tradeDto.setBuyQuantity("20");
        tradeDto.setType("highLevel");
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = controller.updateTrade(1, tradeDto, bindingResult);

        assertEquals("redirect:/tradeHomePage", viewName);
    }
    

    @Test
    public void testDeleteTrade() {
        String view = controller.deleteTrade(1);
        verify(tradeService, times(1)).deleteTrade(1);
        assertEquals("redirect:/tradeHomePage", view);
    }
}
