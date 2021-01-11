package com.marcusscalet.algafood.domain.model;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime registrationDate;

	@ManyToMany
	@JoinTable(name = "user_ggroup", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "ggroup_id"))
	private Set<Group> ggroup = new HashSet<>();

	public boolean passwordMatches(String password) {
		return getPassword().equals(password);
	}

	public boolean passwordDoesNotMatch(String password) {
		return !passwordMatches(password);
	}
	
	public boolean addGroup(Group group) {
		return getGgroup().add(group);
	}
	
	public boolean removeGroup(Group group) {
		return getGgroup().remove(group);
	}
}
