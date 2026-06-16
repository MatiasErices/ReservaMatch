package cl.duoc.canchaMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CanchaMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CanchaMsApplication.class, args);
	}

}
