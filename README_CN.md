其他语言: [Tiếng việt](README_VI.md) | [English](README.md) 
##Ingame 网游接入流程介绍

1.申请接入资格 – 获取sdk参数： ```appid_key```  ```facebook_app_id```<br/>
2.接入阶段 – 下载sdk， 安排解决 约定时间解决问题<br/>
3.上线商业化

<b>SDK接入文档</b>

介绍

Ingame SDK 集成登陆和支付， 登陆包括快速登陆和facebook登录 ,支付包含：电话卡,银行卡， google in app , app in app

**步骤，将SDK:**

​1. 下载 SDK

​2. 整合和配置 SDK

​3. 启动并从应用程序中调用SDK函数

​4. 运行

<hr/>

### I. 下载 SDK

https://github.com/ingamevietnam/android-ingamesdklib/archive/master.zip
https://github.com/ingamevietnam/android-ingamesdklib.git

###II.整合和配置SDK
####A. 整合: 
目录结构:

![add](http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture1_zpsczrmwmy4.png)
  
  该SDK包括2个部分

- <b>需求库</b>：是文件的扩展的 <b>*.jar</b>内库/文件夹：

在应用程序中复制这些<b>*.jar</b>文件库/文件夹（如果libs目录不存在，你可以创建它。）

- <b>资源</b>：库需要的<b>ingamesdk.jar </b>资源

您只需复制并粘贴文件夹 <b>res/</b>在你的应用程序。

####B.配置:

<b>[提醒]您在进行配置之前需要以下信:</b> <br/>


>Replace <your_package_name> with your application packagename. （商务约定）
>Com.ingvn.publish.xxxx <br/>
>Replace <your_facebook_application_id> with your facebook app id. （商务提供）<br/>
>Replace <application_license_key> with your license key.   （商务提供,谷歌支付）<br/>
>Replace <ingame_application_id> with your app id supplied by ingame. （商务提供）<br/>
>Replace <ingame_application_key> with your app key supplied by ingame. （商务提供）<br/>

<b>配置strings.xml</b>
　　
``` 
　　　　<string name="facebook_appId"><your_facebook_application_id></string>
　　　　<string name="google_license_key"><application_license_key></string>
　　　　<string name="App_Id"><ingame_application_id></string>
　　　　<string name="App_Key"><ingame_application_key></string>
``` 
<b>配置AndroidMainfest.xml</b>
-   添加< permission >标签授予访问系统:

```
    <uses-permission android:name="android.permission.SEND_SMS" />
　　<uses-permission android:name="android.permission.INTERNET" />
　　<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
　　<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
　　<uses-permission android:name="android.permission.READ_PHONE_STATE" />
　　<uses-permission android:name="android.permission.WAKE_LOCK" />
　　<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
　　<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
　　
 	<!-- Google Cloud Message Permission -->
　　<permission
　　　　android:name="<your_package_name>.permission.C2D_MESSAGE"
　　　　android:protectionLevel="signature" />
    	<uses-permission android:name="<your_package_name>.C2D_MESSAGE" />
    	<uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />

　　<!-- Google IAP Permission -->
    	<uses-permission android:name="android.permission.GET_ACCOUNTS" />
　　<uses-permission android:name="com.android.vending.BILLING" />
```
- 添加< meta-data >在<Application>标签标记为系统初始值

```
　　<application
　　　　...........................
　　　　<meta-data
　　　　　　android:name="com.facebook.sdk.ApplicationId"
　　　　　　android:value="@string/facebook_appId" />
　　　　<meta-data
　　　　　　android:name="com.google.android.gms.version"
　　　　　　android:value="@integer/google_play_services_version" />
　　　　<meta-data
　　　　　　android:name="com.IngameSDK.GoogleIAPKey"
　　　　　　android:value="@string/google_license_key" />
　　　　<meta-data android:name="com.IngameSDK.AppId" 
　　　　    android:value="@string/App_Id" />
        <meta-data android:name="com.IngameSDK.AppKey" 
            android:value="@string/App_Key" />
       
         <!--  for app flyer -->
        <receiver android:name="com.appsflyer.MultipleInstallBroadcastReceiver" android:exported="true">
	<intent-filter>
	<action android:name="com.android.vending.INSTALL_REFERRER" />
	</intent-filter>
	</receiver>
	<receiver android:name="com.appsflyer.AppsFlyerLib" android:exported="true">
	<intent-filter>
	<action android:name="com.android.vending.INSTALL_REFERRER" />
	</intent-filter>
	</receiver>
        <meta-data android:name="com.appflyer.dev_key" 
             android:value="ekymUhihizGufaXWaeH5nn" />
        <!--  end for app flyer -->
　　　　...........................
　　</application>
```
- 添加< activity >标签来配置界面

```
　　<application>
　　　　...........................
 	<activity
            android:name="com.ingamesdk.ui.LoginActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:theme="@style/UserDialog"
            android:windowSoftInputMode="adjustPan" />
        <activity
            android:name="com.ingamesdk.ui.PaymentActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:theme="@style/UserDialog"
            android:windowSoftInputMode="adjustPan" />
        <activity
            android:name="com.ingamesdk.ui.UserInfoActivity"
            android:configChanges="orientation|keyboardHidden|screenSize|locale"
            android:theme="@style/UserDialog"
            android:windowSoftInputMode="adjustPan" />
        <activity android:name="com.facebook.LoginActivity" />
　　　　...........................
　　</application>
```
###III. 声明变量，启动并从应用程序中调用SDK函数
<b>创建接口从SDK接收事件:</b>

```
　	public class Listener implements IGListenerInterface{

		@Override
		public void LoginSuccessListener(JSONObject json) {
                        //Through the session variable you can get account information by:
                        //json.getString("userID");
                        //json.getString("userName");
                        //json.getString("accessToken");
                        //json.getString("phone");
                        //json.getString("email");
		}
		
		@Override
		public void LogOutSuccessListener() {
			// TODO Auto-generated method stub
		}

		@Override
		public void GetFriendListSuccessListener(JSONObject json) {
		    // TODO Auto-generated method stub
		}

		@Override
		public void InviteFriendSuccessListener() {
			// TODO Auto-generated method stub
		}
	}
```

<b>声明下列变量在游戏的 main Activity:</b>

	    public static InGameSDK ingame_sdk = InGameSDK.getInstance(); // instance of InGameSDK

<b>设置值在InGameSDK inside onCreate(...))</b>
````
	ingame_sdk.callSendInstallationEvent(this); //必须在 init sdk.之前调用此方法

    Listener listener = new Listener();// init your listener

    ingame_sdk.init(this, true, true, callback_url);

    ingame_sdk.setListener(listener);//set your listener to sdk
```
<b>ingame_sdk.init(...) 的参数</b>

```
　　public void init(Activity context, boolean isButton, boolean isAutoLogin, String callback_url) {...}
```

>```context```:  游戏的 main Activity <br/>
>```isButton```: 是否显示浮标(True/False)<br/>
>```isAutoLogin```: 是否自动登陆 <br/>
>```callback_url```: 应用服务器接受回调的地址<br/>

<b>添加following</b>
```
　``
　　@Override
	protected void onResume() {
		super.onResume();
		InGameSDK.getInstance().onResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	     InGameSDK.getInstance().onPause();
	}
	
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
		InGameSDK.getInstance().onActivityResult(requestCode, resultCode, data);
	}
```

<b>调用SDK提供的功能:</b>
```
　  注册: 							  ingame_sdk.callRegister();
　　登陆:				       		ingame_sdk.callLogin();
　　退出: 							  ingame_sdk.callLogout();
　　用户信息: 		                   ingame_sdk.callshowUserInfo();
　　支付: 						 	      ingame_sdk.callPayment(String game_order); // game_order:设置游戏服务器的订单号(less than 50 characters).参与校验..
　　邀请(Show SDK UI)         ingame_sdk.callInviteFriend();// after sent request successfully you can receive a message from InviteFriendSuccessListener
　  获取facebook好友列表：    ingame_sdk.callGetFBFriendList(); //game will receive friend list in  GetFriendListSuccessListener(Json json)
    分享应用到facebook:                   ingame_sdk.callShareMessageFromGame(YOUR_MESSAGE, YOUR_LIST_FRIEND_ID);
```


####IV. 运行sdk



<table border="0">
    <tr>
        <td width ="500px">
            <img src="http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture4_zps7h9ax8od.png">
        </td>
        <td width ="500px">
            <img src="http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture5_zpsnce3ybz6.png" width>
        </td>
        <td width ="500px">
            <img src="http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture6_zpsuzqramsf.png" width>
        </td>
    </tr>
    
    
        <tr>
        <td width ="500px">
            <img src="http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture7_zpspqowr2jh.png">
        </td>
        <td width ="500px">
            <img src="http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture8_zpsnkkspvqy.png" width>
        </td>
        <td width ="500px">
            <img src="http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture9_zpslkkqbbpc.png" width>
        </td>
    </tr>
   
    
        <tr>
        <td width ="500px">
            <img src="http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture10_zpsvz7fuw4b.png">
        </td>
        <td width ="500px">
            <img src="http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture11_zpsfmaxax7g.png" width>
        </td>
        <td width ="500px">
            <img src="http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture12_zpstgtkbzte.png" width>
        </td>
    </tr>
   
</table>
