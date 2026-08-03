package org.example.statify;

import org.example.statify.client.FootballApiClient;
import org.example.statify.dto.ApiResponseDto;
import org.example.statify.dto.LeagueResponseDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StatifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatifyApplication.class, args);
    }

    @Bean
    CommandLineRunner test(FootballApiClient client) {

        return args -> {

            ApiResponseDto<LeagueResponseDto> response = client.getLeagues();

            System.out.println("Broj liga: " + response.getResults());

            response.getResponse()
                    .stream()
                    .limit(2)
                    .forEach(item -> {

                        System.out.println(
                                "League: " + item.getLeague().getName()
                        );

                        System.out.println(
                                "Country: " + item.getCountry().getName()
                        );


                        item.getSeasons()
                                .stream()
                                .limit(3)
                                .forEach(season -> {

                                    System.out.println(
                                            "Season: " + season.getYear()
                                    );

                                    System.out.println(
                                            "Start: " + season.getStart()
                                    );

                                    System.out.println(
                                            "End: " + season.getEnd()
                                    );

                                });

                        System.out.println("----------------");
                    });
        };
    }

}
