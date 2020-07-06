/**
 * @author Piotr Werczyński s17093
 */
package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	/**
	 * Check if amount of money is equal to amount from method getAmount()
	 */
	@Test
	public void testGetAmount() {
		String msg = "incorrect amount";

		assertEquals(msg, Integer.valueOf(10000), SEK100.getAmount());
		assertEquals(msg, Integer.valueOf(1000), EUR10.getAmount());
		assertEquals(msg, Integer.valueOf(20000), SEK200.getAmount());
		assertEquals(msg, Integer.valueOf(2000), EUR20.getAmount());
		assertEquals(msg, Integer.valueOf(0), SEK0.getAmount());
		assertEquals(msg, Integer.valueOf(0), EUR0.getAmount());
		assertEquals(msg, Integer.valueOf(-10000), SEKn100.getAmount());
	}

	/**
	 * Check if currency of money is equal to currency from method getCurrency()
	 */
	@Test
	public void testGetCurrency() {
		assertSame(EUR, EUR10.getCurrency() );
		assertSame(SEK, SEK100.getCurrency() );
		assertNotSame(EUR, SEK100.getCurrency() );
	}

	/**
	 * Check if string is equal to string from method toString()
	 */
	@Test
	public void testToString() {
		assertEquals("100.0 SEK",SEK100.toString());
		assertEquals("-100.0 SEK",SEKn100.toString());
	}

	/**
	 * Check if value is equal to universalValue from method universalValue()
	 */
	@Test
	public void testGlobalValue() {
		assertEquals(Integer.valueOf(3000), SEK200.universalValue());
		assertEquals(Integer.valueOf(3000), EUR20.universalValue());
	}

	/**
	 * Check if money is equal to money
	 */
	@Test
	public void testEqualsMoney() {
		assertTrue(EUR20.equals(SEK200));
		assertFalse(EUR10.equals(SEK0));
	}

	/**
	 * Check if adding amount in money is working
	 */
	@Test
	public void testAdd() {
		assertEquals((new Money(40000, SEK)).getAmount().intValue(), SEK200.add(SEK200).getAmount().intValue());
		assertEquals((new Money(40000, SEK)).getAmount().intValue(), SEK200.add(EUR20).getAmount().intValue());
	}

	/**
	 * Check if subtracting money is working
	 */
	@Test
	public void testSub() {
		assertEquals(SEK200.sub(SEK200).getAmount(), SEK200.sub(EUR20).getAmount());
		assertEquals((new Money(0, SEK)).getAmount().intValue(), SEK100.sub(SEK100).getAmount().intValue());
	}

	/**
	 * Check if amount of money is equal 0
	 */
	@Test
	public void testIsZero() {
		assertTrue(EUR0.isZero());
		assertFalse(EUR20.isZero());
	}

	/**
	 * Check if amount of money is negate
	 */
	@Test
	public void testNegate() {
		assertEquals(SEKn100.getAmount(), SEK100.negate().getAmount() );
		assertEquals(SEK100.getAmount(), SEKn100.negate().getAmount() );
	}

	/**
	 * Check if method Compare to is working
	 */
	@Test
	public void testCompareTo() {
		assertEquals(0, SEK200.compareTo(EUR20));
		assertEquals(-1, SEKn100.compareTo(EUR10));
		assertEquals(1, EUR20.compareTo(SEK0));
	}
}
