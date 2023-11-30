package com.example.orderprojectbe;

import com.example.orderprojectbe.model.Vendor;
import com.example.orderprojectbe.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner
{

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public void run(String... args) throws Exception
    {
        Vendor reverb = new Vendor();
        reverb.setVendorName("Reverb");
        vendorRepository.save(reverb);

        Vendor etsy = new Vendor();
        etsy.setVendorName("Etsy");
        vendorRepository.save(etsy);

        Vendor shopify = new Vendor();
        shopify.setVendorName("Shopify");
        vendorRepository.save(shopify);
    }


}
