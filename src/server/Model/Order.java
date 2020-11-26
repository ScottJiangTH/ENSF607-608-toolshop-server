package server.Model;

import java.time.LocalDate;
import java.util.ArrayList;
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
	private LocalDate orderDate;
	private ArrayList<OrderLine> orderLines;

	public Order() {
		orderId = gen();
		orderLines = new ArrayList<OrderLine>();
		setOrderDate(LocalDate.now());
	}

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
	
	public void addOrderLine(OrderLine ol) {
		orderLines.add(ol);
	}
	
	public ArrayList<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(ArrayList<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
}
