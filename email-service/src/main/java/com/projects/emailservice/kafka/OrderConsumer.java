package com.projects.emailservice.kafka;

import com.projects.basedomains.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
@Service
public class OrderConsumer {

    private static  final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @Autowired
    private JavaMailSender mailSender;

    @KafkaListener(topics="${spring.kafka.topic.name}"
    ,groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrderEvent event){
        LOGGER.info(String.format("Order event received in email service => %s",event.toString()));

        //send email to the customer
        sendOrderEmail(event);
    }

    private void sendOrderEmail(OrderEvent event) {
        String customerEmail = event.getOrder().getCustomerEmail(); // Assuming OrderEvent contains an email field

        if (customerEmail == null || customerEmail.isEmpty()) {
            LOGGER.warn("Customer email is missing. Cannot send order confirmation.");
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customerEmail);
        message.setSubject("Order Confirmation - Your Order is Placed!");
        message.setText("Dear Customer,\n\n"
                + "Thank you for your order! Here are your order details:\n"
                + "Order ID: " + event.getOrder().getOrderId() + "\n"
                + "Product: " + event.getOrder().getName() + "\n"
                + "Quantity: " + event.getOrder().getQty() + "\n"
                + "Price: $" + event.getOrder().getPrice() + "\n\n"
                + "We will notify you when your order is shipped.\n\n"
                + "Best Regards,\nYour E-Commerce Team");

        mailSender.send(message);
        LOGGER.info("Order confirmation email sent to {}", customerEmail);
    }
}
