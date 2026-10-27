package jp.co.sss.lms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.sss.lms.dto.StudentSearchResultDto;
import jp.co.sss.lms.form.StudentSearchForm;
@Mapper
public interface MStudentSearchMapper {
	List<StudentSearchResultDto> getList(@Param("form")StudentSearchForm searchForm);
	List<StudentSearchResultDto> getCourseList(@Param("form")StudentSearchForm searchForm);
	List<StudentSearchResultDto> getCompanyList(@Param("form")StudentSearchForm searchForm);
	List<StudentSearchResultDto> getSearchResult(@Param("form")StudentSearchForm searchForm);
}

