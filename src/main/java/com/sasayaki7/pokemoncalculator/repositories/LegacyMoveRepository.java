package com.sasayaki7.pokemoncalculator.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sasayaki7.pokemoncalculator.models.LegacyMove;

public interface LegacyMoveRepository extends CrudRepository<LegacyMove, Long>{
	
	@Query("SELECT l FROM LegacyMove l WHERE l.identifier=?1 AND l.generation_id >=?2 ORDER BY generation_id ASC")
	public List<LegacyMove> getMoveWithGen(String move, int generation);
}
