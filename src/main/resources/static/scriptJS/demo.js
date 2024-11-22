var chatSocket = null;  // Сокет для чата
var lastSentMessage = ""; // Переменная для хранения последнего отправленного сообщения
var isSendingMessage = false; // Флаг для отслеживания отправки сообщения
var sendMessageTimeout = null; // Таймер для сброса флага через время

// Функция для подключения к WebSocket для чата
function connectChatWebSocket() {
    // Проверка, если сокет уже подключен
    if (chatSocket && chatSocket.readyState === WebSocket.OPEN) {
        console.warn("Chat WebSocket already exists.");
        return chatSocket;
    }

    var keyElement = document.getElementById('accessKey');
    var key = keyElement.value || keyElement.textContent; // Проверяем источник ключа
    if (!key) {
        alert("Access key is required!");
        return null;
    }

    // Создание WebSocket соединения
    chatSocket = new WebSocket("ws://localhost:8080/chat/" + key);

    chatSocket.onopen = function () {
        console.log("Connected to chat WebSocket server for key:", key);
    };

    chatSocket.onmessage = function (event) {
        // Получаем и отображаем сообщение
        var message = event.data;
        displayMessage(message);
    };

    chatSocket.onerror = function (error) {
        console.error("WebSocket error:", error);
    };

    return chatSocket;
}

// Функция для отображения сообщений
function displayMessage(message) {
    var chatBox = document.getElementById('chatBox');
    var messageElement = document.createElement('div');
    messageElement.textContent = message;
    chatBox.appendChild(messageElement);
    chatBox.scrollTop = chatBox.scrollHeight; // Прокрутка вниз
}

// Функция для отправки сообщений
function sendMessage() {
    // Проверка флага, чтобы не отправить несколько сообщений одновременно
    if (isSendingMessage) {
        console.log("Message is already being sent. Please wait.");
        return;
    }

    var messageInput = document.getElementById('messageInput');
    var message = messageInput.value;

    // Проверка, что сообщение не совпадает с предыдущим
    if (message && message !== lastSentMessage && chatSocket && chatSocket.readyState === WebSocket.OPEN) {
        isSendingMessage = true; // Устанавливаем флаг, что сообщение отправляется

        chatSocket.send(message); // Отправляем сообщение на сервер
        messageInput.value = ""; // Очищаем поле ввода
        lastSentMessage = message; // Сохраняем текущее сообщение как последнее

        // Ожидание 1 секунду перед сбросом флага
        clearTimeout(sendMessageTimeout); // Очистить предыдущий таймер, если есть
        sendMessageTimeout = setTimeout(function () {
            isSendingMessage = false;
        }, 1000); // Даем время 1 секунду, чтобы пользователь не мог отправлять сообщения слишком быстро
    } else if (message === lastSentMessage) {
        console.log("Message is the same as the previous one, not sending.");
    } else {
        console.warn("WebSocket is not open or not initialized; unable to send message.");
    }
}

// Подключение WebSocket для чата сразу после загрузки страницы
window.onload = function() {
    // Подключаем WebSocket один раз при загрузке страницы
    connectChatWebSocket();
};

// Обработчик отправки сообщения
document.getElementById('sendMessageButton').addEventListener('click', sendMessage);

// Функция для переключения вкладок
function switchTab(tabName) {
    var tabContents = document.querySelectorAll('.tab-content');
    tabContents.forEach(content => content.classList.remove('active-content'));

    var tabs = document.querySelectorAll('.tab');
    tabs.forEach(tab => tab.classList.remove('active-tab'));

    var targetTab = document.getElementById(tabName);
    if (targetTab) targetTab.classList.add('active-content');

    var activeTab = Array.from(tabs).find(tab => tab.textContent.toLowerCase() === tabName.toLowerCase());
    if (activeTab) activeTab.classList.add('active-tab');
}

var sockets = {};  // Для хранения WebSocket-соединений

// Функция для подключения к WebSocket для экранной трансляции
function connectWebSocket() {
    var keyElement = document.getElementById('accessKey');
    var key = keyElement.value || keyElement.textContent; // Проверяем источник ключа
    if (!key) {
        alert("Access key is required!");
        return null;
    }

    if (sockets[key]) {
        console.warn("WebSocket already exists for key:", key);
        return sockets[key];
    }

    try {
        var socket = new WebSocket("ws://localhost:8080/events/screen-share/" + key);

        socket.onopen = function () {
            console.log("Connected to WebSocket server for key:", key);
        };

        socket.onmessage = function (event) {
            try {
                const frameData = JSON.parse(event.data);
                if (frameData.timestamp && frameData.data) {
                    const videoBlob = new Blob([frameData.data], { type: 'video/webm' });
                    const videoUrl = URL.createObjectURL(videoBlob);
                    const videoElement = document.getElementById("videoElement");

                    // Синхронизация: если видеоElement уже установлен, то заменяем источник
                    if (videoElement) {
                        videoElement.src = videoUrl;
                    }
                }
            } catch (e) {
                console.error("Error parsing video frame:", e);
            }
        };

        socket.onclose = function () {
            console.log("WebSocket connection closed for key:", key);
            delete sockets[key];
        };

        socket.onerror = function (error) {
            console.error("WebSocket error for key:", key, error);
        };

        sockets[key] = socket;
        return socket;
    } catch (error) {
        console.error("Failed to connect WebSocket:", error);
        return null;
    }
}

// Функция для начала трансляции
async function startScreenShare() {
    try {
        var keyElement = document.getElementById('accessKey');
        var key = keyElement.value || keyElement.textContent; // Проверяем источник ключа
        if (!key) {
            alert("Please enter an access key before starting the stream.");
            return;
        }

        // Проверка, является ли пользователь хостом
        if (!isHost) {
            alert("Only the host can start the screen share.");
            return;
        }

        const socket = connectWebSocket();
        if (!socket) return;

        const stream = await navigator.mediaDevices.getDisplayMedia({ video: true });
        const videoElement = document.getElementById("videoElement");
        if (videoElement) videoElement.srcObject = stream;

        const mediaRecorder = new MediaRecorder(stream);
        mediaRecorder.ondataavailable = function (event) {
            if (event.data.size > 0 && socket.readyState === WebSocket.OPEN) {
                const timestamp = new Date().getTime(); // Добавляем метку времени для синхронизации
                const frameData = {
                    timestamp: timestamp,  // Важно для синхронизации
                    data: event.data
                };
                socket.send(JSON.stringify(frameData));  // Отправка кадра
            } else {
                console.warn("WebSocket is not open; unable to send data.");
            }
        };

        mediaRecorder.start(100); // Отправляем данные каждые 100 миллисекунд

        // Обработка окончания потока
        stream.getTracks().forEach(track => {
            track.onended = function () {
                console.log("Screen share stopped.");
                mediaRecorder.stop();
                videoElement.srcObject = null;
            };
        });

    } catch (err) {
        console.error("Error accessing screen:", err);
    }
}

// Добавление обработчиков событий для кнопок
document.getElementById('startShareButton').addEventListener('click', startScreenShare);