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
