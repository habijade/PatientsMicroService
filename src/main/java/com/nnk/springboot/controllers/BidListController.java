package com.nnk.springboot.controllers;

import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;


@Controller
public class BidListController {

    @Autowired
    private BidListService bidListService;


    @GetMapping("/bidListHomePage")
    public String bidListHomePage(Model model) {
        return "redirect:/";
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession httpSession = attr.getRequest().getSession();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ADMIN"));
        httpSession.setAttribute("hasAdminRole", hasAdminRole);
        model.addAttribute("bidListList", bidListService.getAllBidLists());
        return "index";
    }

    @GetMapping("/newBidListForm")
    public String newBidListForm(Model model) {
        BidListDto bidListDto = new BidListDto();
        model.addAttribute("bidListDto", bidListDto);
        return "newBidList";
    }

    @PostMapping("/createBidList")
    public String createBidList(@Valid @ModelAttribute("bidListDto") BidListDto bidListDto,
                              BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "newBidList";
        }
        try {

            BidList bidList = new BidList();
            bidList.setBidListId(Integer.valueOf(bidListDto.getBidListId()));
            bidList.setAccount(bidListDto.getAccount());
            bidList.setType(bidListDto.getType());
            bidList.setBidQuantity(Double.valueOf(bidListDto.getBidQuantity()));

            bidListService.createBidList(bidList);
            return "redirect:/bidListHomePage";
        } catch (Exception exception) {
            result.rejectValue("bidQuantity", "", "error : " + exception.getMessage());
            return "newBidList";
        }
    }


    @GetMapping("/formForBidListUpdate/{id}")
    public String formForBidListUpdate(@PathVariable(value = "id") Integer id, Model model) {
        try {
            Optional<BidList> bidListOpt = bidListService.findById(id);
            if (bidListOpt.isPresent()) {
                BidList bidList = bidListOpt.get();
                model.addAttribute("bidListDto", bidList);
                return "updateBidList";
            } else {
                return "redirect:/bidListHomePage";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "redirect:/bidListHomePage";
        }
    }

    @PostMapping("/updateBidList/{id}")
    public String updateBidList(@PathVariable(value = "id") Integer id, @Valid @ModelAttribute("bidListDto") BidListDto bidListDto, BindingResult result) {
        if (result.hasErrors()) {
            return "updateBidList";
        }
        try {
            BidList updatedBidList = new BidList();
            updatedBidList.setBidListId(Integer.valueOf(bidListDto.getBidListId()));
            updatedBidList.setAccount(bidListDto.getAccount());
            updatedBidList.setType(bidListDto.getType());
            updatedBidList.setBidQuantity(Double.valueOf(bidListDto.getBidQuantity()));

            bidListService.addNewBidList(id, updatedBidList);
            return "redirect:/bidListHomePage";
        } catch (Exception exception) {
            result.rejectValue("bidQuantity", "", "error : " + exception.getMessage());
            return "updateBidList";
        }
    }


    @GetMapping("/deleteBidList/{id}")
    public String deleteBidList(@PathVariable(value = "id") Integer id) {
        this.bidListService.deleteBidList(id);
        return "redirect:/bidListHomePage";
    }
}
