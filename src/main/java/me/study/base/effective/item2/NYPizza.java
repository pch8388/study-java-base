package me.study.base.effective.item2;

import java.util.Objects;

public class NYPizza extends Pizza {
	enum Size { SMALL, MEDIUM, LARGE }
	private final Size size;
	public static class Builder extends Pizza.Builder<Builder> {
		private final Size size;

		public Builder(Size size) {
			this.size = Objects.requireNonNull(size);
		}

		@Override
		NYPizza build() {
			return new NYPizza(this);
		}

		@Override
		protected Builder self() {
			return this;
		}

	}

	NYPizza(Builder builder) {
		super(builder);
		this.size = builder.size;
	}
}
