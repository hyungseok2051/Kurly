package goodsList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsListService {
	
	GoodsListDAO goodsListDAO;
	
	public GoodsListService() {
		goodsListDAO = new GoodsListDAO(); //생성자에서 객체를 생성
	}
	
	public Map listGoods(Map<String, Integer> pagingMap) {
		Map goodsMap = new HashMap();
		List<GoodsListVO> goodsList = goodsListDAO.selectAllArticles(pagingMap);
		int totGoods = goodsListDAO.totalNum();
		goodsMap.put("goodsList", goodsList);
		goodsMap.put("totGoods", totGoods);
		return goodsMap;
	}

}
