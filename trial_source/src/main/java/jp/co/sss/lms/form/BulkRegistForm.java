package jp.co.sss.lms.form;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BulkRegistForm {
	private String placeName;
	private Date toDate;
	private Date fromDate;
	private List<DailyAttendanceForm> dailyAttendanceList;
}
