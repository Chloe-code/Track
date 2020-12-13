package com.example.myapplication;
import android.icu.text.IDNA;
import android.util.Log;

import com.example.myapplication.Model.Users;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;


public class ws_test2 {
    private static final String NAMESPACE = "http://tempuri.org/";       //WebService預設的命名空間
    private static final String URL = "http://134.208.96.168/webservice/WebService.asmx";     //WebService的網址
    private static final String URL1 = "http://localhost:13715/WebService.asmx";

    private Users users;
    //private static final String SOAP_ACTION = " http://tempuri.org/select_test1";          //命名空間+要用的函數名稱
    //private static final String METHOD_NAME = "select_test1";   //函數名稱
    //private static final String SOAP_ACTION = " http://tempuri.org/insert_test1";
    //private static final String METHOD_NAME = "insert_test1";


    public static String ws_test2(String s) {
        String SOAP_ACTION = " http://tempuri.org/select_test1";          //命名空間+要用的函數名稱
        String METHOD_NAME = "select_test1";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("name", s);

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

    public static String inserttest(String username, String email, String gender, String birth, String word) {
        SoapObject object = null;
        String SOAP_ACTION = " http://tempuri.org/insert_test3";
        String METHOD_NAME = "insert_test3";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("UserName", username);
            request.addProperty("Email", email);
            request.addProperty("Gender", gender);
            request.addProperty("Birth", birth);
            request.addProperty("Word", word);

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
            Log.v("test", "object:" + object);
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            Log.v("test", "result:" + result);
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }

    public static String[] friend_list(String name) {
        String SOAP_ACTION = " http://tempuri.org/friendlist";          //命名空間+要用的函數名稱
        String METHOD_NAME = "friendlist";
        String[] result2 = null;
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("name", name);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);
            Log.v("test","gooooood");

            SoapObject object = (SoapObject) envelope.getResponse();
            // 獲取返回的結果
            int intPropertyCount = object.getPropertyCount();
            result2 = new String[intPropertyCount];
            for (int i = 0; i <intPropertyCount; i++) {
                result2[i]=(object.getProperty(i).toString());
                Log.v("test2","goooooodmorning");
            }
            return result2;
        } catch (Exception e) {
            return result2;
        }
    }
    public static String[] message(String sender) {
        String SOAP_ACTION = " http://tempuri.org/message";          //命名空間+要用的函數名稱
        String METHOD_NAME = "message";
        String[] result2 = null;
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("sender", "Apple");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);
            Log.v("test", "gooooood");

            SoapObject object = (SoapObject) envelope.getResponse();
            // 獲取返回的結果
            int intPropertyCount = object.getPropertyCount();
            result2 = new String[intPropertyCount];
            for (int i = 0; i < intPropertyCount; i++) {
                result2[i] = (object.getProperty(i).toString());
                Log.v("test2", "goooooodmorning");
            }
            return result2;
        } catch (Exception e) {
            return result2;
        }
    }
    public static String messageinsert(String msg) {
        String SOAP_ACTION = " http://tempuri.org/messageinsert";          //命名空間+要用的函數名稱
        String METHOD_NAME = "messageinsert";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("sender", "Apple");
            request.addProperty("receiver", "Hedy");
            request.addProperty("msg", msg);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            /// 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }
     public static String test(String s) {
        String SOAP_ACTION = " http://tempuri.org/test2";          //命名空間+要用的函數名稱
        String METHOD_NAME = "test2";
        //必須用try catch包著
        try {
             SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
             request.addProperty("name", s);

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
    public static String personinfoselect(String email)
    {
        String SOAP_ACTION = " http://tempuri.org/personinfoselect";          //命名空間+要用的函數名稱
        String METHOD_NAME = "personinfoselect";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id",email);

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
    public static String select_devicelocation(String deviceid)
    {
        String SOAP_ACTION = " http://tempuri.org/select_devicelocation";          //命名空間+要用的函數名稱
        String METHOD_NAME = "select_devicelocation";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id",deviceid);

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
    public static String personinfoupdate(String username, String email, String phone, String birth, String gender, String word, String imgurl)
    {
        String SOAP_ACTION = " http://tempuri.org/personinfoupdate";
        String METHOD_NAME = "personinfoupdate";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("UserName",username);
            request.addProperty("Email",email);
            request.addProperty("Phone",phone);
            request.addProperty("Gender",gender);
            request.addProperty("Birth",birth);
            request.addProperty("Word",word);
            request.addProperty("imgurl",imgurl);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            /// 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }
    public static String[] homerecyclrview(String name)
    {
        String SOAP_ACTION = " http://tempuri.org/homerecyclrview";          //命名空間+要用的函數名稱
        String METHOD_NAME = "homerecyclrview";
        String[] result2 = null;
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("Username",name);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            // 獲取回傳數據
            SoapObject object = (SoapObject) envelope.getResponse();
            Log.v("test1",object.toString());
            // 獲取返回的結果
            int intPropertyCount = object.getPropertyCount();
            result2 = new String[intPropertyCount];
            for (int i = 0; i <intPropertyCount; i++) {
                result2[i]=(object.getProperty(i).toString());
                Log.v("test1","1118"+result2[i]);
            }
            return result2;
        }
        catch (Exception e) {
            return result2;
        }
    }
    public static String homerecyclrview2(String name)
    {
        String SOAP_ACTION = " http://tempuri.org/homerecyclrview2";          //命名空間+要用的函數名稱
        String METHOD_NAME = "homerecyclrview2";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("Username",name);

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
    public static String deviceinfoselect(String deviceid)
    {
        String SOAP_ACTION = " http://tempuri.org/deviceinfoselect";          //命名空間+要用的函數名稱
        String METHOD_NAME = "deviceinfoselect";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("deviceid",deviceid);

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
    public static String signupornot(String email)
    {
        String SOAP_ACTION = " http://tempuri.org/signupornot";          //命名空間+要用的函數名稱
        String METHOD_NAME = "signupornot";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("Email",email);

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
    public static String personinfoinsert(String username, String email, String phone, String birth, String gender, String word, String imgurl)
    {
        String SOAP_ACTION = " http://tempuri.org/personinfoinsert";
        String METHOD_NAME = "personinfoinsert";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("UserName",username);
            request.addProperty("Email",email);
            request.addProperty("Phone",phone);
            request.addProperty("Gender",gender);
            request.addProperty("Birth",birth);
            request.addProperty("Word",word);
            request.addProperty("imgurl",imgurl);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            /// 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }
    public static String deviceinfoinsert(String devicename, String email, String deviceid, String birth, String gender, String distance, String privategps, String word, String imgurl)
    {
        String SOAP_ACTION = " http://tempuri.org/deviceinfoinsert";
        String METHOD_NAME = "deviceinfoinsert";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("UserName",devicename);
            request.addProperty("Email",email);
            request.addProperty("UUID",deviceid);
            request.addProperty("Gender",gender);
            request.addProperty("Birth",birth);
            request.addProperty("Distance",distance);
            request.addProperty("privategps",privategps);
            request.addProperty("Word",word);
            request.addProperty("imgurl",imgurl);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            /// 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            Log.v("test1","結果"+result);
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }
    public static String[] addfriendlist(String username)
    {
        String SOAP_ACTION = " http://tempuri.org/selectfriend";
        String METHOD_NAME = "selectfriend";
        String[] result2 = null;
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("username",username);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);
            /// 獲取回傳數據
            SoapObject object = (SoapObject) envelope.getResponse();
            // 獲取返回的結果
            int intPropertyCount = object.getPropertyCount();
            result2 = new String[intPropertyCount];
            for (int i = 0; i <intPropertyCount; i++) {
                result2[i]=(object.getProperty(i).toString());
            }
            return result2;
        } catch (Exception e) {
            return result2;
        }
    }
    public static String addfriend(String user1email, String user2email, Integer status, String actionuser)
    {
        String SOAP_ACTION = " http://tempuri.org/addfriend";
        String METHOD_NAME = "addfriend";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("user1",user1email);
            request.addProperty("user2",user2email);
            request.addProperty("status",status);
            request.addProperty("actionuser",actionuser);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            /// 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }
    public static String[] noticerequest(String useremail)
    {
        String SOAP_ACTION = " http://tempuri.org/noticefriendrequest";
        String METHOD_NAME = "noticefriendrequest";
        String[] result2 = null;
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("useremail",useremail);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);
            /// 獲取回傳數據
            SoapObject object = (SoapObject) envelope.getResponse();
            // 獲取返回的結果
            int intPropertyCount = object.getPropertyCount();
            result2 = new String[intPropertyCount];
            for (int i = 0; i <intPropertyCount; i++) {
                result2[i]=(object.getProperty(i).toString());
            }
            return result2;
        } catch (Exception e) {
            return result2;
        }
    }
    public static String acceptrequest(String user1email, String user2email, Integer status, String actionuser)
    {
        String SOAP_ACTION = " http://tempuri.org/acceptrequest";
        String METHOD_NAME = "acceptrequest";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("user1",user1email);
            request.addProperty("user2",user2email);
            request.addProperty("status",status);
            request.addProperty("actionuser",actionuser);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            /// 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }
    public static String beaconcondition(String deviceuuid, Integer open)
    {
        String SOAP_ACTION = " http://tempuri.org/beaconopen";
        String METHOD_NAME = "beaconopen";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("deviceuuid",deviceuuid);
            request.addProperty("open",open);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            /// 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }
    public static Integer beaconcheck(String deviceuuid)
    {
        String SOAP_ACTION = " http://tempuri.org/beaconcheck";
        String METHOD_NAME = "beaconcheck";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("deviceuuid",deviceuuid);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            /// 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            return Integer.valueOf(result);
        } catch (Exception e) {
            return Integer.valueOf(e.toString());
        }
    }
    public static String deviceinfoupdate(String devicename, String uuid, String phone, String birth, String gender, String word, String imgurl)
    {
        String SOAP_ACTION = " http://tempuri.org/personinfoupdate";
        String METHOD_NAME = "personinfoupdate";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            //request.addProperty("UserName",username);
            //request.addProperty("Email",email);
            request.addProperty("Phone",phone);
            request.addProperty("Gender",gender);
            request.addProperty("Birth",birth);
            request.addProperty("Word",word);
            request.addProperty("imgurl",imgurl);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            /// 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }
    public static Integer beaconrssi(String usersetbeacondistance)
    {
        String SOAP_ACTION = " http://tempuri.org/beaconrssi";
        String METHOD_NAME = "beaconrssi";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("usersetbeacondistance",usersetbeacondistance);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            /// 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            return Integer.valueOf(result);
        } catch (Exception e) {
            return Integer.valueOf(e.toString());
        }
    }
    public static String deletefriend(String usergmail, String friendgmail)
    {
        String SOAP_ACTION = " http://tempuri.org/deletefriendship";
        String METHOD_NAME = "deletefriendship";
        //必須用try catch包著
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("usergmail",usergmail);
            request.addProperty("friendgmail",friendgmail);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = request;
            envelope.dotNet = true;//若WS有輸入參數必須要加這一行否則WS沒反應
            envelope.setOutputSoapObject(request);
            envelope.encodingStyle = "utf-8";
            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);

            /// 獲取回傳數據
            SoapObject object = (SoapObject) envelope.bodyIn;
            // 獲取返回的結果
            String result = object.getProperty(0).toString();
            return result;
        } catch (Exception e) {
            return e.toString();
        }
    }
}

