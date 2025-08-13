package org.airpenthouse.GoTel.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class CountApiUsers extends CommonEntityMethod {

    private static final CountApiUsers instance = new CountApiUsers();
    @Getter
    private Integer countWorldLanguagesUsers, countWorldCountriesUsers, countWorldCitiesUsersUsers;
    private String getCountsByMembershipQuery, getCountsQuery, upgradeWorldLanguageCountQuery, upgradeWorldCountriesCountQuery, upgradeWorldCitiesCountQuery;
    private PreparedStatement st, updatePreparedStatement;
    private ExecutorService executorService;
    private Lock lock;
    @Getter
    private LocalDateTime modifiedDate;
    private DateTimeFormatter format;

    protected CountApiUsers() {
        super();
        lock = new ReentrantLock();
        this.format = DateTimeFormatter.ofPattern("dd/MMM/yyyy hh:mm");
        executorService = Executors.newSingleThreadExecutor();
        getCountsByMembershipQuery = PropertiesUtilManager.getPropertiesValue("jdbc.query.getAllCountByPrivilegeName");
        getCountsQuery = PropertiesUtilManager.getPropertiesValue("jdbc.query.getAllCounts");
        upgradeWorldLanguageCountQuery = PropertiesUtilManager.getPropertiesValue("jdbc.update.updateLanguageCount");
        upgradeWorldCountriesCountQuery = PropertiesUtilManager.getPropertiesValue("jdbc.update.updateCitiesCount");
        upgradeWorldCitiesCountQuery = PropertiesUtilManager.getPropertiesValue("jdbc.update.updateCountriesCount");
    }

    private CountApiUsers(Integer countWorldLanguagesUsers, Integer countWorldCountriesUsers, Integer countWorldCitiesUsersUsers) {
        this.countWorldLanguagesUsers = countWorldLanguagesUsers;
        this.countWorldCountriesUsers = (countWorldCountriesUsers);
        this.countWorldCitiesUsersUsers = (countWorldCitiesUsersUsers);
    }

    public static CountApiUsers getInstance() {
        return instance;
    }

    protected void updateWorldLanguageCount() {
        try {
            lock.lock();
            modifiedDate = LocalDateTime.now();
            updatePreparedStatement = databaseConfig(upgradeWorldLanguageCountQuery);
            updatePreparedStatement.setString(3, Privileges.MEMBERSHIP.getMembershipName());
            var list = getCountiesByNoMembership();
            if (list.size() == 1) {

                updatePreparedStatement.setInt(1, list.get(0).countWorldLanguagesUsers++);
                updatePreparedStatement.setString(2, modifiedDate.format(format));
                updatePreparedStatement.executeUpdate();
            } else {
                throw new RuntimeException("Error occurred while updating");
            }

        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    protected void updateWorldCitiesCount() {
        try {
            lock.lock();
            modifiedDate = LocalDateTime.now();
            updatePreparedStatement = databaseConfig(upgradeWorldCitiesCountQuery);
            updatePreparedStatement.setString(3, Privileges.NO_MEMBERSHIP.getMembershipName());
            var list = getCountiesByNoMembership();
            if (list.size() == 1) {

                updatePreparedStatement.setInt(1, list.get(0).countWorldCitiesUsersUsers++);
                updatePreparedStatement.setString(2, modifiedDate.format(format));
                updatePreparedStatement.executeUpdate();
            } else {
                throw new RuntimeException("Error occurred while updating");
            }

        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    protected void updateWorldCountriesCount() {
        try {
            lock.lock();
            modifiedDate = LocalDateTime.now();
            updatePreparedStatement = databaseConfig(upgradeWorldCountriesCountQuery);
            updatePreparedStatement.setString(3, Privileges.NO_MEMBERSHIP.getMembershipName());
            var list = getCountiesByNoMembership();
            if (list.size() == 1) {
                updatePreparedStatement.setInt(1, list.get(0).countWorldCountriesUsers++);
                updatePreparedStatement.setString(2, modifiedDate.format(format));
                updatePreparedStatement.executeUpdate();
            } else {
                throw new RuntimeException("Error occurred while updating");
            }

        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public List<CountApiUsers> prepareToGetAllCounties() {
        Future<List<CountApiUsers>> future = executorService.submit(instance::getAllCounties);
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
            list.add(new CountApiUsers(set.getInt(5), set.getInt(3), set.getInt(4)));
        }
        set.close();

        Log.info("data from database to data structure cities data : " + list);
        return list;
    }

}
