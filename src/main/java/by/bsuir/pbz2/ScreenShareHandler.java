package by.bsuir.pbz2;

import by.bsuir.pbz2.data.entity.Event;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

public class ScreenShareHandler extends TextWebSocketHandler {
    private final ConcurrentHashMap<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long userId = (Long) session.getAttributes().get("userId"); // Получение идентификатора пользователя
        if (userId != null) {
            sessions.put(userId, session); // Добавление сессии в список
            System.out.println("User " + userId + " connected.");
        } else {
            session.close(); // Закрытие сессии, если пользователь не аутентифицирован
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Long userId = (Long) session.getAttributes().get("userId");
        Event event = (Event) session.getAttributes().get("currentEvent");

        if (event != null && userId != null) {
            if (event.getHost().getId().equals(userId)) {
                // Отправка данных всем подключённым сессиям
                for (WebSocketSession s : sessions.values()) {
                    if (s.isOpen()) {
                        s.sendMessage(message);
                    }
                }
            } else {
                session.sendMessage(new TextMessage("Access denied: Only the host can stream."));
            }
        } else {
            session.sendMessage(new TextMessage("Error: Invalid session or event."));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long userId = (Long) session.getAttributes().get("userId");
        if (userId != null) {
            sessions.remove(userId); // Удаление сессии из списка
            System.out.println("User " + userId + " disconnected.");
        }
    }
}