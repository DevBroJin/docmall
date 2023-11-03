package com.docmall.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ProductVO {

	private Integer pro_num; // 시퀀스 생성
	private Integer cg_code; // 2차 카테고리 코드
	private String 	pro_name;
	
	private int 	pro_price;
	private int 	pro_discount;
	
	private String 	pro_publisher;
	private String 	pro_content;
	
	private String 	pro_up_folder; 	// 스프링에서 별도로 처리
	private String 	pro_img; 		// 스프링에서 별도로 처리
	
	private int 	pro_amount;
	private String 	pro_buy; 
	
	private Date	pro_date; 
	private Date	pro_updatedate;
	
//  priavet MultipartFile uploadFile;
}
