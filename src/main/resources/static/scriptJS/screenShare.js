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

// Функция для выхода
function exitStream() {
    alert('You have exited the stream.');
    // Закрытие WebSocket соединения
    if (socket) {
        socket.close();
    }
}

// Список для хранения WebSocket соединений для разных мероприятий
var sockets = {};

// Функция для подключения к трансляции по accessKey
function connectToStream(accessKey) {
    // Закрыть старое соединение, если оно существует
    if (sockets[accessKey]) {
        alert("You are already connected to this stream.");
        return;
    }

    // Подключение к WebSocket серверу с использованием accessKey
    var socket = new WebSocket("ws://localhost:8080/events/screen-share/" + accessKey);

    socket.onopen = function(event) {
        console.log("Connected to WebSocket server for accessKey: " + accessKey);
    };

    socket.onmessage = function(event) {
        console.log("Message from server: " + event.data);

        // Преобразуем данные в JSON
        var messageData = JSON.parse(event.data);

        // Проверка на наличие ключа в событии
        if (messageData.streamKey) {
            // Устанавливаем ключ в input поле
            document.getElementById('streamKey').value = messageData.streamKey;
        }

        // Обработка сообщений чата
        if (messageData.type === 'chat') {
            var chatBox = document.getElementById("chatBox");
            var message = document.createElement("div");
            message.textContent = messageData.content;
            chatBox.appendChild(message);
            chatBox.scrollTop = chatBox.scrollHeight;
        }
    };

    socket.onclose = function(event) {
        console.log("WebSocket connection closed for accessKey: " + accessKey);
        delete sockets[accessKey];  // Удаляем сокет после закрытия соединения
    };

    // Сохраняем соединение в объекте
    sockets[accessKey] = socket;
}

// Функция для захвата экрана
async function startScreenShare() {
    const accessKey = document.getElementById("accessKey").value;

    // Проверка наличия ключа доступа
    if (!accessKey) {
        alert("Please enter an Access Key to start streaming.");
        return;
    }

    // Проверить, является ли пользователь хостом
    const isHost = document.getElementById("isHost").value === "true"; // Это значение передаётся сервером

    if (!isHost) {
        alert("You are not allowed to start screen sharing.");
        return;
    }

    try {
        const stream = await navigator.mediaDevices.getDisplayMedia({ video: true });
        const videoElement = document.getElementById("videoElement");
        videoElement.srcObject = stream;

        // Отправка потока экрана через WebSocket
        const mediaRecorder = new MediaRecorder(stream);
        mediaRecorder.ondataavailable = function(event) {
            if (event.data.size > 0) {
                // Отправка данных видеопотока через WebSocket
                if (sockets[accessKey]) {
                    sockets[accessKey].send(event.data);
                }
            }
        };
        mediaRecorder.start(100); // Отправлять данные каждые 100ms

        alert("Screen sharing started for accessKey: " + accessKey);
    } catch (err) {
        console.error("Error accessing screen: " + err);
    }
}

// Функция для отправки сообщений в чат
function sendMessage() {
    const messageInput = document.getElementById("messageInput");
    const message = messageInput.value.trim();
    const accessKey = document.getElementById("accessKey").value;

    if (message !== "") {
        const messageData = JSON.stringify({ type: "chat", content: message });

        // Отправка сообщения через WebSocket для соответствующего accessKey
        if (sockets[accessKey]) {
            sockets[accessKey].send(messageData);
        }

        messageInput.value = "";
    }
}

// Привязка кнопки отправки сообщений
document.getElementById("sendMessageButton").addEventListener("click", sendMessage);
