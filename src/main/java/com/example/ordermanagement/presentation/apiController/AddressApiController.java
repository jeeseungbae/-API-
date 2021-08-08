package com.example.ordermanagement.presentation.apiController;

import com.example.ordermanagement.application.service.AddressApiService;
import com.example.ordermanagement.domain.model.Address;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressApiController {

    private final AddressApiService addressApiService;

    public AddressApiController(AddressApiService addressApiService) {
        this.addressApiService = addressApiService;
    }

    @GetMapping("/{seq}")
    public Address findById(@PathVariable("seq") Long seq){
        return addressApiService.findBySeq(seq);
    }

    @GetMapping("/all")
    public List<Address> findAll(){
        return addressApiService.findAll();
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Validated @RequestBody final Address resource) throws URISyntaxException {

        Address address = addressApiService.create(
                Address.builder()
                        .content(resource.getContent())
                        .distinction(resource.getDistinction())
                        .build());

        URI location = new URI("/address/"+address.getSeq());
        return ResponseEntity.created(location).body("{}");
    }
}
