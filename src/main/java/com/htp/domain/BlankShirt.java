package com.htp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "blank_shirt")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"id", "designShirts"})
@ToString(exclude = "designShirts")
public class BlankShirt {

  @OneToMany(mappedBy = "shirt", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JsonBackReference
  Set<DesignShirt> designShirts;
  @Id
  @Column(name = "id")
  private String id;
  @Column(name = "size")
  @Enumerated(value = EnumType.STRING)
  private Size size;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "color_id")
  @JsonManagedReference
  private Color color;
  @Column(name = "quantity")
  private int quantity;
  @Column(name = "price")
  private float price;

  public enum Size {
    XS, S, M, L, XL, XXL
  }
}
