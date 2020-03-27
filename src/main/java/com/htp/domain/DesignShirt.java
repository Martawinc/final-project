package com.htp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "design_shirt")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "id") // +exclude Collections
@ToString() //+exclude Collections
public class DesignShirt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blank_shirt_id")
    private BlankShirt shirt;

    @Column(name = "text")
    private String text;

    @Column(name = "picture")
    private String picture;
}
