package com.hulk.processor.ml;

import com.hulk.processor.repository.Repository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class RepositoryFeatureConvertor implements Converter<Repository, double[]> {

    //todo: convert features
    @Override
    public double[] convert(@NonNull Repository source) {
        var fields = source.getClass().getDeclaredFields();

        return Stream.of(fields)
            .map(field -> {
                return 0.0;
            })
            .mapToDouble(d -> d)
            .toArray();
    }
}
