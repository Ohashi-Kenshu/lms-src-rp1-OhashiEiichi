package jp.co.sss.lms.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;
import jp.co.sss.lms.dto.AttendanceManagementDto;
import jp.co.sss.lms.dto.LoginUserDto;
import jp.co.sss.lms.dto.StudentSearchResultDto;
import jp.co.sss.lms.form.AttendanceForm;
import jp.co.sss.lms.form.StudentSearchForm;
import jp.co.sss.lms.service.StudentAttendanceService;
import jp.co.sss.lms.service.StudentSearchService;
import jp.co.sss.lms.util.AttendanceUtil;
import jp.co.sss.lms.util.Constants;

/**
 * 勤怠管理コントローラ
 * 
 * @author 東京ITスクール
 */
@Controller
@RequestMapping("/attendance")
public class AttendanceController {

	@Autowired
	private StudentAttendanceService studentAttendanceService;
	@Autowired
	private StudentSearchService studentSearchService;
	@Autowired
	private LoginUserDto loginUserDto;

	/**
	 * 勤怠管理画面 初期表示
	 * 
	 * @param lmsUserId
	 * @param courseId
	 * @param model
	 * @return 勤怠管理画面
	 * @throws ParseException
	 */
	@RequestMapping(path = "/detail", method = RequestMethod.GET)
	public String index(Model model) throws ParseException {
		Integer courseId = loginUserDto.getCourseId();
		Integer lmsUserId = loginUserDto.getLmsUserId();
		//大橋-task.25
		//現在より過去に未入力が無いかチェック(サービスクラス)
		Boolean hasNotEnter = studentAttendanceService.notEnterCheck();
		model.addAttribute("hasNotEnter", hasNotEnter);
		// 勤怠一覧の取得
		//上で設定した2種のIDを引数に、勤怠情報リストを取得
		List<AttendanceManagementDto> attendanceManagementDtoList = studentAttendanceService
				.getAttendanceManagement(courseId, lmsUserId);
		model.addAttribute("attendanceManagementDtoList", attendanceManagementDtoList);
		return "attendance/detail";
	}

	/**
	 * 勤怠管理画面 『出勤』ボタン押下
	 * 
	 * @param model
	 * @return 勤怠管理画面
	 */
	@RequestMapping(path = "/detail", params = "punchIn", method = RequestMethod.POST)
	public String punchIn(Model model) {

		// 更新前のチェック
		String error = studentAttendanceService.punchCheck(Constants.CODE_VAL_ATWORK);
		model.addAttribute("error", error);
		// 勤怠登録
		if (error == null) {
			String message = studentAttendanceService.setPunchIn();
			model.addAttribute("message", message);
		}
		// 一覧の再取得
		List<AttendanceManagementDto> attendanceManagementDtoList = studentAttendanceService
				.getAttendanceManagement(loginUserDto.getCourseId(), loginUserDto.getLmsUserId());
		model.addAttribute("attendanceManagementDtoList", attendanceManagementDtoList);

		return "attendance/detail";
	}

	/**
	 * 勤怠管理画面 『退勤』ボタン押下
	 * 
	 * @param model
	 * @return 勤怠管理画面
	 */
	@RequestMapping(path = "/detail", params = "punchOut", method = RequestMethod.POST)
	public String punchOut(Model model) {

		// 更新前のチェック
		String error = studentAttendanceService.punchCheck(Constants.CODE_VAL_LEAVING);
		model.addAttribute("error", error);
		// 勤怠登録
		if (error == null) {
			String message = studentAttendanceService.setPunchOut();
			model.addAttribute("message", message);
		}
		// 一覧の再取得
		List<AttendanceManagementDto> attendanceManagementDtoList = studentAttendanceService
				.getAttendanceManagement(loginUserDto.getCourseId(), loginUserDto.getLmsUserId());
		model.addAttribute("attendanceManagementDtoList", attendanceManagementDtoList);

		return "attendance/detail";
	}

	/**
	 * 勤怠管理画面 『勤怠情報を直接編集する』リンク押下
	 * 
	 * @param model
	 * @return 勤怠情報直接変更画面
	 */
	@RequestMapping(path = "/update")
	public String update(@ModelAttribute AttendanceForm attendanceForm, Model model) {

		// 勤怠管理リストの取得
		List<AttendanceManagementDto> attendanceManagementDtoList = studentAttendanceService
				.getAttendanceManagement(loginUserDto.getCourseId(), loginUserDto.getLmsUserId());
		// 勤怠フォームの生成
		attendanceForm = studentAttendanceService
				.setAttendanceForm(attendanceManagementDtoList);
		model.addAttribute("attendanceForm", attendanceForm);

		return "attendance/update";
	}

	/**
	 * 勤怠情報直接変更画面 『更新』ボタン押下
	 * 
	 * @param attendanceForm
	 * @param model
	 * @param result
	 * @return 勤怠管理画面
	 * @throws ParseException
	 */
	@RequestMapping(path = "/update", params = "complete", method = RequestMethod.POST)
	public String complete(@Valid @ModelAttribute AttendanceForm attendanceForm, BindingResult result, Model model)
			throws ParseException {
		studentAttendanceService.formatConversion(attendanceForm);
		studentAttendanceService.updateInputCheck(attendanceForm, result);
		if(result.hasErrors()) {
			AttendanceUtil attendanceUtil = new AttendanceUtil();
			attendanceForm.setBlankTimes(attendanceUtil.setBlankTime());
			attendanceForm.setHourTimes(attendanceUtil.prepareHourAndMin("h"));
			attendanceForm.setMinTimes(attendanceUtil.prepareHourAndMin("m")); 
			return "attendance/update";
		}
		// 更新
		String message = studentAttendanceService.update(attendanceForm);
		model.addAttribute("message", message);
		// 一覧の再取得
		List<AttendanceManagementDto> attendanceManagementDtoList = studentAttendanceService
				.getAttendanceManagement(loginUserDto.getCourseId(), loginUserDto.getLmsUserId());
		model.addAttribute("attendanceManagementDtoList", attendanceManagementDtoList);

		return "attendance/detail";
	}
	/**
	 * 勤怠管理画面 『勤怠情報を直接編集する』リンク押下
	 * 
	 * @param model
	 * @return 勤怠情報直接変更画面
	 */
	@PostMapping("/list")
	public String whatever(@ModelAttribute StudentSearchForm studentSearchForm, Model model) {
		List<StudentSearchResultDto> resultList = studentSearchService.getResult(studentSearchForm);
			model.addAttribute("resultList", resultList);
		return "attendance/searchStudent";
	}
	/**
	 * task 57 勤怠情報管理(受講生一覧)
	 * @author 大橋
	 * 指定件数に応じて受講生検索結果一覧を表示
	 */
	@GetMapping("/list")
	public String studentIndex(@ModelAttribute StudentSearchForm studentSearchForm, Model model) {
		studentSearchForm.setPlaceName(loginUserDto.getPlaceName());
		List<StudentSearchResultDto> resultList = studentSearchService.getIndex(studentSearchForm);
		model.addAttribute("studentSearchForm", studentSearchForm);
		model.addAttribute("resultList", resultList);
		return "attendance/searchStudent";
	}
}