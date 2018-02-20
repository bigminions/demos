package com.githup.bigminions.java9;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ProcessAPI {

    /**
     * java9 新增 ProcessHandler 接口，可以获取进程的基本信息，并且可以在进程结束时执行自定义动作
     * @param args
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("ping", "www.google.com").inheritIO();

        ProcessHandle processHandle = processBuilder.start().toHandle();
        System.out.println("cmd = " + processHandle.info().command());
        System.out.println("pid = " + processHandle.pid());
        System.out.println("user = " + processHandle.info().user());

        processHandle.onExit().whenCompleteAsync(((handle, throwable) -> {
            if (throwable != null) {
                System.out.println("something error when exit " + throwable);
            } else {
                System.out.println("exit success");
            }
        })).get();
    }
}
