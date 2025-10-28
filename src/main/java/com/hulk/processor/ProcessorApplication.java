package com.hulk.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProcessorApplication {

    /*
    2 pipelines:
    1. - convert data from Loader
    2. - run Yarik script to calculate metrics by ml
     */

	public static void main(String[] args) {
		SpringApplication.run(ProcessorApplication.class, args);
	}

}
