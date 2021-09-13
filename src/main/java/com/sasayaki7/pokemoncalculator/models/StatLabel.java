package com.sasayaki7.pokemoncalculator.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name="stats")
@Entity
public class StatLabel {
	
	@Id
	private Long id;
	
	private String identifier;
	
	@OneToMany(mappedBy="label", fetch=FetchType.LAZY)
	private List<Stat> stat;
	
	public StatLabel() {
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

	public List<Stat> getStat() {
		return stat;
	}

	public void setStat(List<Stat> stat) {
		this.stat = stat;
	}
	
	
}
