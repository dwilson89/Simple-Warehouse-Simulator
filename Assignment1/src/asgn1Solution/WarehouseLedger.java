package asgn1Solution;

import java.util.ArrayList;


/**
 * @author Dustin Wilson - n6325157
 * The class WarehouseLedger implements the Ledger interface, 
 * As like a ledger in the real world, this class keeps track
 * of and records day by day stock levels and cash reserves 
 */
public class WarehouseLedger implements Ledger {

	//Class Variables.
	
	//variables that holds the previous days data
	private ArrayList<Integer> stockValues = new ArrayList<Integer>();
	private ArrayList<Integer> cashReserves = new ArrayList<Integer>();
	
	//creation of variables with unchangeable set values.
	private final Integer WHOLESALE_COST, RETAIL_PRICE, DELIVERY_PRICE;
	
	//creation of variables that hold current data
	private Integer currentCashReserve, currentStock;
	private Integer currentDay;
	
	
	/**
	 * Constructor
	 * 
	 * Creates a warehouse ledger and sets the initial values for newly
	 * created object from the parameters supplied.
	 * 
	 * @param intialStock - The initial stock value
	 * @param intialCash - The initial cash value
	 * @param wholesaleCostPerItem - The wholesale cost per each item
	 * @param retailPricePerItem - The retail price per each item
	 * @param deliveryCharge - The delivery charge for an order
	 * @throws WarehouseException - if the stock level, wholesale cost, 
	 * retail price or delivery charge are negative, or if the wholesale cost 
	 * is greater than the retail price 
	 */
	public WarehouseLedger (Integer initialStock, Integer initialCash,
			Integer wholesaleCostPerItem, Integer retailPricePerItem,
			Integer deliveryCharge) throws WarehouseException {
		
		//checks for exceptions and throws them
		checkForInitialExceptions(initialStock, wholesaleCostPerItem,
				retailPricePerItem, deliveryCharge);
		
		//set the values of the class variables.	
		currentStock = initialStock;
		currentCashReserve = initialCash;
		WHOLESALE_COST = wholesaleCostPerItem;
		RETAIL_PRICE = retailPricePerItem;
		DELIVERY_PRICE = deliveryCharge;
		
		//sets current day equal to 1 when ledger is constructed.
		currentDay = new Integer(1);
	
	}//end WarehouseLedger 

	
	/**
	 * Checks the initial values (parameters) used by the constructor WarehouseLedger
	 * for values which incur an exception to be thrown
	 * 
	 * @param intialStock - The initial stock value
	 * @param wholesaleCostPerItem - The wholesale cost per each item
	 * @param retailPricePerItem - The retail price per each item
	 * @param deliveryCharge - The delivery charge for an order
	 * @throws WarehouseException - if the stock level, wholesale cost, 
	 * retail price or delivery charge are negative, or if the wholesale cost 
	 * is greater than the retail price 
	 */
	private void checkForInitialExceptions(Integer initialStock,
			Integer wholesaleCostPerItem, Integer retailPricePerItem,
			Integer deliveryCharge) throws WarehouseException {
		
		//creation of string messages for notification of errors
		String messageOne = "Intial stock cannot be a negative value";
		String messageTwo = "Wholesale cost per item cannot be a negative value";
		String messageThree = "Retail price per item cannot be a negative value";
		String messageFour = "Delivery charge cannot be a negative value";
		
		//checks for negative Exceptions
		checkForNegativeExceptions(initialStock, messageOne);
		checkForNegativeExceptions(wholesaleCostPerItem, messageTwo);
		checkForNegativeExceptions(retailPricePerItem, messageThree);
		checkForNegativeExceptions(deliveryCharge, messageFour);
		
		//throws exception if the wholesale cost is greater than the retail price
		if (wholesaleCostPerItem > retailPricePerItem) {
			
			throw new WarehouseException("Wholesale cost may not exceed retail price");
			
		}//end else if
	
	}//end checkInitialExceptions
	
	/**
	 * Checks if the value inputed is negative and if true throws an exception 
	 * with the message supplied from the parameters 
	 * 
	 * @param input - inputed value to be checked
	 * @param message - message to be printed out in case of exception
	 * @throws WarehouseException - If the value inputed is negative
	 */
	private void checkForNegativeExceptions(Integer input, String message) 
			throws WarehouseException {
		
		//creation of variable to check against
		Integer borderValue = new Integer(0);
		
		if (input < borderValue) {
			
			throw new WarehouseException(message);
			
		}//end if
		
	}//end checkForNegativeExceptions

	@Override
	public void nextDay() {

		//variable for increments of days
		Integer addDay = new Integer(1);
		
		//stores the previous days data
		cashReserves.add(currentCashReserve);
		stockValues.add(currentStock);
		
		//increments the current day (turns the page)
		currentDay += addDay;
		
	}//close nextDay

	@Override
	public void buyItems(Integer required) throws WarehouseException {
		
		String message = "Required stock value cannot be an negative value";
		checkForNegativeExceptions(required, message);

		//updates cash reserve
		Integer totalCost = (WHOLESALE_COST * required) + DELIVERY_PRICE;
		currentCashReserve -= totalCost;
		
		//sets stock back to maximum capacity
		currentStock += required; 
		
	}//end buyItems
	
	@Override
	public boolean sellItems(Integer requested) throws WarehouseException {
	
		String message = "Requested stock value cannot be an negative value";
		checkForNegativeExceptions(requested, message);
		
		//checks if there is enough current stock to meet requested amount
		if (requested <= currentStock){
			
			currentStock -= requested;
			currentCashReserve += (requested * RETAIL_PRICE);
			
			return true;
			
		}//end if
		
		return false;
		
	}//end sellItems 

	@Override
	public Integer currentDay() {
		
		return currentDay;
	
	}//end currentDay

	/**
	 * Checks to see if the day inputed is a correct value, throws an exception if
	 * the given day is less than 1 or greater than the current day.
	 * 
	 * @param day - the day value that is to be checked
	 * @throws WarehouseException - if the given day is less than 1 or greater than the current day
	 */
	private void checkDayException(Integer day) throws WarehouseException {
		
		Integer initialDay = new Integer(1);
		
		if (day < initialDay){
			
			throw new WarehouseException("Invalid input for day, " +
					"the day inputted cannot be less than 1");
			
		} else if (day > currentDay) {
			
			throw new WarehouseException("Invalid input for day, " +
					"the day inputted cannot be greater than the current day");
			
		}//end else if
		
	}//end checkDayException
	
	@Override
	public Integer cashAvailable() {
		
		return currentCashReserve;
		
	}//end cashAvailable 

	@Override
	public Integer cashAvailable(Integer day) throws WarehouseException {
		
		//checks for the exception
		checkDayException(day);
		
		//variable for reduction of index place
		Integer arrayCorrection = new Integer(1); 
	
		if (day.equals(currentDay)){
			
			return currentCashReserve;
			
		}//end if
		
		return cashReserves.get(day - arrayCorrection);
		
	}//end cashAvailable(parameter)

	@Override
	public Integer inStock() {
		
		return currentStock;
	
	}//end inStock 

	@Override
	public Integer inStock(Integer day) throws WarehouseException {
		
		//variable for reduction of index place
		Integer arrayCorrection = new Integer(1); 
		
		//check for the exception
		checkDayException(day);
		
		if (day.equals(currentDay)){
			
			return currentStock;
		
		}//end if
		
		return stockValues.get(day - arrayCorrection);
		
	}//end inStock(parameter)
	
	@Override
	public String toString(){
		
		String day = currentDay.toString();
		String cash = currentCashReserve.toString();
		String items= currentStock.toString();
		
		String currentState = String.format("Day %s: Cash reserve = $%s; Items " +
				"in stock = %s\n", day, cash, items);
		
		return currentState;
		
	}//end toString
	
}//end Class WarehouseLedger
