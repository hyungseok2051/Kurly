package goodsList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import Goods.GoodsDAO;
import Goods.GoodsVO;
import Kurly.Qna.Kurly_qnaDAO;
import Kurly.Qna.Kurly_qnaVO;
import Kurly.Review.Kurly_reviewDAO;
import Kurly.Review.Kurly_reviewVO;



@WebServlet("/good1/*")
public class GoodsListController extends HttpServlet {
	GoodsListDAO goodListDAO;
	GoodsDAO goodsdao;
	Kurly_qnaDAO qnaDAO;
	Kurly_reviewDAO reviewDAO;
	GoodsListService goodListService;

	@Override
	public void init() throws ServletException {
		goodListDAO = new GoodsListDAO();
		goodsdao = new GoodsDAO();
		qnaDAO = new Kurly_qnaDAO();
		reviewDAO = new Kurly_reviewDAO();
		goodListService =new GoodsListService();
	}

	private static String ART_IMAGE_REPO = "C:\\Users\\HS\\Desktop\\kurlyworkspace\\workspace\\MarketKurly2\\MarketKurly2\\src\\main\\webapp\\upload";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nextPage = null;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String action = request.getPathInfo();
		
		try {
//		상품리스트 불러오는부분
		List<GoodsListVO> goodsList = new ArrayList<GoodsListVO>();
		if (action == null || action.equals("/goodslist.do")) {
//			List<GoodsListVO> glist = goodListDAO.goodslist();
//			request.setAttribute("glist", glist);
//			nextPage = "/newproduct.jsp";
			String _section = request.getParameter("section");
			String _pageNum = request.getParameter("pageNum");
			int section = Integer.parseInt((_section == null ? "1" : _section));
			int pageNum = Integer.parseInt((_section == null ? "1" : _pageNum));
			Map<String, Integer> pagingMap= new HashMap<String, Integer>();
			pagingMap.put("section",section);
			pagingMap.put("pageNum",pageNum);
			Map goodsMap =  goodListService.listGoods(pagingMap);
		
			goodsMap.put("section",section);
			goodsMap.put("pageNum",pageNum);
			request.setAttribute("goodsMap", goodsMap);//조회된 글 목록을 glist로 바인딩 후 포워딩작업
			nextPage="/newproduct2.jsp";
			
			
			
		//품목별 찾는방법
		}else if (action.equals("/search.do")){
			String keyword = request.getParameter("keyword");
			System.out.println(keyword);
			List<GoodsListVO> findlist = goodListDAO.findlist(keyword);
			request.setAttribute("findlist", findlist);
			nextPage = "/FindProduct.jsp";
		//단어별검색	
		}else if(action.equals("/detailsearch.do")){
			String keyword = request.getParameter("keyword");
			System.out.println(keyword);
			List<GoodsListVO> findlist = goodListDAO.findword(keyword);
			request.setAttribute("findlist", findlist);
			nextPage = "/FindProduct.jsp";
		//높은가격순	
		}else if(action.equals("/highsearch.do")){
			List<GoodsListVO> findlist = goodListDAO.findhigh();
			request.setAttribute("findlist", findlist);
			nextPage = "/FindProduct.jsp";
		//낮은가격순	
		}else if(action.equals("/lowsearch.do")){
			List<GoodsListVO> findlist = goodListDAO.findlow();
			request.setAttribute("findlist", findlist);
			nextPage = "/FindProduct.jsp";
		//미만가격순	
		}else if(action.equals("/pricesearch.do")){
			int keyword = Integer.parseInt(request.getParameter("keyword"));
			List<GoodsListVO> findlist = goodListDAO.findprice(keyword);
			request.setAttribute("findlist", findlist);
			nextPage = "/FindProduct.jsp";
		//상품상세페이지확인
		}else if (action.equals("/goodsdetail.do")) {
			String goodscode = request.getParameter("goodscode");
			String goodsprice = request.getParameter("goodsprice");
			String goodstitle = request.getParameter("goodstitle");
			System.out.println(">>>>>>>>>>>>>>"+goodscode);

			List<Kurly_reviewVO> ReviewList = reviewDAO.selectAllReview(goodscode);
			request.setAttribute("ReviewList", ReviewList);

			List<Kurly_qnaVO> QnaList = qnaDAO.selectAllQna(goodscode);
			request.setAttribute("Qlist", QnaList);

			List<GoodsVO> gdetail = goodsdao.goodsdetail(goodscode);
			request.setAttribute("gdetail", gdetail);// 리스트를 보낸다.

			request.setAttribute("goodscode", goodscode);
			request.setAttribute("goodsprice", goodsprice);
			request.setAttribute("goodstitle", goodstitle);

			//System.out.println(gdetail.isEmpty());// 리스트가 NULL인지 아닌지 확인한다.
			//System.out.println(gdetail.toString());// gdetail hash 값으로 나온다. toString 문자열로 바꿔줘야지 확인 가능
			//System.out.println(QnaList.isEmpty());
			//System.out.println(QnaList.toString());
			//System.out.println(action + "실행중asd");

			nextPage = "/productDetailPage.jsp";
			// 1:1문의 게시글 작성 이동
		} else if (action.equals("/QnaAdd.do")) {
			HttpSession session = request.getSession();
			String _goodscode = request.getParameter("goodscode");
			GoodsVO goodsvo = goodsdao.findimg(_goodscode);
			System.out.println(goodsvo);
			request.setAttribute("goodsimg", goodsvo);
			request.setAttribute("goodscode", _goodscode);
			nextPage = "/qnaForm.jsp";
			// 게시글 추가
		} else if (action.equals("/insertQna.do")) {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String qnatitle = request.getParameter("qnatitle");
			String qnacontents = request.getParameter("qnacontents");
			String goodscode = request.getParameter("goodscode");

			Kurly_qnaVO qnaVO = new Kurly_qnaVO();
			qnaVO.setId(id);
			qnaVO.setName(name);
			qnaVO.setQnatitle(qnatitle);
			qnaVO.setQnacontents(qnacontents);
			qnaVO.setGoodscode(goodscode);
			qnaDAO.insertQna(qnaVO);
			nextPage = "goodsdetail.do";
			// 해당 게시글 삭제할때
		} else if (action.equals("/qndDel.do")) {
			String qnanum = request.getParameter("qnanum");
			System.out.println(qnanum);
			String goodscode = request.getParameter("goodscode");
			request.setAttribute("goodscode", goodscode);
			qnaDAO.delQna(qnanum);
			nextPage = "goodsdetail.do";
			// 게시글 수정페이지 이동
		} else if (action.equals("/qnaMod.do")) {
			String qnanum = request.getParameter("qnanum");
			Kurly_qnaVO qnaVO = qnaDAO.findqna(qnanum);
			request.setAttribute("qnaInfo", qnaVO);
			
			String goodscode = request.getParameter("goodscode");
			GoodsVO goodsvo = goodsdao.findimg(goodscode);
			request.setAttribute("gimg", goodsvo);
			
			request.setAttribute("goodscode", goodscode);

			nextPage = "/modForm.jsp";
			//qna수정완료
		} else if (action.equals("/qnaModOk.do")) {

			String qnanum = request.getParameter("qnanum");
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String qnatitle = request.getParameter("qnatitle");
			String qnacontents = request.getParameter("qnacontents");
			String goodscode = request.getParameter("goodscode");
			Kurly_qnaVO qnaVO = new Kurly_qnaVO(id, name, goodscode, qnatitle, qnacontents, qnanum);
			qnaDAO.updateQna(qnaVO);

			nextPage = "goodsdetail.do";
			// 후기작성
		} else if (action.equals("/reviewAdd.do")) {
			String goodscode = request.getParameter("goodscode");
			GoodsVO goodsvo = goodsdao.findimg(goodscode);
			System.out.println(goodsvo);
			request.setAttribute("goodsimg", goodsvo);
			request.setAttribute("goodscode", goodscode);
			nextPage = "/reviwForm.jsp";
		} else if (action.equals("/insertReview.do")) {
			int articleNo=0;
			Map<String , String>articleMap=upload(request, response);
			String id = articleMap.get("id");
			String goodscode = articleMap.get("goodscode");
			String reviewnum = articleMap.get("reviewnum");
			String reviewcontents = articleMap.get("reviewcontents");
			String reviewimage = articleMap.get("reviewimage");
			String goodsname = articleMap.get("goodsname");
			

			Kurly_reviewVO rVO = new Kurly_reviewVO();
			rVO.setId(id);
			rVO.setGoodscode(goodscode);
			rVO.setReviewnum(reviewnum);
			rVO.setReviewcontents(reviewcontents);
			rVO.setReviewimage(reviewimage);
			rVO.setGoodsname(goodsname);
			articleNo = reviewDAO.insertRview(rVO);
			if(reviewimage != null && reviewimage.length() !=0) {
				File srcFile = new File(ART_IMAGE_REPO+"\\"+"temp\\"+reviewimage);
				File destDir= new File(ART_IMAGE_REPO+"\\"+ articleNo);
				destDir.mkdir();//글번호생성
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
				srcFile.delete();
			}

			nextPage = "goodsdetail.do";
	    	  
	      }else if(action.equals("/rDelete.do")) {
	    	  String reviewnum = request.getParameter("reviewnum");
	    	  reviewDAO.delReview(reviewnum);
	    	  String goodscode = request.getParameter("goodscode");
			  request.setAttribute("goodscode", goodscode);
	    	  
	    	  nextPage="goodsdetail.do";
	    	  
	      }else if(action.equals("/rModify.do")) {
	    	 String reviewnum = request.getParameter("reviewnum");
	    	 Kurly_reviewVO reviewVO = reviewDAO.findreview(reviewnum);
	    	 request.setAttribute("reviewnum",reviewnum);
	    	 request.setAttribute("reviewinfo", reviewVO);
	    	 
	    	 String goodscode = request.getParameter("goodscode");
	    	 request.setAttribute("goodscode", goodscode);
	    	
	    	 nextPage = "/ModReviwForm.jsp";
	    	  
	      }else if(action.equals("/ModReviwOk.do")) {
	    	  String reviewcontents = request.getParameter("reviewcontents");
	    	  //String reviewimage = request.getParameter("reviewimage");
	    	  String reviewnum =request.getParameter("reviewnum");
	    	  String goodscode = request.getParameter("goodscode");
	    	  request.setAttribute("goodscode", goodscode);
	    	  Kurly_reviewVO reviewVO =  new Kurly_reviewVO();
	    	  reviewVO.setReviewcontents(reviewcontents);
	    	  //reviewVO.setReviewimage(reviewimage);
	    	  reviewVO.setReviewnum(reviewnum);
	    	  reviewDAO.updatereview(reviewVO);
	    	  
	      	  nextPage="goodsdetail.do";
	  	  
	      }
		 RequestDispatcher dispatcher=request.getRequestDispatcher(nextPage);
	     dispatcher.forward(request, response);
	} catch (Exception e) {
		System.out.println("요청 처리중 에러");
		System.out.println(e.getMessage());
	}
 
	     
	      
	}


//이미지 파일 업로드 + 새 글 관련 정보 저장
private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      Map<String, String> articleMap=new HashMap<String, String>();
      String encoding="utf-8";
      File currentDirPath=new File(ART_IMAGE_REPO);
      DiskFileItemFactory factory=new DiskFileItemFactory();
      factory.setRepository(currentDirPath);
      factory.setSizeThreshold(1024*1024); //웬만한 이미지 크기나 다 들어가나 이것보다 큰 크기의 이미지는 오류남
      ServletFileUpload upload=new ServletFileUpload(factory);
      try {
         List items=upload.parseRequest(request);
         for(int i=0; i<items.size(); i++) {
            FileItem fileItem=(FileItem)items.get(i);
            if(fileItem.isFormField()) {
               System.out.println(fileItem.getFieldName()+ "="+ fileItem.getString(encoding));
               //파일 업로드로 같이 전송된 새 글 관련 (제목 ,내용) 매개변수를 저장 한 후 변환 
               articleMap.put(fileItem.getFieldName(),fileItem.getString(encoding));
            }else {
               System.out.println("파라미터이름: "+fileItem.getFieldName());
               System.out.println("파일이름: " + fileItem.getName());
               System.out.println("파일크기: " + fileItem.getSize()+ "bytes");
               if(fileItem.getSize() > 0) {
                  int idx=fileItem.getName().lastIndexOf("\\");//74번쨰 줄에 이미지 있는 경로
                  if(idx == -1) {// C:\hj-lim\board\article_image로 돼서 이미지 경로를 못찾았으면
                     idx=fileItem.getName().lastIndexOf("/");
                  }
                  String fileName=fileItem.getName().substring(idx+1);
                  articleMap.put(fileItem.getFieldName(), fileName);
                  //업로드한 이미지를 일단 temp에 저장
                  File uploadFile=new File(currentDirPath+ "\\temp\\" + fileName);
                  fileItem.write(uploadFile);
               }
            }
         }
      } catch (Exception e) {
         System.out.println("파일 업로드 중 에러");
      }
      return articleMap;
}

}

