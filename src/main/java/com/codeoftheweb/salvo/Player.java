package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Player {

    /**
     * Atributos,
     * @param id =
     */


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String userName;

    public Player() {
    }

    public Player(String userName) {

        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    @OneToMany (mappedBy = "player", fetch = FetchType.EAGER)
    Set<GamePlayer> gameplayers;

}
