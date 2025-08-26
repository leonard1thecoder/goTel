package org.airpenthouse.GoTel.dtos.sponsors;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
public class SponsorsDonateRequest {
    @Getter
    private String sponsorName, SponsorSurname, sponsorEmailAddress, sponsorCellphoneNumber, sponsorReasonToDonate;

    @Getter
    private String cardNumber, cvv, cardDate;
    @Getter
    private Double dononationAmount;

    @Getter
    private LocalDateTime paymentDate;
}
