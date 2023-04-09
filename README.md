# PleaseWait 

PleaseWait is a lightweight library that can be used as a replacement for now deprecated `android.app.ProgressDialog`.
According to Google the reason to deprecate the good old `ProgressDialog` is:
> ProgressDialog is a modal dialog, which prevents the user from interacting with the app. Instead of using this class, you should use a progress indicator like ProgressBar, which can be embedded in your app's UI.

I understand the reasoning but it means we have to do some more work to prevent the user from doing something unexpected before the operation is finished. Come on, using a progress dialog is so much easier. So I made this library to use in my apps just to avoid those deprecation warnings everywhere and to improve the look of the progress dialog. Also I wanted to learn the process of publishing a library.

This is stil in beta so I would appreciate any feedback :)


## Preview
![Preview](https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExMGFjM2ZlODliNzc2YjJlZjk3ZGQxNDAxNTBiZTliY2Y0ZDRjNmExMyZjdD1n/mitgNrEa2SszFJTUHQ/giphy.gif)


## Features
- Supports Material 2 and the latest Material 3 design
- Follows Dark and Light modes automatically
- Adapts to your app's theme colors
- Retains state between orientation changes


## How to use
1. Add the dependency to the app-level `build.gradle`. 

   <sup>Latest version:</sup> [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.tashilapathum/please-wait/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.tashilapathum/please-wait)
```gradle
implementation 'io.github.tashilapathum:please-wait:$latest_version'
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

## TODO
- [x] Test in Fragments and Dialogs
- [ ] Add more scenerios to sample app
- [ ] Add horizontal progress bar option
- [ ] Add determinate progress mode
