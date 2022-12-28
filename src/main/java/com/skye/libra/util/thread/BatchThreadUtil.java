package com.skye.libra.util.thread;

import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * .
 *
 * @author : gh
 * 2022/12/27
 */
public class BatchThreadUtil {

	public static List<String> batchThread() {
		Long start = System.currentTimeMillis();
		//开启多线程
		ExecutorService exs = Executors.newFixedThreadPool(10);
		//结果集
		List<String> list = new ArrayList<String>();
		try {

			List<Future<String>> futureList = new ArrayList<Future<String>>();
			//1.高速提交10个任务，每个任务返回一个Future入list
			for (int i = 0; i < 10; i++) {
				futureList.add(exs.submit(new CallableTask(i + 1)));
			}
			Long getResultStart = System.currentTimeMillis();
			System.out.println("结果归集开始时间=" + new Date());
			//2.结果归集，用迭代器遍历futureList,高速轮询（模拟实现了并发），任务完成就移除
			while (futureList.size() > 0) {
				Iterator<Future<String>> iterable = futureList.iterator();
				//遍历一遍
				while (iterable.hasNext()) {
					Future<String> future = iterable.next();
					//如果任务完成取结果，否则判断下一个任务是否完成
					if (future.isDone() && !future.isCancelled()) {
						//获取结果
						String i = future.get();
						System.out.println("任务i=" + i + "获取完成，移出任务队列！" + new Date());
						list.add(i);
						//任务完成移除任务
						iterable.remove();
					} else {
						Thread.sleep(1);//避免CPU高速运转，这里休息1毫秒，CPU纳秒级别
					}
				}
			}
			System.out.println("list=" + list);
			System.out.println("总耗时=" + (System.currentTimeMillis() - start) + ",取结果归集耗时=" + (System.currentTimeMillis() - getResultStart));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			exs.shutdown();
		}
		return list;
	}

	public static class CallableTask implements Callable<String> {
		Integer i;

		public CallableTask(Integer i) {
			super();
			this.i = i;
		}

		@Override
		public String call() throws Exception {
			if (i == 1) {
				Thread.sleep(3000);//任务1耗时3秒
			} else if (i == 5) {
				Thread.sleep(5000);//任务5耗时5秒
			} else {
				Thread.sleep(1000);//其它任务耗时1秒
			}
			System.out.println("task线程：" + Thread.currentThread().getName() + "任务i=" + i + ",完成！" + new Date());
			return i + "";
		}
	}


	public static List<String> way2() {
		Long start = System.currentTimeMillis();
		//结果集
		List<String> list2 = new ArrayList<String>();
		//定长10线程池
		ExecutorService exs = Executors.newFixedThreadPool(10);
		int[] ints = {2, 1, 3, 4, 5, 6, 7, 8, 9, 10};
		final List<Integer> taskList = new ArrayList<Integer>();
		for (int anInt : ints) {
			taskList.add(anInt);
		}
		try {
			CompletableFuture[] cfs = taskList.stream().map(object -> CompletableFuture.supplyAsync(() -> calc(object), exs)
					.thenApply(h -> Integer.toString(h))
					//如需获取任务完成先后顺序，此处代码即可
					.whenComplete((v, e) -> {
						System.out.println("任务" + v + "完成!result=" + v + "，异常 e=" + e + "," + new Date());
						list2.add(v);
					})).toArray(CompletableFuture[]::new);
			//等待总任务完成，但是封装后无返回值，必须自己whenComplete()获取
			CompletableFuture.allOf(cfs).join();
			System.out.println("任务完成先后顺序，结果list2=" + list2 + "；任务提交顺序，结果list=" + list2 + ",耗时=" + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			exs.shutdown();
		}
		return list2;
	}

	public static Integer calc(Integer i) {
		try {
			if (i == 1) {
				//任务1耗时3秒
				Thread.sleep(3000);
			} else if (i == 5) {
				//任务5耗时5秒
				Thread.sleep(5000);
			} else {
				//其它任务耗时1秒
				Thread.sleep(1000);
			}
			System.out.println("task线程：" + Thread.currentThread().getName() + "任务i=" + i + ",完成！+" + new Date());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * @param futures List
	 * @return
	 * @Description 组合多个CompletableFuture为一个CompletableFuture, 所有子任务全部完成，组合后的任务才会完成。带返回值，可直接get.
	 * @author diandian.zhang
	 * @date 2017年6月19日下午3:01:09
	 * @since JDK1.8
	 */
	public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
		//1.构造一个空CompletableFuture，子任务数为入参任务list size
		CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
		//2.流式（总任务完成后，每个子任务join取结果，后转换为list）
		return allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
	}

	/**
	 * @param futures Stream
	 * @return
	 * @Description Stream流式类型futures转换成一个CompletableFuture, 所有子任务全部完成，组合后的任务才会完成。带返回值，可直接get.
	 * @author diandian.zhang
	 * @date 2017年6月19日下午6:23:40
	 * @since JDK1.8
	 */
	public static <T> CompletableFuture<List<T>> sequence(Stream<CompletableFuture<T>> futures) {
		List<CompletableFuture<T>> futureList = futures.filter(f -> f != null).collect(Collectors.toList());
		return sequence(futureList);
	}


	public static List getResult(List<Callable> callables) {
		List<Object> result = new ArrayList<>();
		List<FutureTask> futureTasks = new ArrayList<>();
		if (callables == null) {
			return null;
		}
		for (Callable callable : callables) {
			FutureTask futureTask = new FutureTask<>(callable);
			Thread thread = new Thread(futureTask);
			thread.start();
			futureTasks.add(futureTask);
		}
		if (futureTasks == null) {
			return null;
		}
		Iterator<FutureTask> futureTaskIterator = futureTasks.iterator();
		while (futureTaskIterator.hasNext()) {
			FutureTask futureTask = futureTaskIterator.next();
			while (true) {
				if (futureTask.isDone() && !futureTask.isCancelled()) {
					try {
						result.add(futureTask.get());
					} catch (InterruptedException | ExecutionException e) {
						throw new RuntimeException(e);
					} finally {
						break;
					}
				}
			}
		}

		for (FutureTask futureTask : futureTasks) {
			while (true) {
				if (futureTask.isDone() && !futureTask.isCancelled()) {
					try {
						result.add(futureTask.get());
					} catch (InterruptedException | ExecutionException e) {
						throw new RuntimeException(e);
					} finally {
						break;
					}
				}
			}
		}

		return result;
	}

}
