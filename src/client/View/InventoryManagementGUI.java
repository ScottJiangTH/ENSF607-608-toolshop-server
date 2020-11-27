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
	private JButton searchID = new JButton("Search Tool by ID");
	private JButton searchName = new JButton("Search Tool by Name");
	private JButton checkQuantitybyId = new JButton("Check Tool Quantity by ID");
	private JButton checkQuantitybyName = new JButton("Check Tool Quantity by Name");
	private JButton UpdateQuantity = new JButton("Update Tool Quantity");
	private JButton addTool = new JButton("Add Tool");
	private JButton deleteTool = new JButton("Delete Tool");
	private JButton checkSupplierByName = new JButton("Check Supplier by Name");
	private JButton checkSupplierById = new JButton("Check Supplier by ID");
	private JButton printDailyOrder = new JButton("Print Order of Today");
	private JButton printHistoryOrder = new JButton("Print History Order");
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
		String[] colName = { "Item ID", "Item Name", "Item Quantity", "Price", "Supplier ID", "Tool Type", "Voltage Rating" };

		DefaultTableModel tableModel = new DefaultTableModel(colName, 10);
		listTable = new JTable(tableModel);
		listScroll = new JScrollPane(listTable);
		
		listTable.setEnabled(false);
		listTable.setFillsViewportHeight(true);
		txtArea.add(listScroll);

		JPanel buttonPanelLeft = new JPanel();
		JPanel buttonPanelRight = new JPanel();
		buttonPanelLeft.setLayout(new GridLayout(6, 1));
		buttonPanelRight.setLayout(new GridLayout(6, 1));
		buttonPanelLeft.add(browseTool);
		buttonPanelLeft.add(searchID);
		buttonPanelLeft.add(searchName);
		buttonPanelLeft.add(checkQuantitybyId);
		buttonPanelLeft.add(checkQuantitybyName);
		buttonPanelLeft.add(UpdateQuantity);
		buttonPanelRight.add(addTool);
		buttonPanelRight.add(deleteTool);
		buttonPanelRight.add(checkSupplierById);
		buttonPanelRight.add(checkSupplierByName);
		buttonPanelRight.add(printDailyOrder);
		buttonPanelRight.add(printHistoryOrder);

		getContentPane().add(labels, BorderLayout.NORTH);
		getContentPane().add(new JScrollPane(txtArea), BorderLayout.CENTER);
		getContentPane().add(buttonPanelLeft, BorderLayout.WEST);
		getContentPane().add(buttonPanelRight, BorderLayout.EAST);

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

	public void addPrintDailyOrderListener(ActionListener printDailyOrderListener) {
		printDailyOrder.addActionListener(printDailyOrderListener);
	}
	
	public void addPrintHistoryOrderListener(ActionListener printHistoryOrderListener) {
		printHistoryOrder.addActionListener(printHistoryOrderListener);
	}
}
