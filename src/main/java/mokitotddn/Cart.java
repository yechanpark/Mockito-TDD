package mokitotddn;

import java.util.HashMap;
import java.util.Iterator;

public class Cart {

	private HashMap<String, Integer> h = new HashMap<String, Integer>();
	private int count = 0;
	private PriceManager priceManager; // ��¥ ��ü�� ����Ͽ� �׽�Ʈ

	// ������ -> setter�� ����Ͽ� ���� ��ü Ȥ�� ��ü�� ���� �� �ִ�.
	public void setPriceManager(PriceManager priceManager) {
		this.priceManager = priceManager;
	}

	public int getTotalNumberOfItemsInCart() {
		return count;
	}

	public void put(String id) {
		if (h.containsKey(id)) {
			int n = h.get(id);
			h.put(id, n + 1);
		} else
			h.put(id, 1);
		count++;
	}

	public int getNumberOfItemsInCart(String id) {
		return h.get(id);
	}

	public int getTotalPrice() {
		Iterator itr = h.keySet().iterator();
		int totalPrice = 0;

		while (itr.hasNext()) {
			String id = itr.next().toString();
			int numberOfStock = h.get(id);
			int itemPrice = priceManager.getPrice(id);

			if (priceManager.isOnePlusOneApplicable(id)) { // 1+1��� ó��
				int quotient = numberOfStock / 2; // �� -> 1+1�̹Ƿ� 2���� 1�� ��
				int remainder = numberOfStock % 2; // ������ -> Ȧ���� ��� 1�� ���� �� �ľ���
				numberOfStock = quotient + remainder;
			}

			totalPrice += itemPrice * numberOfStock;

		}
		return totalPrice;
	}

}
