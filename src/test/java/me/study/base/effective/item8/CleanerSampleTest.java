package me.study.base.effective.item8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CleanerSampleTest {

	@Test
	@DisplayName("clean 을 수행하지 않음")
	void cleaner() {
		for (int i = 0; i < 10; i++) {
			CleanerSample c = new CleanerSample(Integer.valueOf(i).toString());
		}
	}

	@Test
	@DisplayName("clean 을 수행")
	void cleaner_run() throws Exception {
		for (int i = 0; i < 10; i++) {
			try (CleanerSample c = new CleanerSample(Integer.valueOf(i).toString())) {

			}
		}
	}
}