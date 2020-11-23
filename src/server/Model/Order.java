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
 * Stores information of an order, writes an txt file as output for the order
 * generated
 * 
 * @author Yunying Zhang
 * @Since October 10 2020
 *
 */

public class Order {

	private int orderId;
	private Date orderDate = new Date();
	LocalDate localDate = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	private ArrayList<OrderLine> orderLines;

	public Order() {
		orderLines = new ArrayList<OrderLine>();
	}

	/**
	 * generates the order id
	 * 
	 * @return order id
	 */
	private int gen() {
		Random r = new Random(System.currentTimeMillis());
		return 10000 + r.nextInt(20000);
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int newId) {
		this.orderId = newId;
	}
	
	public LocalDate getLocalDate() {
		return localDate;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}
	
	public void addOrderLine(OrderLine ol) {
		orderLines.add(ol);
	}
	
	public ArrayList<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(ArrayList<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}
}
