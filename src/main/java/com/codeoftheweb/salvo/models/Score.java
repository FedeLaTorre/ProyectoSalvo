package com.codeoftheweb.salvo.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

//@Entity le dice a JPA que cree una tabla con la clase Game
@Entity
public class Score {
    //@Id se encarga de manejar automaticamente los id autoincrementables en una tabla, junto a @GeneratedValue y @GenericGenerator
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    //Relacion ManyToOne con Game. Un Game puede tener muchos scores.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_ID")
    private Game game;

    //Relacion ManyToOne con Player. Un Player puede tener muchos scores.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_ID")
    private Player player;

    private double score;
    private Date finishDate;

    //Constructor por defecto
    public Score() {
    }

    //Constructor Personalizado. Recibe Game, Player, Score y un Date
    public Score(Game game, Player player, double score, Date finishDate) {
        this.game = game;
        this.player = player;
        this.score = score;
        this.finishDate = finishDate;
    }

    //Getter de Id.
    public long getId() {
        return id;
    }

    //Getter de game
    public Game getGame() {
        return game;
    }

    //Getter de PLayer
    public Player getPlayer() {
        return player;
    }

    //Getter de Score
    public double getScore() {
        return score;
    }

    //Getter de Date
    public Date getFinishDate() {
        return finishDate;
    }

    //DTO de Score. Envia el Player, el Score y el Date
    public Map<String, Object> makeScoreDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("player", this.player.getId());
        dto.put("score", this.getScore());
        dto.put("finishDate", this.getFinishDate());
        return dto;
    }

}
