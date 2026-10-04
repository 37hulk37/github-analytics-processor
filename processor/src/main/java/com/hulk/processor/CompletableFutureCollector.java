package com.hulk.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CompletableFutureCollector<V, F extends CompletableFuture<V>> implements Collector<F, List<F>, CompletableFuture<List<V>>> {

    private CompletableFutureCollector() {
    }

    public static <V, F extends CompletableFuture<V>> CompletableFutureCollector<V, F> allOf() {
        return new CompletableFutureCollector<>();
    }

    @Override
    public Supplier<List<F>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<F>, F> accumulator() {
        return List::add;
    }

    @Override
    public BinaryOperator<List<F>> combiner() {
        return (l1, l2) -> {
            l1.addAll(l2);
            return l1;
        };
    }

    @Override
    public Function<List<F>, CompletableFuture<List<V>>> finisher() {
        return futures -> CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
            .thenApply(unused -> futures.stream()
                .map(CompletableFuture::join)
                .toList()
            );
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}
