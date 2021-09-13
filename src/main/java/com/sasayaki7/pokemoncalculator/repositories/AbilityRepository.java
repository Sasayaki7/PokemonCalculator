package com.sasayaki7.pokemoncalculator.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sasayaki7.pokemoncalculator.models.Ability;

public interface AbilityRepository extends CrudRepository<Ability, Long>{
	
	public Optional<Ability> findByIdentifier(String name);
}
