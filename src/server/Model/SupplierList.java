package server.Model;
	
import java.util.ArrayList;

public class SupplierList {
	
	private ArrayList <Supplier> supplierList;
	
	public SupplierList (ArrayList <Supplier> supplierList) {
		this.supplierList = supplierList;
	}
	public ArrayList<Supplier> getSupplierList (){
		return supplierList;
	}
	public void setSupplierList (ArrayList <Supplier> suppliers){
		supplierList = suppliers;
	}
	public void listAllSuppliers() {
		// TODO Auto-generated method stub
		for (Supplier s: supplierList) {
			System.out.println(s);
		}
		
	}

	public Supplier findSupplierById(int supplierId) {
		for (Supplier s : supplierList) {
			if (s.getSupId() == supplierId)
				return s;
		}
		return null;
	}
	public Supplier findSupplierByName(String supplierName) {
		for (Supplier s : supplierList) {
			if (s.getSupName().equals(supplierName))
				return s;
		}
		return null;
	}
}
