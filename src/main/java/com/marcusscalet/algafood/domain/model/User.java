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

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "user_")
@Data
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
	@JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Set<Group> group = new HashSet<>();

	public boolean passwordMatches(String password) {
		return getPassword().equals(password);
	}

	public boolean passwordDoesNotMatch(String password) {
		return !passwordMatches(password);
	}
	
	public boolean addGroup(Group group) {
		return getGroup().add(group);
	}
	
	public boolean removeGroup(Group group) {
		return getGroup().remove(group);
	}
}
