package cn.ymex.cute.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by ymexc on 2016/8/4.
 */
public class SocketClient {
    private int CONTENT_TIME_OUT = 60 * 1000;
    private String mHost = null;
    private int port = 9999;
    public static SocketClient mClient;
    private SocketChannel socketChannel;
    private Selector selector;

    private SocketClient() {
    }

    public static SocketClient instance() {
        SocketClient sc = mClient;
        if (mClient == null) {
            synchronized (SocketClient.class) {
                sc = mClient;
                if (sc == null) {
                    sc = new SocketClient();
                    mClient = sc;
                }
            }
        }
        return mClient;
    }


    private SocketClient(String host, int port) {
        boolean done = false;

        try {
            InetSocketAddress address = new InetSocketAddress(host, port);
            socketChannel = SocketChannel.open(address);
            if (socketChannel != null) {
                socketChannel.socket().setTcpNoDelay(false);
                socketChannel.socket().setKeepAlive(true);
                socketChannel.socket().setSoTimeout(CONTENT_TIME_OUT);
                socketChannel.configureBlocking(false);
                selector = Selector.open();
                if (selector != null) {
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    done = true;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (!done && selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!done && socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Builder {
        private String host = "";
        private int port = 9999;


        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setPort(int port){
            this.port = port;
            return this;
        }


        public Builder setAddress(String host, int port) {
            this.host = host;
            this.port = port;
            return this;
        }

        public SocketClient build(){
            return new SocketClient(host,port);
        }
    }
}
