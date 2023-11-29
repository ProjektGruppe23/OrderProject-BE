package com.example.orderprojectbe.service.shopify;

import com.example.orderprojectbe.model.CostumerAddress;
import com.example.orderprojectbe.model.Country;
import com.example.orderprojectbe.model.Order;
import com.example.orderprojectbe.repository.*;
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
public class ShopifyApiServiceGetAllOrdersImpl implements ShopifyApiServiceGetAllOrders {

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

                JsonNode lineItemsNode = orderNode.get("line_items");
                if (lineItemsNode != null && lineItemsNode.isArray()) {
                    for (JsonNode itemNode : lineItemsNode) {
                        String productName = itemNode.get("name").asText("");
                        double price = Double.parseDouble(itemNode.get("price_set").get("shop_money").get("amount").asText("0.0"));
                        int quantity = itemNode.get("quantity").asInt(0);

                        order.setProductName(productName);
                        order.setPrice(price);
                        order.setQuantity(quantity);
                        order.setVendor(vendorRepository.findByVendorName("Shopify"));
                    }
                }

                JsonNode shippingAddressNode = orderNode.get("shipping_address");
                if (shippingAddressNode != null) {
                    CostumerAddress costumerAddress = new CostumerAddress();
                    costumerAddress.setCity(shippingAddressNode.get("city").asText());
                    costumerAddress.setStreetAddress(shippingAddressNode.get("address1").asText());
                    costumerAddress.setExtendedAddress(shippingAddressNode.get("address2").asText());
                    costumerAddress.setPostalCode(shippingAddressNode.get("zip").asText());
                    costumerAddress.setCostumerName(shippingAddressNode.get("name").asText());
                    costumerAddress.setPhone(shippingAddressNode.get("phone").asText());

                    String countryName = shippingAddressNode.get("country").asText();
                    Country country;
                    var countryOptional = countryRepository.findCountryByCountryName(countryName);
                    if (countryOptional.isPresent()) {
                        country = countryOptional.get();
                    } else {
                        country = new Country();
                        country.setCountryName(countryName);
                        countryRepository.save(country);
                    }
                    costumerAddress.setCountry(country);
                    costumerAddressRepository.save(costumerAddress);

                    order.setCostumerAddress(costumerAddress);
                }

                List<Order> ordersCheckList = orderRepository.findOrderApiIdAndVendor(order.getOrderApiId(), order.getVendor());
                if (!ordersCheckList.isEmpty()) {
                    System.out.println("Order with ID " + order.getOrderApiId() + " and vendor " + order.getVendor() + " already exists. Skipping...");
                } else {
                    orders.add(order);
                    System.out.println("Order with ID " + order.getOrderApiId() + " and vendor " + order.getVendor() + " inserted.");
                }
            }

            saveOrders(orders);

            return orders;
        } catch (IOException e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }
    }
}



