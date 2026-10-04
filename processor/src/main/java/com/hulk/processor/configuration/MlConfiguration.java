package com.hulk.processor.configuration;

import com.hulk.processor.ml.GithubModel;
import com.hulk.processor.ml.MlModel;
import com.hulk.processor.ml.RepositoryFeatureConvertor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MlConfiguration {

    @Bean
    public MlModel mlModel(RepositoryFeatureConvertor featureConvertor) {
        return new GithubModel("model", featureConvertor);
    }

}
