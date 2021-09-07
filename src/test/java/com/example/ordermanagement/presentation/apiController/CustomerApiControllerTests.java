package com.example.ordermanagement.presentation.apiController;

import com.example.ordermanagement.application.service.CustomerApiService;
import com.example.ordermanagement.domain.model.entity.Customer;
import com.example.ordermanagement.domain.model.enumClass.GradeStatus;
import com.example.ordermanagement.presentation.optionAdvice.ErrorHandler;
import org.junit.jupiter.api.*;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .userId("aws1234")
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
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        given(customerApiService.create(any())).willReturn(customer);
        given(customerApiService.findBySeq(1L)).willReturn(customer);
        given(customerApiService.findAll()).willReturn(customers);
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
        @Test
        @DisplayName("성공 : 모든 정보 조회")
        public void findAll() throws Exception {
            mockMvc.perform(get("/customer/all"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(header().string("Content-Type",MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(header().exists("TimeStamp"))
                    .andExpect(content().string(
                            containsString("\"seq\":1")));
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
                            .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
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
                                .content("{\"userId\":\"\",\"password\":\"aws123456\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2,\"registeredAt\":\"2002-12-23\"}"))
                        .andExpect(status().is4xxClientError())
                        .andExpect(result -> Assertions.assertTrue(
                                result.getResolvedException()
                                        .getMessage()
                                        .contains("아이디를 입력해주세요")));
            }

            @Test
            @DisplayName("error : 비밀번호")
            public void errorPassword() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\",\"password\":\"\"," +
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
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
                                        "\"name\":\"\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2,\"registeredAt\":\"2002-12-23\"}"))
                        .andExpect(status().is4xxClientError())
                        .andExpect(result -> Assertions.assertTrue(
                                result.getResolvedException()
                                        .getMessage()
                                        .contains("이름을 입력해주세요")));
            }

            @Test
            @DisplayName("error : 닉네임")
            public void errorNickname() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError())
                        .andExpect(result -> Assertions.assertTrue(
                                result.getResolvedException()
                                        .getMessage()
                                        .contains("닉네임을 입력해주세요")));
            }
            @Test
            @DisplayName("error : 생일")
            public void errorBirthday() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError())
                        .andExpect(result -> Assertions.assertTrue(
                                result.getResolvedException()
                                        .getMessage()
                                        .contains("생년월일을 입력해주세요")));
            }
            @Test
            @DisplayName("error : 전화번호")
            public void errorPhoneNumber() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError())
                        .andExpect(result -> Assertions.assertTrue(
                                result.getResolvedException()
                                        .getMessage()
                                        .contains("전화번호를 입력해주세요")));
            }
            @Test
            @DisplayName("error : 이메일")
            public void errorEmail() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
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
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError())
                        .andExpect(result -> Assertions.assertTrue(
                                result.getResolvedException()
                                        .getMessage()
                                        .contains("주소를 입력해주세요")));
            }
            @Test
            @DisplayName("error : 등급")
            public void errorRole() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
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
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
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
            @DisplayName("error : 아이디 5자 미만 입력")
            public void limitSizeUnderId() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"1234\",\"password\":\"aws123456\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError())
                        .andExpect(result -> Assertions.assertTrue(
                                result.getResolvedException()
                                        .getMessage()
                                        .contains("아이디는 5자 이상 50자 이하 입력 해주세요")));
            }

            @Test
            @DisplayName("error : 아이디 50자 초과 입력")
            public void limitSizeUpId() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"01234567890123456789012345678900123456789012345678901234567890\"" +
                                        ",\"password\":\"aws123456\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError())
                        .andExpect(result -> Assertions.assertTrue(
                                result.getResolvedException()
                                        .getMessage()
                                        .contains("아이디는 5자 이상 50자 이하 입력 해주세요")));
            }

            @Test
            @DisplayName("error : 비밀번호 8자 미만 입력")
            public void limitSizeUnderPassword() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError())
                        .andExpect(result -> Assertions.assertTrue(
                                result.getResolvedException()
                                        .getMessage()
                                        .contains("비밀번호는 8자 이상 50자 이하 입력 해주세요")));
            }

            @Test
            @DisplayName("error : 비밀번호 50자 초과 입력")
            public void limitSizeUpPassword() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\"" +
                                        ",\"password\":\"01234567890123456789012345678900123456789012345678901234567890\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError())
                        .andExpect(result -> Assertions.assertTrue(
                                result.getResolvedException()
                                        .getMessage()
                                        .contains("비밀번호는 8자 이상 50자 이하 입력 해주세요")));
            }

            @Test
            @DisplayName("error : 이름 100자 이상 입력")
            public void limitSizeName() throws Exception {
                mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
                                "\"name\":\"0123456789012345678901234567890012345678901234567890123456789001234567890123456789012345678900123456789012345678901234567890\"," +
                                "\"nickname\":\"aws동이\"," +
                                "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError())
                        .andExpect(result -> Assertions.assertTrue(
                                result.getResolvedException()
                                        .getMessage()
                                        .contains("이름은 1~100자 사이에 입력해주세요")));
            }

            @Test
            @DisplayName("error : 닉네임 20자 이상 입력")
            public void limitSizeNickname() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
                                        "\"name\":\"aws동\"," +
                                        "\"nickname\":\"012345678901234567890\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError())
                        .andExpect(result -> Assertions.assertTrue(
                                result.getResolvedException()
                                        .getMessage()
                                        .contains("닉네임은 1~20자 사이에 입력해주세요")));
            }
            @Test
            @DisplayName("error : 생년월일 년도 초과 입력")
            public void errorBirthday() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"20204-02-04\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError());
            }
            @Test
            @DisplayName("error : 전화번호 초과 입력")
            public void errorPhoneNumber() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"012-34565-55789\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":2}"))
                        .andExpect(status().is4xxClientError())
                        .andExpect(result -> Assertions.assertTrue(
                                result.getResolvedException()
                                        .getMessage()
                                        .contains("전화번호 형식에 맞게 작성해주세요(010-0000-0000)")));
            }
            @Test
            @DisplayName("error : 등급 초과입력")
            public void errorRole() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"dfs\",\"role\":2}"))
                        .andExpect(status().is4xxClientError());
            }
            @Test
            @DisplayName("error : grade 초과입력")
            public void errorGrade() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":26}"))
                        .andExpect(status().is4xxClientError())
                        .andExpect(result -> Assertions.assertTrue(
                                result.getResolvedException()
                                        .getMessage()
                                        .contains("Server Error - 잘못된 유저정보가 들어왔습니다.")));
            }
        }

        @Nested
        @DisplayName("error : 잘못된 입력")
        public class ErrorFault{

            @Test
            @DisplayName("error : 생년월일 번호만 입력")
            public void errorBirth() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"35345435\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":26}"))
                        .andExpect(status().is4xxClientError());
            }

            @Test
            @DisplayName("error : 생년월일 잘못된 년도 입력")
            public void errorBirthYear() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"0-02-13\",\"phoneNumber\":\"010-0110-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":26}"))
                        .andExpect(status().is4xxClientError());
            }

            @Test
            @DisplayName("error : 전화번호 - 빼고 입력")
            public void errorPhoneNumberForget() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2000-02-13\",\"phoneNumber\":\"01001100220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":26}"))
                        .andExpect(status().is4xxClientError());
            }
            @Test
            @DisplayName("error : 전화번호 형식보다 많은 번호 입력")
            public void errorPhoneNumberMany() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2000-02-13\",\"phoneNumber\":\"010-01107-0220\"," +
                                        "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":26}"))
                        .andExpect(status().is4xxClientError());
            }
            @Test
            @DisplayName("error : 이메일 번호만 입력")
            public void errorEmailOnlyNumber() throws Exception {
                mockMvc.perform(post("/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
                                        "\"name\":\"aws동\",\"nickname\":\"aws동이\"," +
                                        "\"birthday\":\"2000-02-13\",\"phoneNumber\":\"010-1107-0220\"," +
                                        "\"email\":\"3453@243242.435\",\"address\":\"서울 마포구\"," +
                                        "\"grade\":\"BRONZE\",\"role\":26}"))
                        .andExpect(status().is4xxClientError());
            }
        }
    }


    @Nested
    @DisplayName("user 정보를 수정한다.")
    public class modify {
        @Test
        @DisplayName("성공 : 단일정보 수정")
        public void modifyData() throws Exception {
            Customer customer = Customer.builder()
                    .seq(1L)
                    .userId("aws1234")
                    .password("aws1234")
                    .name("aws동sss")
                    .nickname("aws동이")
                    .birthday(LocalDate.of(2012, 05, 23))
                    .phoneNumber("010-0110-0220")
                    .email("aws@naver.com")
                    .address("서울 마포구")
                    .grade(GradeStatus.BRONZE)
                    .role(2)
                    .registeredAt(LocalDate.of(2002, 12, 23))
                    .build();
            given(customerApiService.modify(any())).willReturn(customer);

            mockMvc.perform(patch("/customer")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"seq\":1," +
                                    "\"userId\":\"aws1234\",\"password\":\"aws123456\"," +
                                    "\"name\":\"aws동sss\",\"nickname\":\"aws동이\"," +
                                    "\"birthday\":\"2012-05-23\",\"phoneNumber\":\"010-0110-0220\"," +
                                    "\"email\":\"aws@naver.com\",\"address\":\"서울 마포구\"," +
                                    "\"grade\":\"BRONZE\",\"role\":2}"))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().string(
                            containsString("\"name\":\"aws동sss\"")));
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