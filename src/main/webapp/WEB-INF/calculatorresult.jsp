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
    <link rel="stylesheet" href="/css/calculatorresult.css"> <!-- change to match your file/naming structure -->
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<h1>Calculation Results</h1>
	<div class = "final-display">
		<div class="pokemon-info">
			<div class="left-side">
				<div class="sprite-frame">
					<img id="pokemon-img" src="https://play.pokemonshowdown.com/sprites/xyani/${pokemon.getIdentifierNoSpace()}.gif">
					<img id="item-img" src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/${pokemon.item}.png">
				</div>
				<h2><c:out value="${pokemon.getIdentifierCleaned()}"/></h2>
				<p>Level: <c:out value="${pokemon.level}"/></p>
				<p>Ability: <c:out value="${pokemon.ability.getIdentifierCleaned()}"/></p>
			</div>
			<div class="stattable">
				<table>
					<thead>
						<tr>
							<th>Stat</th>
							<th>IVs</th>
							<th>EVs</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${calcedstat}" varStatus="loop">
							<tr>
							<c:choose>
								<c:when test="${not loop.last}">
									<td><c:out value="${result[1]}:"/></td>
									<td>31</td>
									<td><c:out value="${result[0]}"/></td>
								</c:when>
								<c:otherwise>
									<td>Remaining:</td>
									<td>31</td>
									<td><c:out value="${result[0]}"/></td>
								</c:otherwise>
							</c:choose>
							</tr>
						</c:forEach>
					</tbody>	
				</table>
				<p>Nature: <c:out value="${pokemon.nature.getIdentifierCleaned()}"/></p>
			</div>
		</div>
	
		<div class="condition-display">
			<div>
				<h2>Conditions that were met:</h2>
				<c:forEach var="condition" items="${goodCondition}">
					<div class="condition-ok condition">
						<img src="https://www.smogon.com/forums//media/minisprites/${condition.pokemon.identifier}.png">
						<p><c:out value="${condition.summary}"/></p>
					</div>
				</c:forEach>
			</div>
			<div>
				<h2>Conditions that could not be met:</h2>
				<c:forEach var="condition" items="${badCondition}">
					<div class="condition-ng condition">
						<img src="https://www.smogon.com/forums//media/minisprites/${condition.pokemon.identifier}.png">
						<p><c:out value="${condition.summary}"/></p>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<div class="pokepaste">
		<p><c:out value="${pokemon.getIdentifierCleaned()}"/> <c:if test="${not empty pokemon.item}">@ <c:out value="${pokemon.getItemCleaned()}"/></c:if></p>
		<p>Ability: <c:out value="${pokemon.ability.getIdentifierCleaned()}"/></p>
		<c:set var="test" value="${false}"/>
		<p>EVs: <c:forEach var="result" items="${calcedstat}" varStatus="owo">
			<c:if test="${result[0] gt 0 and not owo.last}">
				<c:if test="${test}">/</c:if>
				<c:out value="${result[0]} ${result[2]}"/>
				<c:set var="test" value="${true}"/>
			</c:if>
			</c:forEach>
		</p>
		<p><c:out value="${pokemon.nature.getIdentifierCleaned()}"/> Nature</p>  
		<p>- </p>
		<p>- </p>
		<p>- </p>
		<p>- </p>
	</div>
	<c:if test="${not empty suggestedEVarr}">
		<div class="pokepaste">
			<p><c:out value="${pokemon.getIdentifierCleaned()}"/> <c:if test="${not empty pokemon.item}">@ <c:out value="${pokemon.getItemCleaned()}"/></c:if></p>
			<p>Ability: <c:out value="${pokemon.ability.getIdentifierCleaned()}"/></p>
			<c:set var="test" value="${false}"/>
			<p>EVs: <c:forEach var="result" items="${suggestionEV}" varStatus="mish">
				<c:if test="${result gt 0 and (not mish.last)}">
					<c:if test="${test}">/</c:if>
					<c:out value="${result} "/>
					<c:set var="test" value="${true}"/>
				</c:if>
				</c:forEach>
			</p>
			<p><c:out value="${suggestedNature.getIdentifierCleaned()}"/> Nature</p>  
			<p>- </p>
			<p>- </p>
			<p>- </p>
			<p>- </p>
		</div>
	</c:if>
	
	<a class="btn btn-secondary center-button" href="/">Return</a>
</body>
</html>

