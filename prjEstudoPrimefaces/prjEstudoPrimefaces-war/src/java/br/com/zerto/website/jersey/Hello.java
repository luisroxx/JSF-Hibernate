package br.com.zerto.website.jersey;

import br.com.zerto.website.pojo.Usuario;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Plain old Java Object it does not extend as class or implements
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation.
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML.

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/")
public class Hello {
  // This method is called if TEXT_PLAIN is request
  @GET
  @Path("/status")
  @Produces(MediaType.TEXT_HTML)
  public String sayPlainTextHello() {
    return "<html> " + "<title>" + "Hello Jersey" + "</title>"
        + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
  }
  
  @POST
  @Path("/auth")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postUrl(String jsonRequest) {
      
    System.err.println(jsonRequest);  
      
    return Response.status(Response.Status.BAD_REQUEST).entity("bad").build();
  }
}