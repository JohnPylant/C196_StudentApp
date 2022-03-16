WGU Student Scheduler App for C196 
Author: John (Dylan) Pylant #000982207 
Contact: jpylant@wgu.edu 
Application Version: 1.0 
Date: 3/15/2022

Android Studio Bumblebee | 2021.1.1
Build #AI-211.7628.21.2111.8092744, built on January 18, 2022
Runtime version: 11.0.11+9-b60-7590822 amd64
VM: OpenJDK 64-Bit Server VM by Oracle Corporation
Windows 10 10.0
GC: G1 Young Generation, G1 Old Generation
Memory: 1280M
Cores: 12
Registry: external.system.auto.import.disabled=true



1.  Explain how your application would be different if it were developed for a tablet rather than a phone, including a discussion of fragments and layouts.
Developing an application for a cell phone, rather than for a tablet you must be mindful of the smaller display. 
Fragments help with this challenge by being able to handle its own layout range. They will determine which element to display by size available. 
You wouldn't necessarily have this issue with a big tablet since you have a lot more space to work with. 

2.  Identify the minimum and target operating system your application was developed under and is compatible with.
Designed with Pixel API 28 Android 8.0 or higher SDK 28, with Java 8. The target SDK is 31 and the minimum is SDK 28.

3.  Describe (suggested length of 1–2 paragraphs) the challenges you faced during the development of the mobile application.
I struggled with associating the courses that are assigned to terms. I could not seem to grasp the concept of how to code this. 
I also struggle with the notifications, I had the formatting incorrect which was causing the notifications to go off in real time, rather than the date selected. 

4.  Describe (suggested length of 1–2 paragraphs) how you overcame each challenge discussed in part F3.
I overcame the struggles of not being able to associate the courses that are assigned to terms by going through the bicycle shop videos and by looking through the code repository. 
I managed to overcome the notifications issue by looking through the code repository and by watching the last bicycle shop video on notifications.  

5.  Discuss (suggested length of 1–2 paragraphs) what you would do differently if you did the project again.
I would do more research on other student mobile apps, including a user survey to see what the students want out of the app. 
I would also implement a cloud-based database so it could handle more robust data processing and I would use LiveData<List<className>>
so there isn’t a big delay loading every screen. 

6.  Describe how emulators are used and the pros and cons of using an emulator versus using a development device.
Emulators are used to mimic a real device without having to physically have one. 
The pros of emulators are: 
1. You can use different devices with different APIs to test the code
2. It is much cheaper than using a real device. 
The Cons: 
1. It uses up RAM
2. You can't simulate real everything that a real device has, like battery usage, and other functionalities
