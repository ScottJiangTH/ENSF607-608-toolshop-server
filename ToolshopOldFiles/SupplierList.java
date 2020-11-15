	
import java.util.ArrayList;
/**
 * stores information of supplier list
 * @author Yunying Zhang
 * @since October 10 2020
 *
 */

public class SupplierList {
	private ArrayList<Supplier> supplierList;
	
	
	public SupplierList(ArrayList<Supplier> supplierList) {
		this.supplierList = supplierList;
	}
	
	/**
	 * adds supplier to supplier list
	 * @param newSupplier
	 */
	public void addSupplier(Supplier newSupplier) {
		supplierList.add(newSupplier);
	}
	
	/**
	 * gets the supplier
	 * @param supID: id of the supplier
	 * @return the supplier
	 */
	public Supplier getSupplier(int supID) {
		Supplier supplier = new Supplier();
		for (Supplier sup : supplierList) {
			if(supID==sup.getSupID()) {
				supplier = sup;
			}
		}
		return supplier;
	}
	
	/**
	 * shows the supplier list
	 */
	public void showSupplierList(){
		for(int i = 0; i<supplierList.size();i++) {
			System.out.println(supplierList.get(i).getSupID()+";"+
					supplierList.get(i).getCompanyName()+";"+
					supplierList.get(i).getAddress()+";"+
					supplierList.get(i).getSaleRep());
		}
	}
}

