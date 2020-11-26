package client.Controller;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import client.View.CustomerManagementGUI;
import server.Controller.DBController;

public class CustomerGUIController {
	CustomerManagementGUI view;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private JList clientJList;

	public CustomerGUIController(BufferedReader socketIn, PrintWriter socketOut) {
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}

	public void show() {
		view = new CustomerManagementGUI();

		view.addTypeSelectListener(new TypeListener());
		view.addSaveListener(new SaveListener());
		view.addDeleteListener(new DeleteListener());
		view.addClearListener(new ClearListener());
		view.addCustomerIDFilterListener(new ClientIDFilterListener());
		view.addLastNameFilterListener(new LastNameFilterListener());
		view.addTypeFilterListener(new TypeFilterListener());
		view.addSearchListener(new SearchListener());
		view.addClearSearchListener(new ClearSearchListener());
		view.addCustomerListListener(new ClientListListener());

		view.pack();
		view.setVisible(true);
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

	public String addCustomer(int customerId, String firstName, String lastName, String address, String postalCode,
			String phone, String type) {
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

	class TypeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox selectedType = (JComboBox) e.getSource();
			String typeS = (String) selectedType.getSelectedItem();
			if (typeS != "") {
				view.setClientType(typeS.charAt(0));
			}

		}
	}

	class SaveListener implements ActionListener {
		@SuppressWarnings("unused")
		@Override
		public void actionPerformed(ActionEvent e) {

			String message = addCustomer(view.getCustomerID(), view.getFirstName(), view.getLastName(), view.getAddress(),
					view.getPostalCode(), view.getPhoneNumber(), view.getCustomerType());
			if (checkValidInput(newClient) == true) {
				clientModel.addClient(newClient);
				clientList.add(clientList.getSize(), newClient);
				JOptionPane.showMessageDialog(view.getC(), "Successfully added client!");
				view.clearInfo();
			} else {
				JOptionPane.showMessageDialog(view.getC(), "Please enter a valid input!");
			}

		}
	}

	class DeleteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			ClientController tempClient = new ClientController(view.getCustomerID(), view.getFirstName(),
					view.getLastName(), view.getAddress(), view.getPostalCode(), view.getPhoneNumber(),
					view.getCustomerType());

			ClientController check = selectedClient;
			if (tempClient.toString().equals(check.toString())) {

				// TODO: fix null pointer

				// if (tempClient.toString()!=null) {
				// clientModel.deleteClient(check);
				clientList.remove(selectedIndex);
				view.displayErrorMessage("successfully deleted");
			} else {
				view.displayErrorMessage("Cannot delete this client as he's not in our database!!");
			}
		}

	}

	private boolean checkValidInput(ClientController someClient) {
		if (someClient.getPostalCode() != null && someClient.getPhoneNumber() != null
				&& someClient.getFirstName().length() <= 20 && someClient.getLastName().length() <= 20
				&& someClient.getAddress().length() <= 50 && (someClient.getType() + "") != "") {
			return true;
		} else
			return false;
	}

	class ClearListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.clearInfo();

		}
	}

	class SearchListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			searchFunction(view.getSearchKeyword());
			// currently not working
		}
	}

	class ClearSearchListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.clearSearchBar();
			clientJList.setModel(clientList);

		}
	}

	class ClientIDFilterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.setSelection("id");
			searchFilter = view.getSelection();
		}
	}

	class LastNameFilterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.setSelection("lname");
			searchFilter = view.getSelection();
		}
	}

	class TypeFilterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.setSelection("ctype");
			searchFilter = view.getSelection();
		}
	}

	class ClientListListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent evt) {
			// Client selectedClient = (Client) ;
			selectedClient = (ClientController) clientJList.getSelectedValue();
			if (selectedClient != null) {
				selectedIndex = clientJList.getSelectedIndex();
				view.displayInfo(selectedClient);
			} else {
				view.clearInfo();
			}
		}

	}

//	private boolean checkValidInput(Client someClient) {

//	}
	private void searchFunction(String keyword) {

		DefaultListModel filteredItems = new DefaultListModel();
		int counter = 0;
		try {
			for (int i = 0; i < clientList.getSize(); i++) {
				ClientController tempClient = clientList.getElementAt(i);
				if (searchFilter.equals("lname")) {
					if (tempClient.getLastName().toLowerCase().contains(keyword.toLowerCase())) {
						filteredItems.add(counter, clientList.getElementAt(i));
						counter = counter + 1;

					}
				} else if (searchFilter.equals("id")) {
					int searchID = Integer.parseInt(keyword);
					if (tempClient.getID() == searchID) {
						filteredItems.add(counter, clientList.getElementAt(i));
						counter = counter + 1;

					}
				}

				else if (searchFilter.equals("ctype")) {
					if (tempClient.getType() == keyword.charAt(0)) {
						filteredItems.add(counter, clientList.getElementAt(i));
						counter = counter + 1;
					}
				}
			}
			clientJList.setModel(filteredItems);
		}

		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view.getC(), "Please enter a number!");
			view.clearSearchBar();
		}
	}
}
