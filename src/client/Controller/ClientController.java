package client.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.json.*;

/**
 * This class serves as a socket manager and receive/send communication Strings
 * from/to ServerController. It uses ClientModel to process some JSON formatted
 * String into objects, and pass objects into each view controller.
 *
 */
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

		String command = "option,1";
		socketOut.println(command);

		DefaultTableModel m = new DefaultTableModel();
		m.setColumnIdentifiers(new String[] { "Item ID", "Item Name", "Item Quantity" });

		try {
			String json = socketIn.readLine();
			JSONArray itemList = new JSONArray(json);
			for (int i = 0; i < itemList.length(); i++) {
				int itemId = itemList.getJSONObject(i).getInt("itemId");
				String itemName = itemList.getJSONObject(i).getString("itemName");
				int itemQuantity = itemList.getJSONObject(i).getInt("itemQuantity");
				String s[] = { Integer.toString(itemId), itemName, Integer.toString(itemQuantity) };
				m.addRow(s);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return m;
	}

	public DefaultTableModel getItemByID(int itemId) {
		String command = "option,2," + itemId;
		socketOut.println(command);

		DefaultTableModel m = new DefaultTableModel();
		m.setColumnIdentifiers(new String[] { "Item ID", "Item Name", "Item Quantity", "Supplier ID" });

		try {
			String json = socketIn.readLine();
			JSONObject item = new JSONObject(json);
			itemId = item.getInt("itemId");
			String itemName = item.getString("itemName");
			int itemQuantity = item.getInt("itemQuantity");
			int supplierId = item.getInt("supplierId");
			String s[] = { Integer.toString(itemId), itemName, Integer.toString(itemQuantity),
					Integer.toString(supplierId) };
			m.addRow(s);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return m;
	}

	public DefaultTableModel getItemByName(String itemName) {
		String command = "option,3," + itemName;
		socketOut.println(command);

		DefaultTableModel m = new DefaultTableModel();
		m.setColumnIdentifiers(new String[] { "Item ID", "Item Name", "Item Quantity", "Supplier ID" });

		try {
			String json = socketIn.readLine();
			JSONObject item = new JSONObject(json);
			int itemId = item.getInt("itemId");
			itemName = item.getString("itemName");
			int itemQuantity = item.getInt("itemQuantity");
			int supplierId = item.getInt("supplierId");
			String s[] = { Integer.toString(itemId), itemName, Integer.toString(itemQuantity),
					Integer.toString(supplierId) };
			m.addRow(s);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return m;
	}

	public String getQuantityById(int itemId) {
		String command = "option,4," + itemId;
		socketOut.println(command);
		String qty = "";
		try {
			qty = socketIn.readLine();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return qty;
	}

	public String getQuantity(String itemName) {
		String command = "option,5," + itemName;
		socketOut.println(command);
		String qty = "";
		try {
			qty = socketIn.readLine();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return qty;
	}

	public String updateItemQuantity(String itemName, int diff) {
		String command = "option,6," + itemName + diff;
		socketOut.println(command);
		String message = "";
		try {
			message = socketIn.readLine();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return message;
	}

	public String addTool(int id, String type, String name, int quantity, double price, int supplierId) {
		String command = "option,7," + id + type + name + quantity + price + supplierId;
		socketOut.println(command);
		String message = "";
		try {
			message = socketIn.readLine();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return message;
	}

	public String removeTool(String itemName) { // by name
		String command = "option,8," + itemName;
		socketOut.println(command);
		String message = "";
		try {
			message = socketIn.readLine();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return message;
	}

	public DefaultTableModel findSupplierById(int supplierId) {
		String command = "option,9," + supplierId;
		socketOut.println(command);

		DefaultTableModel m = new DefaultTableModel();
		m.setColumnIdentifiers(new String[] { "Supplier ID", "Supplier Name", "Supplier Type" });

		try {
			String json = socketIn.readLine();
			JSONObject supplier = new JSONObject(json);
			supplierId = supplier.getInt("supId");
			String supplierName = supplier.getString("supName");
			String supplierType = supplier.getString("supType");
			String s[] = { Integer.toString(supplierId), supplierName, supplierType };
			m.addRow(s);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return m;
	}

	public DefaultTableModel findSupplierByName(String supplierName) {
		String command = "option,10," + supplierName;
		socketOut.println(command);

		DefaultTableModel m = new DefaultTableModel();
		m.setColumnIdentifiers(new String[] { "Supplier ID", "Supplier Name", "Supplier Type" });

		try {
			String json = socketIn.readLine();
			JSONObject supplier = new JSONObject(json);
			int supplierId = supplier.getInt("supId");
			supplierName = supplier.getString("supName");
			String supplierType = supplier.getString("supType");
			String s[] = { Integer.toString(supplierId), supplierName, supplierType };
			m.addRow(s);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return m;
	}

	public DefaultListModel<String[]> findCustomerById(int customerId) {
		String command = "option,11," + customerId;
		socketOut.println(command);
		
		DefaultListModel<String[]> m = new DefaultListModel<String[]>();
		try {
			String json = socketIn.readLine();
			JSONObject customer = new JSONObject(json);
			customerId = customer.getInt("customerId");
			String firstName = customer.getString("firstName");
			String lastName = customer.getString("lastName");
			String address = customer.getString("address");
			String postalCode = customer.getString("postalCode");
			String phone = customer.getString("phone");
			String type = customer.getString("type");
			String s[] = { Integer.toString(customerId), firstName, lastName, address, postalCode, phone, type };
			m.add(0, s);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return m;
	}

	public DefaultListModel<String[]> findCustomerByName(String lastName) {
		String command = "option,12," + lastName;
		socketOut.println(command);
		
		DefaultListModel<String[]> m = new DefaultListModel<String[]>();
		try {
			String json = socketIn.readLine();
			JSONArray customerList = new JSONArray(json);
			for (int i = 0; i < customerList.length(); i++) {
				int customerId = customerList.getJSONObject(i).getInt("customerId");
				String firstName = customerList.getJSONObject(i).getString("firstName");
				lastName = customerList.getJSONObject(i).getString("lastName");
				String address = customerList.getJSONObject(i).getString("address");
				String postalCode = customerList.getJSONObject(i).getString("postalCode");
				String phone = customerList.getJSONObject(i).getString("phone");
				String type = customerList.getJSONObject(i).getString("type");
				String s[] = { Integer.toString(customerId), firstName, lastName, address, postalCode, phone, type };
				m.add(0, s);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return m;
	}

	public DefaultListModel<String[]> findCustomerByType(String customerType) {
		String command = "option,13," + customerType;
		socketOut.println(command);
		
		DefaultListModel<String[]> m = new DefaultListModel<String[]>();
		try {
			String json = socketIn.readLine();
			JSONArray customerList = new JSONArray(json);
			for (int i = 0; i < customerList.length(); i++) {
				int customerId = customerList.getJSONObject(i).getInt("customerId");
				String firstName = customerList.getJSONObject(i).getString("firstName");
				String lastName = customerList.getJSONObject(i).getString("lastName");
				String address = customerList.getJSONObject(i).getString("address");
				String postalCode = customerList.getJSONObject(i).getString("postalCode");
				String phone = customerList.getJSONObject(i).getString("phone");
				String type = customerList.getJSONObject(i).getString("type");
				String s[] = { Integer.toString(customerId), firstName, lastName, address, postalCode, phone, type };
				m.add(0, s);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return m;
	}


	public String addCustomer(int customerId, String firstName, String lastName, String address, String postalCode, String phone, String type) {
		String command = "option,14," + customerId + firstName + lastName + address + postalCode + phone + type;
		socketOut.println(command);
		String message = "";
		try {
			message = socketIn.readLine();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return message;
	}

	public String deleteCustomer(int customerId) { // by customer ID
		String command = "option,15," + customerId;
		socketOut.println(command);
		String message = "";
		try {
			message = socketIn.readLine();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return message;
	}

	public String printOrder() {
		String command = "option,16";
		socketOut.println(command);
		String message = "";
		try {
			message = socketIn.readLine();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return message;
	}
}

//
//	// for customer management - need to think more
//
//	private int id;
//	private String firstName, lastName, address, postalCode, phoneNumber;
//	private char type;
//
//	public ClientController(int id, String firstName, String lastName, String address, String postalCode,
//			String phoneNumber, char type) {
//		this.id = id;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.address = address;
//		this.postalCode = postalCode;
//		this.phoneNumber = phoneNumber;
//		this.type = type;
//	}
//
//	public int getID() {
//		return id;
//	}
//
//	public void setID(int id) {
//		this.id = id;
//	}
//
//	public String getFirstName() {
//		return firstName;
//	}
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//	public String getLastName() {
//		return lastName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//
//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address;
//	}
//
//	public String getPostalCode() {
//		return postalCode;
//	}
//
//	public void setPostalCode(String postalCode) {
//		this.postalCode = postalCode;
//	}
//
//	public String getPhoneNumber() {
//		return phoneNumber;
//	}
//
//	public void setPhoneNumber(String phoneNumber) {
//		this.phoneNumber = phoneNumber;
//	}
//
//	public char getType() {
//		return type;
//	}
//
//	public void setType(char type) {
//		this.type = type;
//	}
//
//	public boolean compare(ClientController temp) {
//		if (this.id == temp.id && this.firstName == temp.firstName && this.lastName == temp.lastName
//				&& this.address == temp.address && this.postalCode == temp.postalCode && this.type == temp.type) {
//			return true;
//		} else
//			return false;
//	}
//
//	public String toString() {
//		return (this.getID() + " ; " + this.getFirstName() + " ; " + this.getLastName() + " ; " + this.getAddress()
//				+ " ; " + this.getPostalCode() + " ; " + this.getPhoneNumber() + " ; " + this.getType());
//	}
//
//}
