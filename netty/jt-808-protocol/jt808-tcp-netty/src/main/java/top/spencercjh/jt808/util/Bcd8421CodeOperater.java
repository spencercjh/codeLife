package top.spencercjh.jt808.util;

public class Bcd8421CodeOperater {

    /**
     * BCD字节数组===>String
     *
     * @param bytes byte array
     * @return 十进制字符串
     */
    public String bcdCodeToString(byte[] bytes) {
        StringBuilder temp = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            // 高四位
            temp.append((aByte & 0xf0) >>> 4);
            // 低四位
            temp.append(aByte & 0x0f);
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();
    }

    /**
     * 字符串==>BCD字节数组
     *
     * @param str string
     * @return BCD字节数组
     */
    byte[] stringToBcdCode(String str) {
        // 奇数,前补零
        if ((str.length() & 0x1) == 1) {
            str = "0" + str;
        }
        byte[] ret = new byte[str.length() / 2];
        byte[] bytes = str.getBytes();
        for (int i = 0; i < ret.length; i++) {

            byte high = ascIICodeToBcdCode(bytes[2 * i]);
            byte low = ascIICodeToBcdCode(bytes[2 * i + 1]);

            // TODO 只遮罩BCD低四位?
            ret[i] = (byte) ((high << 4) | low);
        }
        return ret;
    }

    private byte ascIICodeToBcdCode(byte asc) {
        if ((asc >= '0') && (asc <= '9')) {
            return (byte) (asc - '0');
        } else if ((asc >= 'A') && (asc <= 'F')) {
            return (byte) (asc - 'A' + 10);
        } else if ((asc >= 'a') && (asc <= 'f')) {
            return (byte) (asc - 'a' + 10);
        } else {
            return (byte) (asc - 48);
        }
    }
}
