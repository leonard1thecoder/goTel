package org.airpenthouse.GoTel.dtos.membership;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MembershipRequest {

    @JsonProperty("_member_name")
    private String memberName;
    @JsonProperty("_member_email")
    private String memberEmailAddress;
    @JsonProperty("_member_id")
    private int memberId;
    @JsonProperty("_membership_status")
    private int membershipStatus;
    @JsonProperty("_privilege_id")
    private int privilegeId;
    @JsonProperty("_membership_registration_date")
    @JsonFormat(pattern = "dd/MMM/yyyy hh:mm")
    private LocalDateTime registeredDate;
    @JsonIgnore
    @JsonProperty("_membership_token")
    private String token;
}
