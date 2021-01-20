package testCases;


import org.testng.annotations.Test;

import utils.TestData;

public class Todolist_TestCases {

	
	@Test
	public void test() {
		
		TestData testdata = new TestData();
		
		testdata = testdata.returnTestData();
		
		System.out.println(testdata.getUserID());
		
		System.out.println(testdata.getPassword());
		
	}
	
}
