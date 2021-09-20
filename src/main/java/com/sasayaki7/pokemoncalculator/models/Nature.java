package com.sasayaki7.pokemoncalculator.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name="natures")
@Entity
public class Nature {
	
	@Id
	private Long id;
	private String identifier;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="decreased_stat_id")
	private Stat decreasedStat;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="increased_stat_id")
	private Stat increasedStat;
	
	public Nature() {
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

	public void setIdentifier(String identifer) {
		this.identifier = identifer;
	}

	public String getIdentifierCleaned() {
		return identifier.substring(0, 1).toUpperCase()+identifier.substring(1);
	}
	
	public Stat getDecreasedStat() {
		return decreasedStat;
	}

	public void setDecreasedStat(Stat decreasedStat) {
		this.decreasedStat = decreasedStat;
	}

	public Stat getIncreasedStat() {
		return increasedStat;
	}

	public void setIncreasedStat(Stat increasedStat) {
		this.increasedStat = increasedStat;
	}
	
	
}
