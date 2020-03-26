package cn.hasfun.framework.cmd;

import org.apache.commons.exec.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 作用：在java程序中开启进程执行外部程序。
 * <p>
 * 使用举例：
 * <p>
 * 1.在java程序中开启打印机程序打印文件。
 * 2.在java程序中执行外部脚本程序。
 * java支持：
 * java提供了Runtime.exec() API来调用外部程序，Apache Commons Exec只是对其进行了封装。
 * <p>
 * 优点：（提供了对开启进程的管理）
 * <p>
 * 提供watchdog（看门狗）关闭挂起的进程。（比如打印机程序缺少纸，但是程序会处于阻塞状态无法结束）
 * <p>
 * 通过ExecuteResultHandler提供了异步执行外部程序的能力。默认是同步。
 * <p>
 * 使用更方便。
 */
public class LinuxCmd {


    private static final String DEFAULT_CHARSET = "GBK";

    /**
     * 执行指定命令
     *
     * @param command 命令
     * @return 命令执行完成返回结果
     * @throws IOException 失败时抛出异常，由调用者捕获处理
     */
    public static String exeCommand(String command) throws IOException {
        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            int exitCode = exeCommand(command, out);
            if (exitCode == 0) {
                System.out.println("命令运行成功！");
            } else {
                System.out.println("命令运行失败！");
            }
            return out.toString(DEFAULT_CHARSET);
        }
    }

    /**
     * 执行指定命令，输出结果到指定输出流中
     *
     * @param command 命令
     * @param out     执行结果输出流
     * @return 执行结果状态码：执行成功返回0
     * @throws ExecuteException 失败时抛出异常，由调用者捕获处理
     * @throws IOException      失败时抛出异常，由调用者捕获处理
     */
    public static int exeCommand(String command, OutputStream out) throws ExecuteException, IOException {
        CommandLine commandLine = CommandLine.parse(command);
        PumpStreamHandler pumpStreamHandler = null;
        if (null == out) {
            pumpStreamHandler = new PumpStreamHandler();
        } else {
            pumpStreamHandler = new PumpStreamHandler(out);
        }

        // 设置超时时间为10秒
        ExecuteWatchdog watchdog = new ExecuteWatchdog(10000);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        executor.setWatchdog(watchdog);

        return executor.execute(commandLine);
    }

    public static void main(String[] args) {
        try {
            String result = exeCommand("ifconfig");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
