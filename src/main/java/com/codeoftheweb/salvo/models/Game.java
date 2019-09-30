package com.codeoftheweb.salvo.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private  Date creationDate;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    Set<Score> scores;
    @OneToMany (mappedBy = "game", fetch = FetchType.EAGER)
    Set <GamePlayer> gameplayers;

    public Game (){}


    public Game (Date creationDate){this.creationDate = creationDate;}
    public long getId() {return id;}

    public Date getCreationDate() {return creationDate;}


    public Set<GamePlayer> getGameplayers() {return gameplayers;}

    public Set<Score> getScores(){return scores;}

    public Map<String, Object> gameDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", this.getId());
        dto.put("created", this.getCreationDate().toInstant().toEpochMilli());
        dto.put("gamePlayers", this.listGamePlayers(getGameplayers()));
        dto.put("score", this.listScores());
        return dto;
    }

    public  List<Map<String, Object>> listGamePlayers(Set<GamePlayer> gamePlayers) {
        return gamePlayers.stream().map(gamePlayer -> gamePlayer.makeGamePlayersDTO()).collect(Collectors.toList());
    }

    public List<Map<String, Object>> listScores(){
        return  this.getScores().stream().map(score -> score.makeScoreDTO()).collect(Collectors.toList());
    }


}
