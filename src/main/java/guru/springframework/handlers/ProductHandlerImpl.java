package guru.springframework.handlers;

import guru.springframework.repositories.ProductRepository;
import guru.springframework.domain.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class ProductHandlerImpl implements ProductHandler {

    private final ProductRepository repository;

    public ProductHandlerImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<ServerResponse> getProductFromRepository(ServerRequest request) {
        int personId = Integer.valueOf(request.pathVariable("id"));
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<Product> personMono = this.repository.getProduct(personId);
        return personMono
                .flatMap(person -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(person)))
                .switchIfEmpty(notFound);
    }

    @Override
    public Mono<ServerResponse> saveProductToRepository(ServerRequest request) {
        Mono<Product> product = request.bodyToMono(Product.class);
        return ServerResponse.ok().build(this.repository.saveProduct(product));
    }

    @Override
    public Mono<ServerResponse> getAllProductsFromRepository(ServerRequest request) {
        Flux<Product> products = this.repository.getAllProducts();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(products, Product.class);
    }

}
