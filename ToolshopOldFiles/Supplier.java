
/**
 * stores information of a supplier
 * @author Yunying Zhang
 * @Since October 10 2020
 *
 */

public class Supplier {
	private int supID;
	private String companyName;
	private String address;
	private String saleRep;
	
	public Supplier() {}
	public Supplier(int supID,String companyName,String address,String saleRep) {
		this.supID = supID;
		this.companyName = companyName;
		this.address = address;
		this.saleRep = saleRep;
	}
	
//getters and setters
	public int getSupID() {
		return supID;
	}

	public void setSupID(int supID) {
		this.supID = supID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSaleRep() {
		return saleRep;
	}

	public void setSaleRep(String saleRep) {
		this.saleRep = saleRep;
	}

	
}
