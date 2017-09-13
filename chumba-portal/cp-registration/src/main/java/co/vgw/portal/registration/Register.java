package co.vgw.portal.registration;

import static spark.Spark.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.PostgresQuirks;

import co.vgw.portal.registration.model.Model;
import co.vgw.portal.registration.model.Sql2oModel;

/**
 *
 */
public class Register {

	static org.slf4j.Logger logger = LoggerFactory.getLogger(Register.class);

	public static void main(String[] args) throws ClassNotFoundException {
		port(4566);

		Class.forName("org.h2.Driver");

		Sql2o sql2o = new Sql2o("jdbc:h2:tcp://127.0.0.1/~/test", "sa", "", new PostgresQuirks() {
			{
				// make sure we use default UUID converter.
				converters.put(UUID.class, new UUIDConverter());
			}
		});

		Model model = new Sql2oModel(sql2o);

		path("/register", () -> {
			// root is 'src/main/resources', so put files in 'src/main/resources/public'
			staticFiles.location("/public"); // Static files
			// static file cache in seconds.
			// staticFiles.expireTime(300); // disabled for dev

			get("", (req, res) -> {
				logger.debug("GET default");
				res.redirect("/register/");
				return "";
			});

			post("/post", (req, res) -> {
				
				String username = req.queryParams("username");
				String password = req.queryParams("password");
				String firstname = req.queryParams("firstname");
				String lastname = req.queryParams("lastname");
				String phone = req.queryParams("phone");
				
				logger.debug(String.format("Captured: username = %s, password = %s, firstName = %s, lastName = %s, phone = %s", username, password, firstname, lastname, phone));
				
				UUID uuid = model.registerUser(firstname, lastname, username, password, phone);
				
				if(uuid == null) {
				    res.redirect("/register/#?error=777");
				} else {
				    // TODO create access token
				    UUID token = UUID.randomUUID();
				    
				    res.redirect("/main.html?token=" + token);
				}

				return "";
			});
		});

	}
}
