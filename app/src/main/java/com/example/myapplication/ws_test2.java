package com.example.myapplication;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class ws_test2
{
    private static final String NAMESPACE = "http://tempuri.org/" ;       //WebService預設的命名空間
    private static final String URL = "http://134.208.96.168/WebService/webservice.asmx";     //WebService的網址
    //private static final String SOAP_ACTION = " http://tempuri.org/select_test1";          //命名空間+要用的函數名稱
    //private static final String METHOD_NAME = "select_test1";   //函數名稱
    //private static final String SOAP_ACTION = " http://tempuri.org/insert_test1";
    //private static final String METHOD_NAME = "insert_test1";

    public static String ws_test2(String s)
    {
         String SOAP_ACTION = " http://tempuri.org/select_test1";          //命名空間+要用的函數名稱
         String METHOD_NAME = "select_test1";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("name",s);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            // 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }
    public static String inserttest(String username, String email, String gender, String birth, String word)
    {
        SoapObject  object = null;
        String SOAP_ACTION = " http://tempuri.org/insert_test3";
        String METHOD_NAME = "insert_test3";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("UserName",username);
            request.addProperty("Email",email);
            request.addProperty("Gender",gender);
            request.addProperty("Birth",birth);
            request.addProperty("Word",word);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            // 獲取回傳數據
            //SoapObject object = (SoapObject) envelope.bodyIn;
            object = (SoapObject) envelope.getResponse();
            Log.v("test","object:"+object);
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            Log.v("test","result:"+result);
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }
}
