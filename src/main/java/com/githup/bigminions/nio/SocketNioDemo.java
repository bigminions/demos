package com.githup.bigminions.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by daren on 2017/10/13.
 * ref : http://rox-xmlrpc.sourceforge.net/niotut/
 */
public class SocketNioDemo {

    public static final int PORT = 12345;
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketNioDemo.class);

    public static void main(String[] args) {
        try {
            LOGGER.info("# start server #");
            Server.start();
            LOGGER.info("# start client #");
            Client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
