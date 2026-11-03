# Trading: The Guidebook 📈
> High-performance Android application with a comprehensive trading masterclass, real-time institutional market intelligence, Gemini AI mentorship, and warm minimalist Apple HIG ergonomics.

---

## ⚡ Quick Start: Build & Download in GitHub Codespaces

You can build and download the debug `.apk` file directly inside **GitHub Codespaces** without needing Android Studio installed on your computer.

### Step 1: Open GitHub Codespace Terminal
Once your Codespace is running, open the integrated terminal (`Ctrl + ~` or `Cmd + ~`).

### Step 2: One-Step Setup (Run Once)
Run the automated environment setup script. This configures Java 17, Android SDK command-line tools, and restores the signing credentials:
```bash
bash ./setup-codespace.sh
```

### Step 3: Build the Debug APK
Compile the test APK:
```bash
./build-apk.sh
```
*Alternatively, you can run directly:*
```bash
./gradlew :app:assembleDebug
```

The APK will be generated at:
```
app/build/outputs/apk/debug/app-debug.apk
```

---

## 📥 How to Download the APK to Your Computer / Phone

Choose any of the following 3 easy methods:

### Method 1: One-Click Browser Download (Recommended)
Run:
```bash
./serve-apk.sh
```
1. GitHub Codespaces will display a notification: *"Your application running on port 8080 is available."*
2. Click **Open in Browser**.
3. Click `app-debug.apk` to download directly to your laptop or phone!
4. Press `Ctrl + C` in the terminal when done.

### Method 2: Download via VS Code File Explorer
1. In the left VS Code file explorer sidebar, expand the folders:
   `app` ➔ `build` ➔ `outputs` ➔ `apk` ➔ `debug`
2. Right-click on **`app-debug.apk`**.
3. Select **Download...** to save it locally.

### Method 3: Install via ADB (Connected Android Device)
If you have an Android device connected with USB debugging enabled:
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

---

## 📱 In-App Testing Notes & Features

- **Developer Bypass Code**: Enter `TRISH30` in the Checkout dialog to instantly unlock all 6 Masterclass Chapters and all 4 Trader Roadmap tiers for testing.
- **Masterclass Pricing**: All individual chapters are priced at ₹20.
- **Gemini AI Market Mentor**: Connected via Firebase AI Logic for contextual trading queries.
- **Institutional Portals**: Direct, crash-proof access to NSE India, RBI Financial Stability, SEBI Investor Education, MOSPI Macro Data, and Google Finance.
- **Design System**: Warm minimalist beige aesthetic (`#FBF9F5` / `#1C1B1A`) with dynamic Apple HIG-inspired typography and card ergonomics.

---

## 🛠️ Local Machine Requirements (If building outside Codespaces)

- **Java**: OpenJDK 17 or higher (`java -version`)
- **Android SDK**: API 36 with Build-Tools 36.0.0
- **Environment Variable**: `ANDROID_HOME` or `ANDROID_SDK_ROOT` set to your Android SDK directory.
