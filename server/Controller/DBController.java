package server.Controller;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * FileManager is in charge of reading files.
 * @author Yunying Zhang
 * @since October 10 2020
 *
 */
public class DBController {
	private ArrayList<Supplier> supList = new ArrayList<Supplier>();
	private SupplierList supplierList = new SupplierList(supList);
	private ArrayList<Item> toolList = new ArrayList<Item>();
	
	/**
	 * Reads and parses two files
	 * @param file1: Item file 
	 * @param file2: Supplier file
	 * @throws Exception
	 */
	public DBController(String file1, String file2)throws Exception {
		
		
		File itemFile = new File(file1);
		File supplierFile = new File(file2);
		Scanner sc = new Scanner(itemFile);
		Scanner sc1 = new Scanner(supplierFile);

		//creating tool list
	    while (sc.hasNextLine()) { 
	    	String item = sc.nextLine();
	    	String[] toolInfo = item.split(";");
	    	
	    	int toolID = Integer.parseInt(toolInfo[0]);
	    	String toolName = toolInfo[1];
	    	int toolQty = Integer.parseInt(toolInfo[2]);
	    	double toolPrice = Double.parseDouble(toolInfo[3]);
	    	int supplierID = Integer.parseInt(toolInfo[4]);
	    	Item tool = new Item(toolID,toolName,toolQty,toolPrice,supplierID);
	    	
	    	toolList.add(tool);
	    	
	    }
	    
	    //creating supplier list
	    while (sc1.hasNextLine()) { 
	    	
	    	String sup = sc1.nextLine();
	    	String[] supInfo = sup.split(";");
	    	
	    	int supID = Integer.parseInt(supInfo[0]);
	    	String supName = supInfo[1];
	    	String supAddress = supInfo[2];
	    	String supRep = supInfo[3]; 
	    	Supplier supplier = new Supplier(supID,supName,supAddress,supRep);
	    	
	    	supplierList.addSupplier(supplier);
	    }
	    
	    sc.close();
	    sc1.close();
	    
	    
	   
	}
	

	public SupplierList generateSupList(){
		return supplierList;
	} 
	
	public ArrayList<Item> generateToolList(){
		return toolList;
	} 
	
	
	

}
