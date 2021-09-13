package com.sasayaki7.pokemoncalculator.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sasayaki7.pokemoncalculator.models.Item;
import com.sasayaki7.pokemoncalculator.models.Nature;

public interface ItemRepository extends CrudRepository<Item, Long>{
	public Optional<Item> findByIdentifier(String name);

	List<Item> findByIdentifierStartingWith(String s);

}
