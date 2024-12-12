package com.nos.home.user.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpFormDto {
    @NotBlank(message = "사용자 아이디는 공백일 수 없습니다")
    @Size(min = 5, max = 20, message = "아이디는 5자에서 20자 사이여야 합니다")
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$", message = "아이디는 영문, 숫자, 밑줄(_) 및 하이픈(-)만 포함할 수 있습니다")
    private String userId;

    @NotBlank(message = "이름은 공백일 수 없습니다.")
    @Size(min = 2, max = 10, message = "이름은 2자에서 10자 사이여야 합니다")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9_-]{2,10}$", message = "사용자 이름은 한글, 영문, 숫자, 밑줄(_) 및 하이픈(-)만 포함할 수 있습니다")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "유효한 이메일 주소를 입력하세요")
    private String email;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다")
    @Size(min = 8, max = 20, message = "비밀번호는 8자에서 20자 사이여야 합니다")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 영문자, 숫자, 특수문자(@$!%*#?&)를 포함하여 8자에서 20자 사이여야 합니다"
    )
    private String password;

    @NotBlank
    @Size(min = 8, max = 20, message = "비밀번호는 8자에서 20자 사이여야 합니다")
    private String passwordConfirm;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Size(min = 9, max = 11, message = "전화번호는 9자에서 11자 사이여야 합니다")
    private String phone;
}
