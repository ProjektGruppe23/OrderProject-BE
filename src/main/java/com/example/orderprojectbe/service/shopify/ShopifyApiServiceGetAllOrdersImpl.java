package com.example.orderprojectbe.service.shopify;

import com.example.orderprojectbe.model.CostumerAddress;
import com.example.orderprojectbe.model.Country;
import com.example.orderprojectbe.model.Order;
import com.example.orderprojectbe.repository.*;
import com.example.orderprojectbe.service.ApiService;
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
import java.text.ParseException;
import java.util.*;

@Service
public class ShopifyApiServiceGetAllOrdersImpl extends ApiService implements ShopifyApiServiceGetAllOrders
{

    private final RestTemplate restTemplate;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CostumerAddressRepository costumerAddressRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    VendorRepository vendorRepository;

    @Value("${shopify.api.key}")
    private String apiKey;

    @Value("${shopify.token.key}")
    private String tokenKey;

    @Autowired
    public ShopifyApiServiceGetAllOrdersImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    String shopifyUrl = "https://" + apiKey + ":" + tokenKey + "@8eff11-2.myshopify.com/admin/api/2023-01/orders.json?status=any";

    private void saveOrders(List<Order> orders) {
        orders.forEach(reg -> orderRepository.save(reg));
    }

    @Override
    public List<Order> loadOrdersFromApi() {
        HttpHeaders headers = new HttpHeaders();
        String auth = apiKey + ":" + tokenKey;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);

        RequestEntity<Void> requestEntity;
        try {
            requestEntity = new RequestEntity<>(headers, HttpMethod.GET, new URI(shopifyUrl));
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error creating request URI", e);
        }

        ResponseEntity<String> rawResponse = restTemplate.exchange(requestEntity, String.class);
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
                order.setOrderApiId(orderNode.get("id").asText());
                order.setDate(setDateFromApi(orderNode.get("created_at").asText()));
                order.setVendor(vendorRepository.findVendorByVendorName("Shopify"));

                JsonNode lineItemsNode = orderNode.get("line_items");
                if (lineItemsNode != null && lineItemsNode.isArray()) {
                    for (JsonNode itemNode : lineItemsNode) {
                        setOrderDetailsFromApi(order, itemNode);

                    }
                }

                JsonNode shippingAddressNode = orderNode.get("shipping_address");

                if (shippingAddressNode != null) {
                    String countryName = shippingAddressNode.get("country").asText();
                    setCostumerAddressFromApi(shippingAddressNode, countryName, order, "city", "address1", "address2", "zip", "name", "phone");
                }

                checkIfOrderAlreadyExists(orders, order);
            }
            saveOrders(orders);

            return orders;
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }
    }
}



