package com.pay.cashier.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

public class HttpConnectionUtil {
    private HttpURLConnection conn;
    private String connectUrl;

    public HttpConnectionUtil(String connectUrl) {
        this.connectUrl = connectUrl;
    }

    /**
     * 初始化网络连接，并配置HTTPS相关的安全设置。
     * <p>
     * 此方法主要用于建立与指定URL的连接，并配置连接为输入/输出模式，同时设置超时时间。
     * 对于HTTPS连接，它还会设置自定义的SSL Socket Factory和HostnameVerifier以增强安全性。
     *
     * @param connectUrl 需要建立连接的URL字符串
     * @throws Exception 如果连接不是HTTP或HTTPS协议，则抛出异常
     */
    public void init() throws Exception {
        // 创建URL对象，用于后续的连接建立
        URL url = new URL(connectUrl);
        // 设置Java的协议处理器包，用于处理HTTPS连接
        System.setProperty("java.protocol.handler.pkgs", "javax.net.ssl");

        // 自定义HostnameVerifier，用于验证SSL证书中的主机名
        HostnameVerifier hv = new HostnameVerifier() {
            @Override
            public boolean verify(String urlHostName, SSLSession session) {
                // 验证URL主机名与SSL会话中的远程主机名是否匹配
                return urlHostName.equals(session.getPeerHost());
            }
        };
        // 设置默认的HostnameVerifier为自定义的实现
        HttpsURLConnection.setDefaultHostnameVerifier(hv);

        // 打开与URL的连接
        URLConnection conn = url.openConnection();
        // 设置连接为输入/输出模式
        conn.setDoInput(true);
        conn.setDoOutput(true);
        // 设置读取和连接超时时间
        conn.setReadTimeout(60000);
        conn.setConnectTimeout(30000);

        // 如果连接是HTTPS类型的，则配置SSL Socket Factory
        if (conn instanceof HttpsURLConnection) {
            HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
            // 设置自定义的SSL Socket Factory
            httpsConn.setSSLSocketFactory(SSLUtil.getInstance().getSSLSocketFactory());
        } else if (conn instanceof HttpURLConnection) {
            // 如果是HTTP连接，则无需特殊配置
            HttpURLConnection httpConn = (HttpURLConnection) conn;
        } else {
            // 如果连接既不是HTTP也不是HTTPS协议，则抛出异常
            throw new Exception("不是http/https协议的url");
        }
        // 将连接赋值给成员变量conn，后续操作将使用这个连接
        this.conn = (HttpURLConnection) conn;
        // 初始化默认的POST请求配置
        initDefaultPost();
    }

    public void destory() {
        try {
            if (this.conn != null) {
                this.conn.disconnect();
            }
        } catch (Exception e) {

        }
    }

    private void initDefaultPost() throws Exception {
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    }

    public byte[] postParams(Map<String, String> params, boolean readreturn) throws IOException {
        StringBuilder outBuf = new StringBuilder();
        boolean isNotFirst = false;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (isNotFirst)
                outBuf.append('&');
            isNotFirst = true;
            outBuf
                    .append(entry.getKey())
                    .append('=')
                    .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        System.out.println("参数:" + outBuf.toString());
        return postParams(outBuf.toString(), readreturn);
    }

    public byte[] postParams(String message, boolean readreturn) throws IOException {
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(message.getBytes("UTF-8"));
        out.close();
        if (readreturn) {
            return readBytesFromStream(conn.getInputStream());
        } else {
            return null;
        }
    }

    public byte[] postParams(byte[] message, boolean readreturn) throws IOException {
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(message);
        out.close();
        if (readreturn) {
            return readBytesFromStream(conn.getInputStream());
        } else {
            return null;
        }
    }

    private byte[] readBytesFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int readLen;
        byte[] tmpBuf = new byte[4096];
        while ((readLen = is.read(tmpBuf)) > 0) {
            baos.write(tmpBuf, 0, readLen);
        }
        is.close();
        return baos.toByteArray();
    }

    public HttpURLConnection getConn() {
        return conn;
    }

}
