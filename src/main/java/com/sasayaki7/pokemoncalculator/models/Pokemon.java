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

@Table(name="pokemon")
@Entity
public class Pokemon {
	
	
	private String identifier;
	
	@Id
	private Long id;
	
	
	@OneToMany(mappedBy="pokemon", fetch=FetchType.LAZY)
	private List<Stat> stats;
	
	@OneToMany(mappedBy="pokemon", fetch=FetchType.LAZY)
	private List<LegacyType> legacyTypes;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="pokemon_abilities",
		joinColumns=@JoinColumn(name="pokemon_id"),
		inverseJoinColumns=@JoinColumn(name="ability_id")
	)
	private List<Ability> abilities;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="pokemon_types",
		joinColumns=@JoinColumn(name="pokemon_id"),
		inverseJoinColumns=@JoinColumn(name="type_id")
	)
	private List<Type> types;
	@Transient
	private List<Type> oldTypes;
	private int weight;
	

	
	@Transient
	private int level;
	@Transient
	private Ability ability;
	@Transient
	private String item;
	
	@Transient
	private Nature nature;
	
	@Transient
	private String status;
	
	public Pokemon() {
	}
	
	public Pokemon(String name, Long id) {
		this.identifier = name;
		this.id = id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
		
	public List<LegacyType> getLegacyTypes() {
		return legacyTypes;
	}

	public void setLegacyTypes(List<LegacyType> legacyTypes) {
		this.legacyTypes = legacyTypes;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getIdentifierCleaned() {
		return identifier.substring(0, 1).toUpperCase()+identifier.substring(1);
	}
	
	public String getIdentifierNoSpace() {
		if (identifier.indexOf("tapu") != -1) {
			return identifier.replace("-", "").replace(" ", "");
		}
		else if (identifier.equals("type-null")||identifier.equals("mr-rime")||identifier.equals("mime-jr")) {
			return identifier.replace("-", "");
		}
		else if (identifier.indexOf("mr-mime") != -1) {
			return identifier.replace("mr-mime", "mrmime");
		}
		else if (identifier.indexOf("mega-x") != -1) {
			return identifier.replace("mega-x", "megax");
		}
		else if (identifier.indexOf("mega-y") != -1) {
			return identifier.replace("mega-y", "megay");
		}
		else if (identifier.indexOf("rapid-strike") != -1) {
			return identifier.replace("rapid-strike", "rapidstrike");
		}
		else if (identifier.indexOf("single-strike") != -1) {
			return identifier.replace("single-strike", "singlestrike");
		}
		else if (identifier.indexOf("-incarnate") != -1) {
			return identifier.replace("-incarnate", "");

		}
		else {
			return identifier;
		}
	}
	
	public String getItemCleaned() {
		String repS = item.replace('-', ' ');
		if (item.length() > 0) {
			repS = repS.substring(0, 1).toUpperCase()+repS.substring(1);
			for(int i = 1; i < repS.length(); i++) {
				if (repS.charAt(i) == ' ') {
					repS = repS.substring(0, i+1)+repS.substring(i+1, i+2).toUpperCase()+repS.substring(i+2);
				}
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

	public List<Stat> getStats() {
		return stats;
	}

	public void setStats(List<Stat> stats) {
		this.stats = stats;
	}

	public List<Ability> getAbilities() {
		return abilities;
	}

	public void setAbilities(List<Ability> abilities) {
		this.abilities = abilities;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Ability getAbility() {
		return ability;
	}

	public void setAbility(Ability ability) {
		this.ability = ability;
	}

	public List<Type> getTypes() {
		return types;
	}

	public void setTypes(List<Type> types) {
		this.types = types;
	}
	
	public List<Type> getOldTypes() {
		return oldTypes;
	}

	public void setOldTypes(List<Type> oldTypes) {
		this.oldTypes = oldTypes;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Nature getNature() {
		return nature;
	}

	public void setNature(Nature nature) {
		this.nature = nature;
	}
	
	public int getHP() {
		return this.getStats().get(0).getStatVal();
	}
	
	public int getAtk() {
		if (this.getNature() != null && (!(this.getNature().getDecreasedStat().getLabel().getIdentifier().equals(this.getNature().getIncreasedStat().getLabel().getIdentifier()))) && this.getNature().getIncreasedStat().getLabel().getIdentifier().equals("attack")) {
			return (int) Math.floor(this.getStats().get(1).getStatVal()*1.1);
		}
		else if (this.getNature() != null && (!(this.getNature().getDecreasedStat().getLabel().getIdentifier().equals(this.getNature().getIncreasedStat().getLabel().getIdentifier()))) && this.getNature().getDecreasedStat().getLabel().getIdentifier().equals("attack")) {
			return (int) Math.floor(this.getStats().get(1).getStatVal()*0.9);
		}
		else {
			return this.getStats().get(1).getStatVal();
		}
	}
	
	public int getDef() {
		if (this.getNature() != null && (!(this.getNature().getDecreasedStat().getLabel().getIdentifier().equals(this.getNature().getIncreasedStat().getLabel().getIdentifier()))) && this.getNature().getIncreasedStat().getLabel().getIdentifier().equals("defense")) {
			return (int) Math.floor(this.getStats().get(2).getStatVal()*1.1);
		}
		else if (this.getNature() != null && (!(this.getNature().getDecreasedStat().getLabel().getIdentifier().equals(this.getNature().getIncreasedStat().getLabel().getIdentifier()))) && this.getNature().getDecreasedStat().getLabel().getIdentifier().equals("defense")) {
			return (int) Math.floor(this.getStats().get(2).getStatVal()*0.9);
		}
		else {
			return this.getStats().get(2).getStatVal();
		}	
	}
	
	
	public int getSpA() {
		if (this.getNature() != null && (!(this.getNature().getDecreasedStat().getLabel().getIdentifier().equals(this.getNature().getIncreasedStat().getLabel().getIdentifier()))) && this.getNature().getIncreasedStat().getLabel().getIdentifier().equals("special-attack")) {
			return (int) Math.floor(this.getStats().get(3).getStatVal()*1.1);
		}
		else if (this.getNature() != null && (!(this.getNature().getDecreasedStat().getLabel().getIdentifier().equals(this.getNature().getIncreasedStat().getLabel().getIdentifier()))) && this.getNature().getDecreasedStat().getLabel().getIdentifier().equals("special-attack")) {
			return (int) Math.floor(this.getStats().get(3).getStatVal()*0.9);
		}
		else {
			return this.getStats().get(3).getStatVal();
		}
	}
	
	
	public int getSpD() {
		if (this.getNature() != null && (!(this.getNature().getDecreasedStat().getLabel().getIdentifier().equals(this.getNature().getIncreasedStat().getLabel().getIdentifier()))) && this.getNature().getIncreasedStat().getLabel().getIdentifier().equals("special-defense")) {
			return (int) Math.floor(this.getStats().get(4).getStatVal()*1.1);
		}
		else if (this.getNature() != null && (!(this.getNature().getDecreasedStat().getLabel().getIdentifier().equals(this.getNature().getIncreasedStat().getLabel().getIdentifier()))) && this.getNature().getDecreasedStat().getLabel().getIdentifier().equals("special-defense")) {
			return (int) Math.floor(this.getStats().get(4).getStatVal()*0.9);
		}
		else {
			return this.getStats().get(4).getStatVal();
		}
	}
	
	
	public int getSpe() {
		if (this.getNature() != null && (!(this.getNature().getDecreasedStat().getLabel().getIdentifier().equals(this.getNature().getIncreasedStat().getLabel().getIdentifier()))) && this.getNature().getIncreasedStat().getLabel().getIdentifier().equals("speed")) {
			return (int) Math.floor(this.getStats().get(5).getStatVal()*1.1);
		}
		else if (this.getNature() != null && (!(this.getNature().getDecreasedStat().getLabel().getIdentifier().equals(this.getNature().getIncreasedStat().getLabel().getIdentifier()))) && this.getNature().getDecreasedStat().getLabel().getIdentifier().equals("speed")) {
			return (int) Math.floor(this.getStats().get(5).getStatVal()*0.9);
		}
		else {
			return this.getStats().get(5).getStatVal();
		}
	}
	
	
	public boolean isType(String name) {
		if (this.oldTypes != null && this.oldTypes.size() > 0) {
			for (Type t: this.oldTypes) {
				if (t.isTypeName(name)) {
					return true;
				}
			}
		}
		else {
			for (Type t: this.types) {
				if (t.isTypeName(name)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getEffectivenessFrom(Type t) {
		double init = 1.0;
		if (this.oldTypes != null && this.oldTypes.size() > 0) {
			for (Type x: this.oldTypes) {
				if (x.getEffectivenessFrom(t) == 200) {
					init *= 2;
				}
				else if (x.getEffectivenessFrom(t) == 50) {
					init *= 0.5;
				}
				else if (x.getEffectivenessFrom(t) == 0) {
					init = 0;
				}
			}
		}
		else {
			for (Type x: this.types) {
				if (x.getEffectivenessFrom(t) == 200) {
					init *= 2;
				}
				else if (x.getEffectivenessFrom(t) == 50) {
					init *= 0.5;
				}
				else if (x.getEffectivenessFrom(t) == 0) {
					init = 0;
				}
			}
		}
		return init;
	}
}
