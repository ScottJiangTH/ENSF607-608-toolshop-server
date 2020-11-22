package exercise;

public class Tool {

	private int id, quantity, supplierId;
	private String name;
	private double price;
	
	public Tool(int id, String name, int quantity, double price, int supplierId) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.supplierId = supplierId;
	}

	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public int getQuantity() {
		// TODO Auto-generated method stub
		return quantity;
	}

	public double getPrice() {
		// TODO Auto-generated method stub
		return price;
	}

	public int getSupplierID() {
		// TODO Auto-generated method stub
		return supplierId;
	}

}
