package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.beans.XMLDecoder;
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
        return gamePlayers.stream().map(gamePlayer -> makeGamePlayersDTO(gamePlayer)).collect(Collectors.toList());
    }

    private Map<String, Object> toDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate().toInstant().toEpochMilli());
        dto.put("gamePlayers", getAllGamePlayersDTO(game.getGameplayers()));
        return dto;
    }

    private Map<String, Object> makePlayerDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("mail", player.getUserName());
        return dto;
    }

    private Map<String, Object> makeGamePlayersDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", makePlayerDTO(gamePlayer.getPlayer()));
    return dto;
    }

    private Map<String, Object> makeShipDTO(Ship ship){
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("type", ship.getType());
        dto.put("locations", ship.getLocations());
        return dto;
    }
    private List<Map<String, Object>> getAllShips(Set<Ship> ships){
        return ships.stream().map(ship -> makeShipDTO(ship)).collect(Collectors.toList());
    }
    private Map<String, Object> shipsDTO (GamePlayer gamePlayer){
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getGame().getId());
        dto.put("created", gamePlayer.getGame().getCreationDate().toInstant().toEpochMilli());
        dto.put("gamePlayers", getAllGamePlayersDTO(gamePlayer.getGame().getGameplayers()));
        dto.put("ships", getAllShips(gamePlayer.getShips()));
        return dto;

    }

    @Autowired
    private GamePlayerRepository gamePlayerRepository;
    @RequestMapping ("/game_view/{id}")
    public  Map<String, Object> findGamePlayer (@PathVariable long id){
        GamePlayer gamePlayer = gamePlayerRepository.findById(id).get();
        return shipsDTO(gamePlayer);
    }


}
