package jp.co.sss.lms.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.sss.lms.dto.PlaceDto;
import jp.co.sss.lms.dto.UserAttendanceDto;

@Mapper
public interface MPlaceMapper {
	PlaceDto getPlaceInfoById(int placeId, int hiddenFlg, int deleteFlg);
	List<UserAttendanceDto> getUserAttendanceDto(int placeId, Date from, Date to, int deleteFlg);
}
