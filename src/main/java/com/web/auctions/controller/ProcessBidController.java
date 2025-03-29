package com.web.auctions.controller;

import com.web.auctions.entity.Auction;
import com.web.auctions.entity.Bid;
import com.web.auctions.repository.AuctionRepository;
import com.web.auctions.repository.BidRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/process")
public class ProcessBidController {
    private final BidRepository bidRepository;
    private final AuctionRepository auctionRepository;

    public ProcessBidController(BidRepository bidRepository, AuctionRepository auctionRepository) {
        this.bidRepository = bidRepository;
        this.auctionRepository = auctionRepository;
    }

    @GetMapping
    public ResponseEntity<List<Bid>> winner(@RequestParam Long auctionId) {
        List<Bid> bids = bidRepository.findAllByAuctionId(auctionId);
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND) );
        int highestBid = bids.stream()
                .filter(b -> b.getTimePlaced().isBefore(auction.getEndTime()))
                .map(Bid::getBid).filter(bid -> bid > auction.getMinBid())
                .max(Integer::compareTo).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        List<Bid> winningBids = bids.stream().filter(b -> b.getBid() > highestBid).toList();
        if(winningBids.size() > 1) {
            auction.getEndTime().plusDays(1);
            auction.setMinBid(highestBid + 1);
        } else if(winningBids.size() == 1) {
            auction.setWinner(winningBids.getFirst());
        }
        return  new ResponseEntity<>(winningBids, HttpStatus.OK);
    }
}
