package com.sasayaki7.pokemoncalculator.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="type_efficacy")
public class TypeEfficacy {
	
	@Id
	private Long id;
	
	
	private int damageFactor;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="damage_type_id")
	private Type damageType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="target_type_id")
	private Type targetType;
	
	
	public TypeEfficacy() {
	}



	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}


	public int getDamageFactor() {
		return damageFactor;
	}


	public void setDamageFactor(int damageFactor) {
		this.damageFactor = damageFactor;
	}


	public Type getDamageType() {
		return damageType;
	}


	public void setDamageType(Type damageType) {
		this.damageType = damageType;
	}


	public Type getTargetType() {
		return targetType;
	}


	public void setTargetType(Type targetType) {
		this.targetType = targetType;
	}
	
	
	
}
