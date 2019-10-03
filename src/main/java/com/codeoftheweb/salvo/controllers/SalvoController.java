package com.codeoftheweb.salvo.controllers;

import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repositories.GamePlayerRepository;
import com.codeoftheweb.salvo.repositories.GameRepository;
import com.codeoftheweb.salvo.repositories.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepository;

    @RequestMapping("/games")
    public Map<String, Object> gettAllgames(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();

        if (isGuest(authentication)) {
            dto.put("player", "Guest");
        } else {
            Player player = playerRepository.findByUserName(authentication.getName());
            dto.put("player", player.makePlayerDTO());
        }
        dto.put("games", gameRepository.findAll()
                .stream()
                .map(game -> game.gameDTO())
                .collect(Collectors.toList()));
        return dto;
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String email, @RequestParam String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        } else if (playerRepository.findByUserName(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        } else {
            playerRepository.save(new Player(email, passwordEncoder.encode(password)));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }


    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @RequestMapping("/game_view/{id}")
    public Map<String, Object> findGamePlayer(@PathVariable long id) {
        GamePlayer gamePlayer = gamePlayerRepository.findById(id).get();
        return gamePlayer.shipsDTO();
    }

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping("/leaderBoard")
    public List<Object> getAllLeaderboard() {
        return playerRepository.findAll().stream().map(player -> player.makePlayerScoreDTO()).collect(Collectors.toList());
    }

}
