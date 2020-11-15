package Server;


public class Controller {

	Server server;
	Model model;
	
	public Controller(Server s, Model m){
		this.server = s;
		this.model = m;
	}
	
	public static void main(String[] args){
		Server s = new Server(9009);
		s.communicate();
	}
}