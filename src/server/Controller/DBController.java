package server.Controller;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.Model.Item;
import server.Model.Supplier;



public class DBController {
	private ArrayList<Item> items;
	private ArrayList<Supplier> suppliers;
//
//	private String conString = "jdbc:mysql://localhost:3306/company";
//	private String username = "root";
//	private String password = "xxxxx";


	private Supplier findSupplier(int supplierId) {
		Supplier theSupplier = null;
		for (Supplier s : suppliers) {
			if (s.getSupId() == supplierId) {
				theSupplier = s;
				break;
			}
		}
		return theSupplier;
	}

	public ArrayList<Item> readItemsFromDatabase()
	{
		this.items = new ArrayList<Item>();



		String sql = "SELECT * FROM items";
		try
		{
			String conString = "jdbc:mysql://localhost:3306/toolshop";
			String username = "root";
			String password = "xxxxx";
			Connection con = DriverManager.getConnection(conString,  username,  password);
			Statement s = con.prepareStatement(sql);
			ResultSet rs = s.executeQuery(sql);

			while(rs.next())
			{
				String ID = rs.getString(1);
				String type = rs.getString(2);
				String name = rs.getString(3);
				String quantity = rs.getString(6);
				String price = rs.getString(5);
				String supplierID = rs.getString(7);
				Supplier theSupplier = findSupplier(Integer.parseInt(supplierID));

				Item myItem = new Item(Integer.parseInt(ID), name, Integer.parseInt(quantity), Double.parseDouble(price), theSupplier);
				items.add(myItem);
				theSupplier.getItemList().add(myItem);
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}

		return items;
	}


	public ArrayList<Supplier> readSuppliersFromDatabase()
	{
		this.suppliers = new ArrayList<Supplier>();

		String sql = "SELECT * FROM suppliers";
		try
		{
			String conString = "jdbc:mysql://localhost:3306/toolshop";
			String username = "root";
			String password = "xxxxx";
			Connection con = DriverManager.getConnection(conString,  username,  password);
			Statement s = con.prepareStatement(sql);
			ResultSet rs = s.executeQuery(sql);

			while(rs.next())
			{
				String supplierID = rs.getString(1);
				String supplierType = rs.getString(2);
				String companyName = rs.getString(3);
				String address = rs.getString(4);
				String salesContact = rs.getString(5);
				suppliers.add(new Supplier(Integer.parseInt(supplierID), companyName, address, salesContact));
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}

		return suppliers;
	}

}



