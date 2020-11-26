package client.View;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;

public class CustomerManagementGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	Container c;
	JPanel selections = new JPanel();
	private String selection;
	JPanel information = new JPanel();
	JPanel leftPanel = new JPanel();

	private JTextField customerIDField = new JTextField();
	private JTextField firstNameField = new JTextField();
	private JTextField lastNameField = new JTextField();
	private JTextField addressField = new JTextField();
	private JTextField postalCodeField = new JTextField();
	private JTextField phoneField = new JTextField();
	private JComboBox typeList;
	private char customerType;
	private JButton save = new JButton("Save");
	private JButton delete = new JButton("Delete");
	private JButton clear = new JButton("Clear");
	private JButton searchButton = new JButton("Search");
	private JButton clearButton = new JButton("Clear Search");
	private JTextField searchBar = new JTextField();
	private JRadioButton customerIDFilter = new JRadioButton("Customer ID");
	private JRadioButton lastNameFilter = new JRadioButton("Last Name");
	private JRadioButton typeFilter = new JRadioButton("Customer Type");
	private JScrollPane searchResults = new JScrollPane();
//	private JTextArea results = new JTextArea();
	private JList customerJList;

	
	
	public CustomerManagementGUI() {
		super("Customer Management System");
		c = getContentPane();
		//		setting up information menu
		TitledBorder informationBorder = new TitledBorder("Customer Information");
	    informationBorder.setTitleJustification(TitledBorder.CENTER);
	    informationBorder.setTitlePosition(TitledBorder.TOP);
	    information.setBorder(informationBorder);
	    
		information.setLayout(new GridLayout(0,1));
		
		JPanel cmg = new JPanel();
		cmg.setLayout(new FlowLayout());
		cmg.add(new JLabel("Customer ID"));
		customerIDField.setPreferredSize(new Dimension(50,20));
	
		cmg.add(customerIDField);
	
		
		JPanel first = new JPanel();
		first.setLayout(new FlowLayout());
		first.add(new JLabel("First Name"));
		firstNameField.setPreferredSize(new Dimension(50,20));
		first.add(firstNameField);
	
		
		JPanel last = new JPanel();
		last.setLayout(new FlowLayout());
		last.add(new JLabel("Last Name"));
		lastNameField.setPreferredSize(new Dimension(50,20));
		last.add(lastNameField);

		
		JPanel addressPanel= new JPanel();
		addressPanel.setLayout(new FlowLayout());
		addressPanel.add(new JLabel("Address"));
		addressField.setPreferredSize(new Dimension(100,20));
		addressPanel.add(addressField);

		
		JPanel postalCodePanel = new JPanel();
		postalCodePanel.setLayout(new FlowLayout());
		postalCodePanel.add(new JLabel("Postal Code"));
		postalCodeField.setPreferredSize(new Dimension(80,20));
		postalCodePanel.add(postalCodeField);

		
		JPanel phonePanel = new JPanel();
		phonePanel.setLayout(new FlowLayout());
		phonePanel.add(new JLabel("Phone Number"));
		phoneField.setPreferredSize(new Dimension(80,20));
		phonePanel.add(phoneField);
	
		
		JPanel typeSelection = new JPanel();
		String [] types = {"","R","C"};
		typeSelection.setLayout(new FlowLayout());
		typeSelection.add(new JLabel("Type"));
		typeList = new JComboBox(types);
		//setting type to be selected type
		typeSelection.add(typeList);
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());

		buttons.add(save);
		buttons.add(delete);
		buttons.add(clear);
		
		
		
		
		information.add(cmg);
		information.add(first);
		information.add(last);
		information.add(addressPanel);
		information.add(postalCodePanel);
		information.add(phonePanel);
		information.add(typeSelection);
		information.add(buttons);
		
		
		
		
		//setting up selection menu
		
		TitledBorder border = new TitledBorder("Search Customers");
	    border.setTitleJustification(TitledBorder.CENTER);
	    border.setTitlePosition(TitledBorder.TOP);
	    selections.setBorder(border);
	    
		selections.setLayout(new GridLayout(0,1));
		selections.add(new JLabel("Search type of customers to be performed:"));

		customerIDFilter.setMnemonic(KeyEvent.VK_B);
       	lastNameFilter.setMnemonic(KeyEvent.VK_B);
       	typeFilter.setMnemonic(KeyEvent.VK_B);

  
        
        ButtonGroup group = new ButtonGroup();
        group.add(customerIDFilter);
        group.add(lastNameFilter);
        group.add(typeFilter);
        
        
        //adding a search bar
        
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        searchBar.setPreferredSize(new Dimension(200,20));

        searchPanel.add(searchBar);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);
        
        
        
        selections.add(customerIDFilter);
        selections.add(lastNameFilter);
        selections.add(typeFilter);
        selections.add(new JLabel("Enter the search parameter below :"));
        selections.add(searchPanel);

        //creating new scroll pane
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));

		searchResults = new JScrollPane(customerJList);
        searchResults.setLayout(new ScrollPaneLayout());
//        results.setPreferredSize(new Dimension(200,200));
//        results.add(searchResults);   
//        results.setText(clientJList.);
        
        //add title to the results pane

        TitledBorder resultsBorder = new TitledBorder("Search Results");
        resultsBorder.setTitleJustification(TitledBorder.CENTER);
        resultsBorder.setTitlePosition(TitledBorder.TOP);
//	    results.setBorder(resultsBorder);
//	    results.setEditable(false);
        searchResults.setBorder(resultsBorder);
        leftPanel.add(selections);
        //leftPanel.add(searchResults);
        
//        leftPanel.add(results);
  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c.setLayout(new GridLayout(0,2));
        c.add(leftPanel);
//        System.out.println(clientJList.getModel().getElementAt(0).toString()+"aaaaa what the fucc");
        //        c.add(leftPanel);
        c.add(information);
		
	}
	
	//adding listeners to buttons
		public void addTypeSelectListener(ActionListener listenForType) {
			typeList.addActionListener(listenForType);
		}
		public void addSaveListener(ActionListener listenForSaveListener) {
			save.addActionListener(listenForSaveListener);
		}
		public void addDeleteListener(ActionListener listenForDeleteListener) {
			delete.addActionListener(listenForDeleteListener);
		}
		public void addClearListener(ActionListener listenForClearListener) {
			clear.addActionListener(listenForClearListener);
		}
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
			clearButton.addActionListener(listenForClearSearch);
		}
		public void addCustomerListListener(ListSelectionListener listenerForJList) {
			//clientJList.addListSelectionListener(listenerForJList);
		}

		//getters and setters
		public String getSearchKeyword() {
			return getTextFromTextBox(searchBar);
		}	
		public String getSelection() {
			return selection;
		}
		public void setSelection(String selection) {
			this.selection = selection;
		}
		public int getCustomerID() {
			String id = getTextFromTextBox(customerIDField);
			return Integer.parseInt(id);
		}
		public String getFirstName() {
			return getTextFromTextBox(firstNameField);
		}
		public String getLastName() {
			return getTextFromTextBox(lastNameField);
		}
		public String getAddress() {
			return getTextFromTextBox(addressField);
		}
		public String getPostalCode() {
			return getTextFromTextBox(postalCodeField);
		}
		public String getPhoneNumber() {
			return getTextFromTextBox(phoneField);
		}
		public char getCustomerType() {

			return customerType;
		}
		public JList getCustomerJList() {
			return customerJList;
		}
		public void setCustomerType(char newType) {
			customerType = newType;
		}
		public Container getC() {
			return c;
		}
		private int getIntFromTextBox(JTextField customerIDField2) {
			return Integer.parseInt(customerIDField2.getText());
		}
		private String getTextFromTextBox(JTextField customerNameField) {
			return customerNameField.getText();
		}
		
		
		//helper functions
		public void displayErrorMessage(String errorMessage) {
			JOptionPane.showMessageDialog(this, errorMessage);
		}
//		public void displayInfo(ClientController tempClient) {
//			clientIDField.setText(tempClient.getID()+"");
//			firstNameField.setText(tempClient.getFirstName());
//			lastNameField.setText(tempClient.getLastName());
//			addressField.setText(tempClient.getAddress());
//			postalCodeField.setText(tempClient.getPostalCode());
//			phoneField.setText(tempClient.getPhoneNumber());
//			typeList.setSelectedItem(tempClient.getType()+"");
//		}
		public void clearInfo() {
			customerIDField.setText("0");
			firstNameField.setText("");
			lastNameField.setText("");
			addressField.setText("");
			postalCodeField.setText("");
			phoneField.setText("");
			typeList.setSelectedItem("");
		}
		public void clearSearchBar() {
			searchBar.setText("");
		}
		public void setClientJList(JList someList) {
			this.customerJList = someList;
			searchResults = new JScrollPane(customerJList);
			leftPanel.add(searchResults);
		}



		
		
	
	
	
}
