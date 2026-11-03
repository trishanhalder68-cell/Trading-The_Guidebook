#!/usr/bin/env bash
# ==============================================================================
# serve-apk.sh
# Serves the generated APK over HTTP for one-click downloading from GitHub Codespaces
# ==============================================================================
set -e

APK_DIR="app/build/outputs/apk/debug"
PORT=8080

if [ ! -d "$APK_DIR" ]; then
    echo "⚠️ APK directory $APK_DIR does not exist yet. Running build first..."
    ./build-apk.sh
fi

echo ""
echo "📡 Starting local download server on port $PORT..."
echo "👉 In GitHub Codespaces, a notification will appear saying 'Port 8080 forwarded'."
echo "👉 Click 'Open in Browser' to download app-debug.apk directly to your computer/phone!"
echo "👉 Press Ctrl + C to stop the server when finished."
echo ""

if command -v python3 &> /dev/null; then
    python3 -m http.server "$PORT" --directory "$APK_DIR"
elif command -v python &> /dev/null; then
    python -m SimpleHTTPServer "$PORT"
else
    echo "❌ Python is not installed. Use the VS Code file explorer to right-click and download app-debug.apk."
fi
