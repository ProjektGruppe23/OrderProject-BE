package com.example.orderprojectbe.service.reverb;


import com.example.orderprojectbe.model.Order;
import com.example.orderprojectbe.repository.OrderRepository;
import com.example.orderprojectbe.service.reverb.ReverbApiServiceGetAllOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class ReverbApiServiceGetAllOrdersImpl implements ReverbApiServiceGetAllOrders
{

    private final RestTemplate restTemplate;
    @Autowired
    OrderRepository orderRepository;

    @Value("${reverb.api.key}")  // Assuming you have a property for the API key in your application.properties or application.yml
    private String apiKey;

    @Autowired
    public ReverbApiServiceGetAllOrdersImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Update the URL to the new endpoint
    String regionUrl = "https://api.reverb.com/api/my/orders/selling/all";

    private void saveOrders(List<Order> orders) {
        orders.forEach(reg -> orderRepository.save(reg));
    }

    @Override
    public List<Order> getAllOrders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);

        RequestEntity<Void> requestEntity;
        try {
            requestEntity = new RequestEntity<>(headers, HttpMethod.GET, new URI(regionUrl));
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error creating request URI", e);
        }

        ResponseEntity<List<Order>> regionResponse = restTemplate.exchange(
                requestEntity,
                new ParameterizedTypeReference<List<Order>>() {});

        // ResponseEntity wraps an HTTP response and body
        List<Order> orders = regionResponse.getBody();
        saveOrders(orders);
        return orders;
    }
}

