package jp.co.sss.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.lms.dto.PlaceDto;
import jp.co.sss.lms.mapper.MPlaceMapper;
import jp.co.sss.lms.util.Constants;

@Service
public class PlaceService {
	@Autowired MPlaceMapper placeMapper;
	public PlaceDto getPlaceInfoById(int lmsUserId) {
		PlaceDto placeDto = new PlaceDto();
		placeDto = placeMapper.getPlaceInfoById(lmsUserId,
				Constants.DB_HIDDEN_FLG_FALSE, Constants.DB_FLG_FALSE );
		return placeDto;
	}
}
