package com.david.movestore;

import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;
import com.david.movestore.auth.AuthenticationService;
import com.david.movestore.auth.RegisterRequest;
import com.david.movestore.order.OrderService;
import com.david.movestore.product.ProductService;
import com.david.movestore.user.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
	public ObjectMapper objectMapper() {
		return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service,
			ProductService productService,
			OrderService orderService) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname("Admin")
					.lastname("Admin")
					.email("admin@mail.com")
					.password("password")
					.role(Role.ADMIN)
					.build();
			System.out.println("Admin token: " + service.register(admin).getAccessToken());

			// var manager = RegisterRequest.builder()
			// 		.firstname("Manager")
			// 		.lastname("Manager")
			// 		.email("manager@mail.com")
			// 		.password("password")
			// 		.role(Role.MANAGER)
			// 		.build();
			// System.out.println("Manager token: " + service.register(manager).getAccessToken());

			var user = RegisterRequest.builder()
					.firstname("User")
					.lastname("User")
					.email("user@mail.com")
					.password("password")
					.role(Role.USER)
					.build();
			System.out.println("User token: " + service.register(user).getAccessToken());
		};
	}
}
