package edu.upc.eetac.dsa.mfilali.libro.api.model;

import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.mfilali.libro.api.LibrosResource;
import edu.upc.eetac.dsa.mfilali.libro.api.LibrosRootAPIResource;
import edu.upc.eetac.dsa.mfilali.libro.api.Mediatype;

public class LibrosRootAPI 	{
	@InjectLinks({
	@InjectLink(resource = LibrosRootAPIResource.class, style = Style.ABSOLUTE, rel = "self bookmark home", title = "Beeter Root API", method = "getRootAPI"),
	@InjectLink(resource = LibrosResource.class, style = Style.ABSOLUTE, rel = "stings", title = "Latest stings", type = Mediatype.LIBROS_API_BOOK_COLLECTION),
	@InjectLink(resource = LibrosResource.class, style = Style.ABSOLUTE, rel = "create-stings", title = "Latest stings", type = Mediatype.LIBROS_API_BOOK) })

private List<Link> links;

public List<Link> getLinks() {
	return links;
}

public void setLinks(List<Link> links) {
	this.links = links;
}
}
