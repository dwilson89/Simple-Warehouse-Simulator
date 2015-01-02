package asgn1Solution;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class LedgerTest {

	/**
	 * Test Suite for WarehouseLedger Class
	 * 
	 * @author Dustin Wilson - n6325157
	 * 
	 * The following tests exercise all of the WarehouseLedger class methods
	 * by testing them with values, that fit into the following case categories
	 * normal, exceptional and boundary.
	 */
	
	//Test Variables
	
	//Test object - ledger
	WarehouseLedger testLedger;
	
	//Just standard initial input for the ledger
	final Integer TEST_STOCK = new Integer(20);
	final Integer TEST_CASH = new Integer(80);
 	final Integer TEST_WHOLESALE = new Integer(6);
	final Integer TEST_RETAIL = new Integer(8);
	final Integer TEST_DELIVERY = new Integer(40);
	
	/* Create a new warehouse ledger for each test */
	@Before
	@Test
	public void setUp() throws WarehouseException {
	
		testLedger = new WarehouseLedger(TEST_STOCK, TEST_CASH, TEST_WHOLESALE,
				TEST_RETAIL, TEST_DELIVERY);
		
	}

	/*Test cases for the constructor WarehouseLedger.*/
	
	//Test case 1 - Zero values - expected result zero values
	@Test
	public void ledgerCreationZeroValues() throws WarehouseException {
		
		Integer zero = new Integer(0);
		
		testLedger = new WarehouseLedger(zero, zero, zero, zero, zero);
		
		assertEquals(zero, testLedger.cashAvailable());
		assertEquals(zero, testLedger.inStock());
		
	}
	
	/*Negative values tests*/
	
	//Test case 2 - Negative stock - expected result throws an exception
	@Test(expected = WarehouseException.class)
	public void ledgerCreationNegStock() throws WarehouseException {
		
		Integer negativeStock = new Integer(-20);
		
		testLedger = new WarehouseLedger(negativeStock, TEST_CASH, TEST_WHOLESALE,
				TEST_RETAIL, TEST_DELIVERY);
		
	}
	
	//Test case 3 - Negative wholesale value - expected result throws an exception
	@Test(expected = WarehouseException.class)
	public void ledgerCreationNegWholesale() throws WarehouseException {
		
		Integer negativeWholesale = new Integer(-6);
		
		testLedger = new WarehouseLedger(TEST_STOCK, TEST_CASH, negativeWholesale,
				TEST_RETAIL, TEST_DELIVERY);
		
	}
	
	//Test case 4 - Negative retail value - expected result throws an exception
	@Test(expected = WarehouseException.class)
	public void ledgerCreationNegRetail() throws WarehouseException {
		
		Integer negativeRetail = new Integer(-8);
		
		testLedger = new WarehouseLedger(TEST_STOCK, TEST_CASH, TEST_WHOLESALE,
				negativeRetail, TEST_DELIVERY);
		
	}
	
	//Test case 5 - Negative delivery value - expected result throws an exception
	@Test(expected = WarehouseException.class)
	public void ledgerCreationNegDelivery() throws WarehouseException {
			
		Integer negativeDelivery = new Integer(-40);
		
		testLedger = new WarehouseLedger(TEST_STOCK, TEST_CASH, TEST_WHOLESALE,
					TEST_RETAIL, negativeDelivery);
			
	}
	
	//Test case 6 - Negative cash value - expected result negative value - nothing happens.
	@Test
	public void ledgerCreationNegCash() throws WarehouseException {
			
		Integer negativeCash = new Integer(-100);
			
		testLedger = new WarehouseLedger(TEST_STOCK, negativeCash, TEST_WHOLESALE,
					TEST_RETAIL, TEST_DELIVERY);
		
		assertEquals(negativeCash,testLedger.cashAvailable());
	}
	
	/*Additional test case for constructor*/ 
	
	//Test case 7 - When Wholesale > Retail - expected result Exception thrown.
	@Test(expected = WarehouseException.class)
	public void wholesaleGreaterThanRetail() throws WarehouseException {
			
		Integer testTwoWholesale = new Integer(8);
		Integer testTwoRetail = new Integer(6);
		
		testLedger = new WarehouseLedger(TEST_STOCK, TEST_CASH, testTwoWholesale,
					testTwoRetail, TEST_DELIVERY);
		
	}
	
	//Test case 8 - When Wholesale == Retail - expected nothing happens
	@Test
	public void wholesaleEqualsRetail() throws WarehouseException {
			
		Integer testTwoWholesale = new Integer(8);
		Integer testTwoRetail = new Integer(8);
		
		testLedger = new WarehouseLedger(TEST_STOCK, TEST_CASH, testTwoWholesale,
					testTwoRetail, TEST_DELIVERY);
		
	}
	
	/*Simple test cases to make sure initial values are being set correctly*/ 
	
	//Test case 1 - Sets correct initial starting day - expected result day 1 
	@Test
	public void initialDayTest(){
		
		Integer testDay = new Integer(1);
		
		assertEquals(testDay, testLedger.currentDay());
		
	}

	//Test case 2 - Sets correct initial cash reserve - expected result initial cash value 80
	@Test
	public void initialCashTest(){
		
		assertEquals(TEST_CASH, testLedger.cashAvailable());
		
	}
	
	//Test case 3 - Sets correct initial stock value - expected result initial cash value 20
	@Test
	public void initialStockTest(){
	
		assertEquals(TEST_STOCK, testLedger.inStock());
		
	}
	
	/*Simple Test cases for getters with parameters, they will be further tested in other
	test cases*/
	
	/*Boundary Test cases for cashAvailable(parameter)*/
	
	//Test case 1 - Day 1 (current day) - no exception is thrown working as intended
	//expected result initial cash value 80 for day 1
	@Test
	public void cashAvailableCurrentDay() throws WarehouseException{
		
		Integer testDay = new Integer(1);
		
		assertEquals(TEST_CASH, testLedger.cashAvailable(testDay));
		
	}
	
	//Test case 2 - tomorrow's value - Exception is thrown as day inputed exceeds currentDay
	@Test(expected = WarehouseException.class)
	public void cashAvailableForTomorrow() throws WarehouseException{
		
		Integer testDay = new Integer(2);
		
		//Try to grab the value
		Integer grabTomorrowsValue = testLedger.cashAvailable(testDay);
		
		assertEquals(TEST_CASH, grabTomorrowsValue);
		
	}
	
	//Test case 3 - zero value for day - Exception is thrown as day inputed is less than 1
	@Test(expected = WarehouseException.class)
	public void cashAvailableZeroDayValue() throws WarehouseException{
			
		Integer testDay = new Integer(0);
		
		//Try to grab the value
		Integer grabZeroDayValue = testLedger.cashAvailable(testDay);
			
		assertEquals(TEST_CASH, grabZeroDayValue);
			
	}
	
	/*Boundary Test cases for inStock(parameter)*/
	
	//Test case 1 - day 1 (current day) - no exception is thrown working as intended
	//expected result initial stock value 20 for day 1
	@Test
	public void inStockCurrentDay() throws WarehouseException{
		
		Integer testDay = new Integer(1);
		
		assertEquals(TEST_STOCK, testLedger.inStock(testDay));
		
	}
	
	//Test case 2 - tomorrow's stock value - Exception is thrown as day inputed exceeds currentDay
	@Test(expected = WarehouseException.class)
	public void inStockTomorrowsValue() throws WarehouseException{
		
		Integer testDay = new Integer(2);
		
		//Try to grab the value
		Integer grabTomorrowsValue = testLedger.inStock(testDay);
		
		assertEquals(TEST_STOCK, grabTomorrowsValue);
		
	}
	
	//Test case 3 - zero day value - Exception is thrown as day inputed is less than 1
	@Test(expected = WarehouseException.class)
	public void inStockZeroDayValue() throws WarehouseException{
			
		Integer testDay = new Integer(0);
		
		//Try to grab the value
		Integer grabZeroDayValue = testLedger.inStock(testDay);
		
		assertEquals(TEST_STOCK, grabZeroDayValue);
			
	}	
	
	/*Test cases for nextDay method - will also be simple testing for 
	cashAvailable(parameter) in addition to inStock(parameter)*/
	
	//Test case 1 - increments current day correctly - expected value 2
	@Test
	public void incrementsNextDay(){
		
		Integer expected = new Integer(2);
		testLedger.nextDay();
		assertEquals(expected, testLedger.currentDay());
		
	}
	
	//Test case 2 - Stores the previous days data correctly - expected yesterday's cash value
	@Test
	public void storesPreviousDaysCashValue() throws WarehouseException{
		
		Integer testDay = new Integer(1);
		testLedger.nextDay();
		assertEquals(TEST_CASH, testLedger.cashAvailable(testDay));

	}
	
	//Test case 3 - Stores the previous days data correctly - expected yesterday's stock value
	@Test
	public void storesPreviousDaysStockValue() throws WarehouseException{
		
		Integer testDay = new Integer(1);
		testLedger.nextDay();
		assertEquals(TEST_STOCK, testLedger.inStock(testDay));
	
	}
	
	/*Test cases for sellItems method*/
	
	//Test case 1 - Sells requested amount (positive value) - expected true
	@Test
	public void saleOccurs()throws WarehouseException{
		
		Integer testRequested = new Integer(1);
		
		assertTrue(testLedger.sellItems(testRequested));
		
	}
	
	//Test case 2 - Sells requested amount (positive value), checks cash value updated correctly 
	//expected true and current cash value 160 
	@Test
	public void saleOccursCashUpdates()throws WarehouseException{
		
		Integer testRequested = new Integer(10);
		Integer expectedCash = new Integer(160);
		
		testLedger.sellItems(testRequested);
		assertEquals(expectedCash, testLedger.cashAvailable());
		
	}
	
	//Test case 3 - Sells requested amount (positive value) - checks stock value updated correctly
	//expected current stock value 10
	@Test
	public void saleOccursStockUpdates()throws WarehouseException{
		
		Integer testRequested = new Integer(10);
		Integer expectedStock = new Integer(10);
		
		testLedger.sellItems(testRequested);
		assertEquals(expectedStock, testLedger.inStock());
		
	}
	
	//Test case 4 - Input is greater than current stock value, no sale occurs - expected false
	@Test
	public void noSaleOccurs()throws WarehouseException{
		
		Integer testRequested = new Integer(21);
		
		assertFalse(testLedger.sellItems(testRequested));

	}
	
	//Test case 5 - Input is greater than current stock value, no sale occurs - 
	//Checks if cash value stays the same - expected cash value 80
	@Test
	public void noSaleOccursCashNotUpdated()throws WarehouseException{
		
		Integer testRequested = new Integer(21);
		
		testLedger.sellItems(testRequested);
		assertEquals(TEST_CASH, testLedger.cashAvailable());
		
	}
	
	//Test case 6 - Input is greater than current stock value, no sale occurs -
	//Checks if stock value stays the same - expected stock value 20
	@Test
	public void noSaleOccursStockNotUpdated()throws WarehouseException{
		
		Integer testRequested = new Integer(21);
		
		testLedger.sellItems(testRequested);
		assertEquals(TEST_STOCK, testLedger.inStock());
		
	}
	
	//Test case 7 - Required amount is negative - checks that it throws an exception
	@Test(expected = WarehouseException.class)
	public void sellsNegativeAmount()throws WarehouseException{
		
		Integer testRequested = new Integer(-8);
		
		//Try to sells the requested goods
		boolean sellNegativeValue = testLedger.sellItems(testRequested);
		
		assertTrue(sellNegativeValue);
		
	}
	
	//Test case 8 - Required amount is zero - Expected true
	@Test
	public void sellsZeroAmount()throws WarehouseException{
		
		Integer testRequested = new Integer(0);
		
		assertTrue(testLedger.sellItems(testRequested));
		
	}
	
	//Test case 9 - Required amount is zero - Expected cash value 80
	@Test
	public void sellsZeroAmountCashCheck()throws WarehouseException{
		
		Integer testRequested = new Integer(0);
		
		testLedger.sellItems(testRequested);
		assertEquals(TEST_CASH, testLedger.cashAvailable());
		
	}
	
	//Test case 10 - Required amount is zero - Expected stock value 20
	@Test
	public void sellsZeroAmountStockCheck()throws WarehouseException{
		
		Integer testRequested = new Integer(0);
		
		testLedger.sellItems(testRequested);
		assertEquals(TEST_STOCK, testLedger.inStock());
		
	}
	
	//Test case 11 - Require amount is all stock - Expected True
	@Test
	public void sellsEverythingInStock()throws WarehouseException{
		
		Integer testRequested = new Integer(20);
		
		assertTrue(testLedger.sellItems(testRequested));

	}
	
	//Test case 12 - Require amount is all stock - Expected Stock value 0
	@Test
	public void sellsEverythingStockUpdated()throws WarehouseException{
		
		Integer testRequested = new Integer(20);
		Integer expectedValue = new Integer(0);
		
		testLedger.sellItems(testRequested);
		assertEquals(expectedValue, testLedger.inStock());
		
	}
	
	//Test case 13 - Require amount is all stock - Expected Cash value 240
	@Test
	public void sellsEverythingCashUpdated()throws WarehouseException{
		
		Integer testRequested = new Integer(20);
		Integer expectedValue = new Integer(240);
		
		testLedger.sellItems(testRequested);
		assertEquals(expectedValue, testLedger.cashAvailable());
	
	}
	
	/*Test cases for buyItems method*/
	
	//Test case 1 - Buys the requested amount - expected Stock value 21
	@Test
	public void buyItems() throws WarehouseException{
		
		Integer testRequired = new Integer(1);
		Integer expectedStock = new Integer(21);
		
		testLedger.buyItems(testRequired);
		assertEquals(expectedStock, testLedger.inStock());
		
	}
	
	//Test case 2 - Buys the requested amount - expected cash value 34
	@Test
	public void buyItemsUpdatesCash() throws WarehouseException{
		
		Integer testRequired = new Integer(1);
		Integer expectedCash = new Integer(34);
		
		testLedger.buyItems(testRequired);
		assertEquals(expectedCash, testLedger.cashAvailable());
		
	}
	
	//Test case 3 - Buys the requested amount of zero - expected stock value 20
	@Test
	public void buyItemsUpdatesStock() throws WarehouseException{
		
		Integer testRequired = new Integer(0);
		
		testLedger.buyItems(testRequired);
		assertEquals(TEST_STOCK, testLedger.inStock());
		
	}
	
	//Test case 4 - Buys the requested amount of zero - expected cash value 40
	//Even though no items are bought, delivery is still deducted 
	@Test
	public void buysZeroAmount() throws WarehouseException{
		
		Integer testRequired = new Integer(0);
		Integer expectedCash = new Integer(40);
		
		testLedger.buyItems(testRequired);
		assertEquals(expectedCash, testLedger.cashAvailable());
		
	}
	
	//Test case 5 - Required is negative - expected thrown exception
	@Test(expected = WarehouseException.class)
	public void buyNegativeAmount() throws WarehouseException{
		
		Integer testRequired = new Integer(-1);
		
		testLedger.buyItems(testRequired);
		
	}
	
	//Test case 6 - Overdrawing budget - expected cash value -2
	//The cash is allowed to become negative, if necessary.
	@Test
	public void overdrawsTheBudget() throws WarehouseException{
		
		Integer testRequired = new Integer(7);
		Integer expectedCash = new Integer(-2);
		
		testLedger.buyItems(testRequired);
		assertEquals(expectedCash, testLedger.cashAvailable());
		
	}

	/*Test case for toString method*/
	
	//Test case 1 - Initial start up message
	@Test
	public void initialStartUpMessage(){
		
		String testString = "Day 1: Cash reserve = $80; Items " +
				"in stock = 20\n";
		
		assertEquals(testString, testLedger.toString());
		
	}
	
	//Test case 2 - Message is correct when values are changed.
	@Test
	public void updatedMessage() throws WarehouseException{
		
		Integer testRequested = new Integer(1);
		
		String testString = "Day 2: Cash reserve = $88; Items " +
				"in stock = 19\n";
		
		testLedger.sellItems(testRequested);
		
		testLedger.nextDay();

		assertEquals(testString, testLedger.toString());
	}	
	
}
