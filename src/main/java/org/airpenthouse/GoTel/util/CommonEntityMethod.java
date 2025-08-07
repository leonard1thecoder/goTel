package org.airpenthouse.GoTel.util;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.*;

import static org.airpenthouse.GoTel.util.Connect.getInstance;

@Component
public class CommonEntityMethod {

    private final String jdbcQueryFindCountryCode;

    protected CommonEntityMethod() {
        jdbcQueryFindCountryCode = PropertiesUtilManager.getPropertiesValue("jdbc.query.findCountryCodeByCountry");
    }

    protected String getCountryCodeByCountryName(String countryName) {
        PreparedStatement ps;
        try {
            ps = databaseConfig(jdbcQueryFindCountryCode);
            ps.setString(1, countryName);
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                return set.getString(1);
            } else throw new RuntimeException("Country searched not found, entered : " + countryName);
        } catch (SQLException | ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected PreparedStatement databaseConfig(String query) throws ExecutionException, InterruptedException, TimeoutException, SQLException {
        Future<Connection> connect = getInstance().getDB_data();
        assert connect != null;
        return connect.get(15, TimeUnit.SECONDS).prepareStatement(query);
    }

}
