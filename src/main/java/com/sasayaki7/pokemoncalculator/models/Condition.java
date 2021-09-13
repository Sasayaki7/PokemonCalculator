package com.sasayaki7.pokemoncalculator.models;

public class Condition {
	
	private String cond;
	private Pokemon pokemon;
	private String terrain;
	private String weather;
	private Move move;
	private boolean screens;
	private double oppBoost;
	private double yourBoost;
	private boolean isDoubles;
	private boolean helpingHand;
	private boolean friendGuard;
	private boolean rivalry;
	private boolean fairyAura;
	private boolean darkAura;
	private boolean auraBreak;
	private boolean neutralizingGas;
	private boolean isCritical;
	private double health;
	
	public Condition() {
	}


	public String getCond() {
		return cond;
	}


	public void setCond(String cond) {
		this.cond = cond;
	}


	public Pokemon getPokemon() {
		return pokemon;
	}


	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}


	public String getTerrain() {
		return terrain;
	}


	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}


	public String getWeather() {
		return weather;
	}


	public void setWeather(String weather) {
		this.weather = weather;
	}


	public boolean isScreens() {
		return screens;
	}


	public void setScreens(boolean screens) {
		this.screens = screens;
	}


	public double getOppBoost() {
		return oppBoost;
	}


	public void setOppBoost(double oppBoost) {
		this.oppBoost = oppBoost;
	}

	public void setOppBoost(String yourBoost) {
		if (yourBoost.equals("0")) {
			this.oppBoost =  1.0;
		}
		else if (yourBoost.equals("+1")) {
			this.oppBoost =  1.5;
		}
		else if (yourBoost.equals("+2")) {
			this.oppBoost =  2.0;
		}
		else if (yourBoost.equals("+3")) {
			this.oppBoost =  2.5;
		}
		else if (yourBoost.equals("+4")) {
			this.oppBoost =  3.0;
		}
		else if (yourBoost.equals("+5")) {
			this.oppBoost =  3.5;
		}
		else if (yourBoost.equals("+6")) {
			this.oppBoost =  4.0;
		}
		else if (yourBoost.equals("-1")) {
			this.oppBoost =  2.0/3.0;
		}
		else if (yourBoost.equals("-2")) {
			this.oppBoost =  2.0/4.0;
		}
		else if (yourBoost.equals("-3")) {
			this.oppBoost = 2.0/5.0 ;
		}
		else if (yourBoost.equals("-4")) {
			this.oppBoost =  2.0/6.0;
		}
		else if (yourBoost.equals("-5")) {
			this.oppBoost =  2.0/7.0;
		}
		else{
			this.oppBoost =  0.25;
		}
	}


	public double getYourBoost() {
		return yourBoost;
	}


	public void setYourBoost(double yourBoost) {
		this.yourBoost = yourBoost;
	}
	
	public void setYourBoost(String yourBoost) {
		if (yourBoost.equals("0")) {
			this.yourBoost =  1.0;
		}
		else if (yourBoost.equals("+1")) {
			this.yourBoost =  1.5;
		}
		else if (yourBoost.equals("+2")) {
			this.yourBoost =  2.0;
		}
		else if (yourBoost.equals("+3")) {
			this.yourBoost =  2.5;
		}
		else if (yourBoost.equals("+4")) {
			this.yourBoost =  3.0;
		}
		else if (yourBoost.equals("+5")) {
			this.yourBoost =  3.5;
		}
		else if (yourBoost.equals("+6")) {
			this.yourBoost =  4.0;
		}
		else if (yourBoost.equals("-1")) {
			this.yourBoost =  2.0/3.0;
		}
		else if (yourBoost.equals("-2")) {
			this.yourBoost =  2.0/4.0;
		}
		else if (yourBoost.equals("-3")) {
			this.yourBoost = 2.0/5.0 ;
		}
		else if (yourBoost.equals("-4")) {
			this.yourBoost =  2.0/6.0;
		}
		else if (yourBoost.equals("-5")) {
			this.yourBoost =  2.0/7.0;
		}
		else{
			this.yourBoost =  0.25;
		}
	}


	public Move getMove() {
		return move;
	}


	public void setMove(Move move) {
		this.move = move;
	}


	public boolean isDoubles() {
		return isDoubles;
	}


	public void setDoubles(boolean isDoubles) {
		this.isDoubles = isDoubles;
	}


	public boolean isHelpingHand() {
		return helpingHand;
	}


	public void setHelpingHand(boolean helpingHand) {
		this.helpingHand = helpingHand;
	}


	public boolean isFriendGuard() {
		return friendGuard;
	}


	public void setFriendGuard(boolean friendGuard) {
		this.friendGuard = friendGuard;
	}


	public boolean isRivalry() {
		return rivalry;
	}


	public void setRivalry(boolean rivalry) {
		this.rivalry = rivalry;
	}


	public boolean isFairyAura() {
		return fairyAura;
	}


	public void setFairyAura(boolean fairyAura) {
		this.fairyAura = fairyAura;
	}


	public boolean isDarkAura() {
		return darkAura;
	}


	public void setDarkAura(boolean darkAura) {
		this.darkAura = darkAura;
	}


	public boolean isAuraBreak() {
		return auraBreak;
	}


	public void setAuraBreak(boolean auraBreak) {
		this.auraBreak = auraBreak;
	}


	public boolean isNeutralizingGas() {
		return neutralizingGas;
	}


	public void setNeutralizingGas(boolean neutralizingGas) {
		this.neutralizingGas = neutralizingGas;
	}


	public boolean isCritical() {
		return isCritical;
	}


	public void setCritical(boolean isCritical) {
		this.isCritical = isCritical;
	}


	public double getHealth() {
		return health;
	}


	public void setHealth(double health) {
		this.health = health;
	}
	
	
	
}
