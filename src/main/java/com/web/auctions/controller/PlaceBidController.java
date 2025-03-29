package com.web.auctions.controller;

import com.web.auctions.entity.Auction;
import com.web.auctions.entity.Bid;
import com.web.auctions.repository.AuctionRepository;
import com.web.auctions.repository.BidRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/bid")
public class PlaceBidController {
    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;

    public PlaceBidController(AuctionRepository auctionRepository, BidRepository bidRepository) {
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
    }

    @PostMapping
    public ResponseEntity<Bid> placeBid(@RequestParam String firstName, @RequestParam String lastName, @RequestParam int amount, @RequestParam Long auctionId) {
        Auction  auction = auctionRepository.findById(auctionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Bid bid = new Bid();
        bid.setAuction(auction);
        bid.setTimePlaced(LocalDateTime.now());
        bid.setBid(amount);
        bid.setFirstName(firstName);
        bid.setLastName(lastName);
        bidRepository.save(bid);
        return new ResponseEntity<>(bid, HttpStatus.OK);
    }
}
