package exam.haluo2019;

/**
 * @author SpencerCJH
 * @date 2019/9/13 10:15
 */
public class Test2 {
    public int getMin(int[] array, int n) {
        int minValue = array[0];
        int answer = 0;
        for (int i = 1; i < n; ++i) {
            if (answer < array[i] - minValue) {
                answer = array[i] - minValue;
            }
            if (minValue > array[i]) {
                minValue = array[i];
            }
        }
        return answer;
    }
}
