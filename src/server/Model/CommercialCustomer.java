package server.Model;

public class CommercialCustomer extends Customer {

	public CommercialCustomer(int customerId, String firstName, String lastName, String address, String postalCode,
			String phone, String type) {
		super(customerId, firstName, lastName, address, postalCode, phone, type);
	}

}
