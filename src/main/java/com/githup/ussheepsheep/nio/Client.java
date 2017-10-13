package com.githup.ussheepsheep.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by daren on 2017/10/13.
 */
public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
    private static Selector selector;

    public static void start() throws IOException {
        selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        new Thread(() -> {
            LOGGER.info("client listen ");
            while (true) {
                try {
                    int count = selector.select();
                    if (count > 0) {
                        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                        if (iterator.hasNext()) {
                            SelectionKey key = iterator.next();
                            iterator.remove();
                            if (!key.isValid()) continue;
                            if (key.isConnectable()) connect(key);
                            if (key.isWritable()) {
                                send(key);
                                // every 2s send a message to port 12345
                                Thread.sleep(2000);
                            }
                            key.cancel();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        socketChannel.connect(new InetSocketAddress("localhost", SocketNioDemo.PORT));
    }

    private static void connect(SelectionKey key) throws IOException {
        // todo connect
        LOGGER.info("client connected");
        key.channel().register(selector, SelectionKey.OP_WRITE);
    }

    private static void send(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        String send = "Hello, World, " + System.currentTimeMillis();
        LOGGER.info("client send : {}", send);
        byte[] bytes = send.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            byteBuffer.put(bytes[i]);
        }
        socketChannel.write(byteBuffer);
    }
}
