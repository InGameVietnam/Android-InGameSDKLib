Other languages: [Vietnamese](README_VI.md) | [Chinese](README_CN.md)
###DOCUMENT FOR INTEGRATED SYSTEM INGAMESDK

**Introduction**

InGame SDK is user and payment integrated system for your mobile application. This SDK provides a simple way to integrate and satisfies user account management, payment requirements on mobile. It provides solutions for payment methods such as: Phone Card, Internet Bankingand Google Play Payment.

**Steps to integrate SDK:**

​1. Download SDK

​2. Integrate & configure SDK

​3. Declare variables, initiate and call SDK function from your application

​4. Run SDK samples

<hr/>

### I. Download SDK

Download InGame SDK for Android here: https://github.com/InGameVietnam/Android-InGameSDKLib/archive/version-2.0.zip

Or use any git tool, clone this url: https://github.com/InGameVietnam/Android-InGameSDKLib.git

###II. Integrate & configure SDK
####A. Integrate: 

  ![add](http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture1_zpsczrmwmy4.png)

<i>The directory structure</i>

The SDK includes 2 parts 
- <b>Requirement libraries:</b>  are files have the extension <b>*.jar</b> inside <b>libs/</b> folder: 
  
    Copy these <b>*.jar</b> files to <b>libs/</b> folder in your application <i>(If the libs directory does not exist, you can create it.)</i>

- <b>Resources :</b> The resources needed by the library <b>ingamesdk.jar</b> 
    
     You simply copy and paste the folder <b>res/</b> in your application.

####B. Configure:

<b>[Notice] You need the following information before proceeding with the configuration:</b> <br/>
<!-- <b><i<!--*Get ```<application_license_key>``` value from Google</b></i><br/>
>  1> First, log into your <b>Google Play Developer Console</b>.<br/>
>  2> Next, click on <b>All Applications</b> and find the application that you'd like to review.<br/>
> 3>After that, go to the section <b>Services and APIs</b><br/>
>![add](http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture2_zpsoquddje9.jpg)<br/>
 You will find your license key under the section labeled <b>Your License Key For this Application</b>.
>-->
><b><i>*Get ```<your_facebook_application_id>``` value from Facebook</b></i><br/>
>1> First, log into your https://developers.facebook.com/<br/>
>2> Next, click on <b>My Apps</b> and find the application that you'd like to review.<br/>
>3> The ```<your_facebook_application_id>``` under the section <b>App ID</b><br/>
>![add](http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture3_zpsajdtkwmv.png)

<b>[Notice] The values be located in the < > you need to replace them as follows:</b>
> Replace ```<your_package_name>``` with your ```application packagename```.<br/>
> Replace ```<your_facebook_application_id>``` with your ```facebook app id```.<br/>
> Replace ```<application_license_key>``` with your ```license key```.<br/>
> Replace ```<ingame_application_id>``` with your ```app id supplied by ingame```.<br/>
> Replace ```<ingame_application_key>``` with your ```app key supplied by ingame```.<br/>
> Replace ```<ingame_googleplay_id>``` with your ```google play id supplied by ingame```.<br/>
> Replace ```<ingame_google_adwords_id>``` with your ```google adwords id supplied by ingame```.<br/>
> Replace ```<ingame_google_adwords_label>``` with your ```google adwords label supplied by ingame```.<br/>



<b>Configure strings.xml</b>

　　Open <b>strings.xml</b> file in your application
　　
``` xml
　　　　<string name="facebook_appId"><your_facebook_application_id></string>
　　　　<string name="google_license_key"><application_license_key></string>
　　　　<string name="App_Id"><ingame_application_id></string>
　　　　<string name="App_Key"><ingame_application_key></string>
　　　　<string name="google_ads_consId"><ingame_google_adwords_id></string>
　　　　<string name="google_ads_consLabel"><ingame_google_adwords_label></string>
``` 
<b>Configure AndroidMainfest.xml</b>

　　Open <b>AndroidMainfest.xml</b> file in your application
-   Add ```<permission>``` tags to grant access to the system:

```
    <uses-permission android:name="android.permission.SEND_SMS" />
　　<uses-permission android:name="android.permission.INTERNET" />
　　<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
　　<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
　　<uses-permission android:name="android.permission.READ_PHONE_STATE" />
　　<uses-permission android:name="android.permission.WAKE_LOCK" />
　　<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
　　<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

　　<!-- Google IAP Permission -->
    	<uses-permission android:name="android.permission.GET_ACCOUNTS" />
　　<uses-permission android:name="com.android.vending.BILLING" />
```
- Add ```<meta-data>``` tags inside ```<application>``` tag to initial value for system

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
	    
	    
        <!-- for Google adWords -->
        <receiver
            android:name="com.google.ads.conversiontracking.InstallReceiver"
            android:exported="true" >
            <intent-filter>
                <action android:name="com.android.vending.INSTALL_REFERRER" />
            </intent-filter>
        </receiver>

        <!-- for google adWords tracking download app -->

        <meta-data
            android:name="com.google.ads.conID"
            android:value="@string/google_ads_consId" />
        <meta-data
            android:name="com.google.ads.conLabel"
            android:value="@string/google_ads_consLabel" />
            
	    
	 <!--  for app flyer -->
        <receiver android:exported="true" android:name="com.appsflyer.MultipleInstallBroadcastReceiver">
            <intent-filter>
                <action android:name="com.android.vending.INSTALL_REFERRER"/>
            </intent-filter>
        </receiver>
        <receiver android:name="com.appsflyer.AppsFlyerLib">
            <intent-filter>
                <action android:name="android.intent.action.PACKAGE_REMOVED"/>
                <data android:scheme="package"/>
            </intent-filter>
        </receiver>
        <meta-data android:name="com.appflyer.dev_key" 
             android:value="ekymUhihizGufaXWaeH5nn" />
        <!--  end for app flyer -->
　　　　...........................
　　</application>
```
- Add ```<activity>``` tags to configure interface:

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
        <activity android:name="com.ingamesdk.ui.AdditionalServicesActivity"
            android:theme="@style/UserDialog"
            android:configChanges="orientation|keyboardHidden|screenSize|locale"
            android:windowSoftInputMode="adjustPan" />
        <activity android:name="com.facebook.LoginActivity" />
　　　　...........................
　　</application>
```
###III. Declare variables, initiate and call SDK function from your application
<b>Create ```Interface``` to receive events from the SDK</b>

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
		public void InviteFriendSuccessListener(List<String> userIdlist) {
			// TODO Auto-generated method stub
		}
	}
```
　　
<b>Declare the following variables inside the main Activity class of applications:</b>


	public static InGameSDK ingame_sdk = InGameSDK.getInstance(); // instance of InGameSDK
    
<b>Set the values to the function InGameSDK inside onCreate(...)</b>
    <br>ingame_sdk.callSendInstallationEvent(this); ```//this function must be called before init sdk.```<br/>
    <br>Listener listener = new Listener();```// init your listener```<br/>
    <br>ingame_sdk.init(this, true, true, callback_url);<br/>
    <br>ingame_sdk.setListener(listener);```//set your listener to sdk```<br/>

<b>The parameters of ingame_sdk.init(...)</b>

```
　　public void init(Activity context, boolean isButton, boolean isAutoLogin, String callback_url) {...}
```

>```context```:  The main Activity of your application<br/>
>```isButton```: use SDK shorcut Button (True/False)<br/>
>```isAutoLogin```: use Auto login function (True/False)<br/>
>```callback_url```: The call back link of your server<br/>

<b>Add the following to the corresponding handle each functions</b>
```
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

<b>Call the function corresponding that the SDK provides functionality for each operation:</b>
```
　　Registration: 		        ingame_sdk.callRegister();
　　Log in:				ingame_sdk.callLogin();
　　Log out: 				ingame_sdk.callLogout();
　　User information: 			ingame_sdk.callshowUserInfo();
　　Payment: 				ingame_sdk.callPayment(String game_order); // game_order: Transaction code is created by Developer (less than 50 characters).
　　Invite Friend (Show sdk UI)         ingame_sdk.callInviteFriend();// after sent request successfully you can receive a message from InviteFriendSuccessListener
　　Get List FB friend:                 ingame_sdk.callGetFBFriendList(); //game will receive friend list in  GetFriendListSuccessListener(Json json)
　　Share FB message:                   ingame_sdk.callShareMessageFromGame(YOUR_MESSAGE, YOUR_LIST_FRIEND_ID);
```


####IV. Run SDK samples

You can see the more detail in the attached sample code. 
![add](http://i757.photobucket.com/albums/xx212/ichirokudo/Capture_zps7vmykcsm.png)
