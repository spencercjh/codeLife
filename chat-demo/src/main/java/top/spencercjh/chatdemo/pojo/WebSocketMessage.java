package top.spencercjh.chatdemo.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author spencercjh
 */
@Data
@EqualsAndHashCode
public class WebSocketMessage {

    /**
     * 消息类型
     */
    private int type;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 房间 ID
     */
    private long roomId;
    /**
     * 消息主体
     */
    private String body;
    /**
     * 错误码
     */
    private int errorCode;

    public WebSocketMessage(int type, String name, int errorCode) {
        this.type = type;
        this.name = name;
        this.errorCode = errorCode;
    }

    public WebSocketMessage(int type, String name) {
        this.type = type;
        this.name = name;
        this.errorCode = 0;
    }

    public WebSocketMessage(int type, String name, String body, int errorCode) {
        this.type = type;
        this.name = name;
        this.body = body;
        this.errorCode = errorCode;
    }

    public WebSocketMessage(int type, String name, String body) {
        this.type = type;
        this.name = name;
        this.body = body;
        this.errorCode = 0;
    }
}
