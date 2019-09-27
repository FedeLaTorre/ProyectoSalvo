package com.codeoftheweb.salvo;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Salvo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private int turn;

        public Salvo(){

        }

    @ElementCollection
    @Column(name = "salvoLocations")
    private Set<String> locations;

    public long getId() {
        return id;
    }

    public int getTurn() {
        return turn;
    }

    public Set<String> getLocations() {
        return locations;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayersID")
    private GamePlayer gamePlayer;


    public Salvo (int turn, GamePlayer gamePlayer, Set<String> locations){
        this.turn = turn;
        this.gamePlayer = gamePlayer;
        this.locations = locations;
    }
}