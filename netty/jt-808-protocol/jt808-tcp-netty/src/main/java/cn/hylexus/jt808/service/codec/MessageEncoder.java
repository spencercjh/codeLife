package cn.hylexus.jt808.service.codec;

import cn.hylexus.jt808.common.TPMSConsts;
import cn.hylexus.jt808.util.BitOperator;
import cn.hylexus.jt808.util.JT808ProtocolUtils;
import cn.hylexus.jt808.vo.PackageData;
import cn.hylexus.jt808.vo.Session;
import cn.hylexus.jt808.vo.request.TerminalRegisterMsg;
import cn.hylexus.jt808.vo.response.ServerCommonRespMsgBody;
import cn.hylexus.jt808.vo.response.TerminalRegisterMsgRespBody;

import java.util.Arrays;

public class MessageEncoder {
    private BitOperator bitOperator;
    private JT808ProtocolUtils jt808ProtocolUtils;

    public MessageEncoder() {
        this.bitOperator = new BitOperator();
        this.jt808ProtocolUtils = new JT808ProtocolUtils();
    }

    public byte[] encode4TerminalRegisterResp(TerminalRegisterMsg req, TerminalRegisterMsgRespBody respMsgBody,
                                              int flowId) throws Exception {
        // 消息体字节数组
        byte[] msgBody;
        // 鉴权码(STRING) 只有在成功后才有该字段
        if (respMsgBody.getReplyCode() == TerminalRegisterMsgRespBody.success) {
            msgBody = this.bitOperator.concatAll(Arrays.asList(
                    // 流水号(2)
                    bitOperator.integerToTwoBytes(respMsgBody.getReplyFlowId()),
                    // 结果
                    new byte[]{respMsgBody.getReplyCode()},
                    // 鉴权码(STRING)
                    respMsgBody.getReplyToken().getBytes(TPMSConsts.STRING_CHARSET)
            ));
        } else {
            msgBody = this.bitOperator.concatAll(Arrays.asList(
                    // 流水号(2)
                    bitOperator.integerToTwoBytes(respMsgBody.getReplyFlowId()),
                    // 错误代码
                    new byte[]{respMsgBody.getReplyCode()}
            ));
        }
        // 消息头
        int msgBodyProps = this.jt808ProtocolUtils.generateMessageBodyProps(msgBody.length, 0b000, false, 0);
        byte[] msgHeader = this.jt808ProtocolUtils.generateMsgHeader(req.getMsgHeader().getTerminalPhone(),
                TPMSConsts.CMD_TERMINAL_REGISTER_RESPONSE, msgBody, msgBodyProps, flowId);
        byte[] headerAndBody = this.bitOperator.concatAll(msgHeader, msgBody);
        // 校验码
        int checkSum = this.bitOperator.getCheckSum4JT808(headerAndBody, 0, headerAndBody.length);
        // 连接并且转义
        return this.doEncode(headerAndBody, checkSum);
    }

    public byte[] encodeForServerCommonResponseMessage(PackageData req, ServerCommonRespMsgBody respMsgBody, int flowId)
            throws Exception {
        byte[] msgBody = this.bitOperator.concatAll(Arrays.asList(
                // 应答流水号
                bitOperator.integerToTwoBytes(respMsgBody.getReplyFlowId()),
                // 应答ID,对应的终端消息的ID
                bitOperator.integerToTwoBytes(respMsgBody.getReplyId()),
                // 结果
                new byte[]{respMsgBody.getReplyCode()}
        ));
        // 消息头
        int msgBodyProps = this.jt808ProtocolUtils.generateMessageBodyProps(msgBody.length, 0b000, false, 0);
        byte[] msgHeader = this.jt808ProtocolUtils.generateMsgHeader(req.getMsgHeader().getTerminalPhone(),
                TPMSConsts.CMD_COMMON_RESPONSE, msgBody, msgBodyProps, flowId);
        byte[] headerAndBody = this.bitOperator.concatAll(msgHeader, msgBody);
        // 校验码
        int checkSum = this.bitOperator.getCheckSum4JT808(headerAndBody, 0, headerAndBody.length);
        // 连接并且转义
        return this.doEncode(headerAndBody, checkSum);
    }

    public byte[] encode4ParamSetting(byte[] msgBodyBytes, Session session) throws Exception {
        // 消息头
        int msgBodyProps = this.jt808ProtocolUtils.generateMessageBodyProps(msgBodyBytes.length, 0b000, false, 0);
        byte[] msgHeader = this.jt808ProtocolUtils.generateMsgHeader(session.getTerminalPhone(),
                TPMSConsts.CMD_TERMINAL_PARAM_SETTINGS, msgBodyBytes, msgBodyProps, session.currentFlowId());
        // 连接消息头和消息体
        byte[] headerAndBody = this.bitOperator.concatAll(msgHeader, msgBodyBytes);
        // 校验码
        int checkSum = this.bitOperator.getCheckSum4JT808(headerAndBody, 0, headerAndBody.length);
        // 连接并且转义
        return this.doEncode(headerAndBody, checkSum);
    }

    private byte[] doEncode(byte[] headerAndBody, int checkSum) throws Exception {
        byte[] noEscapedBytes = this.bitOperator.concatAll(Arrays.asList(
                // 0x7e
                new byte[]{TPMSConsts.PKG_DELIMITER},
                // 消息头+ 消息体
                headerAndBody,
                // 校验码
                bitOperator.integerToOneBytes(checkSum),
                // 0x7e
                new byte[]{TPMSConsts.PKG_DELIMITER}
        ));
        // 转义
        return jt808ProtocolUtils.doEscapeForSend(noEscapedBytes, 1, noEscapedBytes.length - 2);
    }
}
