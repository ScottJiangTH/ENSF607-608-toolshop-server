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

}
