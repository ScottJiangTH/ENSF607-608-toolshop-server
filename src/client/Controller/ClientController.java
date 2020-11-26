package client.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

/**
 * This class serves as a socket manager and receive/send communication Strings
 * from/to ServerController. It uses ClientModel to process some JSON formatted
 * String into objects, and pass objects into each view controller.
 *
 */
public class ClientController {

	private Socket socket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private InventoryGUIController inventoryController;
	private CustomerGUIController customerController;

	public ClientController(String serverName, int portNum) {
		try {
			socket = new Socket(serverName, portNum);
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketOut = new PrintWriter(socket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Unable find Server!");
			System.exit(1);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Unable to connect to Server!");
			System.exit(1);
		}
	}

	public void communicate() {
		inventoryController = new InventoryGUIController(socketIn, socketOut);
		inventoryController.show();
		customerController = new CustomerGUIController(socketIn, socketOut);
		customerController.show();
	}

	public static void main(String[] args) {
		ClientController client = new ClientController("localhost", 9009);
		client.communicate();
	}
}
