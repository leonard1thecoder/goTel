package org.airpenthouse.GoTel.entities.administrator;

import lombok.Getter;
import org.airpenthouse.GoTel.util.CommonEntityMethod;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


public class AdministratorEntity extends CommonEntityMethod implements Callable<Set<AdministratorEntity>> {

    @Getter
    private String administratorName, administratorSurname, administratorCellphoneNo, administratorEmailAddress, administratorToken, administratorPassword;
    public static String entityHandler;
    @Getter
    private LocalDateTime registrationDate;
    private PreparedStatement prepareResultSet, prepareUpdateQuery;
    private String findAllAdministratorsQuery;

    public AdministratorEntity() {
        findAllAdministratorsQuery = PropertiesUtilManager.getPropertiesValue("jdbc.query.getAllAdministrators");
        findAllAdministratorsQuery = PropertiesUtilManager.getPropertiesValue("jdbc.query.getAdministratorsbyEmailAddress");
        findAllAdministratorsQuery = PropertiesUtilManager.getPropertiesValue("jdbc.query.getAdministratorsbyName");
        findAllAdministratorsQuery = PropertiesUtilManager.getPropertiesValue("jdbc.query.administratorLogin");
        findAllAdministratorsQuery = PropertiesUtilManager.getPropertiesValue("jdbc.update.updateAdministratorPassword");
        findAllAdministratorsQuery = PropertiesUtilManager.getPropertiesValue("jdbc.update.updateAdministratorToken");
        findAllAdministratorsQuery = PropertiesUtilManager.getPropertiesValue("jdbc.add.registerAdministrator");

    }

    private AdministratorEntity(String administratorName, String administratorSurname, String administratorCellphoneNo, String administratorEmailAddress, String administratorToken, String administratorPassword, String registrationDate) {
        this.administratorName = administratorName;
        this.administratorSurname = administratorSurname;
        this.administratorCellphoneNo = administratorCellphoneNo;
        this.administratorEmailAddress = administratorEmailAddress;
        this.administratorToken = administratorToken;
        this.administratorPassword = administratorPassword;
        this.registrationDate = LocalDateTime.parse(registrationDate);
    }


    private Set<AdministratorEntity> findAllAdminstrators() {
        try {
            prepareResultSet = super.databaseConfig(findAllAdministratorsQuery);
            var dbResults = prepareResultSet.executeQuery();
            return addDbDataToSet(dbResults);

        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<AdministratorEntity> findAdminstratorsByName() {
        try {
            prepareResultSet = super.databaseConfig(findAllAdministratorsQuery);
            prepareResultSet.setString(1, "administratorName");
            var dbResults = prepareResultSet.executeQuery();
            return addDbDataToSet(dbResults);
        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<AdministratorEntity> findAdminstratorsByEmail() {
        try {
            prepareResultSet = super.databaseConfig(findAllAdministratorsQuery);
            prepareResultSet.setString(1, "administratorEmail");
            var dbResults = prepareResultSet.executeQuery();
            return addDbDataToSet(dbResults);
        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<AdministratorEntity> updateAdministratorPassword() {
        try {
            prepareUpdateQuery = super.databaseConfig(findAllAdministratorsQuery);
            prepareUpdateQuery.setString(2, "administratorEmail");
            prepareUpdateQuery.setString(1, "administratorPassword");
            var set = this.findAdminstratorsByEmail();
            if (set.size() == 1) {
                prepareUpdateQuery.executeUpdate();
                set = this.findAdminstratorsByEmail();
            } else
                set = null;

            return set;
        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<AdministratorEntity> updateAdministratorToken() {
        try {
            prepareUpdateQuery = super.databaseConfig(findAllAdministratorsQuery);
            prepareUpdateQuery.setString(2, "administratorEmail");
            prepareUpdateQuery.setString(1, "administratorToken");
            var set = this.findAdminstratorsByEmail();
            if (set.size() == 1) {
                prepareUpdateQuery.executeUpdate();
                set = this.findAdminstratorsByEmail();
            } else
                set = null;

            return set;
        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<AdministratorEntity> addDbDataToSet(ResultSet set) throws SQLException {
        Set<AdministratorEntity> entities = new CopyOnWriteArraySet<>();
        while (set.next()) {
            entities.add(new AdministratorEntity(set.getString(2), set.getString(3), set.getString(8), set.getString(4), set.getString(6), set.getString(5), set.getString(7)));
        }
        return entities;
    }

    private Set<AdministratorEntity> administrorLogin() {
        try {
            prepareUpdateQuery = super.databaseConfig(findAllAdministratorsQuery);
            prepareUpdateQuery.setString(2, "administratorEmail");
            prepareUpdateQuery.setString(1, "administratorPassword");
            return addDbDataToSet(prepareResultSet.executeQuery());
        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<AdministratorEntity> registorAdministror() {
        try {
            prepareUpdateQuery = super.databaseConfig(findAllAdministratorsQuery);
            prepareUpdateQuery.setString(2, "adminisrtatorName");
            prepareUpdateQuery.setString(1, "administratorPassword");
            prepareUpdateQuery.setString(2, "administratorEmail");
            prepareUpdateQuery.setString(1, "administratorPassword");
            prepareUpdateQuery.setString(2, "administratorEmail");
            prepareUpdateQuery.setString(1, "administratorPassword");
            var set = this.findAdminstratorsByEmail();
            if (set.isEmpty()) {
                prepareUpdateQuery.executeUpdate();
                set = this.findAdminstratorsByEmail();
            } else
                throw new RuntimeException("Error can't add administrator, use already exists");

            return set;
        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<AdministratorEntity> call() throws Exception {
        return switch (entityHandler) {
            case "FIND_ALL_ADMINISTRATORS" -> findAllAdminstrators();
            case "FIND_ADMINISTRATOR_BY_NAME" -> findAdminstratorsByName();
            case "FIND_ADMINISTRATOR_BY_EMAIL" -> findAdminstratorsByEmail();
            case "UPDATE_ADMINISTRATOR_TOKEN" -> updateAdministratorToken();
            case "UPDATE_ADMINISTRATOR_PASSWORD" -> updateAdministratorPassword();
            case "REGISTRATOR_ADMINISTRATOR" -> registorAdministror();
            case "ADMINISTRATOR_LOGIN" -> administrorLogin();
            default -> throw new RuntimeException("Error occurred while processing administrator entity");
        };
    }
}
