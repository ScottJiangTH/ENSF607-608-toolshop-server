package server.Model;
/**
 * Stores information of each tool
 * @author Yunying Zhang
 * @Since October 10 2020
 *
 */


public class Item {
	
	
	 private int toolID;
	 private String toolName;
	 private int toolQty;
	 private double toolPrice;
	 private int supID;

	public Item(int toolID,String toolName,int toolQty,double toolPrice,
			int supID) {
		 this.toolID = toolID;
		 this.toolName = toolName;
		 this.toolQty = toolQty;
		 this.toolPrice = toolPrice;
		 this.supID = supID;
	 }
	@Override
	public String toString() {
		return "Tool name: "+ toolName+"\n"+"Quantity: "+ toolQty+"\n"+"Tool ID:"+ toolID
				+"\n"+"Tool Price:"+ toolPrice +"\n\n";
	}	
	 
	/**
	 * decreases the item quantity by one
	 * @return true if number of item is greater than 0
	 */
	public boolean decreaseItemQuantity () {
		if (toolQty > 0) {
			toolQty--;
		    return true;	
		}
		else
			return false;	
	}
	
	

//getters and setters
	public int getToolQty() {
		return toolQty;
	}
	public void setToolQty(int toolQty) {
		this.toolQty = toolQty;
	}
	public int getToolID() {
		return toolID;
	}
	public void setToolID(int toolID) {
		this.toolID = toolID;
	}
	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	public double getToolPrice() {
		return toolPrice;
	}
	public void setToolPrice(double toolPrice) {
		this.toolPrice = toolPrice;
	}
	public int getSupID() {
		return supID;
	}
	public void setSupID(int supID) {
		this.supID = supID;
	}

}
