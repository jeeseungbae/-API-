package com.example.ordermanagement.presentation.apiController;

import com.example.ordermanagement.exception.NoSuchDataException;
import com.example.ordermanagement.application.service.AddressApiService;
import com.example.ordermanagement.domain.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
//ApplicationContext를 만들고 관리하는 작업을 @RunWith(SpringRunner.class)에 설정된 class로 이용하겠다는 뜻
@WebMvcTest(AddressApiController.class)
@DisplayName("AddressApiControllerTests")
public class AddressApiControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddressApiService addressApiService;

    @BeforeEach
    public void setUp(){
        givenAddress();
        givenError();
    }

    private void givenAddress(){
        Address address = Address.builder()
                .seq(1L)
                .content("서울 중랑구")
                .distinction(1)
                .build();
        given(addressApiService.findBySeq(1L))
                .willReturn(address);
    }

    private void givenError(){
        given(addressApiService.findBySeq(10L))
                .willThrow(new NoSuchDataException());
    }

    @Nested
    @DisplayName("정보를 조회한다.")
    public class FindAddress{
        @Test
        @DisplayName("단일 정보 조회 성공")
        public void findBySeq() throws Exception {
            mvc.perform(get("/address/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(
                            containsString("\"content\":\"서울 중랑구\"")))
                    .andExpect(content().string(
                            containsString("\"distinction\":1")))
                    .andExpect(content().string(
                            containsString("\"seq\":1")));
        }
        @Test
        @DisplayName("잘못된 조회 - 4xx 에러")
        public void NotFindBySeq() throws Exception {
            mvc.perform(get("/address/10"))
                    .andExpect(status().is4xxClientError());
        }
    }

}