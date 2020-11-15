/**
 * This is the front end of the ToolShop project.
 * The user can select options from menu using keyboard input.
 * @author Yunying Zhang
 * @Since October 10 2020
 */


import java.util.Scanner;

public class FrontEnd {

	//1. List all tools (this must be handled by the proper toString methods in the
	//backend).
	//2. Search for tool by toolName
	//3. Search for tool by toolID
	//4. Check item quantity
	//5. Decrease Item quantity (This is to simulate a sale of the item. Once the item count
	//goes under 40, this function should trigger the creation of an order as shown in
	//lab 2) .
	//6. Quit

		private Shop shop;
		private Scanner scan;
		private Scanner scan1;
			
		FrontEnd() throws Exception{	
			//reading two files and load them into the shop
			
			String itemFile = "items.txt";
			String supplierFile = "suppliers.txt";
			FileManager file = new FileManager(itemFile,supplierFile);
			Inventory inventory = new Inventory(file.generateToolList());
			SupplierList supplierList = file.generateSupList();
			shop = new Shop(inventory,supplierList);
			scan = new Scanner(System.in);
			scan1 = new Scanner(System.in);
		}
		
		/**
		 * prints the menu selection
		 */
		private void printMenuSelection() {
			System.out.println("\nPlease select the action you want to take:\n"
					+ "1.List all tools\n"
					+ "2.Search for tool by toolName\n"
					+ "3.Search for tool by toolID\n"
					+ "4.Check item quantity\n"
					+ "5.Decrease item quantity\n"
					+ "6.Send order and generate a txt file\n"
					+ "7.Quit\n");
		}
		public void menu() throws Exception {
			while (true) {
				printMenuSelection();
				int selection = scan.nextInt();
				switch(selection){
				case 1:
					//calls toString function in inventory
					System.out.println(shop.getInventory().toString());
					break;
				case 2:
					searchByName();
					break;
				case 3:
					searchByID();
					break;
				case 4:
					checkQty();
					break;
				case 5:
					decreaseQty();
					break;
				case 6:
					sendOrder();
					break;
				case 7:
					System.out.println("Exit program.");
					return;
				}
			}
		}
		
	/**
	 * searches the tool by name
	 */
		private void searchByName() {
			
			System.out.println("Please enter the tool name you'd like to search.");
			String toolName = scan1.nextLine();
			Item searchToolName;
			try{
				searchToolName = shop.getInventory().searchByName(toolName);
				System.out.println(searchToolName.toString());
			}
			catch(IndexOutOfBoundsException e) {
				System.out.println("It's not in inventory! Please check your spelling.");
			}
			
		}
		
		/**
		 * searches tool by id
		 */
		private void searchByID() {
			System.out.println("Please enter the tool ID you'd like to search.");
			int toolID = scan.nextInt();
			Item searchToolID;
			try {
				searchToolID = shop.getInventory().searchByID(toolID);
				//toString function should print all necessary functions
				System.out.println(searchToolID.toString());
			}
			catch(IndexOutOfBoundsException e) {
				System.out.println("It's not in inventory!");
			}
			
		}
		
		/**
		 * checks quantity of a tool
		 */
		private void checkQty() {
			System.out.println("Please enter the tool name you'd like to check.");
			String checkTool = scan1.nextLine();
			try {
				int remainingTool = shop.checkQty(checkTool);
				System.out.println("The item has "+remainingTool+" left.");
			}
			catch(IndexOutOfBoundsException e) {
				System.out.println("The tool is not in inventory! Please check your spelling.");
			}
			
		}
		
		/**
		 * decrease quantity of a tool
		 */
		private void decreaseQty() {
			System.out.println("Please enter the tool name you'd like to reduce.");
			String saleTool = scan1.nextLine();
			try {
				System.out.println("This item has "+shop.checkQty(saleTool)+" left. Please enter amount you'd like to reduce:");
				int saleQty = scan.nextInt();
				shop.reduceQty(saleTool, saleQty);
			}
			catch(IndexOutOfBoundsException e) {
				System.out.println("The tool is not in inventory! Please check your spelling.");
			}
		}

		/**
		 * sends the tool order
		 * @throws Exception
		 */
		private void sendOrder() throws Exception {
			shop.sendOrder();
		}
		
	
		public static void main (String[] args) throws Exception {
			FrontEnd newApp = new FrontEnd();
			newApp.menu();
		}
		

	}

	
