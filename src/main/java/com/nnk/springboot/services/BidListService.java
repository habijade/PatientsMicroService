package com.nnk.springboot.services;


import com.nnk.springboot.domain.BidList;

import java.util.List;
import java.util.Optional;

public interface BidListService {
    BidList createBidList(BidList bidList);

    List<BidList> getAllBidLists();

    BidList updateBidList(Integer id);

    Optional<BidList> findById(Integer id);

    BidList addNewBidList(Integer id, BidList updatedBidList);

    void deleteBidList(Integer id);
}
