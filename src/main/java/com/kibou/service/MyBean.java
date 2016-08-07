package com.kibou.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    public MyBean(ApplicationArguments args) {
        boolean debug = args.containsOption("debug");
        List<String> files = args.getNonOptionArgs();
        // if run with "--debug logfile.txt" ==>then debug=true, files=["logfile.txt"]
        logger.info("debug options : {}",debug ? "present" : "absent");
        logger.info("args nonOptionArgs : {}",files);
    }

}