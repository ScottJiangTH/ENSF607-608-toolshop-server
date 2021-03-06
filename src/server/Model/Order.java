package server.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class Order {

	private int orderId;
	private LocalDate orderDate;
	private ArrayList<OrderLine> orderLines;

	public Order() {
		orderId = gen();
		orderLines = new ArrayList<OrderLine>();
		setOrderDate(LocalDate.now());
	}
	public Order (int orderId, LocalDate orderDate, ArrayList<OrderLine> orderlines) {
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.orderLines = orderlines;
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
