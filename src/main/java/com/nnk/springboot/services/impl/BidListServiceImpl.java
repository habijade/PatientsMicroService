package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BidListServiceImpl implements BidListService {
    @Autowired
    private BidListRepository bidListRepository;

    @Override
    public BidList createBidList(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    @Override
    public Optional<BidList> findById(Integer id) {
        return bidListRepository.findById(id);
    }

    @Override
    public BidList addNewBidList(Integer id, BidList updatedBidList) {
        Optional<BidList> existingBidListOpt = bidListRepository.findById(id);
        if (existingBidListOpt.isPresent()) {
            BidList existingBidList = existingBidListOpt.get();
            existingBidList.setAccount(updatedBidList.getAccount());
            existingBidList.setType(updatedBidList.getType());
            existingBidList.setBidQuantity(updatedBidList.getBidQuantity());
            return bidListRepository.save(existingBidList);
        } else {
            throw new RuntimeException("BidList not found with id " + id);
        }
    }

    @Override
    public List<BidList> getAllBidLists() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList updateBidList(Integer id) {
        return bidListRepository.findById(id).get();
    }

    @Override
    public void deleteBidList(Integer id) {
        bidListRepository.deleteById(id);
    }
}
