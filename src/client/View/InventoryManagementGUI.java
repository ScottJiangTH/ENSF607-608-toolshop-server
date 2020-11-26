package client.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InventoryManagementGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton browseTool = new JButton("List Tools");
	private JButton searchID = new JButton("Search by ID");
	private JButton searchName = new JButton("Search by Name");
	private JButton checkQuantitybyId = new JButton("Check Quantity by ID");
	private JButton checkQuantitybyName = new JButton("Check Quantity by Name");
	private JButton UpdateQuantity = new JButton("Update Quantity");
	private JButton addTool = new JButton("Add Tool");
	private JButton deleteTool = new JButton("Delete Tool");
	private JButton checkSupplierByName = new JButton("Check Supplier by Name");
	private JButton checkSupplierById = new JButton("Check Supplier by ID");
	private JButton printOrder = new JButton("Print Order");
	private JLabel listLabel = new JLabel("Table Display");
	private JTable listTable;
	private JScrollPane listScroll;

	public InventoryManagementGUI(Dimension dim) {
		setTitle("Tool Shop Application");
		setBounds(0, 0, dim.width / 2, dim.height / 2);
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel labels = new JPanel();
		labels.add(listLabel);

		JPanel txtArea = new JPanel();
		String[] colName = { "Item ID", "Item Name", "Item Quantity" };

		DefaultTableModel tableModel = new DefaultTableModel(colName, 10);
		listTable = new JTable(tableModel);
		listScroll = new JScrollPane(listTable);

		listTable.setEnabled(false);
		listTable.setFillsViewportHeight(true);
		txtArea.add(listScroll);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 3));
		buttonPanel.add(browseTool);
		buttonPanel.add(searchID);
		buttonPanel.add(searchName);
		buttonPanel.add(checkQuantitybyId);
		buttonPanel.add(checkQuantitybyName);
		buttonPanel.add(UpdateQuantity);
		buttonPanel.add(addTool);
		buttonPanel.add(deleteTool);
		buttonPanel.add(checkSupplierById);
		buttonPanel.add(checkSupplierByName);
		buttonPanel.add(printOrder);

		getContentPane().add(labels, BorderLayout.NORTH);
		getContentPane().add(new JScrollPane(txtArea), BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}


	public void setTableModel(DefaultTableModel m) {
		listTable.setModel(m);
	}
	
	public void addBrowseListener(ActionListener browseListener) {
		browseTool.addActionListener(browseListener);
	}

	public void addIDListener(ActionListener idListener) {
		searchID.addActionListener(idListener);
	}

	public void addNameListener(ActionListener nameListener) {
		searchName.addActionListener(nameListener);
	}
	
	public void addCheckItemListenerById(ActionListener checkItemListenerbyId) {
		checkQuantitybyId.addActionListener(checkItemListenerbyId);
	}
	
	public void addCheckItemListenerByName(ActionListener checkItemListenerbyName) {
		checkQuantitybyName.addActionListener(checkItemListenerbyName);
	}

	public void addUpdateItemListener(ActionListener updateItemListener) {
		UpdateQuantity.addActionListener(updateItemListener);
	}

	public void addToolListener(ActionListener addToolListener) {
		addTool.addActionListener(addToolListener);
	}

	public void addDeleteToolListener(ActionListener deleteToolListener) {
		deleteTool.addActionListener(deleteToolListener);
	}

	public void addSupplierListenerByName(ActionListener checkSupplierListenerByName) {
		checkSupplierByName.addActionListener(checkSupplierListenerByName);
	}

	public void addSupplierListenerById(ActionListener checkSupplierListenerById) {
		checkSupplierById.addActionListener(checkSupplierListenerById);
	}

	public void addPrintOrderListener(ActionListener printOrderListener) {
		printOrder.addActionListener(printOrderListener);
	}
}
