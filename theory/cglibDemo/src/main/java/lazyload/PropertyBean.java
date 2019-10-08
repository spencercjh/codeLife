package lazyload;

import java.util.Objects;

/**
 * @author SpencerCJH
 * @date 2019/7/23 15:19
 */
public class PropertyBean {
    private String key;
    private Object value;

    public String getKey() {
        return key;
    }

    public PropertyBean setKey(String key) {
        this.key = key;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public PropertyBean setValue(Object value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "PropertyBean{" +
                "key='" + key + '\'' +
                ", value=" + value +
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
        PropertyBean that = (PropertyBean) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
