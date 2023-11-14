package com.docmall.dto;

import lombok.Data;

@Data
public class CartDTOList {

	// C.PRO_NUM, C.CART_CODE, C.CART_AMOUNT, P.PRO_NAME, P.PRO_PRICE, P.PRO_IMG, P.PRO_DISCOUNT, P.PRO_UP_FOLDER
	
	private Long cart_code;
	private Integer pro_num;
	private int cart_amount;
	
	private	String	pro_name;
	private	int		pro_price;
	private	String	pro_img;	   // 스프링에서 별도로 처리
	private	int		pro_discount;
	private	String	pro_up_folder;  // 스프링에서 별도로 처리

}
