package co.vgw.portal.registration.model;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class Sql2oModel implements Model {

    private Logger logger = LoggerFactory.getLogger(Sql2oModel.class);

    private Sql2o sql2o;

    public Sql2oModel(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public UUID registerUser(String firstName, String lastName, String userName, String password, String phone) {
        UUID uuid = null;
        String query1 = "insert into user(id, first_name, last_name, phone) values(:id, :first_name, :last_name, :phone)";
        String query2 = "insert into credential(id, username, password, active) values(:id, :username, :password, :active)";
        try (Connection conn = sql2o.beginTransaction()) {
            try {
                if (!checkIfUserExists(conn, userName)) {
                    uuid = UUID.randomUUID();
                    logger.debug("creating new UUID: " + uuid);
                    conn.createQuery(query1).addParameter("id", uuid).addParameter("first_name", firstName)
                            .addParameter("last_name", lastName).addParameter("phone", phone).executeUpdate();

                    conn.createQuery(query2).addParameter("id", uuid).addParameter("username", userName)
                            .addParameter("password", password).addParameter("active", true).executeUpdate();

                    conn.commit();
                } else {
                    logger.debug("Username " + userName + " already exists.");
                }
            } finally {
                conn.close();
            }
        }
        return uuid;
    }

    private boolean checkIfUserExists(Connection conn, String username) {
        Integer count = conn.createQuery("select count(*) from credential where username = :username")
                .addParameter("username", username).executeScalar(Integer.class);
        return count > 0;
    }

}
