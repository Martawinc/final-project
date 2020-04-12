package com.htp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "blank_shirt")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"id", "designShirts"})
@ToString(exclude = "designShirts")
public class BlankShirt {

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

  @OneToMany(mappedBy = "shirt", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonBackReference
  Set<DesignShirt> designShirts = Collections.emptySet();

  public enum Size {
    XS,
    S,
    M,
    L,
    XL,
    XXL
  }
}
