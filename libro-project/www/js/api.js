var LIBRO_API_HOME="http://localhost:8080/libros-api";

function Link(rel, linkheader){
	this.rel = rel;
	this.href = decodeURIComponent(linkheader.find(rel).template().template);
	this.type = linkheader.find(rel).attr('type');
	this.title = linkheader.find(rel).attr('title');
}

function buildLinks(linkheaders){
	var links = {};
	$.each(linkheaders, function(i,linkheader){
		var linkhdr = $.linkheaders(linkheader);
		var rels = linkhdr.find().attr('rel').split(' ');
		$.each(rels, function(key,rel){
			var link = new Link(rel, linkhdr);
			links[rel] = link;
		});
	});

	return links;
}

function Libro(libro){
	this.titulo = libro.titulo;
	this.libroid = libro.id;
	this.autor = libro.autor;
	this.fechaed = libro.fechaed;
	this.fechaimp = libro.fechaimp;
	this.edicion = libro.edicion;
	this.lengua = libro.lengua;
	this.links = buildLinks(sting.links);

	var instance = this;
	this.getLink = function(rel){
		return this.links[rel];
	}
}

function LibroCollection(libroCollection){
	
	this.libros = libroCollection.libros;

	this.links = buildLinks(libroCollection.links);
	var instance = this;
	this.getLink = function(rel){
		return this.links[rel];
	}
}

function RootAPI(rootAPI){
	this.links = buildLinks(rootAPI.links);
	var instance = this;
	this.getLink = function(rel){
		return this.links[rel];
	}
}


function loadRootAPI(success){
	$.ajax({
		url : LIBRO_API_HOME,
		type : 'GET',
		crossDomain : true,
		dataType: 'json'
	})
	.done(function (data, status, jqxhr) {
		var response = $.parseJSON(jqxhr.responseText);
		var rootAPI = new RootAPI(response);
    	success(rootAPI);
	})
    .fail(function (jqXHR, textStatus) {
		console.log(textStatus);
	});
	
}

function getLibros(url, success){
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType: 'json'
	})
	.done(function (data, status, jqxhr) {
		var response = $.parseJSON(jqxhr.responseText);
		var libroCollection = new LibroCollection(response);
		//success(response.stings);
		success(libroCollection);
	})
    .fail(function (jqXHR, textStatus) {
		console.log(textStatus);
	});
}

function createLibro(url, type, libro, success){
	$.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		contentType: type, 
		data: sting
	})
	.done(function (data, status, jqxhr) {
		var sting = $.parseJSON(jqxhr.responseText);
		success(sting);
	})
    .fail(function (jqXHR, textStatus) {
		console.log(textStatus);
	});
}

function getLibro(url, success){
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType: 'json'
	}).done(function (data, status, jqxhr) {
		var sting = $.parseJSON(jqxhr.responseText);
		success(libro);
	})
    .fail(function (jqXHR, textStatus) {
		console.log(textStatus);
	});
}

function updateLibro(url, type, sting, success){
	$.ajax({
		url : url,
		type : 'PUT',
		crossDomain : true,
		contentType: type, 
		data: libro
	})
	.done(function (data, status, jqxhr) {
		var libro = $.parseJSON(jqxhr.responseText);
		success(libro);
		//console.log("SUCCESS ON PUT!!");
	})
    .fail(function (jqXHR, textStatus) {
		console.log(textStatus);
	});
}

function deleteLibro(url, success){
	$.ajax({
		url : url,
		type : 'DELETE',
		crossDomain : true
	})
	.done(function (data, status, jqxhr) {
		//var sting = $.parseJSON(jqxhr.responseText);
		success();
	})
    .fail(function (jqXHR, textStatus) {
		console.log(textStatus);
	});
}