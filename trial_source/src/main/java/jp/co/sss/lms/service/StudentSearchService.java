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
	
	public List<StudentSearchResultDto> searchStudent(StudentSearchForm studensSearchForm, int limit, int page, String sortBy, String sortOrder) {
		List<StudentSearchResultDto> list = new ArrayList<>();
		int offset = limit * (page - 1);
		list = mStudentSearchMapper.getList(studensSearchForm, limit, offset, sortBy, sortOrder);
		return list;
		
	}
}
