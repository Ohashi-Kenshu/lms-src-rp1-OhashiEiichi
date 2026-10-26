package jp.co.sss.lms.dto;

import lombok.Data;

/**
 *task57 受講生検索一覧DTO
 *@author 大橋 
 */
@Data
public class StudentSearchResultDto {
	/**ユーザーID*/
	private Integer userId;
	/**ユーザー名*/
	private String userName;
	/**コース名*/
	private String courseName;
	/**企業名*/
	private String companyName;
	/**会場名*/
	private String placeName;
}
