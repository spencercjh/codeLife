package interviewnote.aes;

import java.util.List;
import java.util.Objects;

/**
 * @author SpencerCJH
 * @date 2019/11/30 0:13
 */
class AES {
    private List<Parameter> parameters;

    AES(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AES aes = (AES) o;
        return Objects.equals(parameters, aes.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters);
    }

    @Override
    public String toString() {
        return "AES{" +
                "parameters=" + parametersToString() +
                '}';
    }

    private String parametersToString() {
        String separator = ", ";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < parameters.size(); i++) {
            if (i > 0) {
                stringBuilder.append(separator);
            }
            stringBuilder.append(parameters.get(i).toString());
        }
        return stringBuilder.toString();
    }
}
