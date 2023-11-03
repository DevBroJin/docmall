package com.docmall.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.docmall.domain.CategoryVO;
import com.docmall.domain.ProductVO;
import com.docmall.mapper.AdCategoryMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdCategoryServiceImpl implements AdCategoryService{

	private final AdCategoryMapper adCategoryMapper;
	
	@Override
	public List<CategoryVO> getFirstCategoryList() {
		return adCategoryMapper.getFirstCategoryList();
	}
	
	@Override
	public List<CategoryVO> getSecondCategoryList(Integer cg_parent_code) {
		return adCategoryMapper.getSecondCategoryList(cg_parent_code);
	}
	@Override
	public void pro_insert(ProductVO vo) {
		
	}
}
