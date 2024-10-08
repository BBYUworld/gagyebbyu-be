package com.bbyuworld.gagyebbyu.domain.analysis.dto.param;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class AnalysisParam {

	/**
	 * default 현재 달
	 * month : 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
	 */
	private Integer month;

	/**
	 * default : 현재 년도
	 * year : 2024, 2023, 2021, ...
	 */
	private Integer year;
}
