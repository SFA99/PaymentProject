package com.pay.cashier;

import java.io.BufferedReader;
    import java.io.InputStreamReader;
    import java.net.HttpURLConnection;
    import java.net.URL;

    public class HttpRequest示例 {
        private static final String API_URL = "https://zion-app.functorz.com/rmkky19AQmK/zero/EQvM7ze16PP/callback/74263d09-cfd7-4f0a-ab17-ee432ea6199f";

        public static void main(String[] args) {
            try {
                URL url = new URL(API_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET"); // 或"POST"，取决于端点要求

                int responseCode = conn.getResponseCode();
                System.out.println("响应码 : " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 打印结果或按需处理响应数据
                System.out.println(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
