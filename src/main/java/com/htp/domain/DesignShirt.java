package com.htp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "design_shirt")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "id") // +exclude Collections
@ToString() // +exclude Collections
public class DesignShirt {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "blank_shirt_id")
  @JsonManagedReference
  private BlankShirt shirt;

  @Column(name = "text")
  private String text;

  @Column(name = "picture")
  private String picture;

  @ManyToMany
  @JsonBackReference
  @JoinTable(name = "order_design_shirt",
          joinColumns = @JoinColumn(name = "design_shirt_id"),
          inverseJoinColumns = @JoinColumn(name = "order_id"))
  public Set<Order> orders;
}
