package com.example.ordermanagement.presentation.apiController;


import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressApiController.class)
public class AddressApiControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("서버에서 요청에 성공한다.")
    public void findById() throws Exception {
        mvc.perform(get("/address/1"))
                .andExpect(status().isOk());
    }
}