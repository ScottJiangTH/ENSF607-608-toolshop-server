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
		this.view.addCheckItemListenerByName(new CheckItemListenerByName());
		
		this.view.addCheckItemListenerById(new CheckItemListenerById());
		
		this.view.addUpdateItemListener(new UpdateItemListener());
		
		this.view.addToolListener(new AddToolListener());
		this.view.addDeleteToolListener(new DeleteToolListener());
		this.view.addSupplierListenerByName(new CheckSupplierListenerByName());
		this.view.addSupplierListenerById(new CheckSupplierListenerById());
		
		
		this.view.addPrintOrderListener(new PrintOrderListener());
	}

	class CheckItemListenerByName implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("Enter name of Item to check: ");
			String quantity = client.getQuantityByName(name);
			JOptionPane.showMessageDialog(null, quantity,
					"Quantity", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	class CheckItemListenerById implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int Id = Integer.parseInt(JOptionPane.showInputDialog("Enter Id of Item to check: "));
			String quantity = client.getQuantityById(Id);
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

	class UpdateItemListener implements ActionListener {

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
	//classes below needs to modify
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
	
	class CheckSupplierListenerByName implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("Enter name of supplier to add: ");
			client.addSupplier(name);
			view.setTableModel(client.getItemList());
			JOptionPane.showMessageDialog(null, name, "Supplier", JOptionPane.PLAIN_MESSAGE);
			
			
		}

	}
	class CheckSupplierListenerById implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String name = JOptionPane.showInputDialog("Enter name of supplier to delete: ");
			client.removeSupplier(name);
			view.setTableModel(client.getItemList());
			JOptionPane.showMessageDialog(null, name, "Supplier", JOptionPane.PLAIN_MESSAGE);
			
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
