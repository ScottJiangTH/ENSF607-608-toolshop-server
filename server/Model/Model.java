package server.Model;
//Model of the Server
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;



public class Model implements Runnable{
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private Socket socket;
	
	private Shop theShop;
	private Scanner scan;
	private Scanner scan1;
	private ArrayList<Supplier> suppliers;

	public Model(Socket socket) {
		
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
	
	public void writeToClient(String s){
		socketOut.print(s);
	}
	
	private void decreaseItem(String name) {
		socketOut.print(theShop.decreaseItem(name));

	}
	private void checkItemQuantity(String name) {
		socketOut.print(theShop.getItemQuantity(name));
	}
	private String getItemName() {
		String name = "";
		try {
			name = socketIn.readLine();
		} catch (IOException e) {
			System.err.println("Unable to get name from " + socket.getInetAddress());
		}
		return name;

	}
	
	private int getItemID() {
		int id = 0;
		try {
			id = Integer.parseInt(socketIn.readLine());
		} catch (IOException e) {
			System.err.println("Unable to get id from " + socket.getInetAddress());
		}
		
		return id;
	}
	
	private void searchForItemById(int id) {
		writeToClient(theShop.getItem(id));
	}

	private void searchForItemByName(String name) {
		writeToClient(theShop.getItem(name));
	}
	
	private void listAllItems(){
		//writeToClient(theShop.listAllItems());
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
		
//			switch(selection){
//			case 1:
//				//calls toString function in inventory
//				//System.out.println(shop.getInventory().toString());
//				break;
//			case 2:
//				searchByName();
//				break;
//			case 3:
//				searchByID();
//				break;
//			case 4:
//				checkQty();
//				break;
//			case 5:
//				decreaseQty();
//				break;
//			case 6:
//				try {
//					sendOrder();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				break;
//			case 7:
//				System.out.println("Exit program.");
//				return;
//			}
		
	}
		
	}
}

