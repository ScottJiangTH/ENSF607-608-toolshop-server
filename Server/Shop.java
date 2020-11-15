package Server;
import java.util.ArrayList;
/**
 * stores information of a shop
 * @author Yunying Zhang
 *@since October 10 2020
 */


public class Shop {

	private Inventory inventory;
	private SupplierList supplierList;
	private ArrayList <OrderLine> orderLineList = new ArrayList <OrderLine>();

	
	public Shop (Inventory inventory, SupplierList supplierList) {
		
		this.inventory = inventory;
		this.supplierList =supplierList;
		
	}
	
	//getters and setters for inventory
	public Inventory getInventory () {
		return inventory;
	}
	public void setInventory (Inventory inventory) {
		this.inventory = inventory;
	}
	
	/**
	 * reduces item quantity
	 * @param toolName: the tool that we want to reduce quantity
	 * @param Qty: number of quantity to reduce
	 */
	public void reduceQty(String toolName,int Qty) {

		inventory.reduceQty(toolName,Qty);

		//add to order line if the quantity is less than 40
		if (inventory.checkQty(toolName)<40&&inventory.checkQty(toolName)>0) {
			addOrderLine();
			System.out.println("Successfully reduced quantity of "+toolName+" to "
					+inventory.searchByName(toolName).getToolQty());
		}
		
		else if(inventory.checkQty(toolName)>=40) {
			System.out.println("Successfully reduced quantity of "+toolName+" to "
					+inventory.searchByName(toolName).getToolQty());
		}
		
		else if(inventory.checkQty(toolName)<0) {
			System.out.println("Not enough in stock! Please enter a smaller number!");
		}
		
		
	}
	/**
	 * adds the item to orderline
	 */
	private void addOrderLine() {
		OrderLine currentOrderLine;
		
		currentOrderLine = new OrderLine(inventory.getItemToPurchase(),
				inventory.getPurchaseQty(),
				supplierList.getSupplier(inventory.getItemToPurchase().getSupID()).getCompanyName());
		orderLineList.add(currentOrderLine);
		System.out.println("Order line added!");
		
}
/**
 * lists all suppliers
 */
	public void listAllSuppliers() {
	 supplierList.showSupplierList();
	}
	
	/**
	 * lists all items
	 */
	public void listAllItems() {
		System.out.println(inventory);
		
	}
	
	/**
	 * checks quantity of an item
	 * @param toolName: tool that we want to check quantity
	 * @return quantity of the tool
	 */
	public int checkQty(String toolName) {
		
		if (inventory.checkQty(toolName)<40) {
			addOrderLine();
		}
		return inventory.checkQty(toolName);
	}
	
	public String printOrder() {
		// TODO Auto-generated method stub
		
		return inventory.printOrder();
	}

/**
 * generates the order
 * @throws Exception
 */
	public void sendOrder() throws Exception {
		Order order = new Order();
		if (orderLineList.size()!=0) {
			order.generateOrder(orderLineList);
		}
		else {
			System.out.println("There's no order to be generated.");
		}
	}
	
}

