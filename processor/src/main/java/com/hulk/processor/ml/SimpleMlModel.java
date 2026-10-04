package com.hulk.processor.ml;

import com.hulk.processor.repository.Repository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class SimpleMlModel implements MlModel {

    @Override
    public MlMetrics predict(Repository data) {
        return new MlMetrics(new double[0]);
    }

    @Override
    public void close() {
    }
}
