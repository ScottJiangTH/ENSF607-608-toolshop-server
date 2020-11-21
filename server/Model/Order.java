package server.Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
/**
 * Stores information of an order, writes an txt file as output for the order generated
 * @author Yunying Zhang
 * @Since October 10 2020
 *
 */

public class Order {
	
	private int orderID;
	private ArrayList <OrderLine> orderLines;
	private Date orderDate = new Date();
	private PrintWriter writer;
	LocalDate localDate = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	
	public Order () {
		orderLines = new ArrayList <OrderLine> ();
	}
	
	
	
/**
 * generates the order and creates a txt file
 * @param orderLineList: order that was sent
 * @throws Exception
 */
	public void generateOrder(ArrayList <OrderLine> orderLineList)throws Exception {
		
		System.out.println("Order generated.");
		FileWriter fw = new FileWriter("orders.txt", true);
	    BufferedWriter bw = new BufferedWriter(fw);
		writer = new PrintWriter(bw);
		writer.println("Order Date: "+localDate.getYear()+"-"+localDate.getMonthValue()+"-"+localDate.getDayOfMonth());
		writer.println("Order ID: "+ gen()+"\n\n");
		//adding each order line to the file
		for(OrderLine line:orderLineList){
			writer.println(line.toString());
		}
		writer.println("*********************************************************************");
		writer.close();
		
	}
	
	/**
	 * generates the order id
	 * @return order id
	 */
	private int gen() {
	    Random r = new Random( System.currentTimeMillis() );
	    return 10000 + r.nextInt(20000);
	}
	
	public int getOrderID() {
		return orderID;
	}

	public void addOrderLine (OrderLine ol) {
		orderLines.add(ol);
	}
}
