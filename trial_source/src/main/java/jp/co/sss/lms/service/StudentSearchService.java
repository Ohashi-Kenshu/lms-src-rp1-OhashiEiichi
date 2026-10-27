package jp.co.sss.lms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.lms.dto.StudentSearchResultDto;
import jp.co.sss.lms.form.StudentSearchForm;
import jp.co.sss.lms.mapper.MStudentSearchMapper;

@Service
public class StudentSearchService {
	@Autowired 
	private MStudentSearchMapper mStudentSearchMapper;
	
	public List<StudentSearchResultDto> getIndex(StudentSearchForm studensSearchForm) {
		List<StudentSearchResultDto> list = new ArrayList<>();
		list = mStudentSearchMapper.getList(studensSearchForm);
		return list;
		
	}
	public List<StudentSearchResultDto> getResult(StudentSearchForm studensSearchForm) {
		List<StudentSearchResultDto> list = new ArrayList<>();
		list = mStudentSearchMapper.getSearchResult(studensSearchForm);
		return list;
		
	}
}
