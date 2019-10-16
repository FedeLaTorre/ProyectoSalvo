package com.codeoftheweb.salvo.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//@Entity le dice a JPA que cree una tabla con la clase Game
@Entity
public class Salvo {

    //@Id se encarga de manejar automaticamente los id autoincrementables en una tabla, junto a @GeneratedValue y @GenericGenerator
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private int turn;

    //@Column me crea una columna temporal para agregar salvoLocations, sino tendria que crear otra clase y otro repositorio por un solo atributo
    @ElementCollection
    @Column(name = "salvoLocations")
    private Set<String> salvoLocations;

    //Relacion @ManyToOne con GamePlayer. Un GamePlayer tiene muchos salvos
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayersID")
    private GamePlayer gamePlayer;

    //Constructor por defecto
    public Salvo() {

    }

    //Constructor personalizado. Recibe el turno, el GamePlayer y las locations
    public Salvo(int turn, GamePlayer gamePlayer, Set<String> salvoLocations) {
        this.turn = turn;
        this.gamePlayer = gamePlayer;
        this.salvoLocations = salvoLocations;
    }

    //Getter del id
    public long getId() {
        return id;
    }

    //Getter del turno
    public int getTurn() {
        return turn;
    }

    //Getter de las locations. En este caso
    public Set<String> getSalvoLocations() {
        return salvoLocations;
    }

    //Getter de los GamePlayers
    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    //DTO de Salvo, se le pasa el turno, el player y las locations
    public Map<String, Object> makeSalvoDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("turn", this.getTurn());
        dto.put("player", this.getGamePlayer().getPlayer().getId());
        dto.put("locations", this.getSalvoLocations());
        return dto;
    }


}
