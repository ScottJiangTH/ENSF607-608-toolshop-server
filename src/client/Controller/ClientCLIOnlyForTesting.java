package client.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientCLIOnlyForTesting {

	private Socket aSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private BufferedReader stdIn;

	public ClientCLIOnlyForTesting(String serverName, int portNumber) {
		try {
			aSocket = new Socket(serverName, portNumber);
			// keyboard input stream
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOut = new PrintWriter(aSocket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void prompt() throws IOException {
		String oldInput = "";
		String oldMessage = "";
		String newInput = "Input stream ready";
		String newMessage = "CLI ready";

		while (true) {
			if (newMessage.equals(oldMessage)) {
				newMessage = socketIn.readLine();
			} else {
				System.out.println(newMessage);
				oldMessage = newMessage;
			}
			if (newInput.equals(oldInput)) {
				newInput = stdIn.readLine();
			} else {
				socketOut.println(newInput);
				oldInput = newInput;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		ClientCLIOnlyForTesting aClient = new ClientCLIOnlyForTesting("localhost", 9009);
		aClient.prompt();
	}

}
