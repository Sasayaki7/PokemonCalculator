package com.sasayaki7.pokemoncalculator.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sasayaki7.pokemoncalculator.models.Nature;
import com.sasayaki7.pokemoncalculator.models.Stat;

public interface NatureRepository extends CrudRepository<Nature, Long>{
	List<Nature> findAll();
	Optional<Nature> findByIdentifier(String name);

	@Query("SELECT n FROM Nature n JOIN n.decreasedStat ds JOIN n.increasedStat ixs WHERE ixs = ?1 AND ds = ?2")
	Nature findNatureWithStats(Stat inc, Stat dec);

	List<Nature> findByIdentifierStartingWith(String s);

}
