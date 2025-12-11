package com.fxdidier.trading_portal.app.domain.entity;

import com.fxdidier.trading_portal.util.enums.LinkType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "trade_links")
@Getter
@Setter
public class TradeLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trade_id", nullable = false)
    private Trade trade;

    @Column(nullable = false, length = 600)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "link_type", nullable = false, length = 20)
    private LinkType linkType;

}