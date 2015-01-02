package asgn1Solution;


/**
 * @author Dustin Wilson - n6325157
 * The class WarehouseTransaction implements the Ledger interface, 
 * Manages the daily transactions associated with a warehouse
 */
public class WarehouseTransactions implements Transactions {

	//Class Variables
	
	//Variables that hold the objects initial data
	private WarehouseLedger currentLedger;
	private final Integer CAPACITY, DURATION;
	
	//Keeps track of whether goods were sold or not
	private boolean soldGoods = true;
	
	/**
	 * Constructor 
	 * 
	 * Creates and sets the initial values in order to perform the warehouse
	 * transactions for an warehouse ledger 
	 * 
	 * @param warehouseCapacity - the maximum capacity of the warehouse
	 * @param jobDuration - number of days allocated to the job
	 * @param ledger - the ledger in which records the transactions
	 * @throws WarehouseException - if the warehouse capacity is negative or the job 
	 * duration is not strictly positive (greater than zero)
	 */
	public WarehouseTransactions(Integer warehouseCapacity, Integer jobDuration,
			WarehouseLedger ledger) throws WarehouseException {
		
		checkInitialInput(warehouseCapacity, jobDuration);
		
		currentLedger = ledger;
		CAPACITY = warehouseCapacity;
		DURATION = jobDuration;
	
	}//end WarehouseTransactions

	/**
	 * Checks the initial values for the constructor method to see if any are values
	 * inputed will throw an exception
	 * 
	 * @param warehouseCapacity - the warehouse capacity value to be checked
	 * @param jobDuration - the job duration's value to be checked
	 * @throws WarehouseException - if the warehouse capacity is negative or the job 
	 * duration is not strictly positive (greater than zero)
	 */
	private void checkInitialInput(Integer warehouseCapacity, Integer jobDuration)
			throws WarehouseException {
		
		Integer strictlyPositive = new Integer(1);
		
		String message = "Warehouse capacity cannot have an negative " +
				"value";
		
		checkForNegative(warehouseCapacity, message);
		
		if (jobDuration < strictlyPositive) {
			
			throw new WarehouseException("Job duration is not strictly positive, " +
					"must be greater than zero");
			
		}//end if
	
	}//end checkIntialInput

	/**
	 * Checks to see if the inputed value is a negative, and throws an exception
	 * if that is the case
	 * 
	 * @param inputValue - the inputed value to checked
	 * @param message - the message for the exception if thrown 
	 * @throws WarehouseException - If the inputed value is negative
	 */
	private void checkForNegative(Integer inputValue, String message)
			throws WarehouseException {
		
		Integer minimumValue = new Integer(0);

		if (inputValue < minimumValue) {
			
			throw new WarehouseException(message);
			
		}//end if
	
	}//end checkForNegative

	@Override
	public boolean insolvent() {
		
		Integer bankrupt = new Integer(0);
		
		if(currentLedger.cashAvailable() < bankrupt){
			
			return true;
			
		}//end if
		
		return false;
	
	}//end insolvent

	@Override
	public boolean orderUnfulfilled() {
		
		if (soldGoods == false){
			
			return true;
		
		}//end if
		
		return false;
	
	}//end orderUnfulfilled

	@Override
	public boolean jobDone() {
		
		if (currentLedger.currentDay() > DURATION){
			
			return true;
			
		}//end if
		
		return false;
	
	}//end jobDone

	@Override
	public void sellStock(Integer todaysOrder) throws WarehouseException {
		
		String message = "Today's order cannot be an negative value";
		
		checkForNegative(todaysOrder, message);
		
		//sells todays order and records whether or not the order was fulfilled
		soldGoods = currentLedger.sellItems(todaysOrder);
		
		//increments current day (turns the page)
		currentLedger.nextDay();
		
	}//end sellStock

	@Override
	public void restockAndSellStock(Integer todaysOrder)
			throws WarehouseException {

		String message = "Today's order cannot be an negative value";
		
		checkForNegative(todaysOrder, message);
		
		Integer stockNeeded = CAPACITY - currentLedger.inStock();
		
		//restock the warehouse
		currentLedger.buyItems(stockNeeded);
		
		//sell stock
		sellStock(todaysOrder);
		
	}//end restockAndSellStock

}//end Class WarehouseTransactions
