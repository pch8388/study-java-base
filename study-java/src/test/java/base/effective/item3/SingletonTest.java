package base.effective.item3;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SingletonTest {

	@Test
	@DisplayName("싱글톤 테스트")
	void singleton() {
		final Singleton instance = Singleton.getInstance();
		final Singleton instance2 = Singleton.getInstance();

		assertSame(instance, instance2);
	}

	@Test
	@DisplayName("싱글톤 직렬화 테스트")
	void serialize() throws IOException, ClassNotFoundException {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final ObjectOutputStream outputStream = new ObjectOutputStream(out);
		outputStream.writeObject(Singleton.getInstance());

		final Object object = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray())).readObject();
		assertNotSame(object, Singleton.getInstance());


		final ByteArrayOutputStream overrideOut = new ByteArrayOutputStream();
		final ObjectOutputStream overrideOutputStream = new ObjectOutputStream(overrideOut);
		overrideOutputStream.writeObject(SerializableSingleton.getInstance());

		final Object readResolveOverride = new ObjectInputStream(new ByteArrayInputStream(overrideOut.toByteArray())).readObject();
		assertSame(readResolveOverride, SerializableSingleton.getInstance());
	}

	@Test
	@DisplayName("enum 테스트")
	void singleton_enum() {
		final SingleInstance instance = SingleInstance.SINGLE;
		final SingleInstance instance2 = SingleInstance.SINGLE;

		assertSame(instance, instance2);
	}
}