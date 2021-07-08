package me.study.base.reactive.observer;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ObserverTest {

	@DisplayName("옵저버 패턴 학습")
	@Test
	void observerPattern() {
		Subject<String> subject = new ConcreteSubject();
		Observer<String> observerA = spy(new ConcreteObserverA());
		Observer<String> observerB = spy(new ConcreteObserverB());

		subject.notifyObservers("No listeners");

		subject.registerObserver(observerA);
		subject.notifyObservers("Message only A");

		subject.registerObserver(observerB);
		subject.notifyObservers("Message All");

		subject.unregisterObserver(observerA);
		subject.notifyObservers("Message only B");

		subject.unregisterObserver(observerB);
		subject.notifyObservers("No listeners");

		verify(observerA, times(1)).observe("Message only A");
		verify(observerA, times(1)).observe("Message All");
		verifyNoMoreInteractions(observerA);

		verify(observerB, times(1)).observe("Message All");
		verify(observerB, times(1)).observe("Message only B");
		verifyNoMoreInteractions(observerB);
	}
}