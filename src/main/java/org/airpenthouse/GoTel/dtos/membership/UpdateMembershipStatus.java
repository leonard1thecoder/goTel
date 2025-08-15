package org.airpenthouse.GoTel.dtos.membership;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UpdateMembershipStatus {

    @JsonProperty("_member_email")
    private String memberEmailAddress;
    @JsonProperty("_membership_status")
    private int membershipStatus;
    @JsonProperty("_membership_registration_date")
    @JsonFormat(pattern = "dd/MMM/yyyy hh:mm")
    private LocalDateTime registeredDate;
}
