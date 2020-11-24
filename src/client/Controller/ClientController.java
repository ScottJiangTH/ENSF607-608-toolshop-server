package client.Controller;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ClientController {

	Socket socket;
	BufferedReader socketIn;
	PrintWriter socketOut;

	public ClientController(String serverName, int portNum) {
		try {
			socket = new Socket(serverName, portNum);
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketOut = new PrintWriter(socket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Unable find Server!");
			System.exit(1);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Unable to connect to Server!");
			System.exit(1);
		}
	}

	public DefaultTableModel getItemList() {
		String input;
		String[] item;

		DefaultTableModel m = new DefaultTableModel();
		m.setColumnIdentifiers(new String[] { "Item ID", "Item Type", "Item Name","Item Description","Item Price","Item Quantity","Supplier Id","Already Ordered" });
		socketOut.println("option,1");
		try {
			while ((input = socketIn.readLine())!= null) {
				item = input.split(",");
			//	item = input;
				m.addRow(item);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return m;
	}
	
//	public void getItemList() {
//		socketOut.println("option,1");
//	}

	public String getItemByID(int ID) {
		String input = "", item = "";
		socketOut.println("option,2");
		socketOut.println(ID);
		try {
			while ((input = socketIn.readLine())!= null) {
				item += input;
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}

		return item;
	}

	public String getItemByName(String name) {
		String input = "";
		socketOut.println("option,3");
		socketOut.println(name);
		String item = "";

		try {
			while ((input = socketIn.readLine())!= null) {
				item += input;
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}

		return item;
	}
//need to add: search quantity by id
	public String getQuantityById(int ID) {
		String input = "", quantity = "";
		socketOut.println("option,4");
		socketOut.println(ID);
		try {
			while ((input = socketIn.readLine())!= null) {
				quantity += input;
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return quantity;
	}
	
	public String getQuantityByName(String name) {
		String input = "", quantity = "";
		socketOut.println("option,5");
		socketOut.println(name);
		try {
			while ((input = socketIn.readLine())!= null) {
				quantity += input;
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return quantity;
	}

	public String decreaseItemQuantity(String name) {
		String input = "", message = "";
		socketOut.println("option,6");
		socketOut.println(name);

		try {
			while ((input = socketIn.readLine())!= null) {
				message += input;
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}

		return message;
	}

	public String getPrintOrder() {
		String input = "", order = "";
		socketOut.println("1");
		//need to read from json file passed
//		try {
//			while ((input = socketIn.readLine()).charAt(0) != '\0') {
//				order += input + "\n";
//			}
//		} catch (IOException e) {
//			JOptionPane.showMessageDialog(null, "The server disconnected");
//		}
		return order;
	}

	public void removeTool(String name) {
		// TODO Auto-generated method stub
		
	}

	public void addTool(String name) {
		// TODO Auto-generated method stub
		
	}
	
	//for customer management - need to think more
	
	private int id;
	private String firstName,lastName,address,postalCode,phoneNumber;
	private char type;
	
	public ClientController(int id,String firstName,String lastName,String address,String postalCode,String phoneNumber,char type) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
		this.type = type;
	}
	
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	
	public boolean compare(ClientController temp) {
		if (this.id==temp.id && this.firstName==temp.firstName && this.lastName==temp.lastName && this.address==temp.address && this.postalCode==temp.postalCode && this.type==temp.type) {
			return true;
		}
		else return false;
	}
	public String toString() {
		return (this.getID()+" ; "+this.getFirstName()+" ; "+this.getLastName()+" ; "+this.getAddress()+" ; "+this.getPostalCode()+" ; "+this.getPhoneNumber()+" ; "+this.getType());
	}

	public void addSupplier(String name) {
		// TODO Auto-generated method stub
		//need to add supplier to DB
		
		socketOut.println("option,6");
		
	}
	
	public void removeSupplier(String name) {
		// TODO Auto-generated method stub
		//need to add supplier to DB
		
	}
	
	
}
