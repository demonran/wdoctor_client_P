package com.test.wdoctor.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorUtil {
	
	private static ExecutorService executorService = Executors.newSingleThreadExecutor();
	
	public static void submit(Runnable runnable)
	{
		executorService.submit(runnable);
	}
}
