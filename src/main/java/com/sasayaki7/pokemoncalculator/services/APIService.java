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
import com.sasayaki7.pokemoncalculator.models.LegacyMove;
import com.sasayaki7.pokemoncalculator.models.Move;
import com.sasayaki7.pokemoncalculator.models.MoveTarget;
import com.sasayaki7.pokemoncalculator.models.Nature;
import com.sasayaki7.pokemoncalculator.models.Pokemon;
import com.sasayaki7.pokemoncalculator.models.Stat;
import com.sasayaki7.pokemoncalculator.models.Type;
import com.sasayaki7.pokemoncalculator.repositories.AbilityRepository;
import com.sasayaki7.pokemoncalculator.repositories.ItemRepository;
import com.sasayaki7.pokemoncalculator.repositories.LegacyMoveRepository;
import com.sasayaki7.pokemoncalculator.repositories.MoveRepository;
import com.sasayaki7.pokemoncalculator.repositories.MoveTargetRepository;
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
	
	@Autowired
	private LegacyMoveRepository legacyRepo;
	
	@Autowired
	private MoveTargetRepository moveTargRepo;
	
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
	
	
	public String lowerStringRemoveDashes(String s) {
		return s.toLowerCase().replace(' ', '-');
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
	
	public Type getType(Long id) {
		Optional<Type> tempType= typeRepo.findById(id);
		if (tempType.isPresent()) {
			return tempType.get();
		}
		else {
			return null;
		}
	}
	
	public MoveTarget getMoveTarget(String movetarg) {
		Optional<MoveTarget> tempType= moveTargRepo.findByIdentifier(movetarg);
		if (tempType.isPresent()) {
			return tempType.get();
		}
		else {
			return null;
		}
	}
	
	public Stat getStatByName(String name) {
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
	
	public LegacyMove getLegacyMove(String name, int generation) {
		List<LegacyMove> thing = legacyRepo.getMoveWithGen(name, generation);
		if (thing.size() > 0) {
			return thing.get(0);
		}
		else {
			return null;
		}
	}
	
	public List<Nature> getNatures(){
		return natureRepo.findAll();
	}
	
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
		
	public int pokeRound(double damage) {
		return (int) Math.ceil(damage-0.5);
	}
	
	public boolean affectedBySheerForce(Move m) {
		int[] ids = {3, 5, 6, 7, 21, 32, 37, 69, 70, 71, 72, 73, 74, 76, 77, 78, 93, 126, 139, 140, 141, 147, 151, 153, 159, 198, 201, 210, 254, 261, 263, 264, 268, 274, 275, 276, 296, 297, 330, 331, 332, 333, 334, 372, 375, 380, 393, 396, 397, 419, 425, 500};
		for (int i = 0; i < ids.length; i++) {
			if (m.getEffectId() == ids[i]) {
				return true;
			}
		}
		return false;
	}
	
	
	public boolean affectedByReckless(Move m) {
		int[] ids = {46, 49, 199, 254, 263, 270};
		for (int i = 0; i < ids.length; i++) {
			if (m.getEffectId() == ids[i]) {
				return true;
			}
		}
		return false;
	}
	
	public boolean affectedByMegaLauncher(Move m) {
		String[] pulses = {"aura-sphere", "dark-pulse", "dragon-pulse", "heal-pulse", "origin-pulse", "terrain-pulse", "water-pulse"};
		for (int i = 0; i < pulses.length; i++) {
			if(pulses[i].equals(m.getIdentifier())) {
				return true;
			}
		}
		return false;
	}
	
	
	public boolean affectedByIronFist(Move m) {
		String[] punches = {"bullet-punch", "double-iron bash", "drain-punch", "dynamic-punch", "fire-punch", "focus-punch", "hammer-arm", "ice-punch", "mach-punch", "mega-punch", "meteor-mash", "plasma-fists", "power-up-punch", "shadow-punch", "surging-strikes", "thunder-punch", "wicked-blow"};
		for (int i = 0; i < punches.length; i++) {
			if(punches[i].equals(m.getIdentifier())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean canKnockOffItem(String item) {
		String[] items = {"metagrossite", "gengarite", "gardevoirite", "charizardite-x", "venusaurite", "blastoisinite", "mewtwonite-x", "mewtwonite-y", "blazikenite", "medichamite", "houndoominite", "aggronite", "banettite", "tyranitarite", "scizorite", "pinsirite", "aerodactylite", "lucarionite", "abomasite", "kangaskhanite", 
				"gyaradosite", "absolite", "charizardite-y", "alakazite", "heracronite", "mawilite", "manectite", "garchompite", "latiasite", "latiosite", "swampertite", "sceptilite", "sablenite", "altarianite", "galladite", "audinite", "sharpedonite", "slowbronite", "steelixite", "pidgeotite", "glalitite", "diancite", "camperuptite", "lopunnite", "salamencite", "beedrillite", "griseous-orb", 
				"normalium-z", "firium-z", "waterium-z", "electrium-z", "grassium-z", "icium-z", "fightinium-z", "poisonium-z", "groundium-z", "flyinium-z", "psychium-z", "buginium-z", "rockium-z", "ghostium-z", "dragonium-z", "darkinium-z", "steelium-z", "fairium-z", "pikanium-z", "decidium-z", "incinium-z", "primarium-z", "tapunium-z", "marshadium-z", "aloraichium-z", "snorlium-z", "eevium-z", "mewnium-z", 
				"pikashunium-z"};
		for (int i = 0; i < items.length; i++) {
			if (item.equals(items[i])) {
				return false;
			}
		}
		return true;
	}
	
	public boolean affectedByStrongJaw(Move m) {
		String[] bites = {"bite", "crunch", "fire-fang", "fishious-rend", "ice-fang", "jaw-lock", "poison-fang", "psychic-fangs", "thunder-fang"};
		for (int i = 0; i < bites.length; i++) {
			if(bites[i].equals(m.getIdentifier())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean affectedByToughClaws(Move m) {
		String[] physicalnoncontact = {"attack-order", "aura-wheel", "beat-up", "bone-rush", "bonemerang", "bulldoze", "bullet-seed", 
				"diamond-storm", "dragon-darts", "drum-beating", "earthquake", "explosion", "feint", "fissure", "fling", "freeze-shock", 
				"fusion-bolt", "glacial-lance", "grav-apple", "gunk-shot", "ice-shard", "icicle-crash", "icicle-spear", "lands-wrath", "leafage", 
				"metal-burst", "meteor-assault", "pay-day", "petal-blizzard", "pin-missile", "poison-sting", "poltergeist", "precipice-blades", 
				"present", "psycho-cut", "pyro-ball", "razor-leaf", "rock-blast", "rock-slide", "rock-throw", "rock-tomb", "rock-wrecker", "sacred-fire", 
				"sand-tomb", "scale-shot", "seed-bomb", "self-destruct", "shadow-bone", "sky-attack", "smack-down", "spirit-shackle", "stone-edge", 
				"thousand-arrows", "thousand-waves"};
		String[] specialcontact = {"draining-kiss", "grass-knot", "infestation", "petal-dance"};
		if (m.getDamageClass().getIdentifier().equals("physical")) {
			for (int i = 0; i < physicalnoncontact.length; i++) {
				if(physicalnoncontact[i].equals(m.getIdentifier())) {
					return false;
				}
			}
			return true;
		}
		else if (m.getDamageClass().getIdentifier().equals("special")) {
			for (int i = 0; i < specialcontact.length; i++) {
				if(specialcontact[i].equals(m.getIdentifier())) {
					return true;
				}
			}
			return false;
		}
		return false;
	}
	
	public boolean ignoresAbilities(Pokemon atkPokemon) {
		if (atkPokemon.getAbility().getIdentifier().equals("teravolt") || atkPokemon.getAbility().getIdentifier().equals("mold-breaker") || atkPokemon.getAbility().getIdentifier().equals("turboblaze")) {
			return true;
		}
		return false;
	}
	
	public boolean isPokemonFlying(Pokemon pokemon, Condition c) {
		if (c.isGravity() || c.isSmackDown()) {
			return false;
		}
		if (pokemon.getItem().equals("iron-ball")) {
			return false;
		}
		if (pokemon.isType("flying")) {
			return true;
		}
		if (pokemon.getItem().equals("air-balloon")) {
			return true;
		}
		if (pokemon.getAbility().getIdentifier().equals("levitate")) {
			return true;
		}
		return false;
	}
	
	public int convertToZ(Move m) {
		int bp = m.getPower();
		if (m.getIdentifier().equals("v-create")) {
			return 220;
		}
		else if (m.getIdentifier().equals("thousand-arrows")) {
			return 180;
		}
		else if (m.getIdentifier().equals("hex")) {
			return 160;
		}
		else if (m.getIdentifier().equals("mega-drain")) {
			return 120;
		}
		else if (m.getIdentifier().equals("gear-grind")) {
			return 180;
		}
		else if (m.getIdentifier().equals("core-enforcer")) {
			return 140;
		}
		else if (m.getIdentifier().equals("weather-ball")) {
			return 160;
		}
		else if (bp <= 55) {
			return 100;
		}
		else if (bp <= 65) {
			return 120;
		}
		else if (bp <= 75) {
			return 140;
		}
		else if (bp <= 85) {
			return 160;
		}
		else if (bp <= 95) {
			return 175;
		}
		else if (bp <= 100) {
			return 180;
		}
		else if (bp <=  110) {
			return 185;
		}
		else if (bp <= 125) {
			return 190;
		}
		else if (bp <= 130) {
			return 195;
		}
		else {
			return 200;
		}
	}
	
	
	public int calculateBaseDamage(Condition condition, Pokemon atkPokemon, Pokemon defPokemon) {
		Move m = condition.getMove();
		int basePower = m.getPower();
		int attack = 0;
		int defense = 0;
		int multiplier = 4096;
		boolean attackerDMax = ((condition.isOppDmax() && condition.getPokemon().equals(atkPokemon)) || (condition.isSelfDmax() && condition.getPokemon().equals(defPokemon)));
		boolean defenderDMax = ((condition.isSelfDmax() && condition.getPokemon().equals(atkPokemon)) || (condition.isOppDmax() && condition.getPokemon().equals(defPokemon)));

		//---------------------------------------------------------------------------------
		//-----------------------------BEGIN BASE POWER------------------------------------
		//---------------------------------------------------------------------------------
		
		if (attackerDMax) {
			basePower = m.getMaxMovePower();
		}
		
		
		//LOW KICK/GRASS KNOT
		if (m.getIdentifier().equals("grass-knot") || m.getIdentifier().equals("low-kick")) {
			if (defenderDMax && ! attackerDMax) {
				return 0;
			}
			if (defPokemon.getWeight() < 100) {
				m.setPower(20);
			}
			else if (defPokemon.getWeight() < 250) {
				m.setPower(40);
			}
			else if (defPokemon.getWeight() < 500) {
				m.setPower(60);
			}
			else if (defPokemon.getWeight() < 1000) {
				m.setPower(80);
			}
			else if (defPokemon.getWeight() < 2000) {
				m.setPower(100);
			}
			else {
				m.setPower(120);
			}
		}
		
		
		
		//HEAVY SLAM/HEAT CRASH
		if (m.getIdentifier().equals("heavy-slam") || m.getIdentifier().equals("heat-crash")) {
			if (defenderDMax && ! attackerDMax) {
				return 0;
			}
			if (atkPokemon.getWeight()/defPokemon.getWeight() < 2) {
				m.setPower(40);
			}
			else if (atkPokemon.getWeight()/defPokemon.getWeight() < 3) {
				m.setPower(60);
			}
			else if (atkPokemon.getWeight()/defPokemon.getWeight() < 4) {
				m.setPower(80);
			}
			else if (atkPokemon.getWeight()/defPokemon.getWeight() < 5) {
				m.setPower(100);
			}
			else {
				m.setPower(120);
			}
		}
		
		
		//ACROBATICS
		if (m.getIdentifier().equals("acrobatics") && (atkPokemon.getItem().equals("None") || atkPokemon.getItem().equals("flying-gem"))) {
			basePower = 110;
		}
		
		//WAKE-UP-SLAP
		if (defPokemon.getStatus().equals("Sleep") && m.getIdentifier().equals("wake-up-slap")) {
			basePower = 140;
		}
		
		//SMELLING SALTS
		if (defPokemon.getStatus().equals("Paralyze") && m.getIdentifier().equals("smelling-salts")) {
			basePower = 140;
		}
		
		//WATER SHURIKEN
		if (atkPokemon.getIdentifier().equals("ash-greninja") && m.getIdentifier().equals("water-shuriken")) {
			basePower = 20;
	
		}
		
		//HEX
		if (!defPokemon.getStatus().equals("None") && m.getIdentifier().equals("hex")) {
			basePower = 130;
		}
		
		//WEATHER-BALL
		if (!condition.getWeather().equals("None") && m.getIdentifier().equals("weather-ball")) {
			basePower = 100;
			if (condition.getWeather().equals("Rain")) {
				m.setAttackType(this.getType("water"));
			}
			else if (condition.getWeather().equals("Sun")) {
				m.setAttackType(this.getType("fire"));
			}
			else if (condition.getWeather().equals("Sand")) {
				m.setAttackType(this.getType("rock"));
			}
			else if (condition.getWeather().equals("Hail")) {
				m.setAttackType(this.getType("ice"));
			}

		}

		
		
		//BOLT BEAK/FISHIOUS REND
		if (m.getIdentifier().equals("fishious-rend") || m.getIdentifier().equals("bolt-beak")) {
			if (defPokemon.equals(condition.getPokemon()) && this.calculateSpeed(defPokemon, condition.getOppBoost(), condition.getWeather(), condition.getTerrain(), condition.isFoeTw()) < this.calculateSpeed(atkPokemon, condition.getYourBoost(), condition.getWeather(), condition.getTerrain(), condition.isYourTw())) {
				basePower=170;
			}
			else if (atkPokemon.equals(condition.getPokemon()) && this.calculateSpeed(atkPokemon, condition.getOppBoost(), condition.getWeather(), condition.getTerrain(), condition.isFoeTw()) > this.calculateSpeed(defPokemon, condition.getYourBoost(), condition.getWeather(), condition.getTerrain(), condition.isYourTw())) {
				basePower=170;
			}
		}
		
		//----------------------------------------------------------------------
		//TODO: Assurance, Avalanche, Revenge, Pledges, Gust/Twister, Round, Pursuit, Stomping Tantrum
		//----------------------------------------------------------------------

		
		//BASE POWER LOGIC (IT'LL BE MESSY)
		if (condition.isAuraBreak()) {
			if (m.isType("fairy") && condition.isFairyAura() || m.isType("dark") && condition.isDarkAura()) {
				multiplier = (multiplier*3072/4096);
			}
		}
		
		//----------------------------------------------------------------------
		//TODO: RIVALRY
		//----------------------------------------------------------------------

		
		//-IZE -ATE abilities
		if(m.isType("normal") && (atkPokemon.getAbility().getIdentifier().equals("galvanize") || atkPokemon.getAbility().getIdentifier().equals("aerilate") || 
				atkPokemon.getAbility().getIdentifier().equals("refrigerate") || atkPokemon.getAbility().getIdentifier().equals("pixilate"))) {
			if (condition.getGeneration() == 6) {
				multiplier = (multiplier*5325/4096);
			}
			else {
				multiplier = (multiplier*4915/4096);
			}
			if (atkPokemon.getAbility().getIdentifier().equals("refrigerate")) {
				m.setAttackType(this.getType("ice"));
			}
			else if (atkPokemon.getAbility().getIdentifier().equals("galvanize")) {
				m.setAttackType(this.getType("electric"));
			}
			else if (atkPokemon.getAbility().getIdentifier().equals("pixilate")) {
				m.setAttackType(this.getType("fairy"));
			}
			else if (atkPokemon.getAbility().getIdentifier().equals("aerilate")) {
				m.setAttackType(this.getType("flying"));
			}
		}
		else if (atkPokemon.getAbility().getIdentifier().equals("normalize")) {
			multiplier = (multiplier*4915/4096);
			m.setAttackType(this.getType("normal"));
		}
		
		//BATTERY
		if (condition.isBattery() && m.getDamageClass().getIdentifier().equals("special")) {
			multiplier = (multiplier* 5325/4096);
		}
		
		//SAND FORCE
		if (condition.getWeather().equals("Sand") && atkPokemon.getAbility().getIdentifier().equals("sand-force") && (m.getAttackType().getIdentifier().equals("rock") || m.getAttackType().getIdentifier().equals("steel") || m.getAttackType().getIdentifier().equals("ground"))) {
			multiplier = (multiplier* 5325/4096);
		}
		
		//----------------------------------------------------------------------
		//TODO: Analytic
		//----------------------------------------------------------------------

		//IRON FIST
		if (!condition.isZ() && !((condition.isSelfDmax() && !atkPokemon.equals(condition.getPokemon()) || (condition.isOppDmax() && atkPokemon.equals(condition.getPokemon()))))){
			if (atkPokemon.getAbility().getIdentifier().equals("iron-fist") &&  this.affectedByIronFist(m)) {
				multiplier = (multiplier*4915/4096);
			}
		}		
		
		
		//RECKLESS
		if (!condition.isZ() && !((condition.isSelfDmax() && !atkPokemon.equals(condition.getPokemon()) || (condition.isOppDmax() && atkPokemon.equals(condition.getPokemon()))))){
			if (atkPokemon.getAbility().getIdentifier().equals("reckless") &&  this.affectedByReckless(m)) {
				multiplier = (multiplier*4915/4096);
			}
		}
		
		
		//SHEER FORCE
		if (!condition.isZ() && !((condition.isSelfDmax() && !atkPokemon.equals(condition.getPokemon()) || (condition.isOppDmax() && atkPokemon.equals(condition.getPokemon()))))){
			if (atkPokemon.getAbility().getIdentifier().equals("sheer-force") &&  this.affectedBySheerForce(m)) {
				multiplier = (multiplier*5325/4096);
			}
		}

		
		//TOUGH CLAWS
		if (!condition.isZ() && !((condition.isSelfDmax() && !atkPokemon.equals(condition.getPokemon()) || (condition.isOppDmax() && atkPokemon.equals(condition.getPokemon()))))){
			if (atkPokemon.getAbility().getIdentifier().equals("tough-claws") &&  this.affectedByToughClaws(m)) {
				multiplier = (multiplier*5325/4096);
			}
		}		
		
		//FAIRY/DARK AURA
		if ((condition.isDarkAura() && m.getAttackType().getIdentifier().equals("dark")) || (condition.isFairyAura() && m.getAttackType().getIdentifier().equals("fairy"))) {
			multiplier = (multiplier*5448/4096);
		}
		

		
		//TECHNICIAN
		if (m.getPower()*multiplier/4096 <= 60 && atkPokemon.getAbility().getIdentifier().equals("technician")) {
			multiplier = (multiplier*6144/4096);
		}
		
		//FLARE BOOST
		if (atkPokemon.getAbility().getIdentifier().equals("flare-boost") && m.getDamageClass().getIdentifier().equals("special") && atkPokemon.getStatus().equals("Burn")) {
			multiplier = (multiplier*6144/4096);
		}
		
		//TOXIC BOOST
		if (atkPokemon.getAbility().getIdentifier().equals("toxic-boost") && m.getDamageClass().getIdentifier().equals("physical") && (atkPokemon.getStatus().equals("Toxic") || atkPokemon.getStatus().equals("Poison"))) {
			multiplier = (multiplier*6144/4096);
		}
		
		//DRAGON'S MAW
		if (atkPokemon.getAbility().getIdentifier().equals("dragons-maw") && m.getAttackType().getIdentifier().equals("dragon")) {
			multiplier = (multiplier*6144/4096);
		}
		
		//TRANSISTOR
		if (atkPokemon.getAbility().getIdentifier().equals("transistor") && m.getAttackType().getIdentifier().equals("electric")) {
			multiplier = (multiplier*6144/4096);
		}
		
		//STRONG JAW
		if (!condition.isZ() && !((condition.isSelfDmax() && !atkPokemon.equals(condition.getPokemon()) || (condition.isOppDmax() && atkPokemon.equals(condition.getPokemon()))))){
			if (atkPokemon.getAbility().getIdentifier().equals("strong-jaw") &&  this.affectedByStrongJaw(m)) {
				multiplier = (multiplier*6144/4096);
			}
		}
		
		
		//MEGA LAUNCHER
		if (!condition.isZ() && !((condition.isSelfDmax() && !atkPokemon.equals(condition.getPokemon()) || (condition.isOppDmax() && atkPokemon.equals(condition.getPokemon()))))){
			if (atkPokemon.getAbility().getIdentifier().equals("mega-launcher") &&  this.affectedByMegaLauncher(m)) {
				multiplier = (multiplier*6144/4096);
			}
		}
		
		//HEATPROOF
		if (defPokemon.getAbility().getIdentifier().equals("heatproof") && m.getAttackType().getIdentifier().equals("fire") && !this.ignoresAbilities(atkPokemon)) {
			multiplier = (multiplier*2048/4096);
		}
		
		//DRY SKIN
		if (defPokemon.getAbility().getIdentifier().equals("dry-skin") && m.getAttackType().getIdentifier().equals("fire")) {
			multiplier = (multiplier*5120/4096);
		}

		//MUSCLE BAND/WISE GLASSES
		if ((atkPokemon.getItem().equals("wise-glasses") && m.getDamageClass().getIdentifier().equals("special")) || (atkPokemon.getItem().equals("muscle-band") && m.getDamageClass().getIdentifier().equals("physical"))) {
			multiplier = (multiplier*4505/4096);
		}
		
		
		//TYPE BOOSTING ITEM
		if (!condition.isItemConsumed()) {
			if (m.getAttackType().getIdentifier().equals("ground") && (atkPokemon.getItem().equals("soft-sand") || atkPokemon.getItem().equals("earth-plate"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (m.getAttackType().getIdentifier().equals("normal") && (atkPokemon.getItem().equals("silk-scarf") || atkPokemon.getItem().equals("polkadot-bow") || atkPokemon.getItem().equals("pink-bow"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (m.getAttackType().getIdentifier().equals("fire") && (atkPokemon.getItem().equals("charcoal") || atkPokemon.getItem().equals("flame-plate"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (m.getAttackType().getIdentifier().equals("fighting") && (atkPokemon.getItem().equals("black-belt") || atkPokemon.getItem().equals("fist-plate"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (m.getAttackType().getIdentifier().equals("ice") && (atkPokemon.getItem().equals("never-melt-ice") || atkPokemon.getItem().equals("icicle-plate"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (m.getAttackType().getIdentifier().equals("dragon") && (atkPokemon.getItem().equals("dragon-fang") || atkPokemon.getItem().equals("draco-plate"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (m.getAttackType().getIdentifier().equals("ghost") && (atkPokemon.getItem().equals("spell-tag") || atkPokemon.getItem().equals("spooky-plate"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (m.getAttackType().getIdentifier().equals("bug") && (atkPokemon.getItem().equals("silver-powder") || atkPokemon.getItem().equals("insect-plate"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (m.getAttackType().getIdentifier().equals("grass") && (atkPokemon.getItem().equals("miracle-seed") || atkPokemon.getItem().equals("meadow-plate"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (m.getAttackType().getIdentifier().equals("steel") && (atkPokemon.getItem().equals("metal-coat") || atkPokemon.getItem().equals("iron-plate"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (m.getAttackType().getIdentifier().equals("psychic") && (atkPokemon.getItem().equals("twisted-spoon") || atkPokemon.getItem().equals("mind-plate"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (m.getAttackType().getIdentifier().equals("flying") && (atkPokemon.getItem().equals("sharp-beak") || atkPokemon.getItem().equals("sky-plate"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (m.getAttackType().getIdentifier().equals("water") && (atkPokemon.getItem().equals("mystic-water") || atkPokemon.getItem().equals("splash-plate"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (m.getAttackType().getIdentifier().equals("poison") && (atkPokemon.getItem().equals("poison-barb") || atkPokemon.getItem().equals("toxic-plate"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (m.getAttackType().getIdentifier().equals("rock") && (atkPokemon.getItem().equals("hard-stone") || atkPokemon.getItem().equals("stone-plate"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (m.getAttackType().getIdentifier().equals("electric") && (atkPokemon.getItem().equals("magnet") || atkPokemon.getItem().equals("zap-plate"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (m.getAttackType().getIdentifier().equals("fairy") && (atkPokemon.getItem().equals("pixie-plate"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (m.getAttackType().getIdentifier().equals("dark") && (atkPokemon.getItem().equals("black-glasses") || atkPokemon.getItem().equals("dread-plate"))){
				multiplier = (multiplier*4915/4096);
			}
			else if (atkPokemon.getIdentifier().equals("dialga") && (m.getAttackType().getIdentifier().equals("steel") && m.getAttackType().getIdentifier().equals("dragon")) && atkPokemon.getItem().equals("adamant-orb")) {
				multiplier = (multiplier*4915/4096);
			}
			else if (atkPokemon.getIdentifier().equals("palkia") && (m.getAttackType().getIdentifier().equals("water") && m.getAttackType().getIdentifier().equals("dragon")) && atkPokemon.getItem().equals("lustrous-orb")) {
				multiplier = (multiplier*4915/4096);
			}
			else if (atkPokemon.getIdentifier().contains("giratina") && (m.getAttackType().getIdentifier().equals("ghost") && m.getAttackType().getIdentifier().equals("dragon")) && atkPokemon.getItem().equals("griseous-orb")) {
				multiplier = (multiplier*4915/4096);
			}
			else if ((atkPokemon.getIdentifier().equals("latios") || atkPokemon.getIdentifier().equals("latias")) && (m.getAttackType().getIdentifier().equals("psychic") && m.getAttackType().getIdentifier().equals("dragon")) && atkPokemon.getItem().equals("soul-dew")) {
				multiplier = (multiplier*4915/4096);
			}
			
			//GEMS
			else if (condition.getGeneration() == 5) {
				if (m.getAttackType().getIdentifier().equals("dragon") && atkPokemon.getItem().equals("dragon-gem")){
					multiplier = (multiplier*6144/4096);
					condition.setItemConsumed(true);
				}
				else if (m.getAttackType().getIdentifier().equals("ground") && atkPokemon.getItem().equals("ground-gem")){
					multiplier = (multiplier*6144/4096);
					condition.setItemConsumed(true);
				}
				else if (m.getAttackType().getIdentifier().equals("fire") && atkPokemon.getItem().equals("fire-gem")){
					multiplier = (multiplier*6144/4096);
					condition.setItemConsumed(true);
				}
				else if (m.getAttackType().getIdentifier().equals("water") && atkPokemon.getItem().equals("water-gem")){
					multiplier = (multiplier*6144/4096);
					condition.setItemConsumed(true);
				}
				else if (m.getAttackType().getIdentifier().equals("electric") && atkPokemon.getItem().equals("electric-gem")){
					multiplier = (multiplier*6144/4096);
					condition.setItemConsumed(true);
				}
				else if (m.getAttackType().getIdentifier().equals("grass") && atkPokemon.getItem().equals("grass-gem")){
					multiplier = (multiplier*6144/4096);
					condition.setItemConsumed(true);
				}
				else if (m.getAttackType().getIdentifier().equals("ice") && atkPokemon.getItem().equals("ice-gem")){
					multiplier = (multiplier*6144/4096);
					condition.setItemConsumed(true);
				}
				else if (m.getAttackType().getIdentifier().equals("poison") && atkPokemon.getItem().equals("poison-gem")){
					multiplier = (multiplier*6144/4096);
					condition.setItemConsumed(true);
				}
				else if (m.getAttackType().getIdentifier().equals("normal") && atkPokemon.getItem().equals("normal-gem")){
					multiplier = (multiplier*6144/4096);
					condition.setItemConsumed(true);
				}
				else if (m.getAttackType().getIdentifier().equals("flying") && atkPokemon.getItem().equals("flying-gem")){
					multiplier = (multiplier*6144/4096);
					condition.setItemConsumed(true);
				}
				else if (m.getAttackType().getIdentifier().equals("psychic") && atkPokemon.getItem().equals("psychic-gem")){
					multiplier = (multiplier*6144/4096);
					condition.setItemConsumed(true);
				}
				else if (m.getAttackType().getIdentifier().equals("bug") && atkPokemon.getItem().equals("bug-gem")){
					multiplier = (multiplier*6144/4096);
					condition.setItemConsumed(true);
				}
				else if (m.getAttackType().getIdentifier().equals("rock") && atkPokemon.getItem().equals("rock-gem")){
					multiplier = (multiplier*6144/4096);
					condition.setItemConsumed(true);
				}
				else if (m.getAttackType().getIdentifier().equals("ghost") && atkPokemon.getItem().equals("ghost-gem")){
					multiplier = (multiplier*6144/4096);
					condition.setItemConsumed(true);
				}
				else if (m.getAttackType().getIdentifier().equals("dark") && atkPokemon.getItem().equals("dark-gem")){
					multiplier = (multiplier*6144/4096);
					condition.setItemConsumed(true);
				}
				else if (m.getAttackType().getIdentifier().equals("steel") && atkPokemon.getItem().equals("steel-gem")){
					multiplier = (multiplier*6144/4096);
					condition.setItemConsumed(true);
				}
			}
			else if (m.getAttackType().getIdentifier().equals("normal") && atkPokemon.getItem().equals("normal-gem")){
				multiplier = (multiplier*5325/4096);
				condition.setItemConsumed(true);
			}
		}
		//SOLAR BEAM/BLADE
		if ((m.getIdentifier().equals("solar-blade") || m.getIdentifier().equals("solar-beam")) && (condition.getWeather().equals("Rain") || condition.getWeather().equals("Sand") || condition.getWeather().equals("Hail") || condition.getWeather().equals("Heavy Rain"))){
			multiplier = (multiplier*2048/4096);
		}
		
		//KNOCK OFF
		if (m.getIdentifier().equals("knock-off") && (!condition.isItemConsumed() && defPokemon.getItem() != null && this.canKnockOffItem(defPokemon.getItem()))){
			if (!((defPokemon.getIdentifier().contains("arceus") || defPokemon.getIdentifier().contains("silvally")) && !defPokemon.isType("normal"))) {
				multiplier = (multiplier*6144/4096);
				condition.setItemConsumed(true);
			}
		}
		
		//HELPING HAND
		if (condition.isHelpingHand()) {
			multiplier = (multiplier*6144/4096);
		}

		//CHARGE
		if (condition.isCharge() && m.getAttackType().getIdentifier().equals("electric")) {
			multiplier = (multiplier*8192/4096);
		}
		
		
		//BRINE
		if (m.getIdentifier().equals("brine") && condition.getHealth() < 50) {
			multiplier = (multiplier*8192/4096);
		}
		
		//FACADE
		if (m.getIdentifier().equals("facade") && !atkPokemon.getStatus().equals("None")) {
			multiplier = (multiplier*8192/4096);
		}
		
		//VENOSHOCK
		if (m.getIdentifier().equals("venoshock") && (atkPokemon.getStatus().equals("Poison") || atkPokemon.getStatus().equals("Toxic"))) {
			multiplier = (multiplier*8192/4096);
		}
		
		//----------------------------------------------------------------------
		//TODO: FUSION BOLT/FLARE
		//----------------------------------------------------------------------

		
		//TERRAINS
		if (m.getIdentifier().equals("terrain-pulse")) {
			if (!condition.getTerrain().equals("None")) {
				multiplier = (multiplier*8192/4096);
				if (condition.getTerrain().equals("Grassy")) {
					m.setAttackType(this.getType("grass"));
				}
				else if (condition.getTerrain().equals("Misty")) {
					m.setAttackType(this.getType("fairy"));
				}
				else if (condition.getTerrain().equals("Electric")) {
					m.setAttackType(this.getType("electric"));
				}
				else if (condition.getTerrain().equals("psychic")) {
					m.setAttackType(this.getType("psychic"));
				}
			}
		}
		if (m.getAttackType().getIdentifier().equals("grass") && condition.getTerrain().equals("Grassy") && !this.isPokemonFlying(atkPokemon, condition)) {
			if (condition.getGeneration() < 8) {
				multiplier = (multiplier*6144/4096);
			}
			else {
				multiplier = (multiplier*5325/4096);
			}
		}
		else if (m.getAttackType().getIdentifier().equals("electric") && condition.getTerrain().equals("Electric") && !this.isPokemonFlying(atkPokemon, condition)) {
			if (condition.getGeneration() < 8) {
				multiplier = (multiplier*6144/4096);
			}
			else {
				multiplier = (multiplier*5325/4096);
			}
			if (m.getIdentifier().equals("rising-voltage")) {
				if (!this.isPokemonFlying(defPokemon, condition)){
					multiplier = (multiplier*8192/4096);
				}
			}
		}
		else if (m.getAttackType().getIdentifier().equals("psychic") && condition.getTerrain().equals("Psychic") && this.isPokemonFlying(atkPokemon, condition)) {
			if (condition.getGeneration() < 8) {
				multiplier = (multiplier*6144/4096);
			}
			else {
				multiplier = (multiplier*5325/4096);
			}
			if (m.getIdentifier().equals("expanding-force")) {
				multiplier = (multiplier*6144/4096);
			}
		}
		else if ((m.getIdentifier().equals("earthquake") || m.getIdentifier().equals("magnitude") || m.getIdentifier().equals("bulldoze") ) && condition.getTerrain().equals("Grassy")) {
			multiplier = (multiplier*2048/4096);
		}
		else if (condition.getTerrain().equals("Misty") && m.getAttackType().getIdentifier().equals("dragon") && this.isPokemonFlying(defPokemon, condition)){
			multiplier = (multiplier*2048/4096);
		}
		if (attackerDMax) {
			if (m.getAttackType().getIdentifier().equals("fairy")) {
				condition.setTerrain("Misty");
			}
			else if (m.getAttackType().getIdentifier().equals("grass")) {
				condition.setTerrain("Grassy");
			}
			if (m.getAttackType().getIdentifier().equals("electric")) {
				condition.setTerrain("Electric");
			}
			if (m.getAttackType().getIdentifier().equals("psychic")) {
				condition.setTerrain("Psychic");
			}
		}
		
		//-----------------------------------------------------------
		//------------------TODO: MUD/WATER SPORTS-------------------
		//-----------------------------------------------------------
		//System.out.println(multiplier+" BP: "+basePower);
		basePower = pokeRound(multiplier * basePower /  4096.0);
		//System.out.println(basePower);
		
		//------------------------------------------------------------------------------------
		//-----------------------------END BASE POWER------------------------------------------
		//------------------------------------------------------------------------------------

		
		//------------------------------------------------------------------------------------
		//-----------------------------BEGIN ATTACK STATS-------------------------------------
		//------------------------------------------------------------------------------------
	
		
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
	
		double atkBoost = 1.0;
		//UNAWARE, CRITS AND BOOSTS
		if (!defPokemon.getAbility().getIdentifier().equals("unaware") || (defPokemon.getAbility().getIdentifier().equals("unaware") && this.ignoresAbilities(atkPokemon))) {
			if (condition.isCritical()) {
				if (atkPokemon.equals(condition.getPokemon()) || (defPokemon.equals(condition.getPokemon()) && m.getIdentifier().equals("foul-play"))) {
					attack = (int) Math.floor(attack *Math.max(1, condition.getOppBoost()));
				}
				else {
					attack = (int) Math.floor(attack*Math.max(1, condition.getYourBoost()));
				}
			}
			else {
				if (atkPokemon.equals(condition.getPokemon()) || (defPokemon.equals(condition.getPokemon()) && m.getIdentifier().equals("foul-play"))) {
					attack = (int) Math.floor(attack * condition.getOppBoost());
				}
				else {
					attack = (int) Math.floor(attack* condition.getYourBoost());
				}
			}
		}
		
		//HANDLE DROPS HERE
		int change = 0;
		if (m.getEffectId() == 205) {
			change = -2;
		}
		else if (m.getEffectId() == 183) {
			change = -1;
		}
		
		if (atkPokemon.getAbility().getIdentifier().equals("contrary")) {
			change *= -1;
		}
		if (!(atkPokemon.getItem().equals("white-herb") && change < 0)) {
			if (atkPokemon.equals(condition.getPokemon())) {
				condition.changeOppBoost(change);
			}
			else {
				condition.changeYourBoost(change);
			}
		}

		
		//HUSTLE
		if (atkPokemon.getAbility().getIdentifier().equals("hustle")) {
			attack = pokeRound(attack*6144/4096.0);
		}
		
		double attackMultiplier = 4096.0;
		//SLOW START
		if (m.getDamageClass().getIdentifier().equals("physical") && atkPokemon.getAbility().getIdentifier().equals("hustle")) {
			attackMultiplier = (attackMultiplier*6144/4096);
		}
		
		//DEFEATIST
		if (m.getDamageClass().getIdentifier().equals("physical") && (atkPokemon.getAbility().getIdentifier().equals("slow-start") || (atkPokemon.getAbility().getIdentifier().equals("defeatist") && condition.getHealth() < 50))) {
			attackMultiplier = (attackMultiplier*2048/4096);
		}
		
		//FLOWER GIFT
		if (condition.isFlowerGift() && m.getDamageClass().getIdentifier().equals("physical")) {
			attackMultiplier = (attackMultiplier*6144/4096);
		}
		
		//GUTS
		if (atkPokemon.getAbility().getIdentifier().equals("guts") && m.getDamageClass().getIdentifier().equals("physical")) {
			attackMultiplier = (attackMultiplier*6144/4096);
		}
		
		//OVERGROW/BLAZE/TORRENT/SWARM
		if (condition.getHealth() < 33.33 && ((atkPokemon.getAbility().getIdentifier().equals("overgrow") && m.getAttackType().getIdentifier().equals("grass")) || (atkPokemon.getAbility().getIdentifier().equals("blaze") && m.getAttackType().getIdentifier().equals("fire")) || (atkPokemon.getAbility().getIdentifier().equals("torrent") && m.getAttackType().getIdentifier().equals("water")) || (atkPokemon.getAbility().getIdentifier().equals("swarm") && m.getAttackType().getIdentifier().equals("bug")))) {
			attackMultiplier = (attackMultiplier*6144/4096);
		}
		
		//FLASH-FIRE
		if (condition.isFlashFire() && m.getAttackType().getIdentifier().equals("fire")) {
			attackMultiplier = (attackMultiplier*6144/4096);
		}
		
		//SOLAR-POWER
		if (condition.getWeather().equals("Sun")  && atkPokemon.getAbility().getIdentifier().equals("solar-power") && m.getDamageClass().getIdentifier().equals("special")) {
			attackMultiplier = (attackMultiplier*6144/4096);
		}
		
		//----------------------------------------------------------------------
		//TODO: PLUS/MINUS
		//----------------------------------------------------------------------

		
		//STEELWORKER
		if (atkPokemon.getAbility().getIdentifier().equals("steelworker") && m.getAttackType().getIdentifier().equals("steel")) {
			attackMultiplier = (attackMultiplier*6144/4096);
		}
		
		//HUGE POWER/PURE POWER
		if ((atkPokemon.getAbility().getIdentifier().equals("huge-power") || atkPokemon.getAbility().getIdentifier().equals("pure-power"))  && m.getDamageClass().getIdentifier().equals("physical")) {
			attackMultiplier = (attackMultiplier*8192/4096);
		}
		
		//WATER BUBBLE
		if (atkPokemon.getAbility().getIdentifier().equals("water-bubble") && m.getAttackType().getIdentifier().equals("water")) {
			attackMultiplier = (attackMultiplier*8192/4096);
		}
		
		//----------------------------------------------------------------------
		//TODO: STAKEOUT
		//----------------------------------------------------------------------

		//THICK FAT
		if (defPokemon.getAbility().getIdentifier().equals("thick-fat") && (m.getAttackType().getIdentifier().equals("ice") || m.getAttackType().getIdentifier().equals("fire")) && !this.ignoresAbilities(atkPokemon)){
			attackMultiplier = (attackMultiplier*2048/4096);
		}
		
		//WATER BUBBLE
		if (defPokemon.getAbility().getIdentifier().equals("water-bubble") && m.getAttackType().getIdentifier().equals("fire") && !this.ignoresAbilities(atkPokemon)){
			attackMultiplier = (attackMultiplier*2048/4096);
		}
		
		//CHOICE BAND/SPECS
		if ((atkPokemon.getItem().equals("choice-specs") && m.getDamageClass().getIdentifier().equals("special")) || (atkPokemon.getItem().equals("choice-band") && m.getDamageClass().getIdentifier().equals("physical"))) {
			attackMultiplier = (attackMultiplier*6144/4096);
		}
		
		//ITEM-SPECIFIC
		if ((atkPokemon.getIdentifier().contains("pikachu") && atkPokemon.getItem().equals("light-ball"))||((atkPokemon.getIdentifier().contains("marowak")||atkPokemon.getIdentifier().contains("cubone")) && atkPokemon.getItem().equals("thick-club")) || (atkPokemon.getIdentifier().equals("clamperl") && atkPokemon.getItem().equals("deep-sea-tooth"))) {
			attackMultiplier = attackMultiplier*8192/4096;
		}
		
		attack = pokeRound(attack * attackMultiplier/ 4096.0);
		
		
		//-------------------------------------------------------------------
		//-------------------------END ATTACK MULTIPLIER---------------------
		//-------------------------------------------------------------------

		//-------------------------------------------------------------------
		//-------------------------START DEFENSE MULTIPLIER------------------
		//-------------------------------------------------------------------		
		

		int defenseMultiplier = 4096;
		
		//UNAWARE
		//UNAWARE, CRITS AND BOOSTS
		if (!(atkPokemon.getAbility().getIdentifier().equals("unaware") || condition.getMove().getIdentifier().equals("sacred-sword") || condition.getMove().getIdentifier().equals("chip-away"))) {
			if (condition.isCritical()) {
				if (defPokemon.equals(condition.getPokemon())) {
					defense= (int) Math.floor(defense *Math.min(1, condition.getOppBoost()));
				}
				else {
					defense = (int) Math.floor(defense*Math.min(1, condition.getYourBoost()));
				}
			}
			else {
				if (defPokemon.equals(condition.getPokemon())) {
					defense = (int) Math.floor(defense* condition.getOppBoost());
				}
				else {
					defense = (int) Math.floor(defense* condition.getYourBoost());
				}
			}
		}

		//SAND AND ROCK
		if (defPokemon.isType("rock") && condition.getWeather().equals("Sand")) {
			defense = pokeRound(defense * 6144/4096.0);
		}
		
		//FLOWER GIFT
		if (condition.isFlowerGift() && !isMovePhysical(condition.getMove())) {
			defenseMultiplier = defenseMultiplier * 6144/4096;
		}
		
		//GRASS PELT
		if (condition.getTerrain().equals("Grassy") && defPokemon.getAbility().getIdentifier().equals("grass-pelt") && isMovePhysical(condition.getMove()) && !this.ignoresAbilities(atkPokemon)) {
			defenseMultiplier = defenseMultiplier * 6144/4096;
		}
		
		//MARVEL SCALE
		if (defPokemon.getAbility().getIdentifier().equals("marvel-scale") && isMovePhysical(condition.getMove()) && (!defPokemon.getStatus().equals("Healthy")) && !this.ignoresAbilities(atkPokemon)) {
			defenseMultiplier = defenseMultiplier * 6144/4096;
		}
		
		//FUR COAT
		if (defPokemon.getAbility().getIdentifier().equals("fur-coat") && isMovePhysical(condition.getMove()) && !this.ignoresAbilities(atkPokemon)) {
			defenseMultiplier = defenseMultiplier * 8192/4096;
		}
		
		//---------------NEEDS WORK----------------------------
		//EVIOLITE 
		if (defPokemon.getItem().equals("eviolite")) {
			defenseMultiplier = defenseMultiplier * 6144/4096;
		}
		
		
		//ASSAULT VEST
		if (defPokemon.getItem().equals("assault-vest") && !isMovePhysical(condition.getMove())) {
			defenseMultiplier = defenseMultiplier * 6144/4096;
		}
		
		
		//DEEP SEA SCALE
		if (defPokemon.getItem().equals("deep-sea-scale") && !isMovePhysical(condition.getMove()) && defPokemon.getIdentifier().equals("clamperl")) {
			defenseMultiplier = defenseMultiplier * 8192/4096;
		}
		
		
		//METAL POWDER
		if (defPokemon.getItem().equals("metal-powder") && isMovePhysical(condition.getMove()) && defPokemon.getIdentifier().equals("ditto")) {
			defenseMultiplier = defenseMultiplier * 8192/4096;
		}
		
		defense = (int) pokeRound(defense * defenseMultiplier/4096.0);
		
		//System.out.println("Defense/SpD: "+defense+" Multiplier: "+defenseMultiplier);
		//-------------------------------------------------------------------
		//-------------------------END DEFENSE MULTIPLIER--------------------
		//-------------------------------------------------------------------
		
		if (attackerDMax && m.getAttackType().getIdentifier().equals("rock")) {
			condition.setWeather("Sand");
		}
		
		int level1 = (int) Math.floor(2*atkPokemon.getLevel()/5+2);
		int level2 = (int) Math.floor(level1*basePower*attack/defense);
		System.out.println(level1+" BP: "+basePower+" DEFMULT: "+defenseMultiplier+" Level 2: "+level2 + " Def: "+defense + "Atk: "+attack);

		int level3 = (int) Math.floor(level2/50);
		//System.out.println(level3+2);

		return level3+2;
	}
	
	
	public int checkBerryUsage(Pokemon defPokemon, Condition c, int health) {
		if (!c.isItemConsumed() && health > 0) {
			if (defPokemon.getItem().equals("sitrus-berry")) {
				if (health/defPokemon.getHP() <= 0.5) {
					if (defPokemon.getAbility().getIdentifier().equals("ripen")) {
						health += Math.max(1, Math.floor(defPokemon.getHP()/2));
					}
					else {
						health += Math.max(1, Math.floor(defPokemon.getHP()/4));
						if (defPokemon.getAbility().getIdentifier().equals("cheek-pouch")) {
							health += Math.max(1, Math.floor(defPokemon.getHP()/3));
						}
					}
					c.setItemConsumed(true);
				}
			}
			else if (defPokemon.getItem().equals("wiki-berry") || defPokemon.getItem().equals("aguav-berry") || defPokemon.getItem().equals("iapapa-berry") || defPokemon.getItem().equals("mago-berry") || defPokemon.getItem().equals("figy-berry")) {
				if (c.getGeneration() == 8) {
					if (health/defPokemon.getHP() <= 0.25 || (health/defPokemon.getHP() <= 0.5 && defPokemon.getAbility().getIdentifier().equals("gluttony"))) {
						health += Math.max(1, Math.floor(defPokemon.getHP()/3));
						if (defPokemon.getAbility().getIdentifier().equals("cheek-pouch")) {
							health += Math.max(1, Math.floor(defPokemon.getHP()/3));
						}
						else if (defPokemon.getAbility().getIdentifier().equals("ripen")) {
							health += Math.max(1, Math.floor(defPokemon.getHP()/3*2));
						}
						c.setItemConsumed(true);
					}
				}
				else if (c.getGeneration() == 7) {
					if (health/defPokemon.getHP() <= 0.25 || (health/defPokemon.getHP() <= 0.5 && defPokemon.getAbility().getIdentifier().equals("gluttony"))) {
						health += Math.max(1, Math.floor(defPokemon.getHP()/2));
						if (defPokemon.getAbility().getIdentifier().equals("cheek-pouch")) {
							health += Math.max(1, Math.floor(defPokemon.getHP()/3));
						}
						c.setItemConsumed(true);
					}
				}
				else if (c.getGeneration() <= 6) {
					if (health/defPokemon.getHP() <= 0.5) {
						health += Math.max(1, Math.floor(defPokemon.getHP()/8));
						if (defPokemon.getAbility().getIdentifier().equals("cheek-pouch")) {
							health += Math.max(1, Math.floor(defPokemon.getHP()/3));
						}
						c.setItemConsumed(true);
					}
				}
			}
		}
		return health;
	}
	
	public String getMaxName(Type t) {
		String[] typeName= {"normal", "psychic", "ghost", "dark", "fairy", "steel", "fire", "water", "grass", "flying", "electric", "ground", "ice", "rock", "fighting", "bug", "poison", "dragon"};
		String[] maxName = {"max-strike", "max-mindstorm", "max-phantasm", "max-darkness", "max-starfall", "max-steelspike", "max-flare", 
				"max-geyser", "max-overgrowth", "max-airstream", "max-lightning", "max-quake", "max-hailstorm", "max-rockfall", "max-knuckle", "max-ooze", "max-wyrmwind"};
		for (int i = 0; i < typeName.length; i++) {
			if (typeName[i].equals(t.getIdentifier())) {
				return maxName[i];
			}
		}
		return "";
	}
	
	public String getZName(Type t) {
		String[] typeName= {"normal", "psychic", "ghost", "dark", "fairy", "steel", "fire", "water", "grass", "flying", "electric", "ground", "ice", "rock", "fighting", "bug", "poison", "dragon"};
		String[] zName = {"breakneck-blitz", "shattered-psyche", "never-ending-nightmare", "black-hole-eclipse", "twinkle-tackle", "corkscrew-crash", "inferno-overdrive", 
				"hydro-vortex", "bloom-doom", "supersonic-skystrike", "gigavolt-havoc", "tectonic-rage", "subzero-slammer", "continental-crush", "all-out-pummeling", "acid-downpour", "devastating-drake"};
		for (int i = 0; i < typeName.length; i++) {
			if (typeName[i].equals(t.getIdentifier())) {
				return zName[i];
			}
		}
		return "";
	}
	
	public int calculateDamage(Condition condition, Pokemon pokemon) {
		Move m = condition.getMove();
		if (m.getDamageClass().getIdentifier().equals("status")) {
			return 0;
		}
		Pokemon atkPokemon;
		Pokemon defPokemon;
		if (condition.getCond().equals("Survive") || condition.getCond().equals("Survive 2") ) {
			defPokemon = pokemon;
			atkPokemon = condition.getPokemon();
		}
		else {
			defPokemon = condition.getPokemon();
			atkPokemon = pokemon;
		}
		boolean attackerDMax = ((condition.isOppDmax() && condition.getPokemon().equals(atkPokemon)) || (condition.isSelfDmax() && condition.getPokemon().equals(defPokemon)));
		boolean defenderDMax = ((condition.isSelfDmax() && condition.getPokemon().equals(atkPokemon)) || (condition.isOppDmax() && condition.getPokemon().equals(defPokemon)));


		int damage = calculateBaseDamage(condition, atkPokemon, defPokemon);
		System.out.println(damage);
		
		//General modifiers
		
		if (condition.isDoubles() && (!(m.getTarget().getIdentifier().equals("selected-pokemon")) || (m.getIdentifier().equals("expanding-force") && condition.getTerrain().equals("Psychic")) )) {
			damage = pokeRound(damage*3072.0/4096);
		}
		
		//----------------------------------------------------------------------
		//TODO: parental bond
		//----------------------------------------------------------------------

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

		
		//BATTLE ARMOR/SHELL ARMOR
		if (condition.isCritical() && !(defPokemon.getAbility().getIdentifier().equals("battle-armor") || defPokemon.getAbility().getIdentifier().equals("shell-armor")) && !this.ignoresAbilities(atkPokemon)){
			if (condition.getGeneration() >= 6) {
				damage = pokeRound(damage*6144.0/4096);
			}
			else {
				damage = pokeRound(damage*8192.0/4096);
			}
		}
		
		//RANDOM FACTOR.
		if (condition.getCond().equals("Knock Out") || condition.getCond().equals("2HKO")) {
			damage = (int) Math.floor(damage*0.85);
		}
		
		
		//STAB
		if (atkPokemon.isType(m.getAttackType().getIdentifier())) {
			if (atkPokemon.getAbility().getIdentifier().equals("adaptability")) {
				damage = pokeRound(damage * 8192 / 4096.0);
			}
			else {
				damage = pokeRound(damage*6144/4096.0);
			}
		}
		
		double modifier = defPokemon.getEffectivenessFrom(m.getAttackType());
		
		if (condition.getGeneration() <= 5 && (defPokemon.isType("steel") && (m.getAttackType().getIdentifier().equals("ghost") || m.getAttackType().getIdentifier().equals("dark")))) {
			modifier /= 2.0;
		}
		
		//FREEZE DRY
		if (m.getIdentifier().equals("freeze-dry") && defPokemon.isType("water")) {
			modifier = modifier * 4;
		}
		
		
		//FLYING TYPES, SMACK DOWN/GRAVITY/LEVITATE
		if ((condition.isGravity() || condition.isSmackDown()) && (defPokemon.isType("flying") || (defPokemon.getAbility().getIdentifier().equals("levitate") && !this.ignoresAbilities(atkPokemon)))&& m.getAttackType().getIdentifier().equals("ground")) {
			modifier = 1;
			for (Type t: defPokemon.getTypes()) {
				if (!t.isTypeName("flying")) {
					modifier *= t.getEffectivenessFrom(m.getAttackType())/100.0;
				}
			}
		}
		
		//THOUSAND ARROWS
		else if ((m.getIdentifier().equals("thousand-arrows") || defPokemon.getItem().equals("iron-ball")) && defPokemon.isType("flying")) {
			modifier = 1.0;
			if (m.getIdentifier().equals("thousand-arrows")) {

				condition.setSmackDown(true);
			}
		}
		
		//LEVITATE
		else if (defPokemon.getAbility().getIdentifier().equals("levitate") && m.getAttackType().getIdentifier().equals("ground") && !m.getIdentifier().equals("thousand-arrows") && !this.ignoresAbilities(atkPokemon)) {
			modifier = 0;
		}
		
		
		
		if (modifier > 1.1) {
			damage = damage << 1;
			if (modifier > 3) {
				damage = damage << 1;
			}
		}
		else if (modifier < 0.9) {
			damage = damage >> 1;
			if (modifier < 0.4) {
				damage = damage >> 1;
			}
		}
		
		//----------------------------------------------------------------------
		//TODO: Other interactions with typing.
		//----------------------------------------------------------------------

		
		
		double finalModifier = 4096;
		
		if (condition.isScreens()) {
			if(condition.isDoubles()) {
				finalModifier = finalModifier*2732/4096;
			}
			else {
				finalModifier = finalModifier*2048/4096;
			}
		}
		
		//NEUROFORCE
		if(atkPokemon.getAbility().getIdentifier().equals("neuroforce") && modifier > 1.1) {
			finalModifier = finalModifier*5120/4096;
		}
		
		//SNIPER
		if(atkPokemon.getAbility().getIdentifier().equals("sniper") && condition.isCritical()) {
			finalModifier = finalModifier*6144/4096;

		}
		
		//TINTED LENS
		if(atkPokemon.getAbility().getIdentifier().equals("tinted-lens") && modifier < 0.9) {
			finalModifier = finalModifier*8192/4096;
		}
		
		//MULTISCALE
		if ((defPokemon.getAbility().getIdentifier().equals("multiscale") || defPokemon.getAbility().getIdentifier().equals("shadow-shield")) && (int) (condition.getHealth())==100 && !this.ignoresAbilities(atkPokemon)) {
			finalModifier = finalModifier * 2048/4096;
		}
		
		if (atkPokemon.getStatus().equals("Burn") && m.getDamageClass().getIdentifier().equals("physical") && !m.getIdentifier().equals("facade")) {
			if (!atkPokemon.getAbility().getIdentifier().equals("guts")){
				finalModifier = finalModifier * 2048/4096;
			}
		}
		
		//FLUFFY (CONTACT)
		if (this.affectedByToughClaws(m) && defPokemon.getAbility().getIdentifier().equals("fluffy") && !this.ignoresAbilities(atkPokemon)) {
			finalModifier = finalModifier * 2048/4096;
		}

		
		//FRIEND GUARD
		if (condition.isFriendGuard()) {
			finalModifier = finalModifier*3072/4096;
		}
		
		
		//SOLID ROCK
		if((defPokemon.getAbility().getIdentifier().equals("solid-rock") || defPokemon.getAbility().getIdentifier().equals("filter") || defPokemon.getAbility().getIdentifier().equals("prism-armor")) && modifier > 1.1 && !this.ignoresAbilities(atkPokemon)) {
			finalModifier = finalModifier * 3072/4096;
		}
		
		//TODO: Metronome item
		
		//FLUFFY FIRE
		if (defPokemon.getAbility().getIdentifier().equals("fluffy") && m.getAttackType().getIdentifier().equals("fire") && !this.ignoresAbilities(atkPokemon)) {
			finalModifier = finalModifier * 8192/4096;
		}
		
		
		//EXPERT BELT
		if(atkPokemon.getItem().equals("expert-belt") && modifier > 1.1) {
			finalModifier = finalModifier * 4915 / 4096;
		}
		
		
		//LIFE ORB
		if (atkPokemon.getItem().equals("life-orb")) {
			finalModifier = finalModifier * 5324/4096;
		}
		
		
		
		//resist berries
		if (m.getAttackType().getIdentifier().equals("fighting") && defPokemon.getEffectivenessFrom(m.getAttackType()) > 1.1 && defPokemon.getItem().equals("chople-berry")) {
			finalModifier = finalModifier * 2048/4096;
			condition.setItemConsumed(true);
		}
		if (m.getAttackType().getIdentifier().equals("ice") && defPokemon.getEffectivenessFrom(m.getAttackType()) > 1.1 && defPokemon.getItem().equals("yache-berry")) {
			finalModifier = finalModifier * 2048/4096;
			condition.setItemConsumed(true);
		}
		if (m.getAttackType().getIdentifier().equals("fire") && defPokemon.getEffectivenessFrom(m.getAttackType()) > 1.1 && defPokemon.getItem().equals("occa-berry")) {
			finalModifier = finalModifier * 2048/4096;
			condition.setItemConsumed(true);
		}
		if (m.getAttackType().getIdentifier().equals("grass") && defPokemon.getEffectivenessFrom(m.getAttackType()) > 1.1 && defPokemon.getItem().equals("rindo-berry")) {
			finalModifier = finalModifier * 2048/4096;
			condition.setItemConsumed(true);
		}
		if (m.getAttackType().getIdentifier().equals("electric") && defPokemon.getEffectivenessFrom(m.getAttackType()) > 1.1 && defPokemon.getItem().equals("wacan-berry")) {
			finalModifier = finalModifier * 2048/4096;
			condition.setItemConsumed(true);
		}
		if (m.getAttackType().getIdentifier().equals("water") && defPokemon.getEffectivenessFrom(m.getAttackType()) > 1.1 && defPokemon.getItem().equals("passho-berry")) {
			finalModifier = finalModifier * 2048/4096;
			condition.setItemConsumed(true);
		}
		if (m.getAttackType().getIdentifier().equals("steel") && defPokemon.getEffectivenessFrom(m.getAttackType()) > 1.1 && defPokemon.getItem().equals("babiri-berry")) {
			finalModifier = finalModifier * 2048/4096;
			condition.setItemConsumed(true);
		}
		if (m.getAttackType().getIdentifier().equals("poison") && defPokemon.getEffectivenessFrom(m.getAttackType()) > 1.1 && defPokemon.getItem().equals("kebia-berry")) {
			finalModifier = finalModifier * 2048/4096;
			condition.setItemConsumed(true);
		}
		if (m.getAttackType().getIdentifier().equals("ground") && defPokemon.getEffectivenessFrom(m.getAttackType()) > 1.1 && defPokemon.getItem().equals("shuca-berry")) {
			finalModifier = finalModifier * 2048/4096;
			condition.setItemConsumed(true);
		}
		if (m.getAttackType().getIdentifier().equals("flying") && defPokemon.getEffectivenessFrom(m.getAttackType()) > 1.1 && defPokemon.getItem().equals("coba-berry")) {
			finalModifier = finalModifier * 2048/4096;
			condition.setItemConsumed(true);
		}
		if (m.getAttackType().getIdentifier().equals("psychic") && defPokemon.getEffectivenessFrom(m.getAttackType()) > 1.1 && defPokemon.getItem().equals("payapa-berry")) {
			finalModifier = finalModifier * 2048/4096;
			condition.setItemConsumed(true);
		}
		if (m.getAttackType().getIdentifier().equals("bug") && defPokemon.getEffectivenessFrom(m.getAttackType()) > 1.1 && defPokemon.getItem().equals("tanga-berry")) {
			finalModifier = finalModifier * 2048/4096;
			condition.setItemConsumed(true);
		}
		if (m.getAttackType().getIdentifier().equals("rock") && defPokemon.getEffectivenessFrom(m.getAttackType()) > 1.1 && defPokemon.getItem().equals("charti-berry")) {
			finalModifier = finalModifier * 2048/4096;
			condition.setItemConsumed(true);
		}
		if (m.getAttackType().getIdentifier().equals("ghost") && defPokemon.getEffectivenessFrom(m.getAttackType()) > 1.1 && defPokemon.getItem().equals("kasib-berry")) {
			finalModifier = finalModifier * 2048/4096;
			condition.setItemConsumed(true);
		}
		if (m.getAttackType().getIdentifier().equals("dragon") && defPokemon.getEffectivenessFrom(m.getAttackType()) > 1.1 && defPokemon.getItem().equals("haban-berry")) {
			finalModifier = finalModifier * 2048/4096;
			condition.setItemConsumed(true);
		}
		if (m.getAttackType().getIdentifier().equals("dark") && defPokemon.getEffectivenessFrom(m.getAttackType()) > 1.1 && defPokemon.getItem().equals("colbur-berry")) {
			finalModifier = finalModifier * 2048/4096;
			condition.setItemConsumed(true);
		}
		if (m.getAttackType().getIdentifier().equals("normal") && defPokemon.getEffectivenessFrom(m.getAttackType()) > 1.1 && defPokemon.getItem().equals("chilan-berry")) {
			finalModifier = finalModifier * 2048/4096;
			condition.setItemConsumed(true);
		}
		
		//----------------------------------------------------------------------
		//TODO: double-damage situations, like Dig, Minimize and Dive
		//----------------------------------------------------------------------
		
		if (attackerDMax && condition.getMove().getAttackType().getIdentifier().equals("fire")) {
			condition.setWeather("Sun");
		}
		else if (attackerDMax && condition.getMove().getAttackType().getIdentifier().equals("water")) {
			condition.setWeather("Rain");
		}
		else if (attackerDMax && condition.getMove().getAttackType().getIdentifier().equals("ice")) {
			condition.setWeather("Hail");
		}
		else if (attackerDMax && condition.getMove().getAttackType().getIdentifier().equals("ghost")  && condition.getMove().getDamageClass().getIdentifier().equals("physical")) {
			if (condition.getPokemon().equals(atkPokemon)) {
				condition.changeYourBoost(-1);
			}
			else {
				condition.changeOppBoost(-1);
			}
		}
		else if (attackerDMax && condition.getMove().getAttackType().getIdentifier().equals("dark")  && condition.getMove().getDamageClass().getIdentifier().equals("special")) {
			if (condition.getPokemon().equals(atkPokemon)) {
				condition.changeYourBoost(-1);
			}
			else {
				condition.changeOppBoost(-1);
			}
		}
		else if (attackerDMax && condition.getMove().getAttackType().getIdentifier().equals("poison")  && condition.getMove().getDamageClass().getIdentifier().equals("special")) 
		{
			if (condition.getPokemon().equals(atkPokemon)) {
				condition.changeOppBoost(1);
			}
			else {
				condition.changeYourBoost(1);
			}	
		}
		else if (attackerDMax && condition.getMove().getAttackType().getIdentifier().equals("fighting")  && condition.getMove().getDamageClass().getIdentifier().equals("physical")) 
		{
			if (condition.getPokemon().equals(atkPokemon)) {
				condition.changeOppBoost(1);
			}
			else {
				condition.changeYourBoost(1);
			}	
		}
		return (int) (damage * finalModifier / 4096.0);
	}
		
//	public int finalDamage(Condition condition, int baseDamage) {
//		
//	}

	
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
	
	
	public int calculateSRDamage(Pokemon defPokemon) {
		Type rock = this.getType("rock");
		double modifier = defPokemon.getEffectivenessFrom(rock);
		modifier /= 8.0;
		return (int) Math.floor(modifier*defPokemon.getHP());
		
	}
	
	public boolean isMoveMultihit(Move m) {
		String[] moves = {"arm-thrust", "bone-rush", "bonemerang", "bullet-seed", "double-hit", "double-iron-bash", "double-kick", "dragon-darts", "dual-chop", "dual-wingbeat", 
		                           	"fury-attack", "fury-swipes", "gear-grind", "icicle-spear", "pin-missile", "rock-blast", "scale-shot", "surging-strikes", "tail-slap", "triple-axel", "triple-kick", "water-shuriken"};
		for (int i =0; i < moves.length; i++) {
			if (m.getIdentifier().equals(moves[i])){
				return true;
			}
		}
		return false;
	}
		
	public int calculateSpeed(Pokemon pokemon, double boost, String weather, String terrain, boolean tailwind) {
		
		//TODO: boosts 
		
		int baseSpeed = pokemon.getSpe();

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
		return (int) Math.floor(baseSpeed * boost);
	}
	
	public int healAfterTurn(Pokemon defPokemon, Condition condition, int currentHp) {
		if (!defPokemon.getItem().equals("safety-goggles") || !(defPokemon.getAbility().getIdentifier().equals("overcoat"))) {
			if (condition.getWeather().equals("Sand") && !(defPokemon.getAbility().getIdentifier().equals("sand-rush") || defPokemon.getAbility().getIdentifier().equals("sand-force") || defPokemon.getAbility().getIdentifier().equals("sand-veil"))) {
				if (!(defPokemon.isType("rock") || defPokemon.isType("steel") || defPokemon.isType("ground"))) {
					currentHp -= Math.max(1, Math.floor(defPokemon.getHP()/16));
				}
			}
			else if (condition.getWeather().equals("Hail") && !(defPokemon.getAbility().getIdentifier().equals("slush-rush") || defPokemon.getAbility().getIdentifier().equals("snow-cloak"))) {
				if (!(defPokemon.isType("ice"))) {
					currentHp -= Math.max(1, Math.floor(defPokemon.getHP()/16));
				}
			}
		}
		if (defPokemon.getStatus().equals("Burn")) {
			if (condition.getGeneration() < 7) {
				if (defPokemon.getAbility().getIdentifier().equals("heatproof")) {
					currentHp -= Math.max(1, Math.floor(defPokemon.getHP()/16));
				}
				else {
					currentHp -= Math.max(1, Math.floor(defPokemon.getHP()/8));
				}
			}
			else {
				if (defPokemon.getAbility().getIdentifier().equals("heatproof")) {
					currentHp -= Math.max(1, Math.floor(defPokemon.getHP()/32));
				}
				else {
					currentHp -= Math.max(1, Math.floor(defPokemon.getHP()/16));
				}
			}
		}
		else if (defPokemon.getStatus().equals("Poison")) {
			if (defPokemon.getAbility().getIdentifier().equals("poison-heal")) {
				currentHp += Math.max(1, Math.floor(defPokemon.getHP()/8));
			}
			else {
				currentHp -= Math.max(1, Math.floor(defPokemon.getHP()/8));
			}
		}
		
		else if ((defPokemon.getAbility().getIdentifier().equals("solar-power") || defPokemon.getAbility().getIdentifier().equals("dry-skin")) && condition.getWeather().equals("Sun")) {
			currentHp -= Math.max(1, Math.floor(defPokemon.getHP()/8));
		}
		
		else if (defPokemon.getAbility().getIdentifier().equals("dry-skin") && condition.getWeather().equals("Rain")) {
			currentHp += Math.max(1, Math.floor(defPokemon.getHP()/8));
		}
		
		else if (defPokemon.getAbility().getIdentifier().equals("rain-dish") && condition.getWeather().equals("Rain")) {
			currentHp += Math.max(1, Math.floor(defPokemon.getHP()/16));
		}
		
		if (currentHp > 0) {
		
			if (condition.getTerrain().equals("Grassy")) {
				currentHp += Math.max(1, Math.floor(defPokemon.getHP()/16));
			}
			if (defPokemon.getItem().equals("leftovers")) {
				currentHp += Math.max(1, Math.floor(defPokemon.getHP()/16));
			}
			else if (defPokemon.getItem().equals("black-sludge")) {
				if (defPokemon.isType("poison")) {
					currentHp += Math.max(1, Math.floor(defPokemon.getHP()/16));
				}
				else {
					currentHp -= Math.max(1, Math.floor(defPokemon.getHP()/16));
				}
			}
			else if (defPokemon.getItem().equals("sticky-barb")) {
				currentHp -= Math.max(1, Math.floor(defPokemon.getHP()/16));
			}
			currentHp = this.checkBerryUsage(defPokemon, condition, currentHp);
		}
		return currentHp;
	}
		
	public int[] calcAllEVs(ArrayList<HashMap<String, Object>> atks, ArrayList<HashMap<String, Object>> defense, ArrayList<HashMap<String, Object>> spas, ArrayList<HashMap<String, Object>> spds, ArrayList<HashMap<String, Object>> speed, Nature orig, Nature suggested, boolean isMaxHP) {
		int total = 0;
		int[] results = new int[7];
		if (atks.size() > 0) {
			HashMap<String, Object> atkMap = (HashMap<String, Object>)(atks.get(0).get("with-orig-nature"));
			if (atkMap == null) {
				ArrayList<HashMap<String, Object>> atkList = (ArrayList<HashMap<String, Object>>) atks.get(0).get("allconds");
				atkMap = atkList.get(1);
				if (atkMap == null) {
					atkMap = atkList.get(0);
				}
			}
			int atk = (Integer) atkMap.get("attack");
			total+=atk;
			results[1] = atk;
		}
		else {
			results[1] = 0;
		}
		if (spas.size() > 0) {
			HashMap<String, Object> spaMap = (HashMap<String, Object>)(spas.get(0).get("with-orig-nature"));
			if (spaMap == null) {
				ArrayList<HashMap<String, Object>> spaList = (ArrayList<HashMap<String, Object>>) spas.get(0).get("allconds");
				spaMap = spaList.get(1);
				if (spaMap == null) {
					spaMap = spaList.get(0);
				}
			}
			int spa = (Integer) spaMap.get("special-attack");
			total+=spa;
			results[3] = spa;
		}
		else {
			results[3] = 0;
		}
		
		if (speed.size() > 0) {
			HashMap<String, Object> speedMap = (HashMap<String, Object>)(speed.get(0).get("with-orig-nature"));
			if (speedMap == null) {
				ArrayList<HashMap<String, Object>> speedList = (ArrayList<HashMap<String, Object>>) speed.get(0).get("allconds");
				speedMap = speedList.get(1);
				if (speedMap == null) {
					speedMap = speedList.get(0);
				}
			}			
			
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
			if (spdStack == null) {
				ArrayList<ArrayList<HashMap<String, Object>>> spdList = (ArrayList<ArrayList<HashMap<String, Object>>>) spds.get(0).get("allconds");
				spdStack = spdList.get(1);
				if (spdStack == null) {
					spdStack = spdList.get(0);
				}
			}
			if (defStack == null) {
				ArrayList<ArrayList<HashMap<String, Object>>> defList = (ArrayList<ArrayList<HashMap<String, Object>>>) defense.get(0).get("allconds");
				defStack = defList.get(1);
				if (defStack == null) {
					defStack = defList.get(0);
				}
			}			
			int spdHp=(Integer) spdStack.get(0).get("hp");
			int defHp=(Integer) defStack.get(0).get("hp");
			int currentHp = Math.max(spdHp, defHp);
			int currentDef = (Integer) defStack.get(0).get("defense");
			int currentSpD = (Integer) spdStack.get(0).get("special-defense");
			int totalStat = currentHp+currentDef+currentSpD;
			System.out.println(currentHp+" "+ currentSpD +" "+ currentDef);
			for (int i = 0; i < spdStack.size(); i++) {
				for (int j = 0; j < defStack.size(); j++) {
					int testSpd = (Integer) spdStack.get(i).get("special-defense");
					int testHp = Math.max((Integer) spdStack.get(i).get("hp"), (Integer) defStack.get(j).get("hp"));
					int testDef = (Integer) defStack.get(j).get("defense");
					System.out.println("Test: "+ testHp+" "+ testSpd +" "+ testDef);

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
			if (spdStack == null) {
				ArrayList<ArrayList<HashMap<String, Object>>> spdList = (ArrayList<ArrayList<HashMap<String, Object>>>) spds.get(0).get("allconds");
				spdStack = spdList.get(1);
				if (spdStack == null) {
					spdStack = spdList.get(0);
					
				}
			}
			
			int currentHp = (Integer) spdStack.get(0).get("hp");
			int currentSpD = (Integer) spdStack.get(0).get("special-defense");
			int totalStat = currentHp+currentSpD;

			for (int i = 0; i < spdStack.size(); i++) {
				int testSpd = (Integer) spdStack.get(i).get("special-defense");
				int testHp = (Integer) spdStack.get(i).get("hp");
				if (!isMaxHP) {
					if (totalStat > testHp+testSpd || (totalStat == testHp+testSpd && currentHp < testHp)) {
						currentSpD = testSpd;
						currentHp = testHp;
						totalStat = testHp+testSpd;
					}
				}
				else {
					if (testSpd != currentSpD) {
						break;
					}
					else {
						currentSpD = testSpd;
						currentHp = testHp;
					}
				}

			}
			results[0] = currentHp;
			results[2] = 0;
			results[4] = currentSpD;
			total+= totalStat;

		}
		else if (defense.size() > 0){
			ArrayList<HashMap<String, Object>> defStack = (ArrayList<HashMap<String, Object>>) defense.get(0).get("with-orig-nature");
			if (defStack == null) {
				ArrayList<ArrayList<HashMap<String, Object>>> defList = (ArrayList<ArrayList<HashMap<String, Object>>>) defense.get(0).get("allconds");
				defStack = defList.get(1);
				if (defStack == null) {
					defStack = defList.get(0);
				}
			}
			int currentHp = (Integer) defStack.get(0).get("hp");
			int currentDef = (Integer) defStack.get(0).get("defense");
			int totalStat = currentHp+currentDef;

			for (int i = 0; i < defStack.size(); i++) {
				int testDef = (Integer) defStack.get(i).get("defense");
				int testHp = (Integer) defStack.get(i).get("hp");
				if (!isMaxHP) {
					if (totalStat > testHp+testDef || (totalStat == testHp+testDef && currentHp < testHp)) {
						currentDef = testDef;
						currentHp = testHp;
						totalStat = testHp+testDef;
					}
				}
				else {
					if (testDef != currentDef) {
						break;
					}
					else {
						currentDef = testDef;
						currentHp = testHp;
					}
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
	
	
	public String summarizeCondition(Pokemon dPoke, Condition c) {
		String summary = c.getCond()+" ";
		if (!c.getRawOppBoost().equals("0")) {
			summary += c.getRawOppBoost()+" ";

		}
		if (c.getCond().equals("Knock Out") || c.getCond().equals("2HKO")) {
			summary += c.getPokemon().getStats().get(0).getEffort() + " HP ";
			if (this.isMovePhysical(c.getMove())) {
				summary += c.getPokemon().getStats().get(2).getEffort();
				if (!(c.getPokemon().getNature().getIncreasedStat().equals(c.getPokemon().getNature().getDecreasedStat())) && c.getPokemon().getNature().getIncreasedStat().getLabel().getIdentifier().equals("defense")) {
					summary+="+ ";
				}
				else if (!(c.getPokemon().getNature().getIncreasedStat().equals(c.getPokemon().getNature().getDecreasedStat())) && c.getPokemon().getNature().getDecreasedStat().getLabel().getIdentifier().equals("defense")) {
					summary+="- ";
				}
				summary+= " Def ";
			}
			else {
				summary += c.getPokemon().getStats().get(4).getEffort();
				if (!(c.getPokemon().getNature().getIncreasedStat().equals(c.getPokemon().getNature().getDecreasedStat())) && c.getPokemon().getNature().getIncreasedStat().getLabel().getIdentifier().equals("special-defense")) {
					summary+="+ ";
				}
				else if (!(c.getPokemon().getNature().getIncreasedStat().equals(c.getPokemon().getNature().getDecreasedStat())) && c.getPokemon().getNature().getDecreasedStat().getLabel().getIdentifier().equals("special-defense")) {
					summary+="- ";
				}
				summary+= " SpD ";
			}
		}
		else if (c.getCond().equals("Outspeed")) {
			summary += c.getPokemon().getStats().get(5).getEffort();
			if (!(c.getPokemon().getNature().getIncreasedStat().equals(c.getPokemon().getNature().getDecreasedStat())) && c.getPokemon().getNature().getIncreasedStat().getLabel().getIdentifier().equals("speed")) {
				summary +="+ ";
			}
			else if (!(c.getPokemon().getNature().getIncreasedStat().equals(c.getPokemon().getNature().getDecreasedStat())) && c.getPokemon().getNature().getDecreasedStat().getLabel().getIdentifier().equals("speed")) {
				summary +="- ";
			}
			summary += " Speed ";
		}
		else if (c.getCond().equals("Survive") || c.getCond().equals("Survive 2")) {
			if (c.getMove().getDamageClass().getIdentifier().equals("physical")) {
				summary += c.getPokemon().getStats().get(1).getEffort();
				if (!(c.getPokemon().getNature().getIncreasedStat().equals(c.getPokemon().getNature().getDecreasedStat())) && c.getPokemon().getNature().getIncreasedStat().getLabel().getIdentifier().equals("attack")) {
					summary+="+ ";
				}
				else if (!(c.getPokemon().getNature().getIncreasedStat().equals(c.getPokemon().getNature().getDecreasedStat())) && c.getPokemon().getNature().getDecreasedStat().getLabel().getIdentifier().equals("attack")) {
					summary+="- ";
				}
				summary+= " Atk ";
			}
			else {
				summary += c.getPokemon().getStats().get(3).getEffort();
				if (!(c.getPokemon().getNature().getIncreasedStat().equals(c.getPokemon().getNature().getDecreasedStat())) && c.getPokemon().getNature().getIncreasedStat().getLabel().getIdentifier().equals("special-attack")) {
					summary+="+ ";
				}
				else if (!(c.getPokemon().getNature().getIncreasedStat().equals(c.getPokemon().getNature().getDecreasedStat())) && c.getPokemon().getNature().getDecreasedStat().getLabel().getIdentifier().equals("special-attack")) {
					summary+="- ";
				}
				summary+= " SpA ";
			}
		}

		summary+= c.getPokemon().getAbility().getIdentifierCleaned();

		if (!(c.getPokemon().getItem() == null)) {
			summary += " "+c.getPokemon().getItemCleaned()+" ";
		}
		
		summary += c.getPokemon().getIdentifierCleaned();
		
		if (c.getCond().equals("Outspeed")) {
			if (c.isYourTw()) {
				summary += " in Tailwind";
			}
			if (c.isFoeTw()) {
				summary += " with opposing Tailwind";
			}
		}
		else {
			if (c.getCond().equals("Knock Out") || c.getCond().equals("2HKO")) {
				summary += " with";
			}
			if(c.isCritical()) {
				summary+=" Crit ";
			}
			summary += " "+c.getMove().getIdentifierCleaned();
		}
		if (c.isFriendGuard()) {
			summary += " with Friend Guard";
		}
		if (!c.getTerrain().equals("None")) {
			summary += " in "+c.getTerrain()+" Terrain";
		}
		
		if (!c.getWeather().equals("None")) {
			summary += " in "+c.getWeather();
		}
		if (c.isScreens()) {
			summary += " behind screens";
		}
		if (c.isBattery()) {
			summary += " with Battery";
		}
		if (c.isPowerSpot()) {
			summary += " with Power Spot";
		}
		return summary;
	}
	
	
	public int calcDamageByMove(Pokemon defPokemon, Condition c, int dmg) {
		int damage = dmg;
		if (this.isMoveMultihit(c.getMove())) {
			for (int hits=1; hits <= c.getHits(); hits++) {
				if (c.getMove().getIdentifier().equals("triple-axel")) {
					c.getMove().setPower(20*hits);
				}
				damage += calculateDamage(c, defPokemon);
				if (c.getCond().equals("2HKO") || c.getCond().equals("Knock Out")) {
					damage=this.checkBerryUsage(c.getPokemon(), c, damage);
				}
				else {
					damage=this.checkBerryUsage(defPokemon, c, damage);
				}
			}
		}
		else {
			damage += calculateDamage(c, defPokemon);
		}
		return damage;
	}
	
	
	public void resetCondition(Pokemon defPokemon, Condition c, HashMap<String, Object> origConds) {
		for (Stat s: defPokemon.getStats()) {
			s.setEffort(0);
		}
		c.setItemConsumed(false);
		c.setYourBoost((String) origConds.get("orig-boost"));
		c.setOppBoost((String) origConds.get("opp-boost"));
		c.setGravity((boolean) origConds.get("gravity"));
		c.setSmackDown((boolean) origConds.get("smack-down"));
		c.setWeather((String) origConds.get("weather"));
		c.setTerrain((String) origConds.get("terrain"));
	}
	
	
	public HashMap<String, Object> calculateEVs(Pokemon defPokemon, List<Condition> condition, boolean isMaxHP){

		List<ArrayList<Stat>> lists = new ArrayList<ArrayList<Stat>>();
		ArrayList<Object> conditionList = new ArrayList<Object>();
		String personality;
		boolean[] conditionOk = new boolean[condition.size()];
		for (int i=0; i < condition.size(); i++) {
			conditionOk[i] = true;
		}
		if (defPokemon.getNature() == null){
			personality="undecided";
		}
		else {
			personality = defPokemon.getNature().getIdentifier();
		}
		Nature nat = defPokemon.getNature();
		
		for (Condition c: condition) {
			HashMap<String, Object> origCond = new HashMap<String, Object>();
			origCond.put("orig-boost", c.getRawBoost());
			origCond.put("opp-boost", c.getRawOppBoost());
			origCond.put("gravity", c.isGravity());
			origCond.put("smack-down", c.isSmackDown());
			origCond.put("weather", c.getWeather());
			origCond.put("terrain", c.getTerrain());

			c.setSummary(this.summarizeCondition(defPokemon, c));
			
			
			if (!c.getCond().equals("Outspeed")) {

				if (c.isZ() && c.getGeneration() == 7) {
					c.getMove().setEffectiveBP(this.convertToZ(c.getMove()));
				}
			}
			//-----------------------------------------
			//----------OHKO/2HKO LOGIC----------------
			//-----------------------------------------
			if (c.getCond().equals("Knock Out") || c.getCond().equals("2HKO")) {

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
						this.resetCondition(defPokemon, c, origCond);
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
						int dmg = 0;
						if (c.isStealthRock()) {
							dmg = this.calculateSRDamage(c.getPokemon());
						}
						if (c.isOppDmax() && c.getGeneration() == 8) {
							dmg *= 2;
						}
						dmg= this.calcDamageByMove(defPokemon, c, dmg);
						if (c.getCond().equals("2HKO")) {
							if (c.getGeneration() == 8 && c.isOppDmax()) {
								dmg = (int) (c.getPokemon().getHP()*2*c.getHealth()/100) - this.healAfterTurn(defPokemon, c, (int) (c.getPokemon().getHP()*2*c.getHealth()/100) - dmg);
								dmg = this.calcDamageByMove(defPokemon, c, dmg);
							}
							else {
								dmg = (int) (c.getPokemon().getHP()*c.getHealth()/100) - this.healAfterTurn(defPokemon, c, (int) (c.getPokemon().getHP()*c.getHealth()/100) - dmg);
								dmg = this.calcDamageByMove(defPokemon, c, dmg);
							}
						}	

						//System.out.println("Damage: "+dmg+" Healed: "+ ((int) (c.getPokemon().getHP()*c.getHealth()/100-dmg) - this.healAfterTurn(c.getPokemon(), c, (int) (c.getPokemon().getHP()*c.getHealth()/100-dmg)))+" Total: "+((int) (c.getPokemon().getHP()*c.getHealth()/100-dmg)- (this.healAfterTurn(defPokemon, c, (int) (c.getPokemon().getHP()*c.getHealth()/100-dmg))-dmg))) ;
						//System.out.println("SpA: "+i+" Nature: "+n.getIdentifierCleaned());
						if (dmg >= c.getPokemon().getHP()*c.getHealth()/100 || (c.isOppDmax() && dmg >= c.getPokemon().getHP()*2*c.getHealth()/100 && c.getGeneration() == 8)){
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
			//-----------------------------------------
			//----------OUTSPEED LOGIC-----------------
			//-----------------------------------------
			else if (c.getCond().equals("Outspeed")) {
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
						this.resetCondition(defPokemon, c, origCond);
						defPokemon.getStats().get(5).setEffort(i);
						int speedA = calculateSpeed(defPokemon, c.getYourBoost(), c.getWeather(), c.getTerrain(), c.isYourTw()) ;
						int speedB = calculateSpeed(c.getPokemon(), c.getOppBoost(), c.getWeather(), c.getTerrain(), c.isFoeTw());
						System.out.println(speedA+ " " + speedB);

						if (speedA> speedB) {
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
			//-----------------------------------------
			//----------SURVIVE LOGIC------------------
			//-----------------------------------------
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
						this.resetCondition(defPokemon, c, origCond);
						defPokemon.getStats().get(0).setEffort(hp);
						int damage = 0;
						if (c.isStealthRock()) {
							damage = this.calculateSRDamage(defPokemon);
						}
						if (c.isSelfDmax() && c.getGeneration() == 8) {
							damage *= 2;
						}
						damage = this.calcDamageByMove(defPokemon, c, damage);
						System.out.println("T1: "+damage+ " HP: "+hp + " Def: "+ 0+" Nature: "+natures[i].getIdentifierCleaned());
						if (c.getCond().equals("Survive 2")) {
							if (c.isSelfDmax() && c.getGeneration() == 8) {
								damage = (int) (defPokemon.getHP()*2*c.getHealth()/100) - this.healAfterTurn(defPokemon, c, (int) (defPokemon.getHP()*2*c.getHealth()/100) - damage);
								System.out.println("Pre-T2: "+damage);
								damage = this.calcDamageByMove(defPokemon, c, damage);
								System.out.println("T2: "+damage);
							}
							else {
								damage = (int) (defPokemon.getHP()*c.getHealth()/100) - this.healAfterTurn(defPokemon, c, (int) (defPokemon.getHP()*c.getHealth()/100) - damage);
								System.out.println("Pre-T2: "+damage);
								damage = this.calcDamageByMove(defPokemon, c, damage);
								System.out.println("T2: "+damage);
							}
						}	

						//System.out.println("HP: "+ hp+" Def/SpD: 0 Nature: "+natures[i].getIdentifierCleaned());
						//System.out.println(c.getPokemon().getIdentifierCleaned()+": "+ damage);
						if (damage < defPokemon.getHP()*c.getHealth()/100 || (c.isSelfDmax() && c.getGeneration() == 8 && damage < defPokemon.getHP() *2* c.getHealth() / 100)){
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
							System.out.println("HP: "+ hp+" Def/SpD: "+defPokemon.getStats().get(2).getEffort()+"/"+defPokemon.getStats().get(4).getEffort() +" Nature: "+natures[i].getIdentifierCleaned()+" HP%: "+defPokemon.getHP()+" "+c.getHealth());
							System.out.println("HP: "+ hp+" Def/SpD: 0"+" Nature: "+natures[i].getIdentifierCleaned()+" HP%: "+defPokemon.getHP()+" "+c.getHealth());
							System.out.println(c.getPokemon().getIdentifierCleaned()+": "+ damage);
							break;
						}
					}
					if (found) {
						int lastDef = 0;
						for (int hp = maxHP-4; hp >= 0; hp-=4) {
							for (int def = lastDef; def <= 252; def+=4) {
								this.resetCondition(defPokemon, c, origCond);
								defPokemon.getStats().get(0).setEffort(hp);
								if (isMovePhysical(c.getMove())) {
									defPokemon.getStats().get(2).setEffort(def);
								}
								else {
									defPokemon.getStats().get(4).setEffort(def);
								}
								int damage = 0;
								if (c.isStealthRock()) {
									damage = this.calculateSRDamage(defPokemon);
								}
								if (c.isSelfDmax() && c.getGeneration() == 8) {
									damage *= 2;
								}
								damage = this.calcDamageByMove(defPokemon, c, damage);
								System.out.println("T1: "+damage+ " HP: "+hp + " Def: "+ def+" Nature: "+natures[i].getIdentifierCleaned());

								if (c.getCond().equals("Survive 2")) {
									if (c.isSelfDmax() && c.getGeneration() == 8) {
										damage = (int) (defPokemon.getHP()*2*c.getHealth()/100) - this.healAfterTurn(defPokemon, c, (int) (defPokemon.getHP()*2*c.getHealth()/100) - damage);
										damage = this.calcDamageByMove(defPokemon, c, damage);
									}
									else {
										damage = (int) (defPokemon.getHP()*c.getHealth()/100) - this.healAfterTurn(defPokemon, c, (int) (defPokemon.getHP()*c.getHealth()/100) - damage);
										damage = this.calcDamageByMove(defPokemon, c, damage);
									}
									System.out.println("T2: "+damage);

								}	

								//System.out.println("HP: "+ hp+" Def/SpD: "+def+" Nature: "+natures[i].getIdentifierCleaned());
								//System.out.println(c.getPokemon().getIdentifierCleaned()+": "+ damage);
								if (damage < defPokemon.getHP()*c.getHealth()/100){
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
									System.out.println("HP: "+ hp+" Def/SpD: "+def+" Nature: "+natures[i].getIdentifierCleaned());
									System.out.println(c.getPokemon().getIdentifierCleaned()+": "+ damage);
									break;
								}
							}
						}
					}
					else {
						int lastHp = 252;
						for (int def = 0; def <= 252; def+=4) {
							for (int hp = lastHp; hp >= 0; hp -=4) {
								this.resetCondition(defPokemon, c, origCond);
								defPokemon.getStats().get(0).setEffort(hp);
								if (isMovePhysical(c.getMove())) {
									defPokemon.getStats().get(2).setEffort(def);
								}
								else {
									defPokemon.getStats().get(4).setEffort(def);
								}
								int damage = 0;
								if (c.isStealthRock()) {
									damage = this.calculateSRDamage(defPokemon);
								}
								if (c.isSelfDmax() && c.getGeneration() == 8) {
									damage *= 2;
								}
								damage = this.calcDamageByMove(defPokemon, c, damage);
								System.out.println("T1: "+damage+ " HP: "+hp + " Def: "+ def+" Nature: "+natures[i].getIdentifierCleaned());
								if (c.getCond().equals("Survive 2")) {
									if (c.isSelfDmax() && c.getGeneration() == 8) {
										damage = (int) (defPokemon.getHP()*2*c.getHealth()/100) - this.healAfterTurn(defPokemon, c, (int) (defPokemon.getHP()*2*c.getHealth()/100) - damage);
										System.out.println("Pre-T2: "+damage);
										damage = this.calcDamageByMove(defPokemon, c, damage);
										System.out.println("T2: "+damage);
									}
									else {
										damage = (int) (defPokemon.getHP()*c.getHealth()/100) - this.healAfterTurn(defPokemon, c, (int) (defPokemon.getHP()*c.getHealth()/100) - damage);
										System.out.println("Pre-T2: "+damage);
										damage = this.calcDamageByMove(defPokemon, c, damage);
										System.out.println("T2: "+damage);
									}
									

								}	

								//System.out.println("HP: "+ hp+" Def/SpD: "+def+" Nature: "+natures[i].getIdentifierCleaned());
								//System.out.println(c.getPokemon().getIdentifierCleaned()+": "+ damage);

								if (damage >= defPokemon.getHP()*c.getHealth()/100){
									if (hp != 252) {
										HashMap<String, Object> sx = new HashMap<String, Object>();
										sx.put("hp", hp+4);
										sx.put("nature", natures[i]);
										if (isMovePhysical(c.getMove())) {
											sx.put("defense", def);
										}
										else {
											sx.put("special-defense", def);
										}
										System.out.println("HP: "+ hp+" Def/SpD: "+def+" Nature: "+natures[i].getIdentifierCleaned());
										System.out.println(c.getPokemon().getIdentifierCleaned()+": "+ damage);
										storage.add(sx);
										lastHp = hp;
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
				conditionOk[conditionCounter] = false;

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
				else if (condition.get(conditionCounter).getCond().equals("Knock Out") || condition.get(conditionCounter).getCond().equals("2HKO") ) {
					if (condition.get(conditionCounter).getMove().getDamageClass().getIdentifier().equals("special")){
						boosted[2] = true;
					}
					else if (condition.get(conditionCounter).getMove().getDamageClass().getIdentifier().equals("physical")) {
						boosted[0] = true;
					}
					else {
						conditionOk[conditionCounter] = false;
					}
				}
				else {
					if (isMovePhysical(condition.get(conditionCounter).getMove())){
						boosted[1] = true;
					}
					else if (condition.get(conditionCounter).getMove().getDamageClass().getIdentifier().equals("special")){
						boosted[3] = true;
					}
					else {
						conditionOk[conditionCounter] = true;
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
				else if(condition.get(conditionCounter).getCond().equals("Knock Out") ||condition.get(conditionCounter).getCond().equals("2HKO")) {
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
					System.out.println(conds.get(0)+ " "+conds.get(1)+" "+conds.get(2));
					
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
		Nature natureIfNull;
		if (countBoosts > 1) {
			Nature originalBoostedStat = defPokemon.getNature();
			String[] statlabel = {"attack", "defense", "special-attack", "special-defense", "speed"};

			String statBoosted = null;
			if (originalBoostedStat != null && !originalBoostedStat.getDecreasedStat().equals(originalBoostedStat.getIncreasedStat())) {
				statBoosted = originalBoostedStat.getIncreasedStat().getLabel().getIdentifier();
			}
			for (int i = 0; i < 5; i ++) {
				if (!statlabel[i].equals(statBoosted) && boosted[i]) {
					ArrayList<HashMap<String, Object>> sstack = statStack.get(i);
					if (statlabel[i].equals("defense") || statlabel[i].equals("special-defense")) {
						ArrayList<ArrayList<HashMap<String, Object>>> allConds = (ArrayList<ArrayList<HashMap<String, Object>>>)sstack.get(0).get("allconds");

						while (allConds.get(0).size() == 0 || allConds.get(1).size() == 0 ) {
							conditionOk[condition.indexOf(sstack.get(0).get("condition"))] = false;
							sstack.remove(0);
							allConds = (ArrayList<ArrayList<HashMap<String, Object>>>)sstack.get(0).get("allconds");
						}
					}
					else {
						ArrayList<HashMap<String, Object>> allConds = (ArrayList<HashMap<String, Object>>)sstack.get(0).get("allconds");
						while(allConds.get(0) == null || allConds.get(1) == null) {
							conditionOk[condition.indexOf(sstack.get(0).get("condition"))] = false;
							sstack.remove(0);
							allConds = (ArrayList<HashMap<String, Object>>)sstack.get(0).get("allconds");
						}
					}
				}
			}
			
		}
		if (countBoosts >= 1) {
			String[] statlabel = {"attack", "defense", "special-attack", "special-defense", "speed"};
			Stat loweredStat = null;
			Stat boostedStat=null;
			Stat candidate=null;
			for (int i = 0; i < boosted.length; i++) {
				if (boosted[i]) {
					boostedStat = this.getStatByName(statlabel[i]);
				}
				else if (statStack.get(i).size() == 0) {
					loweredStat = this.getStatByName(statlabel[i]);
				}
				else if (statStack.get(i).size() == 1) {
					candidate = this.getStatByName(statlabel[i]);
				}
			}
			if (loweredStat == null) {
				loweredStat = candidate;
			}
			natureIfNull = natureRepo.findNatureWithStats(boostedStat, loweredStat);
		}
		else {
			natureIfNull = this.getNature("hardy");
		}
		
		//FOLLOW BY PICKING EVS, MINIMIZE, AND SEE IF IT IS OVER 508. IF SO REMOVE THE ONE WITH MOST EVS.
		int[] res;
		do {
			
			res = calcAllEVs(attacks, defense, spas, spds, speed, nat, natureIfNull, isMaxHP);
			//System.out.println(Arrays.toString(res));
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
				case 1: 
					conditionOk[condition.indexOf(attacks.get(0).get("condition"))] = false;
					attacks.remove(0);
					break;
				case 2:
					conditionOk[condition.indexOf(defense.get(0).get("condition"))] = false;
					defense.remove(0);
					break;
				case 3:
					conditionOk[condition.indexOf(spas.get(0).get("condition"))] = false;
					spas.remove(0);
					break;
				case 4:
					conditionOk[condition.indexOf(spds.get(0).get("condition"))] = false;
					spds.remove(0);
					break;
				case 5: 
					conditionOk[condition.indexOf(speed.get(0).get("condition"))] = false;
					speed.remove(0);
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

					
					for (int x = 0; x < statMapList.size(); x++) {
						String statL = (i == 1 ? "defense" : "special-defense");
						
						if (!isMaxHP) {
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
						else {
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
		//System.out.println(suggestedNature.getIdentifier());
		int[] suggestedRes = new int[7];
		int suggestedTotal = 0;
		for (int i = 0; i < 6; i++) {
			suggestedRes[i] = res[i] + hinderArray[i] - boostArray[i];
			suggestedTotal+= suggestedRes[i];
		}
		suggestedRes[6] = 508-suggestedTotal;
		
		ArrayList<Condition> goodCond = new ArrayList<Condition>();
		ArrayList<Condition> badCond = new ArrayList<Condition>();
		for(int i = 0; i < condition.size(); i++) {
			if (conditionOk[i]) {
				goodCond.add(condition.get(i));
			}
			else {
				badCond.add(condition.get(i));
			}
		}
		
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultEVarr", res);
		resultMap.put("calculatedNature", natureIfNull);
		resultMap.put("suggestedEVarr", suggestedRes);
		resultMap.put("suggestedNature", suggestedNature);
		resultMap.put("goodCond", goodCond);
		resultMap.put("badCond", badCond);
		return resultMap;
	}
	
}
