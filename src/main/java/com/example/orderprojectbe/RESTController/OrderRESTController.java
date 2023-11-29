package com.example.orderprojectbe.RESTController;

import com.example.orderprojectbe.model.Order;
import com.example.orderprojectbe.repository.CostumerAddressRepository;
import com.example.orderprojectbe.repository.OrderRepository;
import com.example.orderprojectbe.service.reverb.ReverbApiServiceGetAllOrders;
import com.example.orderprojectbe.service.shopify.ShopifyApiServiceGetAllOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class OrderRESTController
{
    @Autowired
    ReverbApiServiceGetAllOrders reverbApiServiceGetAllOrders;

    @Autowired
    ShopifyApiServiceGetAllOrders shopifyApiServiceGetAllOrders;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CostumerAddressRepository CostumerAddressRepository;


    @GetMapping("/getorders")
    public List<Order> getOrders()
    {
        List<Order> lstOrders = reverbApiServiceGetAllOrders.getAllOrders();
        return lstOrders;
    }

    @GetMapping("/getordersshopify")
    public List<Order> getOrdersShopify()
    {
        List<Order> lstOrders = shopifyApiServiceGetAllOrders.loadOrdersFromApi();
        return lstOrders;
    }

    @GetMapping("/getordersfromdb")
    public List<Order> getOrdersFromDB()
    {
        List<Order> lstOrders = orderRepository.findAll();
        return lstOrders;
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable int orderId)
    {
        try
        {
            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            if ( optionalOrder.isPresent() )
            {
                // Order with the specified ID exists, so delete it
                System.out.println("order with id " + orderId + " deleted successfully");
                System.out.println("ID: " + orderId + ", Api ID: " + optionalOrder.get().getOrderApiId() + ", Product name: " + optionalOrder.get().getProductName() + ", Quantity: " + optionalOrder.get().getQuantity() + ", Vendor: " + optionalOrder.get().getVendor());
                orderRepository.deleteById(orderId);
                return ResponseEntity.ok("Order deleted successfully");
            }
            else
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the order");
        }
    }

}
