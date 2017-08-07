package guru.springframework.repositories;

import guru.springframework.domain.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ReactiveProductRepository extends ReactiveCrudRepository<Product, String> {
    Flux<Product> findByName(String name);

    Flux<Product> findByName(Mono<String> name);

    Mono<Product> findByNameAndImageUrl(Mono<String> name, String imageUrl);

    @Query("{ 'name': ?0, 'imageUrl': ?1}")
    Mono<Product> findByNameAndImageUrl(String name, String imageUrl);
}
