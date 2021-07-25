package base.effective.item2;

public class CalzonePizza extends Pizza {
	private final boolean sauceInside;

	public static class Builder extends Pizza.Builder<Builder> {
		private boolean sauceInside = false;

		public Builder sauceInside() {
			this.sauceInside = true;
			return this;
		}

		@Override
		CalzonePizza build() {
			return new CalzonePizza(this);
		}

		@Override
		protected Builder self() {
			return this;
		}
	}


	CalzonePizza(Builder builder) {
		super(builder);
		this.sauceInside = builder.sauceInside;
	}
}
