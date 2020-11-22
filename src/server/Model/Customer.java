package server.Model;

public class Customer {

	private String customerId;
	private String firstName;
	private String lastName;
	private String address;
	private String postalCode;
	private String phone;
	private String type;

	public Customer(String customerId, String firstName, String lastName, String address, String postalCode, String phone, String type) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.postalCode = postalCode;
		this.phone = phone;
		this.type = type;
	}
	
	public String getCustomerId() {
		// TODO Auto-generated method stub
		return customerId;
	}

	public String getFirstName() {
		// TODO Auto-generated method stub
		return firstName;
	}

	public String getLastName() {
		// TODO Auto-generated method stub
		return lastName;
	}

	public String getAddress() {
		// TODO Auto-generated method stub
		return address;
	}

	public String getPostalCode() {
		// TODO Auto-generated method stub
		return postalCode;
	}

	public String getPhone() {
		// TODO Auto-generated method stub
		return phone;
	}

	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

}
