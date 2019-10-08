package leetcode;

import java.util.*;

/**
 * @author : SpencerCJH
 * @date : 2019/4/12 1:56
 */
public class SubsetsTwo_90 {
    class Solution2 {
        /**
         * @param nums
         * @return
         * @ 宝宝可乖了
         * 这种数据量不大的情况下，时间多少纯粹碰运气，所以看时间意义不大。这道题相比上一道题多了去重的步骤，所以在看懂上一道题的情况下，这道题就是如何理解去重了。上一道题的解题介绍（78. Subsets）,下面讲解思路。
         * <p>
         * 以下思路基于元素列已经被排序。
         * <p>
         * 为什么会出现重复子列？
         * <p>
         * 该解法生成子列是在之前已经生成的所有子列上依次加上新的元素，生成新的子列。
         * 如果元素列(nums)中存在重复元素，则当前元素生成新子列的过程会与前面重复元素生成新子列的过程部分重复，则生成的子列也是部分重复的。
         * 重复子列出现在哪些部分？ 假设元素列(nums)为：[1, 2, 2]，下面进行模拟
         * <p>
         * 遍历完第一个元素--->[[], [1]]
         * 遍历完第二个元素--->[[], [1], [2], [1, 2]]
         * 遍历完第三个元素--->[[], [1], [2], [1, 2], **[2]**, **[1, 2]**, [2, 2], [1, 2, 2]]。
         * 加粗部分就是第三个元素遍历以后，产生的重复部分。[ **[2]**, **[1, 2]**]是因为第三个元素2，
         * 在与[[], [1]]生成新序列时生成的，可以看到这个过程与第二个元素2生成新序列的过程时一样的。
         * 即重复序列就是第三个元素在与[[], [1]]生成新序列时生成的，同时注意到[[], [1]]之后的序列就是第二元素遍历时生成的新序列，
         * 而第三个序列与这些新序列不会生成重复序列，同时注意每次生成的新序列的长度是可以被记录的。
         * <p>
         * 如何避免生成重复序列 在了解重复序列出现的原因和位置以后，就可以去重操作了。
         * <p>
         * 先进行排序，保证重复元素挨在一起
         * 记录每次遍历生成的新序列的长度，这里用start表示每次遍历的开始位置，answers.size()结束位置，previousEnd表示之前一次遍历时Answers的长度
         * 根据与前面元素是否重复，来决定start的取值，也就是开始遍历的位置
         * 上述三个步骤就能有效避免当前元素与之前相同元素遍历过程发生重叠。
         */
        public List<List<Integer>> subsetsWithDup(int[] nums) {
            Arrays.sort(nums);
            List<List<Integer>> answers = new ArrayList<>();
            answers.add(new ArrayList<>());
            int start, previousEnd = 0;
            for (int i = 0; i < nums.length; i++) {
                if (i != 0 && (nums[i] == nums[i - 1])) {
                    start = previousEnd;
                } else {
                    start = 0;
                }
                previousEnd = answers.size();
                for (int j = start; j < answers.size(); j++) {
                    List<Integer> newAnswer = new ArrayList<>(answers.get(j));
                    newAnswer.add(nums[i]);
                    answers.add(newAnswer);
                }
            }
            return answers;
        }
    }

    class Solution1 {
        public List<List<Integer>> subsetsWithDup(int[] nums) {
            Set<List<Integer>> answers = new HashSet<>((int) Math.pow(2, nums.length));
            answers.add(new ArrayList<>());
            for (int n : nums) {
                Set<List<Integer>> tempLists = new HashSet<>(answers.size());
                for (List<Integer> list : answers) {
                    List<Integer> newAnswer = new ArrayList<>(list);
                    newAnswer.add(n);
                    Collections.sort(newAnswer);
                    tempLists.add(newAnswer);
                }
                answers.addAll(tempLists);
            }
            return new ArrayList<>(answers);
        }
    }
}