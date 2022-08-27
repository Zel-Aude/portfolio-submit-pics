package model;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ディレクトリの相対パスを設定するenum
 *
 */
public enum PathConfig {

	//picturesディレクトリの相対パス文字列
	PICTURES_DIR_PATH("..\\workspace\\portfolio-submit-pics\\WebContent\\pictures") {
		@Override
		public Path getPath() {
			return Paths.get(PICTURES_DIR_PATH.toString());
		}
	};

	private final String path;

	private PathConfig(String path) {
		this.path = path;
	}

	public Path getPath() {
		return Paths.get("");
	}

	@Override
	public String toString() {
		return this.path;
	}
}
