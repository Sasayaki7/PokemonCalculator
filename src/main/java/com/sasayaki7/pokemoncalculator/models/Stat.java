package com.sasayaki7.pokemoncalculator.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="pokemon_stats")
public class Stat {
	
	@Id
	private Long id;
	
	private int baseStat;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="stat_id")
	private StatLabel label;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pokemon_id")
	private Pokemon pokemon;
	
	@Transient
	private int effort;
	
	@Transient
	private int iv=31;
	
	
	@OneToMany(mappedBy="increasedStat", fetch=FetchType.LAZY)
	private List<Nature> boostingNatures;

	@OneToMany(mappedBy="decreasedStat", fetch=FetchType.LAZY)
	private List<Nature> hinderingNatures;

	
	public Stat() {
	}


	public int getBaseStat() {
		return baseStat;
	}

	public void setBaseStat(int baseStat) {
		this.baseStat = baseStat;
	}

	public int getEffort() {
		return effort;
	}

	public void setEffort(int effort) {
		this.effort = effort;
	}

	public int getIv() {
		return iv;
	}

	public void setIv(int iv) {
		this.iv = iv;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatLabel getLabel() {
		return label;
	}

	public void setLabel(StatLabel label) {
		this.label = label;
	}

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}
	
	public int getStatVal() {
		if(this.label.getIdentifier().equals("hp")) {
			return (int)(Math.floor(((2*this.baseStat+this.iv+Math.floor(this.effort/4))*this.pokemon.getLevel())/100)+this.pokemon.getLevel()+10);
		}
		else {
			return (int)((Math.floor(((2*this.baseStat+this.iv+Math.floor(this.effort/4))*this.pokemon.getLevel())/100)+5));
		}
	}


	public List<Nature> getBoostingNatures() {
		return boostingNatures;
	}


	public void setBoostingNatures(List<Nature> boostingNatures) {
		this.boostingNatures = boostingNatures;
	}


	public List<Nature> getHinderingNatures() {
		return hinderingNatures;
	}


	public void setHinderingNatures(List<Nature> hinderingNatures) {
		this.hinderingNatures = hinderingNatures;
	}
	
	public double getNatureBoost(Nature n) {
		if (n == null) {
			return 1.0;
		}
		if (this.boostingNatures.contains(n) && this.hinderingNatures.contains(n)) {
			return 1.0;
		}
		else if (this.boostingNatures.contains(n)) {
			return 1.1;
		}
		else if (this.hinderingNatures.contains(n)) {
			return 0.9;
		}
		else {
			return 1.0;
		}
	}
	
	
	
	
}
