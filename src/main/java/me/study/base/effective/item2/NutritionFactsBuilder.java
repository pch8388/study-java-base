package me.study.base.effective.item2;

public class NutritionFactsBuilder {
	private final int servingSize;
	private final int servings;
	private final int calories;
	private final int fat;
	private final int sodium;
	private final int carbohydrate;

	private NutritionFactsBuilder(Builder builder) {
		this.servingSize = builder.servingSize;
		this.servings = builder.servings;
		this.calories = builder.calories;
		this.fat = builder.fat;
		this.sodium = builder.sodium;
		this.carbohydrate = builder.carbohydrate;
	}

	public static class Builder {
		private final int servingSize;
		private final int servings;

		private int calories = 0;
		private int fat = 0;
		private int sodium = 0;
		private int carbohydrate = 0;

		public Builder(int servingSize, int servings) {
			this.servingSize = servingSize;
			this.servings = servings;
		}

		public Builder calories(int val) {
			this.calories = val;
			return this;
		}

		public Builder fat(int val) {
			this.fat = val;
			return this;
		}

		public Builder sodium(int val) {
			this.sodium = val;
			return this;
		}

		public Builder carbohydrate(int val) {
			this.carbohydrate = val;
			return this;
		}

		public NutritionFactsBuilder build() {
			return new NutritionFactsBuilder(this);
		}
	}

	public int getServingSize() {
		return servingSize;
	}

	public int getServings() {
		return servings;
	}

	public int getCalories() {
		return calories;
	}

	public int getFat() {
		return fat;
	}

	public int getSodium() {
		return sodium;
	}

	public int getCarbohydrate() {
		return carbohydrate;
	}
}
