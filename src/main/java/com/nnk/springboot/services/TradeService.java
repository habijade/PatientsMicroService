package com.nnk.springboot.services;


import com.nnk.springboot.domain.Trade;

import java.util.List;
import java.util.Optional;

public interface TradeService {
    Trade createTrade(Trade trade);
    Optional<Trade> getTradeById(Integer id);
    List<Trade> getAllTrade();
    Trade updateTrade(Integer id);
    Trade addNewTrade(Integer id, Trade updatedTrade);
    void deleteTrade(Integer id);
}
