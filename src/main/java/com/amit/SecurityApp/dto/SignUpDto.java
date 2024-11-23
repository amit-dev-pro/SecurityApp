package com.amit.SecurityApp.dto;

import com.amit.SecurityApp.entities.enums.Permission;
import com.amit.SecurityApp.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {

    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
    private Set<Permission> permissions;
}
