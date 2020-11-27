package server.Model;

import java.util.ArrayList;

public class CustomerList {

	private ArrayList<Customer> customerList;
	public CustomerList(ArrayList<Customer> customerList) {
		this.customerList = customerList;
	}

	public Customer findCustomerById(int customerId) {
		for (Customer c : customerList) {
			if (c.getCustomerId() == customerId)
				return c;
		}
		return null;
	}

	public ArrayList<Customer> findCustomerById(String lastname) {
		ArrayList<Customer> cList = new ArrayList<Customer>();
		for (Customer c : customerList) {
			if (c.getLastName().equals(lastname))
				cList.add(c);
		}
		return cList;
	}

	public ArrayList<Customer> findCustomerByType(String type) {
		ArrayList<Customer> cList = new ArrayList<Customer>();
		for (Customer c : customerList) {
			if (c.getType().equals(type))
				cList.add(c);
		}
		return cList;
	}

	public void addNewCustomer(int customerId, String firstName, String lastName, String address, String postalCode,
			String phone, String type) {
		Customer newCustomer = null;
		if (type.equals("R"))
			newCustomer = new ResidentialCustomer(customerId, firstName, lastName, address, postalCode, phone, type);
		else if (type.equals("C"))
			newCustomer = new CommercialCustomer(customerId, firstName, lastName, address, postalCode, phone, type);
		customerList.add(newCustomer);
	}

	public void deleteCustomer(int customerId) {
		Customer c = findCustomerById(customerId);
		if (c != null)
			customerList.remove(c);
	}

	public void updateCustomerInfo(int customerId, String firstName, String lastName, String address, String postalCode,
			String phone, String type) {
		Customer c = findCustomerById(customerId);
		c.updateCustomerInfo(customerId, firstName, lastName, address, postalCode, phone, type);
	}

}
