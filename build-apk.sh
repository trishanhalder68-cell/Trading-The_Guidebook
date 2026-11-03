#!/usr/bin/env bash
# ==============================================================================
# build-apk.sh
# Compiles the Debug APK for personal testing in GitHub Codespaces or local CLI
# ==============================================================================
set -e

# Ensure debug.keystore exists (restore from base64 if needed)
if [ ! -f "debug.keystore" ] && [ -f "debug.keystore.base64" ]; then
    echo "🔑 Restoring debug.keystore from debug.keystore.base64..."
    base64 -d debug.keystore.base64 > debug.keystore 2>/dev/null || base64 -D debug.keystore.base64 > debug.keystore
fi

# Ensure gradlew has execute permission
chmod +x ./gradlew 2>/dev/null || true

# Set SDK path if present in default directory and not exported
if [ -z "$ANDROID_HOME" ] && [ -d "$HOME/android-sdk" ]; then
    export ANDROID_HOME="$HOME/android-sdk"
    export ANDROID_SDK_ROOT="$HOME/android-sdk"
    export PATH="$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH"
fi

echo "🔨 Building Debug APK with Gradle..."
if [ -f "./gradlew" ]; then
    ./gradlew :app:assembleDebug --no-daemon
elif command -v gradle &> /dev/null; then
    gradle :app:assembleDebug --no-daemon
else
    echo "❌ Error: Neither ./gradlew nor system gradle was found."
    exit 1
fi

APK_PATH="app/build/outputs/apk/debug/app-debug.apk"

if [ -f "$APK_PATH" ]; then
    APK_SIZE=$(du -h "$APK_PATH" | cut -f1)
    echo ""
    echo "================================================================================"
    echo "  BUILD SUCCEEDED! 🚀"
    echo "================================================================================"
    echo "  APK File : $APK_PATH"
    echo "  Size     : $APK_SIZE"
    echo "================================================================================"
    echo ""
    echo "📥 HOW TO DOWNLOAD YOUR APK TO YOUR COMPUTER/PHONE:"
    echo ""
    echo "  Option 1 (Easiest - Direct Click via Built-in Web Server):"
    echo "    Run: ./serve-apk.sh"
    echo "    GitHub Codespaces will show a notification: 'Application running on port 8080'."
    echo "    Click 'Open in Browser' -> click 'app-debug.apk' to download instantly!"
    echo ""
    echo "  Option 2 (VS Code File Explorer):"
    echo "    1. In the left file explorer sidebar, expand:"
    echo "       app -> build -> outputs -> apk -> debug"
    echo "    2. Right-click on 'app-debug.apk'"
    echo "    3. Click 'Download...'"
    echo ""
    echo "  Option 3 (Install directly on connected phone via ADB):"
    echo "    adb install -r $APK_PATH"
    echo "================================================================================"
else
    echo "⚠️ Warning: Expected APK at $APK_PATH was not found. Please review build output."
fi
