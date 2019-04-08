import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author SpencerCJH
 * @date 2019/4/8 16:00
 */
public class MergeIntervals_56 {
    /**
     * Definition for an interval.
     * public class Interval {
     * int start;
     * int end;
     * Interval() { start = 0; end = 0; }
     * Interval(int s, int e) { start = s; end = e; }
     * }
     */
    public class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

    class Solution {
        public List<Interval> merge(List<Interval> intervals) {
            if (null == intervals || intervals.size() == 0) {
                return Collections.emptyList();
            }
            intervals.sort(Comparator.comparingInt(o -> o.start));
            Interval pre = intervals.get(0), current;
            for (int i = 1; i < intervals.size(); ) {
                current = intervals.get(i);
                if (current.start > pre.end) {
                    pre = current;
                    i++;
                } else {
                    pre.end = Math.max(current.end, pre.end);
                    intervals.remove(i);
                }
            }
            return intervals;
        }
    }
}
