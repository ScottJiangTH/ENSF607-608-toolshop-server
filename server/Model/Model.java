package server.Model;
//Model of the Server
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import server.Model.Model.Item;
import server.Model.Model.Shop;

public class Model implements Runnable{
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private Socket socket;
	
	private Shop shop;
	private Scanner scan;
	private Scanner scan1;
	private ArrayList<Supplier> suppliers;

	
	
	Model(Socket socket) {
		
			suppliers = new ArrayList<Supplier>();
		    //need to get suppliers and inventory from database
			scan = new Scanner(System.in);
			this.socket = socket;
			
			try {
				socketOut = new PrintWriter(socket.getOutputStream(), true);
				socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				System.err.println("Error creating stream");
			}
		}
	
	private void searchByName() {
		
		socketOut.println("Please enter the tool name you'd like to search.");
		String toolName = scan1.nextLine();
		Item searchToolName;
		try{
			searchToolName = shop.getInventory().searchByName(toolName);
			socketOut.println(searchToolName.toString());
		}
		catch(IndexOutOfBoundsException e) {
			socketOut.println("It's not in inventory! Please check your spelling.");
		}
		
	}
	
	/**
	 * searches tool by id
	 */
	private void searchByID() {
		socketOut.println("Please enter the tool ID you'd like to search.");
		int toolID = scan.nextInt();
		Item searchToolID;
		try {
			searchToolID = shop.getInventory().searchByID(toolID);
			//toString function should print all necessary functions
			socketOut.println(searchToolID.toString());
		}
		catch(IndexOutOfBoundsException e) {
			socketOut.println("It's not in inventory!");
		}
		
	}
	
	/**
	 * checks quantity of a tool
	 */
	private void checkQty() {
		socketOut.println("Please enter the tool name you'd like to check.");
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
		socketOut.println("Please enter the tool name you'd like to reduce.");
		String saleTool = scan1.nextLine();
		try {
			System.out.println("This item has "+shop.checkQty(saleTool)+" left. Please enter amount you'd like to reduce:");
			int saleQty = scan.nextInt();
			shop.reduceQty(saleTool, saleQty);
		}
		catch(IndexOutOfBoundsException e) {
			socketOut.println("The tool is not in inventory! Please check your spelling.");
		}
	}

	/**
	 * sends the tool order
	 * @throws Exception
	 */
	private void sendOrder() throws Exception {
		shop.sendOrder();
	}
	
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			int selection = 0;
		
			try {
				selection = Integer.parseInt(socketIn.readLine());
			} catch (NumberFormatException e) {
				System.err.println("Invalid option by user!");
			} catch (IOException e) {
				System.err.println(socket.getInetAddress() + " disconnected!");	
					try {
						socket.close();
						return;
					} catch (IOException e1) {
						System.err.println("Unable to close socket for " + socket.getInetAddress());
					}
			}
			
			System.out.println(socket.getInetAddress() + " entered: " + selection);
		
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
				try {
					sendOrder();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 7:
				System.out.println("Exit program.");
				return;
			}
		
	}
		
	}
}

