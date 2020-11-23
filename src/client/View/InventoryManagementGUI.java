package client.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InventoryManagementGUI extends JFrame {

	private JButton browseTool = new JButton("List Tools");
	private JButton searchName = new JButton("Search by Name");
	private JButton searchID = new JButton("Search by ID");
	private JButton checkQuantity = new JButton("Check Quantity");
	private JButton decreaseQuantity = new JButton("Decrease Quantity");
	private JButton printOrder = new JButton("Print Order");
	private JButton addTool = new JButton("Add Tool");
	private JButton deleteTool = new JButton("Delete Tool");
	private JLabel listLabel = new JLabel("List of Tools");
	private JTable listTable;
	private JScrollPane listScroll;

	public InventoryManagementGUI(Dimension dim) {
		setTitle("Tool Shop Application");
		setBounds(0, 0, dim.width/2, dim.height/2);
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel labels = new JPanel();
		labels.add(listLabel);
		
		JPanel txtArea = new JPanel();
		String[] colName = {"Item ID", "Item Name", "Item Quantity"};
		
		DefaultTableModel tableModel = new DefaultTableModel(colName, 10);
		listTable = new JTable(tableModel);
		listScroll = new JScrollPane(listTable);

		listTable.setEnabled(false);
		listTable.setFillsViewportHeight(true);
		txtArea.add(listScroll);
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(2,3));
		buttons.add(browseTool);
		buttons.add(searchName);
		buttons.add(searchID);
		buttons.add(checkQuantity);
		buttons.add(decreaseQuantity);
		buttons.add(checkQuantity);
		buttons.add(printOrder);
		buttons.add(addTool);
		buttons.add(deleteTool);
		
		getContentPane().add(labels, BorderLayout.NORTH);
		getContentPane().add(new JScrollPane(txtArea), BorderLayout.CENTER);
		getContentPane().add(buttons, BorderLayout.SOUTH);
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

	
	public void addCheckItemListener(ActionListener checkItemListener) {
		checkQuantity.addActionListener(checkItemListener);
	}

	
	public void addDecreaseItemListener(ActionListener decreaseItemListener) {
		decreaseQuantity.addActionListener(decreaseItemListener);
	}

	public void addPrintOrderListener(ActionListener printOrderListener) {
		printOrder.addActionListener(printOrderListener);
	}
	public void addAddToolListener(ActionListener addTool) {
		printOrder.addActionListener(addTool);
	}
	public void deleteToolListener(ActionListener deleteTool) {
		printOrder.addActionListener(deleteTool);
	}

	public void setTableModel(DefaultTableModel m) {
		listTable.setModel(m);
	}

}
