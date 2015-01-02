package asgn1Solution;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TransactionsTest {


	/**
	 * Test Suite for WarehouseTransactions Class
	 * 
	 * @author Dustin Wilson - n6325157
	 * 
	 * The following tests exercise all of the WarehouseTransaction class methods
	 * by testing them with values, that fit into the following case categories
	 * normal, exceptional and boundary.
	 */
	
	//Test Variables
	WarehouseTransactions testTransactions;
	
	final Integer TEST_CAPACITY = new Integer(20);
	final Integer TEST_DURATION = new Integer(3);
	
	final Integer ZERO = new Integer(0);
	
	WarehouseLedger testLedger;
	
	//method for creating a warehouse ledger with particular cash values for 
	//certain testing conditions
	private void testingConditionsSetUp(Integer cash, Integer stock) throws WarehouseException{
		
		//Ledger variables - set up the test object, regular values
		Integer wholesale = new Integer(6);
		Integer retail = new Integer(8);
		Integer delivery = new Integer(40);
		
		testLedger = new WarehouseLedger(stock, cash, wholesale,
				retail, delivery);
		
		testTransactions = new WarehouseTransactions(TEST_CAPACITY, TEST_DURATION,
				testLedger);
		
	}
	
	/* Create a new warehouse transactions for each test */
	@Before
	@Test
	public void setUp() throws WarehouseException {
	
		//regular cash value
		Integer cash = new Integer(80);
		Integer stock = TEST_CAPACITY;
		
		testingConditionsSetUp(cash, stock);
		
	}
	
	/*Test cases for the constructor WarehouseTransactions.*/
	
	//Test case 1 - Zero values - Capacity - expected result nothing occurs
	@Test
	public void transactionCreationZeroCapacity() throws WarehouseException {
		
		testTransactions = new WarehouseTransactions(ZERO, TEST_DURATION,
				testLedger);
		
	}
	
	//Test case 2 - Zero values - Duration - expected result throws exception
	//not strictly positive
	@Test(expected = WarehouseException.class)
	public void transactionCreationZeroDuration() throws WarehouseException {
		
		testTransactions = new WarehouseTransactions(TEST_CAPACITY, ZERO,
				testLedger);
		
	}
	
	//Test case 3 - Negative values - Capacity - expected result throws exception
	@Test(expected = WarehouseException.class)
	public void transactionCreationNegCapacity() throws WarehouseException {
			
		Integer negativeTest = new Integer(-1);
			
		testTransactions = new WarehouseTransactions(negativeTest, TEST_DURATION,
				testLedger);
			
	}
	
	//Test case 4 - Negative values - Duration - expected result throws exception
	@Test(expected = WarehouseException.class)
	public void transactionCreationNegDuration() throws WarehouseException {
			
		Integer negativeTest = new Integer(-1);
			
		testTransactions = new WarehouseTransactions(TEST_CAPACITY, negativeTest,
				testLedger);
			
	}
	
	/*Test cases for the insolvent method.*/
	
	//Test case 1 - Positive cash value after end of business day - expected result false
	@Test
	public void positiveCashBalance() throws WarehouseException{
		
		//ends business day
		testTransactions.sellStock(ZERO);
		
		assertFalse(testTransactions.insolvent());
		
	}
	
	//Test case 2 - Zero cash value after end of business day - expected result false
	@Test
	public void zeroCashBalance() throws WarehouseException{
		
		//sets cash reserve to zero
		Integer cash = new Integer(0);
		testingConditionsSetUp(cash, TEST_CAPACITY);
		
		//ends business day
		testTransactions.sellStock(ZERO);
		
		assertFalse(testTransactions.insolvent());
		
	}
	
	//Test case 3 - Negative cash value after end of business day - expected result True
	@Test
	public void negativeCashBalance() throws WarehouseException{
		
		//sets cash reserve to negative
		Integer cash = new Integer(-20);
		testingConditionsSetUp(cash, TEST_CAPACITY);

		//ends business day
		testTransactions.sellStock(ZERO);
		
		assertTrue(testTransactions.insolvent());
		
	}
	
	/*Test cases for the orderUnfulfilled method.*/
	
	//Test case 1 - When no order have been placed - Expected false
	@Test
	public void noOrderPlaced(){
		
		assertFalse(testTransactions.orderUnfulfilled());
		
	}
	
	//Test case 2 - When an order has been fulfilled - Expected false
	@Test
	public void orderWasFulfilled() throws WarehouseException{
		
		//fulfilled sales request
		testTransactions.sellStock(ZERO);
		
		assertFalse(testTransactions.orderUnfulfilled());
		
	}
	
	//Test case 3 - When an order has been unfulfilled - Expected true
	@Test
	public void orderHasBeenUnfulfilled() throws WarehouseException{
		
		Integer unfulfilledOrder = new Integer(TEST_CAPACITY + 1);
		
		//an unfulfilled order 
		testTransactions.sellStock(unfulfilledOrder);
		
		assertTrue(testTransactions.orderUnfulfilled());
		
	}
	
	/*Test cases for the jobDone method.*/
	
	//Test case 1 - Initial start up - Expected result false
	@Test
	public void initialStartUp(){
		
		assertFalse(testTransactions.jobDone());
		
	}
	
	//Test case 2 - Job isn't complete - Expected result false
	@Test
	public void jobNotComplete() throws WarehouseException{
		
		//ends business day - enters day 2
		testTransactions.sellStock(ZERO);
		
		assertFalse(testTransactions.jobDone());
		
	}
	
	//Test case 3 - Current day is Equal to requested job duration - Expected result false
	@Test
	public void currentDayEqualsJobDuration() throws WarehouseException{
		
		//Job duration test value is 3, so jump it ahead to day 3
		testTransactions.sellStock(ZERO);
		testTransactions.sellStock(ZERO);
		
		assertFalse(testTransactions.jobDone());
		
	}
	
	//Test case 4 - Current day exceeds job duration - Expected result true
	@Test
	public void currentDayExceedsJobDuration() throws WarehouseException{
		
		//Job duration test value is 3, so jump it ahead to day 4
		testTransactions.sellStock(ZERO);
		testTransactions.sellStock(ZERO);
		testTransactions.sellStock(ZERO);
		
		assertTrue(testTransactions.jobDone());
		
	}
	
	/*Test cases for the sellStock method.*/
	
	//Test case 1 - Negative value todays order - Expected result throws exception
	@Test(expected = WarehouseException.class)
	public void sellStockNegativeOrder() throws WarehouseException{
		
		Integer negativeTestValue = new Integer(-10);
		
		testTransactions.sellStock(negativeTestValue);
		
	}
	
	//Test case 2 - Normal value todays order - Expected result stock decreases
	@Test
	public void sellStockNormalOrderStockReduced() throws WarehouseException{
		
		Integer normalTestValue = new Integer(1);
		
		testTransactions.sellStock(normalTestValue);
		
		assertEquals(new Integer(19),testLedger.inStock());
		
	}
	
	//Test case 3 - Normal value todays order increments current day - Expected result day 2 
	@Test
	public void sellStockNormalOrderDayIncremented() throws WarehouseException{
		
		Integer normalTestValue = new Integer(1);
		
		testTransactions.sellStock(normalTestValue);
		
		assertEquals(new Integer(2),testLedger.currentDay());
		
	}
	
	//Test case 4 - Zero value for todays order - Expected result stock values stays the same
	@Test
	public void sellStockZeroOrder() throws WarehouseException{
		
		testTransactions.sellStock(ZERO);
		
		assertEquals(TEST_CAPACITY,testLedger.inStock());
	
	}
	
	/*Test cases for the restockAndSellStock method.*/
	
	//Test case 1 - Negative order value - Expected result throws an exception
	@Test(expected = WarehouseException.class)
	public void restockNegativeOrder() throws WarehouseException{
		
		Integer negativeValue = new Integer(-1);
		
		testTransactions.restockAndSellStock(negativeValue);
		
	}
	
	//Test case 2 - Normal order value, restocks correctly, Expected result full capacity
	@Test
	public void restockNormalOrder() throws WarehouseException{
		
		//set up an initial stock value lower than capacity
		Integer testStock = new Integer(10);
		Integer testCash = new Integer(80);
		testingConditionsSetUp(testCash, testStock);
		
		testTransactions.restockAndSellStock(ZERO);
		
		assertEquals(TEST_CAPACITY, testLedger.inStock());
		
	}
	
	//Test case 3 - Normal order value, sells stock correctly, Expected result 10
	@Test
	public void restockNormalOrderSellsStock() throws WarehouseException{
		
		Integer testStock = new Integer(10);
		
		testTransactions.restockAndSellStock(testStock);
		
		assertEquals(testStock, testLedger.inStock());
		
	}
	
	//Test case 4 - Normal order value, increments day, Expected result 2
	@Test
	public void restockNormalOrderIncrementsDay() throws WarehouseException{
		
		Integer testStock = new Integer(10);
		Integer expectedDay = new Integer(2);
		
		testTransactions.restockAndSellStock(testStock);
		
		assertEquals(expectedDay, testLedger.currentDay());
		
	}
	
	//Test case 5 - Zero order value, stock levels stay the same
	@Test
	public void restockZeroOrder() throws WarehouseException{
		
		testTransactions.restockAndSellStock(ZERO);
		
		assertEquals(TEST_CAPACITY, testLedger.inStock());
		
	}

}
