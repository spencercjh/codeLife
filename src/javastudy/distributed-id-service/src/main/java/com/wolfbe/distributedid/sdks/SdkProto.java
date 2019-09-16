package com.wolfbe.distributedid.sdks;

import java.util.Objects;

/**
 * @author Andy
 */
public class SdkProto {
    /**
     * 请求的ID
     */
    private int rqid;
    /**
     * 全局的ID
     */
    private long did;

    SdkProto(int rqid, long did) {
        this.rqid = rqid;
        this.did = did;
    }

    int getRqid() {
        return rqid;
    }

    public void setRqid(int rqid) {
        this.rqid = rqid;
    }

    long getDid() {
        return did;
    }

    void setDid(long did) {
        this.did = did;
    }

    @Override
    public String toString() {
        return "SdkProto{" +
                "rqid=" + rqid +
                ", did=" + did +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SdkProto sdkProto = (SdkProto) o;
        return rqid == sdkProto.rqid &&
                did == sdkProto.did;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rqid, did);
    }
}
