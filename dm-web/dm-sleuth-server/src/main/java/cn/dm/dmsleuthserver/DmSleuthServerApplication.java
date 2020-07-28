package cn.dm.dmsleuthserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class DmSleuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DmSleuthServerApplication.class, args);
	}
}
