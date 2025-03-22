package edu.eci.cvds.labReserves.model;

public class LabReserveException extends Exception{
    public static final String TYPE_RESERVE_NOT_FOUND = "type reserve not found"; //type of reserve isn't in the array
    public static final String REASON_RESERVE_NOT_FOUND = "reason reserve not found"; //reason of reserve is null
    public static final String USER_RESERVE_NOT_FOUND = "user reserve not found"; //user of reserve is null
    public static final String STATE_RESERVE_NOT_FOUND = "state reserve not found"; //state of reserve isn't in the array

    public static final String YEAR_BEFORE_ACTUALLY = "year selected before actually"; //year is before to current
    public static final String MONTH_BEFORE_ACTUALLY = "month selected before actually"; //Month is before to current
    public static final String DAY_BEFORE_ACTUALLY = "day selected before actually"; //day is before to current
    public static final String HOUR_BEFORE_ACTUALLY = "hour selected before actually"; //hour is before to current
    public static final String RESERVE_ALREADY_EXIST = "this reserve already exist";
    public static final String INVALID_ROL_TYPE = "this rol is not admited";
    public static final String PRIORITY_NOT_IN_RANGE = "priority not in range"; //priority less than 1 or more than 5

    public LabReserveException(String message){
        super(message);
    }
}
