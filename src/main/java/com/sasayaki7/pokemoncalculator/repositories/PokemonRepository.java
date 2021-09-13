package com.sasayaki7.pokemoncalculator.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sasayaki7.pokemoncalculator.models.Nature;
import com.sasayaki7.pokemoncalculator.models.Pokemon;

public interface PokemonRepository extends CrudRepository<Pokemon, Long> {
	public Optional<Pokemon> findByIdentifier(String name);
	List<Pokemon> findByIdentifierStartingWith(String s);

}
