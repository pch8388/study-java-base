package base.enumcollect;

public enum AbstractMethodEnum {

	PRINT {
		@Override
		String apply(String str) {
			return str + " enum test";
		}
	};

	abstract String apply(String data);
}
