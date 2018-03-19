package com.http.assertion;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({ListenerResult.class})
public class TestListener {

	@Test
	public void test1(){
	System.out.println("this is test1");
	}
	@Test
	public void test2(){
	System.out.println("this is test2");
	System.out.println(1/0);
	}
}
