package com.leetai.purchasingagent.tools;

import com.lidroid.xutils.http.RequestParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

;


/**
 * Created by dell on 2015/9/9.
 */
public class HttpTool {
    public static final String BasePATH = "http://172.16.69.49:80/PurchasingAgent";
    ///public static final String PATH = "http://192.168.31.101:8080/PurchasingAgent/";
    //  public static final String PATH = "http://www.ltdry.top:8080/PurchasingAgent/";
    private static final String PATH = BasePATH+"/servlet/";
    private static final String PicPATH = BasePATH+"/Pic";

    HttpURLConnection conn;
    URL url = null;

    public static String getPicUrl( ) {
        return PicPATH;
    }
    public static String getUrl(List<String> param, String servletPath) {
        //添加参数
        String url = "";
        StringBuilder sb = new StringBuilder(PATH);
        try {
            sb.append(servletPath);
            if (param != null) {
                if (param.size() != 0) {
                    sb.append("?");
                    for (int i = 0; i < param.size(); i++) {

                        sb.append("param" + (i + 1) + "=").append(URLEncoder.encode(param.get(i).toString(), "utf-8")).append("&");
                        ;

                    }
                    url = sb.toString().substring(0, sb.toString().length() - 1);
                }
            } else {
                url = sb.toString();
            }
            System.out.println("Http连接为：" + url);
            return url;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            return sb.toString();
        }


    }

    public static String getUrl(String[] param, String servletPath) {
        //添加参数
        String url = "";
        StringBuilder sb = new StringBuilder(PATH);
        try {
            sb.append(servletPath);
            if (param != null) {
                if (param.length != 0) {
                    sb.append("?");
                    for (int i = 0; i < param.length; i++) {

                        sb.append("param" + (i + 1) + "=").append(URLEncoder.encode(param[i].toString(), "utf-8")).append("&");

                    }
                    url = sb.toString().substring(0, sb.toString().length() - 1);
                }
            } else {
                url = sb.toString();
            }
            System.out.println("Http连接为：" + url);
            return url;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("错误信息为：" + e);
        } finally {
            return url;
        }


    }

    public static String getUrl(String param, String servletPath) {
        //添加参数

        StringBuilder sb = new StringBuilder(PATH);
        try {
            sb.append(servletPath);
            if (param != null && param != "") {
                sb.append("?");
                sb.append("param1=").append(URLEncoder.encode(param, "utf-8"));
            }
            System.out.println("Http连接为：" + sb.toString());
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            return sb.toString();
        }


    }

    public static RequestParams getParam(String[] param){
        RequestParams params = new RequestParams();
        if (param != null) {
            if (param.length != 0) {
                for (int i = 0; i < param.length; i++) {
                    params.addBodyParameter("param"+(i+1), param[i]);
                }
            }
        } else {
            params.addBodyParameter("param1", "");
        }
        return params;
    }



    private HttpURLConnection getConnection(String strurl) {
        try {
//建立连接
            url = new URL(strurl);
            conn = (HttpURLConnection) url.openConnection();
            return conn;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return conn;
        }
    }

    /**
     * @param param    字符串参数
     * @param servlet servlet路径
     * @return json格式字符串
     */
    public String Query(List<String> param, String servlet) {
        String result = "";
        String gonString = "";

        try {

            conn = getConnection(getUrl(param, servlet));
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            //conn.setDoOutput(true);
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                //Toast.makeText(LoginActivity.this, "connect fail!!", Toast.LENGTH_LONG).show();
                System.out.println("HttpUtils网络连接失败");
            } else {
                InputStreamReader in = new InputStreamReader(conn.getInputStream(), "utf-8");
                BufferedReader reader = new BufferedReader(in);
                try {
                    while ((gonString = reader.readLine()) != null) {
                        result += gonString;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }



}
