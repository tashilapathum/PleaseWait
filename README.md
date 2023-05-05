# PleaseWait 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.tashilapathum/please-wait/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.tashilapathum/please-wait)

PleaseWait is a lightweight library that can be used as a drop-in replacement for now-deprecated `android.app.ProgressDialog`.
According to Google, the reason to deprecate the good old `ProgressDialog` is:
> ProgressDialog is a modal dialog, which prevents the user from interacting with the app. Instead of using this class, you should use a progress indicator like ProgressBar, which can be embedded in your app's UI.

I understand the reasoning but it means we have to do some more work to prevent the user from doing something unexpected before the operation is finished. Come on, using a progress dialog is so much easier. So I made this library to use in my apps to avoid those deprecation warnings everywhere and to improve the look of the progress dialog. Also I wanted to learn the process of publishing a library.


## Preview
| Indeterminate mode | Determinate mode | Dark and light modes |
| --- | --- | --- |
| ![Preview](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExZjczMmM0NzE1N2FlMmI3NmU5ZDYyODZmNWIzZjlhMjk3ZGZhMGU3YSZjdD1n/WxR15UuJyCwBzhpEYj/giphy.gif) | ![Preview](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExNzc0YmQ5ZTBhMDU0YWNkOTJlMjIyOThlNGE0ZDQ3OTJjNTA0NTM2NiZjdD1n/dPOJQYUIjGCzMBd31N/giphy.gif) | ![Preview](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExMDAxYjM1YTkwNDUzM2ZkYzg4YTdmNGE5ODlkMThmNTRhZmExNGUxYyZjdD1n/Z7aZNVYnjYEgTbTZS2/giphy.gif) | 


## Features
- Supports Material 2 and the latest Material 3 design
- Both determinate and indeterminate progress modes
- Both circular and linear progress bars
- Ability to set a delay before showing
- Ability to set a minimum duration to show the dialog
- Follows Dark and Light modes automatically
- Adapts to your app's theme colors
- Retains state between orientation changes
- Smooth Material animations and transitions
- Lightweight and Easy to implement
- Fully interoperable with Java


## How to use
1. Add the dependency to the app-level `build.gradle`. 

```gradle
implementation 'io.github.tashilapathum:please-wait:0.4.0'
```

2. Intitialize with `Activity` or `Fragment` context and show.
```kotlin
val progressDialog = PleaseWaitDialog(context = this)
progressDialog.show()
```

3. Optionally set title and message
```kotlin
progressDialog.setTitle("Please wait")
progressDialog.setMessage("Loading...")
```

4. Dismiss when the operation is complete
```
progressDialog.dismiss()
```


## Additional options

- Choose progress style: `CIRCULAR`, `LINEAR`, `BOTH` or `NONE`. Default is `CIRCULAR`
```kotlin
progressDialog.setProgressStyle(PleaseWaitDialog.ProgressStyle.LINEAR)
```

- Set determinate or indeterminate mode. Default is `true`.
```kotlin
progressDialog.setIndeterminate(false) //apply to both progress bars
progressDialog.setIndeterminate(ProgressStyle.LINEAR, false) //apply to a specific progress bar
```

- Set progress. You can just set the progress and the progress bars will smoothly animate from indeterminate to determinate mode.
```kotlin
progressDialog.setProgress(20)
```

- Set a delay before showing to avoid flashing the progress dialog for short operations. The dialog won't be shown if you called `dismiss()` before the time has elapsed.
```kotlin
progressDialog.setShowDelay(2000)
```

- Set a delay before dismissing the dialog to show the dialog for a minimum amount of time.
```kotlin
progressDialog.setDismissDelay(3000)
```

- Set title and message by overriding resources on `strings.xml`. There's no title or message by default. 
```xml
<string name="please_wait_dialog_default_title">Please wait</string>
<string name="please_wait_dialog_default_message">Loading…</string>
```


## Java implementation
```java
PleaseWaitDialog progressDialog = new PleaseWaitDialog(this);
progressDialog.seTitle("Please wait");
progressDialog.setMessage("Loading...");
progressDialog.setCancelable(false);
progressDialog.show();
```
