package base.reactive.observer;

import java.util.HashSet;
import java.util.Set;

public class ConcreteSubject implements Subject<String> {

	private final Set<Observer<String>> observers = new HashSet<>();

	@Override
	public void registerObserver(Observer<String> observer) {
		observers.add(observer);
	}

	@Override
	public void unregisterObserver(Observer<String> observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(String event) {
		observers.forEach(observer -> observer.observe(event));
	}
}
