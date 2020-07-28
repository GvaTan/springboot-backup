package top.anets;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootRun {
	
	public static void main(String[] args) {
		System.err.println("运行了");
		SpringApplication.run(SpringBootRun.class, args);
	}
}
