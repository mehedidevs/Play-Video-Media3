**Tlece Player App**

This Android application fetches JSON data from a provided URL, displays it in blocks with video thumbnails, and allows users to click on the thumbnail to play the video. It also implements local database storage using SQLite to store the fetched information locally on the device. Additionally, it fetches updated data from the URL every 5 minutes in the background and updates the local database accordingly. The app also utilizes Firebase Cloud Messaging (FCM) to receive push notifications and display them as notifications with appropriate icons on the Android device.

<img src="https://firebasestorage.googleapis.com/v0/b/videocallapp-1bb92.appspot.com/o/telece%2FScreenshot_20240511_142553.png?alt=media&token=8e1fba90-7f1e-4af3-9c24-ec1e90ad3713" width="200" height="425" /> <img src="https://firebasestorage.googleapis.com/v0/b/videocallapp-1bb92.appspot.com/o/telece%2FScreenshot_20240511_142608.png?alt=media&token=69ef95fe-df19-459d-a76c-fd750549bff3" width="200" height="425" /> <img src="https://firebasestorage.googleapis.com/v0/b/videocallapp-1bb92.appspot.com/o/telece%2FScreenshot_20240511_142620.png?alt=media&token=96b9ae9c-e625-4cdd-8eb6-b1a6756e34e6" width="200" height="425" /> <img src="https://firebasestorage.googleapis.com/v0/b/videocallapp-1bb92.appspot.com/o/telece%2FScreenshot_20240511_142638.png?alt=media&token=b9c4ec96-8fe1-4e52-825b-31c1ee71dcfd" width="200" height="425" />





**Features:**
 - [ ]  Fetch JSON data from the provided URL (videos.json)
 - [ ] Display video blocks with thumbnails, titles, and descriptions
 - [ ] Click on the thumbnail to play the video
 - [ ] Implement local database storage (SQLite) for storing fetched information
 - [ ] Background data fetching and local database update every 5 minutes
 - [ ] Firebase Cloud Messaging (FCM) for push notifications



**To run the project locally, follow these steps:**

Clone the repository: git clone  https://github.com/mehedidevs/Tlece-Video.git 

**Step 1:** Open the project in Android Studio  </br>
**Step 2:** Build and run the project on an Android device or emulator </br> </br>



**Dependencies:**  </br>  
**Retrofit:** For making HTTP requests and handling API calls </br>
**Gson:** For parsing JSON data into Java/Kotlin objects  </br>
**Room:** For local database storage  </br>
**Firebase Cloud Messaging:** For push notifications  </br>
**Kotlin Coroutines:** For  executing the code asynchronously  </br>
**Glide:** For fast and efficient Image loading  </br>



**Contributing:**
Contributions are welcome! Feel free to open issues or pull requests for any improvements or bug fixes.

**License:**
This project is licensed under the MIT License - see the LICENSE file for details.
