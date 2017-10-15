package com.githup.bigminions.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
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
            LOGGER.info("server listen port on : {} ...", SocketNioDemo.PORT);

            new Thread(() -> {
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
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error("server error !", e);
                    }
                }
            }).start();
        }

        private static void accept(SelectionKey key) {
            try {
                LOGGER.info("client will be accept ");
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.register(selector, SelectionKey.OP_READ);
            } catch (ClosedChannelException e) {
                LOGGER.error("something error when close !", e);
            } catch (IOException e) {
                LOGGER.error("something error in IO !", e);
            }
        }

        private static void read(SelectionKey key) {
            try {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                // this example is simple : just support 1024 byte and string
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                if (socketChannel.read(byteBuffer) > 0) {
                    // read string
                    byteBuffer.flip();
                    int strLen = byteBuffer.getInt();
                    byte[] bytes = new byte[strLen];
                    for (int i = 0; i < strLen; i++) {
                        bytes[i] = byteBuffer.get();
                    }

                    String recv = new String(bytes);
                    LOGGER.info("[SERVER] size : {}, read : {}", bytes.length, recv);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }