package architecture.factory;

public class Application {
	public static void main(String[] args) {
		final Client client = new Client(new ServiceFactoryImpl());
		client.startApp();
	}
}

class Client {

	private final ServiceFactory factory;

	public Client(ServiceFactory factory) {
		this.factory = factory;
	}

	void startApp() {
		final Service service = factory.makeService();
		service.run();
	}
}

interface Service {
	void run();
}

class ConcreteImpl implements Service {

	@Override
	public void run() {
		System.out.println("run concrete");
	}
}

interface ServiceFactory {
	Service makeService();
}

class ServiceFactoryImpl implements ServiceFactory {

	@Override
	public Service makeService() {
		return new ConcreteImpl();
	}
}