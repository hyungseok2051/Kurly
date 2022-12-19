<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>



		<c:forEach var="goods" items="${glist}">
			<!--1번물품-->
			<div class="newproductcontent">
				<div class="realnewproductarea">
					<div class="realnewproduct">
						<!--goodsselect-->
						<a
							href="${contextPath}/good1/goodsdetail.do?goodscode=${goods.goodscode}&goodsprice=${goods.goodsprice}&goodstitle=${goods.goodstitle}">${goods.goodsimage}</a>
						<div>
							<button class="shoppingbasketbutton">
								<img
									src="data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNDUiIGhlaWdodD0iNDUiIHZpZXdCb3g9IjAgMCA0NSA0NSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPGNpcmNsZSBmaWxsPSIjMkEwMDM4IiBvcGFjaXR5PSIuNSIgY3g9IjIyLjUiIGN5PSIyMi41IiByPSIyMi41Ii8+CiAgICAgICAgPGcgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoMTEuMDMgMTQuMzgpIiBzdHJva2U9IiNGRkYiIHN0cm9rZS1saW5lY2FwPSJzcXVhcmUiIHN0cm9rZS1saW5lam9pbj0icm91bmQiPgogICAgICAgICAgICA8cGF0aCBzdHJva2Utd2lkdGg9IjEuNCIgZD0ibTIwLjQ2IDIuOTEtMi4xNyA5LjIzSDUuODdMMy43MSAyLjkxeiIvPgogICAgICAgICAgICA8Y2lyY2xlIHN0cm9rZS13aWR0aD0iMS4yIiBjeD0iMTYuMzUiIGN5PSIxNi44NiIgcj0iMS43Ii8+CiAgICAgICAgICAgIDxjaXJjbGUgc3Ryb2tlLXdpZHRoPSIxLjIiIGN4PSI3LjgyIiBjeT0iMTYuODYiIHI9IjEuNyIvPgogICAgICAgICAgICA8cGF0aCBzdHJva2Utd2lkdGg9IjEuNCIgZD0iTTAgMGgzLjAybDEuNDEgNS45OCIvPgogICAgICAgIDwvZz4KICAgIDwvZz4KPC9zdmc+Cg=="
									alt="장바구니 아이콘">
							</button>
						</div>
					</div>
				</div>
				<div class="newproductexplan">
					<span class="deliveryarea"> <span class="deliverytext">샛별배송</span>
					</span>
					<!--goodstitle-->
					<span class="deliveryinfo">${goods.goodstitle}</span>

					<div class="newproductprice">
						<div></div>
						<span class="newproductpricetext"> ${goods.goodsprice}<!--goodsprice-->
							<span class="won">원</span>
						</span>
					</div>
					<!--goodsinfo-->
					${goods.goodsinfo}
				</div>
				<input type="hidden" value="${goods.goodsselectname}"> <input
					type="hidden" value="${goods.goodscode}"> <input
					type="hidden" value="${goods.goodsrate}">
			</div>
		</c:forEach>


</body>
</html>