package leetcode;

import java.util.Stack;

/**
 * @author : SpencerCJH
 * @date : 2019/4/11 15:52
 */
public class SimplifyPath_71 {

    class Solution {
        public String simplifyPath(String path) {
            if (null == path || path.length() == 0) {
                return path;
            }
            String[] strings = path.split("/");
            Stack<String> stack = new Stack<>();
            for (String s : strings) {
                if ("".equals(s) || ".".equals(s)) {
                    continue;
                } else if ("..".equals(s)) {
                    if (!stack.isEmpty()) {
                        stack.pop();
                    }
                } else {
                    stack.push(s);
                }
            }
            if (stack.isEmpty()) {
                return "/";
            }
            StringBuilder sb = new StringBuilder();
            while (!stack.isEmpty()) {
                sb.insert(0, "/" + stack.pop());
            }
            return sb.toString();
        }
    }
}