package com.codeoftheweb.salvo.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

//@Entity le dice a JPA que cree una tabla con la clase Game
@Entity
public class GamePlayer {
    //@Id se encarga de manejar automaticamente los id autoincrementables en una tabla, junto a @GeneratedValue y @GenericGenerator
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date joinDate;

    //Relacion ManyToOne con Game,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_ID")
    private Game game;

    //Relacion ManyToOne con Player, un Player puede tener muchos GamePlayers
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_ID")
    private Player player;

    //Relacion OneToMany con Ship (Un GamePlayer tiene muchos ships)
    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    Set<Ship> ships;

    //Relacion OneToMany con Salvo (Un GamePlayer tiene muchos salvos)
    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    Set<Salvo> salvos;

    //Constructor por defecto
    public GamePlayer() {
    }

    //Getter de los barcos
    public Set<Ship> getShips() {
        return ships;
    }

    //Getter de salvos
    public Set<Salvo> getSalvos() {
        return salvos;
    }

    // Getter del Id
    public long getId() {
        return id;
    }

    //Getter del JoinDate
    public Date getJoinDate() {
        return joinDate;
    }

    // Getter de Game. En caso de no mandar un long se manda@JsonIgnore
    public Game getGame() {
        return game;
    }

    // Getter de Player. En caso de no mandar un long se manda@JsonIgnore
    public Player getPlayer() {
        return player;
    }

    // Constructor personalizado
    public GamePlayer(Game game, Player player, Date joinDate) {
        this.game = game;
        this.player = player;
        this.joinDate = joinDate;
    }

    //Creo un DTO que envie el id y el player de este GamePlayer
    public Map<String, Object> GamePlayersDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", this.getId());
        dto.put("player", this.getPlayer().makePlayerDTO());
        dto.put("joinDate",this.getJoinDate().toInstant().toEpochMilli());
        return dto;
    }

    // Verifico el oponente del gameplayer
    public Optional<GamePlayer> getOpponent() {
        return this.getGame().getGameplayers()
                .stream()
                .filter(gamePlayer -> gamePlayer.getId() != this.getId())
                .findFirst();
    }


}
