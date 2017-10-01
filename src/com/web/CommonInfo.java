package com.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

public class CommonInfo {
	public  volatile static  Vector<Info> loginInfo=new Vector<Info>();
	public  volatile static  Vector<Info> successInfo=new Vector<Info>();
	public  volatile static  Vector<Thread> processThread=new Vector<Thread>();
	public volatile static int accountNum;
	public volatile static int successNum;

}
