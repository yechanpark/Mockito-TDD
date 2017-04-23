package mokitotddn;

import java.util.HashMap;
import java.util.Iterator;

public class Cart {

	private HashMap<String, Integer> h = new HashMap<String, Integer>();
	private int count = 0;
	private PriceManager priceManager; // 가짜 객체를 사용하여 테스트

	// 주입점 -> setter를 사용하여 실제 객체 혹은 목객체를 넣을 수 있다.
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

			if (priceManager.isOnePlusOneApplicable(id)) { // 1+1행사 처리
				int quotient = numberOfStock / 2; // 몫 -> 1+1이므로 2개가 1개 값
				int remainder = numberOfStock % 2; // 나머지 -> 홀수일 경우 1개 값을 더 쳐야함
				numberOfStock = quotient + remainder;
			}

			totalPrice += itemPrice * numberOfStock;

		}
		return totalPrice;
	}

}
