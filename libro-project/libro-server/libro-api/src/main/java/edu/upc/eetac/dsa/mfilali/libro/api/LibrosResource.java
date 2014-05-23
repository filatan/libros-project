package edu.upc.eetac.dsa.mfilali.libro.api;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;





import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.mfilali.libro.api.DataSourceSPA;
import edu.upc.eetac.dsa.mfilali.libro.api.model.Libro;
import edu.upc.eetac.dsa.mfilali.libro.api.model.LibrosCollection;
import edu.upc.eetac.dsa.mfilali.libro.api.model.Review;
import edu.upc.eetac.dsa.mfilali.libro.api.model.ReviewCollection;


@Path("/libro")
public class LibrosResource {
	@Context
	SecurityContext security;
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	private boolean administrator;
	@GET
	@Produces(Mediatype.LIBROS_API_BOOK_COLLECTION)
	public LibrosCollection getlibros(/* @QueryParam("length") int length */) {
		LibrosCollection books = new LibrosCollection();

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {

			stmt = conn.prepareStatement(buildGetLibrosQuery());
			/*
			 * length = (length <= 0) ? 5 : length; stmt.setInt(1, length);
			 */
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Libro book = new Libro();
				book.setLibroid(rs.getInt("libroid"));
				book.setTitulo(rs.getString("titulo"));
				book.setLengua(rs.getString("lengua"));
				book.setAutor(rs.getString("autor"));
				book.setEdicion(rs.getString("edicion"));
				// book.setFechaed(rs.getDate("fechaed"));
				// book.setFechaimp(rs.getDate("fechaimp"));

				books.addLibro(book);
			}

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return books;
	}

	private String buildGetLibrosQuery() {

		return "select * from libros";

	}

	/*
	 * private Libro getlibrofromDatabase(String libroid){ Libro book = new
	 * Libro(); Connection conn = null; try { conn = ds.getConnection(); } catch
	 * (SQLException e) { throw new
	 * ServerErrorException("Could not connect to the database",
	 * Response.Status.SERVICE_UNAVAILABLE); }
	 * 
	 * PreparedStatement stmt = null; try { stmt =
	 * conn.prepareStatement(buildGetStingByIdQuery()); stmt.setInt(1,
	 * Integer.valueOf(libroid)); ResultSet rs = stmt.executeQuery(); if
	 * (rs.next()) {
	 * 
	 * book.setLibroid(rs.getInt("libroid"));
	 * book.setTitulo(rs.getString("titulo"));
	 * book.setLengua(rs.getString("lengua"));
	 * book.setAutor(rs.getString("autor"));
	 * book.setEdicion(rs.getString("edicion")); } else { throw new
	 * NotFoundException("There's no sting with stingid=" + libroid); }
	 * 
	 * } catch (SQLException e) { throw new ServerErrorException(e.getMessage(),
	 * Response.Status.INTERNAL_SERVER_ERROR); } finally { try { if (stmt !=
	 * null) stmt.close(); conn.close(); } catch (SQLException e) { } }
	 * 
	 * return book; }
	 * 
	 * }
	 */

	// para el GET de un libro con bookid (2)
	@GET
	@Path("/{bookid}")
	@Produces(Mediatype.LIBROS_API_BOOK)
	public Libro getBookid(@PathParam("bookid") int bookid) {
		Connection conn = null;
		Libro book = new Libro();
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			String sql = buildQueryGetBookByBookid();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, Integer.valueOf(bookid));

			ResultSet rs = stmt.executeQuery();
			
				while (rs.next()) {
					book.setLibroid(rs.getInt("libroid"));
					book.setAutor(rs.getString("autor"));
					book.setTitulo(rs.getString("titulo"));
					book.setEdicion(rs.getString("edicion"));
					book.setLengua(rs.getString("lengua"));
					// book.setEditorial(rs.getString("editorial"));
					// book.setFecha(fecha);//¿cómo indico variable fecha (DATE)
					// en
					// mysql en java?
				}
		}
		 catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// Haya ido bien o haya ido mal cierra las conexiones
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
				throw new ServerErrorException(e.getMessage(),
						Response.Status.INTERNAL_SERVER_ERROR);
			}
		}
		return book;
	}

	// (2)GET de un libro con identificador bookid
	private String buildQueryGetBookByBookid() {
		return "select * from libros where libroid=?";
	}

	@DELETE
	@Path("/{libroid}")
	public void deletelibro(@PathParam("libroid") String libroid) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			String sql = buildDeletelibro();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, Integer.valueOf(libroid));

			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("There's no book with libroid="
						+ libroid);
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	private String buildDeletelibro() {
		return "delete from libros where libroid=?";
	}
	
	@POST
	// necesita que lo que se le envie este en este formato de datos
	@Consumes(Mediatype.LIBROS_API_BOOK)
	@Produces(Mediatype.LIBROS_API_BOOK)
	public Libro createBook(Libro book) {
		//validateLibro(book);
		//setAdministrator(security.isUserInRole("administrador"));
		//System.out.printf("%b \n", security.isUserInRole("administrador"));
		// Comprobamos que el usuario que vaya a crear la ficha de libro sea
		// ADMIN
		//if (!security.isUserInRole("administrador")) {
			//throw new ForbiddenException("You are not an admin.");
		//}
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			String sql = buildInsertLibro();
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);// se
																				// genera
																				// la
																				// query;
																				// con
																				// el
																				// RETURN_GENERATED_KEYS
																				// te
																				// devuelde
																				// el
																				// id

			//stmt.setString(1, sting.getUsername());
			//stmt.setString(1, security.getUserPrincipal().getName());
			stmt.setString(1, book.getTitulo());
			stmt.setString(2, book.getAutor());
			stmt.setString(3, book.getLengua());
			stmt.setString(4, book.getEdicion());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				String libroid = rs.getString(1);

				book = getLibroFromDatabase( libroid);
			} else {
				// Something has failed...
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
				throw new ServerErrorException(e.getMessage(),
						Response.Status.INTERNAL_SERVER_ERROR);
			}
		}

		return book;
	}

	private String buildInsertLibro() {
		return "insert into libros (titulo, autor, lengua, edicion) value (?, ?, ?, ?)";
	}
	private Libro getLibroFromDatabase(String libroid) {
		Libro book = new Libro();

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(buildQueryGetBookByBookid());
			stmt.setInt(1, Integer.valueOf(libroid));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				book.setLibroid(rs.getInt("libroid"));
				book.setAutor(rs.getString("autor"));
				book.setTitulo(rs.getString("titulo"));
				book.setEdicion(rs.getString("edicion"));
				book.setLengua(rs.getString("lengua"));
			} else {
				throw new NotFoundException("There's no sting with stingid="
						+ libroid);
			}

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return book;
	}
	/*private void validateLibro(Libro book) {
		if (book.getSubject() == null)
			throw new BadRequestException("Subject can't be null.");
		if (sting.getContent() == null)
			throw new BadRequestException("Content can't be null.");
		if (sting.getSubject().length() > 100)
			throw new BadRequestException(
					"Subject can't be greater than 100 characters.");
		if (sting.getContent().length() > 500)
			throw new BadRequestException(
					"Content can't be greater than 500 characters.");
	}*/
	
		@GET
		@Path("/{libroid}/reviews")
		@Produces(Mediatype.LIBROS_API_REVIEW_COLLECTION)
		public ReviewCollection getReviews(@PathParam("libroid") String libroid) {
			ReviewCollection reviews = new ReviewCollection();

			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}

			PreparedStatement stmt = null;
			try {

				stmt = conn.prepareStatement((buildQueryGetReviewByBookid()));
				stmt.setInt(1, Integer.valueOf(libroid));
				
				/*
				 * length = (length <= 0) ? 5 : length; stmt.setInt(1, length);
				 */
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					Review r = new Review();
					r.setReviewid(rs.getInt("reviewid"));
					r.setLibroid(rs.getInt("libroid"));
					
					r.setUsername(rs.getString("username"));
					r.setReview(rs.getString("review"));
					r.setFecha(rs.getDate("fecha"));

					reviews.addReview(r);
				}

			} catch (SQLException e) {
				throw new ServerErrorException(e.getMessage(),
						Response.Status.INTERNAL_SERVER_ERROR);
			} finally {
				try {
					if (stmt != null)
						stmt.close();
					conn.close();
				} catch (SQLException e) {
				}
			}

			return reviews;
		}


		// (2)GET de un libro con identificador bookid
		private String buildQueryGetReviewByBookid() {
			return "select * from reviews where libroid=?";
		}
		
		@DELETE
		@Path("/{libroid}/reviews/{reviewid}")
		public void deletelibro(@PathParam("libroid") String libroid , @PathParam("reviewid") String reviewid) {
			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}

			PreparedStatement stmt = null;
			try {
				String sql = buildDeletereview();
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, Integer.valueOf(reviewid));
				stmt.setInt(2, Integer.valueOf(libroid));

				int rows = stmt.executeUpdate();
				if (rows == 0)
					throw new NotFoundException("There's no review with reviewid="
							+ reviewid );
			} catch (SQLException e) {
				throw new ServerErrorException(e.getMessage(),
						Response.Status.INTERNAL_SERVER_ERROR);
			} finally {
				try {
					if (stmt != null)
						stmt.close();
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

		private String buildDeletereview() {
			return "delete from reviews where reviewid = ? and libroid=?";
		}
		
		
		@POST
		@Path("/reviews")
		// necesita que lo que se le envie este en este formato de datos
		@Consumes(Mediatype.LIBROS_API_REVIEW)
		@Produces(Mediatype.LIBROS_API_REVIEW)
		public Review createReview(Review r) {
			//validateLibro(book);
			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}

			PreparedStatement stmt = null;
			try {
				String sql = buildInsertReview();
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);// se
																					// genera
																					// la
																					// query;
																					// con
																					// el
																					// RETURN_GENERATED_KEYS
																					// te
																					// devuelde
																					// el
																					// id

				//stmt.setString(1, sting.getUsername());
				//stmt.setString(1, security.getUserPrincipal().getName());
				stmt.setString(2, r.getUsername());
				stmt.setInt(1, r.getLibroid());
				stmt.setString(3, r.getReview());
				
				stmt.executeUpdate();
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					int reviewid = rs.getInt(1);

					r = getReviewFromDatabase( reviewid);
				} else {
					// Something has failed...
				}
			} catch (SQLException e) {
				throw new ServerErrorException(e.getMessage(),
						Response.Status.INTERNAL_SERVER_ERROR);
			} finally {
				try {
					if (stmt != null)
						stmt.close();
					conn.close();
				} catch (SQLException e) {
				}
			}

			return r;
		}

		private String buildInsertReview() {
			return "insert into reviews (libroid, username, review, fecha) values (?, ?, ?)";
		}
		private Review getReviewFromDatabase(int reviewid) {
			Review r = new Review();

			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}

			PreparedStatement stmt = null;
			try {
				stmt = conn.prepareStatement(buildQueryGetReviewByReviewid());
				stmt.setInt(1, reviewid);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					r.setLibroid(rs.getInt("libroid"));
					r.setUsername(rs.getString("username"));
					r.setReviewid(rs.getInt("reviewid"));
					r.setReview(rs.getString("Review"));
					
				} else {
					throw new NotFoundException("There's no review with reviewid="
							+ reviewid);
				}

			} catch (SQLException e) {
				throw new ServerErrorException(e.getMessage(),
						Response.Status.INTERNAL_SERVER_ERROR);
			} finally {
				try {
					if (stmt != null)
						stmt.close();
					conn.close();
				} catch (SQLException e) {
				}
			}

			return r;
		}
		private String buildQueryGetReviewByReviewid() {
			return "select * from reviews where reviewid=?";
		}
		public boolean isAdministrator() {
			return administrator;
		}

		public void setAdministrator(boolean administrator) {
			this.administrator = administrator;
		}
		
		
		@PUT
		@Path("/{libroid}/reviews/{reviewid}")
		@Consumes(Mediatype.LIBROS_API_REVIEW)
		@Produces(Mediatype.LIBROS_API_REVIEW)
		public Review updateReview(@PathParam("libroid") String libroid,
				@PathParam("reviewid") int reviewid, Review review) {
			Libro book;
			book = null;
			Review review2 = null; // para que devuelva la nueva review

			// En prime lugar comprobar que el usuario sea registrado
			// y que la reseña a editar sea la suya (Alicia solo puede editar la
			// reseña de Alicia)
			if (!security.isUserInRole("registered")) {
				throw new ForbiddenException("You have not registered");
			}

			// ahora el usuario
			validateUser(reviewid);

			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}

			PreparedStatement stmt = null;
			try {
				String sql = buildUpdateReview();
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setInt(1, review.getLibroid());
				stmt.setDate(2, (Date) review.getFecha());
				stmt.setString(3, review.getReview());
				stmt.setInt(4, review.getReviewid());
				stmt.executeUpdate();

				// si ha ido bien la inserción
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					book = getLibroFromDatabase(libroid);
					review2 = getReviewFromDatabase(review.getReviewid());
				} else {
					throw new ServerErrorException(
							"Could not connect to the database",
							Response.Status.SERVICE_UNAVAILABLE);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (stmt != null)
						stmt.close();
					conn.close();
				} catch (SQLException e) {
					throw new ServerErrorException(e.getMessage(),
							Response.Status.INTERNAL_SERVER_ERROR);
				}
			}

			return review2;
		}
		private void validateUser(int reviewid) {
			// si el usuario que consulta la reseña no es el que la ha creado,
			// ForbiddenException
			Review currentReview = getReviewFromDatabase(reviewid);
			if (!security.getUserPrincipal().getName()
					.equals(currentReview.getUsername()))
				throw new ForbiddenException(
						"You are not allowed to modify/create/delete this review.");
		}

		// 9.2. Query para hacer edición/actualizar reseña
		private String buildUpdateReview() {
			return "update review set bookid=ifnull(?, bookid), fecha=ifnull(?, fecha), reviewtext=ifnull(?, reviewtext) where reviewid=?";
		}
		
		@DELETE
		@Path("/{ibroid}/reviews/{reviewid}")
		public void deleteReview(@PathParam("bookid") String libroid,
				@PathParam("reviewid") int reviewid) {

			// reseña de Alicia)
			if (!security.isUserInRole("registered")) {
				throw new ForbiddenException("You have not registered");
			}

			// ahora el usuario
			validateUser(reviewid);

			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}

			PreparedStatement stmt = null;
			try {
				// llamamos a la función para la query y la hacemos la database
				String sql = buildDeleteReview();
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setInt(1, reviewid);

				int rows = stmt.executeUpdate();

				if (rows == 0) {
					throw new NotFoundException("There's no review with review="
							+ reviewid + "with the book with bookid=" + libroid);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (stmt != null)
						stmt.close();
					conn.close();
				} catch (SQLException e) {
					throw new ServerErrorException(e.getMessage(),
							Response.Status.INTERNAL_SERVER_ERROR);
				}
			}
		}
		private String buildDeleteReview() {
			return "delete from review where reviewid=?";
		}
}