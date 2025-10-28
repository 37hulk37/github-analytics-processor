package com.hulk.processor.ml;

import com.hulk.processor.model.MlMetrics;
import com.hulk.processor.repository.Repository;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Tensor;
import org.tensorflow.Tensors;

public class GithubModel implements MlModel, AutoCloseable {

    private final SavedModelBundle modelBundle;
    private final RepositoryFeatureConvertor convertor;

    public GithubModel(String modelName, RepositoryFeatureConvertor convertor) {
        this.modelBundle = SavedModelBundle.load(modelName, "serve");
        this.convertor = convertor;
    }

    @Override
    public MlMetrics predict(Repository data) {
        var features = convertor.convert(data);

        try (var inputTensor = Tensors.create(new double[][]{features})) {
            var tensor = modelBundle.session()
                .runner()
                .feed("serving_default_input", inputTensor)
                .fetch("StatefulPartitionedCall")
                .run()
                .getFirst()
                .expect(Double.class);

            var array = toResult(tensor);

            return new MlMetrics();
        }
    }

    private double[] toResult(Tensor<Double> tensor) {
        var arr = new double[(int) tensor.shape()[1]];
        tensor.copyTo(new double[1][arr.length]);

        return arr;
    }

    @Override
    public void close() {
        modelBundle.close();
    }
}
