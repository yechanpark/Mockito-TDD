package mokitotddn;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.mockito.Mockito.*; // 강제 import

public class CartTest {

	@Test
	public void createCart() {
		Cart cart = new Cart();
		int n = cart.getTotalNumberOfItemsInCart();
		assertEquals(0, n);
	}

	@Test
	public void putOneItemInCart() {
		Cart cart = new Cart();
		cart.put("1234");
		int n1 = cart.getTotalNumberOfItemsInCart();
		assertEquals(1, n1);
		int n2 = cart.getNumberOfItemsInCart("1234");
		assertEquals(1, n2);
	}

	@Test
	public void putMultipleItemsInCart() {
		Cart cart = new Cart();
		cart.put("1234");
		cart.put("1234");
		int n1 = cart.getTotalNumberOfItemsInCart();
		assertEquals(2, n1);
		int n2 = cart.getNumberOfItemsInCart("1234");
		assertEquals(2, n2);

		cart.put("2345");
		int n3 = cart.getTotalNumberOfItemsInCart();
		assertEquals(3, n3);
		int n4 = cart.getNumberOfItemsInCart("2345");
		assertEquals(1, n4);

		cart.put("2345");
		int n5 = cart.getTotalNumberOfItemsInCart();
		assertEquals(4, n5);
		int n6 = cart.getNumberOfItemsInCart("2345");
		assertEquals(2, n6);
	}

	@Test
	public void computeTotalPriceWhenOneItemPuts() {
		Cart cart = new Cart();
		PriceManager priceManager = mock(PriceManager.class);
		cart.setPriceManager(priceManager); // 주입점에 목객체 주입
		when(priceManager.getPrice("1234")).thenReturn(1000); // stubbing
		cart.put("1234");
		int tot = cart.getTotalPrice();
		assertEquals(1000, tot);
	}

	@Test
	public void computeTotalPriceWhenMultipleItemsPut() {
		Cart cart = new Cart();
		PriceManager priceManager = mock(PriceManager.class);
		cart.setPriceManager(priceManager); // 주입점에 목객체 주입
		when(priceManager.getPrice("1234")).thenReturn(1000); // stubbing
		when(priceManager.getPrice("7891")).thenReturn(800); // stubbing
		cart.put("1234");
		cart.put("7891");
		int tot = cart.getTotalPrice();
		assertEquals(1800, tot);
	}

	@Test
	public void testTotalPriceForOnePlusOneItems() {
		Cart cart = new Cart();
		PriceManager priceManager = mock(PriceManager.class);

		/* Start of Stubbing */
		when(priceManager.getPrice("1234")).thenReturn(1000);
		when(priceManager.getPrice("7896")).thenReturn(800);
		when(priceManager.getPrice("3456")).thenReturn(2500);
		when(priceManager.isOnePlusOneApplicable("1234")).thenReturn(false);
		when(priceManager.isOnePlusOneApplicable("7896")).thenReturn(false);
		when(priceManager.isOnePlusOneApplicable("3456")).thenReturn(true);
		/* End of Stubbing */

		cart.setPriceManager(priceManager); // 주입점에 목객체 주입

		cart.put("1234");
		int numOfStock = cart.getTotalNumberOfItemsInCart();
		assertEquals(1, numOfStock);
		int tot = cart.getTotalPrice();
		assertEquals(1000, tot);

		cart.put("7896");
		numOfStock = cart.getTotalNumberOfItemsInCart();
		assertEquals(2, numOfStock);
		tot = cart.getTotalPrice();
		assertEquals(1800, tot);

		cart.put("3456");
		cart.put("3456");
		numOfStock = cart.getTotalNumberOfItemsInCart();
		assertEquals(4, numOfStock);
		tot = cart.getTotalPrice();
		assertEquals(4300, tot);

		
	}
}
