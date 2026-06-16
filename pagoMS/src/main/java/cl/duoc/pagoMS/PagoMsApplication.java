package cl.duoc.pagoMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PagoMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagoMsApplication.class, args);
	}

}
