package com.codeoftheweb.salvo.controllers;

import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepository;

    @RequestMapping("/games")
    private Map<String, Object> gettAllgames(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();

        if (isGuest(authentication)) {
            dto.put("player", "Guest");
        } else {
            Player player = playerRepository.findByUserName(authentication.getName());
            dto.put("player", player.makePlayerDTO());
        }
        dto.put("games", gameRepository.findAll()
                .stream()
                .map(game -> gameDTO(game))
                .collect(Collectors.toList()));
        return dto;
    }

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    private ResponseEntity<Map<String, Object>> createNewGame(Authentication authentication) {
        if (isGuest(authentication)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            Player player = playerRepository.findByUserName(authentication.getName());
            Date date = new Date();
            Game game = gameRepository.save(new Game(date));
            GamePlayer gamePlayer = gamePlayerRepository.save(new GamePlayer(game, player, date));
            return new ResponseEntity<>(makeMap("gpid", gamePlayer.getId()), HttpStatus.CREATED);

        }
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    @Autowired
    ShipRepository shipRepository;

    @RequestMapping(path = "/games/players/{id}/ships", method = RequestMethod.POST)
    private ResponseEntity<Map<String, Object>> createShips(Authentication authentication, @PathVariable long id, @RequestBody Set<Ship> ships) {

        if (isGuest(authentication)) {
            return new ResponseEntity<>(makeMap("error", "No player logged"), HttpStatus.FORBIDDEN);
        }
        if (!gamePlayerRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(makeMap("error", "There is no player with that id"), HttpStatus.FORBIDDEN);

        }
        GamePlayer gamePlayer = gamePlayerRepository.findById(id).get();
        if (!authentication.getName().equals(gamePlayer.getPlayer().getUserName())) {
            return new ResponseEntity<>(makeMap("error", "This is not your game"), HttpStatus.FORBIDDEN);

        }
        if (getAllShips(gamePlayer).isEmpty()) {
            ships.forEach(ship -> ship.setGamePlayer(gamePlayer));
            shipRepository.saveAll(ships);
            return new ResponseEntity<>(makeMap("OK", "You can place ships"), HttpStatus.CREATED);

        } else {
            return new ResponseEntity<>(makeMap("error", "Player already has ships"), HttpStatus.FORBIDDEN);

        }

    }

    @RequestMapping(path = "/game/{id}/players", method = RequestMethod.POST)
    private ResponseEntity <Map<String, Object>> joinCreatedGame(Authentication authentication, @PathVariable long id) {


        if (isGuest(authentication)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!gameRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(makeMap("error", "No such game"), HttpStatus.FORBIDDEN);
        }

        Game game = gameRepository.findById(id).get();
        if (listGamePlayers(game).toString().contains(authentication.getName())) {
            return new ResponseEntity<>(makeMap("ERROR", "User already in game"), HttpStatus.FORBIDDEN);

        }
        if (game.getGameplayers().stream().map(gamePlayer1 -> gamePlayer1.getPlayer()).count() >= 2) {
            return new ResponseEntity<>(makeMap("error", "Game is full"), HttpStatus.FORBIDDEN);
        }

        Player player = playerRepository.findByUserName(authentication.getName());
        Date date = new Date();
        GamePlayer newGamePlayer = gamePlayerRepository.save(new GamePlayer(game, player, date));
        return new ResponseEntity<>(makeMap("gpid", newGamePlayer.getId()), HttpStatus.CREATED);

    }

    @Autowired
    SalvoRepository salvoRepository;

    @RequestMapping(path = "/games/players/{id}/salvoes", method = RequestMethod.POST)
    private ResponseEntity<Object> createSalvos(Authentication authentication, @PathVariable long id, @RequestBody Salvo salvo) {
        if (isGuest(authentication)) {
            return new ResponseEntity<>(makeMap("error", "No player logged"), HttpStatus.UNAUTHORIZED);
        }
        if (!gamePlayerRepository.findById(id).isPresent()) {
            return new ResponseEntity<>(makeMap("error", "There is no player with that id"), HttpStatus.UNAUTHORIZED);

        }
        GamePlayer gamePlayer = gamePlayerRepository.findById(id).get();
        if (!authentication.getName().equals(gamePlayer.getPlayer().getUserName())) {
            return new ResponseEntity<>(makeMap("error", "This is not your game"), HttpStatus.UNAUTHORIZED);

        }
        int turn = gamePlayer.getSalvos().size() + 1;
        if (salvo.getTurn() > turn || gamePlayer.getSalvos().size() > gamePlayer.getOpponent().get().getSalvos().size()) {
            return new ResponseEntity<>(makeMap("error", "It is not your turn"), HttpStatus.FORBIDDEN);

        }
        if (salvo.getSalvoLocations().size() > 5) {
            return new ResponseEntity<>(makeMap("error", "You've already placed 5 salvos."), HttpStatus.FORBIDDEN);

        } else {

            salvoRepository.save(new Salvo(turn, gamePlayer, salvo.getSalvoLocations()));
            return new ResponseEntity<>(makeMap("OK", "Salvos fired"), HttpStatus.CREATED);
        }


    }

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @RequestMapping("/game_view/{id}")
    private ResponseEntity<Map<String, Object>> findGamePlayer(Authentication authentication, @PathVariable long id) {
        GamePlayer gamePlayer = gamePlayerRepository.findById(id).get();
        if (authentication.getName() == gamePlayer.getPlayer().getUserName()) {
            return ResponseEntity.ok(makeGameViewDTO(gamePlayer));
        } else {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private  ScoreRepository scoreRepository;

    @RequestMapping(value = "/players", method = RequestMethod.POST)
    private ResponseEntity<Object> register(
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

    private Map<String, Object> makeGameViewDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        Map<String, Object> hits = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getGame().getId());
        dto.put("created", gamePlayer.getGame().getCreationDate().toInstant().toEpochMilli());
        dto.put("gameState", stateofTheGame(gamePlayer));
        dto.put("gamePlayers", listGamePlayers(gamePlayer.getGame()));
        dto.put("ships", getAllShips(gamePlayer));
        dto.put("salvoes", listAllSalvos(gamePlayer.getGame().getGameplayers().stream().flatMap(gamePlayer1 -> gamePlayer1.getSalvos().stream()).collect(Collectors.toSet())));

        dto.put("hits", hits);
        if (gamePlayer.getOpponent().isPresent()) {
            hits.put("self", hitsDTO(gamePlayer.getOpponent().get()));
        } else {
            hits.put("self", new ArrayList<>());
        }
        hits.put("opponent", hitsDTO(gamePlayer));

        return dto;

    }

    private String stateofTheGame(GamePlayer gamePlayer) {
        String stateofgame = "";

        if (gamePlayer.getShips().isEmpty()) {
            stateofgame = "PLACESHIPS";
            return  stateofgame;

        }
        if (!gamePlayer.getOpponent().isPresent()) {
            stateofgame = "WAITINGFOROPP";
            return  stateofgame;

        }

        if (gamePlayer.getOpponent().get().getShips().isEmpty()) {
            stateofgame = "WAIT";
            return  stateofgame;

        }
        int mySalvos = gamePlayer.getSalvos().size();
        int opponentSalvos = gamePlayer.getOpponent().get().getSalvos().size();
        int hitsOpponnent = hitsInter(gamePlayer.getOpponent().get()).size();
        int hitsGamePlayer = hitsInter(gamePlayer).size();
        if (mySalvos <= opponentSalvos
                && (hitsOpponnent < 17 && hitsGamePlayer< 17)) {
            stateofgame = "PLAY";
            return  stateofgame;
        }
        if (mySalvos > opponentSalvos) {
            stateofgame = "WAIT";
            return  stateofgame;

        }
        if ( hitsGamePlayer >= 17 && hitsOpponnent < 17 && (mySalvos == opponentSalvos)) {
          if (gamePlayer.getGame().getScores().size() < 2){
            scoreRepository.save( new Score(gamePlayer.getGame(), gamePlayer.getPlayer(), 1.0, new Date()));}
            stateofgame = "WON";
            return  stateofgame;

        }
        if (hitsOpponnent >= 17 && hitsGamePlayer < 17 && (mySalvos == opponentSalvos)){
            if (gamePlayer.getGame().getScores().size() < 2){
                scoreRepository.save( new Score(gamePlayer.getGame(), gamePlayer.getPlayer(), 0, new Date()));}
            stateofgame = "LOST";
            return  stateofgame;

        }
        if (hitsOpponnent == 17 && hitsGamePlayer == 17 && (mySalvos == opponentSalvos)){
            if (gamePlayer.getGame().getScores().size() < 2){
                scoreRepository.save( new Score(gamePlayer.getGame(), gamePlayer.getPlayer(), 0.5, new Date()));}
            stateofgame = "TIE";
            return  stateofgame;

        }

        return stateofgame;
    }

    private List<String> hitsInter(GamePlayer gamePlayer) {
        List<String> ships = gamePlayer.getOpponent().get().getShips()
                .stream()
                .flatMap(ship -> ship.getLocations().stream())
                .collect(Collectors.toList());


        List<String> hits = gamePlayer
                .getSalvos()
                .stream()
                .flatMap(salvo -> salvo.getSalvoLocations().stream())
                .collect(Collectors.toList());
        ships.retainAll(hits);
        return ships;
    }


    private List<Map<String, Object>> hitsDTO(GamePlayer gamePlayer) {
        int accumCarrier = 0, accumBattleship = 0, accumSubmarine = 0, accumDestroyer = 0, accumPatrol = 0;
        List<Map<String, Object>> shipHitsDTO = new ArrayList<>();
        for (Salvo salvo : gamePlayer.getSalvos()) {
            int countCarrier = 0, countBattleship = 0, countSubmarine = 0, countDestroyer = 0,
                    countPatrol = 0;
            for (Ship ship : gamePlayer.getOpponent().get().getShips()) {
                Collection<String> similar = new HashSet<String>(salvo.getSalvoLocations());
                similar.retainAll(ship.getLocations());
                int equals = similar.size();
                if (equals != 0) {
                    switch (ship.getType()) {
                        case "carrier":
                            countCarrier += equals;
                            accumCarrier += equals;
                            break;
                        case "battleship":
                            countBattleship += equals;
                            accumBattleship += equals;
                            break;
                        case "submarine":
                            countSubmarine += equals;
                            accumSubmarine += equals;
                            break;
                        case "destroyer":
                            countDestroyer += equals;
                            accumDestroyer += equals;
                            break;
                        case "patrolboat":
                            countPatrol += equals;
                            accumPatrol += equals;
                            break;
                    }
                }
            }
            Map<String, Object> dto = new LinkedHashMap<>();
            Map<String, Object> damages = new LinkedHashMap<>();
            dto.put("turn", salvo.getTurn());
            dto.put("hitLocations", shipGotHit(salvo));
            dto.put("damages", damages);
            damages.put("carrierHits", countCarrier);
            damages.put("battleshipHits", countBattleship);
            damages.put("submarineHits", countSubmarine);
            damages.put("destroyerHits", countDestroyer);
            damages.put("patrolboatHits", countPatrol);
            damages.put("carrier", accumCarrier);
            damages.put("battleship", accumBattleship);
            damages.put("submarine", accumSubmarine);
            damages.put("destroyer", accumDestroyer);
            damages.put("patrolboat", accumPatrol);
            dto.put("missed", 5 - shipGotHit(salvo).stream().count());
            shipHitsDTO.add(dto);
        }
        return shipHitsDTO;
    }


    private List<String> shipGotHit(Salvo salvo) {
        if (salvo.getGamePlayer().getOpponent().isPresent()) {
            List<String> ships = salvo.getGamePlayer().getOpponent().get().getShips()
                    .stream()
                    .flatMap(ship -> ship.getLocations().stream())
                    .collect(Collectors.toList());
            ships.retainAll(salvo.getSalvoLocations());
            return ships;
        } else {
            return new ArrayList<>();
        }

    }


    private Map<String, Object> gameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();

        dto.put("id", game.getId());
        dto.put("created", game.getCreationDate().toInstant().toEpochMilli());
        dto.put("gamePlayers", listGamePlayers(game));
        dto.put("scores", listScores(game));
        return dto;
    }


    private List<Map<String, Object>> getAllShips(GamePlayer gamePlayer) {
        return gamePlayer.getShips().stream().map(ship -> ship.makeShipDTO()).collect(Collectors.toList());
    }


    private List<Map<String, Object>> listAllSalvos(Set<Salvo> salvos) {
        return salvos.stream().map(salvo -> salvo.makeSalvoDTO()).collect(Collectors.toList());
    }

    private List<Map<String, Object>> listGamePlayers(Game game) {
        return game.getGameplayers().stream().map(gamePlayer -> gamePlayer.GamePlayersDTO()).collect(Collectors.toList());
    }

    private List<Map<String, Object>> listScores(Game game) {
        return game.getScores().stream().map(score -> score.makeScoreDTO()).collect(Collectors.toList());
    }


    private Map<String, Object> makePlayerScoreDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<>();
        Map<String, Object> score = new LinkedHashMap<>();

        dto.put("id", player.getId());
        dto.put("email", player.getUserName());
        dto.put("score", score);
        score.put("total", player.getTotalScore());
        score.put("won", player.getWinScore());
        score.put("lost", player.getLostScore());
        score.put("tied", player.getTiedScore());
        return dto;
    }


    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }


}
