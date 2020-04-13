package com.htp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "m_user")
@NoArgsConstructor()
@Data
@EqualsAndHashCode(exclude = {"id", "password","orders"})
@ToString(exclude = {"password", "orders"})
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "login")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "full_name")
  private String fullName;

  @Column(name = "birth_date")
  @Temporal(TemporalType.DATE)
  private Date birthDate;

  @Column(name = "city")
  private String city;

  @Column(name = "street")
  private String street;

  @Column(name = "zip")
  private Long zip;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "mail")
  private String mail;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "role_id")
  @JsonManagedReference
  private Role role;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonBackReference
  private Set<Order> orders = Collections.emptySet();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(role);
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
    return true;
  }
}
