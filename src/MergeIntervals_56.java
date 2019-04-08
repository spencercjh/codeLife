import java.util.ArrayList;
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
            if (intervals.size() == 0) {
                return Collections.emptyList();
            }
            List<Interval> res = new ArrayList<>();
            intervals.sort(Comparator.comparingInt(o -> o.start));
            int start = intervals.get(0).start;
            int end = intervals.get(0).end;
            for (Interval interval : intervals) {
                if (interval.start <= end) {
                    end = Math.max(interval.end, end);
                } else {
                    res.add(new Interval(start, end));
                    start = interval.start;
                    end = interval.end;
                }
            }
            res.add(new Interval(start, end));
            return res;
        }
    }
}
