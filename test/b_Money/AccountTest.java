/**
 * @author Piotr Werczyński s17093
 */
package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("Test", 1, 1, new Money(100, SEK), SweBank, "Alice");
		assertTrue( testAccount.timedPaymentExists("Test") );

		testAccount.removeTimedPayment("Test");
		assertFalse( testAccount.timedPaymentExists("Test") );
	}

	@Test(expected = AccountDoesNotExistException.class)
	public void testTimedPayment() throws AccountDoesNotExistException {
		int testAmountHans = testAccount.getBalance().getAmount().intValue();
		int testAmountAlice = SweBank.getBalance("Alice").intValue();
		int amount = 50;

		testAccount.addTimedPayment("Test", 1, 1, new Money(amount, SEK), SweBank, "Alice");
		testAccount.tick();

		assertEquals(testAmountHans, testAccount.getBalance().getAmount().intValue() );
		assertEquals(testAmountAlice, SweBank.getBalance("Alice").intValue() );

		testAccount.tick();

		assertEquals(testAmountHans-amount, testAccount.getBalance().getAmount().intValue() );
		assertEquals(testAmountAlice+amount, SweBank.getBalance("Alice").intValue() );

		testAccount.tick();
		testAccount.tick();

		assertEquals(testAmountHans-2*amount, testAccount.getBalance().getAmount().intValue() );
		assertEquals(testAmountAlice+2*amount, SweBank.getBalance("Alice").intValue() );

		testAccount.removeTimedPayment("Test");

		testAccount.tick();
		testAccount.tick();
		testAccount.tick();
		testAccount.tick();
		testAccount.tick();
		testAccount.tick();
		testAccount.tick();
		testAccount.tick();

		assertEquals(testAmountHans-2*amount, testAccount.getBalance().getAmount().intValue() );
		assertEquals(testAmountAlice+2*amount, SweBank.getBalance("Alice").intValue() );

		SweBank.getBalance("qwerty");
	}

	@Test
	public void testAddWithdraw() {
		int testAmount = testAccount.getBalance().getAmount().intValue();
		int addAmount = 500;

		testAccount.deposit(new Money(addAmount, SEK));
		assertEquals(testAmount+addAmount, testAccount.getBalance().getAmount().intValue() );

		testAccount.withdraw(new Money(addAmount, SEK));
		assertEquals(testAmount, testAccount.getBalance().getAmount().intValue() );
	}

	@Test
	public void testGetBalance() {
		assertEquals(10000000, testAccount.getBalance().getAmount().intValue() );
	}
}
