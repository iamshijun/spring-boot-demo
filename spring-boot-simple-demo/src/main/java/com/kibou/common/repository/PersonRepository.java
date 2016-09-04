package com.kibou.common.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PersonRepository {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
			
	public PersonRepository(){
		logger.info("PersonRepository.init<>()");
	}
}
