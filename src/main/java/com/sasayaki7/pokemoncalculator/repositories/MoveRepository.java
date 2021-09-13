package com.sasayaki7.pokemoncalculator.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sasayaki7.pokemoncalculator.models.Move;
import com.sasayaki7.pokemoncalculator.models.Nature;

public interface MoveRepository extends CrudRepository<Move, Long>{
	public Optional<Move> findByIdentifier(String name);
	List<Move> findByIdentifierStartingWith(String s);

}
