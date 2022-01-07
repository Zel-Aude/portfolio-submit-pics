package model;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreateIdBusinessLogic {

	private static final String BASE_ID = "at000";

	private static final Pattern ID_NUM_REG = Pattern.compile("[0-9]{3}");

	private static final int ID_NUM_MAX = 999;
	private static final int ADD_NUM    = 1;

	public CreateIdBusinessLogic() {}

	//DBから最新のIDを取得するようDAOに依頼し、新しいIDを作成して返すメソッド
	public String createId() {

		//DAOクラスをインスタンス化＆「user_member」テーブルから最新のIDを取得するよう依頼
		UserInfoDao      dao   = new UserInfoDao(TestDBConnection.generateConnection());
		Optional<String> optId = dao.selectLatestId();

		String newId = generateIdNum(optId.orElse(BASE_ID));

		return newId;
	}

	//DBから取得したIDを基に新しいID番号を作成して返すメソッド
	private String generateIdNum(String baseId) {

		Matcher idNumMatch = ID_NUM_REG.matcher(baseId);

		int idNum = 0;

		if(idNumMatch.find()) {
			idNum = Integer.parseInt(idNumMatch.group());
		}

		String newId = "";

		if(idNum < ID_NUM_MAX) {

			idNum += ADD_NUM;
			newId = String.format("at%03d", idNum);
		}

		return newId;
	}
}
