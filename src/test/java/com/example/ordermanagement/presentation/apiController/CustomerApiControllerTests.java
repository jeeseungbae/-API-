package com.example.ordermanagement.presentation.apiController;

import com.example.ordermanagement.application.service.CustomerApiService;
import com.example.ordermanagement.domain.model.entity.Customer;
import com.example.ordermanagement.domain.model.enumClass.GradeStatus;
import org.junit.jupiter.api.*;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerApiController.class)
class CustomerApiControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerApiService customerApiService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        createData();
    }

    private void createData(){
        Customer customer = Customer.builder()
                .seq(1L)
                .id("aws1234")
                .password("aws1234")
                .name("aws동")
                .nickname("aws동이")
                .birthday(LocalDate.of(2012,05,23))
                .phoneNumber("010-0110-0220")
                .email("aws@naver.com")
                .address("서울 마포구")
                .grade(GradeStatus.BRONZE)
                .role(2)
                .registeredAt(LocalDate.of(2002,12,23))
                .build();

        given(customerApiService.create(any())).willReturn(customer);
        given(customerApiService.findBySeq(1L)).willReturn(customer);
    }

    @Nested
    @DisplayName("user 정보를 조회한다.")
    public class Find{

        @Test
        @DisplayName("성공 : 단일 정보 조회")
        public void findBySeq() throws Exception {
            mockMvc.perform(get("/customer/1"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().string(
                            containsString("\"name\":\"aws동\"")))
                    .andExpect(content().string(
                            containsString("\"grade\":\"BRONZE\"")))
                    .andExpect(content().string(
                            containsString("\"address\":\"서울 마포구\"")));

            verify(customerApiService).findBySeq(any());
        }
    }

    @Nested
    @DisplayName("user 정보를 저장한다.")
    public class save {

        @Test
        @DisplayName("성공 : 단일 정보 저장")
        public void createData() throws Exception {
            mockMvc.perform(post("/customer")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"id\":\"aws1234\",\"password\":\"aws1234\"," +
                                    "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                    "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                    "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                    "\"grade\":\"BRONZE\",\"role\":2}"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().string(
                            containsString("\"name\":\"aws동\"")))
                    .andExpect(content().string(
                            containsString("\"grade\":\"BRONZE\"")))
                    .andExpect(content().string(
                            containsString("\"address\":\"서울 마포구\"")))
                    .andExpect(content().string(
                            containsString("\"role\":2")));

            verify(customerApiService).create(any());
        }

        @Nested
        @DisplayName("error : 정보 입력 x")
        public class ErrorInput {
            @Test
            @DisplayName("error : 아이디")
            public void ErrorId() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"\",\"password\":\"aws1234\"," +
                                        "\"name\":\"\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2,\"registeredAt\":\"2002-12-23\"}"))
                        .andExpect(status().is4xxClientError());
            }

            @Test
            @DisplayName("error : 비밀번호")
            public void errorPassword() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"aws1234\",\"password\":\"\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError());
            }
            @Test
            @DisplayName("error : 이름")
            public void ErrorName() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"aws1234\",\"password\":\"aws1234\"," +
                                        "\"name\":\"\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2,\"registeredAt\":\"2002-12-23\"}"))
                        .andExpect(status().is4xxClientError());
            }

            @Test
            @DisplayName("error : 닉네임")
            public void errorNickname() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"aws1234\",\"password\":\"aws1234\"," +
                                        "\"name\":\"\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError());
            }
            @Test
            @DisplayName("error : 생일")
            public void errorBirthday() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"aws1234\",\"password\":\"aws1234\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError());
            }
            @Test
            @DisplayName("error : 전화번호")
            public void errorPhoneNumber() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"aws1234\",\"password\":\"aws1234\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError());
            }
            @Test
            @DisplayName("error : 이메일")
            public void errorEmail() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"aws1234\",\"password\":\"aws1234\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError());
            }
            @Test
            @DisplayName("error : 주소")
            public void errorAddress() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"aws1234\",\"password\":\"aws1234\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError());
            }
            @Test
            @DisplayName("error : 등급")
            public void errorRole() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"aws1234\",\"password\":\"aws1234\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":}"))
                        .andExpect(status().is4xxClientError());
            }
            @Test
            @DisplayName("error : grade")
            public void errorGrade() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"aws1234\",\"password\":\"aws1234\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"\",\"role\":2}"))
                        .andExpect(status().is4xxClientError());
            }
        }

        @Nested
        @DisplayName("error : 길이 제한이상 입력")
        public class ErrorSize{
            @Test
            @DisplayName("error : 아이디 30자 초과 입력")
            public void limitSizeId() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":\"0123456789012345678901234567890\",\"password\":\"aws1234\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError());
            }
            @Test
            @DisplayName("error : 이름 100자 이상 입력")
            public void limitSizeName() throws Exception {
                mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"aws1234\",\"password\":\"aws1234\"," +
                                "\"name\":\"0123456789012345678901234567890012345678901234567890123456789001234567890123456789012345678900123456789012345678901234567890\"," +
                                "\"nickname\":\"aws동이\"," +
                                "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError());
            }
        }

    }
}

//{
//        "id":"aws1234",
//        "password":"aws1234",
//        "name":"aws동",
//        "nickname":"aws동이",
//        "birthday":"2012-05-23",
//        "phoneNumber":"010-0110-0220",
//        "email":"aws@naver.com",
//        "address":"서울 마포구",
//        "role":"GRANDMASTER",
//        "grade":2,
//        "registeredAt":"2002-12-23"
//        }