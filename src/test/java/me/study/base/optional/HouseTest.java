package me.study.base.optional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HouseTest {

	@ParameterizedTest
	@CsvSource({
		"일번방1_1방, CODE1, 1, 1", "이번방2_3방, CODE2, 2, 3", "삼번방4_5방, CODE3, 4, 5"
	})
	@DisplayName("text 를 받아 방 정보 생성")
	void create(String input, String code, String houseSn, String roomSn) {
		final House houseInfo = House.fromText(input);
		assertEquals(code, houseInfo.getCode());
		assertEquals(houseSn, houseInfo.getHouseSn());
		assertEquals(roomSn, houseInfo.getRoomSn());
	}

}