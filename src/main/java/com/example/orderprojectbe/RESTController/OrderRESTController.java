package com.example.orderprojectbe.RESTController;

import com.example.orderprojectbe.model.ArchivedOrder;
import com.example.orderprojectbe.model.CostumerAddress;
import com.example.orderprojectbe.model.Order;
import com.example.orderprojectbe.repository.ArchivedOrderRepository;
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

    @Autowired
    ArchivedOrderRepository archivedOrderRepository;

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
            Optional<Order> selectedOrder = orderRepository.findById(orderId);

            if ( selectedOrder.isPresent() )
            {
                Order orderToDelete = selectedOrder.get();

                ArchivedOrder archivedOrder = new ArchivedOrder();
                archivedOrder.setProductName(orderToDelete.getProductName());
                archivedOrder.setCountry(orderToDelete.getCostumerAddress().getCountry().getCountryName());
                archivedOrder.setPrice(orderToDelete.getPrice());
                archivedOrder.setQuantity(orderToDelete.getQuantity());
                archivedOrder.setApiId(orderToDelete.getOrderApiId());
                archivedOrder.setVendor(orderToDelete.getVendor().getVendorName());

                archivedOrderRepository.save(archivedOrder);

                orderRepository.deleteById(orderId);

                System.out.println("Order with id " + orderId + " deleted and archived successfully");
                return ResponseEntity.ok("Order deleted and archived successfully");
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

    @GetMapping("/get2")
    public String get2()
    {
        return "get2";
    }


    @GetMapping("/getCustomerAddressByOrderId/{orderId}")
    public ResponseEntity<CostumerAddress> getCustomerAddressByOrderId(@PathVariable int orderId) {
        Order order = orderRepository.findById(orderId).get(); // Directly get the Order
        CostumerAddress customerAddress = order.getCostumerAddress(); // Get the CustomerAddress from the Order

        return ResponseEntity.ok(customerAddress); // Return the CustomerAddress
    }


//    @GetMapping("/getCustomerAddressByOrderId/{orderId}")
//    public ResponseEntity<CostumerAddress> getCustomerAddressByOrderId(@PathVariable int orderId) {
//        Optional<Order> order = orderRepository.findById(orderId);
//
//        if (order.isPresent()) {
//            CostumerAddress customerAddress = order.get().getCostumerAddress();
//            if (customerAddress != null) {
//                return ResponseEntity.ok(customerAddress);
//            }
//        }
//        return ResponseEntity.notFound().build();
//    }








}
