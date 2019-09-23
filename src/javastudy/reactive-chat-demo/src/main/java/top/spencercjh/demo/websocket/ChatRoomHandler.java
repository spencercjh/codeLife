package top.spencercjh.demo.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Spencer
 */
@Slf4j
public class ChatRoomHandler implements WebSocketHandler {
    private List<String> users = new CopyOnWriteArrayList<>();

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        users.add(webSocketSession.getId());
        log.debug(webSocketSession.toString());
        return webSocketSession
                .send(webSocketSession.receive()
                        .map(msg -> {
                            String result = "RECEIVED ON SERVER :: " + msg.getPayloadAsText();
                            log.info(result);
                            return result;
                        })
                        .map(webSocketSession::textMessage)
                );
    }
}
