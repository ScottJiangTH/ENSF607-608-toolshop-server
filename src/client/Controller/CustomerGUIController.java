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



public class CustomerGUIController {
	CustomerManagementGUI view;

	ClientController client;
	
	private JList clientJList = new JList();
	private String searchFilter;
	private DBController clientModel;

	private ClientController selectedClient;
	private DefaultListModel<ClientController> clientList;
	private int selectedIndex;
	private int newID;

	public CustomerGUIController(CustomerManagementGUI view, ClientController client) throws SQLException {
		this.view = view;
		this.client = client;
		
		//clientList = clientModel.getClientList();
	//	this.clientJList = new JList(clientList);
		this.clientJList = clientModel.getClientJList();
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
	class TypeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox selectedType = (JComboBox)e.getSource();
			String typeS = (String)selectedType.getSelectedItem();
			if (typeS != "") {
			//client.setClientType(typeS.charAt(0));
		}
		
	}
	}
	class SaveListener implements ActionListener{
		@SuppressWarnings("unused")
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}
	class DeleteListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}
	class ClearListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
		
		}
	}
	class SearchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
		
		}
	}	
	class ClearSearchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}
	class ClientIDFilterListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}
	class LastNameFilterListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {

			
		}
	}
	class TypeFilterListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}
	class ClientListListener implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent evt) {

		}


	}

//	private boolean checkValidInput(Client someClient) {

//	}
	private void searchFunction(String keyword) {

	//	return newID;
	}


	
	
	
	
	public static void main(String[] args) throws SQLException {
		
		CustomerManagementGUI g = new CustomerManagementGUI();
		ClientController client = new ClientController("localhost", 9009);
		CustomerGUIController c = new CustomerGUIController(g,client);
		g.pack();
		g.setVisible(true);
		
	


	}
}
