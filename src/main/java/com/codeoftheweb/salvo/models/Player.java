package com.codeoftheweb.salvo.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String userName;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    Set<Score> scores;

    @OneToMany (mappedBy = "player", fetch = FetchType.EAGER)
    Set<GamePlayer> gameplayers;

    public Player() {}

    public Set<GamePlayer> getGameplayers() {return gameplayers;}

    public Player(String userName) {this.userName = userName;}

    public long getId() {return id;}

    public String getUserName() {return userName;}

    public Set<Score> getScores(){return scores;}

    public Map<String, Object> makePlayerDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", this.getId());
        dto.put("email", this.getUserName());
        return dto;
    }


    public  Map<String, Object> makePlayerScoreDTO(){
        Map<String, Object> dto = new LinkedHashMap<>();
        Map<String, Object> score = new LinkedHashMap<>();

        dto.put("id", this.getId());
        dto.put("email", this.getUserName());
        dto.put("score", score);
            score.put("total", this.getTotalScore());
            score.put("won", this.getWinScore());
            score.put("lost", this.getLostScore());
            score.put("tied", this.getTiedScore());
    return dto;
    }
    //Crear total score para el leaderboard.

    public Double getTotalScore(){
        return this.getWinScore() * 1.0D + this.getTiedScore() * 0.5D;

    }

    public long getWinScore(){
        return this.getScores().stream().filter(score -> score.getScore() == 1.0D).count();
    }

    public long getLostScore(){
        return this.getScores().stream().filter(score -> score.getScore() == 0).count();
    }
    public long getTiedScore(){
        return this.getScores().stream().filter(score -> score.getScore() == 0.5D).count();
    }
}
