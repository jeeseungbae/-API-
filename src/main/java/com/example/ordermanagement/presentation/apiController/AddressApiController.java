package com.example.ordermanagement.presentation.apiController;

import com.example.ordermanagement.application.service.AddressApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressApiController {

    private final AddressApiService addressApiService;

    public AddressApiController(AddressApiService addressApiService) {
        this.addressApiService = addressApiService;
    }

    @GetMapping("{seq}")
    public String findById(@PathVariable("seq") Long seq){
        return addressApiService.findBySeq(seq);
    }
}
