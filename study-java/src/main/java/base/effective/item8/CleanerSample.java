package base.effective.item8;

import java.lang.ref.Cleaner;

public class CleanerSample implements AutoCloseable {

	private static final Cleaner CLEANER = Cleaner.create();

	private final CleanerRunner cleanerRunner;

	private final Cleaner.Cleanable cleanable;

	public CleanerSample(String id) {
		cleanerRunner = new CleanerRunner(id);
		cleanable = CLEANER.register(this, cleanerRunner);
	}

	@Override
	public void close() throws Exception {
		System.out.println("clean");
		cleanable.clean();
	}

	private static class CleanerRunner implements Runnable {
		private String id;

		public CleanerRunner(String id) {
			this.id = id;
		}

		@Override
		public void run() {
			System.out.printf("MyObject with id %s, is gc%n", id);
		}
	}
}
