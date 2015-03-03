Ngôn ngữ khác: [English](README.md)
##TÀI LIỆU HƯỚNG DẪN TÍCH HỢP HỆ THỐNG INGAMESDK 1.0

**Giới thiệu**

InGameSDK là hệ thống tích hợp tài khoản và thanh toán cho ứng dụng của bạn. Hệ thống hỗ trợ các bước tích hợp đơn giản vào ứng dụng, đáp ứng các nhu cầu trong việc quản lý thông tin tài khoản và thanh toán trên di động. SDK này cung cấp giải pháp cho các hình thức thanh toán: Thẻ cào điện thoại, Internet banking và Google Payment.

**Các bước tích hợp SDK:**

​1. Tải hệ thống SDK

​2. Các bước tích hợp và cấu hình

​3. Cách thức khai báo, khởi tạo và gọi các chức năng của SDK từ ứng dụng của bạn

​4. Chạy ứng dụng minh họa

<hr/>

### I. Tải hệ thống SDK

** Tải InGame SDK tại địa chỉ: https://github.com/ingamevietnam/android-ingamesdklib/archive/master.zip

Hoặc sử dụng công cụ git tool để clone từ đường dẫn này: https://github.com/ingamevietnam/android-ingamesdklib.git

###II. Các bước tích hợp và cấu hình
####A. Tích hợp: 
Bộ SDK khi tải về bao gồm 2 phần 
- <b>Các thư viện </b>(requirement libraries): là các tập tin có đuôi <b>*.jar</b> bên trong thư mục <b>libs/</b> 
  
Sao chép các tập tin <b>*.jar</b> này vào thư mục <b>libs/</b>  trong project của bạn <i>(nếu chưa có thư mục <b>libs/</b>  bạn có thể tạo mới sau đó chép những file này vào)</i>

- <b>Resource </b>(res folder): là những tài nguyên cần thiết của thư viện <b>ingamesdk1.0.jar </b>

![add](http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture1_zpsczrmwmy4.png)
   
  <i>Cấu trúc thư mục</i>
    
Tương tự như trên bạn chỉ cần sao chép và dán vào thư mục <b>res/</b> trong ứng dụng của bạn.

####B. Cấu hình:

<b>[Quan trọng] Bạn cần có thông tin sau đây trước khi tiến hành cấu hình:</b> <br/>
> <b><i>*Lấy giá trị``` <application_license_key>``` từ Google</b></i><br/>
>  1> Đăng nhập vào <b>Google Play Developer Console</b> của bạn.<br/>
>  2>  Nhấn chọn <b>All Applications</b> và tìm ứng dụng bạn cần lấy thông tin.<br/>
> 3>Sau đó, đến mục <b>Services and APIs</b><br/>
>![add](http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture2_zpsoquddje9.jpg)<br/>
 Bạn sẽ tìm thấy giấy phép của ứng dụng (license key) dưới mục <b>Your License Key For this Application</b>.
>
<b><i>*Lấy giá trị ```<your_facebook_application_id>```  từ Facebook</b></i><br/>
>1> Đăng nhập vào tài khoản Facebook của bạn tại địa chỉ https://developers.facebook.com/<br/>
>2> Nhấn chọn mục <b>My Apps</b> và chọn ứng dụng bạn cần lấy thông tin.<br/>
>3> Thông tin ```<your_facebook_application_id``` nằm trong mục  <b>App ID</b><br/>
>![add](http://i757.photobucket.com/albums/xx212/ichirokudo/Ingame/Picture3_zpsajdtkwmv.png)

<b>[Lưu ý] Bên dưới sẽ có những vị trí nằm trong < > bạn cần thay thế chúng như sau:</b>
> Thay ```<your_package_name>``` bằng ```packagename của ứng dụng```.<br/>
> Thay ```<your_facebook_application_id>``` bằng ```facebook app id``` của bạn.<br/>
> Thay ```<application_license_key>```bằng ```giấy phép ứng dụng (license key)``` của bạn.<br/>



<b>Cấu hình strings.xml</b>

　　Mở tập tin <b>strings.xml</b> trong project Android ứng dụng
　　
``` 
　　　　<string name="facebook_appId"><your_facebook_application_id></string>
　　　　<string name="google_license_key"><application_license_key></string>
``` 
<b>Cấu hình AndroidMainfest.xml</b>

　　Mở tập tin <b>AndroidMainfest.xml</b> trong project Android ứng dụng
-   Thêm các thẻ ```<permission>``` để cấp quyền truy cập cho hệ thống:

```
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
- Thêm các thẻ  ```<receiver>,<service> ``` bên trong thẻ ```<application>``` để nhận thông báo từ <b>Google Cloud Messaging</b>:

```
　　<application
　　　　...........................
　　　　<receiver
　　　　　　android:name="com.ingamesdk.pushnotification.GcmBroadcastReceiver"
　　　　　　android:permission="com.google.android.c2dm.permission.SEND" >
　　　　　　<intent-filter>
　　　　　　　　<!-- Receives the actual messages. -->
　　　　     		<action android:name="com.google.android.c2dm.intent.RECEIVE" />
　　　　　　　　<category android:name="<your_package_name>" />
　　　　　　</intent-filter>
　　　　</receiver>
　　　　<service android:name="com.ingamesdk.pushnotification.GcmIntentService" />
　　　　...........................
　　</application>
```
- Thêm các thẻ ```<meta-data>``` bên trong thẻ ```<application>``` để cấu hình các giá trị cho hệ thống:

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
　　　　...........................
　　</application>
```
- Thêm các thẻ ```<activity>``` để cấu hình các giao diện hệ thống:

```
　　<application>
　　　　...........................
 		<activity
　　　　　　android:name="com.ingamesdk.ui.LoginActivity"
            	android:configChanges="orientation|keyboardHidden|screenSize"
            	android:windowSoftInputMode="adjustPan" />
        	<activity
            	android:name="com.ingamesdk.ui.PaymentActivity"
            	android:configChanges="orientation|keyboardHidden|screenSize"
            	android:windowSoftInputMode="adjustPan" />
        	<activity
            	android:name="com.ingamesdk.ui.UserInfoActivity"
            	android:configChanges="orientation|keyboardHidden|screenSize|locale"
            	android:windowSoftInputMode="adjustPan" />
        	<activity android:name="com.facebook.LoginActivity" />
　　　　...........................
　　</application>
```
###III. Cách thức khai báo, khởi tạo và gọi các chức năng của SDK từ ứng dụng của bạn
<b>Khai báo lớp ```Receiver.java``` để nhận về các sự kiện từ SDK from the SDK</b>

```
　　private class GameReceiver extends IGReceiver {
		
　　　　@Override
		public void onLoginSuccess(IGSession session) {
		}

		@Override
		public void onLogoutSuccess() {
		}
	}
```

>Thông qua biến session bạn có thể lấy được thông tin của tài khoản bằng cách gọi những hàm sau:<br/>
> ```session.getUserName()```: Tên tài khoản đăng nhập <br/>
> ```session.getUserId()```: Số ID của tài khoản hiện tại <br/>
> ```session.getAccessToken()```: Access token <br/>
> ```session.getEmail()```: Thông tin email của tài khoản <br/>
> ```session.getPhone()```: Thông tin số điện thoại của tài khoảnr <br/>

　　
<b>Khai báo các biến sau vào bên trong lớp Activity chính của ứng dụng:</b>

	public String app_id = "911fe9dbd0f94625b4b591301c0f3818"; // ID ứng dụng của bạn
	public String app_key = "a2de0d67a7aecbcc89293fbed6712b1a"; // Key ứng dụng của bạn
	private GameReceiver game_receiver = new GameReceiver();
	private IntentFilter filter = new IntentFilter();
	public static InGameSDK ingame_sdk = InGameSDK.getInstance(); // instance của InGameSDK
　　
<b>Thiết lập các giá trị cho InGameSDK bên trong hàm onCreate(...)</b>

	ingame_sdk.init(this, true, true, callback_url, this.app_id, this.app_key);

<b>Các tham số của hàm ingame_sdk.init(...)</b>

```
　　public void init(Activity context, boolean isButton, boolean isAutoLogin, String callback_url, String app_id, String 	app_key) {...}
```

>```context```:  Activity chính của ứng dụng<br/>
>```isButton```: Tham số sử dụng nút phím tắt của SDK<br/>
>```isAutoLogin```: Tham số tự động đăng nhập<br/>
>```callback_url```: Đường link gọi callback của Server<br/>
>```app_id```: ID ứng dụng của bạn được cấp bởi Ingame<br/>
>```app_key```: Key ứng dụng của bạn được cấp bởi Ingame<br/>

<b>Thêm các xử lý sau vào tương ứng từng hàm</b>
```
　　@Override
	protected void onResume() {
		super.onResume();
		filter.addAction(this.getPackageName() + "ingame.login.success");
		filter.addAction(this.getPackageName() + "ingame.logout.success");
		registerReceiver(game_receiver, filter);
		ingame_sdk.addSDKButton(this);
		ingame_sdk.setContext(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		ingame_sdk.removeSDKButton(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(game_receiver);
	}
```

<b>Gọi hàm tương ứng với các chức năng mà SDK cung cấp cho từng thao tác:</b>
```
　  Đăng ký: 				ingame_sdk.callRegister();
　　Đăng nhập:				       ingame_sdk.callLogin();
　　Đăng xuất: 					 ingame_sdk.callLogout();
　　Hiển thị thông tin tài khoản: 			ingame_sdk.callshowUserInfo();
　　Thanh toán: 				 	ingame_sdk.callPayment(String game_order); // game_order:Mã giao dịch do 
　　Nhà phát triển tự tạo ra (nhỏ hơn 50 ký tự)..
```


####IV. Ứng dụng minh họa

Ứng dụng minh họa đi kèm SDK là một ví dụ đơn giản cho việc tích hợp. 

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
        <td>
            <i>Giao diện chọn phương thức đăng nhập</i>
        </td>
        <td>
            <i>Giao diện đăng nhập</i>
        </td>
        <td>
          <i> Giao diện đăng ký tài khoản mới </i>
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
        <td>
            <i>Giao diện quên mật khẩu</i>
        </td>
        <td>
            <i>Giao diện thông tin tài khoản</i>
        </td>
        <td>
          <i> Giao diện Lịch sử giao dịch </i>
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
    <tr>
        <td textAlign="center">
            <i>Giao diện chọn phương thức thanh toán</i>
        </td>
        <td textAlign="center">
            <i>Giao diện thanh toán bằng thẻ cào điện thoại</i>
        </td>
        <td textAlign="center">
          <i> Giao diện thanh toán bằng Internet Banking </i>
        </td>
    </tr>



</table>
