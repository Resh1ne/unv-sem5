<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Screen Share</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            height: 100vh;
        }

        /* Контейнер для видео и боковых блоков */
        .main-container {
            display: flex;
            flex-grow: 1;
            width: 100%;
        }

        /* Блок с видео */
        .video-container {
            flex-grow: 2;
            padding: 20px;
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
            align-items: center;
            background-color: #f0f0f0;
        }

        video {
            width: 80%;
            height: auto;
            border: 2px solid #ccc;
            border-radius: 8px;
        }

        .event-info {
            margin-top: 20px;
            text-align: center;
            background-color: #333;
            color: white;
            padding: 15px;
            border-radius: 8px;
            width: 80%;
        }

        /* Боковые блоки (чат, зрители, настройки) */
        .sidebar {
            width: 250px;
            background-color: #333;
            color: white;
            padding: 20px;
            overflow-y: auto;
        }

        /* Блоки внутри сайдбара */
        .tab-container {
            display: flex;
            flex-direction: column;
            margin-bottom: 20px;
        }

        .tab {
            background-color: #444;
            color: white;
            padding: 10px;
            margin: 5px 0;
            cursor: pointer;
            border-radius: 5px;
        }

        .tab:hover {
            background-color: #555;
        }

        .active-tab {
            background-color: #4CAF50;
        }

        /* Контейнер для контента вкладки */
        .tab-content {
            display: none;
        }

        .active-content {
            display: block;
        }

        /* Чат */
        .chat-container {
            display: flex;
            flex-direction: column;
            height: 100%;
        }

        .chat-box {
            flex-grow: 1;
            background-color: white;
            border: 1px solid #ccc;
            margin-bottom: 10px;
            padding: 10px;
            overflow-y: auto;
            max-height: 600px; /* Увеличили размер чата */
        }

        .message-input {
            display: flex;
        }

        .message-input input {
            width: 80%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .message-input button {
            width: 20%;
            padding: 10px;
            border: 1px solid #ccc;
            background-color: #4CAF50;
            color: white;
            border-radius: 4px;
            cursor: pointer;
        }

        .message-input button:hover {
            background-color: #45a049;
        }

        /* Список зрителей */
        .viewers-container {
            display: flex;
            flex-direction: column;
            height: 100%;
        }

        .viewers-list {
            flex-grow: 1;
            background-color: white;
            border: 1px solid #ccc;
            margin-bottom: 10px;
            padding: 10px;
            overflow-y: auto;
            max-height: 300px;
        }

        .viewer {
            padding: 8px;
            border-bottom: 1px solid #ccc;
        }

        /* Настройки */
        .settings-container {
            display: flex;
            flex-direction: column;
            height: 100%;
        }

        .setting {
            margin-bottom: 15px;
        }

        .setting label {
            display: block;
            margin-bottom: 5px;
        }

        .setting input {
            padding: 8px;
            width: 200px;
        }

        .exit-button {
            padding: 10px;
            background-color: #f44336;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .exit-button:hover {
            background-color: #e53935;
        }
    </style>
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
                        <div class="viewer">Viewer 1</div>
                        <div class="viewer">Viewer 2</div>
                        <div class="viewer">Viewer 3</div>
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
                        <label for="key">Stream Key:</label>
                        <input type="text" id="streamKey" disabled>
                    </div>
                    <div class="setting">
                        <button class="exit-button" onclick="exitStream()">Exit</button>
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
        // Функция для переключения вкладок
        function switchTab(tabName) {
            // Скрыть все вкладки
            var tabContents = document.querySelectorAll('.tab-content');
            tabContents.forEach(function(content) {
                content.classList.remove('active-content');
            });

            // Убрать активный класс с всех вкладок
            var tabs = document.querySelectorAll('.tab');
            tabs.forEach(function(tab) {
                tab.classList.remove('active-tab');
            });

            // Показать выбранную вкладку и сделать её активной
            document.getElementById(tabName).classList.add('active-content');
            var activeTab = Array.from(tabs).find(function(tab) {
                return tab.textContent.toLowerCase() === tabName;
            });
            activeTab.classList.add('active-tab');
        }

        // Генерация рандомного ключа для трансляции
        function generateStreamKey() {
            var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
            var key = '';
            for (var i = 0; i < 10; i++) {
                key += characters.charAt(Math.floor(Math.random() * characters.length));
            }
            document.getElementById('streamKey').value = key;
        }

        // Функция для выхода
        function exitStream() {
            alert('You have exited the stream.');
            // Здесь можно добавить логику выхода из трансляции
        }

        // Генерация ключа при загрузке страницы
        window.onload = generateStreamKey;

        // Подключение к WebSocket серверу
        var socket = new WebSocket("ws://localhost:8080/screen-share");

        socket.onopen = function(event) {
            console.log("Connected to WebSocket server.");
        };

        socket.onmessage = function(event) {
            console.log("Message from server: " + event.data);
            // Обработка сообщений чата
            var chatBox = document.getElementById("chatBox");
            var message = document.createElement("div");
            message.textContent = event.data;
            chatBox.appendChild(message);
            chatBox.scrollTop = chatBox.scrollHeight;
        };

        // Функция для захвата экрана
        async function startScreenShare() {
            try {
                const stream = await navigator.mediaDevices.getDisplayMedia({ video: true });
                const videoElement = document.getElementById("videoElement");
                videoElement.srcObject = stream;

                // Отправка потока экрана через WebSocket
                const mediaRecorder = new MediaRecorder(stream);
                mediaRecorder.ondataavailable = function(event) {
                    if (event.data.size > 0) {
                        socket.send(event.data); // Отправка данных видеопотока через WebSocket
                    }
                };
                mediaRecorder.start(100); // Отправлять данные каждые 100ms
            } catch (err) {
                console.error("Error accessing screen: " + err);
            }
        }

        startScreenShare();
    </script>
</body>
</html>
