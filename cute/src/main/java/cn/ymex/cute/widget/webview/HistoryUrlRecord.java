package cn.ymex.cute.widget.webview;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * ClassName: HistoryRecordMan
 * Description:: 访问历史记录器
 * @author: ymex 2016年4月23日 下午2:17:52
 */
public class HistoryUrlRecord {

	private static HistoryUrlRecord historyRecordMan;

	private List<String> exceptUrlList;//不被记录url的集合
	private Stack<String> history ;

	private HistoryUrlRecord() {
		this.exceptUrlList = new ArrayList<String>();
		this.history = new Stack<String>();
	}
	
	public static HistoryUrlRecord getInstance(){
		if (historyRecordMan==null) {
			historyRecordMan = new HistoryUrlRecord();
		}
		return historyRecordMan;
	}

	/**
	 * 添加的url将不被记录
	 * @param url
     */
	public void addExceptUrl(String url){
		exceptUrlList.add(url);
	}

	/**
	 * 移除记录器中的Url
	 * @param url
     */
	public void removeExceptUrl(String url){
		exceptUrlList.remove(url);
	}

	/**
	 * 清空
	 */
	public void clearExceptUrl(){
		exceptUrlList.clear();
	}

	/**
	 * 添加url到历史记录
	 * @param url
     */
	public void addUrl(String url){
		addUrl(url, true);
	}

	/**
	 * 重新加载的
	 * @param url
     */
	public void reload(String url){
		if (!history.peek().equals(url)) {
			addUrl(url);
		}
	}

	/**
	 * 清除记录
	 */
	public void clearHistoryUrl(){
		if (history!=null) {			
			history.clear();
		}
	}

	/**
	 *
	 * @param url
	 * @param isadd
     */
	public  void addUrl(String url, boolean isadd){
		if (!isadd) {
			return;
		}
		if (!containsUrl(url)&&!url.trim().isEmpty()) {
			history.add(url);
		}
	}


	private  boolean containsUrl(String url){
		for (String exurl : exceptUrlList) {
			if (exurl.equals(url)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否能回退
	 * @return
     */
	public boolean canGoBack(){
		if (history.size()>=2) {
			return true;
		}
		return false;
	}

	/**
	 * 当前的历史记录url
	 * @return
     */
	public String currentUrl(){
		if (!history.isEmpty()) {
			return history.peek();
		}
		return null;
	}

	/**
	 * 得到回退的url(调用此方法前最好先判断 canGoBack 返回值)
	 * @return
     */
	public String backUrl(){
		if (history.size()>=2) {
			history.pop();
			return history.pop();
		}
		return history.peek();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < history.size(); i++) {
			builder.append(history.get(i));
			builder.append("\n");
		}
		builder.append("history url count："+history.size());
		return builder.toString();
	}
}
