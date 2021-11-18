package architecture.boundary.encrypt;

public class Encrypt {

	private final CharReader charReader;
	private final CharWriter charWriter;

	public Encrypt(CharReader charReader, CharWriter charWriter) {
		this.charReader = charReader;
		this.charWriter = charWriter;
	}

	public String encrypt() {
		return charWriter.write(charReader.read());
	}
}
