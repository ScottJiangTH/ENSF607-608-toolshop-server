package client.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import client.View.CustomerManagementGUI;

public class CustomerGUIController {
	private CustomerManagementGUI view;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private DefaultTableModel m;
	private String searchType = "";

	public CustomerGUIController(BufferedReader socketIn, PrintWriter socketOut) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
		m = new DefaultTableModel();
		m.setColumnIdentifiers(new String[] { "Customer ID", "Customer First Name", "Customer Last Name", "Address",
				"Postal Code", "Phone", "Type" });
	}

	public void show() {
		view = new CustomerManagementGUI();

		view.addCustomerIDFilterListener(new CustomerIDFilterListener());
		view.addLastNameFilterListener(new LastNameFilterListener());
		view.addTypeFilterListener(new TypeFilterListener());
		view.addSearchListener(new SearchListener());
		view.addClearSearchListener(new ClearSearchListener());
		view.addSaveListener(new SaveListener());
		view.addDeleteListener(new DeleteListener());
		view.addClearListener(new ClearListener());

		view.pack();
		view.setVisible(true);
	}

	public DefaultTableModel findCustomerById(int customerId) {
		String command = "option,11," + customerId;
		socketOut.println(command);

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
			String[] s = { Integer.toString(customerId), firstName, lastName, address, postalCode, phone, type };
			m.addRow(s);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return m;
	}

	public DefaultTableModel findCustomerByName(String lastName) {
		String command = "option,12," + lastName;
		socketOut.println(command);

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
				m.addRow(s);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return m;
	}

	public DefaultTableModel findCustomerByType(String customerType) {
		String command = "option,13," + customerType;
		socketOut.println(command);

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
				m.addRow(s);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return m;
	}

	public String addCustomer(int customerId, String firstName, String lastName, String address, String postalCode,
			String phone, String type) {
		String command = "option,14," + customerId + "," + firstName + "," + lastName + "," + address + "," + postalCode
				+ "," + phone + "," + type;
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

	public String updateCustomerInfo(int customerId, String firstName, String lastName, String address,
			String postalCode, String phone, String type) {
		String command = "option,16," + customerId + firstName + lastName + address + postalCode + phone + type;
		socketOut.println(command);
		String message = "";
		try {
			message = socketIn.readLine();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "The server disconnected");
		}
		return message;
	}

	/////////////////////// FOLLOWING ARE LISTENER CLASSES/////////////////////////

	class CustomerIDFilterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			searchType = "id";
		}
	}

	class LastNameFilterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			searchType = "name";
		}
	}

	class TypeFilterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			searchType = "type";
		}
	}

	class SearchListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (searchType.equals("id")) {
				int customerId = Integer.parseInt(view.getSearchKeyword());
				view.setTableModel(findCustomerById(customerId));

			} else if (searchType.equals("name")) {
				String customerName = view.getSearchKeyword();
				view.setTableModel(findCustomerByName(customerName));

			} else if (searchType.equals("type")) {
				String customerType = view.getSearchKeyword();
				view.setTableModel(findCustomerByName(customerType));

			} else {
				JOptionPane.showMessageDialog(null, "Please select a searching criteria!", "Error Message",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class ClearSearchListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.clearSearchPanel();
		}
	}

	class SaveListener implements ActionListener {
		@SuppressWarnings("unused")
		@Override
		public void actionPerformed(ActionEvent e) {

			String message = addCustomer(view.getCustomerID(), view.getFirstName(), view.getLastName(),
					view.getAddress(), view.getPostalCode(), view.getPhoneNumber(), view.getCustomerType());
			JOptionPane.showMessageDialog(view.getC(), message);
		}
	}

	class DeleteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String message = deleteCustomer(view.getCustomerID());
			JOptionPane.showMessageDialog(view.getC(), message);
		}
	}

	class ClearListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.clearInfo();
		}

	}
}
