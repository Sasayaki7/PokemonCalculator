package com.sasayaki7.pokemoncalculator.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="types")
public class Type {
	
	
	@Id
	private Long id;
	
	@Transient
	private List<Type> doubleDamageFrom;
	@Transient

	private List<Type> doubleDamageTo;
	
	@Transient
	private List<Type> halfDamageFrom;

	@Transient
	private List<Type> halfDamageTo;

	@Transient
	private List<Type> noDamageFrom;

	@Transient
	private List<Type> noDamageTo;
	private String identifier;
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="pokemon_types",
		joinColumns=@JoinColumn(name="type_id"),
		inverseJoinColumns=@JoinColumn(name="pokemon_id")
	)
	private List<Pokemon> pokemons;
	
	
	@OneToMany(mappedBy="type", fetch=FetchType.LAZY)
	private List<Move> moves;
	
	
	
	@OneToMany(mappedBy="damageType", fetch=FetchType.LAZY)
	private List<TypeEfficacy> effectiveTo;
	
	@OneToMany(mappedBy="targetType", fetch=FetchType.LAZY)
	private List<TypeEfficacy> targetedFrom;
	
	
	
	public Type() {
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

	public List<Pokemon> getPokemons() {
		return pokemons;
	}

	public void setPokemons(List<Pokemon> pokemons) {
		this.pokemons = pokemons;
	}

	public boolean isTypeName(String name) {
		return name.equals(identifier);
	}
	
	public int getEffectivenessTo(Type t) {
		for (TypeEfficacy x: effectiveTo) {
			if (x.getTargetType().equals(t)) {
				return x.getDamageFactor();
			}
		}
		return 100;
	}
	
	public int getEffectivenessFrom(Type t) {
		for (TypeEfficacy x: targetedFrom) {
			if (x.getDamageType().equals(t)) {
				return x.getDamageFactor();
			}
		}
		return 100;
	}
	
}
