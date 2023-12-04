package com.example.orderprojectbe.service;

import com.example.orderprojectbe.model.CostumerAddress;
import com.example.orderprojectbe.model.Country;
import com.example.orderprojectbe.model.Order;
import com.example.orderprojectbe.repository.CostumerAddressRepository;
import com.example.orderprojectbe.repository.CountryRepository;
import com.example.orderprojectbe.repository.OrderRepository;
import com.example.orderprojectbe.repository.VendorRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class ApiService
{
    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CostumerAddressRepository costumerAddressRepository;

    @Autowired
    OrderRepository orderRepository;


    public void setOrderDetailsFromApi(Order order, JsonNode orderNode)
    {
        if(order.getVendor().getVendorName().equals("Reverb"))
        {
            order.setOrderApiId(orderNode.get("order_number").asText());
            order.setProductName(orderNode.get("title").asText());
            order.setPrice(orderNode.get("amount_product").get("amount").asDouble());
            order.setQuantity(orderNode.get("quantity").asInt());
        }
        else if(order.getVendor().getVendorName().equals("Shopify"))
        {
                    order.setProductName(orderNode.get("name").asText(""));
                    order.setPrice(Double.parseDouble(orderNode.get("price_set").get("shop_money").get("amount").asText("0.0")));
                    order.setQuantity(orderNode.get("quantity").asInt(0));

        }

    }

    public Country setCountryFromApi(String countryName)
    {
        Country country;
        var countryOptional = countryRepository.findCountryByCountryName(countryName);
        if (countryOptional.isPresent())
        {
            country = countryOptional.get();
        } else
        {
            country = new Country();
            country.setCountryName(countryName);
            countryRepository.save(country);
        }

        return country;
    }

    public void setCostumerAddressFromApi(JsonNode shippingAddressNode, String countryName, Order order, String city, String streetAddress, String extendedAddress, String postalCode, String costumerName, String phone)
    {
        CostumerAddress costumerAddress = new CostumerAddress();
        costumerAddress.setCity(shippingAddressNode.get(city).asText());
        costumerAddress.setStreetAddress(shippingAddressNode.get(streetAddress).asText());
        costumerAddress.setExtendedAddress(shippingAddressNode.get(extendedAddress).asText());
        costumerAddress.setPostalCode(shippingAddressNode.get(postalCode).asText());
        costumerAddress.setCostumerName(shippingAddressNode.get(costumerName).asText());
        costumerAddress.setPhone(shippingAddressNode.get(phone).asText());

       Country country = setCountryFromApi(countryName);


        var costumerAddressOptional = costumerAddressRepository.findCostumerAddressByCityAndStreetAddressAndExtendedAddressAndPostalCode(costumerAddress.getCity(), costumerAddress.getStreetAddress(), costumerAddress.getExtendedAddress(), costumerAddress.getPostalCode());
        if (costumerAddressOptional.isPresent()) {
            System.out.println(" already exists. Skipping...");
            order.setCostumerAddress(costumerAddressOptional.get());
        } else {
            costumerAddress.setCountry(country);
            System.out.println(" inserted.");
            order.setCostumerAddress(costumerAddress);
            costumerAddressRepository.save(costumerAddress);
        }


    }

    public void checkIfOrderAlreadyExists(List<Order> orders, Order order)
    {
        List<Order> ordersCheckList = orderRepository.findOrderByOrderApiIdAndVendor(order.getOrderApiId(), order.getVendor());
        if (!ordersCheckList.isEmpty())
        {
            System.out.println("Order with ID " + order.getOrderApiId() + " and vendor " + order.getVendor() + " already exists. Skipping...");
        } else
        {
            orders.add(order);
            System.out.println("Order with ID " + order.getOrderApiId() + " and vendor " + order.getVendor() + " inserted.");
        }
    }
}
