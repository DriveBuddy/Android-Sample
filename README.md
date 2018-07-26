# Drive Buddy Android SDK Docs


- [Introduction](#introduction)  
- [Get your SDK key](#get-your-sdk-key)  
- [Integration](#integration)  
- [Using Drive Buddy SDK](#using-drivebuddy-sdk)  
- [Tips](#tips)  
- [API Interface Reference](#api-interface-reference)


## Introduction

Drive Buddy aims to provide safer travel for all drivers using only a phone's sensors. Drive Buddy will also build a detailed information and analysis of drivings. For letting Drive Buddy to collect driving data, you should place Drive Buddy SDK to your application. Drive Buddy SDK will collect Acceleration and Location data using the phone's sensors and build analysis of drivings. Drive Buddy SDK can also detect crashes using sensor data and notify you.

## Get your SDK key

To get your SDK key, you need to contact us via E-Mail. You can send a mail with subject `Request: SDK Key` to [support@drivebuddyapp.com](mailto:support@drivebuddapp.com?Subject=Request:%20SDK%20Key). 

## Integration

#### *Before starting with anything, you can always check how we encourage you to use Drive Buddy SDK on the sample app. Sample app can be found at [github.com/DriveBuddy/Android-Sample](https://github.com/DriveBuddy/Android-Sample).*

1. Your project has multiple `build.gradle`  files, one for the whole project and one for each module. You need to add the following lines into your module's local `build.gradle` file. Be sure that you are using the latest available version.

	```java
	repositories {  
		jcenter()  
	}  
	dependencies {
		implementation 'com.drivebuddyapp:drivebuddysdk:1.0.0'
	}
	```
2. Now your `build.gradle` file should look something similar to this.
	
	```java

	apply plugin: 'com.android.application'

	android {
	    compileSdkVersion 27
	    buildToolsVersion "27.0.1"

	    defaultConfig {
	        applicationId "com.XXX.XXX"
	        minSdkVersion 11
	        targetSdkVersion 23
	        versionCode 1
	        versionName "1.0"
	    }
	    buildTypes {
	        release {
	            minifyEnabled false
	            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
	        }
	    }
	}
	repositories {
		jcenter()
	}
	dependencies {  
	    implementation fileTree(dir: 'libs', include: ['*.jar'])  
	    implementation 'com.android.support:appcompat-v7:26.1.0'  
		implementation 'com.android.support.constraint:constraint-layout:1.1.0'  
		implementation 'com.drivebuddyapp:drivebuddysdk:0.0.1'
	}
	```

3. After you run gradle sync, you're done.

## Using Drive Buddy SDK

1. Construct a [`DriveBuddyNotification`](#drivebuddynotification-class) instance.

	```java
	DriveBuddyNotification notification = new DriveBuddyNotification("DriveBuddy", "DriveBuddy is working", R.drawable.ic_stat);  
	```

2. Construct a [`DriveBuddyConfiguration`](#drivebuddyconfiguration-class) instance.
	
	```java
	DriveBuddyConfiguration config = new DriveBuddyConfiguration("**sdk-key**", username, drivingDetection, notification);  
	```

3. Set a [`DriveBuddyOperationalCallback`](#drivebuddyoperationalcallback-class). 
	
	```java
	final DriveBuddyOperationalCallback callback = new DriveBuddyOperationalCallback() {  
	    @Override  
		public void onCompletion(boolean success, String message) {  
			Toast.makeText(MainActivity.this, "DriveBuddy - " + message, Toast.LENGTH_SHORT).show();  
		}  
	};
	```

4. You should setup the SDK using `Drivebuddy.setup()` when you start your app. DriveBuddy SDK will work in the background when it once set.
	
	```java
	DriveBuddy.setup(MainActivity.this, config, callback);
	```

5. The only permission Drive Buddy needs to run is location permission. You can get it with something similart to this.
	
	```java
	if (ActivityCompat.checkSelfPermission(YourActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(YourActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
	      ActivityCompat.requestPermissions(YourActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
	}
	```

6. There are 2 ways you can use Drive Buddy SDK, one of them is with automatic driving detection and other one is starting drives manually.
	- If you want to use automatic driving detection all you need to set `drivingDetection	` in [`DriveBuddyConfiguration`](#drivebuddyconfiguration-class) to `True`.
	- If you want to start drives manually, than you should use [`DriveBuddy.startDrive`](#drivebuddy-class) to start tracking a drive and [`DriveBuddy.stopDrive`](#drivebuddy-class) to stop tracking. Also you need to be sure that you have `drivingDetection` off.


# Tips

#### 1. High Accuracy

Make sure users enable High Accuracy Location on the device. This is important for Drive Buddy to work as intended. If a user doesn't enable high accuracy location, it might not work properly.

#### 2. Subscribe to the BOOT_COMPLETED and MY_PACKAGE_REPLACED intents.

Subscribing to both of these intents and calling `DriveBuddy.setup()` will make sure that Drive Buddy is always set up and ready to detect drives.

- BOOT_COMPLETED will wake up the app to automatic driving detection start when phone is rebooted.
- MY_PACKAGE_REPLACED will wake up the app to start when app is updated. You can check the sample app on how this is done.

# API Interface Reference

## DriveBuddy Class

- `setup(Context context, DriveBuddyConfiguration driveBuddyConfiguration, DriveBuddyOperationalCallback callback)`
	
	Setups the SDK with given configurations.

- `tearDown(Context context, DriveBuddyOperationalCallback callback)`
	
	Deletes all the user data in the device, stop driving detection, opposite of setup. ( Doesn't delete the files that haven't uploaded to server yet. )

- `startDrive(Context context, DriveBuddyOperationalCallback callback)`
	
	Starts driving tracking

- `stopDrive(Context context, DriveBuddyOperationalCallback callback)`
	
	Stops driving tracking

- `isSdkSetup(final Context context, DriveBuddyOperationalCallback callback)`

	Returns true if SDK is initialized, return false if it's not.

- `getCurrentConfiguration(context)`

	Returns the current DriveBuddyConfiguration that's stored in the device.

- `getVersion()`
	
	Returns the current version of SDK.

- `isCrashDetectionSupported(Context context)`

	Returns true if sensors in the device are capable of detect a crash.

- `isScoringSupported(Context context)`

	Returns true if sensors in the device are can give the needed info. 

## DriveBuddyConfiguration Class

- `DriveBuddyConfiguration(String sdkKey, String driverId, boolean drivingDetection, DriveBuddyNotification notification, String driverFirstName, String driverLastName, String driverEmail)`

	Constructor for configuration class. You can instead use constructors at the bottom if you like.
	
	 - `DriveBuddyConfiguration(String sdkKey, String driverId, boolean drivingDetection, DriveBuddyNotification notification)`
	
	
	 - `DriveBuddyConfiguration(String sdkKey, String driverId, boolean drivingDetection, DriveBuddyNotification notification, String driverFirstName, String driverLastName)`


### Getters

- `getSdkKey()`
- `getDriverUserName()`
- `getDrivingDetection()`
- `getDriverFirstName()`
- `getdriverLastName()`
- `getdriverEmail()`

### Setters

- `setSdkKey()`
- `setDriverUserName()`
- `setDrivingDetection()`
- `setDriverFirstName()`
- `setdriverLastName()`
- `setdriverEmail()`

## DriveBuddyNotification Class

- `DriveBuddyNotification(String notificationTitle, String notificationContent, int notificationSmallIcon)`

	Constructor for notification class.

### Getters
- `getNotificationTitle()`
- `getNotificationContent()`
- `getNotificationSmallIcon()`

### Setters

- `setNotificationTitle()`
- `setNotificationContent()`
- `setNotificationSmallIcon()`

## DriveBuddyOperationalCallback Class

- `onCompletion(boolean success, String message)`

	You can see 2 different examples on usage of DriveBuddyOperationalCallback. First one sends a toast notification with a custom message, and second one sends an alert with the message that DriveBuddyOperationalCallback returns.
	
	```java
	final DriveBuddyOperationalCallback callback = new DriveBuddyOperationalCallback() {  
	    @Override  
		public void onCompletion(boolean success, String message) {  
		    if (success) {  
	            Toast.makeText(MainActivity.this, "DriveBuddy - Callback returns success!", Toast.LENGTH_SHORT).show();  
			} else {  
			    Toast.makeText(MainActivity.this, "DriveBuddy - Callback returns failure.", Toast.LENGTH_SHORT).show();  
			}  
	    }  
	};
	```

	```java
	final DriveBuddyOperationalCallback callback = new DriveBuddyOperationalCallback() {  
	    @Override  
		public void onCompletion(boolean success, String message) {  
		    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();  
			alertDialog.setTitle("DriveBuddy");  
			alertDialog.setMessage(message);  
			alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",  
			new DialogInterface.OnClickListener() {  
		        public void onClick(DialogInterface dialog, int which) {  
				    dialog.dismiss();  
				}  
	        });  
			alertDialog.show();
		}  
	};
	```
