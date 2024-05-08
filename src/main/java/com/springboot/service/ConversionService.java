package com.springboot.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class ConversionService  {

	 public Date parseDate(String dateStr) throws ParseException {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        return sdf.parse(dateStr);
	    }
}
