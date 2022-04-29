package org.example.controller;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import org.example.pojo.Pizza;
import org.example.repository.PizzaRepository;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static io.micronaut.http.HttpStatus.CONFLICT;
import static io.micronaut.http.HttpStatus.CREATED;

@Controller("/pizzas")
public class PizzaController {

    private final PizzaRepository pizzaService;


    public PizzaController(PizzaRepository pizzaService) {
        this.pizzaService = pizzaService;
    }

    @Get
    Publisher<Pizza> list() {
        return pizzaService.list();
    }

    @Post
    Mono<HttpStatus> save(@NonNull @NotNull @Valid Pizza pizza) {
        return pizzaService.save(pizza)
                .map(added -> added ? CREATED : CONFLICT);
    }
}
