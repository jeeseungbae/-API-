package com.example.ordermanagement.domain.model.entity;

import com.example.ordermanagement.domain.model.enumClass.GradeStatus;
import com.example.ordermanagement.domain.model.enumClass.GradeStatusConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 5,max = 50,message = "아이디는 5자 이상 입력 해주세요")
    private String id;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8,max = 50,message = "비밀번호는 8자 이상 입력 해주세요")
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    @Size(min = 1,max = 100)
    private String name;

    @NotBlank(message = "닉네임을 입력해주세요")
    @Size(min = 1,max = 20)
    private String nickname;

    @NotNull(message = "생년월일을 입력해주세요")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotBlank(message = "전화번호를 입력해주세요")
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$"
            ,message = "전화번호 형식에 맞게 작성해주세요(010-0000-0000)")
    private String phoneNumber;

    @NotBlank(message = "이메일을 입력해주세요")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$"
            ,message = "이메일 형식에 맞게 작성해주세요")
    private String email;

    @NotBlank(message = "주소를 입력해주세요")
    private String address;

    @Convert(converter = GradeStatusConverter.class)
    private GradeStatus grade;

    @NotNull
    @Digits(integer = 5,fraction = 0,message = "Server Error - 잘못된 유저정보가 들어왔습니다.")
    private Integer role;

    @CreationTimestamp
    private LocalDate registeredAt;
}
