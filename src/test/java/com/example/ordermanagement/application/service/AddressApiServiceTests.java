package com.example.ordermanagement.application.service;


import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class AddressApiServiceTests {

    private AddressApiService addressApiService;

    @Before
    public void setUp(){
//        MockitoAnnotations.initMocks(this);
        addressApiService = new AddressApiService();
    }

    @Test
    public void findById(){
        assertThat(addressApiService.findBySeq(1L).equals(""));
    }
}