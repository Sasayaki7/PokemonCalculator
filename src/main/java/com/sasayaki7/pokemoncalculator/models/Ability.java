package com.sasayaki7.pokemoncalculator.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Table(name="abilities")
@Entity
public class Ability {
	
	@Id
	private Long id;
	private String identifier;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="pokemon_abilities",
		joinColumns=@JoinColumn(name="ability_id"),
		inverseJoinColumns=@JoinColumn(name="pokemon_id")
	)
	private List<Pokemon> pokemon;
	
	public Ability() {
	}
	
	public Ability(String name) {
		this.identifier = name;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String name) {
		this.identifier = name;
	}

	public String getIdentifierCleaned() {
		String repS = identifier.replace('-', ' ');
		repS = repS.substring(0, 1).toUpperCase()+repS.substring(1);
		for(int i = 1; i < repS.length(); i++) {
			if (repS.charAt(i) == ' ') {
				repS = repS.substring(0, i+1)+repS.substring(i+1, i+2).toUpperCase()+repS.substring(i+2);
			}
		}
		return repS;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Pokemon> getPokemon() {
		return pokemon;
	}

	public void setPokemon(List<Pokemon> pokemon) {
		this.pokemon = pokemon;
	}

	
}
