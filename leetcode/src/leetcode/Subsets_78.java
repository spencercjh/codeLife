package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : SpencerCJH
 * @date : 2019/4/11 20:14
 */
public class Subsets_78 {
    class Solution {
        public List<List<Integer>> subsets(int[] nums) {
            List<List<Integer>> answers = new ArrayList<>((int) Math.pow(2, nums.length));
            answers.add(new ArrayList<>());
            for (int n : nums) {
                List<List<Integer>> tempLists = new ArrayList<>(answers.size());
                for (List<Integer> list : answers) {
                    List<Integer> newAnswer = new ArrayList<>(list);
                    newAnswer.add(n);
                    tempLists.add(newAnswer);
                }
                answers.addAll(tempLists);
            }
            return answers;
        }
    }
}
