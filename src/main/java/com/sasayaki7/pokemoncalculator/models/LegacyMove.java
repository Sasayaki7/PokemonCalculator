package com.sasayaki7.pokemoncalculator.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name="move_past_generation")
@Entity
public class LegacyMove {

	@Id
	private Long id;
	
	private String identifier;
	private Integer power;
	private Integer generation_id;
	
	public Integer getGeneration_id() {
		return generation_id;
	}

	public void setGeneration_id(Integer generation_id) {
		this.generation_id = generation_id;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="damage_class_id")	
	private DamageClass damageClass;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="type_id")
	private Type type;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="target_id")
	private MoveTarget target;
	
	private LegacyMove() {
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public MoveTarget getTarget() {
		return target;
	}

	public void setTarget(MoveTarget target) {
		this.target = target;
	}
	
	

}