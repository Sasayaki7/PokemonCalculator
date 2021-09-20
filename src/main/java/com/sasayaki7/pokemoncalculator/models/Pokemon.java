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
	
	
	public String getIdentifierCleaned() {
		return identifier.substring(0, 1).toUpperCase()+identifier.substring(1);
	}
	
	public String getIdentifierNoSpace() {
		if (identifier.indexOf("tapu") != -1) {
			return identifier.replace(" ", "");
		}
		else if (identifier.equals("type-null")||identifier.equals("mr-rime")||identifier.equals("mime-jr")) {
			return identifier.replace("-", "");
		}
		else if (identifier.indexOf("mr-mime") != -1) {
			return identifier.replace("mr-mime", "mrmime").replace('-', ' ');
		}
		else if (identifier.indexOf("mega-x") != -1) {
			return identifier.replace("mega-x", "megax").replace('-', ' ');
		}
		else if (identifier.indexOf("mega-y") != -1) {
			return identifier.replace("mega-y", "megay").replace('-', ' ');
		}
		else if (identifier.indexOf("rapid-strike") != -1) {
			return identifier.replace("rapid-strike", "rapidstrike").replace('-', ' ');
		}
		else if (identifier.indexOf("single-strike") != -1) {
			return identifier.replace("single-strike", "singlestrike").replace('-', ' ');
		}
		else {
			return identifier.replace('-', ' ');
		}
	}
	
	public String getItemCleaned() {
		String repS = item.replace('-', ' ');
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
		return this.getStats().get(1).getStatVal();
	}
	
	public int getDef() {
		return this.getStats().get(2).getStatVal();
	}
	
	public int getSpA() {
		return this.getStats().get(3).getStatVal();
	}
	
	public int getSpD() {
		return this.getStats().get(4).getStatVal();
	}
	
	public int getSpe() {
		return this.getStats().get(5).getStatVal();
	}
	
	public boolean isType(String name) {
		for (Type t: this.types) {
			if (t.isTypeName(name)) {
				return true;
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
		return init;
	}
}
