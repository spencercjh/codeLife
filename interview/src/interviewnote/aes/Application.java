package interviewnote.aes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SpencerCJH
 * @date 2019/11/30 0:18
 */
public class Application {
    public static void main(String[] args) {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new Parameter<>("v0", -1));
        parameters.add(new Parameter<>("v1", 1));
        parameters.add(new Parameter<>("v2", 1.5F));
        parameters.add(new Parameter<>("v3", "auto"));
        parameters.add(new Parameter<>("v4", "InParallel"));
        parameters.add(new Parameter<>("v5", "SeriesConnection"));

        AES aes = new AES(parameters);
        System.out.println(aes.toString());

    }
}
