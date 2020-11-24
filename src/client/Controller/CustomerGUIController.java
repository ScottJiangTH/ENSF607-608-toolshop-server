package client.Controller;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import client.View.CustomerManagementGUI;
import server.Controller.DBController;
import client.Model.*;

public class CustomerGUIController {
	CustomerManagementGUI view;

	ClientController client;

	private JList clientJList = new JList();
	private String searchFilter;
	private ClientModel clientModel;
	private ClientController selectedClient;

	private ClientController clientController;
	private DefaultListModel<ClientController> clientList;
	private int selectedIndex;
	private int newID;

	public CustomerGUIController(CustomerManagementGUI view, ClientModel clientModel, ClientController clientController)
			throws SQLException {
		this.view = view;
		this.clientModel = clientModel;
		this.clientController = clientController;

		clientList = clientModel.getClientList();
		this.clientJList = new JList(clientList);
		view.setClientJList(clientJList);
		view.addTypeSelectListener(new TypeListener());
		view.addSaveListener(new SaveListener());
		view.addDeleteListener(new DeleteListener());
		view.addClearListener(new ClearListener());
		view.addClientIDFilterListener(new ClientIDFilterListener());
		view.addLastNameFilterListener(new LastNameFilterListener());
		view.addTypeFilterListener(new TypeFilterListener());
		view.addSearchListener(new SearchListener());
		view.addClearSearchListener(new ClearSearchListener());
		view.addClientListListener(new ClientListListener());

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

		}
	}

	class DeleteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			ClientController tempClient = new ClientController(view.getClientID(), view.getFirstName(),
					view.getLastName(), view.getAddress(), view.getPostalCode(), view.getPhoneNumber(),
					view.getClientType());

			ClientController check = selectedClient;
			//if (tempClient.toString().equals(check.toString())) {
		
			//TODO: fix null pointer
			
			if (tempClient.toString()!=null) {
				//clientModel.deleteClient(check);
				clientList.remove(selectedIndex);
				view.displayErrorMessage("successfully deleted");
			} else {
				view.displayErrorMessage("Cannot delete this client as he's not in our database!!");
			}
		}
		

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
		//currently not working 
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
		//Client selectedClient = (Client) ;
		selectedClient = (ClientController) clientJList.getSelectedValue();
		if(selectedClient !=null) {
			selectedIndex = clientJList.getSelectedIndex();
			view.displayInfo(selectedClient);
		}
		else {
			view.clearInfo();
		}
	}

}

//	private boolean checkValidInput(Client someClient) {

//	}
	private void searchFunction(String keyword) {

		DefaultListModel filteredItems=new DefaultListModel();
		int counter = 0;
		try {
			for(int i=0;i<clientList.getSize();i++) {
				ClientController tempClient = clientList.getElementAt(i);
				if (searchFilter.equals("lname")) {
					if (tempClient.getLastName().toLowerCase().contains(keyword.toLowerCase())) {
						filteredItems.add(counter, clientList.getElementAt(i));
						counter = counter + 1;
						
						}
				}
				else if(searchFilter.equals("id")) {
						int searchID = Integer.parseInt(keyword);
						if (tempClient.getID()==searchID) {
							filteredItems.add(counter, clientList.getElementAt(i));
							counter = counter + 1;
							
							}
						}
					
				
				else if(searchFilter.equals("ctype")) {
					if (tempClient.getType()==keyword.charAt(0)) {
							filteredItems.add(counter, clientList.getElementAt(i));
							counter = counter + 1;
							}
						}
		}
				clientJList.setModel(filteredItems);}

		catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(view.getC(), "Please enter a number!");
			view.clearSearchBar();
			}	}

	public static void main(String[] args) throws SQLException {

		ClientModel theModel = new ClientModel();
		theModel.populateList();

		CustomerManagementGUI g = new CustomerManagementGUI();
		ClientController client = new ClientController("localhost", 9009);
		CustomerGUIController c = new CustomerGUIController(g, theModel, client);
		g.pack();
		g.setVisible(true);

	}
}
