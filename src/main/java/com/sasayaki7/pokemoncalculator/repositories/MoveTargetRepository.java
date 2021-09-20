package com.sasayaki7.pokemoncalculator.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sasayaki7.pokemoncalculator.models.MoveTarget;

public interface MoveTargetRepository extends CrudRepository<MoveTarget, Long> {
	public Optional<MoveTarget> findByIdentifier(String s);
}
