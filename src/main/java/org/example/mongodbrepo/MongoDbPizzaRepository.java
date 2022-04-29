package org.example.mongodbrepo;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import org.example.config.MongoDbConfiguration;
import org.example.pojo.Pizza;
import org.example.repository.PizzaRepository;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Singleton
public class MongoDbPizzaRepository implements PizzaRepository {

    private final MongoDbConfiguration mongoConf;
    private final MongoClient mongoClient;

    public MongoDbPizzaRepository(MongoDbConfiguration mongoConf, MongoClient mongoClient) {
        this.mongoConf = mongoConf;
        this.mongoClient = mongoClient;
    }


    @Override
    @NonNull
    public Publisher<Pizza> list() {
        return getCollection().find();
    }

    @Override
    public Mono<Boolean> save(@NonNull @NotNull @Valid Pizza pizza) {
        return Mono.from(getCollection().insertOne(pizza))
                .map(insertOneResult -> true)
                .onErrorReturn(false);
    }

    @NonNull
    private MongoCollection<Pizza> getCollection() {
        return mongoClient.getDatabase(mongoConf.getName())
                .getCollection(mongoConf.getCollection(), Pizza.class);
    }
}
