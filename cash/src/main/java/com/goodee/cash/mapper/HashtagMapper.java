package com.goodee.cash.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.cash.vo.Hashtag;

@Mapper
public interface HashtagMapper {

	List<Map<String, Object>> selectTagCountByMonth(Map<String, Object> paramMap);
	
	int insertHashtag(Hashtag hashtag);
	
	int deleteHashtag(Hashtag hashtag);
}
