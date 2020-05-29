package com.wl.java8.test;

public class SubClass /*extends MyClass*/ implements MyFun2, MyInterface{

	@Override
	public String getName() {
		return MyInterface.super.getName();
	}

}
