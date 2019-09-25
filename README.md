<div style="width:100%">
<div style="width:100%">
	<div style="width:50%; display:inline-block">
		<p align="center">
		<img align="center" width="180" height="180" alt="" src="https://raw.githubusercontent.com/wajahatkarim3/Chaty/master/Screenshots/Chaty_Logo.png">	
		</p>	
	</div>	
</div>
</br>
</br>
</div>

<div align="center">
  <sub>Built with ❤︎ by
  <a href="https://twitter.com/WajahatKarim">Wajahat Karim</a> and
  <a href="https://github.com/wajahatkarim3/Chaty/graphs/contributors">
    contributors
  </a>
</div>
<br/>

Chaty is an Android Demo app (using **CometChat Pro**) for a fully functional messaging app capable of **one-on-one** (private) messaging. The app enables users to send **text** messages to other users in realtime.

This branch [`push-notifications-finish`](https://github.com/wajahatkarim3/Chaty/tree/push-notifications-finish) is an example demo to send push notifications for chat messages to other users' Android devices. 

### Features of this demo
This demo showcases the implementation of the push notifications in the Chaty app using CometChat Pro and Firebase Messaging (FCM). This includes:
* Sending Notifications when app is background or completely removed from the Recent Apps in Android
* Sending Notifications when user is inside app in Contacts or Profile or any other chat conversation screen
* Don't send notification when user is already chatting with the sender user and chat is open
* User can mute / unmute any specific user's notifications at anytime

[![Platform](https://img.shields.io/badge/Platform-Android-brightgreen.svg)](#)      [![Platform](https://img.shields.io/badge/Language-Kotlin-yellowgreen.svg)](#)

## ScreenShots
![](https://raw.githubusercontent.com/wajahatkarim3/Chaty/push-notifications-finish/Screenshots/Push%20Notifications%20-%20Demo.gif)

## Technology
This demo uses:

* CometChat Pro Android SDK (1.3.+) versions
* Android and Kotlin
* Material Components by Google
* Glide for Image Loading
* Firebase Messaging (FCM) for Push Notifications
* [Notify](https://github.com/Karn/notify) for creating Android notifications

## Installation

   Simply Clone the project from this repository and open in Android Studio. Then follow the below instructions.
   

## Run the Sample App
To run the demo follow these steps:

1. [Head to CometChat Pro and create an account](https://cometchat.com/pro?utm_source=github&utm_medium=wajahatkarim3-chaty-readme)
2. From the [dashboard](https://app.cometchat.com/?utm_source=github&utm_medium=wajahatkarim3-chaty-readme), create a new app called "One-To-One Android Chat"
3. Once created, click **Explore**
4. Go to the **API Keys** tab and click **Create API Key**
5. Create an API key called "Android One-To-One Chat Key" with **Full Access**
6. Clone the Chaty project from the `push-notifications-finish` branch from [this link](https://github.com/wajahatkarim3/Chaty/tree/push-notifications-finish). When you open the project, the gradle sync will fail with an error saying that `google-services.json` file is missing. Don't worry about this and proceed to the next step.
7. Create a project in [Firebase Console](https://console.firebase.google.com). 
8. When your Firebase project is ready, add an Android app in it. Add your package name, app name, your system's debug fingerprint SHA1 key. If you don't know how to get the fingerprint SHA1, follow the step 9.
9. (Optional) - On Android Studio in the right side, click on `Gradle` tab. Then run the `Your Project` -> `Tasks` -> `android` -> `signingReport` task by double clicking on it. This will print your SHA1 key in the `Run` panel in Android Studio. Copy that to the Firebase.
10. In the next step, Firebase will allow you to download `google-services.json` file. Download that and copy it your Android project in the root directory of the `app` folder.
12. Open the `app/build.gradle` and change the `applicationId` with the same exact package name used in the Firebase to create Android app in the project.
11. Update [strings.xml](https://github.com/wajahatkarim3/Chaty/blob/push-notifications-finish/app/src/main/res/values/strings.xml) with your newly-created `appID` and `apiKey`
12. Press the Gradle Sync / Sync Now button and now it should be successful.
13. Now, in Firebase Console, open `Project Settings` and then choose `Cloud Messaging` tab. Copy the `Token` field of the `Server Key` row.
13. In Comet Chat project dashboard, go to `Extensions` and add `Push Notifications` extension. Once added, click on the `Actions` button for the extension, and select `Settings` from drop-down. It will show a popup with the Title and `FCM Server Key`. Put app name as `Title` and paste the copied `FCM Server Key` here from Firebase.
13. Run the app on two emulators / devices and login on each with one of the test users: `superhero1`, `superhero2`, `superhero3`, `superhero4` or `superhero5`
14. Try sending messages to each other and see the push notifications.

Questions about running the demo? [Open an issue](https://github.com/wajahatkarim3/Chaty/issues). We're here to help ✌️


## Useful links

- 🏠 [CometChat Homepage](https://cometchat.com/pro?utm_source=github&utm_medium=wajahatkarim3-chaty-readme)
- 🚀 [Create your free account](https://app.cometchat.com?utm_source=github&utm_medium=wajahatkarim3-chaty-readme)
- 📚 [Documentation](https://prodocs.cometchat.com/docs?utm_source=github&utm_medium=wajahatkarim3-chaty-readme)
- 👾 [GitHub](https://github.com/CometChat-Pro)
   
