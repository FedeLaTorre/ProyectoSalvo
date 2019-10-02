package com.codeoftheweb.salvo.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String type;

    @ElementCollection
    @Column(name = "shipLocations")
    private Set<String> locations;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayerID")
    private GamePlayer gamePlayer;

    public Ship() {
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Set<String> getLocations() {
        return locations;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public Ship(String type, Set<String> locations, GamePlayer gamePlayer) {
        this.type = type;
        this.locations = locations;
        this.gamePlayer = gamePlayer;
    }


    public Map<String, Object> makeShipDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("type", this.getType());
        dto.put("shipLocations", this.getLocations());
        return dto;
    }


}
