<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Screen Share</title>
    <link rel="stylesheet" type="text/css" href="/css/screenShare.css">
</head>
<body>
    <div class="main-container">
        <!-- Боковые блоки -->
        <div class="sidebar">
            <div class="tab-container">
                <div class="tab active-tab" onclick="switchTab('chat')">Chat</div>
                <div class="tab" onclick="switchTab('viewers')">Viewers</div>
                <div class="tab" onclick="switchTab('settings')">Settings</div>
            </div>

            <!-- Чат -->
            <div class="tab-content" id="chat">
                <div class="chat-container">
                    <h3>Chat</h3>
                    <div class="chat-box" id="chatBox">
                        <!-- Здесь будут отображаться сообщения -->
                    </div>
                    <div class="message-input">
                        <input type="text" id="messageInput" placeholder="Type a message">
                        <button id="sendMessageButton">Send</button>
                    </div>
                </div>
            </div>

            <!-- Список зрителей -->
            <div class="tab-content" id="viewers">
                <div class="viewers-container">
                    <h3>Viewers</h3>
                    <div class="viewers-list" id="viewersList">
                        <!-- Список зрителей -->
                    </div>
                </div>
            </div>

            <!-- Настройки -->
            <div class="tab-content" id="settings">
                <div class="settings-container">
                    <h3>Settings</h3>
                    <div class="setting">
                        <label for="quality">Video Quality:</label>
                        <input type="range" id="quality" name="quality" min="1" max="10">
                    </div>
                    <div class="setting">
                        <label for="audio">Audio:</label>
                        <input type="checkbox" id="audio" checked> Enable Audio
                    </div>
                    <div class="setting">
                        <label for="accessKey">Access Key:</label>
                        <span id="accessKey">${accessKey}</span>
                    </div>
                    <div class="setting">
                        <button id="startShareButton">Start Screen Share</button>
                        <form action="/events/screen-share/${accessKey}/exit" method="GET" style="${isHost ? 'display:block' : 'display:none'}">
                            <button type="submit" id="exitButton" class="exit-button">Exit</button>
                        </form>
                        <form action="/" method="GET" style="${!isHost ? 'display:block' : 'display:none'}">
                            <button type="submit" id="homeButton" class="home-button">Home</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Видео -->
        <div class="video-container">
            <video id="videoElement" autoplay></video>
            <div class="event-info">
                <h3>Event Title</h3>
                <p>Event description goes here. This is where the details of the event will be displayed.</p>
            </div>
        </div>
    </div>
    <script>
        var isHost = ${isHost};  // Это значение будет доступно в JavaScript
    </script>
    <script src="/scriptJS/demo.js" defer></script>
</body>
</html>
