import java.util.LinkedList;
import java.util.List;

/**
 * @author : SpencerCJH
 * @date : 2019/4/11 17:24
 */
public class Combinations_77 {
    class Solution {
        public List<List<Integer>> combine(int n, int k) {
            List<List<Integer>> lists = new LinkedList<>();
            getCombination(lists, new LinkedList<>(), n, k);
            return lists;
        }

        private void getCombination(List<List<Integer>> lists, List<Integer> list, int n, int k) {
            if (k == 0) {
                lists.add(list);
                return;
            }
            if (n < k) {
                return;
            }
            for (int i = n; i >= 1; i--) {
                List<Integer> temp = new LinkedList<>(list);
                temp.add(i);
                getCombination(lists, temp, i - 1, k - 1);
            }
        }
    }
}
