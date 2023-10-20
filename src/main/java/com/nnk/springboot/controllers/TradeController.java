package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDto;
import com.nnk.springboot.services.TradeService;
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
public class TradeController {

    @Autowired
    private TradeService tradeService;


    @GetMapping("/tradeHomePage")
    public String viewHomePage(Model model) {

        model.addAttribute("tradeList", tradeService.getAllTrade());
        return "tradeIndex";
    }

    @GetMapping("/showNewTradeForm")
    public String showNewTradeForm(Model model) { // TradeForm instead of Trade
        TradeDto tradeDto = new TradeDto();
        model.addAttribute("tradeDto", tradeDto);
        return "newTrade";
    }

    @PostMapping("/saveTrade")
    public String saveTrade(@Valid @ModelAttribute("tradeDto") TradeDto tradeDto,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "newTrade";
        }

        try {
            Trade trade = new Trade();
            trade.setAccount(tradeDto.getAccount());
            trade.setBuyQuantity(Double.valueOf(tradeDto.getBuyQuantity()));
            trade.setType(tradeDto.getType());

            tradeService.createTrade(trade);

            return "redirect:/tradeHomePage";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "newTrade";
        }
    }

    @GetMapping("/showFormForTradeUpdate/{tradeId}")
    public String showFormForTradeUpdate(@PathVariable(value = "tradeId") Integer id, Model model) {
        try {
            Optional<Trade> tradeOpt = tradeService.getTradeById(id);
            if (tradeOpt.isPresent()) {
                Trade trade = tradeOpt.get();
                model.addAttribute("tradeDto", trade);
                return "updateTrade";
            } else {
                return "redirect:/tradeHomePage";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "redirect:/tradeHomePage";
        }
    }

    @PostMapping("/updateTrade/{tradeId}")
    public String updateTrade(@PathVariable(value = "tradeId") Integer tradeId, @Valid @ModelAttribute("tradeForm") TradeDto tradeForm, BindingResult result) {
        if (result.hasErrors()) {
            return "updateTrade";
        }
        try {
            Trade updatedTrade = new Trade();
            updatedTrade.setAccount(tradeForm.getAccount());
            updatedTrade.setBuyQuantity(Double.valueOf(tradeForm.getBuyQuantity()));
            updatedTrade.setType(tradeForm.getType());

            tradeService.addNewTrade(tradeId, updatedTrade);
            return "redirect:/tradeHomePage";
        } catch (Exception exception) {
            result.rejectValue("account", "", "error : " + exception.getMessage());
            return "updateTrade";
        }
    }


    @GetMapping("/deleteTrade/{tradeId}")
    public String deleteTrade(@PathVariable(value = "tradeId") Integer tradeId) {
        this.tradeService.deleteTrade(tradeId);
        return "redirect:/tradeHomePage";
    }
}
