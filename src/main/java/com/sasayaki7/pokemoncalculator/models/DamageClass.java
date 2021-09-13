package com.sasayaki7.pokemoncalculator.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="move_damage_classes")
public class DamageClass {
	
	@Id
	private Long id;
	private String identifier;
	
	@OneToMany(mappedBy="damageClass", fetch=FetchType.LAZY)
	private List<Move>move;
	
	
	public DamageClass() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	
}
