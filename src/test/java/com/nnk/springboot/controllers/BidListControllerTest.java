package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDto;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BidListController.class)
public class BidListControllerTest {

    @MockBean
    private BidListService bidListService;
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
    @InjectMocks
    private BidListController controller;

    @Captor
    private ArgumentCaptor<BidList> bidListArgumentCaptor;


    @Mock
    private Model model;
    @Mock
    private BindingResult result;
    @Test
    public void testBidListHomePage() {
        String view = controller.bidListHomePage(model);
        assertEquals("redirect:/", view);
    }

    @Test
    @WithMockUser(value = "spring")
    public void getAllBidLists() throws Exception {
        BidList bidList = new BidList();
        bidList.setBidListId(1);
        bidList.setAccount("Account1");
        bidList.setType("Type1");
        bidList.setBidQuantity(100.0);

        List<BidList> bidListList = Arrays.asList(bidList);

        when(bidListService.getAllBidLists()).thenReturn(bidListList);

        mvc.perform(get("/"))

                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bidListList"));
    }

    @Test
    public void testNewBidListForm() {
        String view = controller.newBidListForm(model);
        verify(model, times(1)).addAttribute(any(String.class), any(BidListDto.class));
        assertEquals("newBidList", view);
    }

    @Test
    public void testCreateBidList() {
        BidListDto dto = new BidListDto();
        dto.setBidListId("18");
        dto.setBidQuantity("200");
        when(result.hasErrors()).thenReturn(false);
        String view = controller.createBidList(dto, result, model);
        verify(bidListService, times(1)).createBidList(any(BidList.class));
        assertEquals("redirect:/bidListHomePage", view);
    }

    @Test
    public void showFormForBidListUpdateWhenBidListReturnsUpdateBidListView() {
        when(bidListService.findById(anyInt())).thenReturn(Optional.of(new BidList()));

        String viewName = controller.formForBidListUpdate(1, model);

        assertEquals("updateBidList", viewName);
    }
    @Test
    public void updateBidListWhenNoErrorsAndExceptionOccursReturnsUpdateBidListView() {
        when(result.hasErrors()).thenReturn(false);
        when(bidListService.addNewBidList(anyInt(), any(BidList.class))).thenThrow(RuntimeException.class);
        String viewName = controller.updateBidList(1, new BidListDto(), result);
        assertEquals("updateBidList", viewName);
    }

    @Test
    public void testDeleteBidList() {
        String view = controller.deleteBidList(1);
        verify(bidListService, times(1)).deleteBidList(1);
        assertEquals("redirect:/bidListHomePage", view);
    }

}
