$(document).ready(function() {
	var store = "http://143.93.114.137/sparqlproxy/SPARQL";
	var query = 'SELECT * WHERE { ?s ?p ?o. } LIMIT 10';
	query = encodeURIComponent(query);
	$.ajax({
		async: false,
		dataType: 'json',
		data: {
			repo: 'annedb',
			query: query,
			format: 'json'
		},
		url: store,
		error: function(jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
		},
		success: function(response) {
			var bindings = response.results.bindings;
			console.log(bindings);
		}
	});
});


$(document).ready(function() {
	var store = "https://dbpedia.org/sparql";
	var query = 'SELECT * WHERE { ?s ?p ?o. FILTER (?s=<http://dbpedia.org/resource/Mainz>) }';
	$.ajax({
		async: false,
		data: {
			"default-graph-uri": "http://dbpedia.org",
			"query": query,
			"format": 'application/sparql-results+json'
		},
		url: store,
		error: function(jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
		},
		success: function(response) {
			var bindings = response.results.bindings;
			console.log(bindings);
		}
	});
});

$(document).ready(function() {
	var store = "http://api.geonames.org/get";
	$.ajax({
		async: false,
		url: store,
		data: {
			geonameId: 6554818,
			username: "i3obm"
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
		},
		success: function(response) {
			console.log(response);
			console.log(response.getElementsByTagName("name")[0].innerHTML);
		}
	});
});

$(document).ready(function() {
	var store = "http://143.93.114.137/sparqlproxy/GND";
	var uri = 'http://d-nb.info/gnd/1063654211';
	$.ajax({
		async: false,
		dataType: 'json',
		data: {
			uri: uri
		},
		url: store,
		error: function(jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
		},
		success: function(response) {
			var object = response[uri];
			console.log(object["http://d-nb.info/standards/elementset/gnd#preferredNameForTheCorporateBody"][0].value);
		}
	});
});