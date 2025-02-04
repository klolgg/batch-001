package site.klol.batch001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Batch001Application {

	public static void main(String[] args) {
		SpringApplication.run(Batch001Application.class, args);
	}

}
