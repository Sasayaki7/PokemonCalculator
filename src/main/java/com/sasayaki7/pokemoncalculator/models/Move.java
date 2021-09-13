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

