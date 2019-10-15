package top.spencercjh.jt808.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;

/**
 * JT808协议转义工具类
 *
 * <pre>
 * 0x7d01 <====> 0x7d
 * 0x7d02 <====> 0x7e
 * </pre>
 *
 * @author hylexus
 */
public class JT808ProtocolUtils {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private BitOperator bitOperator;
    private Bcd8421CodeOperater bcd8421Operater;

    public JT808ProtocolUtils() {
        this.bitOperator = new BitOperator();
        this.bcd8421Operater = new Bcd8421CodeOperater();
    }

    /**
     * 接收消息时转义<br>
     *
     * <pre>
     * 0x7d01 <====> 0x7d
     * 0x7d02 <====> 0x7e
     * </pre>
     *
     * @param bytes 要转义的字节数组
     * @param start 起始索引
     * @param end   结束索引
     * @return 转义后的字节数组
     * @throws Exception e
     */
    public byte[] doEscapeForReceive(byte[] bytes, int start, int end) throws Exception {
        if (start < 0 || end > bytes.length) {
            throw new ArrayIndexOutOfBoundsException("doEscapeForReceive error : index out of bounds(start=" + start
                    + ",end=" + end + ",bytes length=" + bytes.length + ")");
        }
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            for (int i = 0; i < start; i++) {
                byteArrayOutputStream.write(bytes[i]);
            }
            for (int i = start; i < end - 1; i++) {
                if (bytes[i] == 0x7d && bytes[i + 1] == 0x01) {
                    byteArrayOutputStream.write(0x7d);
                    i++;
                } else if (bytes[i] == 0x7d && bytes[i + 1] == 0x02) {
                    byteArrayOutputStream.write(0x7e);
                    i++;
                } else {
                    byteArrayOutputStream.write(bytes[i]);
                }
            }
            for (int i = end - 1; i < bytes.length; i++) {
                byteArrayOutputStream.write(bytes[i]);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 发送消息时转义<br>
     *
     * <pre>
     *  0x7e <====> 0x7d02
     * </pre>
     *
     * @param bs    要转义的字节数组
     * @param start 起始索引
     * @param end   结束索引
     * @return 转义后的字节数组
     * @throws Exception e
     */
    public byte[] doEscapeForSend(byte[] bs, int start, int end) throws Exception {
        if (start < 0 || end > bs.length) {
            throw new ArrayIndexOutOfBoundsException("doEscapeForSend error : index out of bounds(start=" + start
                    + ",end=" + end + ",bytes length=" + bs.length + ")");
        }
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            for (int i = 0; i < start; i++) {
                byteArrayOutputStream.write(bs[i]);
            }
            for (int i = start; i < end; i++) {
                if (bs[i] == 0x7e) {
                    byteArrayOutputStream.write(0x7d);
                    byteArrayOutputStream.write(0x02);
                } else {
                    byteArrayOutputStream.write(bs[i]);
                }
            }
            for (int i = end; i < bs.length; i++) {
                byteArrayOutputStream.write(bs[i]);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw e;
        }
    }

    public int generateMessageBodyProps(int messageLength, int encryptionType, boolean isSubPackage, int reservation) {
        // [ 0-9 ] 0000,0011,1111,1111(3FF)(消息体长度)
        // [10-12] 0001,1100,0000,0000(1C00)(加密类型)
        // [ 13 ] 0010,0000,0000,0000(2000)(是否有子包)
        // [14-15] 1100,0000,0000,0000(C000)(保留位)
        if (messageLength >= 1024) {
            log.warn("The max value of messageLength is 1023, but {} .", messageLength);
        }
        int subPkg = isSubPackage ? 1 : 0;
        int ret = (messageLength & 0x3FF) |
                ((encryptionType << 10) & 0x1C00) |
                ((subPkg << 13) & 0x2000) |
                ((reservation << 14) & 0xC000);
        return ret & 0xffff;
    }

    public byte[] generateMsgHeader(String phone, int msgType, byte[] body, int msgBodyProps, int flowId)
            throws Exception {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            // 1. 消息ID word(16)
            byteArrayOutputStream.write(bitOperator.integerToTwoBytes(msgType));
            // 2. 消息体属性 word(16)
            byteArrayOutputStream.write(bitOperator.integerToTwoBytes(msgBodyProps));
            // 3. 终端手机号 bcd[6]
            byteArrayOutputStream.write(bcd8421Operater.stringToBcdCode(phone));
            // 4. 消息流水号 word(16),按发送顺序从 0 开始循环累加
            byteArrayOutputStream.write(bitOperator.integerToTwoBytes(flowId));
            // 消息包封装项 此处不予考虑
            return byteArrayOutputStream.toByteArray();
        }
    }
}
