package pos.idm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("pos.idm.Repository")
@SpringBootApplication
public class SoapServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoapServiceApplication.class, args);
	}

}
