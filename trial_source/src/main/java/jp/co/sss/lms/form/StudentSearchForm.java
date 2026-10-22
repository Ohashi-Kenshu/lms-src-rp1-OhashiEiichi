package jp.co.sss.lms.form;

import lombok.Data;

/**
 * task57 受講生検索Form
 * @author 大橋
 * 
 * 
 */
@Data
public class StudentSearchForm {
	/**コース名*/
	private String courseName;
	/**会場名*/
	private String placeName;
	/**企業名*/
	private String companyName;
	/**ユーザー名*/
	private String userName;
}
