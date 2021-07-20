package me.study.base.ddd.factory;

import java.util.HashSet;
import java.util.Set;

public class PurchaseOrder {
	private final String id;
	private final Set<PurchaseItem> purchaseItem;

	public PurchaseOrder(final String id) {
		this.id = id;
		this.purchaseItem = new HashSet<>();
	}

	public String newItem(final String partNumber, final int quantity) {
		final String itemNumber = generateItemNumber();
		purchaseItem.add(new PurchaseItem(itemNumber, quantity, new CatalogPart(partNumber)));
		return itemNumber;
	}

	/** 실제로는 item number 를 생성하도록 구현  */
	private String generateItemNumber() {
		return "i3";
	}
}
