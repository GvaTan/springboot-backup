/**
 * 
 */
package top.anets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Administrator
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class MemberServer {
    public static void main(String[] args) {
		SpringApplication.run(MemberServer.class, args);
	}
}
