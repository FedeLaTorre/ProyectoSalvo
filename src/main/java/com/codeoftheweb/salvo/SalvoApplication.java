package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.models.*;
import com.codeoftheweb.salvo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@SpringBootApplication
public class SalvoApplication {

    public static void main(String[] args) {

        SpringApplication.run(SalvoApplication.class, args);
    }

    @Bean
    public CommandLineRunner iniData(PlayerRepository playerRepository, GameRepository gameRepository,
                                     GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository,
                                     SalvoRepository salvoRepository,
                                     ScoreRepository scoreRepository) {
        return (args) -> {
            Player p1 = new Player("j.bauer@ctu.gov", passwordEncoder().encode("24"));
            Player p2 = new Player("c.obrian@ctu.gov", passwordEncoder().encode("42"));
            Player p3 = new Player("kim_bauer@gmail.com", passwordEncoder().encode("kb"));
            Player p4 = new Player("t.almeida@ctu.gov", passwordEncoder().encode("mole"));


            playerRepository.saveAll(Arrays.asList(p1, p2, p3, p4));

            Date date = new Date();
            Game game1 = new Game(date);
            Game game2 = new Game(Date.from(date.toInstant().plusSeconds(3600)));
            Game game3 = new Game(Date.from(date.toInstant().plusSeconds(7200)));
            Game game4 = new Game(Date.from(date.toInstant().plusSeconds(10800)));
            Game game5 = new Game(Date.from(date.toInstant().plusSeconds(14400)));
            Game game6 = new Game(Date.from(date.toInstant().plusSeconds(18000)));
            Game game7 = new Game(Date.from(date.toInstant().plusSeconds(21600)));
            Game game8 = new Game(Date.from(date.toInstant().plusSeconds(25200)));
            gameRepository.saveAll(Arrays.asList(game1, game2, game3, game4, game5, game6, game7, game8));


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


            gamePlayerRepository.saveAll(Arrays.asList(gp1, gp2, gp3, gp4, gp5, gp6, gp7, gp8, gp9, gp10, gp11, gp12, gp13, gp14));


            Ship ship1 = new Ship("Destroyer", new HashSet<>(Arrays.asList("H2", "H3", "H4")), gp1);
            Ship ship2 = new Ship("Submarine", new HashSet<>(Arrays.asList("E1", "F1", "G1")), gp1);
            Ship ship3 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("B4", "B5")), gp1);
            Ship ship4 = new Ship("Destroyer", new HashSet<>(Arrays.asList("B5", "C5", "D5")), gp2);
            Ship ship5 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("F1", "F2")), gp2);
            Ship ship6 = new Ship("Destroyer", new HashSet<>(Arrays.asList("B5", "C5", "D5")), gp3);
            Ship ship7 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("C6", "C7")), gp3);
            Ship ship8 = new Ship("Submarine", new HashSet<>(Arrays.asList("A2", "A3", "A4")), gp4);
            Ship ship9 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("G6", "H6")), gp4);
            Ship ship10 = new Ship("Destroyer", new HashSet<>(Arrays.asList("B5", "C5", "D5")), gp5);
            Ship ship11 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("C6", "C7")), gp5);
            Ship ship12 = new Ship("Submarine", new HashSet<>(Arrays.asList("A2", "A3", "A4")), gp6);
            Ship ship13 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("G6", "H6")), gp6);
            Ship ship14 = new Ship("Destroyer", new HashSet<>(Arrays.asList("B5", "C5", "D5")), gp7);
            Ship ship15 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("C6", "C7")), gp7);
            Ship ship16 = new Ship("Submarine", new HashSet<>(Arrays.asList("A2", "A3", "A4")), gp8);
            Ship ship17 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("G6", "H6")), gp8);
            Ship ship18 = new Ship("Destroyer", new HashSet<>(Arrays.asList("B5", "C5", "D5")), gp9);
            Ship ship19 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("C6", "C7")), gp9);
            Ship ship20 = new Ship("Submarine", new HashSet<>(Arrays.asList("A2", "A3", "A4")), gp10);
            Ship ship21 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("G6", "H6")), gp10);
            Ship ship22 = new Ship("Destroyer", new HashSet<>(Arrays.asList("B5", "C5", "D5")), gp11);
            Ship ship23 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("C6", "C7")), gp11);
            Ship ship24 = new Ship("Destroyer", new HashSet<>(Arrays.asList("B5", "C5", "D5")), gp13);
            Ship ship25 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("C6", "C7")), gp13);
            Ship ship26 = new Ship("Submarine", new HashSet<>(Arrays.asList("A2", "A3", "A4")), gp14);
            Ship ship27 = new Ship("Patrol Boat", new HashSet<>(Arrays.asList("G6", "H6")), gp14);

            shipRepository.saveAll(Arrays.asList(ship1, ship2, ship3, ship4, ship5, ship6, ship7, ship8, ship9, ship10, ship11, ship12, ship13, ship14, ship15, ship16, ship17, ship18, ship19, ship20, ship21, ship22, ship23, ship24, ship25, ship26, ship27));


            Salvo sa1 = new Salvo(1, gp1, new HashSet<>(Arrays.asList("B5", "C5", "F1")));
            Salvo sa2 = new Salvo(1, gp2, new HashSet<>(Arrays.asList("B4", "B5", "B6")));
            Salvo sa3 = new Salvo(2, gp1, new HashSet<>(Arrays.asList("F2", "D5")));
            Salvo sa4 = new Salvo(2, gp2, new HashSet<>(Arrays.asList("E1", "H3", "A2")));
            Salvo sa5 = new Salvo(1, gp3, new HashSet<>(Arrays.asList("A2", "A4", "G6")));
            Salvo sa6 = new Salvo(1, gp4, new HashSet<>(Arrays.asList("B5", "D5", "C7")));
            Salvo sa7 = new Salvo(2, gp3, new HashSet<>(Arrays.asList("A3", "H6")));
            Salvo sa8 = new Salvo(2, gp4, new HashSet<>(Arrays.asList("C5", "C6")));
            Salvo sa9 = new Salvo(1, gp5, new HashSet<>(Arrays.asList("G6", "H6", "A4")));
            Salvo sa10 = new Salvo(1, gp6, new HashSet<>(Arrays.asList("H1", "H2", "H3")));
            Salvo sa11 = new Salvo(2, gp5, new HashSet<>(Arrays.asList("A2", "A3", "D8")));
            Salvo sa12 = new Salvo(2, gp6, new HashSet<>(Arrays.asList("E1", "F2", "G3")));
            Salvo sa13 = new Salvo(1, gp7, new HashSet<>(Arrays.asList("A3", "A4", "F7")));
            Salvo sa14 = new Salvo(1, gp8, new HashSet<>(Arrays.asList("B5", "C6", "H1")));
            Salvo sa15 = new Salvo(2, gp7, new HashSet<>(Arrays.asList("A2", "G6", "H6")));
            Salvo sa16 = new Salvo(2, gp8, new HashSet<>(Arrays.asList("C5", "C7", "D5")));
            Salvo sa17 = new Salvo(1, gp9, new HashSet<>(Arrays.asList("A1", "A2", "A3")));
            Salvo sa18 = new Salvo(1, gp10, new HashSet<>(Arrays.asList("B5", "B6", "C7")));
            Salvo sa19 = new Salvo(2, gp9, new HashSet<>(Arrays.asList("G6", "G7", "G8")));
            Salvo sa20 = new Salvo(2, gp10, new HashSet<>(Arrays.asList("C6", "D6", "E6")));
            Salvo sa21 = new Salvo(3, gp10, new HashSet<>(Arrays.asList("H1", "H8")));
            salvoRepository.saveAll(Arrays.asList(sa1, sa2, sa3, sa4, sa5, sa6, sa7, sa8, sa9, sa10,
                    sa11, sa12, sa13, sa14, sa15, sa16, sa17, sa18, sa19, sa20, sa21));


            Score sc1 = new Score(game1, p1, 1, Date.from(date.toInstant().plusSeconds(3600)));
            Score sc2 = new Score(game1, p2, 0, Date.from(date.toInstant().plusSeconds(3600)));
            Score sc3 = new Score(game2, p1, 0.5, Date.from(date.toInstant().plusSeconds(7200)));
            Score sc4 = new Score(game2, p2, 0.5, Date.from(date.toInstant().plusSeconds(7200)));
            Score sc5 = new Score(game3, p2, 1, Date.from(date.toInstant().plusSeconds(10800)));
            Score sc6 = new Score(game3, p4, 0, Date.from(date.toInstant().plusSeconds(10800)));
            Score sc7 = new Score(game4, p2, 0.5, Date.from(date.toInstant().plusSeconds(14400)));
            Score sc8 = new Score(game4, p1, 0.5, Date.from(date.toInstant().plusSeconds(14400)));
            scoreRepository.saveAll(Arrays.asList(sc1, sc2, sc3, sc4, sc5,
                    sc6, sc7, sc8));


        };

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inputName -> {
            Player player = playerRepository.findByUserName(inputName);
            if (player != null) {
                return new User(player.getUserName(), player.getPassword(),
                        AuthorityUtils.createAuthorityList("USER"));
            } else {
                throw new UsernameNotFoundException("Unknown user: " + inputName);
            }
        });
    }
}

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
				.antMatchers("/web/**").permitAll()
                .antMatchers("/api/game_view/**").hasAuthority("USER")
                .antMatchers("/api/games").permitAll();



        http.formLogin()
                .usernameParameter("name")
                .passwordParameter("pwd")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

		http.csrf().disable();

		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
    }
}




