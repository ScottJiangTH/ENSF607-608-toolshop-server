
	import java.util.ArrayList;	
	
	/**
	 * Keeps track of the tool inventory
	 * @author Yunying Zhang
	 * @Since October 10 2020
	 *
	 */
	public class Inventory {
		private ArrayList<Item>itemList = new ArrayList<Item>();

		private int purchaseQty;
		private Item itemToPurchase;
		private Order order;
	
		
		public Inventory(ArrayList<Item> itemList) {
			this.itemList = itemList;
		}
	
		/**
		 * searches the tool by id
		 * @param toolID: id of the tool
		 * @return the tool found
		 */
		public Item searchByID(int toolID) {
			int index = 0;
			index = searchID(toolID);
			return itemList.get(index);
			
		}
		
		/**
		 * searches the tool by name
		 * @param toolName: name of the tool
		 * @return the matching tool
		 */
		public Item searchByName(String toolName) {

			int index = 0;
			index = searchName(toolName);
			return itemList.get(index);
			
		}
		
		/**
		 * adds a tool to item list
		 * @param newTool: new tool to be added
		 */
		public void addTool(Item newTool) {
			itemList.add(newTool);
			
		}
		
		/**
		 * removes tool from item list
		 * @param toolName: tool that we want to remove
		 */
		public void removeTool(String toolName) {
			int index = 0;
			index = searchName(toolName);
			itemList.remove(index);
			
		}
		
		/**
		 * reduces the quantity of a tool
		 * @param toolName: the tool that we want to reduce quantity
		 * @param qty: quantity to reduce the tool
		 */
		public void reduceQty(String toolName,int qty) {
			int index = 0;
			index = searchName(toolName);
			int remaining = itemList.get(index).getToolQty()-qty;
			itemList.get(index).setToolQty(remaining);		
		}
		
		
		/**
		 * checks quantity of a tool
		 * @param toolName: tool to check quantity
		 * @return the quantity of the tool
		 */
		public int checkQty(String toolName) {
			int i = searchName(toolName);
			if (itemList.get(i).getToolQty()<40) {
				itemToPurchase = itemList.get(i);
				purchaseQty = 50-itemList.get(i).getToolQty();}
			return itemList.get(i).getToolQty();
			
		}
			
		/**
		 * restock the item
		 * @param purchaseToolList
		 */
		public void restock(ArrayList<Item> purchaseToolList) {
			for(Item tool : purchaseToolList) {
				tool.setToolQty(50);
				
			}
		}
		@Override
		public String toString() {
		    String results = "\n";
		    for(Item tool : itemList) {
		        results += tool.toString(); 
		    }
		    return results;
		    
		  }

		
		/**
		 * searches the item by name
		 * @param toolName
		 * @return the index of the tool
		 */
		private int searchName(String toolName) {
			int index=-1;
			//searches for the tool with that name
			for(int i=0;i<itemList.size();i++) {
				if (itemList.get(i).getToolName().equals(toolName)){
					index = i;
				}
			}
			return index;
			
		}
		
		/**
		 * searches for the id of the tool
		 * @param id
		 * @return the index of the tool
		 */
		private int searchID(int id) {
			int index=-1;
			//searches for the tool with that name
			for(int i=0;i<itemList.size();i++) {
				if (itemList.get(i).getToolID()==id){
					index = i;
				}
			}
			return index;
			
		}

		public ArrayList<Item> getItemList() {
			return itemList;
		}

		public void setItemList(ArrayList<Item> itemList) {
			this.itemList = itemList;
		}
		public Item getItemToPurchase() {
			return itemToPurchase;
		}
		public int getPurchaseQty() {
			return purchaseQty;
		}
		
		public String printOrder() {
			// TODO Auto-generated method stub
			return order.toString();
		}

	

	}

	
	
