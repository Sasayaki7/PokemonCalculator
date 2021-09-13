package com.sasayaki7.pokemoncalculator.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name="move_targets")
@Entity
public class MoveTarget {
	
	@Id
	private int id;
	private String identifier;
	
	@OneToMany(mappedBy="target", fetch=FetchType.LAZY)
	private List<Move> moves;
	
	public MoveTarget() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public List<Move> getMoves() {
		return moves;
	}

	public void setMoves(List<Move> moves) {
		this.moves = moves;
	}
	
	
	
	
}
