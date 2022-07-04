package base.effective.item3;

import java.io.Serial;
import java.io.Serializable;

public class SerializableSingleton implements Serializable {
	private static final SerializableSingleton INSTANCE = new SerializableSingleton();

	private SerializableSingleton() {
	}

	public static SerializableSingleton getInstance() {
		return INSTANCE;
	}

	@Serial
	private Object readResolve() {
		return INSTANCE;
	}
}
