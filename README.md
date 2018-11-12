# Snip-it
App idea to send short audio files to other contacts. Idea courtesy of the lovely Chris Harmantzis.

This Android application utilizes several apis and development platforms. They are as follows:
- Google Play Services
- Firebase Authentication Service
- Firebase Realtime Database

When opening the application, users are prompted to sign-in with their Google accounts. Users must have a Google account to access the application. When a user successfully signs into Google,
an ID token is sent to Firebase to autheticate. Existing users access the application as usual. New users will be prompted to make a username for other users to find them and send them snips.

Snips are small audio recordings that can be sent between friends. Friends are found and identified by the username they make for themselves when accessing Snip-it for the first time. Several audio file formats will be
supported.

The Firebase Realtime Database is used to store information about each user; data like username, email, snips, etc.

Overall, this application aims to allow users to quickly and easily send an audio message to another user, with the recipient effortlessly able to play it back. Fast and reliable communication is the key.
