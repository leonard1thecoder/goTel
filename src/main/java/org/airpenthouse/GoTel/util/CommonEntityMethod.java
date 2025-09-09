package org.airpenthouse.GoTel.util;

import org.airpenthouse.GoTel.entities.membership.MembershipEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.*;

import static org.airpenthouse.GoTel.util.Connect.getInstance;

public class CommonEntityMethod {

    private final String jdbcQueryFindCountryCode;
    private static MembershipEntity membershipEntity;

    static {
        membershipEntity = new MembershipEntity();
    }

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

    public boolean checkMemberShipStatusAndTokenMatch() {

        try {
            var set = this.membershipEntity.getMemberByToken();
            var set2 = this.membershipEntity.getMemberByUsername();
            if (set.size() == 1 && set2.size() == 1) {
                var memberList = set.stream().toList();
                var memberStatus = memberList.get(0).getMembershipStatus();
                var memberList2 = set2.stream().toList();
                return memberStatus == 1 && memberList2.get(0).getMemberToken().equals(memberList.get(0).getMemberToken());
            } else {
                throw new RuntimeException("Error getting token failed, due set size is not equal to 1");
            }
        } catch (NullPointerException e) {
            return false;
        }
    }


    protected PreparedStatement databaseConfig(String query) throws ExecutionException, InterruptedException, TimeoutException, SQLException {
        Future<Connection> connect = getInstance().getDB_data();
        assert connect != null;
        return connect.get(15, TimeUnit.SECONDS).prepareStatement(query);
    }

}
