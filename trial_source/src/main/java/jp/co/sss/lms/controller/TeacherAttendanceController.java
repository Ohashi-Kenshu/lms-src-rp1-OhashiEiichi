package jp.co.sss.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.sss.lms.dto.StudentSearchResultDto;
import jp.co.sss.lms.form.StudentSearchForm;
import jp.co.sss.lms.service.StudentSearchService;

@Controller
public class TeacherAttendanceController {
	@Autowired private StudentSearchService studentSearchService;
	@PostMapping("/teacher/search")
	public String whatever(@ModelAttribute StudentSearchForm studentSearchForm, Model model) {
		List<StudentSearchResultDto> resultList = studentSearchService.getIndex(studentSearchForm);
			System.out.println("reached");
			model.addAttribute("resultList", resultList);
		return "attendance/searchStudent";
	}
}
