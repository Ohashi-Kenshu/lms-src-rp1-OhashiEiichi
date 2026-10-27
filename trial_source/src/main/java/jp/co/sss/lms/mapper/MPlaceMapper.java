package jp.co.sss.lms.mapper;

import org.apache.ibatis.annotations.Mapper;

import jp.co.sss.lms.dto.PlaceDto;

@Mapper
public interface MPlaceMapper {
	PlaceDto getPlaceInfoById(int placeId, int hiddenFlg, int deleteFlg);
}
