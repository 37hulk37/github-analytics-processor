package com.hulk.processor.ml;

import com.hulk.processor.repository.Repository;

public interface MlModel extends AutoCloseable {

    MlMetrics predict(Repository data);

}
