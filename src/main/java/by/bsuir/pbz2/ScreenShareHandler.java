package by.bsuir.pbz2;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ScreenShareHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Здесь можно обработать сообщения от клиента, например, транслировать изображение экрана
        System.out.println("Received message: " + message.getPayload());
        // Отправить сообщение всем подключенным пользователям
        session.sendMessage(new TextMessage("Sending screen data..."));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("New WebSocket connection established.");
    }
}
