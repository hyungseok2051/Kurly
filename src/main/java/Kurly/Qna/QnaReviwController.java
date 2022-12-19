//package Kurly.Qna;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.List;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import GoodList.GoodListDAO;
//import GoodList.GoodListVO;
//import Goods.GoodsDAO;
//
//@WebServlet("/qna/*")
//public class QnaReviwController extends HttpServlet {
//	Kurly_qnaDAO qnaDAO;
//
//	@Override
//	public void init() throws ServletException {
//		qnaDAO = new Kurly_qnaDAO();
//	}
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		doHandle(request, response);
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		doHandle(request, response);
//	}
//
//	private void doHandle(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
////		String nextPage = null;
////		HttpSession session;
////		request.setCharacterEncoding("utf-8");
////		response.setContentType("text/html;charset=utf-8");
////		String action = request.getPathInfo();
////		System.out.println("액션 실행중>>" + action);
////
////		if (action == null || action.equals("/qnalist.do")) {
////			List<Kurly_qnaVO> QnaList = qnaDAO.selectAllQna();
////			request.setAttribute("QnaList", QnaList);
////			nextPage = "kurly/test4.jsp";
////		} else if (action.equals("/qnaForm.do")) {
////			nextPage="/qnaForm.jsp";
////		}
////
////		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
////
//	}
//
//}
