package server.Model;

public class InternationalSupplier extends Supplier{

	private float importTax;
	
	public InternationalSupplier(int id, String type, String name, String address, String contactName, float importTax) {
		super(id, type, name, address, contactName);
		this.setImportTax(importTax);
	}

	public float getImportTax() {
		return importTax;
	}

	public void setImportTax(float importTax) {
		this.importTax = importTax;
	}
}
