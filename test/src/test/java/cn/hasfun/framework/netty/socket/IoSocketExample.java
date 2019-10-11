package cn.hasfun.framework.netty.socket;

import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 传统的IO编程
 * while(true){
 * socket = accept();
 * handle(socket)
 * }
 *
 * 多线程IO编程
 *
 * while(true){
 * socket = accept();
 * new thread(socket);
 * }
 * 一个线程维护一个IO
 *
 * 线程池IO编程
 * 线程池本身可以缓解线程创建-销毁的代价
 * 就是线程的粒度太大。每一个线程把一次交互的事情全部做了，包括读取和返回，甚至连接，
 *  线程同步的粒度太大了，限制了吞吐量
 *
 * NIO 有了reactor的雏形
 *
 *  后来演变，reactor 模式 是 IO多路复用的经典模型
 *
 *  netty实现
 */
public class IoSocketExample {


    /**
     * 单线程的socket服务端
     * 一次只能处理一个客户端
     * @throws Exception
     */
    @Test
    public void testBlockServer() throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket server = serverSocket.accept();// 一次一个socket
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(server.getInputStream(), "UTF-8"));
        String str;
        //通过while循环不断读取信息，
        while ((str = bufferedReader.readLine()) != null) {
            //输出打印
            System.out.println(str);
        }
    }

    /**
     * 多线程的socket服务端
     * 服务多个客户端
     * 一个客户端建立一个线程服务，
     * 弊端：1、线程数增多 ，线程数有限  2、线程数多的时候上下文切换消耗性能大
     * @throws Exception
     */
    @Test
    public void testMutiThreadBlockServer() throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);

        while (true) {
            Socket server = serverSocket.accept();// 等到链接
            //每当有一个客户端连接进来后，就启动一个单独的线程进行处理
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(server.getInputStream(), "UTF-8"));
                        String str;
                        //通过while循环不断读取信息，
                        while ((str = bufferedReader.readLine()) != null) {
                            //输出打印
                            System.out.println(str);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Test
    public void nioServer() throws Exception{
        Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(8899));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        while(true){
            //阻塞等待事件
            selector.select();
            // 事件列表
            Set selected = selector.selectedKeys();
            Iterator it = selected.iterator();
            while (it.hasNext()) {
                it.remove();
                //分发事件
                dispatch((SelectionKey) (it.next()));
            }
        }

    }

    private void dispatch(SelectionKey key) throws Exception {
        if (key.isAcceptable()) {
            register(key);//新链接建立，注册
        } else if (key.isReadable()) {
           // read(key);//读事件处理
        } else if (key.isWritable()) {
            //wirete(key);//写事件处理
        }
    }
    private void register(SelectionKey key) throws Exception {
        ServerSocketChannel server = (ServerSocketChannel) key
                .channel();
        // 获得和客户端连接的通道
        SocketChannel channel = server.accept();
        channel.configureBlocking(false);
        //客户端通道注册到selector 上
        //channel.register(this.selector, SelectionKey.OP_READ);
    }



}
