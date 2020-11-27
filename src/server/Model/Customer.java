package server.Model;

public abstract class Customer {

	private int customerId;
	private String firstName;
	private String lastName;
	private String address;
	private String postalCode;
	private String phone;
	private String type;

	public Customer(int customerId, String firstName, String lastName, String address, String postalCode, String phone, String type) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.postalCode = postalCode;
		this.phone = phone;
		this.type = type;
	}
	
	public int getCustomerId() {
		return customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getPhone() {
		return phone;
	}

	public String getType() {
		return type;
	}

	public void updateCustomerInfo(int customerId, String firstName, String lastName, String address,
			String postalCode, String phone, String type) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.postalCode = postalCode;
		this.phone = phone;
		this.type = type;
	}

}
