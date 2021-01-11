package com.marcusscalet.algafood.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "ggroup")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Group {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String name;

	@ManyToMany
	@JoinTable(name = "ggroup_permission", joinColumns = @JoinColumn(name = "ggroup_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private Set<Permission> permissions = new HashSet<>();
	
	public boolean removePermission(Permission permission) {
		return getPermissions().remove(permission);
	}
	
	public boolean addPermission(Permission permission) {
		return getPermissions().add(permission);
	}
}
