package com.htp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "design_shirt")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"id, shirt, orders"})
@ToString(exclude = {"shirt, orders"})
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

  @Column(name = "image_link")
  private String imageLink;

  @ManyToMany(mappedBy = "designShirts", fetch = FetchType.LAZY)
  @JsonBackReference
  private Set<Order> orders = Collections.emptySet();
}
