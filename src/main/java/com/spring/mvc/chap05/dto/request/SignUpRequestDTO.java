package com.spring.mvc.chap05.dto.request;

import com.spring.mvc.chap05.entity.Member;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDTO {
    @NotBlank
    @Size(min = 4, max = 14)
    private String account;
    @NotBlank
    @Size(min = 8)
    private String password;
    @NotBlank
    @Size(min = 2, max = 8)
    private String name;
    @NotBlank
    @Email //이메일 패턴검증(@가 들어갔는지 등)
    private String email;

    // 프로필 사진 파일
    // sign-up.jsp에서 input에 name과 이름을 맞춰야함
    private MultipartFile profileImage;

    // 엔터티로 변환하는 유틸메서드
    public Member toEntity(PasswordEncoder encoder, String savePath) {
        return Member.builder()
                .account(account)
                .password(encoder.encode(password))
                .email(email)
                .name(name)
                .profileImage(savePath)
                .build()
                ;
    }
}
