## Introduction

Drive Buddy aims to provide safer travel for all drivers using only a phone's sensors. Drive Buddy SDK can detect crashes using sensor data and notify insurance companies or call chosen contacts. Drive Buddy will also build a detailed information and analysis of drivings. For letting Drive Buddy to collect driving data, you should place Drive Buddy SDK to your application. Drive Buddy SDK will collect Acceleration and Location data using the phone's sensors and build analysis of drivings.

## Get your SDK key

To get your SDK key, you need to contact us via E-Mail. You can send a mail with subject `Request: SDK Key` to [support@drivebuddyapp.com](mailto:support@drivebuddapp.com?Subject=Request:%20SDK%20Key). 

## Running Sample App

All you need to do is get your SDK key as mentioned above, and then add your SDK key to line 151 in `MainActivity` where it writes `***sdk-key***`.

```java
DriveBuddyConfiguration config = new DriveBuddyConfiguration("***sdk-key***",  
                                                              username,  
                                                              drivingDetection,  
                                                              notification,  
                                                              firstName,  
                                                              surname,  
                                                              mail);
```

#### _For more information please visit [Android Docs](https://docs.drivebuddyapp.com/android/#introduction)._