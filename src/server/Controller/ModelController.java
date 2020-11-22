package server.Controller;

import java.util.ArrayList;

import server.Model.Item;
import server.Model.Model;
import server.Model.Supplier;

public class ModelController {

	ServerController server;
	Model model;
	private ArrayList<Item> items;
	private ArrayList<Supplier> suppliers;
	
	public ModelController(ServerController s, Model m){
		this.server = s;
		this.model = m;
	}
	
	public static void main(String[] args){
		ServerController s = new ServerController(9009);
		s.communicate();
	}
	
	
}