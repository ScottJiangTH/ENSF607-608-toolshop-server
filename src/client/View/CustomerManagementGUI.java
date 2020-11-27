package client.View;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class CustomerManagementGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private Container c = getContentPane();;

	private JPanel leftPanel = new JPanel();
	private JPanel informationPanel = new JPanel();

	private JRadioButton customerIDFilter = new JRadioButton("Customer ID");
	private JRadioButton lastNameFilter = new JRadioButton("Last Name");
	private JRadioButton typeFilter = new JRadioButton("Customer Type");
	private JTextField searchBar = new JTextField();
	private JScrollPane searchResults = new JScrollPane();
	private JTable listTable;
	
	private JTextField customerIDField = new JTextField();
	private JTextField firstNameField = new JTextField();
	private JTextField lastNameField = new JTextField();
	private JTextField addressField = new JTextField();
	private JTextField postalCodeField = new JTextField();
	private JTextField phoneField = new JTextField();
	private JComboBox<String> typeCBox;
	private String customerType;

	private JButton searchButton = new JButton("Search");
	private JButton clearSearchButton = new JButton("Clear Search");
	private JButton saveButton = new JButton("Save");
	private JButton deleteButton = new JButton("Delete");
	private JButton clearButton = new JButton("Clear");

	public CustomerManagementGUI() {
		super("Customer Management System");

		// setting up left Panel for Search Customers
		TitledBorder leftPanelTitle = new TitledBorder("Searching Customers");
		leftPanelTitle.setTitleJustification(TitledBorder.CENTER);
		leftPanelTitle.setTitlePosition(TitledBorder.TOP);
		leftPanel.setBorder(leftPanelTitle);
		leftPanel.setLayout(new GridLayout(0, 1));
		leftPanel.add(new JLabel("Perform searching on:"));

		customerIDFilter.setMnemonic(KeyEvent.VK_B);
		lastNameFilter.setMnemonic(KeyEvent.VK_B);
		typeFilter.setMnemonic(KeyEvent.VK_B);
		leftPanel.add(customerIDFilter);
		leftPanel.add(lastNameFilter);
		leftPanel.add(typeFilter);
		
		JPanel searchBarPanel = new JPanel(); // Setting up Search Bar
		searchBarPanel.setLayout(new FlowLayout());
		searchBar.setPreferredSize(new Dimension(200, 20));
		searchBarPanel.add(searchBar);
		searchBarPanel.add(searchButton);
		searchBarPanel.add(clearSearchButton);
		leftPanel.add(new JLabel("Enter the search parameter below :"));
		leftPanel.add(searchBarPanel);

//		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Setting up search result display panel
		String[] colName = { "Customer ID", "Customer First Name", "Customer Last Name", "Address", "Postal Code", "Phone", "Type" };
		DefaultTableModel tableModel = new DefaultTableModel(colName, 10);
		listTable = new JTable(tableModel);
		searchResults = new JScrollPane(listTable);
		listTable.setEnabled(false);
		listTable.setFillsViewportHeight(true);
		searchResults.setLayout(new ScrollPaneLayout());
		TitledBorder resultsPanelTitle = new TitledBorder("Search Results");// add title to the results pane
		resultsPanelTitle.setTitleJustification(TitledBorder.CENTER);
		resultsPanelTitle.setTitlePosition(TitledBorder.TOP);
		searchResults.setBorder(resultsPanelTitle);

		// setting up information menu
		TitledBorder informationBorder = new TitledBorder("Customer Information");
		informationBorder.setTitleJustification(TitledBorder.CENTER);
		informationBorder.setTitlePosition(TitledBorder.TOP);
		informationPanel.setBorder(informationBorder);
		informationPanel.setLayout(new GridLayout(0, 1));

		JPanel cid = new JPanel();
		cid.setLayout(new FlowLayout());
		cid.add(new JLabel("Customer ID"));
		customerIDField.setPreferredSize(new Dimension(50, 20));
		cid.add(customerIDField);

		JPanel first = new JPanel();
		first.setLayout(new FlowLayout());
		first.add(new JLabel("First Name"));
		firstNameField.setPreferredSize(new Dimension(50, 20));
		first.add(firstNameField);

		JPanel last = new JPanel();
		last.setLayout(new FlowLayout());
		last.add(new JLabel("Last Name"));
		lastNameField.setPreferredSize(new Dimension(50, 20));
		last.add(lastNameField);

		JPanel addressPanel = new JPanel();
		addressPanel.setLayout(new FlowLayout());
		addressPanel.add(new JLabel("Address"));
		addressField.setPreferredSize(new Dimension(100, 20));
		addressPanel.add(addressField);

		JPanel postalCodePanel = new JPanel();
		postalCodePanel.setLayout(new FlowLayout());
		postalCodePanel.add(new JLabel("Postal Code"));
		postalCodeField.setPreferredSize(new Dimension(80, 20));
		postalCodePanel.add(postalCodeField);

		JPanel phonePanel = new JPanel();
		phonePanel.setLayout(new FlowLayout());
		phonePanel.add(new JLabel("Phone Number"));
		phoneField.setPreferredSize(new Dimension(80, 20));
		phonePanel.add(phoneField);

		JPanel typePanel = new JPanel();
		typePanel.setLayout(new FlowLayout());
		typePanel.add(new JLabel("Type"));
		String[] types = { "", "R", "C" };
		typeCBox = new JComboBox<String>(types);
		typePanel.add(typeCBox);

		JPanel buttonPanelRight = new JPanel();
		buttonPanelRight.setLayout(new FlowLayout());
		buttonPanelRight.add(saveButton);
		buttonPanelRight.add(deleteButton);
		buttonPanelRight.add(clearButton);

		informationPanel.add(cid);
		informationPanel.add(first);
		informationPanel.add(last);
		informationPanel.add(addressPanel);
		informationPanel.add(postalCodePanel);
		informationPanel.add(phonePanel);
		informationPanel.add(typePanel);
		informationPanel.add(buttonPanelRight);

		ButtonGroup group = new ButtonGroup();
		group.add(customerIDFilter);
		group.add(lastNameFilter);
		group.add(typeFilter);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.setLayout(new GridLayout(0, 2));
		c.add(leftPanel);
		c.add(informationPanel);
		c.add(searchResults);
	}

	public void clearInfo() {
		customerIDField.setText("0");
		firstNameField.setText("");
		lastNameField.setText("");
		addressField.setText("");
		postalCodeField.setText("");
		phoneField.setText("");
		typeCBox.setSelectedItem("");
	}

	public void clearSearchPanel() {
		searchBar.setText("");
		customerIDFilter.setSelected(false);
		lastNameFilter.setSelected(false);
		typeFilter.setSelected(false);
		listTable.setModel(new DefaultTableModel());
	}
	
	// getters and setters
	private String getTextFromTextBox(JTextField customerNameField) {return customerNameField.getText();}
	
	public String getSearchKeyword() {return getTextFromTextBox(searchBar);}

	public int getCustomerID() {
		String id = getTextFromTextBox(customerIDField);
		return Integer.parseInt(id);
	}
	
	public Container getC() {return c;}
	
	public String getFirstName() {return getTextFromTextBox(firstNameField);}

	public String getLastName() {return getTextFromTextBox(lastNameField);}

	public String getAddress() {return getTextFromTextBox(addressField);}

	public String getPostalCode() {return getTextFromTextBox(postalCodeField);}

	public String getPhoneNumber() {return getTextFromTextBox(phoneField);}

	public String getCustomerType() {return typeCBox.getName();}

	public JTable getCustomerJList() {return listTable;}
		
	public void setTableModel(DefaultTableModel m) {
		listTable.setModel(m);
	}
	
	// adding listeners to buttons
	public void addCustomerIDFilterListener(ActionListener listenForIDilter) {
		customerIDFilter.addActionListener(listenForIDilter);
		}
	public void addLastNameFilterListener(ActionListener listenForLNFilter) {
		lastNameFilter.addActionListener(listenForLNFilter);
	}
	public void addTypeFilterListener(ActionListener listenForTypeFilter) {
		typeFilter.addActionListener(listenForTypeFilter);
	}
	public void addSearchListener(ActionListener listenForSearch) {
		searchButton.addActionListener(listenForSearch);
	}

	public void addClearSearchListener(ActionListener listenForClearSearch) {
		clearSearchButton.addActionListener(listenForClearSearch);
	}

	public void addSaveListener(ActionListener listenForSaveListener) {
		saveButton.addActionListener(listenForSaveListener);
	}

	public void addDeleteListener(ActionListener listenForDeleteListener) {
		deleteButton.addActionListener(listenForDeleteListener);
	}

	public void addClearListener(ActionListener listenForClearListener) {
		clearButton.addActionListener(listenForClearListener);
	}
}
