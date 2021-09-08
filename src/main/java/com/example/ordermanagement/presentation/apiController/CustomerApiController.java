package com.example.ordermanagement.presentation.apiController;

import com.example.ordermanagement.application.service.CustomerApiService;
import com.example.ordermanagement.domain.model.entity.Customer;
import com.example.ordermanagement.domain.model.entity.CustomerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@RestController()
@RequestMapping("/customer")
public class CustomerApiController {

    private final CustomerApiService customerApiService;
    private HttpHeaders headers;

    public CustomerApiController(CustomerApiService customerApiService) {
        this.customerApiService = customerApiService;
    }

    @PostConstruct
    public void setUp(){
        headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        headers.add("TimeStamp",LocalDateTime.now().toString());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> findAll(){
        List<Customer> customers = customerApiService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .headers(makeHeader())
                .body(customers);
    }

    @GetMapping("/{seq}")
    public ResponseEntity<Customer> findBySeq(@PathVariable Long seq){
        Customer customer = customerApiService.findBySeq(seq);
        return ResponseEntity.status(HttpStatus.OK)
                .headers(makeHeader())
                .body(customer);
    }

    @PostMapping("")
    public ResponseEntity<Customer> create(@Validated @RequestBody Customer resource){
        Customer customer = customerApiService.create(resource);
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(makeHeader())
                .body(customer);
    }

    @PatchMapping("")
    public ResponseEntity<Customer> modify(@Validated @RequestBody CustomerDto resource){
        Customer customer = customerApiService.modify(resource);
        return ResponseEntity.status(HttpStatus.OK)
                .headers(makeHeader())
                .body(customer);
    }

    @DeleteMapping("{seq}")
    public ResponseEntity<String> deleteBySeq(@PathVariable Long seq){
        customerApiService.deleteBySeq(seq);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .headers(makeHeader())
                .body("");
    }

    private HttpHeaders makeHeader(){
        headers.set("TimeStamp",LocalDateTime.now().toString());
        return headers;
    }
}
