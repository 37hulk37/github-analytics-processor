package com.hulk.processor;

import com.hulk.processor.ml.MlModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ApplicationTests {

    @MockitoBean
    private MlModel mlModel;

	@Test
	void contextLoads() {
	}

}
