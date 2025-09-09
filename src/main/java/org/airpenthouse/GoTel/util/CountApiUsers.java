package org.airpenthouse.GoTel.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@ToString
@AllArgsConstructor
public class CountApiUsers extends CommonEntityMethod {

    @Getter
    private AtomicInteger countWorldLanguagesUsers = new AtomicInteger()
            ,countWorldCountriesUsers = new AtomicInteger()
            ,countWorldCitiesUsersUsers = new AtomicInteger();
    private String getCountsByMembershipQuery, getCountsQuery, upgradeWorldLanguageCountQuery, upgradeWorldCountriesCountQuery, upgradeWorldCitiesCountQuery;
    private PreparedStatement st, updatePreparedStatement;
    private ExecutorService executorService;

    @Getter
    private LocalDateTime modifiedDate;
    private DateTimeFormatter format;

    public CountApiUsers() {
        super();

        this.format = DateTimeFormatter.ofPattern("dd/MMM/yyyy hh:mm");
        executorService = Executors.newSingleThreadExecutor();
        getCountsByMembershipQuery = PropertiesUtilManager.getPropertiesValue("jdbc.query.getAllCountByPrivilegeName");
        getCountsQuery = PropertiesUtilManager.getPropertiesValue("jdbc.query.getAllCounts");
        upgradeWorldLanguageCountQuery = PropertiesUtilManager.getPropertiesValue("jdbc.update.updateLanguageCount");
        upgradeWorldCitiesCountQuery = PropertiesUtilManager.getPropertiesValue("jdbc.update.updateCitiesCount");
        upgradeWorldCountriesCountQuery = PropertiesUtilManager.getPropertiesValue("jdbc.update.updateCountriesCount");
    }

    private CountApiUsers(Integer countWorldLanguagesUsers, Integer countWorldCountriesUsers, Integer countWorldCitiesUsersUsers) {
        this.countWorldLanguagesUsers.set(countWorldLanguagesUsers);
        this.countWorldCountriesUsers.set(countWorldCountriesUsers);
        this.countWorldCitiesUsersUsers.set(countWorldCitiesUsersUsers);
    }

    protected void updateWorldLanguageCountForMembers() {
        try {

            modifiedDate = LocalDateTime.now();
            updatePreparedStatement = databaseConfig(upgradeWorldLanguageCountQuery);
            updatePreparedStatement.setString(3, Privileges.MEMBERSHIP.getMembershipName());
            var list = getCountiesByNoMembership();
            if (list.size() == 1) {

                updatePreparedStatement.setInt(1, list.get(0).countWorldLanguagesUsers.incrementAndGet());
                updatePreparedStatement.setString(2, modifiedDate.format(format));
                updatePreparedStatement.executeUpdate();
            } else {
                throw new RuntimeException("Error occurred while updating");
            }

        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void updateWorldCitiesCountForMembers() {
        try {

            modifiedDate = LocalDateTime.now();
            updatePreparedStatement = databaseConfig(upgradeWorldCitiesCountQuery);
            updatePreparedStatement.setString(3, Privileges.MEMBERSHIP.getMembershipName());
            var list = getCountiesByNoMembership();
            if (list.size() == 1) {

                updatePreparedStatement.setInt(1, list.get(0).countWorldCitiesUsersUsers.incrementAndGet());
                updatePreparedStatement.setString(2, modifiedDate.format(format));
                updatePreparedStatement.executeUpdate();
            } else {
                throw new RuntimeException("Error occurred while updating");
            }

        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void updateWorldCountriesCountForMembers() {
        try {

            modifiedDate = LocalDateTime.now();
            updatePreparedStatement = databaseConfig(upgradeWorldCountriesCountQuery);
            updatePreparedStatement.setString(3, Privileges.MEMBERSHIP.getMembershipName());
            var list = getCountiesByNoMembership();
            if (list.size() == 1) {
                updatePreparedStatement.setInt(1, list.get(0).countWorldCountriesUsers.incrementAndGet());
                updatePreparedStatement.setString(2, modifiedDate.format(format));
                updatePreparedStatement.executeUpdate();
            } else {
                throw new RuntimeException("Error occurred while updating");
            }

        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void updateWorldLanguageCount() {
        try {

            modifiedDate = LocalDateTime.now();
            updatePreparedStatement = databaseConfig(upgradeWorldLanguageCountQuery);
            updatePreparedStatement.setString(3, Privileges.NO_MEMBERSHIP.getMembershipName());
            var list = getCountiesByNoMembership();
            if (list.size() == 1) {

                updatePreparedStatement.setInt(1, list.get(0).countWorldLanguagesUsers.incrementAndGet());
                updatePreparedStatement.setString(2, modifiedDate.format(format));
                updatePreparedStatement.executeUpdate();
            } else {
                throw new RuntimeException("Error occurred while updating");
            }

        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void updateWorldCitiesCount() {
        try {

            modifiedDate = LocalDateTime.now();
            updatePreparedStatement = databaseConfig(upgradeWorldCitiesCountQuery);
            updatePreparedStatement.setString(3, Privileges.NO_MEMBERSHIP.getMembershipName());
            var list = getCountiesByNoMembership();
            if (list.size() == 1) {

                updatePreparedStatement.setInt(1, list.get(0).countWorldCitiesUsersUsers.incrementAndGet());
                updatePreparedStatement.setString(2, modifiedDate.format(format));
                updatePreparedStatement.executeUpdate();
            } else {
                throw new RuntimeException("Error occurred while updating");
            }

        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateWorldCountriesCount() {
        try {

            modifiedDate = LocalDateTime.now();
            updatePreparedStatement = databaseConfig(upgradeWorldCountriesCountQuery);
            updatePreparedStatement.setString(3, Privileges.NO_MEMBERSHIP.getMembershipName());
            var list = getCountiesByNoMembership();
            if (list.size() == 1) {

                updatePreparedStatement.setInt(1, list.get(0).countWorldCountriesUsers.incrementAndGet());
                updatePreparedStatement.setString(2, modifiedDate.format(format));
                updatePreparedStatement.executeUpdate();
            } else {
                throw new RuntimeException("Error occurred while updating");
            }

        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<CountApiUsers> prepareToGetAllCounties() {
        Future<List<CountApiUsers>> future = executorService.submit(this::getAllCounties);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private List<CountApiUsers> getAllCounties() {
        try {
            st = databaseConfig(getCountsQuery);
            return addDataFromDBToList();
        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<CountApiUsers> getCountiesByNoMembership() {
        try {
            st = databaseConfig(getCountsByMembershipQuery);
            st.setString(1, Privileges.NO_MEMBERSHIP.getMembershipName());
            return addDataFromDBToList();
        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private List<CountApiUsers> addDataFromDBToList() throws SQLException {
        List<CountApiUsers> list = new CopyOnWriteArrayList<>();
        ResultSet set = st.executeQuery();

        while (set.next()) {
            Log.info(" checking1: " + set.getInt(6));
            Log.info(" checking2: " + set.getInt(4));
            Log.info(" checking: " + set.getInt(5));
            list.add(new CountApiUsers(set.getInt(4), set.getInt(6), set.getInt(5)));
        }
        set.close();

        Log.info("data from database to data structure cities data : " + list);
        return list;
    }

}
