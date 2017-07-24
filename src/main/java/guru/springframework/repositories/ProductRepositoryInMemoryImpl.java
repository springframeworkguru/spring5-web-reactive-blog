
package guru.springframework.repositories;

import guru.springframework.domain.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProductRepositoryInMemoryImpl implements ProductRepository {

    private final Map<Integer, Product> productMap = new ConcurrentHashMap<>();

    public ProductRepositoryInMemoryImpl() {
        this.productMap.put(1, new Product(313,
                "Spring Guru Shirt",
                "Spring Framework Guru White collared Shirt",
                "https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_shirt-rf412049699c14ba5b68bb1c09182bfa2_8nax2_512.jpg",
                new BigDecimal("18.95")));

        this.productMap.put(2, new Product(512,
                "Spring Guru Mug",
                "Spring Framework Guru Green Cofee Mug",
                "https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_coffee_mug-r11e7694903c348e1a667dfd2f1474d95_x7j54_8byvr_512.jpg",
                new BigDecimal("11.95")));
    }

    @Override
    public Mono<Product> getProduct(int id) {
        return Mono.justOrEmpty(this.productMap.get(id));
    }

    @Override
    public Flux<Product> getAllProducts() {
        return Flux.fromIterable(this.productMap.values());
    }

    @Override
    public Mono<Void> saveProduct(Mono<Product> productMono) {

        Mono<Product> pMono = productMono.doOnNext(product -> {
            int id = productMap.size() + 1;
            productMap.put(id, product);
            System.out.format("Saved %s with id %d%n", product, id);
        });
        return pMono.thenEmpty(Mono.empty());
    }
}
