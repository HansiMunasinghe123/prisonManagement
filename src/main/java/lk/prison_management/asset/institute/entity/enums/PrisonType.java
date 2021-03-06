package lk.prison_management.asset.institute.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrisonType {
    HQ("Head Quaters"),
    CP("Closed Prisons"),
    RP("Remand Prisons"),
    WC("Work Camps"),
    OP("Open Prison Camps"),
    WR("Work Release centers"),
    LU("Lock ups"),
    CC("Correction Centre for Youthful offenders"),
    TS("Training  Schools");

    private final String prisonType;

}
