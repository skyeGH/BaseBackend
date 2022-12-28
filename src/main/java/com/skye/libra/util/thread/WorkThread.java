package com.skye.libra.util.thread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * .
 *
 * @author : gh
 * 2022/12/27
 */
public class WorkThread {
	public static List<String> work(int i) throws InterruptedException {
		List<String> list = new ArrayList<>();
		list.add("当前时间" + new SimpleDateFormat("hh-mm-ss") + ";第" + i + "个线程");
		return list;
	}
}
