package com.example.orderprojectbe.service.shopify;

import com.example.orderprojectbe.model.Order;
import com.example.orderprojectbe.repository.OrderRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ShopifyApiServiceGetAllOrdersImpl implements ShopifyApiServiceGetAllOrders
{

    private final RestTemplate restTemplate;
    @Autowired
    OrderRepository orderRepository;

    @Value("${shopify.api.key}")  // Assuming you have a property for the API key in your application.properties or application.yml
    private String apiKey;

    @Value("${shopify.token.key}")  // Assuming you have a property for the API key in your application.properties or application.yml
    private String tokenKey;

    @Autowired
    public ShopifyApiServiceGetAllOrdersImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    String shopifyUrl = "https://" + apiKey + ":" + tokenKey + "@8eff11-2.myshopify.com/admin/api/2023-01/orders.json?status=any;";


    private void saveOrders(List<Order> orders) {
        orders.forEach(reg -> orderRepository.save(reg));
    }

    @Override
    public List<Order> getAllOrders() {
        HttpHeaders headers = new HttpHeaders();

        // Encode API key and token
        String auth = apiKey + ":" + tokenKey;  // Replace apiToken with your actual token
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);

        headers.set("Authorization", authHeader);


        RequestEntity<Void> requestEntity;
        try {
            System.out.println("inde i try");
            requestEntity = new RequestEntity<>(headers, HttpMethod.GET, new URI(shopifyUrl));
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error creating request URI", e);
        }

        ResponseEntity<String> rawResponse = restTemplate.exchange(
                requestEntity,
                String.class);

        String responseBody = rawResponse.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(responseBody);

            JsonNode ordersNode = root.get("orders");
            if (ordersNode == null) {
                throw new RuntimeException("No 'orders' node found in JSON response");
            }

            List<Order> orders = new ArrayList<>();
            for (JsonNode orderNode : ordersNode) {
                Order order = new Order();
                order.setOrderApiId(orderNode.get("id").asInt()); // Extract the order ID

                JsonNode lineItemsNode = orderNode.get("line_items");
                if (lineItemsNode != null && lineItemsNode.isArray()) {
                    for (JsonNode itemNode : lineItemsNode) {
                        String productName = itemNode.get("name").asText(""); // Extract product name
                        double price = Double.parseDouble(itemNode.get("price_set").get("shop_money").get("amount").asText("0.0")); // Extract price
                        int quantity = itemNode.get("quantity").asInt(0); // Extract quantity

                        // Set these values in the Order object
                        order.setProductName(productName);
                        order.setPrice(price);
                        order.setQuantity(quantity);
                    }
                }

                orders.add(order);
            }

            saveOrders(orders);

            System.out.println("Processed Orders: " + orders);
            return orders;
        } catch (IOException e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }

    }
}



