package com.sasayaki7.pokemoncalculator.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name="moves")
@Entity
public class Move {

	@Id
	private Long id;
	
	private String identifier;
	private Integer power;
	private Integer maxMovePower;
	private Integer effectId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="damage_class_id")	
	private DamageClass damageClass;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="type_id")
	private Type type;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="target_id")
	private MoveTarget target;
	
	public Move() {
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}
	
	public Integer getEffectId() {
		return effectId;
	}

	public void setEffectId(Integer effectId) {
		this.effectId = effectId;
	}

	public DamageClass getDamageClass() {
		return damageClass;
	}

	public void setDamageClass(DamageClass damageClass) {
		this.damageClass = damageClass;
	}

	public MoveTarget getTarget() {
		return target;
	}

	public void setTarget(MoveTarget target) {
		this.target = target;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Integer getMaxMovePower() {
		return maxMovePower;
	}

	public void setMaxMovePower(Integer maxMovePower) {
		this.maxMovePower = maxMovePower;
	}

	public String getIdentifierCleaned() {
		String repS = identifier.replace('-', ' ');
		if (identifier.length() > 0) {
			repS = repS.substring(0, 1).toUpperCase()+repS.substring(1);
			for(int i = 1; i < repS.length(); i++) {
				if (repS.charAt(i) == ' ') {
					repS = repS.substring(0, i+1)+repS.substring(i+1, i+2).toUpperCase()+repS.substring(i+2);
				}
			}
		}
		return repS;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public boolean isType(String name) {
		return this.type.isTypeName(name);
	}
	
}

