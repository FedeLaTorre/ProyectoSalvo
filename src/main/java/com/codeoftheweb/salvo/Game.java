package com.codeoftheweb.salvo;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private  Date creationDate;


    public Game (){

    }


    public Game (Date creationDate){

        this.creationDate = creationDate;

    }
    public long getId() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }


    public Map<String, Object> toDTO(){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", this.getId());
        dto.put("created", this.getCreationDate().toInstant());
        return dto;
    }

    @OneToMany (mappedBy = "game", fetch = FetchType.EAGER)
    Set <GamePlayer> gameplayers;


}
