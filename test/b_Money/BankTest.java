/**
 * @author Piotr Werczyński s17093
 */
package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("DanskeBank",DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, Nordea.getCurrency());
	}

	/**
	 * Expected b_Money.AccountExistsException to be thrown, but nothing was thrown.
	 * @throws AccountExistsException
	 * @throws AccountDoesNotExistException
	 */
	@Test(expected = AccountExistsException.class)
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("Test");
		SweBank.deposit("Test", new Money(100, SEK) );
		SweBank.openAccount("Test");
	}

	/**
	 * Expected b_Money.AccountDoesNotExistException to be thrown, but nothing was thrown.
	 * @throws AccountDoesNotExistException
	 */
	@Test(expected = AccountDoesNotExistException.class)
	public void testDeposit() throws AccountDoesNotExistException {
		int amount = 2000;
		SweBank.deposit("Ulrika", new Money(amount,SEK));
		assertEquals(amount,SweBank.getBalance("Ulrika").intValue());
		SweBank.deposit("TEST12345", new Money(2000,SEK));
	}

	@Test(expected = AccountDoesNotExistException.class)
	public void testWithdraw() throws AccountDoesNotExistException {
		int amount = 200;
		SweBank.withdraw("Ulrika", new Money(amount,SEK));
		assertEquals(-amount,SweBank.getBalance("Ulrika").intValue());
		SweBank.withdraw("TEST12345", new Money(2000,SEK));
	}

	@Test(expected = AccountDoesNotExistException.class)
	public void testGetBalance() throws AccountDoesNotExistException {
		int amount = 2000;
		SweBank.deposit("Bob", new Money(amount, SEK) );
		assertEquals(amount, SweBank.getBalance("Bob").intValue());
		SweBank.getBalance("TEST12345");
	}

	@Test(expected = AccountDoesNotExistException.class)
	public void testTransfer() throws AccountDoesNotExistException {
		int amount =2000;
		SweBank.transfer("Ulrika","Bob",new Money(amount, SEK));
		assertEquals(amount, SweBank.getBalance("Bob").intValue());
		assertEquals(-amount, SweBank.getBalance("Ulrika").intValue());
		SweBank.transfer("TEST12345","qwertyu",new Money(2000 ,SEK));
	}

	@Test(expected = AccountDoesNotExistException.class)
	public void testTimedPayment() throws AccountDoesNotExistException {
		int amountSweBankBob = SweBank.getBalance("Bob");
		int amountNordeaBob = Nordea.getBalance("Bob");
		int amount = 100;

		SweBank.addTimedPayment("Bob", "Test", 1, 1, new Money(amount, SEK), Nordea, "Bob");
		SweBank.tick();

		assertEquals(amountSweBankBob, SweBank.getBalance("Bob").intValue());
		assertEquals(amountNordeaBob, Nordea.getBalance("Bob").intValue());

		SweBank.tick();

		assertEquals(amountSweBankBob-amount, SweBank.getBalance("Bob").intValue());
		assertEquals(amountNordeaBob+amount, Nordea.getBalance("Bob").intValue());

		SweBank.tick();
		SweBank.tick();

		assertEquals(amountSweBankBob-2*amount, SweBank.getBalance("Bob").intValue());
		assertEquals(amountNordeaBob+2*amount, Nordea.getBalance("Bob").intValue());

		SweBank.removeTimedPayment("Bob", "Test");

		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();
		SweBank.tick();

		assertEquals(amountSweBankBob-2*amount, SweBank.getBalance("Bob").intValue());
		assertEquals(amountNordeaBob+2*amount, Nordea.getBalance("Bob").intValue());

		SweBank.addTimedPayment("Test123","P123",12,12,new Money(222,SEK),SweBank,"Ulrika");
	}
}
