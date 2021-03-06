package com.somn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * Java Bean that represents Role.
 *
 * @author evakule
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity implements GrantedAuthority {
  @Column(name = "role_name")
  private String roleName;
  
  @Override
  public String getAuthority() {
    return getRoleName();
  }
}
