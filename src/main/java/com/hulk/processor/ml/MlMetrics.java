package com.hulk.processor.ml;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MlMetrics {

    private final Map<String, Integer> languagePopularityForecast;
    private final Map<String, String> repositoriesByPurpose;

    public MlMetrics(double[] results) {
        this.languagePopularityForecast = new HashMap<>();
        this.repositoriesByPurpose = new HashMap<>();
    }
}
