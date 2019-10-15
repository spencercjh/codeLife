package top.spencercjh.jt808.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Objects;

public class PackageData {

    /**
     * 16byte 消息头
     */
    protected MsgHeader msgHeader;

    /**
     * 消息体字节数组
     */
    @JSONField(serialize = false)
    protected byte[] msgBodyBytes;
    /**
     * 校验码 1byte
     */
    protected int checkSum;
    @JSONField(serialize = false)
    protected Channel channel;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PackageData that = (PackageData) o;
        return checkSum == that.checkSum &&
                Objects.equals(msgHeader, that.msgHeader) &&
                Arrays.equals(msgBodyBytes, that.msgBodyBytes) &&
                Objects.equals(channel, that.channel);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(msgHeader, checkSum, channel);
        result = 31 * result + Arrays.hashCode(msgBodyBytes);
        return result;
    }

    public MsgHeader getMsgHeader() {
        return msgHeader;
    }

    public void setMsgHeader(MsgHeader msgHeader) {
        this.msgHeader = msgHeader;
    }

    public byte[] getMsgBodyBytes() {
        return msgBodyBytes;
    }

    public void setMsgBodyBytes(byte[] msgBodyBytes) {
        this.msgBodyBytes = msgBodyBytes;
    }

    public int getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(int checkSum) {
        this.checkSum = checkSum;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "PackageData [msgHeader=" + msgHeader + ", msgBodyBytes=" + Arrays.toString(msgBodyBytes) + ", checkSum="
                + checkSum + ", address=" + channel + "]";
    }

    public static class MsgHeader {
        /**
         * 消息ID
         */
        int msgId;

        /**
         * 消息体属性
         */
        int msgBodyPropsField;
        /**
         * 消息体属性中的消息体长度
         */
        int msgBodyLength;
        /**
         * 消息体属性中的数据加密方式
         */
        int encryptionType;
        /**
         * 消息体属性中的是否分包,true==>有消息包封装项
         */
        boolean hasSubPackage;
        /**
         * 消息体属性中的保留位[14-15]
         */
        String reservedBit;

        /**
         * 终端手机号
         */
        String terminalPhone;
        /**
         * 流水号
         */
        int flowId;

        /**
         * 消息包封装项
         */
        int packageInfoField;
        /**
         * 消息包封装项中的消息包总数(word(16))
         */
        long totalSubPackage;
        /**
         * 消息包封装项中的包序号(word(16))
         * 这次发送的这个消息包是分包中的第几个消息包, 从 1 开始
         */
        long subPackageSeq;

        public int getMsgId() {
            return msgId;
        }

        public void setMsgId(int msgId) {
            this.msgId = msgId;
        }

        public int getMsgBodyLength() {
            return msgBodyLength;
        }

        public void setMsgBodyLength(int msgBodyLength) {
            this.msgBodyLength = msgBodyLength;
        }

        public int getEncryptionType() {
            return encryptionType;
        }

        public void setEncryptionType(int encryptionType) {
            this.encryptionType = encryptionType;
        }

        public String getTerminalPhone() {
            return terminalPhone;
        }

        public void setTerminalPhone(String terminalPhone) {
            this.terminalPhone = terminalPhone;
        }

        public int getFlowId() {
            return flowId;
        }

        public void setFlowId(int flowId) {
            this.flowId = flowId;
        }

        public boolean isHasSubPackage() {
            return hasSubPackage;
        }

        public void setHasSubPackage(boolean hasSubPackage) {
            this.hasSubPackage = hasSubPackage;
        }

        public String getReservedBit() {
            return reservedBit;
        }

        public void setReservedBit(String reservedBit) {
            this.reservedBit = reservedBit;
        }

        public long getTotalSubPackage() {
            return totalSubPackage;
        }

        public void setTotalSubPackage(long totalPackage) {
            this.totalSubPackage = totalPackage;
        }

        public long getSubPackageSeq() {
            return subPackageSeq;
        }

        public void setSubPackageSeq(long packageSeq) {
            this.subPackageSeq = packageSeq;
        }

        public int getMsgBodyPropsField() {
            return msgBodyPropsField;
        }

        public void setMsgBodyPropsField(int msgBodyPropsField) {
            this.msgBodyPropsField = msgBodyPropsField;
        }

        public int getPackageInfoField() {
            return packageInfoField;
        }

        public void setPackageInfoField(int packageInfoField) {
            this.packageInfoField = packageInfoField;
        }

        @Override
        public String toString() {
            return "MsgHeader [msgId=" + msgId + ", msgBodyPropsField=" + msgBodyPropsField + ", msgBodyLength="
                    + msgBodyLength + ", encryptionType=" + encryptionType + ", hasSubPackage=" + hasSubPackage
                    + ", reservedBit=" + reservedBit + ", terminalPhone=" + terminalPhone + ", flowId=" + flowId
                    + ", packageInfoField=" + packageInfoField + ", totalSubPackage=" + totalSubPackage
                    + ", subPackageSeq=" + subPackageSeq + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MsgHeader msgHeader = (MsgHeader) o;
            return msgId == msgHeader.msgId &&
                    msgBodyPropsField == msgHeader.msgBodyPropsField &&
                    msgBodyLength == msgHeader.msgBodyLength &&
                    encryptionType == msgHeader.encryptionType &&
                    hasSubPackage == msgHeader.hasSubPackage &&
                    flowId == msgHeader.flowId &&
                    packageInfoField == msgHeader.packageInfoField &&
                    totalSubPackage == msgHeader.totalSubPackage &&
                    subPackageSeq == msgHeader.subPackageSeq &&
                    Objects.equals(reservedBit, msgHeader.reservedBit) &&
                    Objects.equals(terminalPhone, msgHeader.terminalPhone);
        }

        @Override
        public int hashCode() {
            return Objects.hash(msgId, msgBodyPropsField, msgBodyLength, encryptionType, hasSubPackage, reservedBit, terminalPhone, flowId, packageInfoField, totalSubPackage, subPackageSeq);
        }
    }

}
