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
		while (true) {
			socketOut.println(stdIn.readLine());
			System.out.println(socketIn.readLine());
		}
	}

	public static void main(String[] args) throws IOException {
		ClientCLIOnlyForTesting aClient = new ClientCLIOnlyForTesting("localhost", 9009);
		aClient.prompt();
	}

}
