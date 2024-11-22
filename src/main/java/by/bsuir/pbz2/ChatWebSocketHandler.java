package by.bsuir.pbz2;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private WebSocketSession currentSession = null;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        currentSession = session; // Сохраняем текущую сессию
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (currentSession == null || !currentSession.isOpen()) {
            // Если сессия закрыта или не существует, переподключаем
            reconnectWebSocket(session);
        }
        // Отправка сообщения, если сессия открыта
        currentSession.sendMessage(new TextMessage(message.getPayload()));
    }

    private void reconnectWebSocket(WebSocketSession session) {
        // Логика переподключения WebSocket
        System.out.println("Reconnecting WebSocket session...");
        // Например, закрытие старой сессии и создание новой
        try {
            if (currentSession != null && currentSession.isOpen()) {
                currentSession.close();
            }
            // Создаем новое соединение здесь
            // currentSession = ...
        } catch (Exception e) {
            System.out.println("Error while reconnecting WebSocket: " + e.getMessage());
        }
    }
}