package com.supience.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    
    @NotBlank(message = "아이디는 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "아이디는 영문과 숫자로 4~20자리여야 합니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자리여야 합니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$", 
             message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수입니다.")
    @Pattern(regexp = "^[가-힣]{2,10}$", message = "이름은 한글로 2~10자리여야 합니다.")
    private String name;
} 