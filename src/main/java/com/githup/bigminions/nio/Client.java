package com.githup.bigminions.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharsetEncoder;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by daren on 2017/10/13.
 */
public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public static void start() throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", SocketNioDemo.PORT));
        socketChannel.configureBlocking(false);
        // every 2s send message to server
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            try {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                String send = "Hello, World, " + LocalTime.now().format(DateTimeFormatter.ofPattern("mm:ss"));
                byte[] bytes = send.getBytes();
                LOGGER.info("[CLIENT] size : {}, send : {}", bytes.length, send);
                // write string
                byteBuffer.putInt(bytes.length);
                for (int i = 0; i < bytes.length; i++) {
                    byteBuffer.put(bytes[i]);
                }

                byteBuffer.flip();
                socketChannel.write(byteBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 2, 2, TimeUnit.SECONDS);
    }
}
