package top.spencercjh.exercise;

import java.util.function.Function;

/**
 * @author SpencerCJH
 * @date 2019/11/25 21:55
 */
public class HigherOrderFunctionInJava {
    public static void main(String[] args) {
        Function<Integer, Function<Integer, Integer>> canReturnNull = x -> y -> null;


    }
}
