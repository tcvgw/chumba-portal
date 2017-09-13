package co.vgw.portal.authentication;

import static spark.Spark.*;

import java.util.UUID;

/**
 * Hello world!
 *
 */
public class Authentication {
	
	final static String SERVICE_PREFIX = "/auth";
	final static int PORT = 4545;
	
	public static void main(String[] args) {
	    port(PORT);
		path(SERVICE_PREFIX, () -> {

			get("/healthcheck", (req, res) -> "Live and healthy.");

			post("/authenticate", (req, res) -> {
				
			    // TODO create access token
			    UUID token = UUID.randomUUID();
			    
				// on success
				res.redirect("/main.html?token=" + token);
				return "";
			});
			
			get("/logout", (req, res) -> {
			    res.redirect("/index.html");
			    return "";
			});
			
		});
	}
}
