package edu.upc.eetac.dsa.mfilali.libro.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
 

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.mfilali.libro.api.model.LibrosRootAPI;
 
@Path("/")
public class LibrosRootAPIResource {
	private boolean administrador;
	@Context
	SecurityContext security;
	
	@GET
	public LibrosRootAPI getRootAPI() {
		System.out.println(security.getUserPrincipal().getName());
		setAdministrator(security.isUserInRole("administrador"));
		LibrosRootAPI api = new LibrosRootAPI();
		return api;
	}
	public boolean isAdministrator() {
		return administrador;
	}

	public void setAdministrator(boolean administrator) {
		this.administrador = administrator;
	}
}



