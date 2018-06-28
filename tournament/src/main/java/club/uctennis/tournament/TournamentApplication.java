package club.uctennis.tournament;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import graphql.Scalars;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

@SpringBootApplication
@RestController
public class TournamentApplication {

    @RequestMapping("/")
    String hello() {
        return "Hello Spring Boot!";
    }

	public static void main(String[] args) {
		SpringApplication.run(TournamentApplication.class, args);
	}
}
