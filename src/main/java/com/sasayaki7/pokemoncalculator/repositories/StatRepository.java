package com.sasayaki7.pokemoncalculator.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sasayaki7.pokemoncalculator.models.Stat;

public interface StatRepository extends CrudRepository<Stat, Long>{
	
	@Query("SELECT s FROM Stat s JOIN s.label l WHERE l.identifier=?1")
	public List<Stat> getByName(String s);
	
}
