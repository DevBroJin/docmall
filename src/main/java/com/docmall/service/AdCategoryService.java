package com.docmall.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

public interface AdCategoryService {

	List<CategoryVO> getFirstCategoryList();

	List<CategoryVO> getSecondCategoryList(Integer cg_parent_code);
	
	void pro_insert(ProductVO vo);
}
