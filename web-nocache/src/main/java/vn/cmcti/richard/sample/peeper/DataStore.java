package vn.cmcti.richard.sample.peeper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataStore {

    // Logger
    private static final Logger _LOGGER = LoggerFactory.getLogger(DataStore.class);
    // h2 connection
    private Connection conn;

    public void init() throws ClassNotFoundException, SQLException {
        _LOGGER.info("Initializing DB connection...");
        Class.forName("org.h2.Driver");
        conn = DriverManager.getConnection("jdbc:h2:~/ehcache-demo-peeper", "sa", "");

        _LOGGER.info("Creating DB structure...");
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS PEEPS (ID BIGINT AUTO_INCREMENT PRIMARY KEY, PEEP_TEXT VARCHAR(150) NOT NULL)");
            conn.commit();
        }
    }

    public synchronized void addPeep(String peepText) throws SQLException {
        _LOGGER.info("Adding peep into DB");
        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO PEEPS (PEEP_TEXT) VALUES (?)")) {
            pstmt.setString(1, peepText);
            pstmt.execute();
            conn.commit();
        }
    }

    public synchronized List<String> findAllPeeps() throws SQLException {
        _LOGGER.info("Loading peeps from DB");
        List<String> peeps = new ArrayList<>();

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM PEEPS");
            while (rs.next()) {
                peeps.add(rs.getString("PEEP_TEXT"));
            }
        }

        return peeps;
    }

    public void close() throws SQLException {
        _LOGGER.info("Close connection");
        if (conn != null && !conn.isClosed())
            conn.close();
    }

}
