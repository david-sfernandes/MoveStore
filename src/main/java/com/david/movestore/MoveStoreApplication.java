package com.david.movestore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;
import com.david.movestore.auth.AuthenticationRequest;
import com.david.movestore.auth.AuthenticationResponse;
import com.david.movestore.auth.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootApplication
public class MoveStoreApplication {
	public static void main(String[] args) {
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
				"cloud_name", "dj4pzc33p",
				"api_key", "459697167742439",
				"api_secret", "h1Vbikvt57dEJ2P5AzzSxq9r_lo",
				"secure", true));
		SingletonManager manager = new SingletonManager();
		manager.setCloudinary(cloudinary);
		manager.init();
		SpringApplication.run(MoveStoreApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000");
			}
		};
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service) {
		return args -> {
			var adminReq = AuthenticationRequest.builder().email("admin@mail.com").password("password").build();
			var userReq = AuthenticationRequest.builder().email("user@mail.com").password("password").build();
			AuthenticationResponse userTokens = service.authenticate(userReq);
			AuthenticationResponse adminTokens = service.authenticate(adminReq);

			System.out.println("\n=============");
			System.out.println("User token: " + userTokens.getAccessToken());
			System.out.println("Admin token: " + adminTokens.getAccessToken());
		};
	}
}
