package cn.hylexus.jt808.service;

import cn.hylexus.jt808.server.SessionManager;
import cn.hylexus.jt808.service.codec.MessageEncoder;
import cn.hylexus.jt808.vo.PackageData;
import cn.hylexus.jt808.vo.PackageData.MsgHeader;
import cn.hylexus.jt808.vo.Session;
import cn.hylexus.jt808.vo.request.LocationInfoUploadMsg;
import cn.hylexus.jt808.vo.request.TerminalAuthenticationMsg;
import cn.hylexus.jt808.vo.request.TerminalRegisterMsg;
import cn.hylexus.jt808.vo.response.ServerCommonRespMsgBody;
import cn.hylexus.jt808.vo.response.TerminalRegisterMsgRespBody;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TerminalMsgProcessService extends BaseMsgProcessService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private MessageEncoder messageEncoder;
    private SessionManager sessionManager;

    public TerminalMsgProcessService() {
        this.messageEncoder = new MessageEncoder();
        this.sessionManager = SessionManager.getInstance();
    }

    public void processRegisterMsg(TerminalRegisterMsg message) throws Exception {
        log.debug("终端注册:{}", JSON.toJSONString(message, true));
        settingSession(message);
        TerminalRegisterMsgRespBody respMsgBody = new TerminalRegisterMsgRespBody();
        respMsgBody.setReplyCode(TerminalRegisterMsgRespBody.success);
        respMsgBody.setReplyFlowId(message.getMsgHeader().getFlowId());
        // TODO 鉴权码暂时写死
        respMsgBody.setReplyToken("123");
        int flowId = super.getFlowId(message.getChannel());
        byte[] bs = this.messageEncoder.encode4TerminalRegisterResp(message, respMsgBody, flowId);

        super.sendToClient(message.getChannel(), bs);
    }

    public void processAuthMsg(TerminalAuthenticationMsg message) throws Exception {
        // TODO 暂时每次鉴权都成功
        log.debug("终端鉴权:{}", JSON.toJSONString(message, true));
        settingSession(message);
        ServerCommonRespMsgBody respMsgBody = new ServerCommonRespMsgBody();
        respMsgBody.setReplyCode(ServerCommonRespMsgBody.success);
        respMsgBody.setReplyFlowId(message.getMsgHeader().getFlowId());
        respMsgBody.setReplyId(message.getMsgHeader().getMsgId());
        int flowId = super.getFlowId(message.getChannel());
        byte[] bs = this.messageEncoder.encodeForServerCommonResponseMessage(message, respMsgBody, flowId);
        super.sendToClient(message.getChannel(), bs);
    }

    private void settingSession(PackageData message) {
        final String sessionId = Session.buildId(message.getChannel());
        Session session = sessionManager.findBySessionId(sessionId);
        if (session == null) {
            session = Session.buildSession(message.getChannel(), message.getMsgHeader().getTerminalPhone());
        }
        session.setAuthenticated(true);
        session.setTerminalPhone(message.getMsgHeader().getTerminalPhone());
        sessionManager.put(session.getId(), session);
    }

    public void processTerminalHeartBeatMsg(PackageData request) throws Exception {
        log.debug("心跳信息:{}", JSON.toJSONString(request, true));
        terminalUniversalResponse(request);
    }

    public void processTerminalLogoutMsg(PackageData request) throws Exception {
        log.info("终端注销:{}", JSON.toJSONString(request, true));
        terminalUniversalResponse(request);
    }

    public void processLocationInfoUploadMsg(LocationInfoUploadMsg request) throws Exception {
        log.debug("位置 信息:{}", JSON.toJSONString(request, true));
        terminalUniversalResponse(request);
    }

    private void terminalUniversalResponse(PackageData request) throws Exception {
        final MsgHeader reqHeader = request.getMsgHeader();
        ServerCommonRespMsgBody responseMessageBody = new ServerCommonRespMsgBody(reqHeader.getFlowId(), reqHeader.getMsgId(),
                ServerCommonRespMsgBody.success);
        int flowId = super.getFlowId(request.getChannel());
        byte[] bytes = this.messageEncoder.encodeForServerCommonResponseMessage(request, responseMessageBody, flowId);
        super.sendToClient(request.getChannel(), bytes);
    }
}
