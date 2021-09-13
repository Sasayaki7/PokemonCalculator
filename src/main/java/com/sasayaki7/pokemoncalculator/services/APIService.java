package com.sasayaki7.pokemoncalculator.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sasayaki7.pokemoncalculator.models.Ability;
import com.sasayaki7.pokemoncalculator.models.Condition;
import com.sasayaki7.pokemoncalculator.models.Item;
import com.sasayaki7.pokemoncalculator.models.Move;
import com.sasayaki7.pokemoncalculator.models.Nature;
import com.sasayaki7.pokemoncalculator.models.Pokemon;
import com.sasayaki7.pokemoncalculator.models.Stat;
import com.sasayaki7.pokemoncalculator.models.Type;
import com.sasayaki7.pokemoncalculator.repositories.AbilityRepository;
import com.sasayaki7.pokemoncalculator.repositories.ItemRepository;
import com.sasayaki7.pokemoncalculator.repositories.MoveRepository;
import com.sasayaki7.pokemoncalculator.repositories.NatureRepository;
import com.sasayaki7.pokemoncalculator.repositories.PokemonRepository;
import com.sasayaki7.pokemoncalculator.repositories.StatRepository;
import com.sasayaki7.pokemoncalculator.repositories.TypeRepository;


@Service
public class APIService {
	
//	@Autowired
//	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private PokemonRepository pokemonRepo;
	
	@Autowired
	private MoveRepository moveRepo;
	
	@Autowired
	private TypeRepository typeRepo;
	
	@Autowired
	private AbilityRepository abilityRepo;
	
	@Autowired
	private NatureRepository natureRepo;
	
	@Autowired
	private StatRepository statRepo;
	
	@Autowired
	private ItemRepository itemRepo;
	
	//If I ever want to use an API call in Java again its a nice reference
//	public Pokemon[] getAllPokemon() {
//		Pokemon[] pokemons= webClientBuilder.build()
//			.get()
//			.uri("https://pokeapi.co/api/v2/pokemon/?limit=899")
//			.retrieve()
//			.bodyToMono(Pokemon[].class)
//			.block();
//		return pokemons;
//	}
	
	
	public Pokemon getPokemon(String pokemonName) {
		Optional<Pokemon> tempPokemon = pokemonRepo.findByIdentifier(pokemonName);
		if (tempPokemon.isPresent()) {
			return tempPokemon.get();
		}
		else {
			return null;
		}
	}
	
	public Move getMove(String move) {
		Optional<Move> tempMove = moveRepo.findByIdentifier(move);
		if (tempMove.isPresent()) {
			return tempMove.get();
		}
		else {
			return null;
		}
	}
	
	public Ability getAbility(String ability) {
		Optional<Ability> tempAbility = abilityRepo.findByIdentifier(ability);
		if (tempAbility.isPresent()) {
			return tempAbility.get();
		}
		else {
			return null;
		}
	}
	
	public Type getType(String type) {
		Optional<Type> tempType= typeRepo.findByIdentifier(type);
		if (tempType.isPresent()) {
			return tempType.get();
		}
		else {
			return null;
		}
		
	}
	
	public Stat getStatByName(String name) {
		System.out.println(name);
		List<Stat> tempStat = statRepo.getByName(name);
		if (tempStat.size() > 0) {
			return tempStat.get(0);
		}
		return null;
	}
	
	public Nature getNature(String name) {
		Optional<Nature> tempNature = natureRepo.findByIdentifier(name);
		if (tempNature.isPresent()) {
			return tempNature.get();
		}
		else {
			return null;
		}
	}
	
	public List<Nature> getNatures(){
		return natureRepo.findAll();
	}
		
	public int pokeRound(double damage) {
		return (int) Math.ceil(damage-0.5);
	}

	public int calculateBaseDamage(Condition condition, Pokemon atkPokemon, Pokemon defPokemon) {
		Move m = condition.getMove();
		int basePower = m.getPower();
		int attack = 0;
		int defense = 0;
		int multiplier = 4096;
		
		//BASE POWER LOGIC (IT'LL BE MESSY)
		if (condition.isAuraBreak()) {
			if (m.isType("fairy") && condition.isFairyAura() || m.isType("dark") && condition.isDarkAura()) {
				multiplier = Math.round(multiplier*3072/4096);
			}
		}
		
		//TODO: RIVALRY
		
		if(m.isType("normal") && (atkPokemon.getAbility().getIdentifier().equals("galvanize") || atkPokemon.getAbility().getIdentifier().equals("aerilate") || 
				atkPokemon.getAbility().getIdentifier().equals("refrigerate") || atkPokemon.getAbility().getIdentifier().equals("pixilate"))) {
			multiplier = Math.round(multiplier*4915/4096);
		}
		
		if (m.getDamageClass().getIdentifier().equals("physical")) {
			attack = atkPokemon.getAtk();
			defense = defPokemon.getDef();
			if (m.getIdentifier().equals("body-press")) {
				attack = atkPokemon.getDef();
			}
			else if (m.getIdentifier().equals("foul-play")) {
				attack = defPokemon.getAtk();
			}
		}
		if (m.getDamageClass().getIdentifier().equals("special")) {
			attack = atkPokemon.getSpA();
			defense = defPokemon.getSpD();
			if (m.getIdentifier().equals("psyshock") || m.getIdentifier().equals("psystrike") || m.getIdentifier().equals("secret-sword")) {
				defense = defPokemon.getDef();
			}
		}


		return (int)Math.floor(Math.floor(Math.floor((2*atkPokemon.getLevel()/5)+2)*basePower*attack*condition.getYourBoost()/(defense*condition.getOppBoost())))/50+2;
	}
	
	public int calculateDamage(Condition condition, Pokemon pokemon) {
		Move m = condition.getMove();
		Pokemon atkPokemon;
		Pokemon defPokemon;
		if (condition.getCond().equals("Survive")) {
			defPokemon = pokemon;
			atkPokemon = condition.getPokemon();
		}
		else {
			defPokemon = condition.getPokemon();
			atkPokemon = pokemon;
		}

		int damage = calculateBaseDamage(condition, atkPokemon, defPokemon);
		
		//General modifiers
		
		if (condition.isDoubles() && !m.getDamageClass().getIdentifier().equals("selected-pokemon")) {
			damage = pokeRound(damage*3072.0/4096);
		}
		
		//TODO: parental bond
		
		if (condition.getWeather().equals("Sun")) {
			if (m.isType("fire")) {
				damage = pokeRound(damage*6144.0/4096);
			}
			else if (m.isType("water")) {
				damage = pokeRound(damage*2048.0/4096);
			}
		}
		else if (condition.getWeather().equals("Rain")) {
			if (m.isType("water")) {
				damage = pokeRound(damage*6144.0/4096);
			}
			else if (m.isType("fire")) {
				damage = pokeRound(damage*2048.0/4096);
			}
		}
		
		//TODO: crits
		
		if (condition.getCond().equals("Knock Out")) {
			damage = (int) Math.floor(damage*0.85);
		}
		
		if (atkPokemon.isType(m.getType().getIdentifier())) {
			if (atkPokemon.getAbility().getIdentifier().equals("adaptability")) {
				damage *=2;
			}
			else {
				damage = pokeRound(damage*6144.0/4096);
			}
		}
		
		double modifier = defPokemon.getEffectivenessFrom(m.getType());
		if (m.getIdentifier().equals("freeze-dry") && defPokemon.isType("water")) {
			modifier = modifier * 4;
		}
		else if ((m.getIdentifier().equals("thousand-arrows") || defPokemon.getItem().equals("iron-ball")) && defPokemon.isType("flying")) {
			modifier = 1.0;
		}
		
		//TODO: Other interactions with typing.
		
		damage = (int) (damage*modifier);
		
		double finalModifier = 4096/4096;
		
		if (condition.isScreens()) {
			if(condition.isDoubles()) {
				finalModifier = finalModifier*2732/4096;
			}
			else {
				finalModifier = finalModifier*2048/4096;
			}
		}
		if(atkPokemon.getAbility().getIdentifier().equals("neuroforce") && modifier > 1.1) {
			finalModifier = finalModifier*5120/4096;
		}
		if(atkPokemon.getAbility().getIdentifier().equals("sniper") && condition.isCritical()) {
			finalModifier = finalModifier*6144/4096;

		}
		if(atkPokemon.getAbility().getIdentifier().equals("tinted-lens") && modifier < 0.9) {
			finalModifier = finalModifier*8192/4096;
		}
		
		if ((defPokemon.getAbility().getIdentifier().equals("multiscale") || defPokemon.getAbility().getIdentifier().equals("shadow-shield")) && (int) (condition.getHealth())==100) {
			finalModifier = finalModifier * 2048/4096;
		}
		
		//TODO: Burn
		//TODO: Fluffy
		
		if (condition.isFriendGuard()) {
			finalModifier = finalModifier*3072/4096;
		}
		
		if((defPokemon.getAbility().getIdentifier().equals("solid-rock") || defPokemon.getAbility().getIdentifier().equals("filter") || defPokemon.getAbility().getIdentifier().equals("prism-armor")) && modifier > 1.1) {
			finalModifier = finalModifier * 3072/4096;
		}
		
		
		//TODO: Metronome item
		//TODO: Fluffy Fire
		
		if(atkPokemon.getItem().equals("expert-belt") && modifier > 1.1) {
			finalModifier = finalModifier * 4915 / 4096;
		}
		
		if (atkPokemon.getItem().equals("life-orb")) {
			finalModifier = finalModifier * 5324/4096;
		}
		
		//TODO: resist berries
		//TODO: double-damage situations
		
		
		return damage;
	}
		
//	public int finalDamage(Condition condition, int baseDamage) {
//		
//	}
	public List<Nature> getNatureStartingWith(String s){
		return natureRepo.findByIdentifierStartingWith(s.toLowerCase());
	}
	
	public List<Move> getMoveStartingWith(String s){
		return moveRepo.findByIdentifierStartingWith(s.toLowerCase());
	}
	
	public List<Pokemon> getPokemonStartingWith(String s){
		return pokemonRepo.findByIdentifierStartingWith(s.toLowerCase());
	}
	
	public List<Item> getItemStartingWith(String s){
		return itemRepo.findByIdentifierStartingWith(s.toLowerCase());
	}
	
	public void determineOrderOfStat(ArrayList<HashMap<String, Object>> arrToInsertIn, String descriptor, ArrayList<Object> conds, List<Condition> condList, int conditionCounter){
		int statToCompare;
		if (conds.get(0) == null) {
			statToCompare = (Integer) ((HashMap<String,Object>) conds.get(1)).get(descriptor);
		}
		else if (conds.get(1) == null) {
			statToCompare = (Integer) ((HashMap<String,Object>) conds.get(0)).get(descriptor);
		}
		else {
			int tempStat0 = (Integer) ((HashMap<String,Object>) conds.get(0)).get(descriptor);
			int tempStat1 = (Integer) ((HashMap<String,Object>) conds.get(1)).get(descriptor);
			if (tempStat1 < tempStat0) {
				statToCompare = tempStat1;
			}
			else {
				statToCompare = tempStat0;
			}
		}
		HashMap<String, Object> stor = new HashMap<String, Object>();
		stor.put("condition", condList.get(conditionCounter));
		stor.put(descriptor, statToCompare);
		stor.put("with-orig-nature", conds.get(0) != null ? (HashMap<String, Object>) conds.get(0): null);
		stor.put("allconds", conds);
		placeIntoArray(arrToInsertIn, stor, statToCompare, descriptor);
	}
		
	public void placeIntoArray(ArrayList<HashMap<String, Object>> arrToInsertIn, HashMap<String, Object> itemToPlace, int valueToCompare, String descriptor){
		int pos = 0;
		boolean found = false;
		while (pos < arrToInsertIn.size()) {
			if ((Integer) arrToInsertIn.get(pos).get(descriptor) < valueToCompare) {
				found = true;
				arrToInsertIn.add(pos, itemToPlace);
				break;
			}
			pos++;
		}
		if (!found) {
			arrToInsertIn.add(itemToPlace);
		}
	}
		
	public boolean isMovePhysical(Move m) {
		if (m.getDamageClass().getIdentifier().equals("physical") || m.getIdentifier().equals("psyshock") || m.getIdentifier().equals("psystrike") || m.getIdentifier().equals("secret-sword")) {
			return true;
		}
		return false;
	}
		
	public int calculateSpeed(Pokemon pokemon, double boost, String weather, String terrain, boolean tailwind) {
		
		//TODO: boosts 
		
		int baseSpeed = pokemon.getSpe();
		if (pokemon.getNature().getIncreasedStat().getLabel().getIdentifier().equals("speed")) {
			baseSpeed = (int) (baseSpeed*1.1);
		}
		else if (pokemon.getNature().getDecreasedStat().getLabel().getIdentifier().equals("speed")) {
			baseSpeed = (int) (baseSpeed*0.9);
		}
		if (pokemon.getItem().equals("iron-ball") || pokemon.getItem().equals("lax-incense")) {
			baseSpeed = (int) (baseSpeed*2048/4096);
		}
		else if (pokemon.getItem().equals("choice-scarf")) {
			baseSpeed = (int) (baseSpeed*6144/4096);
		}
		if (tailwind) {
			baseSpeed = baseSpeed * 2;
		}
		if ((weather.equals("Sun") && pokemon.getAbility().getIdentifier().equals("chlorophyll")) || (weather.equals("Rain") && pokemon.getAbility().getIdentifier().equals("swift-swim"))
				|| (weather.equals("Sand") && pokemon.getAbility().getIdentifier().equals("sand-rush")) || (weather.equals("Hail") && pokemon.getAbility().getIdentifier().equals("slush-rush"))
				|| (terrain.equals("Electric") && pokemon.getAbility().getIdentifier().equals("surge-surfer"))){
			baseSpeed = baseSpeed * 2;
		}
		return baseSpeed;
	}
		
	public int[] calcAllEVs(ArrayList<HashMap<String, Object>> atks, ArrayList<HashMap<String, Object>> defense, ArrayList<HashMap<String, Object>> spas, ArrayList<HashMap<String, Object>> spds, ArrayList<HashMap<String, Object>> speed) {
		int total = 0;
		int[] results = new int[7];
		if (atks.size() > 0) {
			HashMap<String, Object> atkMap = (HashMap<String, Object>)(atks.get(0).get("with-orig-nature"));
			int atk = (Integer) atkMap.get("attack");
			total+=atk;
			results[1] = atk;
		}
		else {
			results[1] = 0;
		}
		if (spas.size() > 0) {
			HashMap<String, Object> spaMap = (HashMap<String, Object>)(spas.get(0).get("with-orig-nature"));
			int spa = (Integer) spaMap.get("special-attack");
			total+=spa;
			results[3] = spa;
		}
		else {
			results[3] = 0;
		}
		
		if (speed.size() > 0) {
			HashMap<String, Object> speedMap = (HashMap<String, Object>)(speed.get(0).get("with-orig-nature"));
			int speeeedd = (Integer) speedMap.get("speed");
			total+=speeeedd;
			results[5] = speeeedd;
		}
		else {
			results[5] = 0;
		}
		
		if (spds.size() > 0 && defense.size() > 0) {
			ArrayList<HashMap<String, Object>> spdStack = (ArrayList<HashMap<String, Object>>) spds.get(0).get("with-orig-nature");
			ArrayList<HashMap<String, Object>> defStack = (ArrayList<HashMap<String, Object>>) defense.get(0).get("with-orig-nature");
			int spdHp=(Integer) spdStack.get(0).get("hp");
			int defHp=(Integer) defStack.get(0).get("hp");
			int currentHp = Math.max(spdHp, defHp);
			int currentDef = (Integer) defStack.get(0).get("defense");
			int currentSpD = (Integer) spdStack.get(0).get("special-defense");
			int totalStat = currentHp+currentDef+currentSpD;
			for (int i = 0; i < spdStack.size(); i++) {
				for (int j = 0; j < defStack.size(); j++) {
					int testDef = (Integer) spdStack.get(i).get("special-defense");
					int testHp = Math.max((Integer) spdStack.get(i).get("hp"), (Integer) defStack.get(j).get("hp"));
					int testSpd = (Integer) defStack.get(j).get("defense");
					if (totalStat > testDef+testHp+testSpd || (totalStat == testDef+testHp+testSpd && currentHp < testHp)) {
						totalStat = testDef+testHp+testSpd;
						currentHp = testHp;
						currentSpD = testSpd;
						currentDef = testDef;
					}
				}
			}
			results[0] = currentHp;
			results[2] = currentDef;
			results[4] = currentSpD;
			total += totalStat;
			
		}
		else if (spds.size() > 0) {
			ArrayList<HashMap<String, Object>> spdStack = (ArrayList<HashMap<String, Object>>) spds.get(0).get("with-orig-nature");
			int currentHp = (Integer) spdStack.get(0).get("hp");
			int currentSpD = (Integer) spdStack.get(0).get("special-defense");
			int totalStat = currentHp+currentSpD;

			for (int i = 0; i < spdStack.size(); i++) {
				int testSpd = (Integer) spdStack.get(i).get("special-defense");
				int testHp = (Integer) spdStack.get(i).get("hp");

				if (totalStat > testHp+testSpd || (totalStat == testHp+testSpd && currentHp < testHp)) {
					currentSpD = testSpd;
					currentHp = testHp;
					totalStat = testHp+testSpd;
				}

			}
			results[0] = currentHp;
			results[2] = 0;
			results[4] = currentSpD;
			total+= totalStat;

		}
		else if (defense.size() > 0){
			ArrayList<HashMap<String, Object>> defStack = (ArrayList<HashMap<String, Object>>) defense.get(0).get("with-orig-nature");
			int currentHp = (Integer) defStack.get(0).get("hp");
			int currentDef = (Integer) defStack.get(0).get("defense");
			int totalStat = currentHp+currentDef;

			for (int i = 0; i < defStack.size(); i++) {
				int testDef = (Integer) defStack.get(i).get("defense");
				int testHp = (Integer) defStack.get(i).get("hp");

				if (totalStat > testHp+testDef || (totalStat == testHp+testDef && currentHp < testHp)) {
					currentDef = testDef;
					currentHp = testHp;
					totalStat = testHp+testDef;

				}

			}
			results[0] = currentHp;
			results[2] = currentDef;
			results[4] = 0;
			total+= totalStat;
		}
		results[6] = total;
		return results;
	}
		
	public HashMap<String, Object> calculateEVs(Pokemon defPokemon, List<Condition> condition){
		List<ArrayList<Stat>> lists = new ArrayList<ArrayList<Stat>>();
		ArrayList<Object> conditionList = new ArrayList<Object>();
		String personality;
		if (defPokemon.getNature() == null){
			personality="undecided";
		}
		else {
			personality = defPokemon.getNature().getIdentifier();
		}
		Nature nat = defPokemon.getNature();
		
		for (Condition c: condition) {
			if (c.getCond().equals("Knock Out")) {

				ArrayList<HashMap<String, Object>> tObj = new ArrayList<HashMap<String, Object>>();
				Nature[] natures = new Nature[3];
				if(personality.equals("undecided")) {
					if (c.getMove().getIdentifier().equals("body-press")) {
						natures[1] = this.getNature("relaxed");
						natures[0] = this.getNature("hardy");
						natures[2] = this.getNature("hasty");
					}
					else if (c.getMove().getDamageClass().getIdentifier().equals("physical")) {
						natures[1] = this.getNature("adamant");
						natures[0] = this.getNature("hardy");
						natures[2] = this.getNature("modest");
					}
					else {
						natures[1] = this.getNature("modest");
						natures[0] = this.getNature("hardy");
						natures[2] = this.getNature("adamant");
					}
				}
				else {
					if (c.getMove().getIdentifier().equals("body-press")) {
						natures[0] = nat;
						if(nat.getIncreasedStat().equals(nat.getDecreasedStat())) {
							natures[1] = this.getNature("relaxed");
							natures[2] = this.getNature("hasty");
						}
						else if (nat.getIncreasedStat().getLabel().getIdentifier().equals("defense")) {
							natures[1] = this.getNature("hardy");
							natures[2] = this.getNature("hasty");
						}
						else if (nat.getDecreasedStat().getLabel().getIdentifier().equals("defense")) {
							natures[1] = this.getNature("relaxed");
							natures[2] = this.getNature("hardy");
						}
						else {
							natures[1] = this.getNature("relaxed");
							natures[2] = this.getNature("hasty");
						}
					}
					else {
						natures[0] = nat;
						if(nat.getIncreasedStat().equals(nat.getDecreasedStat())) {
							natures[1] = this.getNature("adamant");
							natures[2] = this.getNature("modest");
						}
						else if (c.getMove().getDamageClass().getIdentifier().equals("physical")){
							
							if (nat.getIncreasedStat().getLabel().getIdentifier().equals("attack")) {
								natures[1] = this.getNature("hardy");
								natures[2] = this.getNature("modest");
							}
							else if (nat.getDecreasedStat().getLabel().getIdentifier().equals("attack")) {
								natures[1] = this.getNature("adamant");
								natures[2] = this.getNature("hardy");
							}
							else {
								natures[1] = this.getNature("adamant");
								natures[2] = this.getNature("modest");
							}
						}
						else {
							if (nat.getIncreasedStat().getLabel().getIdentifier().equals("special-attack")) {
								natures[1] = this.getNature("hardy");
								natures[2] = this.getNature("adamant");
							}
							else if (nat.getDecreasedStat().getLabel().getIdentifier().equals("special-attack")) {
								natures[1] = this.getNature("modest");
								natures[2] = this.getNature("hardy");
							}
							else {
								natures[1] = this.getNature("adamant");
								natures[2] = this.getNature("modest");
							}
						}
					}
				}
				for (int j = 0; j < 3; j++) {
					Nature n = natures[j];
					defPokemon.setNature(n);
					boolean found = false;
					for (int i = 0; i <= 252; i+= 4) {
						if (c.getMove().getDamageClass().getIdentifier().equals("physical")) {
							if (c.getMove().getIdentifier().equals("body-press")) {
								defPokemon.getStats().get(2).setEffort(i);
							}
							else {
								defPokemon.getStats().get(1).setEffort(i);
							}
						}
						else if (c.getMove().getDamageClass().getIdentifier().equals("special")) {
							defPokemon.getStats().get(3).setEffort(i);
						}
						int dmg = calculateDamage(c, defPokemon);
						if (dmg >= c.getPokemon().getHP()*c.getHealth()/100){
							HashMap<String, Object> x = new HashMap<String, Object>();
							
							if (c.getMove().getIdentifier().equals("body-press")) {
								x.put("defense", i);
							}
							else if (c.getMove().getDamageClass().getIdentifier().equals("physical")) {
								x.put("attack", i);
							}
							else {
								x.put("special-attack", i);
							}
							x.put("nature", n);
							tObj.add(x);
							found=true;
							break;
						}	
					}
					if (!found) {
						tObj.add(null);
					}
				}
				conditionList.add(tObj);
				
			}
			else if (c.getCond().equals("Outspeed") || c.getCond().equals("Underspeed")) {
				ArrayList<HashMap<String, Object>> tObj = new ArrayList<HashMap<String, Object>> ();
				Nature[] natures = new Nature[3];
				if(personality.equals("undecided")) {
					natures[1] = this.getNature("jolly");
					natures[0] = this.getNature("hardy");
					natures[2] = this.getNature("quiet");
				}
				else {
					natures[0] = nat;
					if(nat.getIncreasedStat().equals(nat.getDecreasedStat())) {
						natures[1] = this.getNature("jolly");
						natures[2] = this.getNature("quiet");
					}
					else if (nat.getIncreasedStat().getLabel().getIdentifier().equals("speed")) {
						natures[1] = this.getNature("hardy");
						natures[2] = this.getNature("quiet");
					}
					else if (nat.getDecreasedStat().getLabel().getIdentifier().equals("speed")) {
						natures[1] = this.getNature("jolly");
						natures[2] = this.getNature("hardy");
					}
					else {
						natures[1] = this.getNature("jolly");
						natures[2] = this.getNature("quiet");
					}
					
				}
				for (int j = 0; j < 3; j++) {
					Nature n = natures[j];
					defPokemon.setNature(n);
					boolean found = false;
					for (int i = 0; i <= 252; i+=4) {
						defPokemon.getStats().get(5).setEffort(i);
						int speedA =  calculateSpeed(defPokemon, c.getYourBoost(), c.getWeather(), c.getTerrain(), false) ;
						int speedB = calculateSpeed(c.getPokemon(), c.getOppBoost(), c.getWeather(), c.getTerrain(), false);


						if (calculateSpeed(defPokemon, c.getYourBoost(), c.getWeather(), c.getTerrain(), false) > calculateSpeed(c.getPokemon(), c.getOppBoost(), c.getWeather(), c.getTerrain(), false)) {
							HashMap<String, Object> x = new HashMap<String, Object>();
							x.put("speed", i);
							x.put("nature", n);
							tObj.add(x);
							found = true;
							break;
						}
					}
					if (!found) {
						tObj.add(null);
					}
				}
				conditionList.add(tObj);

			}
			else {
				ArrayList<ArrayList<HashMap<String, Object>>> tObj = new ArrayList<ArrayList<HashMap<String, Object>>>();
				Nature[] natures = new Nature[3];
				if(personality.equals("undecided")) {
					if (isMovePhysical(c.getMove())) {
						natures[1] = this.getNature("impish");
						natures[0] = this.getNature("hardy");
						natures[2] = this.getNature("mild");
					}
					else {
						natures[1] = this.getNature("calm");
						natures[0] = this.getNature("hardy");
						natures[2] = this.getNature("naive");
					}
				}
				else {
					if(nat.getIncreasedStat().equals(nat.getDecreasedStat())) {
						natures[0] = nat;
						if (isMovePhysical(c.getMove())) {
							natures[1] = this.getNature("impish");
							natures[2] = this.getNature("mild");
						}
						else {
							natures[1] = this.getNature("calm");
							natures[2] = this.getNature("naive");
						}
					}
					else if (isMovePhysical(c.getMove())) {
						natures[0] = nat;
						if (nat.getIncreasedStat().getLabel().getIdentifier().equals("defense")) {
							natures[1] = this.getNature("hardy");
							natures[2] = this.getNature("mild");
						}
						else if (nat.getDecreasedStat().getLabel().getIdentifier().equals("defense")) {
							natures[1] = this.getNature("impish");
							natures[2] = this.getNature("hardy");
						}
						else {
							natures[1] = this.getNature("impish");
							natures[2] = this.getNature("mild");
						}
					}
					else{
						natures[0] = nat;
						if (nat.getIncreasedStat().getLabel().getIdentifier().equals("special-defense")) {
							natures[1] = this.getNature("hardy");
							natures[2] = this.getNature("naive");
						}
						else if (nat.getDecreasedStat().getLabel().getIdentifier().equals("special-defense")) {
							natures[1] = this.getNature("calm");
							natures[2] = this.getNature("hardy");
						}
						else {
							natures[1] = this.getNature("calm");
							natures[2] = this.getNature("naive");
						}
					}
				}
				for (int i = 0; i < 3; i++) {
					ArrayList<HashMap<String, Object>> storage = new ArrayList<HashMap<String, Object>>();
					boolean found = false;
					int maxHP = -1;
					defPokemon.setNature(natures[i]);
					for (int hp = 0; hp <= 252; hp+=4) {
						defPokemon.getStats().get(0).setEffort(hp);
						int damage = calculateDamage(c, defPokemon);
						if (damage < defPokemon.getHP()*c.getHealth()/100){
							found = true;
							maxHP = hp;
							HashMap<String, Object> sx = new HashMap<String, Object>();
							sx.put("hp", hp);
							sx.put("nature", natures[i]);
							if (isMovePhysical(c.getMove())) {
								sx.put("defense", 0);
							}
							else {
								sx.put("special-defense", 0);
							}
							storage.add(sx);
							break;
						}
					}
					if (found) {
						int lastDef = 0;
						for (int hp = maxHP-4; hp >= 0; hp-=4) {
							defPokemon.getStats().get(0).setEffort(hp);
							for (int def = lastDef; def <= 252; def+=4) {
								if (isMovePhysical(c.getMove())) {
									defPokemon.getStats().get(2).setEffort(def);
								}
								else {
									defPokemon.getStats().get(4).setEffort(def);
								}
								if (calculateDamage(c, defPokemon) < defPokemon.getHP()*c.getHealth()/100){
									lastDef = def;
									HashMap<String, Object> sx = new HashMap<String, Object>();
									sx.put("hp", hp);
									sx.put("nature", natures[i]);
									if (isMovePhysical(c.getMove())) {
										sx.put("defense", def);
									}
									else {
										sx.put("special-defense", def);
									}
									storage.add(sx);
									break;
								}
							}
						}
					}
					else {
						for (int def = 0; def <= 252; def+=4) {
							for (int hp = 252; hp >= 0; hp -=4) {
								defPokemon.getStats().get(0).setEffort(hp);
								if (isMovePhysical(c.getMove())) {
									defPokemon.getStats().get(2).setEffort(def);
								}
								else {
									defPokemon.getStats().get(4).setEffort(def);
								}
								if (calculateDamage(c, defPokemon) < defPokemon.getHP()*c.getHealth()/100){
									if (hp != 252) {
										HashMap<String, Object> sx = new HashMap<String, Object>();
										sx.put("hp", hp);
										sx.put("nature", natures[i]);
										if (isMovePhysical(c.getMove())) {
											sx.put("defense", def+4);
										}
										else {
											sx.put("special-defense", def+4);
										}
										storage.add(sx);
									}
									break;
								}
							}
							//End hp for loop
						}
						//End def for loop
					}
					if (storage.size() == 0) {
						tObj.add(null);
					}
					else {
						tObj.add(storage);
					}
					//End else for found hp.
				}
				//End nature for loop
				conditionList.add(tObj);
			}
			//End else for defense condition
		}
		//End for loop for all conditions
		
		//FROM HERE: LOOP THROUGH AND FIGURE OUT APPROPRIATE LOCATION/STACK/ARRAY.
		
		ArrayList<String> suggestions = new ArrayList<String>();
		int conditionCounter = 0;
		String boostOn = "";
		boolean[] boosted = {false, false, false, false, false};
		ArrayList<Boolean> boost = new ArrayList<Boolean>();
		ArrayList<HashMap<String, Object>> conditionPossible = new ArrayList<HashMap<String, Object>>();
		ArrayList<Boolean> hinder = new ArrayList<Boolean>();
		ArrayList<HashMap<String, Object>> attacks = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> defense = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> spas = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> spds = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> speed = new ArrayList<HashMap<String, Object>>();
		for (Object c: conditionList) {
			ArrayList<Object> conds = (ArrayList<Object>) c;
			if (conds.get(0) == null && conds.get(1) == null && conds.get(2) == null) {
				HashMap<String, Object> suggestion= new HashMap<String, Object>();
				suggestion.put("suggestion", "Not possible, consider changing items, or adding boosts");
				suggestion.put("possible", false);
				conditionPossible.add(suggestion);
				boost.add(false);
				hinder.add(false);
			}
			else if ((conds.get(0) == null && conds.get(1) == null) || (conds.get(1) == null && conds.get(2) == null) || (conds.get(0) == null && conds.get(2) == null)) {
				HashMap<String, Object> suggestion= new HashMap<String, Object>();
				suggestion.put("possible", true);
				boost.add(true);
				hinder.add(false);
				conditionPossible.add(suggestion);
				if(condition.get(conditionCounter).getCond().equals("Outspeed") || condition.get(conditionCounter).getCond().equals("Underspeed")) {
					boosted[4] = true;
				}
				else if (condition.get(conditionCounter).getCond().equals("Knock Out")) {
					if (condition.get(conditionCounter).getMove().getDamageClass().getIdentifier().equals("special")){
						boosted[2] = true;
					}
					else if (condition.get(conditionCounter).getMove().getDamageClass().getIdentifier().equals("physical")) {
						boosted[0] = true;
					}
				}
				else {
					if (isMovePhysical(condition.get(conditionCounter).getMove())){
						boosted[1] = true;
					}
					else {
						boosted[3] = true;
					}
				}

			}
			else if (conds.get(0) == null || conds.get(1) == null || conds.get(2) == null){
				HashMap<String, Object> suggestion= new HashMap<String, Object>();
				suggestion.put("possible", true);
				boost.add(false);
				hinder.add(false);
				conditionPossible.add(suggestion);

			}
			else {
				HashMap<String, Object> suggestion= new HashMap<String, Object>();
				suggestion.put("possible", true);
				boost.add(false);
				hinder.add(true);
				conditionPossible.add(suggestion);
			}
			if ((Boolean) conditionPossible.get(conditionCounter).get("possible")) {

				if(condition.get(conditionCounter).getCond().equals("Outspeed") || condition.get(conditionCounter).getCond().equals("Underspeed")) {			
					determineOrderOfStat(speed, "speed", conds, condition, conditionCounter);
				}
				else if(condition.get(conditionCounter).getCond().equals("Knock Out")) {
					if (isMovePhysical(condition.get(conditionCounter).getMove())) {
						determineOrderOfStat(attacks, "attack", conds, condition, conditionCounter);
					}
					else {
						determineOrderOfStat(spas, "special-attack", conds, condition, conditionCounter);
					}
				}
				else {
					String statToComp;			
					ArrayList<HashMap<String, Object>> arrToInsertIn;
					if (isMovePhysical(condition.get(conditionCounter).getMove())) {
						statToComp = "defense";
						arrToInsertIn = defense;
					}
					else {
						statToComp = "special-defense";
						arrToInsertIn = spds;

					}
					int statToCompare;
					if (conds.get(0) == null) {
						ArrayList<HashMap<String, Object>> ss = (ArrayList<HashMap<String, Object>>) conds.get(1);
						statToCompare = (Integer) ss.get(0).get("hp") + (Integer) ss.get(0).get(statToComp);
					}
					else if (conds.get(1) == null) {
						ArrayList<HashMap<String, Object>> ss = (ArrayList<HashMap<String, Object>>) conds.get(0);
						statToCompare = (Integer) ss.get(0).get("hp") + (Integer) ss.get(0).get(statToComp);
					}
					else {
						ArrayList<HashMap<String, Object>> ss = (ArrayList<HashMap<String, Object>>) conds.get(0);
						ArrayList<HashMap<String, Object>> sx = (ArrayList<HashMap<String, Object>>) conds.get(1);

						int tempStat0 = (Integer) ss.get(0).get("hp") + (Integer) ss.get(0).get(statToComp);
						int tempStat1 = (Integer) sx.get(0).get("hp") + (Integer) sx.get(0).get(statToComp);
						if (tempStat1 < tempStat0) {
							statToCompare = tempStat1;
						}
						else {
							statToCompare = tempStat0;
						}
					}
					HashMap<String, Object> stor = new HashMap<String, Object>();
					stor.put("condition", condition.get(conditionCounter));
					stor.put(statToComp, statToCompare);
					if (conds.get(0) != null) {
						ArrayList<HashMap<String, Object>> ss= (ArrayList<HashMap<String, Object>>) conds.get(0); 
						stor.put("with-orig-nature", ss);
					}
					else {
						stor.put("with-orig-nature", null);
					}
					stor.put("allconds", conds);
					placeIntoArray(arrToInsertIn, stor, statToCompare, statToComp);


				}
			}
			
			conditionCounter ++;
		}
		//CREATED STACK OF POTENTIAL CANDIDATES
		
		
		//NOW FIGURE OUT IF THERE ARE ANY ISSUES (INCOMPATIBLE NATURES), IF SO WE REMOVE.
		int countBoosts = 0;
		for (boolean b: boosted) {
			if (b) {
				countBoosts++;
			}
		}
		
		ArrayList<ArrayList<HashMap<String, Object>>> statStack = new ArrayList<ArrayList<HashMap<String, Object>>>();
		statStack.add(attacks);
		statStack.add(defense);
		statStack.add(spas);
		statStack.add(spds);
		statStack.add(speed);
		if (countBoosts > 1) {
			Nature originalBoostedStat = defPokemon.getNature();
			String[] statlabel = {"attack", "defense", "special-attack", "special-defense", "speed"};

			String statBoosted = null;
			if (originalBoostedStat != null && !originalBoostedStat.getDecreasedStat().equals(originalBoostedStat.getIncreasedStat())) {
				statBoosted = originalBoostedStat.getIncreasedStat().getLabel().getIdentifier();
			}
			for (int i = 0; i < 5; i ++) {
				if (!statBoosted.equals(statlabel[i]) && boosted[i]) {
					ArrayList<HashMap<String, Object>> sstack = statStack.get(i);
					if (statlabel[i].equals("defense") || statlabel[i].equals("special-defense")) {
						ArrayList<ArrayList<HashMap<String, Object>>> allConds = (ArrayList<ArrayList<HashMap<String, Object>>>)sstack.get(0).get("allconds");
						while (allConds.get(0).size() == 0 || allConds.get(0).size() == 0 ) {
							sstack.remove(0);
							allConds = (ArrayList<ArrayList<HashMap<String, Object>>>)sstack.get(0).get("allconds");
						}
					}
					else {
						ArrayList<HashMap<String, Object>> allConds = (ArrayList<HashMap<String, Object>>)sstack.get(0).get("allconds");
						while(allConds.get(0) == null || allConds.get(1) == null) {
							sstack.remove(0);
							allConds = (ArrayList<HashMap<String, Object>>)sstack.get(0).get("allconds");
						}
					}
				}
			}
		}
		
		//FOLLOW BY PICKING EVS, MINIMIZE, AND SEE IF IT IS OVER 508. IF SO REMOVE THE ONE WITH MOST EVS.
		int[] res;
		do {
			res = calcAllEVs(attacks, defense, spas, spds, speed);

			if (res[6] > 508) {
				int highest = 0;
				int highestidx = 0;
				if (res[1] > highest) {
					highest = res[1];
					highestidx = 1;
				}
				if (res[3] > highest) {
					highest = res[3];
					highestidx = 3;
				}
				if (res[5] > highest) {
					highest = res[5];
					highestidx = 5;
				}
				if (res[2]+res[0] > highest*2) {
					highest = res[2];
					highestidx = 2;
				}
				if(res[0]+res[4]> highest*2) {
					highest=res[4];
					highestidx=4;
				}
				switch(highestidx) {
				case 1: attacks.remove(0);
					break;
				case 2: defense.remove(0);
					break;
				case 3: spas.remove(0);
					break;
				case 4: spds.remove(0);
					break;
				case 5: speed.remove(0);
					break;
				}
			}

		} while(res[6] > 508);
		res[6] = 508-res[6];
		//OPTIMIZE IF POSSIBLE
		defPokemon.setNature(nat);
		Stat statToHinder;
		int[] hinderArray = {0, 0, 0, 0, 0, 0};
		if (defPokemon.getNature() != null && defPokemon.getNature().getDecreasedStat().getLabel().getIdentifier().equals("speed")) {
			statToHinder = this.getStatByName("speed");
		}
		else if (defPokemon.getNature() != null && (defPokemon.getNature().getDecreasedStat().getLabel().getIdentifier().equals("special-attack") || defPokemon.getNature().getDecreasedStat().getLabel().getIdentifier().equals("attack"))) {
			statToHinder = this.getStatByName(defPokemon.getNature().getDecreasedStat().getLabel().getIdentifier());

		}
		else if (attacks.size() == 0) {
			statToHinder =this.getStatByName("attack");

		}
		
		else if (spas.size() == 0) {
			statToHinder = this.getStatByName("special-attack");
		}
		
		else {
			Stat testHinder = this.getStatByName("attack");
			Stat[] statToTest = {this.getStatByName("attack"), this.getStatByName("defense"), this.getStatByName("special-attack"), this.getStatByName("special-defense"), this.getStatByName("speed")};
			int lowestDiff = 508;
			for (int i = 0; i < 5; i ++) {
				if (statStack.get(i).size() > 0) {
					ArrayList<Object> listOfNatures = (ArrayList<Object>) statStack.get(i).get(0).get("allconds");
					Object statSomething = listOfNatures.get(2);
					if (statSomething != null) {
						if (i% 2 == 1) {
							ArrayList<HashMap<String, Object>> statMapList = (ArrayList<HashMap<String, Object>>) statSomething;
							//if (isHPEnabled) > logic later
							
							for (int x = 0; x < statMapList.size(); x++) {
								String statL = (i == 1 ? "defense" : "special-defense");
								
								if ((Integer) statMapList.get(x).get("hp") + (Integer) statMapList.get(x).get(statL) - (res[0] + res[i+1]) < lowestDiff) {
									lowestDiff = (res[0] + res[i+1]);
									testHinder = statToTest[i];
									hinderArray[0] = (Integer) statMapList.get(x).get("hp") - res[0];
									for (int z=1; z < i; z++) {
										hinderArray[z] = 0;
									}
									hinderArray[i+1] = (Integer) statMapList.get(x).get(statL) - res[i+1];
								}
							}
						}
						else {
							HashMap<String, Object> statMap = (HashMap<String, Object>) statSomething;
							if (Math.abs((Integer) statMap.get(statToTest[i].getLabel().getIdentifier()) - res[i+1] ) < lowestDiff) {
								testHinder = statToTest[i];
								lowestDiff  = Math.abs((Integer) statMap.get(statToTest[i].getLabel().getIdentifier()) - res[i+1]);
								for (int z=0; z < i; z++) {
									hinderArray[z] = 0;
								}
								hinderArray[i+1] = (Integer) statMap.get(statToTest[i].getLabel().getIdentifier()) - res[i+1];
							}
						}
					}
				}
				else {
					testHinder = statToTest[i];
					break;
				}
			}
			statToHinder = testHinder;
			
		}
		
		Stat statToBoost = this.getStatByName("attack");
		Stat[] statToTest = {this.getStatByName("attack"), this.getStatByName("defense"), this.getStatByName("special-attack"), this.getStatByName("special-defense"), this.getStatByName("speed")};
		int highestDiff = 0;
		int[] boostArray = {0, 0, 0, 0, 0, 0};

		for (int i = 0; i < 5; i ++) {
			if (statStack.get(i).size() > 0) {
				ArrayList<Object> listOfNatures = (ArrayList<Object>) statStack.get(i).get(0).get("allconds");
				if (listOfNatures.get(1) == null || listOfNatures.get(0) == null) {
					statToBoost = statToTest[i];
					break;
				}
				
				if (i% 2 == 1) {
					ArrayList<HashMap<String, Object>> statMapList;  

					if (defPokemon.getNature() == null || (defPokemon.getNature().getDecreasedStat().equals(defPokemon.getNature().getIncreasedStat()) || !defPokemon.getNature().getIncreasedStat().equals(statToTest[i]))) {
						statMapList = (ArrayList<HashMap<String, Object>>) listOfNatures.get(1);
					}
					else {
						statMapList = (ArrayList<HashMap<String, Object>>) listOfNatures.get(0);
					}
						//if (isHPEnabled) > logic later
					
					for (int x = 0; x < statMapList.size(); x++) {
						String statL = (i == 1 ? "defense" : "special-defense");
						
						if ((Integer) statMapList.get(x).get("hp") + (Integer) statMapList.get(x).get(statL) - (res[0] + res[i+1]) > highestDiff) {
							highestDiff = (Integer) statMapList.get(x).get("hp") + (Integer) statMapList.get(x).get(statL) - (res[0] + res[i+1]);
							statToBoost = statToTest[i];
							boostArray[0] = (Integer) statMapList.get(x).get("hp") - res[0];
							for (int z=0; z < i; z++) {
								boostArray[z] = 0;
							}
							boostArray[i+1] = (Integer) statMapList.get(x).get(statL) - res[1];

						}
					}
				}	
				else {
					HashMap<String, Object> statMapList;  
					if (defPokemon.getNature() == null || (defPokemon.getNature().getDecreasedStat().equals(defPokemon.getNature().getIncreasedStat()) || !defPokemon.getNature().getIncreasedStat().equals(statToTest[i]))) {
						statMapList = (HashMap<String, Object>) listOfNatures.get(1);
					}
					else {
						statMapList = (HashMap<String, Object>) listOfNatures.get(0);
					}

					if (Math.abs((Integer) statMapList.get(statToTest[i].getLabel().getIdentifier()) - res[i+1] ) > highestDiff) {
						statToBoost = statToTest[i];
						highestDiff  = Math.abs((Integer) statMapList.get(statToTest[i].getLabel().getIdentifier()) - res[i+1]);
						for (int z=0; z < i; z++) {
							boostArray[z] = 0;
						}
						boostArray[i+1] = Math.abs((Integer) statMapList.get(statToTest[i].getLabel().getIdentifier()) - res[i+1]);

					}
				}
			}
		}
		
		//TODO: We found Stats to increase and lower, as well as difference in stats. Now we need to implement the difference function.
		Nature suggestedNature = natureRepo.findNatureWithStats(statToHinder, statToBoost);
		System.out.println(suggestedNature.getIdentifier());
		int[] suggestedRes = new int[7];
		int suggestedTotal = 0;
		for (int i = 0; i < 6; i++) {
			suggestedRes[i] = res[i] + hinderArray[i] - boostArray[i];
			suggestedTotal+= suggestedRes[i];
		}
		suggestedRes[6] = 508-suggestedTotal;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultEVarr", res);
		resultMap.put("suggestedEVarr", suggestedRes);
		resultMap.put("suggestedNature", suggestedNature);
		return resultMap;
	}
	
}
