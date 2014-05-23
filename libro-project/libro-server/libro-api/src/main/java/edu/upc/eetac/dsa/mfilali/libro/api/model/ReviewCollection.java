package edu.upc.eetac.dsa.mfilali.libro.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.mfilali.libro.api.LibrosResource;
import edu.upc.eetac.dsa.mfilali.libro.api.Mediatype;

public class ReviewCollection {
	@InjectLinks({ @InjectLink(resource = LibrosResource.class, style = Style.ABSOLUTE, rel = "create-book", title = "Create Book", type = Mediatype.LIBROS_API_BOOK_COLLECTION), })
	private List<Link> links;
	private List<Review> reviews;

	public ReviewCollection() {
		super();
		reviews = new ArrayList<>();
	}

	public List<Link> getLinks() {
		return links;
	}



	public void setLinks(List<Link> links) {
		this.links = links;
	}

	



	public List<Review> getReviews() {
		return reviews;
	}



	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}



	
	public void addReview(Review r) {
		reviews.add(r);
	}

	
}
