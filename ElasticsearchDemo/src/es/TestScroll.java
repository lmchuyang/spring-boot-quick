package es;

import com.esutils.controller.Scroll;

public class TestScroll {

	//第一次调用 游标   http://192.168.11.237:9200/java_demo_index/_search?pretty&scroll=2m
	//第二次要用返回的游标ID再查询  http://192.168.11.237:9200/java_demo_index/scroll?scroll=2m&pretty&scroll_id=DnF1ZXJ5VGhlbkZldGNoBQAAAAAAABOxFmJ3RTFCZ2VzUXVhMGcyaGNndm9Ib2cAAAAAAAATshZid0UxQmdlc1F1YTBnMmhjZ3ZvSG9nAAAAAAAAE7UWYndFMUJnZXNRdWEwZzJoY2d2b0hvZwAAAAAAABOzFmJ3RTFCZ2VzUXVhMGcyaGNndm9Ib2cAAAAAAAATtBZid0UxQmdlc1F1YTBnMmhjZ3ZvSG9n
	//如果查询中包含聚合，只有最初的查询结果是聚合结果
	//Scanning Scroll API
	//如果只对查询结果感兴趣而不关心结果的顺序，可以使用更高效的scanning scroll。使用方法非常简单，只需在查询语句后加上“search_type=scan”即可。
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scroll scrollTest = new Scroll();
		scrollTest.scrollTest();
	}

}
