package com.githup.bigminions.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {

        private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
        private static Selector selector;

        public static void start() throws IOException {
            selector = Selector.open();
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.configureBlocking(false);

            ServerSocket serverSocket = channel.socket();
            serverSocket.bind(new InetSocketAddress(SocketNioDemo.PORT));

            channel.register(selector, SelectionKey.OP_ACCEPT);

            new Thread(() -> {
                LOGGER.info("server listen port on : {} ...", SocketNioDemo.PORT);
                while (true) {
                    try {
                        int count = selector.select();
                        if (count > 0) {
                            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                            if (iterator.hasNext()) {
                                SelectionKey key = iterator.next();
                                iterator.remove();
                                if (!key.isValid()) continue;
                                if (key.isAcceptable()) accept(key);
                                if (key.isReadable()) read(key);
                                key.cancel();
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        private static void accept(SelectionKey key) throws IOException {
            LOGGER.info("client will be accept ");
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        }

        private static void read(SelectionKey key) throws IOException {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            // this example is simple : just support 1024 byte and string
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int num = 0;
            num = socketChannel.read(byteBuffer);
            byte[] bytes = new byte[num];
            for (int i = 0; i < num; i++) {
                bytes[i] = byteBuffer.get();
            }
            String recv = new String(bytes);
            LOGGER.info("server recv : " + recv);
        }
    }