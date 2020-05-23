package com.taeproiect.soap.webservices.soapwebservices.soap.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.taeproiect.soap.webservices.soapwebservices.soap.Order;

@Component
public class OrderDetailsService {

	public enum Status {
		SUCCESS, FAILURE;
	}

	private static List<Order> orders = new ArrayList<>();
	static {
		Order order1 = new Order(1, "Laptop", "Asus Tuf Gaming Fx504", 4350);
		orders.add(order1);
		Order order2 = new Order(2, "TV", "Samsung", 1700);
		orders.add(order2);
		Order order3 = new Order(3, "Bicycle", "Scott Bicycle", 2500);
		orders.add(order3);
	}

	public Order findById(int id) {
		for (Order order : orders) {
			if (order.getId() == id)
				return order;
		}
		return null;
	}

	public Status addOrder(String name, String description, float price) {
		try {
			Order o = orders.get(orders.size() - 1);
			int i = o.getId();
			Order order1 = new Order(++i, name, description, price);
			orders.add(order1);
			return Status.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Status.FAILURE;
	}

	public List<Order> findAll() {
		return orders;
	}

	public Status deletebyId(int id) {
		Iterator<Order> iterator = orders.iterator();
		while (iterator.hasNext()) {
			Order order = iterator.next();
			if (order.getId() == id) {
				iterator.remove();
				return Status.SUCCESS;
			}

		}
		return Status.FAILURE;
	}

}
