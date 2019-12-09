package com.somn.model;

import com.somn.model.status.UserStatus;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Java Bean that represents User.
 *
 * @author evakule
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity implements UserDetails {
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "encrypted_password")
  private String encryptedPassword;
  @Column(name = "user_status")
  @Enumerated(EnumType.STRING)
  private UserStatus userStatus;
  
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "FK_USER_ROLE",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id")})
  private Set<RoleEntity> roles;
  
  @OneToMany(
      mappedBy = "userEntity",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL)
  private List<AccountEntity> accounts;
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }
  
  @Override
  public String getPassword() {
    return encryptedPassword;
  }
  
  @Override
  public String getUsername() {
    return firstName;
  }
  
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  
  @Override
  public boolean isEnabled() {
    return userStatus.equals(UserStatus.ACTIVE);
  }
}
