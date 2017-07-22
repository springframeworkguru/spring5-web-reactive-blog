/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
