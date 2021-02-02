package me.study.base.effective.item5;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public class SpellChecker {
	 private final Lexicon dictionary;

	public SpellChecker(Supplier<Lexicon> dictionary) {
		this.dictionary = Objects.requireNonNull(dictionary.get());
	}

	public boolean isValid(String word) {
		return dictionary.isValid(word);
	}
}

interface Lexicon {
	boolean isValid(String word);
}

class EnglishLexicon implements Lexicon {
	private final static Map<String, String> words;

	static {
		words = new HashMap<>();
		words.put("a", "word - a");
		words.put("b", "word - b");
		words.put("c", "word - c");
		words.put("d", "word - d");
	}

	public boolean isValid(String word) {
		return words.containsKey(word);
	}
}

class KoreanLexicon implements Lexicon {
	private final static Map<String, String> words;

	static {
		words = new HashMap<>();
		words.put("가", "word - 가");
		words.put("나", "word - 나");
		words.put("다", "word - 다");
		words.put("라", "word - 라");
	}

	public boolean isValid(String word) {
		return words.containsKey(word);
	}
}