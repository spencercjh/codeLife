package interviewnote.aes;

import java.util.Objects;

/**
 * @author SpencerCJH
 * @date 2019/11/30 0:14
 */
class Parameter<T extends Comparable> {
    private String name;
    private T value;

    Parameter(String name, T value) {
        this.name = name;
        this.value = value;
    }

    String getName() {
        return name;
    }

    T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Parameter parameter = (Parameter) o;
        return Objects.equals(name, parameter.name) &&
                Objects.equals(value, parameter.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return name + " = " + value;
    }
}
