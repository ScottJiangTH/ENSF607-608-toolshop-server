package server.Controller;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.Model.Model;

public class ServerController {
	
	private Socket socket;
	private ServerSocket serverSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private ExecutorService pool;
	private static int clientCount = 0;

	public ServerController(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Server error: Failed to create ServerSocket.");
		}
		pool = Executors.newCachedThreadPool();
		System.out.println(" Server started successfully on port " + port);
	}
	
	public void communicate() {
		try{
			while(true) {
				// server socket accept connections
				Socket s = serverSocket.accept();
				clientCount++;
				System.out.print(String.format("Client %d connected.", clientCount));
				System.out.println("Connection from: " + s.getInetAddress());
				// create socketIn and socketOut
				socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				socketOut = new PrintWriter(socket.getOutputStream(), true);
				// Create a new Model object and send reference of socketIn and socketOut
				ModelController model = new ModelController(socketIn, socketOut);
				pool.execute(model);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		pool.shutdown();
		try {
			socket.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			System.err.println("Server error: Failed to close client socket.");
		}
	}
	
	public static void main(String[] args) {
		ServerController s = new ServerController(9009);
		s.communicate();

	}

}
