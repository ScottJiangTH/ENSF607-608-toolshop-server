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

		}
	}

	class IDListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

		}

	}

	class BrowseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	class NameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}

	class DecreaseItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}

	}

	class PrintOrderListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}

	}
	class AddToolListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}

	}
	class DeleteToolListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}

	}

	public static void main(String[] args) {
		InventoryManagementGUI g = new InventoryManagementGUI(Toolkit.getDefaultToolkit().getScreenSize());
		// ClientController client = new ClientController("localhost", 9009);
		// InventoryGUIController c = new InventoryGUIController(g, client);
		g.pack();
		g.setVisible(true);
	}
}
