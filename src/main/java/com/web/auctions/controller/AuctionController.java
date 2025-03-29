package com.web.auctions.controller;

import com.web.auctions.entity.Auction;
import com.web.auctions.repository.AuctionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/auction")
public class AuctionController {
    private final AuctionRepository auctionRepository;
    public AuctionController(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @GetMapping
    public ResponseEntity<List<Auction>> getAllAuctions() {
        return new ResponseEntity<>(auctionRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Auction> createAuction(@RequestParam String auctionName,
                                                 @RequestParam LocalDateTime startTime,
                                                 @RequestParam LocalDateTime endTime,
                                                 @RequestParam int minBid) {
        Auction auction = new Auction();
        auction.setAuctionName(auctionName);
        auction.setStartTime(startTime);
        auction.setEndTime(endTime);
        auction.setMinBid(minBid);
        return new ResponseEntity<>(auctionRepository.save(auction), HttpStatus.CREATED);
    }
}
