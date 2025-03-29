package com.web.auctions.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Auction {
    @Id
    @GeneratedValue
    private Long id;
    String auctionName;
    LocalDateTime startTime;
    LocalDateTime endTime;
    private int minBid;
    @OneToMany
    Set<Bid> bids;
    @OneToOne
    Bid winner;
}
