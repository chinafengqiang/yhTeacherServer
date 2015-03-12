package com.study.service.impl;

import java.util.Comparator;

import com.study.model.part.TestPaperOption;

public class TestPaperOptionComparator implements Comparator<TestPaperOption>{

	public int compare(TestPaperOption t0, TestPaperOption t1) {
		
		return t1.getSortFlag().compareTo(t0.getSortFlag()); 
	}
}
