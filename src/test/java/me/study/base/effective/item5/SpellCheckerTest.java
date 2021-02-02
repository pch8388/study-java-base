package me.study.base.effective.item5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SpellCheckerTest {

	@Test
	void findWord() {
		SpellChecker s = new SpellChecker(EnglishLexicon::new);
		assertTrue(s.isValid("a"));
		assertFalse(s.isValid("x"));
	}
}