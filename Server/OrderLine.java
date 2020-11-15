package Server;
/**
 * Stores information of an orderline.
 * @author Yunying Zhang
 * @Since October 10 2020
 *
 */

public class OrderLine {
	private Item toolDescription;
	private int orderQty;
	private String supInfo;
	
	public OrderLine(Item toolDescription,int orderQty, String supInfo) {
		this.toolDescription = toolDescription;
		this.orderQty = orderQty;
		this.supInfo = supInfo;
	}
	public OrderLine (Item item, int quantity) {
		toolDescription = item;
		setOrderQty(quantity); 
		
	}
	
	//getters and setters
	
	public Item getToolDescription() {
		return toolDescription;
	}
	public void setToolDescription(Item toolDescription) {
		this.toolDescription = toolDescription;
	}

	public int getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(int orderQty) {
		this.orderQty=orderQty;
	}

	public String getSupInfo() {
		return supInfo;
	}
	@Override
	public String toString() {
		return "Item Description: "+ toolDescription.getToolName()+"\nAmount Orderd: "+orderQty+"\nSupplier: " +supInfo+"\n\n";
	}

	
}


