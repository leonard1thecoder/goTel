package org.airpenthouse.GoTel.entities.sponsors;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.airpenthouse.GoTel.util.PropertiesUtilManager;
import org.airpenthouse.GoTel.util.executors.SponsorsExecutors;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;

@AllArgsConstructor
public class SponsorEntity extends SponsorsExecutors implements Callable<List<SponsorEntity>> {

    @Getter
    private String sponsorName, SponsorSurname, sponsorEmailAddress, sponsorCellphoneNumber,
            sponsorReasonToDonate;

    @Getter
    private String cardNumber, cvv, cardDate;
    @Getter
    private Double dononationAmount;

    @Getter
    private LocalDateTime paymentDate;

    private String jdbcQueryGetAllSponsor, jdbcQueryGetSponsorByName, jdbcQueryInsertSponsor;

    private PreparedStatement prepareResultSet, prepareInsert;

    public static String EntityHandler;

    public SponsorEntity() {
        this.jdbcQueryInsertSponsor = PropertiesUtilManager.getPropertiesValue("jdbc.query.donate");
        this.jdbcQueryGetAllSponsor = PropertiesUtilManager.getPropertiesValue("jdbbc.query.getSponsorByName");
        this.jdbcQueryGetSponsorByName = PropertiesUtilManager.getPropertiesValue("jdbc.query.getAllSponsors");
    }


    private SponsorEntity(String sponsorName
            , String sponsorSurname
            , String sponsorEmailAddress
            , String sponsorCellphoneNumber
            , String sponsorReasonToDonate
            , String cardNumber
            , String cvv
            , String cardDate
            , Double dononationAmount
            , LocalDateTime paymentDate) {
        this.sponsorName = sponsorName;
        SponsorSurname = sponsorSurname;
        this.sponsorEmailAddress = sponsorEmailAddress;
        this.sponsorCellphoneNumber = sponsorCellphoneNumber;
        this.sponsorReasonToDonate = sponsorReasonToDonate;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.cardDate = cardDate;
        this.dononationAmount = dononationAmount;
        this.paymentDate = paymentDate;
    }

    @Override
    public List<SponsorEntity> call() throws Exception {

        return switch (SponsorEntity.EntityHandler) {
            case "GET_ALL_SPONSOR" -> getAllSponsors();
            case "GET_SPONSOR_BY_NAME" -> getSponsorByName();
            case "DONATE" -> insertDonate();
            default -> throw new RuntimeException("Entity handler failed due to incorrect value");
        };
    }

    private List<SponsorEntity> getAllSponsors() {
        try {
            prepareResultSet = databaseConfig(this.jdbcQueryGetAllSponsor);
            return resultSetData(prepareResultSet.executeQuery());
        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<SponsorEntity> getSponsorByName() {
        try {
            prepareResultSet = databaseConfig(this.jdbcQueryGetSponsorByName);
            prepareResultSet.setString(1, PropertiesUtilManager.getPropertiesValue("sponsorsName"));

            return resultSetData(prepareResultSet.executeQuery());
        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<SponsorEntity> insertDonate() {
        try {
            prepareInsert = databaseConfig(this.jdbcQueryInsertSponsor);
            prepareInsert.setString(1, getEntity().getSponsorName());
            prepareInsert.setString(2, getEntity().getSponsorSurname());
            prepareInsert.setString(3, getEntity().getSponsorCellphoneNumber());
            prepareInsert.setString(4, getEntity().getSponsorEmailAddress());
            prepareInsert.setString(5, getEntity().getCardNumber());
            prepareInsert.setString(6, getEntity().getCardDate());
            prepareInsert.setString(7, getEntity().getSponsorReasonToDonate());
            prepareInsert.setDouble(8, getEntity().getDononationAmount());
            prepareInsert.setString(9, getEntity().getPaymentDate().toString());

            var entity = getSponsorByName();
            if (entity.isEmpty()) {
                prepareInsert.executeUpdate();
                return getSponsorByName();
            } else {
                return null;
            }
        } catch (ExecutionException | InterruptedException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private List<SponsorEntity> resultSetData(ResultSet set) throws SQLException {
        var entities = new CopyOnWriteArrayList<SponsorEntity>();

        while (set.next()) {
            entities.add(new SponsorEntity(set.getString(1)
                    , set.getString(2)
                    , set.getString(3)
                    , set.getString(4)
                    , set.getString(5)
                    , set.getString(6)
                    , set.getString(7)
                    , set.getString(8)
                    , set.getDouble(9)
                    , LocalDateTime.parse(set.getString(10))));
        }
        return entities;
    }

}
