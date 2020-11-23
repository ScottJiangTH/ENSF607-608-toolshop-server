package client.Controller;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import client.View.InventoryManagementGUI;

public class InventoryGUIController {

	InventoryManagementGUI view;

	ClientController client;

	public InventoryGUIController(InventoryManagementGUI view, ClientController client) {
		this.view = view;
		this.client = client;

		this.view.addBrowseListener(new BrowseListener());
		this.view.addIDListener(new IDListener());
		this.view.addNameListener(new NameListener());
		this.view.addCheckItemListener(new CheckItemListener());
		this.view.addDecreaseItemListener(new DecreaseItemListener());
		this.view.addPrintOrderListener(new PrintOrderListener());
	}

	class CheckItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("Enter name of Item to check: ");
			String quantity = client.getQuantity(name);
			JOptionPane.showMessageDialog(null, quantity,
					"Quantity", JOptionPane.PLAIN_MESSAGE);
		}
	}

	class IDListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				int id = Integer.parseInt(JOptionPane.showInputDialog("Enter ID: "));
				String i = client.getItemByID(id);
				JOptionPane.showMessageDialog(null, i, "Item", JOptionPane.PLAIN_MESSAGE);
			}catch(NumberFormatException n){
				JOptionPane.showMessageDialog(null, "Please enter valid number!",
						"Error Message", JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	class BrowseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.setTableModel(client.getItemList());
		}
	}

	class NameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("Enter name of Item: ");
			String i = client.getItemByName(name);
			JOptionPane.showMessageDialog(null, i, "Item", JOptionPane.PLAIN_MESSAGE);
			
		}
	}

	class DecreaseItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("Enter name of Item: ");
			String value = client.decreaseItemQuantity(name);
			view.setTableModel(client.getItemList());
			JOptionPane.showMessageDialog(null, value, "Item", JOptionPane.PLAIN_MESSAGE);
		}

	}

	class PrintOrderListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String order = client.getPrintOrder();
			JOptionPane.showMessageDialog(null, order, "Order for Today",
					JOptionPane.PLAIN_MESSAGE);
		}

	}
	class AddToolListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("Enter name of Item to add: ");
			client.addTool(name);
			view.setTableModel(client.getItemList());
			JOptionPane.showMessageDialog(null, name, "Item", JOptionPane.PLAIN_MESSAGE);
			
			
		}

	}
	class DeleteToolListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String name = JOptionPane.showInputDialog("Enter name of Item to delete: ");
			client.removeTool(name);
			view.setTableModel(client.getItemList());
			JOptionPane.showMessageDialog(null, name, "Item", JOptionPane.PLAIN_MESSAGE);
			
		}

	}

	public static void main(String[] args) {
		InventoryManagementGUI g = new InventoryManagementGUI(Toolkit.getDefaultToolkit().getScreenSize());
		ClientController client = new ClientController("localhost", 9009);
		InventoryGUIController c = new InventoryGUIController(g, client);
		g.pack();
		g.setVisible(true);
	}
}
