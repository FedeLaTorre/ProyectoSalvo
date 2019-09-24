package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SalvoApplication.class, args);
	}
	@Bean
	public CommandLineRunner iniData (PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository){
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
		};
	}

}
