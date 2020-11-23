package server.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.Model.Item;
import server.Model.Model;
import server.Model.Supplier;

public class ModelController {

	ServerController serverController;
	DBController dBController;
	Model model;
	private ArrayList<Item> items;
	private ArrayList<Supplier> suppliers;

	public ModelController(ServerController s, Model m, DBController d) {
		this.serverController = s;
		this.model = m;
		this.dBController = d;
	}

	public ArrayList<Item> allItems() {
		items = new ArrayList<Item>();
		ResultSet rs = dBController.readWholeTable("item");

		try {
			while (rs.next()) {
				String ID = rs.getString(1);
				String type = rs.getString(2);
				String name = rs.getString(3);
				String quantity = rs.getString(6);
				String price = rs.getString(5);
				String supplierID = rs.getString(7);
				Supplier theSupplier = findSupplier(Integer.parseInt(supplierID));

				Item anItem = new Item(Integer.parseInt(ID), type, name, Integer.parseInt(quantity),
						Double.parseDouble(price), theSupplier);
				items.add(anItem);
				theSupplier.getItemList().add(anItem);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}

	public ArrayList<Supplier> allSuppliers() {
		suppliers = new ArrayList<Supplier>();
		ResultSet rs = dBController.readWholeTable("supplier");

		try {
			while (rs.next()) {
				String supplierID = rs.getString(1);
				String supplierType = rs.getString(2);
				String companyName = rs.getString(3);
				String address = rs.getString(4);
				String salesContact = rs.getString(5);
				suppliers.add(new Supplier(Integer.parseInt(supplierID), supplierType, companyName, address, salesContact));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return suppliers;
	}

	public Item searchForItem() {
		Item theItem = null;
		return theItem;
	}
	
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

	public static void main(String[] args) {
		ServerController s = new ServerController(9009);
		DBController d = new DBController();
		Model m = new Model();
		ModelController mc = new ModelController(s, m, d);

		s.communicate();

	}

}