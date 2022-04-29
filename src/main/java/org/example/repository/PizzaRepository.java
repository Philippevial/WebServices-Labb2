package org.example.repository;

import io.micronaut.core.annotation.NonNull;
import org.example.pojo.Pizza;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface PizzaRepository {

    @NonNull
    Publisher<Pizza> list();

    Mono<Boolean> save(@NonNull @NotNull @Valid Pizza pizza);
}
