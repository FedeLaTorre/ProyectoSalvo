package com.codeoftheweb.salvo.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

//@Entity le dice a JPA que cree una tabla con la clase Game
@Entity
public class Game {
    //@Id se encarga de manejar automaticamente los id autoincrementables en una tabla, junto a @GeneratedValue y @GenericGenerator
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date creationDate;
    // Relacion de @OneToMany con la clase Score (Un Game tiene muchos Scores, un set en este caso)
    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private Set<Score> scores;
    // Relacion de @OneToMany con la clase GamePlayers (Un Game tiene dos Game Players, un set en este caso)
    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private Set<GamePlayer> gameplayers;

    //Clase constructora vacia definida para poder generar una clase constructura con los parametros que queremos
    public Game() {
    }

    //Clase constructora con la estructura que deseamos que tenga nuestra clase. (En este caso solo recibe un Date)
    public Game(Date creationDate) {
        this.creationDate = creationDate;
    }

    //Getter de nuestro Id para poder instanciarlo en otro momento
    public long getId() {
        return id;
    }

    //Getter de nuestro Creation Date para poder instanciarlo en otro momento
    public Date getCreationDate() {
        return creationDate;
    }

    //Getter de nuestros GamePlayers para poder instanciarlo en otro momento (Necesitariamos pasarlo a stream y map para poder navegarlo)
    public Set<GamePlayer> getGameplayers() {
        return gameplayers;
    }

    //Getter de nuestros GamePlayers para poder instanciarlo en otro momento (Necesitariamos pasarlo a stream y map para poder navegarlo)
    public Set<Score> getScores() {
        return scores;
    }


}
