package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;

import java.util.Map;
import java.util.Set;
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
                .map(game -> toDTO(game))
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> getAllGamePlayersDTO(Set<GamePlayer> gamePlayers) {
        return gamePlayers.stream().map(gamePlayer -> MakeGamePlayersDTO(gamePlayer)).collect(Collectors.toList());
    }

    public Map<String, Object> toDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate().toInstant());
        dto.put("gamePlayers", getAllGamePlayersDTO(game.getGameplayers()));
        return dto;
    }

    private Map<String, Object> makePlayerDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("mail", player.getUserName());
        return dto;
    }

    private Map<String, Object> MakeGamePlayersDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", makePlayerDTO(gamePlayer.getPlayer()));
    return dto;
    }

}
