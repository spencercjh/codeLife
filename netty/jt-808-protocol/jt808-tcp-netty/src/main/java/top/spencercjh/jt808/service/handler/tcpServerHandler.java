package top.spencercjh.jt808.service.handler;

import top.spencercjh.jt808.common.TPMSConsts;
import top.spencercjh.jt808.server.SessionManager;
import top.spencercjh.jt808.service.TerminalMsgProcessService;
import top.spencercjh.jt808.service.codec.MessageDecoder;
import top.spencercjh.jt808.util.JT808ProtocolUtils;
import top.spencercjh.jt808.vo.PackageData;
import top.spencercjh.jt808.vo.Session;
import top.spencercjh.jt808.vo.request.LocationInfoUploadMsg;
import top.spencercjh.jt808.vo.request.TerminalAuthenticationMsg;
import top.spencercjh.jt808.vo.request.TerminalRegisterMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class tcpServerHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final SessionManager sessionManager;
    private final MessageDecoder decoder;
    private TerminalMsgProcessService msgProcessService;
    private JT808ProtocolUtils protocolUtils = new JT808ProtocolUtils();

    public tcpServerHandler() {
        this.sessionManager = SessionManager.getInstance();
        this.decoder = new MessageDecoder();
        this.msgProcessService = new TerminalMsgProcessService();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            ByteBuf byteBuf = (ByteBuf) msg;
            if (byteBuf.readableBytes() <= 0) {
                return;
            }
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            bytes = this.protocolUtils.doEscapeForReceive(bytes, 0, bytes.length);
            // 字节数据转换为针对于808消息结构的实体类
            PackageData packageData = this.decoder.bytes2PackageData(bytes);
            // 引用channel,以便回送数据给硬件
            packageData.setChannel(ctx.channel());
            this.processPackageData(packageData);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            release(msg);
        }
    }

    /**
     * 处理业务逻辑
     *
     * @param packageData 消息
     */
    private void processPackageData(PackageData packageData) {
        final PackageData.MsgHeader header = packageData.getMsgHeader();
        // 1. 终端心跳-消息体为空 ==> 平台通用应答
        if (TPMSConsts.MSG_ID_TERMINAL_HEART_BEAT == header.getMsgId()) {
            logger.info(">>>>>[终端心跳],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
            try {
                this.msgProcessService.processTerminalHeartBeatMsg(packageData);
                logger.info("<<<<<[终端心跳],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
            } catch (Exception e) {
                logger.error("<<<<<[终端心跳]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
                        e.getMessage());
                e.printStackTrace();
            }
        }
        // 5. 终端鉴权 ==> 平台通用应答
        else if (TPMSConsts.MSG_ID_TERMINAL_AUTHENTICATION == header.getMsgId()) {
            logger.info(">>>>>[终端鉴权],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
            try {
                TerminalAuthenticationMsg authenticationMsg = new TerminalAuthenticationMsg(packageData);
                this.msgProcessService.processAuthMsg(authenticationMsg);
                logger.info("<<<<<[终端鉴权],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
            } catch (Exception e) {
                logger.error("<<<<<[终端鉴权]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
                        e.getMessage());
                e.printStackTrace();
            }
        }
        // 6. 终端注册 ==> 终端注册应答
        else if (TPMSConsts.MSG_ID_TERMINAL_REGISTER == header.getMsgId()) {
            logger.info(">>>>>[终端注册],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
            try {
                TerminalRegisterMsg msg = this.decoder.toTerminalRegisterMsg(packageData);
                this.msgProcessService.processRegisterMsg(msg);
                logger.info("<<<<<[终端注册],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
            } catch (Exception e) {
                logger.error("<<<<<[终端注册]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
                        e.getMessage());
                e.printStackTrace();
            }
        }
        // 7. 终端注销(终端注销数据消息体为空) ==> 平台通用应答
        else if (TPMSConsts.MSG_ID_TERMINAL_LOG_OUT == header.getMsgId()) {
            logger.info(">>>>>[终端注销],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
            try {
                this.msgProcessService.processTerminalLogoutMsg(packageData);
                logger.info("<<<<<[终端注销],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
            } catch (Exception e) {
                logger.error("<<<<<[终端注销]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
                        e.getMessage());
                e.printStackTrace();
            }
        }
        // 3. 位置信息汇报 ==> 平台通用应答
        else if (TPMSConsts.MSG_ID_TERMINAL_LOCATION_INFO_UPLOAD == header.getMsgId()) {
            logger.info(">>>>>[位置信息],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
            try {
                LocationInfoUploadMsg locationInfoUploadMsg = this.decoder.toLocationInfoUploadMsg(packageData);
                logger.info(locationInfoUploadMsg.toString());
                this.msgProcessService.processLocationInfoUploadMsg(locationInfoUploadMsg);
                logger.info("<<<<<[位置信息],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
            } catch (Exception e) {
                logger.error("<<<<<[位置信息]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
                        e.getMessage());
                e.printStackTrace();
            }
        }
        // 其他情况
        else {
            logger.error(">>>>>>[未知消息类型],phone={},msgId={},package={}", header.getTerminalPhone(), header.getMsgId(),
                    packageData);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("发生异常:{}", cause.getMessage());
        cause.printStackTrace();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Session session = Session.buildSession(ctx.channel());
        sessionManager.put(session.getId(), session);
        logger.debug("终端连接:{}", session);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        final String sessionId = ctx.channel().id().asLongText();
        Session session = sessionManager.findBySessionId(sessionId);
        this.sessionManager.removeBySessionId(sessionId);
        logger.debug("终端断开连接:{}", session);
        ctx.channel().close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                Session session = this.sessionManager.removeBySessionId(Session.buildId(ctx.channel()));
                logger.error("服务器主动断开连接:{}", session);
                ctx.close();
            }
        }
    }

    private void release(Object msg) {
        try {
            ReferenceCountUtil.release(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}