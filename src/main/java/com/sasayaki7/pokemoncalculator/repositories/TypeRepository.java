package com.sasayaki7.pokemoncalculator.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sasayaki7.pokemoncalculator.models.Type;

public interface TypeRepository extends CrudRepository<Type, Long>{
	
	@Query(value="SELECT * FROM types JOIN type_efficacy ON types.id=type_efficacy.target_type_id WHERE type_efficacy.damage_factor=200 AND types.id=?1", nativeQuery=true)
	public List<Type> findAllWeaknesses(Long id);
	
	@Query(value="SELECT * FROM types JOIN type_efficacy ON types.id=type_efficacy.target_type_id WHERE type_efficacy.damage_factor=50 AND types.id=?1", nativeQuery=true)
	public List<Type> findAllResistances(Type t);
	
	@Query(value="SELECT * FROM types JOIN type_efficacy ON types.id=type_efficacy.target_type_id WHERE type_efficacy.damage_factor=0 AND types.id=?1", nativeQuery=true)
	public List<Type> findAllImmunities(Type t);

	public Optional<Type> findByIdentifier(String type);
}
