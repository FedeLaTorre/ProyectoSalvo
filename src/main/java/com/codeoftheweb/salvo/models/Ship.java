package com.codeoftheweb.salvo.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

//@Entity le dice a JPA que cree una tabla con la clase Game
@Entity
public class Ship {


    //@Id se encarga de manejar automaticamente los id autoincrementables en una tabla, junto a @GeneratedValue y @GenericGenerator
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String type;

    //@Column me crea una columna temporal para agregar shipLocations, sino tendria que crear otra clase y otro repositorio por un solo atributo
    @ElementCollection
    @Column(name = "shipLocations")
    private Set<String> locations;

    //Relacion ManyToOne con Gameplayer. Un GamePlayer tiene muchas ships
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayerID")
    private GamePlayer gamePlayer;

    //Constructor por defecto
    public Ship() {
    }

    //Construsctor personalizado. Se le pasa Type, Locations y GamePlayer
    public Ship(String type, Set<String> locations, GamePlayer gamePlayer) {
        this.type = type;
        this.locations = locations;
        this.gamePlayer = gamePlayer;
    }

    //Getter de Id
    public long getId() {
        return id;
    }

    //Getter de Type
    public String getType() {
        return type;
    }

    //Getter de Locations
    public Set<String> getLocations() {
        return locations;
    }

    //Getter de GamePlayer
    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    //Setter de GamePlayer
    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    //DTO de Ship. Envia informacion de Type y de Locations
    public Map<String, Object> makeShipDTO() {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("type", this.getType());
        dto.put("locations", this.getLocations());
        return dto;
    }


}
