package model;

import java.util.List;

/**
 * ページング処理をセッションオブジェクトで実現するためのクラス
 * @author sisk ques
 *
 */
public final class HandlePageSessionBean {

	//1ページに表示するレコード数の最大値
	private static final int MAX_PAGE_RECORDS = 10;
	//一度に読み込むページ数
	private static final int LOAD_PAGE_VALUE = 3;

	//現在のページ番号
	private int currentPage;
	//ページ数 = 最後のページ番号
	private int pageValue;
	//カウントしたレコード数
	private int countRecords;
	//1度に取得するレコードのリスト
	private List<UserInfoDto> userRecordList;
	//ページ範囲のリスト
	private List<String> paginationList;
	//最後ページ要求
	private boolean requestLastPage;

	public HandlePageSessionBean() {}

	//取得するレコード数の最大値を計算して返すメソッド
	public int calcExtractMaxRecords() {
		return (MAX_PAGE_RECORDS * LOAD_PAGE_VALUE) + 1;
	}

	//データオフセットの計算値を返すメソッド
	public int calcDataOffset() {
		return (currentPage - 1) * MAX_PAGE_RECORDS;
	}

	//現在ページより前のページリンクのオフセット用計算値を返すメソッド
	public int calcPreviousPageOffset() {
		return LOAD_PAGE_VALUE - 1;
	}

	//現在ページより後のページリンクのオフセット用計算値を返すメソッド
	public int calcNextPageOffset() {
		return (int)Math.ceil(userRecordList.size() / (double)MAX_PAGE_RECORDS);
	}

	//beanのフィールドをリセットするメソッド
	public void resetBeanField() {
		this.currentPage     = 0;
		this.pageValue       = 0;
		this.countRecords    = 0;
		this.userRecordList  = null;
		this.paginationList  = null;
		this.requestLastPage = false;
	}

	public int getMaxPageRecords() {
		return MAX_PAGE_RECORDS;
	}

	public int getLoadPageValue() {
		return LOAD_PAGE_VALUE;
	}

	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageValue() {
		return pageValue;
	}
	public void setPageValue(int pageValue) {
		this.pageValue = pageValue;
	}

	public int getCountRecords() {
		return countRecords;
	}
	public void setCountRecords(int countRecords) {
		this.countRecords = countRecords;
	}

	public List<UserInfoDto> getUserRecordList() {
		return userRecordList;
	}
	public void setUserRecordList(List<UserInfoDto> userRecordList) {
		this.userRecordList = userRecordList;
	}

	public List<String> getPaginationList() {
		return paginationList;
	}
	public void setPaginationList(List<String> paginationList) {
		this.paginationList = paginationList;
	}

	public boolean isRequestLastPage() {
		return requestLastPage;
	}
	public void setRequestLastPage(boolean requestLastPage) {
		this.requestLastPage = requestLastPage;
	}

	@Override
	public String toString() {
		String beanInfo = String.format(
				"currentPage:%d、pageValue:%d、countRecords:%d、requestLastPage:%b",
				this.currentPage, this.pageValue, this.countRecords, this.requestLastPage);

		return beanInfo;
	}
}
