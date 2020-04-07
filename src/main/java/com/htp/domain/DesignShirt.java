package com.htp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "design_shirt")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"id, shirt, ordersWithDesignShirts"})
@ToString(exclude = {"shirt, ordersWithDesignShirts"})
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

  @ManyToMany(mappedBy = "designShirts", fetch = FetchType.EAGER)
  @JsonBackReference
  private Set<Order> orders = Collections.emptySet();
}
