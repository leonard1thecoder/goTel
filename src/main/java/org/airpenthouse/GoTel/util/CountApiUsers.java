package org.airpenthouse.GoTel.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class CountApiUsers extends CommonEntityMethod {

    private static final CountApiUsers instance = new CountApiUsers();
    private AtomicInteger countWorldLanguagesUsers, countWorldCountriesUsers, countWorldCitiesUsersUsers;
    private String getCountsByMembershipQuery, getCountsQuery, upgradeWorldLanguageCountQuery, upgradeWorldCountriesCountQuery, upgradeWorldCitiesCountQuery;
    private PreparedStatement st, updatePreparedStatement;
    private ExecutorService executorService;
    private Lock lock;
    @Getter
    private LocalDateTime modifiedDate;

    protected CountApiUsers() {
        super();
        lock = new ReentrantLock();

        this.countWorldLanguagesUsers = new AtomicInteger();
        this.countWorldCountriesUsers = new AtomicInteger();
        this.countWorldCitiesUsersUsers = new AtomicInteger();

        executorService = Executors.newSingleThreadExecutor();
        getCountsByMembershipQuery = PropertiesUtilManager.getPropertiesValue("jdbc.query.getAllCountByPrivilegeName");
        getCountsQuery = PropertiesUtilManager.getPropertiesValue("jdbc.query.getAllCounts");
        upgradeWorldLanguageCountQuery = PropertiesUtilManager.getPropertiesValue("jdbc.update.updateLanguageCount");
        upgradeWorldCountriesCountQuery = PropertiesUtilManager.getPropertiesValue("jdbc.update.updateCitiesCount");
        upgradeWorldCitiesCountQuery = PropertiesUtilManager.getPropertiesValue("jdbc.update.updateCountriesCount");
    }

    private CountApiUsers(Integer countWorldLanguagesUsers, Integer countWorldCountriesUsers, Integer countWorldCitiesUsersUsers) {
        this.countWorldLanguagesUsers.set(countWorldLanguagesUsers);
        this.countWorldCountriesUsers.set(countWorldCountriesUsers);
        this.countWorldCitiesUsersUsers.set(countWorldCitiesUsersUsers);
    }

    public static CountApiUsers getInstance() {
        return instance;
    }

    protected void updateWorldLanguageCount() {
        try {
            lock.lock();
            updatePreparedStatement = databaseConfig(upgradeWorldLanguageCountQuery);
            updatePreparedStatement.setString(1, Privileges.MEMBERSHIP.getMembershipName());
            var list = getCountiesByNoMembership();
            if (list.size() == 1) {
                countWorldLanguagesUsers.set(list.get(0).countWorldLanguagesUsers.get());
                updatePreparedStatement.setInt(2, countWorldLanguagesUsers.incrementAndGet());
            }
            updatePreparedStatement.executeUpdate();
        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    protected void updateWorldCitiesCount() {
        try {
            lock.lock();
            updatePreparedStatement = databaseConfig(upgradeWorldCitiesCountQuery);
            updatePreparedStatement.setString(1, Privileges.NO_MEMBERSHIP.getMembershipName());
            var list = getCountiesByNoMembership();
            if (list.size() == 1) {
                countWorldLanguagesUsers.set(list.get(0).countWorldCitiesUsersUsers.get());
                updatePreparedStatement.setInt(2, countWorldCitiesUsersUsers.incrementAndGet());
            }
            updatePreparedStatement.executeUpdate();
        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    protected void updateWorldCountriesCount() {
        try {
            lock.lock();
            updatePreparedStatement = databaseConfig(upgradeWorldCountriesCountQuery);
            updatePreparedStatement.setString(1, Privileges.NO_MEMBERSHIP.getMembershipName());
            var list = getCountiesByNoMembership();
            if (list.size() == 1) {
                countWorldLanguagesUsers.set(list.get(0).countWorldCountriesUsers.get());
                updatePreparedStatement.setInt(2, countWorldCountriesUsers.incrementAndGet());
            }
            updatePreparedStatement.executeUpdate();
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
