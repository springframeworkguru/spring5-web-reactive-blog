package guru.springframework.repositories;

import guru.springframework.domain.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {

	Mono<Product> getProduct(int id);
	Flux<Product> getAllProducts();
	Mono<Void> saveProduct(Mono<Product> product);

}
