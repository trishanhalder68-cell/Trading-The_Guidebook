#!/usr/bin/env bash
# ==============================================================================
# setup-codespace.sh
# Turnkey environment setup for GitHub Codespaces / Linux / macOS
# ==============================================================================
set -e

echo "🚀 [1/5] Setting up Trading:The Guidebook Android Build Environment..."

# 1. Restore debug.keystore from base64 if not present
if [ ! -f "debug.keystore" ]; then
    if [ -f "debug.keystore.base64" ]; then
        echo "🔑 Restoring debug.keystore from debug.keystore.base64..."
        base64 -d debug.keystore.base64 > debug.keystore 2>/dev/null || base64 -D debug.keystore.base64 > debug.keystore
        echo "✅ debug.keystore restored successfully."
    else
        echo "⚠️ debug.keystore.base64 not found, proceeding..."
    fi
else
    echo "✅ debug.keystore is present."
fi

# 2. Check Java JDK 17+
echo "☕ [2/5] Checking Java JDK..."
if ! command -v java &> /dev/null; then
    echo "📦 Java not found. Installing OpenJDK 17..."
    if command -v apt-get &> /dev/null; then
        sudo apt-get update -qq && sudo apt-get install -y -qq openjdk-17-jdk-headless
    fi
else
    echo "✅ Java is installed: $(java -version 2>&1 | head -n 1)"
fi

# 3. Setup Android SDK if not detected
echo "📱 [3/5] Checking Android SDK..."
if [ -z "$ANDROID_HOME" ] && [ -z "$ANDROID_SDK_ROOT" ]; then
    DEFAULT_SDK_DIR="$HOME/android-sdk"
    if [ ! -d "$DEFAULT_SDK_DIR/cmdline-tools" ]; then
        echo "📦 Android SDK not detected. Installing command-line tools into $DEFAULT_SDK_DIR..."
        mkdir -p "$DEFAULT_SDK_DIR/cmdline-tools"
        TMP_ZIP="/tmp/cmdline-tools.zip"
        
        # Download Android commandlinetools
        curl -sSL "https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip" -o "$TMP_ZIP"
        unzip -q "$TMP_ZIP" -d "$DEFAULT_SDK_DIR/cmdline-tools"
        rm -f "$TMP_ZIP"
        
        # Structure as required: cmdline-tools/latest/bin/sdkmanager
        if [ -d "$DEFAULT_SDK_DIR/cmdline-tools/cmdline-tools" ]; then
            mv "$DEFAULT_SDK_DIR/cmdline-tools/cmdline-tools" "$DEFAULT_SDK_DIR/cmdline-tools/latest"
        fi
    fi

    export ANDROID_HOME="$DEFAULT_SDK_DIR"
    export ANDROID_SDK_ROOT="$DEFAULT_SDK_DIR"
    export PATH="$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH"

    # Persist environment variables in ~/.bashrc if not already present
    if ! grep -q "ANDROID_HOME" "$HOME/.bashrc" 2>/dev/null; then
        echo "export ANDROID_HOME=$DEFAULT_SDK_DIR" >> "$HOME/.bashrc"
        echo "export ANDROID_SDK_ROOT=$DEFAULT_SDK_DIR" >> "$HOME/.bashrc"
        echo "export PATH=\$ANDROID_HOME/cmdline-tools/latest/bin:\$ANDROID_HOME/platform-tools:\$PATH" >> "$HOME/.bashrc"
    fi
else
    echo "✅ Android SDK found at: ${ANDROID_HOME:-$ANDROID_SDK_ROOT}"
fi

# 4. Accept Android SDK Licenses & Install Core Components
echo "📜 [4/5] Accepting Android SDK Licenses..."
SDK_BIN="${ANDROID_HOME:-$ANDROID_SDK_ROOT}/cmdline-tools/latest/bin/sdkmanager"
if [ -f "$SDK_BIN" ]; then
    yes | "$SDK_BIN" --licenses > /dev/null 2>&1 || true
    echo "📦 Ensuring platform-tools and platforms;android-36 are available..."
    "$SDK_BIN" "platform-tools" "platforms;android-36" > /dev/null 2>&1 || true
fi

# 5. Make gradle wrapper and build scripts executable
echo "🔧 [5/5] Configuring execution permissions..."
chmod +x ./gradlew 2>/dev/null || true
chmod +x ./build-apk.sh 2>/dev/null || true
chmod +x ./serve-apk.sh 2>/dev/null || true

echo ""
echo "🎉 Setup complete! You can now run:"
echo "   ./build-apk.sh"
echo "to compile the test APK."
