package model;


public final class XssControl {

	private enum BeforeEscapeChars {

		BEFORE_AND("&"),
		BEFORE_LT("<"),
		BEFORE_GT(">"),
		BEFORE_DOUBLE_QUOT("\""),
		BEFORE_SINGLE_QUOT("'");

		private final String beforeChar;

		private BeforeEscapeChars(String beforeChar) {
			this.beforeChar = beforeChar;
		}

		@Override
		public String toString() {
			return this.beforeChar;
		}
	}

	private enum AfterEscapeChars {

		AFTER_AND("&amp;"),
		AFTER_LT("&lt;"),
		AFTER_GT("&gt;"),
		AFTER_DOUBLE_QUOT("&quot;"),
		AFTER_SINGLE_QUOT("&#039;");

		private final String afterChar;

		private AfterEscapeChars(String afterChar) {
			this.afterChar = afterChar;
		}

		@Override
		public String toString() {
			return this.afterChar;
		}
	}

	private static final String NO_NAME = "noName";

	private XssControl() {}

	//XSSに使用される特殊文字をエスケープ処理して返すメソッド
	public static String replaceEscapeChar(String xssStr) {

		if (xssStr.isEmpty()) {
			//ユーザー名が無回答だったとき
			xssStr = NO_NAME;

		} else {
			//回答があるとき
			BeforeEscapeChars[] beforeChars = BeforeEscapeChars.values();
			AfterEscapeChars[]  afterChars  = AfterEscapeChars.values();

			for (int i = 0 ; i < beforeChars.length ; i++) {
				xssStr = xssStr.replace(beforeChars[i].toString(), afterChars[i].toString());
			}
		}

		return xssStr;
	}
}