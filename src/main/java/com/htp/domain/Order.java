package com.htp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "m_order")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"id", "designShirts"})
@ToString(exclude = "designShirts")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "city")
	private String city;

	@Column(name = "street")
	private String street;

	@Column(name = "zip")
	private String zip;

	@JsonIgnore
	@Column(name = "card_number")
	private String cardNumber;

	@JsonIgnore
	@Column(name = "card_expiration")
	private String cardExpiration;

	@JsonIgnore
	@Column(name = "cardCVV")
	private String cardCVV;

	@Column(name = "placed_at")
	@Temporal(TemporalType.DATE)
	private Date placedAt;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	@JsonManagedReference
	private User user;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonManagedReference
	@JoinTable(
			name = "order_design_shirt",
			joinColumns = @JoinColumn(name = "order_id"),
			inverseJoinColumns = @JoinColumn(name = "design_shirt_id"))
	private Set<DesignShirt> designShirts = Collections.emptySet();
}
