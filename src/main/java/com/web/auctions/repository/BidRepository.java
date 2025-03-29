package com.web.auctions.repository;

import com.web.auctions.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findAllByAuctionId(@RequestParam Long auctionId);
}
