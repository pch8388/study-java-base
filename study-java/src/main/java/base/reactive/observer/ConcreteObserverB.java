package base.reactive.observer;

public class ConcreteObserverB implements Observer<String> {
	@Override
	public void observe(String event) {
		System.out.println("Observer B : " + event);
	}
}
