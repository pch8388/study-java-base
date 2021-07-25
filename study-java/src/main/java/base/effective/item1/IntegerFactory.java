package base.effective.item1;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class IntegerFactory {

	private static final Map<Integer, IntegerFactory> cache = new HashMap<>();

	private int data;

	static {
		IntStream.range(0, 101).forEach(i -> cache.put(i, new IntegerFactory(i)));
	}

	private IntegerFactory(int data) {
		this.data = data;
	}

	public static IntegerFactory valueOf(int data) {
		if (cache.containsKey(data)) {
			return cache.get(data);
		}
		return new IntegerFactory(data);
	}
}
