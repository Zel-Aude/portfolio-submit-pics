package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class ShowUserBusinessLogic {

	private static final String TABLE_DATA_HEADER = "<td>";
	private static final String TABLE_DATA_FOOTER = "</td>";

	private static final String LIST_ITEM_HEADER = "<li>";
	private static final String LIST_ITEM_FOOTER = "</li>";
	private static final String ANCHOR_HEADER    = "<a href=\"jump-%s\">";
	private static final String ANCHOR_FOOTER    = "</a>";

	private static final String FIRST_PAGE_LINK = "&lt;&lt;";
	private static final String LAST_PAGE_LINK  = "&gt;&gt;";

	private static final String FIRST_PAGE_HREF = "firstPage";
	private static final String LAST_PAGE_HREF  = "lastPage";

	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private HandlePageSessionBean pageBean;

	public ShowUserBusinessLogic() {}

	public ShowUserBusinessLogic(HandlePageSessionBean bean) {
		this.pageBean = bean;
	}

	//全てのユーザーデータを取得するメソッド、使用しない
	public Optional<List<UserInfoDto>> showAllUser() {

		UserInfoDao dao = new UserInfoDao(TestDBConnection.generateConnection());

		Optional<List<UserInfoDto>> optDtoList = dao.selectAllUser();

		return optDtoList;
	}

	//任意のユーザーデータ範囲を取得して、Beanにセットするメソッド
	public void showUserAnyRange() {

		UserInfoDao dao = new UserInfoDao(TestDBConnection.generateConnection());

		List<UserInfoDto> anyRangeDtoList = dao.selectUserAnyRange(
				pageBean.calcExtractMaxRecords(), pageBean.calcDataOffset());

		if(!anyRangeDtoList.isEmpty()) {
			pageBean.setUserRecordList(anyRangeDtoList);

		} else {
			pageBean.setUserRecordList(Collections.emptyList());
		}
	}

	//最後のユーザーデータ範囲を取得して、Beanにセットするメソッド
	public void showUserLastRange() {

		UserInfoDao dao = new UserInfoDao(TestDBConnection.generateConnection());

		int maxPageRecords = pageBean.getMaxPageRecords();

		Map<Integer, List<UserInfoDto>> lastRangeMap = dao.selectUserLastRange(maxPageRecords);

		if(!lastRangeMap.isEmpty()) {

			for(Integer idCount : lastRangeMap.keySet()) {

				pageBean.setCountRecords(idCount);

				int pageValue = (int)Math.ceil(idCount / (double)maxPageRecords);
				pageBean.setPageValue(pageValue); //現在は値を利用していない
				pageBean.setCurrentPage(pageValue);

				List<UserInfoDto> dtoList = lastRangeMap.get(idCount);

				int lastPageRecordsFraction = idCount % maxPageRecords;

				if(lastPageRecordsFraction != 0) {
					dtoList = dtoList.subList(0, lastPageRecordsFraction);
				}

				Collections.reverse(dtoList);
				pageBean.setUserRecordList(dtoList);
			}

		} else {
			pageBean.setUserRecordList(Collections.emptyList());
		}
	}

	//ページリンクの表示要素を作成して、Beanにセットするメソッド
	public void createPageLinkDisplay() {

		List<String> pageLinkList = new ArrayList<String>();

		int currentPage   = pageBean.getCurrentPage();
		int loadPageValue = pageBean.getLoadPageValue();

		if(currentPage > loadPageValue) {
			pageLinkList.add(FIRST_PAGE_LINK);
		}

		if(currentPage > 1) {

			for(int i = pageBean.calcPreviousPageOffset() ; i > 0 ; i--) {

				if(currentPage - i < 1) {
					continue;
				}

				pageLinkList.add(String.valueOf(currentPage - i));
			}
		}

		int userRecordListSize = pageBean.getUserRecordList().size();
		int maxPageRecords     = pageBean.getMaxPageRecords();

		if(!(pageBean.calcDataOffset() == 0 && userRecordListSize <= maxPageRecords)) {
			pageLinkList.add(String.valueOf(currentPage));
		}

		if(userRecordListSize > maxPageRecords) {

			for(int i = 1 ; i < loadPageValue ; i++) {

				if(i == pageBean.calcNextPageOffset()) {
					break;
				}

				pageLinkList.add(String.valueOf(currentPage + i));
			}
		}

		if(userRecordListSize == pageBean.calcExtractMaxRecords()) {
			pageLinkList.add(LAST_PAGE_LINK);
		}

		pageLinkList = createPageLinkHtml(pageLinkList);

		pageBean.setPaginationList(pageLinkList);
	}

	//ページリンクのhtml文を作成して返すメソッド
	private List<String> createPageLinkHtml(List<String> pageLinkList) {

		StringBuilder sb                = new StringBuilder();
		List<String>  pageLinkHtmlList = new ArrayList<String>();

		for(String pl : pageLinkList) {

			sb.append(LIST_ITEM_HEADER);

			if(pl.equals(String.valueOf(pageBean.getCurrentPage()))) {
				sb.append(pl);

			} else {

				String formattedAnchorHeader = "";

				switch(pl) {

					case FIRST_PAGE_LINK:
						formattedAnchorHeader = String.format(ANCHOR_HEADER, FIRST_PAGE_HREF);
						break;

					case LAST_PAGE_LINK:
						formattedAnchorHeader = String.format(ANCHOR_HEADER, LAST_PAGE_HREF);
						break;

					default:
						formattedAnchorHeader = String.format(ANCHOR_HEADER, pl);
						break;
				}

				sb.append(formattedAnchorHeader);
				sb.append(pl);
				sb.append(ANCHOR_FOOTER);
			}

			sb.append(LIST_ITEM_FOOTER);

			pageLinkHtmlList.add(sb.toString());

			sb.delete(0, sb.length());
		}

		return pageLinkHtmlList;
	}

	//ユーザーデータを表示するhtml文を作成して返すメソッド
	public String createAllUserDisplay(UserInfoDto dto, StringBuilder sb) {

		String    userId   = dto.getUserId();
		String    userName = dto.getUserName();
		Timestamp timeTemp = dto.getTime();

		String timeDisplay = SDF.format(timeTemp);

		List<String> userDataList = Arrays.asList(
				userId, XssControl.replaceEscapeChar(userName), timeDisplay);

		for(String ud : userDataList) {
			//html文の生成
			sb.append(TABLE_DATA_HEADER);
			sb.append(ud);
			sb.append(TABLE_DATA_FOOTER);
		}

		return sb.toString();
	}
}