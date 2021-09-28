<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pokémon EV/IV calculator</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/css/calculator.css"> <!-- change to match your file/naming structure -->
    
</head>
<body>
	<h1>Pokémon EV Calculator</h1>
	<form name="submission-form" action="/" method="POST"  autocomplete="off">
		<div class="input-row top-row">
			<div class="btn-group" role="group" aria-label="Basic radio toggle button group">
				<input type="radio" class="btn-check" name="generation" id="gen8" value="8" checked>
				<label class="btn btn-outline-secondary" for="gen8">SS</label>
				<input type="radio" class="btn-check" name="generation" id="gen7" value="7">
				<label class="btn btn-outline-secondary" for="gen7">SM</label>
				<input type="radio" class="btn-check" name="generation" id="gen6" value="6">
				<label class="btn btn-outline-secondary" for="gen6">XY</label>
				<input type="radio" class="btn-check" name="generation" id="gen5" value="5">
				<label class="btn btn-outline-secondary" for="gen5">BW</label>
<!--				<input type="radio" class="btn-check" name="generation" id="gen4" value="4">
 				<label class="btn btn-outline-secondary" for="gen4">DPP</label>
				<input type="radio" class="btn-check" name="generation" id="gen3" value="3">
				<label class="btn btn-outline-secondary" for="gen3">ADV</label>  -->
			</div>
			
			<div class="input-row">
			   	<div class="btn-group" role="group" aria-label="Basic radio toggle button group">
				  <input type="radio" class="btn-check" name="single-double" id="single-1" value="single" checked>
				  <label class="btn btn-outline-secondary" for="single-1">Singles</label>
				  <input type="radio" class="btn-check" name="single-double" id="double-1" value="double">
				  <label class="btn btn-outline-secondary" for="double-1">Doubles</label>
				</div>
		   	</div>
			
		</div>
		<div class="form-div">
			<div class="left-pokemon">
				<div class="item-pokemon-display">
					<img id="pokemon-pic" <c:choose> <c:when test="${errors and not defPokemonError}"> src="https://play.pokemonshowdown.com/sprites/xyani/${defPokemon.getIdentifierNoSpace()}.gif" </c:when><c:otherwise> src="" </c:otherwise></c:choose> alt=""/>
					<img id="item-pic" <c:choose> <c:when test="${errors and not defPokemonError}"> src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/${defPokemon.getItem()}.png" </c:when><c:otherwise> src="" </c:otherwise></c:choose> alt=""/>
				</div>
				<div class="type-pokemon"><c:if test="${errors and not defPokemonError}">
					<c:forEach var="type" items = "${defPokemon.types}">
						<img src="https://www.serebii.net/pokedex-bw/type/${type.identifier}.gif">
					</c:forEach>
				</c:if></div>
				<div class="input-row">
					<c:if test="${defPokemonError}">
						<p class="error">Please enter in a valid Pokemon</p>
					</c:if>
					<label for="calcpokemon">Pokémon:</label>
					<div class="form-input-with-autocomplete">
			   			<input type="text" class="medium-text" name="calcpokemon" oninput="autoComplete(this)" <c:if test="${errors and not defPokemonError }">value="${defPokemon.getIdentifierCleaned() }"</c:if>/>
			   		   	<div class="item-autocomplete"></div>
			   		</div>
			   	</div>
		   		<div class="input-row">
		   			<label for="item">Item:</label>
		   			<div class="form-input-with-autocomplete">
			   			<input type="text" class="medium-text" name="item" oninput="autoComplete(this)" <c:if test="${errors and not defPokemonError }">value="${defPokemon.getItemCleaned()}"</c:if>/>
			   			<div class="item-autocomplete"></div>
		   			</div>
		   		</div>
		   		<div class="input-row">
		   			<label for="ability">Ability: </label>
		   			<select name="ability" class="your-ability">
		   				<c:if test="${errors and not defPokemonError}">
		   					<c:forEach var="ability" items="${defPokemon.abilities}">
		   						<option value="${ability.identifier}" <c:if test="${ability.identifier eq defPokemon.ability.identifier}">selected</c:if>><c:out value="${ability.getIdentifierCleaned()}"/></option>
		   					</c:forEach>
		   				</c:if>
			   		</select>
		   		</div>
		   		<div class="input-row">
		   			<label for="level">Level:</label>
		   			<input class="short-number" name="level" type="number" min="1" max="100" 
		   			<c:choose>
		   				<c:when test="${errors and not defPokemonError}">value="${defPokemon.level}"</c:when>
		   				<c:otherwise>value=100</c:otherwise>
		   			</c:choose>
		   			/>
		   		</div>
		   		<div class="input-row">
		   		   	<label for="nature-pokemon">Nature:</label>
			   		<select name="nature-pokemon">
			   			<option value="undecided" <c:if test="${errors and not defPokemonError and defPokemon.nature.identifier eq 'undecided' }">selected </c:if>>Not important</option>
			   			<c:forEach var="nature" items="${natures}">
			   				<option value="${nature.identifier}" <c:if test="${errors and not defPokemonError and defPokemon.nature.identifier eq nature.identifier }">selected </c:if>><c:out value="${nature.identifier}"/></option>
			   			</c:forEach>
			   		</select>
		   		</div>

		   		<div class="input-row">
		   			<label for="status">Status:</label>
		   			<select name="status">
		   				<c:forEach var="stat" items="${status}" varStatus="loop">
		   					<option value="${stat}" <c:if test="${loop.first}">selected</c:if>><c:out value="${stat}"/></option>
		   				</c:forEach>
		   			</select>
		   		</div>
		   	</div>
	   		<div class="conditions">
	   			<c:choose>
	   				<c:when test="${errors}">
	   					<c:forEach var="cond" items="${allConditions}" varStatus="loopvar">
		   					<div id="condition-${loopvar.index+1}" class="cells">
				   				<div class="cell">
					   				<select name="condition-${loopvar.index+1}" onChange= "changeCondition(this)">
					   					<c:forEach var="condition" items="${conditions}">
					   						<option value="${condition}" <c:if test="${cond.getCond() eq condition}">selected</c:if>> <c:out value="${condition}"/></option>
					   					</c:forEach>
					   				</select>
					   				<div class="btn-group btn-group-sm" role="group" aria-label="Basic checkbox toggle button group">
				   						<input type="checkbox" class="btn-check" name="dynamax-self-${loopvar.index+1}" autocomplete="off">
										<label class="btn btn-outline-secondary" for="dynamax-self-${loopvar.index+1}">Self D-Max</label>
									</div>
					   				
					   			</div>
					   			<div class="cell">
						   			<div class="opp-pokemon-cell">
						   				<img class="opp-pokemon" <c:choose> <c:when test="${not empty cond.getPokemon()}"> src="https://play.pokemonshowdown.com/sprites/xyani/${cond.getPokemon().getIdentifierNoSpace()}.gif" </c:when><c:otherwise> src="" </c:otherwise></c:choose> alt=""/>
						   				<img class="opp-item" <c:choose> <c:when test="${not empty cond.getPokemon()}"> src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/${cond.getPokemon().getItem()}.png" </c:when><c:otherwise> src="" </c:otherwise></c:choose> alt=""/>
					   				</div>
					   				<div class="type-pokemon">
					   					<c:if test="${not empty cond.getPokemon()}">
											<c:forEach var="type" items = "${cond.getPokemon().types}">
												<img src="https://www.serebii.net/pokedex-bw/type/${type.identifier}.gif">
											</c:forEach>
										</c:if>
					   				</div>
					   				<c:if test="${empty cond.getPokemon()}">
										<p class="error">Please enter in a valid Pokemon</p>
					   				</c:if>
					   				<div class="input-row">
						   				<label for="pokemon-${loopvar.index+1}">Opp. Pokémon:</label>
						   				<div class="form-input-with-autocomplete">
						   					<input type="text" class="medium-text" name="pokemon-${loopvar.index+1}" 	<c:if test="${not empty cond.getPokemon()}">value="${cond.getPokemon().getIdentifierCleaned()}"</c:if> oninput="autoComplete(this)">
					   					   	<div class="item-autocomplete"></div>
				   						</div>
				   					</div>
					   			</div>
					   			<div class="cell">
					   				<div class="input-row">
					   					<label for="ability-${loopvar.index+1}">Opp. Ability:</label>				   					
					   					<select name="ability-${loopvar.index+1}" class="opp-ability">
					   					<c:if test="${not empty cond.getPokemon()}">
					   						<c:forEach var="abilityBeforehand" items="${cond.getPokemon().getAbilities()}">
					   							<option value="${abilityBeforehand.getIdentifier()}" <c:if test="${abilityBeforehand.getIdentifier() eq cond.getPokemon().identifier}">selected</c:if>><c:out value="${abilityBeforehand.getIdentifierCleaned()}"/></option>
					   						</c:forEach>
					   					</c:if> 
					   					</select>
					   				</div>
					   				<div class="input-row">
					   					<label for="item-${loopvar.index+1}">Opp. Item:</label>
					   				   	<div class="form-input-with-autocomplete">	
					   						<input type="text" class="medium-text" name="item-${loopvar.index+1}" oninput="autoComplete(this)" <c:if test="${not empty cond.getPokemon()}">value="${cond.getPokemon().getItemCleaned()}"</c:if>>
					   					   	<div class="item-autocomplete"></div>
				   						</div>	   				
				   					</div>
					   				<div class="input-row">
					   					<label for="level-${loopvar.index+1}">Opp. Level:</label>
					   					<input class="short-number" type="number" name="level-${loopvar.index+1}" min="1" max="100" 
					   						<c:choose>
		   										<c:when test="${not empty cond.getPokemon() }">value="${cond.getPokemon().level}"</c:when>
		   										<c:otherwise>value=100</c:otherwise>
		   									</c:choose>
		   								/>
					   				</div>
					   				<div class="input-row">
					   					<label for="nature-${loopvar.index+1}">Nature:</label>
						   				<select name="nature-${loopvar.index+1}">
					   						<c:forEach var="nature" items="${natures}" varStatus="loop">
					   							<option value="${nature.identifier}" <c:if test="${nature.identifier eq cond.getPokemon().getNature().getIdentifier()}">selected</c:if>><c:out value="${nature.identifier}"/></option>
					   						</c:forEach>
					   					</select>
				   					</div>
				   					<div class="input-row">
				   						<label for="hp-percentage-${loopvar.index+1}">HP:</label>
				   						<input class="short-number" type="text" name="hp-percentage-${loopvar.index+1}"
				   						<c:choose>
		   										<c:when test="${not empty cond.getPokemon()}">value="${cond.getHealth()}"</c:when>
		   										<c:otherwise>value=100</c:otherwise>
		   									</c:choose>/>
				   					</div>
				   				</div>
				   				<div class="cell">
				   					<label for="move-${loopvar.index+1}">Move:</label>
					   			   	<div class="form-input-with-autocomplete">	
					   					<input type="text" class="medium-text" name="move-${loopvar.index+1}" oninput="autoComplete(this)"
					   						<c:if test="${not empty cond.getMove()}">
					   							value="${cond.getMove().getIdentifierCleaned()}"
					   						</c:if>
					   					>
					   				   	<div class="item-autocomplete"></div>
				   					</div>
				   					<div class="btn-group btn-group-sm" role="group" aria-label="Basic checkbox toggle button group">
					   					<input type="checkbox" class="btn-check" id="crit-${loopvar.index+1}" name="crit-${loopvar.index}" <c:if test="${cond.isCritical()}"> checked</c:if> autocomplete="off">
										<label class="btn btn-outline-secondary" for="crit-${loopvar.index+1}">Crit</label>
										<input type="checkbox" class="btn-check" name="dynamax-opp-${loopvar.index+1}" autocomplete="off">
										<label class="btn btn-outline-secondary" for="dynamax-opp-${loopvar.index+1}">Opp DMax</label>
										<input type="checkbox" class="btn-check" name="z-${loopvar.index+1}" autocomplete="off">
										<label class="btn btn-outline-secondary" for="z-${loopvar.index+1}">Z</label>
						
									</div>
									<label for="status">Status:</label>
					   				<select name="status-${loopvar.index+1}">
					   				<c:forEach var="stat" items="${status}" varStatus="loop">
					   					<option value="${stat}" <c:if test="${cond.getPokemon().getStatus() eq stat}">selected</c:if>><c:out value="${stat}"/></option>
					   				</c:forEach>
					   				</select>

				   				</div>
				   				<div class="cell">		
					   				<div class="defev invisible">
					   					<div class="display-ev-iv">
					   						<h6>HP</h6>
						   					<label for="hp-${loopvar.index+1}">EV:</label>
						   					<input class="short-number" type="number" name="hp-${loopvar.index+1}" min="0" max="252" 
						   					<c:choose>
						   						<c:when test="${not empty cond.getPokemon()}"> value="${cond.getPokemon().getStats().get(0).getEffort()}" </c:when> 
						   						<c:otherwise> value="0"</c:otherwise>
						   					</c:choose>/>
						   					<label for="hp-iv-${loopvar.index+1}">IV:</label>
						   					<input class="short-number" type="number" name="hp-iv-${loopvar.index+1}" min="0" max="31"
						   					<c:choose>
						   						<c:when test="${not empty cond.getPokemon()}"> value="${cond.getPokemon().getStats().get(0).getIv()}" </c:when> 
						   						<c:otherwise> value="31"</c:otherwise>
						   					</c:choose>/>/>
					   					</div>
					
					   					<div class="display-ev-iv">
					   						<h6>Def/Spd</h6>
					   						<label for="def-${loopvar.index+1}">EV:</label>
					   						<input class="short-number" type="number" name="def-${loopvar.index+1}" min="0" max="252" 
					   						<c:choose>
						   						<c:when test="${not empty cond.getPokemon() and cond.getPokemon().getStats().get(2).getEffort() gt 0}"> value="${cond.getPokemon().getStats().get(2).getEffort()}" </c:when>
						   						<c:when test="${not empty cond.getPokemon() and cond.getPokemon().getStats().get(4).getEffort() gt 0}"> value="${cond.getPokemon().getStats().get(4).getEffort()}" </c:when>
						   						<c:otherwise> value="0"</c:otherwise>
						   					</c:choose>/>
					   						<label for="def-iv-${loopvar.index+1}">IV:</label>
					   						<input class="short-number" type="number" name="def-iv-${loopvar.index+1}" min="0" max="31"
					   						<c:choose>
						   						<c:when test="${not empty cond.getPokemon() and cond.getPokemon().getStats().get(2).getIv() lt 31}"> value="${cond.getPokemon().getStats().get(2).getIv()}" </c:when>
						   						<c:when test="${not empty cond.getPokemon() and cond.getPokemon().getStats().get(4).getIv() lt 31}"> value="${cond.getPokemon().getStats().get(4).getIv()}" </c:when>
						   						<c:otherwise> value="31"</c:otherwise>
						   					</c:choose>
					   						/>
					   					</div>
					   				</div>
					   				<div class="invisible offev">
					   					<div class="display-ev-iv">
					   						<h6>Atk/SpA</h6>
					   						<label for="atk-${loopvar.index+1}">EV:</label>
					   						<input class="short-number" type="number" name="atk-${loopvar.index+1}" min="0" max="252"
					   						<c:choose>
						   						<c:when test="${not empty cond.getPokemon() and cond.getPokemon().getStats().get(1).getEffort() gt 0}"> value="${cond.getPokemon().getStats().get(1).getEffort()}" </c:when>
						   						<c:when test="${not empty cond.getPokemon() and cond.getPokemon().getStats().get(3).getEffort() gt 0}"> value="${cond.getPokemon().getStats().get(3).getEffort()}" </c:when>
						   						<c:otherwise> value="0"</c:otherwise>
						   					</c:choose>
					   						/>
					   						<label for="atk-iv-${loopvar.index+1}">IV:</label>
					   						<input class="short-number" type="number" name="atk-iv-${loopvar.index+1}" min="0" max="31"
					   						<c:choose>
						   						<c:when test="${not empty cond.getPokemon() and cond.getPokemon().getStats().get(1).getIv() lt 31}"> value="${cond.getPokemon().getStats().get(1).getIv()}" </c:when>
						   						<c:when test="${not empty cond.getPokemon() and cond.getPokemon().getStats().get(3).getIv() lt 31}"> value="${cond.getPokemon().getStats().get(3).getIv()}" </c:when>
						   						<c:otherwise> value="31"</c:otherwise>
						   					</c:choose>
					   						/>
					   					</div>
					   				</div>
					   				<div class="speedev">
					   					<div class="display-ev-iv">
					   						<h6>Speed</h6>
					   						<label for="speed-${loopvar.index+1}">EV:</label>
					   						<input class="short-number" type="number" name="speed-${loopvar.index+1}" min="0" max="252"
					   						<c:choose>
						   						<c:when test="${not empty cond.getPokemon()}"> value="${cond.getPokemon().getStats().get(5).getEffort()}" </c:when>
						   						<c:otherwise> value="0"</c:otherwise>
						   					</c:choose>
					   						/>
					   						<label for="speed-iv-${loopvar.index+1}">IV:</label>
					   						<input class="short-number" type="number" name="speed-iv-${loopvar.index+1}" min="0" max="31"
					   						<c:choose>
						   						<c:when test="${not empty cond.getPokemon()}"> value="${cond.getPokemon().getStats().get(5).getIv()}" </c:when>
						   						<c:otherwise> value="31"</c:otherwise>
						   					</c:choose>
					   						/>
					   					</div>
					   				</div>
					   			</div>
					   			<div class="cell">	
					   				<div class="boost-selector">
						   				<label for="boost-you-${loopvar.index+1}">Your boosts:</label>
						   				<select name="boost-you-${loopvar.index+1}">
								   			<c:forEach var="boost" items="${boosts}">
								   				<option value="${boost}"<c:if test="${boost eq cond.getRawBoost()}"> selected</c:if>><c:out value="${boost}"/></option>
								   			</c:forEach>
								   		</select>
									</div>
								   	<div class="boost-selector">
						   				<label for="boost-opp-${loopvar.index+1}">Opp boosts:</label>
						   				<select name="boost-opp-${loopvar.index+1}">
								   			<c:forEach var="boost" items="${boosts}">
								   				<option value="${boost}"<c:if test="${boost eq cond.getRawOppBoost()}"> selected</c:if>><c:out value="${boost}"/></option>
									   		</c:forEach>
									   	</select>
									</div>
								</div>
				   				<img id="expand-button" class='add-button' src="/assets/plus.png" alt="plus button" onClick="expandRow(this)"/>
				   				<div class="cell">
				   					<div>
				   						<h4>Field Effects</h4>
				   						<label for="terrain-${loopvar.index+1}">Terrain:</label>
				   						<select name="terrain-${loopvar.index+1}">
				   							<c:forEach var="terrain" items="${terrains}">
				   								<option value="${terrain}" <c:if test="${terrain eq cond.getTerrain()}">selected</c:if>><c:out value="${terrain}"/></option>
				   							</c:forEach>
				   						</select>
				   						<label for="weather-${loopvar.index+1}">Weather:</label>
				   						<select name="weather-${loopvar.index+1}">
				   							<c:forEach var="weather" items="${weathers}">
				   								<option value="${weather}" <c:if test="${weather eq cond.getWeather()}"> selected</c:if>><c:out value="${weather}"/></option>
				   							</c:forEach>
				   						</select>
				   						<div class="btn-group btn-group-sm" role="group" aria-label="Basic checkbox toggle button group">
										  <input type="checkbox" class="btn-check" id="screen-${loopvar.index+1}" name="screen-${loopvar.index+1}" <c:if test="${cond.isScreens()}">checked</c:if> autocomplete="off">
										  <label class="btn btn-outline-secondary" for="screen-${loopvar.index+1}">Screens</label>
			
										  <input type="checkbox" class="btn-check" id="your-tailwind-${loopvar.index+1}" <c:if test="${cond.isYourTw()}">checked</c:if> name="your-tailwind-${loopvar.index }" autocomplete="off">
										  <label class="btn btn-outline-secondary" for="your-tailwind-${loopvar.index+1}">Tailwind (You)</label>
										  
										  <input type="checkbox" class="btn-check" id="foe-tailwind-${loopvar.index+1}" <c:if test="${cond.isFoeTw()}">checked</c:if> name="foe-tailwind-${loopvar.index }" autocomplete="off">
										  <label class="btn btn-outline-secondary" for="foe-tailwind-${loopvar.index+1}">Tailwind (Foe)</label>
										</div>
										<div class="btn-group btn-group-sm" role="group" aria-label="Basic checkbox toggle button group">
										  <input type="checkbox" class="btn-check" <c:if test="${cond.isHelpingHand()}">checked</c:if> id="hh-${loopvar.index+1}" autocomplete="off">
										  <label class="btn btn-outline-secondary" for="hh-${loopvar.index+1}">Helping Hand</label>
										  <input type="checkbox" class="btn-check" id="flower-gift-${loopvar.index+1}" <c:if test="${cond.isFlowerGift()}">checked</c:if> autocomplete="off">
										  <label class="btn btn-outline-secondary" for="flower-gift-${loopvar.index+1}">Flower Gift</label>
										  
										  <input type="checkbox" class="btn-check" id="power-spot-${loopvar.index+1}" <c:if test="${cond.isPowerSpot()}">checked</c:if> autocomplete="off">
										  <label class="btn btn-outline-secondary" for="power-spot-${loopvar.index+1}">Power Spot</label>
										  
										  <input type="checkbox" class="btn-check" id="battery-${loopvar.index+1}" <c:if test="${cond.isBattery()}">checked</c:if> autocomplete="off">
										  <label class="btn btn-outline-secondary" for="battery-${loopvar.index+1}">Battery</label>
										</div>
			
				   					</div>
				   				</div>
				   				<img class="delete-button" src="/assets/garbage.png" alt="delete button" onClick="deleteRow(this)"/>
				   			</div>
				   		</c:forEach>
	   				</c:when>
	   				<c:otherwise>
			   			<div id="condition-1" class="cells">
			   				<div class="cell">
				   				<select name="condition-1" onChange= "changeCondition(this)">
				   					<c:forEach var="condition" items="${conditions}">
				   						<option value="${condition}"><c:out value="${condition }"/></option>
				   					</c:forEach>
				   				</select>
				   			</div>
				   			<div class="cell">
					   			<div class="opp-pokemon-cell">
						   			<img class="opp-pokemon" alt=""/>
						   			<img class="opp-item" alt=""/>
					   			</div>
					   			<div class="type-pokemon"></div>
				   				<div class="input-row">
					   				<label for="pokemon-1">Opp. Pokémon:</label>
					   				<div class="form-input-with-autocomplete">
					   					<input type="text" class="medium-text" name="pokemon-1" oninput="autoComplete(this)">
				   					   	<div class="item-autocomplete"></div>
			   						</div>
			   					</div>
				   			</div>
				   			<div class="cell">
				   				<div class="input-row">
				   					<label for="ability-1">Opp. Ability:</label>
				   					<select name="ability-1" class="opp-ability">
				   					</select>
				   				</div>
				   				<div class="input-row">
				   					<label for="item-1">Opp. Item:</label>
				   				   	<div class="form-input-with-autocomplete">	
				   						<input type="text" class="medium-text" name="item-1" oninput="autoComplete(this)">
				   					   	<div class="item-autocomplete"></div>
			   						</div>	   				
			   					</div>
				   				<div class="input-row">
				   					<label for="level-1">Opp. Level:</label>
				   					<input class="short-number" type="number" name="level-1" min="1" max="100" value="100"/>
				   				</div>
				   				<div class="input-row">
				   					<label for="nature-1">Nature:</label>
					   				<select name="nature-1">
				   						<c:forEach var="nature" items="${natures}" varStatus="loop">
				   							<option value="${nature.identifier}" <c:if test="${loop.first}">selected</c:if>><c:out value="${nature.identifier}"/></option>
				   						</c:forEach>
				   					</select>
			   					</div>
			   					<div class="input-row">
			   						<label for="hp-percentage-1">HP:</label>
			   						<input class="short-number" type="text" name="hp-percentage-1" value="100"/>
			   					</div>
			   				</div>
			   				<div class="cell">
			   					<label for="move-1">Move:</label>
				   			   	<div class="form-input-with-autocomplete">	
				   					<input type="text" class="medium-text" name="move-1" oninput="autoComplete(this)">
				   				   	<div class="item-autocomplete"></div>
			   					</div>
			   					<div class="btn-group btn-group-sm" role="group" aria-label="Basic checkbox toggle button group">
				   					<input type="checkbox" class="btn-check" id="crit-1" name="crit-1" autocomplete="off">
									<label class="btn btn-outline-secondary" for="crit-1">Crit</label>
								</div>
								<label for="status">Status:</label>
				   				<select name="status-1">
				   				<c:forEach var="stat" items="${status}" varStatus="loop">
				   					<option value="${stat}" <c:if test="${loop.first}">selected</c:if>><c:out value="${stat}"/></option>
				   				</c:forEach>
				   			</select>
			   				</div>
			   				<div class="cell">		
				   				<div class="defev invisible">
				   					<div class="display-ev-iv">
				   						<h6>HP</h6>
					   					<label for="hp-1">EV:</label>
					   					<input class="short-number" type="number" name="hp-1" min="0" max="252" value="0"/>
					   					<label for="hp-iv-1">IV:</label>
					   					<input class="short-number" type="number" name="hp-iv-1" min="0" max="31" value="31"/>
				   					</div>
				
				   					<div class="display-ev-iv">
				   						<h6>Def/Spd</h6>
				   						<label for="def-1">EV:</label>
				   						<input class="short-number" type="number" name="def-1" min="0" max="252" value="0"/>
				   						<label for="def-iv-1">IV:</label>
				   						<input class="short-number" type="number" name="def-iv-1" min="0" max="31" value="31"/>
				   					</div>
				   				</div>
				   				<div class="invisible offev">
				   					<div class="display-ev-iv">
				   						<h6>Atk/SpA</h6>
				   						<label for="atk-1">EV:</label>
				   						<input class="short-number" type="number" name="atk-1" min="0" max="252" value="0"/>
				   						<label for="atk-iv-1">IV:</label>
				   						<input class="short-number" type="number" name="atk-iv-1" min="0" max="31" value="31"/>
				   					</div>
				   				</div>
				   				<div class="speedev">
				   					<div class="display-ev-iv">
				   						<h6>Speed</h6>
				   						<label for="speed-1">EV:</label>
				   						<input class="short-number" type="number" name="speed-1" min="0" max="252" value="0"/>
				   						<label for="speed-iv-1">IV:</label>
				   						<input class="short-number" type="number" name="speed-iv-1" min="0" max="31" value="31"/>
				   					</div>
				   				</div>
				   			</div>
				   			<div class="cell">	
				   				<div class="boost-selector">
					   				<label for="boost-you-1">Your boosts:</label>
					   				<select name="boost-you-1">
							   			<c:forEach var="boost" items="${boosts}">
							   				<option value="${boost}"<c:if test="${boost eq '0'}"> selected</c:if>><c:out value="${boost}"/></option>
							   			</c:forEach>
							   		</select>
								</div>
							   	<div class="boost-selector">
					   				<label for="boost-opp-1">Opp boosts:</label>
					   				<select name="boost-opp-1">
							   			<c:forEach var="boost" items="${boosts}">
							   				<option value="${boost}"<c:if test="${boost eq '0'}"> selected</c:if>><c:out value="${boost}"/></option>
								   		</c:forEach>
								   	</select>
								</div>
							</div>
			   				<img id="expand-button" class='add-button' src="/assets/plus.png" alt="plus button" onClick="expandRow(this)"/>
			   				<div class="cell">
			   					<div>
			   						<h4>Field Effects</h4>
			   						<label for="terrain-1">Terrain:</label>
			   						<select name="terrain-1">
			   							<c:forEach var="terrain" items="${terrains}">
			   								<option value="${terrain}"><c:out value="${terrain}"/></option>
			   							</c:forEach>
			   						</select>
			   						<label for="weather-1">Weather:</label>
			   						
			   						<select name="weather-1">
			   							<c:forEach var="weather" items="${weathers}">
			   								<option value="${weather}"><c:out value="${weather}"/></option>
			   							</c:forEach>
			   						</select>
			   						<div class="btn-group btn-group-sm" role="group" aria-label="Basic checkbox toggle button group">
									  <input type="checkbox" class="btn-check" id="screen-1" name="screen-1" autocomplete="off">
									  <label class="btn btn-outline-secondary" for="screen-1">Screens</label>
		
									  <input type="checkbox" class="btn-check" id="your-tailwind-1" name="your-tailwind-1" autocomplete="off">
									  <label class="btn btn-outline-secondary" for="your-tailwind-1">Tailwind (You)</label>
									  
									  <input type="checkbox" class="btn-check" id="foe-tailwind-1" name="foe-tailwind-1" autocomplete="off">
									  <label class="btn btn-outline-secondary" for="foe-tailwind-1">Tailwind (Foe)</label>				
									</div>
									<div class="btn-group btn-group-sm" role="group" aria-label="Basic checkbox toggle button group">
									  <input type="checkbox" class="btn-check" id="hh-1" autocomplete="off">
									  <label class="btn btn-outline-secondary" for="hh-1">Helping Hand</label>
									
									  <input type="checkbox" class="btn-check" id="flower-gift-1" autocomplete="off">
									  <label class="btn btn-outline-secondary" for="flower-gift-1">Flower Gift</label>
									  
									  <input type="checkbox" class="btn-check" id="power-spot-1" autocomplete="off">
									  <label class="btn btn-outline-secondary" for="power-spot-1">Power Spot</label>
									  
									  <input type="checkbox" class="btn-check" id="battery-1" autocomplete="off">
									  <label class="btn btn-outline-secondary" for="battery-1">Battery</label>
									</div>
		
			   					</div>
			   				</div>
			   				<img class="delete-button" src="/assets/garbage.png" alt="delete button" onClick="deleteRow(this)"/>
			   			</div>
			   			</c:otherwise>
		   			</c:choose>
	   			<img id="add-button" class='add-button' src="/assets/plus.png" alt="plus button" onClick="addRow()"/>
	   		</div>
	   	</div>
	   	<div class="centerizer">
	   		<button class="btn btn-primary submit-button">Calculate</button>
   		</div>
   	</form>
   	<script src="js/formManage.js"></script>
</body>
</html>

