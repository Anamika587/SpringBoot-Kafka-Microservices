package com.projects.stockservice.service;

import com.projects.basedomains.dto.OrderDTO;
import com.projects.stockservice.entity.Order;
import com.projects.stockservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void saveOrder(OrderDTO orderDTO) {
        Order order = new Order(orderDTO.getOrderId(), orderDTO.getName(), orderDTO.getQty(), orderDTO.getPrice(), orderDTO.getCustomerEmail());
        Order savedOrder = orderRepository.save(order);
//        new OrderDTO(savedOrder.getOrderId(), savedOrder.getName(), savedOrder.getQty(), savedOrder.getPrice());
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> new OrderDTO(order.getOrderId(), order.getName(), order.getQty(), order.getPrice(), order.getCustomerEmail()))
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .map(order -> new OrderDTO(order.getOrderId(), order.getName(), order.getQty(), order.getPrice(),order.getCustomerEmail()))
                .orElse(null);
    }
}

