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

var sockets = {};

// Функция для подключения к WebSocket
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