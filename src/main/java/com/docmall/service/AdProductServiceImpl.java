package com.docmall.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.docmall.domain.ProductVO;
import com.docmall.dto.Criteria;
import com.docmall.mapper.AdProductMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
// @RestController : 모든 매핑주소가 ajax호출로 사용하는 경우
public class AdProductServiceImpl implements AdProductService{

	private final AdProductMapper adProductMapper;
	
	@Override
	public void pro_insert(ProductVO vo) {
		adProductMapper.pro_insert(vo);
		
	}

	@Override
	public int getTotalcount(Criteria cri) {
		// TODO Auto-generated method stub
		return adProductMapper.getTotalcount(cri);
	}

	@Override
	public List<ProductVO> pro_list(Criteria cri) {
		// TODO Auto-generated method stub
		return adProductMapper.pro_list(cri);
	}
}
