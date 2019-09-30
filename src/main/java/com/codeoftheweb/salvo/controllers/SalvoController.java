package com.codeoftheweb.salvo.controllers;

import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.repositories.GameRepository;
import com.codeoftheweb.salvo.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepository;

    @RequestMapping("/games")
    public List<Object> gettAllgames() {
        return gameRepository.findAll()
                .stream()
                .map(game -> game.gameDTO())
                .collect(Collectors.toList());
    }

    @Autowired
    private GamePlayerRepository gamePlayerRepository;
    @RequestMapping ("/game_view/{id}")
    public  Map<String, Object> findGamePlayer (@PathVariable long id){
        GamePlayer gamePlayer = gamePlayerRepository.findById(id).get();
        return gamePlayer.shipsDTO();
    }

    @Autowired
    private PlayerRepository playerRepository;
    @RequestMapping("/leaderboard")
    public List<Object> getAllLeaderboard(){
        return playerRepository.findAll().stream().map(player -> player.makePlayerScoreDTO()).collect(Collectors.toList());
    }

}
