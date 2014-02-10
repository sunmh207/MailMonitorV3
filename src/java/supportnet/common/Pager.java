package supportnet.common;

public class Pager {
	private int totalRecords;
	private int totalPages;
	private int currentPage=1;
	private int pageSize = 20;

	public Pager(int curentPage, int pageSize) {
		this.currentPage = curentPage;
		this.pageSize = pageSize;
	}

	public Pager() {
		this(1, 20);
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int curentPage) {
		this.currentPage = curentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getStartRecord(){
		return pageSize*(currentPage-1)+1;
	}
	@Override
	public String toString() {
		return "Pager [totalRecords=" + totalRecords + ", totalPages="
				+ totalPages + ", curentPage=" + currentPage + ", pageSize="
				+ pageSize + "]";
	}

}
