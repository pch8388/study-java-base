package me.study.base.effective.item3;

import java.io.Serializable;

public class SerializableSingleton implements Serializable {
	private static final SerializableSingleton INSTANCE = new SerializableSingleton();

	private SerializableSingleton() {
	}

	public static SerializableSingleton getInstance() {
		return INSTANCE;
	}

	private Object readResolve() {
		return INSTANCE;
	}
}
