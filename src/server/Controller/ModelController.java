package server.Controller;

import server.Model.Model;

public class ModelController {

	ServerController server;
	Model model;
	
	public ModelController(ServerController s, Model m){
		this.server = s;
		this.model = m;
	}
	
	public static void main(String[] args){
		ServerController s = new ServerController(9009);
		s.communicate();
	}
}