package guru.springframework.handlers;

import guru.springframework.domain.Product;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public interface ProductHandler {
    public Mono<ServerResponse> getProductFromRepository(ServerRequest request);
    public Mono<ServerResponse> saveProductToRepository(ServerRequest request);
    public Mono<ServerResponse> getAllProductsFromRepository(ServerRequest request);
}
