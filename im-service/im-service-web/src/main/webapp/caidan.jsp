<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/23
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@page import="java.io.*"%>
<%@page import="java.net.*" %>
<%@page import="org.json.*" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
  final String appId = "wx04847323bd5a6669";
  final String appSecret = "bfe3e2639b29a84e40bcf7489b728536"; //自己的APPIP 和APPSECRET

%>
<%
  class TestGetPost{

    public String getAccess_token(){ // 获得ACCESS_TOKEN

      String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appId + "&secret=" +appSecret;

      String accessToken = null;
      try {
        URL urlGet = new URL(url);
        HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();

        http.setRequestMethod("GET"); //必须是get方式请求
        http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        http.setDoOutput(true);
        http.setDoInput(true);
        System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
        System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒

        http.connect();

        InputStream is =http.getInputStream();
        int size =is.available();
        byte[] jsonBytes =new byte[size];
        is.read(jsonBytes);
        String message=new String(jsonBytes,"UTF-8");

        JSONObject demoJson = new JSONObject(message);
        accessToken = demoJson.getString("access_token");

        System.out.println(message);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return accessToken;
    }
    public int createMenu() throws IOException {
      String user_define_menu = "{\"button\":[{\"type\":\"view\",\"name\":\"A：E+精选\",\"url\":\"http://www.baidu.com\"}, {\"type\":\"view\",\"name\":\"B：E+精服\",\"url\":\"http://www.baidu.com\"},{\"name\":\"C：MY.E+\",\"sub_button\":[ {\"type\":\"view\",\"name\":\"我的E课\",\"url\":\"http://www.soso.com/\"}, {\"type\":\"view\",\"name\":\"我的E数据\",\"url\":\"http://www.elastictime.cn/common_fourPoint.html\"}, {\"type\":\"view\",\"name\":\"我的E咨询\",\"url\":\"http://www.elastictime.cn/report.html\"}}]}";
      String access_token= getAccess_token();

      String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
      try {
        URL url = new URL(action);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        http.setRequestMethod("POST");
        http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        http.setDoOutput(true);
        http.setDoInput(true);
        System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
        System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒

        http.connect();
        OutputStream os= http.getOutputStream();
        os.write(user_define_menu.getBytes("UTF-8"));//传入参数
        os.flush();
        os.close();

        InputStream is =http.getInputStream();
        int size =is.available();
        byte[] jsonBytes =new byte[size];
        is.read(jsonBytes);
        String message=new String(jsonBytes,"UTF-8");
        System.out.println(message);
      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return 0;
    }
  }%>
<%
  TestGetPost tgp = new TestGetPost();

  tgp.createMenu();
%>