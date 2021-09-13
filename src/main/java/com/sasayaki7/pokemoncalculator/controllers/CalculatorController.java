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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sasayaki7.pokemoncalculator.models.Ability;
import com.sasayaki7.pokemoncalculator.models.Condition;
import com.sasayaki7.pokemoncalculator.models.Item;
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
	private static final String[] conditions = {"Outspeed", "Survive", "Knock Out", "Underspeed"};
	private static final String[] weathers = {"None", "Rain", "Sun", "Sand", "Hail"};
	private static final String[] terrains = {"None", "Misty", "Grassy", "Electric", "Psychic"};

	
	@GetMapping("/api/moves")
	@ResponseBody public List<String> getMoves(@RequestParam(value="startWith") String param) {
		System.out.println(param);
		List<Move> moves = apiServ.getMoveStartingWith(param);
		List<String> retStr = new ArrayList<String>();
		for (Move m: moves) {
			retStr.add(m.getIdentifier());
		}
		return retStr;
	}
	
	@GetMapping("/api/items")
	@ResponseBody public List<String> getItems(@RequestParam(value="startWith") String param) {
		List<Item> items= apiServ.getItemStartingWith(param);
		List<String> retStr = new ArrayList<String>();
		for (Item i: items) {
			retStr.add(i.getIdentifier());
		}
		return retStr;
	}
	
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
		model.addAttribute("conditions", conditions);
		model.addAttribute("weathers", weathers);
		model.addAttribute("terrains", terrains);
		model.addAttribute("natures", apiServ.getNatures());
		return "calculator.jsp";
	}
	
	@PostMapping("/")
	public String calculateResult(HttpServletRequest request, Model model) {
		Pokemon defendingPokemon = apiServ.getPokemon(request.getParameter("calcpokemon"));
		defendingPokemon.setLevel(Integer.parseInt(request.getParameter("level")));
		System.out.println("Level: "+defendingPokemon.getLevel());
		defendingPokemon.setAbility(new Ability(request.getParameter("ability")));
		defendingPokemon.setItem(request.getParameter("item"));
		if (request.getParameter("nature-pokemon").equals("undecided")) {
			defendingPokemon.setNature(null);
		}
		else {
			defendingPokemon.setNature(apiServ.getNature(request.getParameter("nature-pokemon")));
		}
		int counter = 1;
		List<Condition> conditions = new ArrayList<Condition>();
		while(request.getParameter("pokemon-"+counter) != null) {
			Condition condition = new Condition();
			Pokemon condPokemon = apiServ.getPokemon(request.getParameter("pokemon-"+counter));
			condPokemon.setAbility(new Ability(request.getParameter("ability-"+counter)));
			condPokemon.setItem(request.getParameter("item-"+counter));
			condPokemon.setLevel(Integer.parseInt(request.getParameter("level-"+counter)));
			condPokemon.getStats().get(0).setIv(Integer.parseInt(request.getParameter("hp-iv-"+counter)));
			condPokemon.getStats().get(0).setEffort(Integer.parseInt(request.getParameter("hp-"+counter)));

			Move move = apiServ.getMove(request.getParameter("move-"+counter));
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
			condPokemon.setNature(apiServ.getNature(request.getParameter("nature-"+counter)));
			condPokemon.getStats().get(5).setIv(Integer.parseInt(request.getParameter("speed-iv-"+counter)));
			condPokemon.getStats().get(5).setEffort(Integer.parseInt(request.getParameter("speed-"+counter)));
			condition.setPokemon(condPokemon);
			condition.setCond(request.getParameter("condition-"+counter));
			condition.setOppBoost(request.getParameter("boost-opp-"+counter));
			condition.setYourBoost(request.getParameter("boost-you-"+counter));
			condition.setWeather(request.getParameter("weather-"+counter));
			condition.setTerrain(request.getParameter("terrain-"+counter));
			condition.setScreens(Boolean.parseBoolean(request.getParameter("screen-"+counter)));
			condition.setMove(apiServ.getMove(request.getParameter("move-"+counter)));
			condition.setHealth(Double.parseDouble(request.getParameter("hp-percentage-"+counter)));
			counter++;
			conditions.add(condition);
		}
		
		
		HashMap<String, Object> result = apiServ.calculateEVs(defendingPokemon, conditions);
		ArrayList<Object[]> statListX = new ArrayList<Object[]>();
		String[] labels = {"HP", "Attack", "Defense", "Spec. Attack", "Spec. Defense", "Speed", "EVs Remaining"};
		for (int i = 0; i < 7; i++) {
			Object[] tempX = new Object[2];
			int[] tempArr = (int[]) result.get("resultEVarr");
			tempX[0] = tempArr[i];
			tempX[1] = labels[i];
			statListX.add(tempX);
		}
		model.addAttribute("calcedstat", statListX);
		model.addAttribute("conditions", conditions);
		model.addAttribute("pokemon", defendingPokemon);
		if (result.get("suggestedEVarr") != null) {
			model.addAttribute("suggestionEV", (int[])result.get("suggestedEVarr"));
			model.addAttribute("suggestedNature", (Nature) result.get("suggestedNature"));

		}
		
//		Map<String, String[]> paramMap =  request.getParameterMap();
//		for(Map.Entry<String, String[]> entry : paramMap.entrySet()) {
//	    }
		
		return "calculatorresult.jsp";
	}
}
