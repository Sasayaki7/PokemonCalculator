package com.sasayaki7.pokemoncalculator.controllers;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sasayaki7.pokemoncalculator.models.Ability;
import com.sasayaki7.pokemoncalculator.models.Condition;
import com.sasayaki7.pokemoncalculator.models.Item;
import com.sasayaki7.pokemoncalculator.models.LegacyType;
import com.sasayaki7.pokemoncalculator.models.Move;
import com.sasayaki7.pokemoncalculator.models.Nature;
import com.sasayaki7.pokemoncalculator.models.Pokemon;
import com.sasayaki7.pokemoncalculator.models.Stat;
import com.sasayaki7.pokemoncalculator.services.APIService;

@Controller
public class CalculatorController {
	
	@Autowired
	private APIService apiServ;
		
	
	private static final String[] boosts = {"+6", "+5", "+4", "+3", "+2", "+1", "0", "-1", "-2", "-3", "-4", "-5", "-6"};
	private static final String[] conditionsdef = {"Outspeed", "Survive", "Survive 2", "Knock Out", "2HKO", };
	private static final String[] weathers = {"None", "Rain", "Sun", "Sand", "Hail"};
	private static final String[] terrains = {"None", "Misty", "Grassy", "Electric", "Psychic"};
	private static final String[] status = {"Healthy", "Burn", "Paralyze", "Freeze", "Poison", "Toxic"};
	
	@CrossOrigin
	@GetMapping("/api/moves")
	@ResponseBody public List<String> getMoves(@RequestParam(value="startWith") String param) {
		List<Move> moves = apiServ.getMoveStartingWith(param);
		List<String> retStr = new ArrayList<String>();
		for (Move m: moves) {
			retStr.add(m.getIdentifier());
		}
		return retStr;
	}
	
	@CrossOrigin
	@GetMapping("/api/items")
	@ResponseBody public List<String> getItems(@RequestParam(value="startWith") String param) {
		List<Item> items= apiServ.getItemStartingWith(param);
		List<String> retStr = new ArrayList<String>();
		for (Item i: items) {
			retStr.add(i.getIdentifier());
		}
		return retStr;
	}
	
	@CrossOrigin
	@GetMapping("/api/pokemon")
	@ResponseBody public List<String> getPokemon(@RequestParam(value="startWith") String param) {
		List<Pokemon> pokes= apiServ.getPokemonStartingWith(param);
		List<String> retStr = new ArrayList<String>();
		for (Pokemon p: pokes) {
			retStr.add(p.getIdentifier());
		}
		return retStr;
	}
	
	@GetMapping("/")
	public String getCalculator(Model model) {
		model.addAttribute("boosts", boosts);
		model.addAttribute("conditions", conditionsdef);
		model.addAttribute("weathers", weathers);
		model.addAttribute("terrains", terrains);
		model.addAttribute("natures", apiServ.getNatures());
		model.addAttribute("status", status);
		return "calculator.jsp";
	}
	
	@PostMapping("/")
	public String calculateResult(HttpServletRequest request, Model model) {
		boolean valid = true;
		int generation = Integer.parseInt(request.getParameter("generation"));
		
		Pokemon defendingPokemon = apiServ.getPokemon(apiServ.lowerStringRemoveDashes(request.getParameter("calcpokemon")));
		if (defendingPokemon == null) {
			valid = false;
			model.addAttribute("defPokemonError", true);
		}
		else {
			model.addAttribute("defPokemon", defendingPokemon);
			defendingPokemon.setLevel(Integer.parseInt(request.getParameter("level")));
			defendingPokemon.setAbility(new Ability(apiServ.lowerStringRemoveDashes(request.getParameter("ability"))));
			defendingPokemon.setItem(apiServ.lowerStringRemoveDashes(request.getParameter("item")));
			defendingPokemon.setStatus(request.getParameter("status"));
			if (request.getParameter("nature-pokemon").equals("undecided")) {
				defendingPokemon.setNature(null);
			}
			else {
				defendingPokemon.setNature(apiServ.getNature(request.getParameter("nature-pokemon")));
			}
			if (defendingPokemon.getLegacyTypes().size() > 0) {
				for (LegacyType lt: defendingPokemon.getLegacyTypes()) {
					if (lt.getGenerationId() <= generation){
						defendingPokemon.getOldTypes().add(apiServ.getType(lt.getTypeId()));
					}
				}
			}
		}
		int counter = 1;
		List<Condition> conditions = new ArrayList<Condition>();
		while(request.getParameter("pokemon-"+counter) != null) {
			Condition condition = new Condition();
			condition.setCond(request.getParameter("condition-"+counter));
			Pokemon condPokemon = apiServ.getPokemon(apiServ.lowerStringRemoveDashes(request.getParameter("pokemon-"+counter)));
			if (condPokemon == null) {
				valid = false;
				model.addAttribute("condPokemon"+counter+"Error", true);
			}
			else {
				condPokemon.setAbility(new Ability(apiServ.lowerStringRemoveDashes(request.getParameter("ability-"+counter))));
				condPokemon.setItem(apiServ.lowerStringRemoveDashes(request.getParameter("item-"+counter)));
				condPokemon.setLevel(Integer.parseInt(request.getParameter("level-"+counter)));
				condPokemon.setStatus(request.getParameter("status-"+counter));
				condPokemon.getStats().get(0).setIv(Integer.parseInt(request.getParameter("hp-iv-"+counter)));
				condPokemon.getStats().get(0).setEffort(Integer.parseInt(request.getParameter("hp-"+counter)));
				condPokemon.setNature(apiServ.getNature(apiServ.lowerStringRemoveDashes(request.getParameter("nature-"+counter))));
				condPokemon.getStats().get(5).setIv(Integer.parseInt(request.getParameter("speed-iv-"+counter)));
				condPokemon.getStats().get(5).setEffort(Integer.parseInt(request.getParameter("speed-"+counter)));			
				if (condPokemon.getLegacyTypes().size() > 0) {
					for (LegacyType lt: condPokemon.getLegacyTypes()) {
						if (lt.getGenerationId() <= Integer.parseInt(request.getParameter("generation"))){
							condPokemon.getOldTypes().add(apiServ.getType(lt.getTypeId()));
						}
					}
				}
				
				Move move = apiServ.getMove(apiServ.lowerStringRemoveDashes(request.getParameter("move-"+counter)));
				if (move == null) {
					if (!(condition.getCond().equals("Outspeed") || condition.getCond().equals("Underspeed"))) {
						model.addAttribute("move"+counter+"Error", true);
						valid = false;
					}
					
				}
				else {
					if (move.getDamageClass().getIdentifier().equals("physical")) {
						condPokemon.getStats().get(1).setIv(Integer.parseInt(request.getParameter("atk-iv-"+counter)));
						condPokemon.getStats().get(1).setEffort(Integer.parseInt(request.getParameter("atk-"+counter)));			
						condPokemon.getStats().get(2).setIv(Integer.parseInt(request.getParameter("def-iv-"+counter)));
						condPokemon.getStats().get(2).setEffort(Integer.parseInt(request.getParameter("def-"+counter)));
					}
					else {
						condPokemon.getStats().get(3).setIv(Integer.parseInt(request.getParameter("atk-iv-"+counter)));
						condPokemon.getStats().get(3).setEffort(Integer.parseInt(request.getParameter("atk-"+counter)));
						condPokemon.getStats().get(4).setIv(Integer.parseInt(request.getParameter("def-iv-"+counter)));
						condPokemon.getStats().get(4).setEffort(Integer.parseInt(request.getParameter("def-"+counter)));
					}
					condition.setMove(move);

					if (apiServ.getLegacyMove(move.getIdentifier(), generation) != null) {
						move.setEffectiveBP(apiServ.getLegacyMove(move.getIdentifier(), generation).getPower());
					}
				}
			}
			
			condition.setDoubles(request.getParameter("single-double").equals("double"));
			condition.setPokemon(condPokemon);
			condition.setGeneration(Integer.parseInt(request.getParameter("generation")));
			condition.setOppBoost(request.getParameter("boost-opp-"+counter));
			condition.setYourBoost(request.getParameter("boost-you-"+counter));
			condition.setWeather(request.getParameter("weather-"+counter));
			condition.setTerrain(request.getParameter("terrain-"+counter));
			condition.setHits(Integer.parseInt(request.getParameter("hits-"+counter)));
			
			
			if (request.getParameter("z-"+counter) != null) {
				condition.setZ(true);
			}
			if (request.getParameter("dynamax-self-"+counter) != null) {
				condition.setSelfDmax(true);
			}
			if (request.getParameter("dynamax-opp-"+counter) != null) {
				condition.setOppDmax(true);
			}
			if (request.getParameter("screen-"+counter) != null) {
				condition.setScreens(true);
			}
			if (request.getParameter("your-tailwind-"+counter) != null){
				condition.setYourTw(true);
			}
			
			if (request.getParameter("foe-tailwind-"+counter) != null){
				condition.setFoeTw(true);
			}
			
			if (request.getParameter("hh-"+counter) != null){
				condition.setHelpingHand(true);
			}
			
			if (request.getParameter("flower-gift-"+counter) != null){
				condition.setFlowerGift(true);
			}
			
			if (request.getParameter("power-spot-"+counter) != null){
				condition.setPowerSpot(true);
			}
			
			if (request.getParameter("battery-"+counter) != null){
				condition.setBattery(true);
			}
			
			if (request.getParameter("stealth-rock-"+counter) != null){
				condition.setStealthRock(true);
			}
			
			if (request.getParameter("gravity-"+counter) != null){
				condition.setGravity(true);
			}
			
			if (request.getParameter("smack-down-"+counter) != null){
				condition.setSmackDown(true);
			}

			condition.setHealth(Double.parseDouble(request.getParameter("hp-percentage-"+counter)));
			counter++;
			conditions.add(condition);
		}
		
		if (!valid) {
			model.addAttribute("allConditions", conditions);
			model.addAttribute("errors", true);
			model.addAttribute("boosts", boosts);
			model.addAttribute("conditions", conditionsdef);
			model.addAttribute("weathers", weathers);
			model.addAttribute("terrains", terrains);
			model.addAttribute("natures", apiServ.getNatures());
			model.addAttribute("status", status);
			return "calculator.jsp";
		}
		
		HashMap<String, Object> result = apiServ.calculateEVs(defendingPokemon, conditions, request.getParameter("max-hp") != null);
		ArrayList<Object[]> statListX = new ArrayList<Object[]>();
		String[] labels = {"HP", "Attack", "Defense", "Spec. Attack", "Spec. Defense", "Speed", "EVs Remaining"};
		for (int i = 0; i < 7; i++) {
			Object[] tempX = new Object[2];
			int[] tempArr = (int[]) result.get("resultEVarr");
			tempX[0] = tempArr[i];
			tempX[1] = labels[i];
			statListX.add(tempX);
		}
		ArrayList<Condition> goodCond = new ArrayList<Condition>();
		ArrayList<Condition> badCond = new ArrayList<Condition>();
		
		model.addAttribute("resultOn", true);
		model.addAttribute("calcedstat", statListX);
		model.addAttribute("conditions", conditions);
		model.addAttribute("pokemon", defendingPokemon);
		if (defendingPokemon.getNature() == null) {
			defendingPokemon.setNature((Nature) result.get("calculatedNature"));
		}
		if (result.get("suggestedEVarr") != null) {
			model.addAttribute("suggestionEV", (int[])result.get("suggestedEVarr"));
			model.addAttribute("suggestedNature", (Nature) result.get("suggestedNature"));
		}
		model.addAttribute("goodCondition", result.get("goodCond"));
		model.addAttribute("badCondition", result.get("badCond"));
		
//		Map<String, String[]> paramMap =  request.getParameterMap();
//		for(Map.Entry<String, String[]> entry : paramMap.entrySet()) {
//	    }
		
		return "calculatorresult.jsp";
	}
}
