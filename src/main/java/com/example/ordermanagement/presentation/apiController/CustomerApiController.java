package com.example.ordermanagement.presentation.apiController;

import com.example.ordermanagement.application.service.CustomerApiService;
import com.example.ordermanagement.domain.model.entity.Customer;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerApiController {

    private final CustomerApiService customerApiService;

    public CustomerApiController(CustomerApiService customerApiService) {
        this.customerApiService = customerApiService;
    }

    @GetMapping("/{seq}")
    public Customer findBySeq(@PathVariable Long seq){
        return customerApiService.findBySeq(seq);
    }

    @PostMapping("")
    public Customer create(@Validated @RequestBody Customer customer){
        return customerApiService.create(customer);
    }

}
