package client.Controller;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import client.View.InventoryManagementGUI;

public class InventoryGUIController {

	InventoryManagementGUI view;
	private BufferedReader socketIn;
	private PrintWriter socketOut;

	public InventoryGUIController(BufferedReader socketIn, PrintWriter socketOut) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}

	public void show() {
		view = new InventoryManagementGUI(Toolkit.getDefaultToolkit().getScreenSize());

		view.addBrowseListener(new BrowseListener());
		view.addIDListener(new IDListener());
		view.addNameListener(new NameListener());
		view.addCheckItemListenerById(new CheckItemListenerById());
		view.addCheckItemListenerByName(new CheckItemListenerByName());
		view.addUpdateItemListener(new UpdateItemListener());
		view.addToolListener(new AddToolListener());
		view.addDeleteToolListener(new DeleteToolListener());
		view.addSupplierListenerById(new CheckSupplierListenerById());
		view.addSupplierListenerByName(new CheckSupplierListenerByName());
		view.addPrintDailyOrderListener(new PrintDailyOrderListener());
		view.addPrintHistoryOrderListener(new PrintHistoryOrderListener());

		view.pack();
		view.setVisible(true);
	}

	private DefaultTableModel parseJsonArrayOfItems() {
		DefaultTableModel m = new DefaultTableModel();
		m.setColumnIdentifiers(new String[] { "Item ID", "Item Name", "Item Quantity", "Price", "Supplier ID",
				"Tool Type", "Voltage Rating" });

		try {
			String json = socketIn.readLine();
			JSONArray itemList = new JSONArray(json);
			for (int i = 0; i < itemList.length(); i++) {
				int itemId = itemList.getJSONObject(i).getInt("itemId");
				String itemName = itemList.getJSONObject(i).getString("itemName");
				int itemQuantity = itemList.getJSONObject(i).getInt("itemQuantity");
				double price = itemList.getJSONObject(i).getDouble("itemPrice");
				int supplierId = itemList.getJSONObject(i).getInt("supplierId");
				String type = itemList.getJSONObject(i).getString("itemType");
				String powerType = "";
				if (itemList.getJSONObject(i).has("powerType"))
					powerType = itemList.getJSONObject(i).getString("powerType");
				String s[] = { Integer.toString(itemId), itemName, Integer.toString(itemQuantity),
						Double.toString(price), Integer.toString(supplierId), type, powerType };
				m.addRow(s);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return m;
	}

	private DefaultTableModel parseJsonObjectOfItems() {
		DefaultTableModel m = new DefaultTableModel();
		m.setColumnIdentifiers(new String[] { "Item ID", "Item Name", "Item Quantity", "Price", "Supplier ID",
				"Tool Type", "Voltage Rating" });

		try {
			String json = socketIn.readLine();
			JSONObject item = new JSONObject(json);
			int itemId = item.getInt("itemId");
			String itemName = item.getString("itemName");
			int itemQuantity = item.getInt("itemQuantity");
			double price = item.getDouble("itemPrice");
			int supplierId = item.getInt("supplierId");
			String type = item.getString("itemType");
			String powerType = "";
			if (item.has("powerType"))
				powerType = item.getString("powerType");
			String s[] = { Integer.toString(itemId), itemName, Integer.toString(itemQuantity), Double.toString(price),
					Integer.toString(supplierId), type, powerType };
			m.addRow(s);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return m;
	}

	private DefaultTableModel parseJsonObjectOfSupplier() {
		DefaultTableModel m = new DefaultTableModel();
		m.setColumnIdentifiers(new String[] { "Supplier ID", "Supplier Name", "Address", "Sales Contact",
				"Supplier Type", "Import Tax" });

		try {
			String json = socketIn.readLine();
			JSONObject supplier = new JSONObject(json);
			int supplierId = supplier.getInt("supId");
			String supplierName = supplier.getString("supName");
			String address = supplier.getString("supAddress");
			String contact = supplier.getString("supContactName");
			String supplierType = supplier.getString("supType");
			float importTax = 0;
			if (supplier.has("importTax"))
				importTax = supplier.getFloat("importTax");
			String s[] = { Integer.toString(supplierId), supplierName, address, contact, supplierType,
					Float.toString(importTax) };
			m.addRow(s);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return m;
	}

	private DefaultTableModel parseJsonObjectOfOrders() {
		DefaultTableModel m = new DefaultTableModel();
		m.setColumnIdentifiers(
				new String[] { "Order ID", "Order Date", "Orderline-Item ID", "Orderline-Order Quantity" });
		try {
			String json = socketIn.readLine();
			JSONObject order = new JSONObject(json);
			int orderId = order.getInt("orderId");
			JSONObject dateObj = order.getJSONObject("orderDate");
			String date = dateObj.getInt("year") + "-" + dateObj.getInt("monthValue") + "-"
					+ dateObj.getInt("dayOfMonth");
			JSONArray orderlines = order.getJSONArray("orderLines");
			for (int i = 0; i < orderlines.length(); i++) {
				int itemId = orderlines.getJSONObject(i).getJSONObject("theItem").getInt("itemId");
				int orderQuantity = orderlines.getJSONObject(i).getInt("orderQuantity");
				String s[] = { Integer.toString(orderId), date, Integer.toString(itemId),
						Integer.toString(orderQuantity) };
				m.addRow(s);
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return m;
	}

	private String parseStringMessage() {
		String message = "";
		try {
			message = socketIn.readLine();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return message;
	}

	public DefaultTableModel getItemList() {
		String command = "option,1";
		socketOut.println(command);
		return parseJsonArrayOfItems();
	}

	public DefaultTableModel getItemById(int itemId) {
		String command = "option,2," + itemId;
		socketOut.println(command);
		return parseJsonObjectOfItems();
	}

	public DefaultTableModel getItemByName(String itemName) {
		String command = "option,3," + itemName;
		socketOut.println(command);
		return parseJsonObjectOfItems();
	}

	public String getQuantityById(int itemId) {
		String command = "option,4," + itemId;
		socketOut.println(command);
		return parseStringMessage();
	}

	public String getQuantityByName(String itemName) {
		String command = "option,5," + itemName;
		socketOut.println(command);
		return parseStringMessage();
	}

	public String updateItemQuantity(String itemName, int diff) {
		String command = "option,6," + itemName + "," + diff;
		socketOut.println(command);
		return parseStringMessage();
	}

	public String addTool(int id, String type, String name, int quantity, double price, int supplierId) {
		String command = "option,7," + id + "," + type + "," + name + "," + quantity + "," + price + "," + supplierId;
		socketOut.println(command);
		return parseStringMessage();
	}

	public String deleteTool(String itemName) { // by name
		String command = "option,8," + itemName;
		socketOut.println(command);
		return parseStringMessage();
	}

	public DefaultTableModel findSupplierById(int supplierId) {
		String command = "option,9," + supplierId;
		socketOut.println(command);
		return parseJsonObjectOfSupplier();
	}

	public DefaultTableModel findSupplierByName(String supplierName) {
		String command = "option,10," + supplierName;
		socketOut.println(command);
		return parseJsonObjectOfSupplier();
	}

	public DefaultTableModel printOrder(LocalDate date) {
		String command = "option,17," + date.toString();
		socketOut.println(command);
		return parseJsonObjectOfOrders();
	}

///////////////////////FOLLOWING ARE LISTENER CLASSES/////////////////////////

	class BrowseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.setTableModel(getItemList());
		}
	}

	class IDListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int id = 0;
			try {
				id = Integer.parseInt(JOptionPane.showInputDialog("Enter ID: "));
			} catch (NumberFormatException n) {
				JOptionPane.showMessageDialog(null, "Please input a 4 digit ID!", "Error Message",
						JOptionPane.ERROR_MESSAGE);
			}
			view.setTableModel(getItemById(id));
		}
	}

	class NameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("Enter name of Item: ");
			view.setTableModel(getItemByName(name));
		}
	}

	class CheckItemListenerById implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int Id = Integer.parseInt(JOptionPane.showInputDialog("Enter Id of Item to check: "));
			String quantity = getQuantityById(Id);
			JOptionPane.showMessageDialog(null, quantity, "Quantity of the tool", JOptionPane.PLAIN_MESSAGE);
		}
	}

	class CheckItemListenerByName implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("Enter name of Item to check: ");
			String quantity = getQuantityByName(name);
			JOptionPane.showMessageDialog(null, quantity, "Quantity of the tool", JOptionPane.PLAIN_MESSAGE);
		}
	}

	class UpdateItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("Enter name of Item: ");
			int diff = 0;
			try {
				diff = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity change: "));
			} catch (NumberFormatException n) {
				JOptionPane.showMessageDialog(null, "Please input a positive or negative integer!", "Error Message",
						JOptionPane.ERROR_MESSAGE);
			}
			String message = updateItemQuantity(name, diff);
			view.setTableModel(getItemList());
			JOptionPane.showMessageDialog(null, message, "Update tool quantity", JOptionPane.PLAIN_MESSAGE);
		}
	}

	class AddToolListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int id = 0, quantity = 0, supplierId = 0;
			double price = 0.0;

			String name = JOptionPane.showInputDialog("Enter name of the tool: ");
			try {
				id = Integer.parseInt(JOptionPane.showInputDialog("Enter tool ID: "));
				quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter tool quantity: "));
				price = Double.parseDouble(JOptionPane.showInputDialog("Enter price: "));
				supplierId = Integer.parseInt(JOptionPane.showInputDialog("Enter supplier ID: "));
			} catch (NumberFormatException n) {
				JOptionPane.showMessageDialog(null, "Please input valid number!", "Error Message",
						JOptionPane.ERROR_MESSAGE);
			}
			String type = JOptionPane.showInputDialog("Enter type of the tool: ");
			String message = addTool(id, type, name, quantity, price, supplierId);
			view.setTableModel(getItemList());
			JOptionPane.showMessageDialog(null, message, "Adding new tool status", JOptionPane.PLAIN_MESSAGE);
		}
	}

	class DeleteToolListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String name = JOptionPane.showInputDialog("Enter name of the tool: ");
			String message = deleteTool(name);
			view.setTableModel(getItemList());
			JOptionPane.showMessageDialog(null, message, "Deleting tool status", JOptionPane.PLAIN_MESSAGE);
		}
	}

	class CheckSupplierListenerById implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int id = 0;
			try {
				id = Integer.parseInt(JOptionPane.showInputDialog("Enter supplier ID: "));
			} catch (NumberFormatException n) {
				JOptionPane.showMessageDialog(null, "Please input a four digit ID!", "Error Message",
						JOptionPane.ERROR_MESSAGE);
			}
			view.setTableModel(findSupplierById(id));
		}
	}

	class CheckSupplierListenerByName implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("Enter supplier name: ");
			view.setTableModel(findSupplierByName(name));
		}
	}

	class PrintDailyOrderListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.setTableModel(printOrder(LocalDate.now()));
		}
	}

	class PrintHistoryOrderListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String dateString = JOptionPane.showInputDialog("Enter a date in format YYYY-MM-DD: ");
			LocalDate date = LocalDate.parse(dateString);
			if (date != null)
				view.setTableModel(printOrder(date));
			else
				JOptionPane.showMessageDialog(null, "Wrong format, input in YYYY-MM-DD!", "Error Message",
						JOptionPane.ERROR_MESSAGE);
		}
	}
}
