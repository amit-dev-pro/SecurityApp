package com.amit.SecurityApp.entities;


import com.amit.SecurityApp.entities.enums.Role;
import com.amit.SecurityApp.utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated
    private Set<Role> roles;


    public Collection<? extends GrantedAuthority> getAuthorities(){
        Set<SimpleGrantedAuthority> authorities=new HashSet<>();
        roles.forEach(
                role -> {
                    Set<SimpleGrantedAuthority> permission= PermissionMapping.getAuthoritiesForRole(role);
                    authorities.addAll(permission);
                    authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));

                }
        );
        return authorities;
    }


    public String getPassword() {
        return this.password;
    }


    public String getUsername() {
        return this.email;
    }

}
