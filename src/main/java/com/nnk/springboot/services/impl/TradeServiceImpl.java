package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public Trade createTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    @Override
    public Optional<Trade> getTradeById(Integer id) {
        return tradeRepository.findById(id);
    }

    @Override
    public List<Trade> getAllTrade() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade updateTrade(Integer id) {
        return tradeRepository.findById(id).get();
    }

    @Override
    public Trade addNewTrade(Integer id, Trade updatedTrade) {
        Optional<Trade> existingTradeOpt = tradeRepository.findById(id);
        if (existingTradeOpt.isPresent()) {
            Trade existingTrade = existingTradeOpt.get();
            existingTrade.setAccount(updatedTrade.getAccount());
            existingTrade.setBuyQuantity(updatedTrade.getBuyQuantity());
            existingTrade.setType(updatedTrade.getType());
            return tradeRepository.save(existingTrade);
        } else {
            throw new RuntimeException("Trade not found with id " + id);
        }
    }

    @Override
    public void deleteTrade(Integer id) {
        tradeRepository.deleteById(id);
    }
}
