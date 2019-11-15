package com.somn.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



/**
 * Base class with, property ID.
 *
 * @author evakule
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "accounts")
public class AccountEntity extends BaseEntity {

  @Column(name = "balance")
  private Integer balance;
  @Column(name = "account_status")
  private String accountStatus;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = true)
  private UserEntity userEntity;
  
  public AccountEntity(Integer balance, String accountStatus) {
    this.balance = balance;
    this.accountStatus = accountStatus;
  }
}
