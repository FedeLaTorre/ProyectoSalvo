package com.codeoftheweb.salvo.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date joinDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_ID")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_ID")
    private Player player;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    Set<Ship> ships;


    public Set<Ship> getShips() {
        return ships;
    }


    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    Set<Salvo> salvos;

    public Set<Salvo> getSalvos() {
        return salvos;
    }

    public GamePlayer() {
    }

    public long getId() {
        return id;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    // En caso de no mandar un long se manda@JsonIgnore
    public Game getGame() {
        return game;
    }

    // En caso de no mandar un long se manda@JsonIgnore
    public Player getPlayer() {
        return player;
    }

    public GamePlayer(Game game, Player player, Date joinDate) {
        this.game = game;
        this.player = player;
        this.joinDate = joinDate;
    }

    public Map<String, Object> makeGamePlayersDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", this.getId());
        dto.put("player", this.getPlayer().makePlayerDTO());
        return dto;
    }

    public Map<String, Object> shipsDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", this.getGame().getId());
        dto.put("created", this.getGame().getCreationDate().toInstant().toEpochMilli());
        dto.put("gamePlayers", this.getGame().listGamePlayers(getGame().getGameplayers()));
        dto.put("ships", this.getAllShips(getShips()));
        dto.put("salvoes", this.listAllSalvos(getGame().getGameplayers().stream().flatMap(gamePlayer1 -> gamePlayer1.getSalvos().stream()).collect(Collectors.toSet())));
        return dto;

    }


    public List<Map<String, Object>> getAllShips(Set<Ship> ships) {
        return ships.stream().map(ship -> ship.makeShipDTO()).collect(Collectors.toList());
    }


    public List<Map<String, Object>> listAllSalvos(Set<Salvo> salvos) {
        return salvos.stream().map(salvo -> salvo.makeSalvoDTO()).collect(Collectors.toList());
    }


}
