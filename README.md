# News Feed Application

## Project Overview
The **News Feed Application** is an Android app built using Java in Android Studio. It features a user-friendly interface for browsing, saving, and managing news articles. The app uses a locally stored SQLite database for saving favorite articles, ensuring data persistence even when the app is closed. Articles are fetched via an RSS feed from [BBC News](https://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml) using `AsyncTask`.

### Key Features
#### News Browsing
- **AsyncTask for Data Fetching:** News articles are fetched asynchronously, ensuring a smooth user experience.
- **ListView Display:** Articles are presented in a ListView for easy navigation.
- **Progress Bar:** A progress bar is displayed while articles are being loaded.

#### Favorites Management
- **Save Articles:** Users can save articles to their favorites by tapping the star icon.
- **Remove Articles:** Articles can be removed from the favorites list by tapping the star icon again.
- **SQLite Database:** Favorites are stored locally for persistence across sessions.

#### Article Details
- **Detailed View:** Selecting an article displays its full details, including the option to save it or read the full article.
- **External Browser Support:** Users can open articles in their web browser via a "Read Article" button.

#### User Assistance
- **Help Instructions:** A help button displays instructions in an `AlertDialog` for using the app.

#### Multilingual Support
- **Canadian French Support:** All notifications and labels adapt to Canadian French when the phone's language setting is changed.

### Screenshots
#### Main Screen
- Displays news articles in a ListView.
- Progress bar visible during article loading.

#### Favorites
- Shows saved articles.
- Allows users to manage their favorites.

#### Article Details
- Displays detailed article information.
- Includes options to save articles or read them in a web browser.

#### Help Dialog
- Provides step-by-step instructions for using the app.

#### Multilingual Mode
- Interface adapts to display text in Canadian French.

### Technologies Used
- **Programming Language:** Java
- **Database:** SQLite (locally stored on the device)
- **XML Parsing:** XMLPullParser for parsing the RSS feed.
- **UI Components:** ListView, AlertDialog, Toast, ProgressBar

### How It Works
1. **Launch the App:** The app fetches articles from the RSS feed using `AsyncTask` in `MainActivity.java`. Articles are displayed in a ListView.
2. **View Articles:** Select any article to view its details.
3. **Manage Favorites:** Save or remove articles from the favorites list by toggling the star icon.
4. **Help Menu:** Access instructions via the help icon on any page.
5. **Read Full Articles:** Open articles in a web browser from the details screen.
6. **Language Support:** Switch the phone's language to Canadian French for a localized experience.

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/hamdihismail/Android-News-Feed-App
   ```
2. Open the project in Android Studio.
3. Build and run the app on an Android device or emulator.
   
Contributing

    - Hamdi Ismail - Developer
    
License
This project is licensed under the MIT License.
