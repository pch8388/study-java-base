package base.effective.item2;

import static base.effective.item2.NYPizza.Size.*;
import static base.effective.item2.Pizza.Topping.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Item2Test {

	@Test
	@DisplayName("점층적 생성자 패턴")
	void telescoping_constructor_pattern() {
		NutritionFacts nutritionFacts = new NutritionFacts(200, 10, 8, 100, 30, 20);

		assertNotNull(nutritionFacts);
	}

	@Test
	@DisplayName("자바빈즈 패턴")
	void java_beans_pattern() {
		NutritionFactsJavaBean nutritionFacts = new NutritionFactsJavaBean();
		nutritionFacts.setServingSize(200);
		nutritionFacts.setServings(10);
		nutritionFacts.setCalories(8);
		nutritionFacts.setFat(100);
		nutritionFacts.setSodium(30);
		nutritionFacts.setCarbohydrate(20);

		assertNotNull(nutritionFacts);
	}

	@Test
	@DisplayName("빌더 패턴")
	void builder_pattern() {
		NutritionFactsBuilder nutritionFacts = new NutritionFactsBuilder.Builder(200, 10)
			.calories(8).fat(100).sodium(30).carbohydrate(20).build();

		assertNotNull(nutritionFacts);
		assertEquals(200, nutritionFacts.getServingSize());
		assertEquals(10, nutritionFacts.getServings());
		assertEquals(8, nutritionFacts.getCalories());
		assertEquals(100, nutritionFacts.getFat());
		assertEquals(30, nutritionFacts.getSodium());
		assertEquals(20, nutritionFacts.getCarbohydrate());
	}

	@Test
	@DisplayName("계층 구조")
	void hierarchy_builder() {
		NYPizza nyPizza = new NYPizza.Builder(MEDIUM).addTopping(MUSHROOM).addTopping(HAM).build();
		CalzonePizza calzonePizza = new CalzonePizza.Builder().addTopping(ONION).sauceInside().build();

		assertThat(nyPizza.getToppings()).contains(MUSHROOM, HAM);
		assertThat(calzonePizza.getToppings()).contains(ONION);
	}
}