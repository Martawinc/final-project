package com.htp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "colour")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"id", "blankShirts"})
@ToString(exclude = "blankShirts")
public class Colour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "colour_name")
    private String colorName;

    @OneToMany(mappedBy = "colour", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<BlankShirt> blankShirts;
}
