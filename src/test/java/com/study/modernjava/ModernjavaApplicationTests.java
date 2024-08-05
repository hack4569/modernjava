package com.study.modernjava;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import stream.Dish;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ModernjavaApplicationTests {

	@BeforeEach
	public void 호출할때마다(){
		List<Dish> menu = Arrays.asList(
				new Dish("pork", false, 800, Dish.Type.MEAT)
		)
	}

	@Test
	void test() {

	}

}
