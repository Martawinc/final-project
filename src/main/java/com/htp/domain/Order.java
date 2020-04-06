package com.htp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "m_order")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "id") // +exclude Collections
@ToString() // +exclude Collections
public class Order {

  @ManyToMany
  @JsonBackReference
  @JoinTable(
      name = "order_design_shirt",
      joinColumns = @JoinColumn(name = "order_id"),
      inverseJoinColumns = @JoinColumn(name = "design_shirt_id"))
  public Set<DesignShirt> designShirts;
    @ManyToMany
    @JsonBackReference
    @JoinTable(name = "order_design_shirt",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "design_shirt_id"))
    public Set<DesignShirt> designShirtsInOrder;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "city")
  private String city;
  @Column(name = "street")
  private String street;
  @Column(name = "zip")
  private Long zip;
  @Column(name = "card_number")
  private String cardNumber;
  @Column(name = "card_expiration")
  private String cardExpiration;
  @Column(name = "cardCVV")
  private String cardCVV;
  @Column(name = "placed_at")
  @Temporal(TemporalType.DATE)
  private Date placed_at;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  @JsonManagedReference
  private User user;
}
