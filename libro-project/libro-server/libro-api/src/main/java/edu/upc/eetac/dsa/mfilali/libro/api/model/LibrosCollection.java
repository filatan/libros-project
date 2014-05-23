package edu.upc.eetac.dsa.mfilali.libro.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.mfilali.libro.api.LibrosResource;
import edu.upc.eetac.dsa.mfilali.libro.api.Mediatype;







public class LibrosCollection {
	@InjectLinks({
			@InjectLink(resource = LibrosResource.class, style = Style.ABSOLUTE, rel = "create-book", title = "Create Book", type = Mediatype.LIBROS_API_BOOK_COLLECTION),
			})
	private List<Link> links;
	private List<Libro> libros;


	public LibrosCollection() {
		super();
		libros = new ArrayList<>();
	}

	public List<Libro> getLibros() {
		return libros;
	}

	public void setLibros(List<Libro> books) {
		this.libros = books;
	}

	public void addLibro(Libro book) {
		libros.add(book);
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
}
