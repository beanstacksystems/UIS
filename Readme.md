[Facebook Login](https://developers.facebook.com/docs/facebook-login/android)

[Create Android Facebook Key Hash](https://stackoverflow.com/questions/7506392/how-to-create-android-facebook-key-hash)

Here is complete details (For Windows)

1. Download OpenSSl either 3rd or 4th (with e will work better) based on your system 32bit or 64bit .

2. Extract the downloaded zip inside C directory

3. Open the extracted folder up to bin and copy the path ,it should be some thing like C:\openssl-0.9.8k_X64\bin\openssl (add \openssl at end)

4. (Get the path to the bin folder of Jdk ,if you know how,ignore this ) .

Open android studio ~file~Project Structure(ctrl+alt+shift+s) , select SDK location in left side panel ,copy the JDK location and add /bin to it

So final JDK Location will be like C:\Program Files\Android\Android Studio\jre\bin

we are following this method to get Jdk location because you might use embedded jdk like me

enter image description here

now you have OpenSSl location & JDK location

5. now we need debug keystore location , for that open C~>Users~>YourUserName~>.android there should be a file name debug.keystore ,now copy the path location ,it should be some thing like

C:\Users\Redman\.android\debug.keystore

6. now open command prompt and type command

cd YourJDKLocationFromStep4
in my case

 cd "C:\Program Files\Android\Android Studio\jre\bin"
7. now construct the following command

keytool -exportcert -alias androiddebugkey -keystore YOURKEYSTORELOCATION | YOUROPENSSLLOCATION sha1 -binary | YOUROPENSSLLOCATION base64
in my case the command will look like

keytool -exportcert -alias androiddebugkey -keystore "C:\Users\Redman\.android\debug.keystore" | "C:\openssl-0.9.8k_X64\bin\openssl" sha1 -binary | "C:\openssl-0.9.8k_X64\bin\openssl" base64
now enter this command in command prompt , if you did ever thing right you will be asked for password (password is android)

Enter keystore password:  android
thats it ,you will be given the Key Hash , just copy it and use it

For Signed KeyHash construct the following Command

keytool -exportcert -alias YOUR_ALIAS_FOR_JKS -keystore YOUR_JKS_LOCATION | YOUROPENSSLLOCATION sha1 -binary | YOUROPENSSLLOCATION base64
enter your keystore password , If you enter wrong password it will give wrong KeyHash

NOTE

If for some reason if it gives error at some path then wrap that path in double quotes .Also Windows power shell was not working well for me, I used git bash (or use command prompt) .

example

keytool -exportcert -alias androiddebugkey -keystore "C:\Users\Redman\.android\debug.keystore" | "C:\openssl-0.9.8k_X64\bin\openssl" sha1 -binary | "C:\openssl-0.9.8k_X64\bin\openssl" base64