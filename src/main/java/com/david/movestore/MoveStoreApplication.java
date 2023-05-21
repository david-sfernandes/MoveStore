package com.david.movestore;

import com.david.movestore.auth.AuthenticationService;
import com.david.movestore.auth.RegisterRequest;
import com.david.movestore.order.OrderService;
import com.david.movestore.product.ProductRequest;
import com.david.movestore.product.ProductService;
import com.david.movestore.user.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MoveStoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(MoveStoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
		AuthenticationService service,
		ProductService productService,
		OrderService orderService
	) {
		return args -> {
			var admin = RegisterRequest.builder()
				.firstname("Admin")
				.lastname("Admin")
				.email("admin@mail.com")
				.password("password")
				.role(Role.ADMIN)
				.build();
			System.out.println("Admin token: " + service.register(admin).getAccessToken());

			var manager = RegisterRequest.builder()
				.firstname("Manager")
				.lastname("Manager")
				.email("manager@mail.com")
				.password("password")
				.role(Role.MANAGER)
				.build();
			System.out.println("Manager token: " + service.register(manager).getAccessToken());

			var user = RegisterRequest.builder()
				.firstname("User")
				.lastname("User")
				.email("user@mail.com")
				.password("password")
				.role(Role.USER)
				.build();
			System.out.println("User token: " + service.register(user).getAccessToken());

			var product = ProductRequest.builder()
				.price(100.0)
				.quantity(1)
				.image("img.png")
				.name("mesa")
				.description("Description")
				.build();
			System.out.println("Product avaliable: " + productService.saveProduct(product).getId());

//			List<OrderProduct> products = new ArrayList<>();
//			products.add(OrderProduct.builder().productId(1).quantity(1).build());
//
//			var order = OrderRequest.builder()
//				.orderDate(new Date())
//				.products(products)
//				.userEmail("user@mail.com")
//				.build();
//			orderService.save(order);
		};
	}
}
