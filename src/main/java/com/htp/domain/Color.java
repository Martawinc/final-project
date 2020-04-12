package com.htp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "color")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"id", "blankShirts"})
@ToString(exclude = "blankShirts")
public class Color {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "color_name")
  private String colorName;

  @OneToMany(mappedBy = "color", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonBackReference
  private Set<BlankShirt> blankShirts = Collections.emptySet();
}
