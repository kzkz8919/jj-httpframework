package com.http.assertion;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class ListenerResult extends TestListenerAdapter {

	@Override
	public void onTestStart(ITestResult result) {
		//System.out.println("this is test start.");
		Assertion.begin();
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		System.out.println("this is test fail.");
		if (!Assertion.flag) {
			tr.setStatus(ITestResult.FAILURE);
		}
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		System.out.println("this is test skip.");
		if (!Assertion.flag) {
			tr.setStatus(ITestResult.FAILURE);
		}
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		System.out.println("this is test success.");
		if (!Assertion.flag) {
			tr.setStatus(ITestResult.FAILURE);
		}
	}

}
