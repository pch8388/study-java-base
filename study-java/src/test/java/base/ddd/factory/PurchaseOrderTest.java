package base.ddd.factory;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PurchaseOrderTest {

	@DisplayName("AGGREGATE 루트에 FACTORY METHOD 를 둔다")
	@Test
	void aggregate_root_factory_method() {
		PurchaseOrder order = new PurchaseOrder("b123");
		final String itemNumber = order.newItem("pt393", 5);

		assertThat(itemNumber).isEqualTo("i3");
	}
}