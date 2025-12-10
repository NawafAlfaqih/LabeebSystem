package org.example.labeebsystem.DTO_out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttendanceReportDTO {

    private String courseName;
    private Integer totalSessions;
    private Integer attendedCount;
    private Integer absentCount;
    private Integer lateCount;
    private Double attendancePercentage;
}
