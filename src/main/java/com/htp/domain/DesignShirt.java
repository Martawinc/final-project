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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "design_shirt")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"id", "orders"})
@ToString(exclude = "orders")
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

	@Column(name = "total_price")
	private float totalPrice;

	@Column(name = "creation_date")
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	@Column(name = "is_deleted")
	private boolean deleted;

	@ManyToMany(mappedBy = "designShirts", fetch = FetchType.LAZY)
	@JsonBackReference
	private Set<Order> orders = Collections.emptySet();
}
