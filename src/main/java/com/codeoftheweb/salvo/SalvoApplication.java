package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SalvoApplication.class, args);
	}
	@Bean
	public CommandLineRunner iniData (PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository){
		return	(args) -> {
			Player p1 = new Player("j.bauer@ctu.gov");
			Player p2 = new Player("c.obrian@ctu.gov");
			Player p3 = new Player("kim_bauer@gmail.com");
			Player p4 = new Player("t.almeida@ctu.gov");


			playerRepository.saveAll(Arrays.asList(p1,p2,p3,p4));

			Date date = new Date();
			Game game1 = new Game (date);
			Game game2 = new Game (Date.from(date.toInstant().plusSeconds(3600)));
			Game game3 = new Game (Date.from(date.toInstant().plusSeconds(7200)));
			Game game4 = new Game (Date.from(date.toInstant().plusSeconds(10800)));
			Game game5 = new Game (Date.from(date.toInstant().plusSeconds(14400)));
			Game game6 = new Game (Date.from(date.toInstant().plusSeconds(18000)));
			Game game7 = new Game (Date.from(date.toInstant().plusSeconds(21600)));
			Game game8 = new Game (Date.from(date.toInstant().plusSeconds(25200)));
			gameRepository.saveAll(Arrays.asList(game1, game2, game3, game4, game5, game6,game7, game8));



			GamePlayer gp1 = new GamePlayer(game1, p1, date);
			GamePlayer gp2 = new GamePlayer(game1, p2, date);
			GamePlayer gp3 = new GamePlayer(game2, p1, date);
			GamePlayer gp4 = new GamePlayer(game2, p2, date);
			GamePlayer gp5 = new GamePlayer(game3, p2, date);
			GamePlayer gp6 = new GamePlayer(game3, p4, date);
			GamePlayer gp7 = new GamePlayer(game4, p2, date);
			GamePlayer gp8 = new GamePlayer(game4, p1, date);
			GamePlayer gp9 = new GamePlayer(game5, p4, date);
			GamePlayer gp10 = new GamePlayer(game5, p1, date);
			GamePlayer gp11 = new GamePlayer(game6, p3, date);
			GamePlayer gp12 = new GamePlayer(game7, p4, date);
			GamePlayer gp13 = new GamePlayer(game8, p3, date);
			GamePlayer gp14 = new GamePlayer(game8, p4, date);


			gamePlayerRepository.saveAll(Arrays.asList(gp1, gp2, gp3, gp4, gp5, gp6, gp7, gp8, gp9, gp10, gp11, gp12,gp13,gp14));


			Ship ship1 = new Ship("Destroyer",new HashSet<>(Arrays.asList("H2", "H3", "H4")), gp1 );
			Ship ship2 = new Ship("Submarine", new HashSet<>(Arrays.asList("E1", "F1", "G1")), gp1);
			Ship ship3 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("B4", "B5")), gp1);
			Ship ship4 = new Ship("Destroyer",  new HashSet<>(Arrays.asList("B5", "C5", "D5")), gp2);
			Ship ship5 = new Ship("Patrol Boat",  new HashSet<>(Arrays.asList("F1", "F2")), gp2);
			Ship ship6 = new Ship("Destroyer", new HashSet<>(Arrays.asList("B5", "C5", "D5")), gp3);
			Ship ship7 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("C6", "C7")), gp3);
			Ship ship8 = new Ship("Submarine", new HashSet<>(Arrays.asList("A2", "A3", "A4")), gp4);
			Ship ship9 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("G6", "H6")), gp4);
			Ship ship10 = new Ship("Destroyer",  new HashSet<>(Arrays.asList("B5", "C5", "D5")), gp5);
			Ship ship11 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("C6", "C7")), gp5);
			Ship ship12 = new Ship("Submarine", new HashSet<>(Arrays.asList("A2", "A3", "A4")), gp6);
			Ship ship13 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("G6", "H6")), gp6);
			Ship ship14 = new Ship("Destroyer", new HashSet<>(Arrays.asList("B5", "C5", "D5")), gp7);
			Ship ship15 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("C6", "C7")), gp7);
			Ship ship16 = new Ship("Submarine",  new HashSet<>(Arrays.asList("A2", "A3", "A4")), gp8);
			Ship ship17 = new Ship("Patrol Boat",  new HashSet<>(Arrays.asList("G6", "H6")), gp8);
			Ship ship18 = new Ship("Destroyer",  new HashSet<>(Arrays.asList("B5", "C5", "D5")), gp9);
			Ship ship19 = new Ship("Patrol Boat",  new HashSet<>(Arrays.asList("C6", "C7")), gp9);
			Ship ship20 = new Ship("Submarine",  new HashSet<>(Arrays.asList("A2", "A3", "A4")), gp10);
			Ship ship21 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("G6", "H6")), gp10);
			Ship ship22 = new Ship("Destroyer",new HashSet<>(Arrays.asList("B5", "C5", "D5")), gp11);
			Ship ship23 = new Ship("Patrol Boat",new HashSet<>(Arrays.asList("C6", "C7")), gp11);
			Ship ship24 = new Ship("Destroyer",  new HashSet<>(Arrays.asList("B5", "C5", "D5")), gp13);
			Ship ship25 = new Ship("Patrol Boat",  new HashSet<>(Arrays.asList("C6", "C7")), gp13);
			Ship ship26 = new Ship("Submarine",  new HashSet<>(Arrays.asList("A2", "A3", "A4")), gp14);
			Ship ship27 = new Ship("Patrol Boat",  new HashSet<>(Arrays.asList("G6", "H6")), gp14);

			shipRepository.saveAll(Arrays.asList(ship1,ship2, ship3, ship4, ship5, ship6, ship7, ship8, ship9, ship10, ship11, ship12, ship13, ship14, ship15, ship16, ship17, ship18, ship19, ship20, ship21, ship22, ship23, ship24, ship25, ship26, ship27));







		};
	}

}
