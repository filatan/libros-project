package edu.upc.eetac.dsa.mfilali.libro.api.model;

import java.sql.Date;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.mfilali.libro.api.LibrosResource;
import edu.upc.eetac.dsa.mfilali.libro.api.Mediatype;

public class Libro {

	@InjectLinks({
			
			@InjectLink(resource = LibrosResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "Book", type = Mediatype.LIBROS_API_BOOK, method = "getBook", bindings = @Binding(name = "libroid", value = "${instance.id}")) })
	private List<Link> links;
	private int libroid;
	private String titulo;
	private String autor;
	private String lengua;
	private String edicion;
	private Date fechaed;
	private Date fechaimp;

	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public int getLibroid() {
		return libroid;
	}
	public void setLibroid(int libroid) {
		this.libroid = libroid;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getLengua() {
		return lengua;
	}
	public void setLengua(String lengua) {
		this.lengua = lengua;
	}
	public String getEdicion() {
		return edicion;
	}
	public void setEdicion(String edicion) {
		this.edicion = edicion;
	}
	public Date getFechaed() {
		return fechaed;
	}
	public void setFechaed(Date date) {
		this.fechaed = date;
	}
	public Date getFechaimp() {
		return fechaimp;
	}
	public void setFechaimp(Date fechaimp) {
		this.fechaimp = fechaimp;
	}
	
	
}
