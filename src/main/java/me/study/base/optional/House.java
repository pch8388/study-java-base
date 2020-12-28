package me.study.base.optional;

import java.util.Arrays;
import java.util.Optional;

public class House {
	private String houseSn;
	private String roomSn;
	private House.Info info;

	enum Info {
		FIRST_ROOM("일번방", "CODE1"),
		SECOND_ROOM("이번방", "CODE2"),
		THIRD_ROOM("삼번방", "CODE3");

		private final String name;
		private final String code;

		Info(String name, String code) {
			this.name = name;
			this.code = code;
		}

		String getCode() {
			return code;
		}

		String getName() {
			return name;
		}
	}

	public static House fromText(String input) {
		final String[] strings = input.split("_");

		return Arrays.stream(Info.values())
			.filter(houseInfo -> input.startsWith(houseInfo.name))
			.findAny()
			.map(info -> {
				final House houseInfo = new House();
				houseInfo.info = info;
				houseInfo.houseSn = strings[0].replace(info.getName(), "");
				houseInfo.roomSn = strings[1].replaceAll("\\D+", "");
				return houseInfo;
			}).orElseGet(House::new);
	}

	public static House fromTextBad(String input) {
		final String[] strings = input.split("_");

		final Optional<Info> optionalInfo = Arrays.stream(Info.values())
			.filter(houseInfo -> input.startsWith(houseInfo.name))
			.findAny();

		// 이런 형태로 사용하면 null check 를 하는 것보다 못하다
		if (optionalInfo.isPresent()) {
			final House houseInfo = new House();
			houseInfo.info = optionalInfo.get();
			houseInfo.houseSn = strings[0].replace(houseInfo.info.getName(), "");
			houseInfo.roomSn = strings[1].replaceAll("\\D+", "");
			return houseInfo;
		}

		return new House();
	}

	public String getCode() {
		return info.getCode();
	}

	public String getHouseSn() {
		return houseSn;
	}

	public String getRoomSn() {
		return roomSn;
	}
}
