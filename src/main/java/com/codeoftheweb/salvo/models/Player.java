package com.codeoftheweb.salvo.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

//@Entity le dice a JPA que cree una tabla con la clase Game
@Entity
public class Player {
    //@Id se encarga de manejar automaticamente los id autoincrementables en una tabla, junto a @GeneratedValue y @GenericGenerator
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String userName;
    private String password;


    //Relacion OneToMany con Score. Un Player tiene muchos scores.
    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private Set<Score> scores;

    //Relacion OneToMany con GamePlayer. Un Player tiene muchos GamePlayers
    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private Set<GamePlayer> gameplayers;

    //Constructor por defecto.
    public Player() {
    }

    //Constructor personalizado.
    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    //Getter de GamePlayers.
    public Set<GamePlayer> getGameplayers() {
        return gameplayers;
    }

    //Getter de Id.
    public long getId() {
        return id;
    }

    //Getter de UserName.
    public String getUserName() {
        return userName;
    }

    //Getter de Score.
    public Set<Score> getScores() {
        return scores;
    }

    //Getter del Password.
    public String getPassword() {
        return password;
    }

    //Setter del password.
    public void setPassword(String password) {
        this.password = password;
    }

    //DTO del Player, recibe Id e Email.
    public Map<String, Object> makePlayerDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", this.getId());
        dto.put("email", this.getUserName());
        return dto;
    }

    //Obtengo el Score total.
    public Double getTotalScore() {
        return this.getWinScore() * 1.0D + this.getTiedScore() * 0.5D;
    }

    //Obtengo el Score por victoria.
    public long getWinScore() {
        return this.getScores().stream().filter(score -> score.getScore() == 1.0D).count();
    }

    //Obtengo el score por perder.
    public long getLostScore() {
        return this.getScores().stream().filter(score -> score.getScore() == 0).count();
    }

    //Obtengo el score por empatar.
    public long getTiedScore() {
        return this.getScores().stream().filter(score -> score.getScore() == 0.5D).count();
    }
}
