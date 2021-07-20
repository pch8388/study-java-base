package me.study.base.ddd.factory;

import java.util.Objects;

class PurchaseItem {
	private final String itemNumber;
	private final int quantity;
	private final CatalogPart catalogPart;

	PurchaseItem(final String itemNumber, final int quantity, final CatalogPart catalogPart) {
		this.itemNumber = itemNumber;
		this.quantity = quantity;
		this.catalogPart = catalogPart;
	}

	String getItemNumber() {
		return itemNumber;
	}

	int getQuantity() {
		return quantity;
	}

	CatalogPart getCatalogPart() {
		return catalogPart;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PurchaseItem that = (PurchaseItem)o;
		return Objects.equals(itemNumber, that.itemNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemNumber);
	}
}
