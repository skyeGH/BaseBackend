package com.skye.libra;

import com.skye.libra.util.thread.BatchThreadUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * .
 *
 * @author : gh
 * 2022/12/8
 */
public class Test {
	public static void main(String[] args) throws InterruptedException {
		List<Callable> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			BatchThreadUtil.CallableTask callableTask = new BatchThreadUtil.CallableTask(i);
			list.add(callableTask);
		}
		long start = System.currentTimeMillis();
		List result = BatchThreadUtil.getResult(list);
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		//List<String> list = BatchThreadUtil.way2();
		System.out.println(result);
	}
}
