package model;

public class Attendance {
    private int attendId, staffId;
    private String workDate;
    private int inHour, inMin, inSec;
    private Integer outHour, outMin, outSec;
    private int workMinutes;

    public Attendance(int attendId, int staffId, String workDate, int inHour, int inMin, int inSec,
                      Integer outHour, Integer outMin, Integer outSec, int workMinutes) {
        this.attendId = attendId;
        this.staffId = staffId;
        this.workDate = workDate;
        this.inHour = inHour;
        this.inMin = inMin;
        this.inSec = inSec;
        this.outHour = outHour;
        this.outMin = outMin;
        this.outSec = outSec;
        this.workMinutes = workMinutes;
    }
}