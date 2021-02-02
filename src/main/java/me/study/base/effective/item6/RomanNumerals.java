package me.study.base.effective.item6;

import java.util.regex.Pattern;

public class RomanNumerals {

	static boolean isRomanNumeralWithStringMatchers(String word) {
		return word.matches("^(?=.)M*(C[MD]|D?C{0,3})"
			+ "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
	}

	private static final Pattern ROMAN = Pattern.compile(
		"^(?=.)M*(C[MD]|D?C{0,3})"
		+ "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

	static boolean isRomanNumeralWithPattern(String word) {
		return ROMAN.matcher(word).matches();
	}

}
