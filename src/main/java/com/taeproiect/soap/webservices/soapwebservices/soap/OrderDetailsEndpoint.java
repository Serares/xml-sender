package com.taeproiect.soap.webservices.soapwebservices.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.taeproiect.orders.AddOrderDetailsRequest;
import com.taeproiect.orders.AddOrderDetailsResponse;
import com.taeproiect.orders.DeleteOrderDetailsRequest;
import com.taeproiect.orders.DeleteOrderDetailsResponse;
import com.taeproiect.orders.GetAllOrderDetailsRequest;
import com.taeproiect.orders.GetAllOrderDetailsResponse;
import com.taeproiect.orders.GetOrderDetailsRequest;
import com.taeproiect.orders.GetOrderDetailsResponse;
import com.taeproiect.orders.OrderDetails;
import com.taeproiect.soap.webservices.soapwebservices.soap.service.OrderDetailsService;
import com.taeproiect.soap.webservices.soapwebservices.soap.service.OrderDetailsService.Status;

@Endpoint
public class OrderDetailsEndpoint {

	@Autowired
	OrderDetailsService service;

	@PayloadRoot(namespace = "http://taeproiect.com/orders", localPart = "GetOrderDetailsRequest")
	@ResponsePayload
	public GetOrderDetailsResponse processOrderDetailsRequest(@RequestPayload GetOrderDetailsRequest request) {
		Order order = service.findById(request.getId());

		return mapOrderDetails(order);

	}

	private GetOrderDetailsResponse mapOrderDetails(Order order) {
		GetOrderDetailsResponse response = new GetOrderDetailsResponse();
		response.setOrderDetails(mapOrder(order));
		return response;
	}
	
//	private AddOrderDetailsResponse mapAddOrderDetails(Order order) {
//		AddOrderDetailsResponse response = new AddOrderDetailsResponse();
//		response.setOrderDetails(mapOrder(order));
//		return response;
//	}

	private GetAllOrderDetailsResponse mapAllOrderDetails(List<Order> orders) {
		GetAllOrderDetailsResponse response = new GetAllOrderDetailsResponse();
		for (Order order : orders) {
			OrderDetails mapOrder = mapOrder(order);
			response.getOrderDetails().add(mapOrder);
		}
		return response;
	}

	private OrderDetails mapOrder(Order order) {
		OrderDetails orderDetails = new OrderDetails();

		orderDetails.setId(order.getId());

		orderDetails.setName(order.getName());

		orderDetails.setDescription(order.getDescription());

		orderDetails.setPrice(order.getPrice());
		return orderDetails;
	}

	@PayloadRoot(namespace = "http://taeproiect.com/orders", localPart = "GetAllOrderDetailsRequest")
	@ResponsePayload
	public GetAllOrderDetailsResponse processAllOrderDetailsRequest(@RequestPayload GetAllOrderDetailsRequest request) {
		List<Order> orders = service.findAll();

		return mapAllOrderDetails(orders);

	}

	@PayloadRoot(namespace = "http://taeproiect.com/orders", localPart = "DeleteOrderDetailsRequest")
	@ResponsePayload
	public DeleteOrderDetailsResponse deleteOrderDetailsRequest(@RequestPayload DeleteOrderDetailsRequest request) {
		Status status = service.deletebyId(request.getId());
		DeleteOrderDetailsResponse response = new DeleteOrderDetailsResponse();
		response.setStatus(mapStatus(status));
		return response;

	}
	
	@PayloadRoot(namespace = "http://taeproiect.com/orders", localPart = "AddOrderDetailsRequest")
	@ResponsePayload
	public AddOrderDetailsResponse processAddOrderDetailsRequest(@RequestPayload AddOrderDetailsRequest request) {
		Status order = service.addOrder(request.getName(), request.getDescription(), request.getPrice());

		AddOrderDetailsResponse response = new AddOrderDetailsResponse();
		response.setStatus(mapStatus(order));
		return response;

	}

	private com.taeproiect.orders.Status mapStatus(Status status) {
		if (status == Status.FAILURE)
			return com.taeproiect.orders.Status.FAILURE;
		return com.taeproiect.orders.Status.SUCCESS;
	}
	
	
}
