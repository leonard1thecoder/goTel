package org.airpenthouse.GoTel.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PRIVILEGES {
    NO_MEMBERSHIP("NO_MEMBERSHIP", 3),
    MEMBERSHIP("MEMBERSHIP", 4);

    @Getter
    private final String membershipName;
    @Getter
    private final int privilege_id;
}
