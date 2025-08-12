package org.airpenthouse.GoTel.entities.membership;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.airpenthouse.GoTel.util.Log;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.airpenthouse.GoTel.util.executors.MembershipExecutor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.*;

@ToString
@AllArgsConstructor
public class MembershipEntity extends MembershipExecutor implements Callable<Set<MembershipEntity>> {

    private static final MembershipEntity INSTANCE = new MembershipEntity();
    @Getter
    private String memberName, memberEmailAddress, memberToken;
    @Getter
    private int memberId, membershipStatus, privilegeId;
    @Getter
    private LocalDateTime registeredDate;
    private String insertMemberQuery, getAllMembersQuery, getMemberByName, getMemberByToken, updateMembershipStatus, updateMembershipToken;
    private PreparedStatement preparedStatementResultSet, preparedStatementUpdateQuery;

    public static String entityHandle;

    private MembershipEntity() {
        this.getAllMembersQuery = PropertiesUtilManager.getPropertiesValue("jdbc.query.getAllMembers");
        this.insertMemberQuery = PropertiesUtilManager.getPropertiesValue("jdbc.add.insertMembers");
        this.getMemberByToken = PropertiesUtilManager.getPropertiesValue("jdbc.query.getMemberByToken");
        this.getAllMembersQuery = PropertiesUtilManager.getPropertiesValue("jdbc.query.getAllMembers");
        this.getMemberByName = PropertiesUtilManager.getPropertiesValue("jdbc.query.getMemberByName");
        this.updateMembershipStatus = PropertiesUtilManager.getPropertiesValue("jdbc.update.updateMembershipStatus");
        this.updateMembershipToken = PropertiesUtilManager.getPropertiesValue("jdbc.update.updateMemberToken");
    }

    private MembershipEntity(String memberName, String memberEmailAddress, int memberId, String memberToken, int membershipStatus) {
        this.memberName = memberName;
        this.memberEmailAddress = memberEmailAddress;
        this.memberId = memberId;
        this.memberToken = memberToken;
        this.membershipStatus = membershipStatus;
        this.registeredDate = LocalDateTime.now();
    }

    public static MembershipEntity getInstance() {
        return INSTANCE;
    }

    private Set<MembershipEntity> getAllMembers() {
        try {
            this.preparedStatementResultSet = super.databaseConfig(this.getAllMembersQuery);
            return addDataFromDBToList();
        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Set<MembershipEntity> getMemberByName() {
        try {
            this.preparedStatementResultSet = super.databaseConfig(this.getMemberByName);
            this.preparedStatementResultSet.setString(1, PropertiesUtilManager.getPropertiesValue("memberName"));
            return addDataFromDBToList();
        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<MembershipEntity> getMemberByToken() {
        try {
            this.preparedStatementResultSet = super.databaseConfig(this.getMemberByToken);
            this.preparedStatementResultSet.setString(1, PropertiesUtilManager.getPropertiesValue("membershipToken"));
            return addDataFromDBToList();
        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Set<MembershipEntity> addDataFromDBToList() throws SQLException {
        Set<MembershipEntity> list = new CopyOnWriteArraySet<>();
        ResultSet set = preparedStatementResultSet.executeQuery();
        while (set.next()) {
            list.add(new MembershipEntity(set.getString(2), set.getString(3), set.getInt(1), set.getString(4), set.getInt(5)));
        }
        set.close();

        Log.info("data from database to data structure cities data : " + list);
        return list;
    }

    private Set<MembershipEntity> updateMembershipToken() {
        try {
            this.preparedStatementUpdateQuery = databaseConfig(this.updateMembershipToken);
            this.preparedStatementUpdateQuery.setString(1, PropertiesUtilManager.getPropertiesValue("membershipToken"));
            this.preparedStatementUpdateQuery.setString(2, PropertiesUtilManager.getPropertiesValue("modifiedDate"));
            this.preparedStatementUpdateQuery.setString(3, PropertiesUtilManager.getPropertiesValue("memberName"));
            return validateUpdatingOrInserting();


        } catch (ExecutionException | TimeoutException | SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<MembershipEntity> registerMember() {
        try {
            this.preparedStatementUpdateQuery = databaseConfig(this.insertMemberQuery);
            this.preparedStatementUpdateQuery.setInt(1, Integer.parseInt(PropertiesUtilManager.getPropertiesValue("privilegeId")));
            this.preparedStatementUpdateQuery.setInt(5, Integer.parseInt(PropertiesUtilManager.getPropertiesValue("membershipStatus")));
            this.preparedStatementUpdateQuery.setString(6, PropertiesUtilManager.getPropertiesValue("registrationDate"));
            this.preparedStatementUpdateQuery.setString(3, PropertiesUtilManager.getPropertiesValue("membershipEmailAddress"));
            this.preparedStatementUpdateQuery.setString(4, PropertiesUtilManager.getPropertiesValue("membershipToken"));
            this.preparedStatementUpdateQuery.setString(2, PropertiesUtilManager.getPropertiesValue("memberName"));
            insertData = true;
            return validateUpdatingOrInserting();
        } catch (ExecutionException | TimeoutException | SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean insertData;

    private Set<MembershipEntity> validateUpdatingOrInserting() {
        var localSet = new CopyOnWriteArraySet<MembershipEntity>();
        try {
            var data = this.getMemberByName();

            if (insertData) {
                insertData = false;
                if (!data.isEmpty()) {
                    throw new RuntimeException("Member already exists in DB " + data);
                } else {
                    int rowAffected = this.preparedStatementUpdateQuery.executeUpdate();
                    if (rowAffected > 0) return this.getMemberByName();
                    else {
                        Log.info("No data added to db");
                        return localSet;
                    }
                }
            } else {
                if (data.isEmpty()) {
                    Log.info("data is empty, can't update");
                    return localSet;
                } else {
                    int rowAffected = this.preparedStatementUpdateQuery.executeUpdate();
                    if (rowAffected > 0) return this.getMemberByName();
                    else {
                        Log.info("No data");
                        return localSet;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private Set<MembershipEntity> updateMembershipStatus() {
        try {
            this.preparedStatementUpdateQuery = databaseConfig(this.updateMembershipStatus);
            this.preparedStatementUpdateQuery.setString(1, PropertiesUtilManager.getPropertiesValue("membershipStatus"));
            this.preparedStatementUpdateQuery.setString(2, PropertiesUtilManager.getPropertiesValue("modifiedDate"));
            this.preparedStatementUpdateQuery.setString(3, PropertiesUtilManager.getPropertiesValue("memberName"));
            return validateUpdatingOrInserting();
        } catch (ExecutionException | TimeoutException | SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<MembershipEntity> call() {
        return switch (entityHandle) {
            case "GET_ALL_MEMBERS" -> getAllMembers();
            case "GET_MEMBER_BY_NAME" -> getMemberByName();
            case "GET_MEMBER_BY_TOKEN" -> getMemberByToken();
            case "UPDATE_MEMBERSHIP_TOKEN" -> updateMembershipToken();
            case "UPDATE_MEMBERSHIP_STATUS" -> updateMembershipStatus();
            case "REGISTER_MEMBER" -> registerMember();
            default -> null;
        };
    }
}
