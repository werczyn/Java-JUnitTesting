/**
 * @author Piotr Werczyński s17093
 */
package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	/**
	 * Check if name of currency is equal to name from method getName()
	 */
	@Test
	public void testGetName() {
		assertEquals("SEK", SEK.getName() );
		assertEquals("DKK", DKK.getName() );
		assertEquals("EUR", EUR.getName() );
	}

	/**
	 * Check if rate of currency is equal to rate from method getRate()
	 */
	@Test
	public void testGetRate() {
		assertEquals(Double.valueOf(0.15), SEK.getRate() );
		assertEquals(Double.valueOf(0.20), DKK.getRate() );
		assertEquals(Double.valueOf(1.5), EUR.getRate() );
	}

	/**
	 * Check if rate of currency is equal to rate set in method setName()
	 */
	@Test
	public void testSetRate() {
		double rate = 2.2;
		SEK.setRate(rate);
		assertEquals(Double.valueOf(rate), SEK.getRate());

		rate = 5.5;
		DKK.setRate(rate);
		assertEquals(Double.valueOf(rate), DKK.getRate());

		rate = 0.0;
		EUR.setRate(rate);
		assertEquals(Double.valueOf(rate), EUR.getRate());
	}

	/**
	 * Check if universal Value of currency is equal to value set in method universalValue()
	 */
	@Test
	public void testGlobalValue() {
		assertEquals(Integer.valueOf(30), EUR.universalValue(20));
		assertEquals(Integer.valueOf(45), SEK.universalValue(300));
	}

	/**
	 * Check if value is equal to value in chosen currency in method valueInThisCurrency()
	 */
	@Test
	public void testValueInThisCurrency() {
		assertEquals(Integer.valueOf(50), SEK.valueInThisCurrency(5, EUR));
	}

}
