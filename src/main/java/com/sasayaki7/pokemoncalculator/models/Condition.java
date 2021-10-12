package com.sasayaki7.pokemoncalculator.models;

public class Condition {
	
	private String cond;
	private Pokemon pokemon;
	private String terrain;
	private String weather;
	private Move move;
	private int generation;
	private int hits;
	private boolean screens;
	private double oppBoost;
	private double yourBoost;
	private String rawBoost;
	private String rawOppBoost;
	private String summary;
	private boolean isDoubles;
	private boolean helpingHand=false;
	private boolean friendGuard=false;
	private boolean rivalry=false;
	private boolean fairyAura=false;
	private boolean darkAura=false;
	private boolean auraBreak=false;
	private boolean neutralizingGas=false;
	private boolean isCritical=false;
	private double health;
	private boolean battery=false;
	private boolean flowerGift=false;
	private boolean powerSpot=false;
	private boolean yourTw=false;
	private boolean foeTw=false;
	private boolean charge=false;
	private boolean gravity=false;
	private boolean flashFire=false;
	private boolean z=false;
	private boolean selfDmax=false;
	private boolean oppDmax=false;
	private boolean itemConsumed=false;
	private boolean smackDown=false;
	private boolean stealthRock=false;
	private boolean maxHp = false;

	
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

	
	public boolean isSmackDown() {
		return smackDown;
	}


	public void setSmackDown(boolean smackDown) {
		this.smackDown = smackDown;
	}


	public int getHits() {
		return hits;
	}


	public void setHits(int hits) {
		this.hits = hits;
	}


	public boolean isItemConsumed() {
		return itemConsumed;
	}


	public void setItemConsumed(boolean itemConsumed) {
		this.itemConsumed = itemConsumed;
	}


	public void setWeather(String weather) {
		this.weather = weather;
	}


	public boolean isScreens() {
		return screens;
	}


	public boolean isStealthRock() {
		return stealthRock;
	}


	public void setStealthRock(boolean stealthRock) {
		this.stealthRock = stealthRock;
	}


	public void setScreens(boolean screens) {
		this.screens = screens;
	}

	
	public boolean isMaxHp() {
		return maxHp;
	}


	public void setMaxHp(boolean maxHp) {
		this.maxHp = maxHp;
	}


	public double getOppBoost() {
		return oppBoost;
	}
	

	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public void setOppBoost(double oppBoost) {
		this.oppBoost = oppBoost;
	}

	public void setOppBoost(String yourBoost) {
		this.rawOppBoost = yourBoost;

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

	public void changeYourBoost(int drop) {
		int newBoost = Integer.parseInt(this.rawBoost)+drop;
		String newStrBoost = String.valueOf(newBoost);
		if (newBoost > 0) {
			newStrBoost = "+"+newStrBoost;
		}
		this.setYourBoost(newStrBoost);
	}

	public void changeOppBoost(int drop) {
		int newBoost = Integer.parseInt(this.rawOppBoost)+drop;
		String newStrBoost = String.valueOf(newBoost);
		if (newBoost > 0) {
			newStrBoost = "+"+newStrBoost;
		}
		this.setOppBoost(newStrBoost);
	}
	
	public double getYourBoost() {
		return yourBoost;
	}


	public void setYourBoost(double yourBoost) {
		this.yourBoost = yourBoost;
	}
	
	public void setYourBoost(String yourBoost) {
		this.rawBoost = yourBoost;
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

	

	public String getRawBoost() {
		return rawBoost;
	}


	public void setRawBoost(String rawBoost) {
		this.rawBoost = rawBoost;
	}


	public String getRawOppBoost() {
		return rawOppBoost;
	}


	public void setRawOppBoost(String rawOppBoost) {
		this.rawOppBoost = rawOppBoost;
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


	public boolean isBattery() {
		return battery;
	}


	public void setBattery(boolean battery) {
		this.battery = battery;
	}


	public boolean isFlowerGift() {
		return flowerGift;
	}


	public void setFlowerGift(boolean flowerGift) {
		this.flowerGift = flowerGift;
	}


	public boolean isPowerSpot() {
		return powerSpot;
	}


	public void setPowerSpot(boolean powerSpot) {
		this.powerSpot = powerSpot;
	}


	public boolean isYourTw() {
		return yourTw;
	}


	public void setYourTw(boolean yourTw) {
		this.yourTw = yourTw;
	}


	public boolean isFoeTw() {
		return foeTw;
	}


	public void setFoeTw(boolean foeTw) {
		this.foeTw = foeTw;
	}


	public int getGeneration() {
		return generation;
	}


	public void setGeneration(int generation) {
		this.generation = generation;
	}


	public boolean isCharge() {
		return charge;
	}


	public void setCharge(boolean charge) {
		this.charge = charge;
	}


	public boolean isGravity() {
		return gravity;
	}


	public void setGravity(boolean gravity) {
		this.gravity = gravity;
	}


	public boolean isFlashFire() {
		return flashFire;
	}


	public void setFlashFire(boolean flashfire) {
		this.flashFire = flashfire;
	}


	public boolean isZ() {
		return z;
	}


	public void setZ(boolean z) {
		this.z = z;
	}


	public boolean isSelfDmax() {
		return selfDmax;
	}


	public void setSelfDmax(boolean selfDmax) {
		this.selfDmax = selfDmax;
	}


	public boolean isOppDmax() {
		return oppDmax;
	}


	public void setOppDmax(boolean oppDmax) {
		this.oppDmax = oppDmax;
	}
	
	
	
	
	
}
