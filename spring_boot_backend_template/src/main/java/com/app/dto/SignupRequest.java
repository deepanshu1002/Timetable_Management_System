package com.app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SignupRequest {
  	
  private Long userId;
  @NotBlank(message = "first name can't be blank")
  private String firstName;
  @Length(min=3,max=20, message="Invalid length	of last name")
  private String lastName;
  @Email
  private String email;
  @NotBlank
  private String mobileNo;
  @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[#@$*]).{5,20})",message = "invalid password format")
  private String password;
//  private Long roleId;
  private Long deptId;
  
}
