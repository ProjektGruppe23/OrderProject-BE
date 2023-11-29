package com.example.orderprojectbe.service.reverb;


import com.example.orderprojectbe.model.CostumerAddress;
import com.example.orderprojectbe.model.Country;
import com.example.orderprojectbe.model.Order;
import com.example.orderprojectbe.model.Vendor;
import com.example.orderprojectbe.repository.CostumerAddressRepository;
import com.example.orderprojectbe.repository.CountryRepository;
import com.example.orderprojectbe.repository.OrderRepository;
import com.example.orderprojectbe.repository.VendorRepository;
import com.example.orderprojectbe.service.reverb.ReverbApiServiceGetAllOrders;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReverbApiServiceGetAllOrdersImpl implements ReverbApiServiceGetAllOrders
{

    private final RestTemplate restTemplate;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CostumerAddressRepository costumerAddressRepository;

    @Value("${reverb.api.key}")  // Assuming you have a property for the API key in your application.properties or application.yml
    private String apiKey;

    @Autowired
    public ReverbApiServiceGetAllOrdersImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Update the URL to the new endpoint
    String reverbUrl = "https://api.reverb.com/api/my/orders/selling/all"; //awaiting_shipment

    private void saveOrders(List<Order> orders) {
        orders.forEach(order -> orderRepository.save(order));
    }

    private void saveCostumerAddress(Set<CostumerAddress> costumerAddressSet) {
        costumerAddressSet.forEach(costumerAddress -> costumerAddressRepository.save(costumerAddress));
    }

    private void saveCountry(Set<Country> countrySet) {
        countrySet.forEach(country -> countryRepository.save(country));
    }

    @Override
    public List<Order> getAllOrders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);

        RequestEntity<Void> requestEntity;
        try {
            System.out.println("inde i try");
            requestEntity = new RequestEntity<>(headers, HttpMethod.GET, new URI(reverbUrl));
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

            // Assuming the orders are in an array called "orders"
            JsonNode ordersNode = root.get("orders");

            // Process each order
            List<Order> orders = new ArrayList<>();
            Set<CostumerAddress> costumerAddressSet = new HashSet<>();
            Set<Country> countrySet = new HashSet<>();
            for (JsonNode orderNode : ordersNode) {
                Order order = new Order();

                CostumerAddress costumerAddress = new CostumerAddress();
                Country country = new Country();

                // Extract specific fields from the orderNode and set them in the Order object
                order.setOrderApiId(orderNode.get("order_number").asText());
                order.setProductName(orderNode.get("title").asText());
                order.setPrice(orderNode.get("amount_product").get("amount").asDouble());
                order.setQuantity(orderNode.get("quantity").asInt());

                order.setVendor(vendorRepository.findByVendorName("Reverb"));

                String displayLocation = orderNode.get("shipping_address").get("display_location").asText();
                String countryName = country.getReverbCountrySubstring(displayLocation);

                if(countryRepository.findCountryByCountryName(countryName).isPresent())
                {
                    costumerAddress.setCountry(countryRepository.findCountryByCountryName(countryName).get());
                } else
                {
                    country.setCountryName(countryName);
                    costumerAddress.setCountry(country);
                    countrySet.add(country);
                    saveCountry(countrySet);
                }

                costumerAddress.setCity(orderNode.get("shipping_address").get("locality").asText());
                costumerAddress.setStreetAddress(orderNode.get("shipping_address").get("street_address").asText());
                costumerAddress.setExtendedAddress(orderNode.get("shipping_address").get("extended_address").asText());
                costumerAddress.setPostalCode(orderNode.get("shipping_address").get("postal_code").asText());
                costumerAddress.setCostumerName(orderNode.get("shipping_address").get("name").asText());
                costumerAddress.setPhone(orderNode.get("shipping_address").get("phone").asText());


                order.setCostumerAddress(costumerAddress);


                costumerAddressSet.add(costumerAddress);
                saveCostumerAddress(costumerAddressSet);
                orders.add(order);

            }

            // Save orders to the database if needed
            saveOrders(orders);

            System.out.println("Processed Orders: " + orders);
            return orders;
        } catch (IOException e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }
    }
}

