package Server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
	
	private ServerSocket serverSocket;
	private ExecutorService pool;

	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			pool = Executors.newCachedThreadPool();
		} catch (IOException e) {
			System.err.println("Unable to use port!");
		}
		
		System.out.println(" Server started successfully on port " + port);
	}
	
	public void communicate() {
		try{
			while(true) {
				Socket s = serverSocket.accept();
				System.out.println("Successfully connected from: " + s.getInetAddress());
				Model model = new Model(s);
				pool.execute(model);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			pool.shutdown();
		}
	}

}
