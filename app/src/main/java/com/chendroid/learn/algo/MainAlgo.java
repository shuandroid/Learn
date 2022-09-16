package com.chendroid.learn.algo;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * @author : zhaochen
 * @date : 2022/2/21
 * @description : 算法记录类
 */
public class MainAlgo {

    // 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
    //请注意 ，必须在不复制数组的情况下原地对数组进行操作。
    // 输入: nums = [0,1,0,3,12]
    // 输出: [1,3,12,0,0]
    public void moveZeroes(int[] nums) {
        if (nums.length == 0) {
            return;
        }

//        int index = 0;
//        for (int i = 0; i< nums.length; i++) {
//            if (nums[i] == 0) {
//                // 找到目标的 0 位置，然后对后续的进行循环，找到第一个不为 0 的数组
//                index = i + 1;
//                while (index < nums.length) {
//                    if (nums[index] != 0) {
//                        // 找到第一个不为 0 的地方; 交换
//                        nums[i] = nums[index];
//                        nums[index] = 0;
//                        break;
//                    } else {
//                        index++;
//                    }
//                }
//            }
//        }

        int index = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == 0) {
                index = i + 1;
                while (index < nums.length) {
                    if (nums[index] != 0) {
                        // 交换
                        nums[i] = nums[index];
                        nums[index] = 0;
                        break;
                    } else {
                        // 如果不为 0， 则继续向后寻找；如果为最后一个，则不再进行循环，直接返回
                        if (index == nums.length - 1) {
                            return;
                        }
                        index++;
                    }
                }
            } else {
                // 不是目标数据，不需要移动
                index++;
            }
        }
    }

    public void moveZeroes2(int[] nums) {
        if (nums.length == 0) {
            return;
        }

        int right = 0;
        int left = 0;

        while (left < nums.length) {
            if (nums[left] != 0) {
                left++;
                right++;
            } else {
                // 此时 nums[left]等于 0，
                if (nums[right] != 0) {
                    // 此时需要交换
                    nums[left] = nums[right];
                    nums[right] = 0;
                    left++;
                    right++;
                } else {
                    right++;
                }
            }
        }

    }


    // 给你一个下标从 1 开始的整数数组 numbers ，该数组已按 非递减顺序排列  ，请你从数组中找出满足相加之和等于目标数 target 的两个数。如果设这两个数分别是 numbers[index1] 和 numbers[index2] ，则 1 <= index1 < index2 <= numbers.length 。
    //
    //以长度为 2 的整数数组 [index1, index2] 的形式返回这两个整数的下标 index1 和 index2。
    //
    //你可以假设每个输入 只对应唯一的答案 ，而且你 不可以 重复使用相同的元素。
    //
    //你所设计的解决方案必须只使用常量级的额外空间。
    // 输入：numbers = [2,7,11,15], target = 9
    //输出：[1,2]
    //解释：2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。返回 [1, 2] 。

    public int[] twoSum(int[] numbers, int target) {
        int[] targetNum = new int[2];
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] + numbers[j] == target) {
                    targetNum[0] = i + 1;
                    targetNum[1] = j + 1;
                    return targetNum;
                } else if (numbers[i] + numbers[j] > target) {
                    // 如果已经大于，则直接退出本次循环，后面不会出现了
                    break;
                }
            }
        }

        return targetNum;
    }

    // 加入二分法进步一缩短时间；
    public int[] twoSum2(int[] numbers, int target) {
        int[] targetNum = new int[2];
        for (int i = 0; i < numbers.length; i++) {
            int low = i + 1;
            int high = numbers.length - 1;
            // 这里用二分进一步缩短时间
            while (low <= high) {
                int center = low + (high - low) / 2;
                if (numbers[center] == target - numbers[i]) {
                    targetNum[0] = i + 1;
                    targetNum[1] = center + 1;
                    return targetNum;
                } else if (numbers[center] > target - numbers[i]) {
                    high = center - 1;
                } else {
                    low = center + 1;
                }
            }
        }

        return targetNum;
    }

    // 根据有且仅有一个唯一值，那么其实可以使用双指针，一个在 left, 一个 right, 进行移动；
    public int[] twoSum3(int[] numbers, int target) {
        int[] targetNum = new int[2];
        int left = 0;
        int right = numbers.length - 1;

//        while (left <= right) {
//            if (numbers[left] + numbers[right] == target) {
//                targetNum[0] = left + 1;
//                targetNum[1] = right + 1;
//                return targetNum;
//            } else if (numbers[left] + numbers[right] > target) {
//                right--;
//            } else {
//                left++;
//            }
//        }

        while (left <= right) {
            if (numbers[left] + numbers[right] == target) {
                targetNum[0] = left + 1;
                targetNum[1] = right + 1;
                break;
            } else if (numbers[left] + numbers[right] > target) {
                // 此时已经大于了～则，把  right 往左移动。 因为 numbers 递增
                right--;
            } else {
                left++;
            }
        }

        return targetNum;
    }

    // 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
    //
    //不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
    // 输入：s = ["h","e","l","l","o"]
    // 输出：["o","l","l","e","h"]

    public void reverseString(char[] s) {
        if (s.length == 0) {
            return;
        }
        int left = 0;
        int right = s.length - 1;

        while (left <= right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }

    }

    // 给定一个字符串 s ，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
    // 输入：s = "Let's take LeetCode contest"
    // 输出："s'teL ekat edoCteeL tsetnoc"

    public String reverseWords(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        int right = s.length();
        int i = 0;
//        String[] wordList = s.split(" ");
//        for (int index = 0; index < wordList.length; index++) {
////            s.toCharArray()
//        }

        while (i < right) {
            int start = i;
            while (i < right && s.charAt(i) != ' ') {
                // 此时没有到单词的分界线；
                i++;
            }
            // 此时到了分界线， 距离为 start -> i 的距离为一个单词
            for (int j = start; j < i; j++) {
                stringBuilder.append(s.charAt(start + (i - j) - 1));
                // 从尾到头，依此加入；
            }
            // 此时第一个单词已经反转好了，存放在 stringBuilder 中
            while (i < right && s.charAt(i) == ' ') {
                // 此时依此补充空格，有几个空格，补充几个空格
                stringBuilder.append(' ');
                i++;
            }
        }

        return stringBuilder.toString();

    }

    // 给定一个头结点为 head 的非空单链表，返回链表的中间结点。
    //
    //如果有两个中间结点，则返回第二个中间结点。

    // 输入：[1,2,3,4,5]
    //输出：此列表中的结点 3 (序列化形式：[3,4,5])
    //返回的结点值为 3 。 (测评系统对该结点序列化表述是 [3,4,5])。
    //注意，我们返回了一个 ListNode 类型的对象 ans，这样：
    //ans.val = 3, ans.next.val = 4, ans.next.next.val = 5, 以及 ans.next.next.next = NULL.
    //

    // 使用数组额外空间来实现
    public ListNode middleNode(ListNode head) {

        ListNode[] nodes = new ListNode[100];
        int size = 0;
        while (head != null) {
            nodes[size] = head;
            head = head.next;
            size++;
        }

        return nodes[size / 2];

    }

    // 使用遍历来做
    public ListNode middleNode2(ListNode head) {

        ListNode temp = head;

        int size = 0;
        while (temp != null) {
            temp = temp.next;
            size++;
        }

        // 得到 原链表的大小 size
        // 再把 temp 指向第一个 head;
        temp = head;
        int i = 0;
        while (i < size / 2) {
            temp = temp.next;
            i++;
        }
        // while 循环过去，此时指向中间的那个值
        return temp;
    }

    // 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 设置一个虚拟的头节点；
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode temp = head;

        int size = 0;
        while (temp != null) {
            temp = temp.next;
            size++;
        }
        // 得到 长度 size
        if (size < n) {
            // 如果不存在该数，则返回
            return head;
        }

        int target = size - n;
        // 设置虚拟节点
        temp = dummy;
        for (int i = 0; i < target; i++) {
            temp = temp.next;
        }
        // 此时走到的 temp 为要要留的，它的 next 为需要剔除的；
        //
        temp.next = temp.next.next;
        // 返回结果为，虚节点的下一个！
        return dummy.next;

    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    // 合并两个有序的链表：
    // 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的
    // 递归方式；
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        //
        if (list1 == null) {
            return list2;
        } else if (list2 == null) {
            return list1;
        } else {
            // 此时，两者都不为空
            if (list1.val < list2.val) {
                // 如果小于，则需要设置list.next 为「为他们中的下的下一个的小的值」
                list1.next = mergeTwoLists(list1.next, list2);
                return list1;
            } else {
                list2.next = mergeTwoLists(list1, list2.next);
                return list2;
            }
        }
    }

    public ListNode mergeTwoLists2(ListNode list1, ListNode list2) {
        //不使用递归；
        // 设置一个虚拟头节点
        ListNode preHead = new ListNode(-1);
        ListNode pre = preHead;

        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                pre.next = list1;
                list1 = list1.next;
            } else {
                // 此时，需要 pre 指向 list2, 因为它小
                pre.next = list2;
                // 移动 list2 的下标
                list2 = list2.next;
            }
            // 移动 pre 的下标；
            pre = pre.next;
        }

        if (list1 != null) {
            // 如果 while 循环结束了，此时 list1 不为空，则把剩余的 list1， 全部给到 pre.next
            pre.next = list1;
        }
        if (list2 != null) {
            // 同样的逻辑，这里，上一个 if 和这个 if， 只可能有一个走到，或者两个都走不到
            pre.next = list2;
        }

        return preHead.next;

    }

    // 给你一个链表数组，每个链表都已经按升序排列。
    //
    //请你将所有链表合并到一个升序链表中，返回合并后的链表。
    // 分治的方法
    public ListNode mergeKLists(ListNode[] lists) {
        return merge(lists, 0, lists.length - 1);
    }

    public ListNode merge(ListNode[] lists, int l, int r) {
        if (l == r) {
            return lists[l];
        }
        if (l > r) {
            return null;
        }
        int mid = (l + r) >> 1;
        return mergeTwoLists(merge(lists, l, mid), merge(lists, mid + 1, r));
    }

//    public ListNode mergeTwoLists(ListNode a, ListNode b) {
//        if (a == null || b == null) {
//            return a != null ? a : b;
//        }
//        ListNode head = new ListNode(0);
//        ListNode tail = head, aPtr = a, bPtr = b;
//        while (aPtr != null && bPtr != null) {
//            if (aPtr.val < bPtr.val) {
//                tail.next = aPtr;
//                aPtr = aPtr.next;
//            } else {
//                tail.next = bPtr;
//                bPtr = bPtr.next;
//            }
//            tail = tail.next;
//        }
//        tail.next = (aPtr != null ? aPtr : bPtr);
//        return head.next;
//    }


    // 反转链表
    public ListNode reverseList(ListNode head) {
        // 设置一个虚拟头节点
        ListNode preHead = new ListNode(-1);
        ListNode pre = preHead;
        ListNode temp = null;
        while (head != null && head.next != null) {
            pre.next = head;
            head = head.next;
            pre.next.next = temp;
            temp = pre.next;
        }
        if (head != null) {
            pre.next = head;
            pre.next.next = temp;
        }

        return preHead.next;
    }

    // 反转链表
    public ListNode reverseList2(ListNode head) {
        // 设置一个虚拟头节点
        ListNode pre = null;
        ListNode temp = head;
        while (temp != null) {
            ListNode next = temp.next;
            temp.next = pre;
            pre = temp;
            temp = next;
        }


        return pre;
    }

    // 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
    // 输入: s = "abcabcbb"
    //输出: 3
    //解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int maxLength = 0;
        // 字符串的长度
        int length = s.length();
        // 两个坐标，第一个指向左边 ,   第二个指向右边xxx
        int first = -1;
        // first 从 -1 开始的好处是：
        Set temp = new HashSet<Character>();

        for (int i = 0; i < length; i++) {

            if (length - i < maxLength) {
                break;
            }

            if (i != 0) {
                // 此时已经找到了重复的字符位置， 需要以下一个 i 的为开始的「最大不重复字符串的大小」；此时需要手动移除以及加入到 temp 的第一个字符即：i-1
                // 移除掉～
                temp.remove(s.charAt(i - 1));
            }
            // 重新开始以本次 i ,去寻找 「最大不重复字符串的大小」
            while (first + 1 < length && !temp.contains(s.charAt(first + 1))) {
                // 依此添加不同的字符到 set 中，直到 s.charAt(first + 1) 的值在 temp 中已经有了，找到了重复的值
                temp.add(s.charAt(first + 1));
                first++;
            }

            // 计算出此时的字符串的大小； 此时的 maxLength 的大小，是以 i 为开始字符找到的最大的不重复的字符串的大小；
            maxLength = Math.max(maxLength, (first + 1) - i);
        }

        return maxLength;
    }

    // 给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的排列。如果是，返回 true ；否则，返回 false 。
    //
    //换句话说，s1 的排列之一是 s2 的 子串
    // 输入：s1 = "ab" s2 = "eidbaooo"
    //输出：true
    //解释：s2 包含 s1 的排列之一 ("ba")

    // 排列，排队会出现各种个样的形式，该如何确定一个字符包含另外一个字符的排列，这个是个很大的问题；条件：
    //
    public boolean checkInclusion(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return false;
        }

        int s1Length = s1.length();
        int s2Length = s2.length();

        if (s1Length > s2Length) {
            return false;
        }

        // 滑动窗口，字符中都是小写，可以考虑使用 char - 'a' 来获取到它的整形数；
        // 小写字母一共 26 个；
        int[] cnt1 = new int[26];
        int[] cnt2 = new int[26];

        // 对要找的 s1 数组进行判断，判断各个字符的数量；
        for (int i = 0; i < s1Length; i++) {
            // 找到该字符对应的位置，使得个数 +1 . 表示这个字符有数据；
            cnt1[s1.charAt(i) - 'a']++;
            // 同时从第 0 个开始计算 s2 的字符，以及每个字符的个数；
            cnt2[s2.charAt(i) - 'a']++;
        }

        if (Arrays.equals(cnt1, cnt2)) {
            // 如果 s2 从 0 开始到 s1Length 位置，组成的字符串就是 s1 的排列的话，直接返回 true;
            return true;
        }

        //如果不等，则需要移动 s2 的区间字符串，依此再次从 1 开始；
        for (int i = s1Length; i < s2Length; i++) {
            // 从 s1Length 的下标位置，开始找 s2 的字符；
            // 此时只需要操作 cnt2 的数组即可，
            // 先移除前面的一个字符, 下标为： i - s1{的字符长度}；
            cnt2[s2.charAt(i - s1Length) - 'a']--;
            // 加入新的字符：
            cnt2[s2.charAt(i) - 'a']++;
            if (Arrays.equals(cnt1, cnt2)) {
                return true;
            }
        }

        return false;
    }

    // 优化方式，根据 diff，差异，来判断
    // diff == 0 表示不存在差异，此时表示找到，返回 true;
    public boolean checkInclusion2(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return false;
        }

        int s1Length = s1.length();
        int s2Length = s2.length();

        if (s1Length > s2Length) {
            return false;
        }

        // 滑动窗口，字符中都是小写，可以考虑使用 char - 'a' 来获取到它的整形数；
        // 小写字母一共 26 个；
        int[] cnt1 = new int[26];
//        int[] cnt2 = new int[26];

        // 对要找的 s1 数组进行判断，判断各个字符的数量；
        for (int i = 0; i < s1Length; i++) {
            // 找到该字符对应的位置，使得个数 -1 . 表示这个字符与 s2 之间的有差异
            cnt1[s1.charAt(i) - 'a']--;
            // 使用同一个数组，判断 s2, 如果有， 则 +1, 表示把上面的差异抹平；
            cnt1[s2.charAt(i) - 'a']++;
        }
        int diff = 0;
        // 计算 diff 的大小，不为空，则表示有 diff
        for (int i = 0; i < cnt1.length; i++) {
            if (cnt1[i] != 0) {
                diff++;
            }
        }

        if (diff == 0) {
            return true;
        }

        //如果不等，则需要移动 s2 的区间字符串，依此再次从 1 开始；
        for (int i = s1Length; i < s2Length; i++) {
            // 要移走的 char 的数值
            int removeChar = s2.charAt(i - s1Length) - 'a';
            // 要添加的 char 的数值
            int addChar = s2.charAt(i) - 'a';
            if (removeChar == addChar) {
                // 相当于本次移动没有效果，跳过，进入下一次循环；
                continue;
            }

            if (cnt1[removeChar] == 0) {
                // 如果要准备移走的，数值为 0, 表示不存在 diff；那么要移走了，则需要把 diff + 1
                diff++;
            }
            // 真正移走 removeChar
            cnt1[removeChar]--;
            if (cnt1[removeChar] == 0) {
                // 如果移走后，数组为 0， 表示 diff 少一个
                diff--;
            }

            if (cnt1[addChar] == 0) {
                // 如果原本不存在这个字符的数值，即为 0， 现在要添加了，则需要 diff +1 ,表示新增了一个；
                diff++;
            }
            // 真正加入 addChar
            cnt1[addChar]++;

            if (cnt1[addChar] == 0) {
                // 如果加入后，此时为 0， 表示加入后，使得 diff 减少，则 diff - 1;
                diff--;
            }

            if (diff == 0) {
                // 每一轮的最后，判断 diff 是否为 0 ， 如果为 0 ，表示符合要求，返回 true
                return true;
            }
        }
        // 已经走完 for 循环，此时 diff 仍然不为 0， 表示不存在，返回 false
        return false;
    }

    // 有一幅以二维整数数组表示的图画，每一个整数表示该图画的像素值大小，数值在 0 到 65535 之间。
    //
    //给你一个坐标 (sr, sc) 表示图像渲染开始的像素值（行 ，列）和一个新的颜色值 newColor，让你重新上色这幅图像。
    //
    //为了完成上色工作，从初始坐标开始，记录初始坐标的上下左右四个方向上像素值与初始坐标相同的相连像素点，接着再记录这四个方向上符合条件的像素点与他们对应四个方向上像素值与初始坐标相同的相连像素点，……，重复该过程。将所有有记录的像素点的颜色值改为新的颜色值。
    //
    //最后返回经过上色渲染后的图像

    // 需要定义上下左右，四个方向的值，预计为：
    // x 方向的坐标：分别为：下、右、左，上
    int[] dx = {1, 0, 0, -1};
    int[] dy = {0, 1, -1, 0};

    // x 为行！！！ 第几行； y 为列：第几列
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        // 保存当前的坐标颜色值
        int currColor = image[sr][sc];

        if (currColor == newColor) {
            return image;
        }
        // n 为：image 数组的大小，即行数，也算作 x 坐标；
        // m 为：image 中每个数组的大小，即列数，也算做 y 坐标；
        int n = image.length, m = image[0].length;
        // 队列
        Queue<int[]> queue = new LinkedList<int[]>();
        // 默认把目标位置入队列
        queue.offer(new int[]{sr, sc});
        // 同时修改目前位置的颜色值
        image[sr][sc] = newColor;
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            // x 为 目前位置的 x 坐标； y 目前位置的 y 坐标;
            int x = cell[0], y = cell[1];
            // 取 4 的原因：上下左右，4 个方向；
            for (int i = 0; i < 4; i++) {
                // 找到目标坐标的上下左右，四个 方向的其他位置的坐标，分别为：mx， my
                int mx = x + dx[i], my = y + dy[i];
                // 边界条件检测
                if (mx >= 0 && mx < n && my >= 0 && my < m && image[mx][my] == currColor) {
                    // 符合的话，分别入组，并且把它的颜色值改变
                    queue.offer(new int[]{mx, my});
                    image[mx][my] = newColor;
                }
            }
        }
        return image;
    }

    // 给你一个大小为 m x n 的二进制矩阵 grid 。
    //
    //岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在 水平或者竖直的四个方向上 相邻。你可以假设 grid 的四个边缘都被 0（代表水）包围着。
    //
    //岛屿的面积是岛上值为 1 的单元格的数目。
    //
    //计算并返回 grid 中最大的岛屿面积。如果没有岛屿，则返回面积为 0 。
    //同样需要 dx,dy 这两个参数; 需要考虑去重问题；
    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null) {
            return 0;
        }
        int numberTotal = 0;
        // 数组的大小，即行数，也算作 x 坐标；
        int n = grid.length;
        // m 为：image 中每个数组的大小，即列数，也算做 y 坐标；
        int m = grid[0].length;
        // 循环行数
        for (int i = 0; i < n; i++) {
            // 循环列数
            for (int j = 0; j < m; j++) {
                numberTotal = Math.max(numberTotal, targetNumber(grid, i, j));
            }
        }

        return numberTotal;
    }

    // 计算在 [i][j] 坐标下，上下左右，符合我们要求的区域的数量
    private int targetNumber(int[][] grid, int i, int j) {
        if (grid[i][j] != 1) {
            // 不是我们要找的区域，则直接返回 0,
            return 0;
        }
        // 表示是我们需要找的区域，则计算该区域的符合我们要求的数量大小, 此时 size 最少为 1；
        int size = 1;
        // 把它设置为 0, 同一区域，只需要计算一次；
        grid[i][j] = 0;

        Queue<int[]> queue = new LinkedList<int[]>();
        queue.offer(new int[]{i, j});
        while (!queue.isEmpty()) {
            // 先 poll 出来，此时第一次时， queue 已经为空，后续需要 offer 进入队列其他的；
            int[] current = queue.poll();
            // 拿到此时 current 的坐标
            int x = current[0];
            int y = current[1];
            // 取 4 的原因：上下左右，4 个方向；
            for (int d = 0; d < 4; d++) {
                int mx = x + dx[d];
                int my = y + dy[d];
                if (mx >= 0 && mx < grid.length && my >= 0 && my < grid[0].length && grid[mx][my] == 1) {
                    // 找到目标为 1 的坐标；
                    size++;
                    queue.offer(new int[]{mx, my});
                    // 同时把坐标对应的值，设置为 0；
                    grid[mx][my] = 0;
                }
            }
        }

        return size;
    }

    // 矩阵问题：

    // 给定一个由 0 和 1 组成的矩阵 mat ，请输出一个大小相同的矩阵，其中每一个格子是 mat 中对应位置元素到最近的 0 的距离。
    //
    //两个相邻元素间的距离为 1 。
    //

    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int[][] updateMatrix(int[][] matrix) {
        //
        if (matrix == null || matrix.length == 0) {
            return new int[0][0];
        }
        // 行的个数
        int m = matrix.length;
        // 列的个数
        int n = matrix[0].length;
        // 预计返回的矩阵数组
        int[][] dist = new int[m][n];
        // 是否检查过该位置的数组，true 表示这个位置已经检查过，需要跳过； false 表示未检查过；
        boolean[][] seen = new boolean[m][n];
        // 队列，需要把数组中的元素，进入队列
        Queue<int[]> queue = new LinkedList<int[]>();
        // 把目前所有为 0 的位置加入到 queue 中
        for (int i = 0; i < m; i++) {
            //
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                    // 该位置已经检查过；
                    seen[i][j] = true;
                }
            }
        }

        // queue 中已经包含了，所有为 0 的位置；
        // 进行广度优先搜索
        while (!queue.isEmpty()) {
            // 队列不为空，进入循环
            int[] cell = queue.poll();
            // 获取当前 cell 中保存的位置
            int i = cell[0];
            int j = cell[1];
            // 四个方向
            for (int d = 0; d < 4; d++) {

                int ni = i + dirs[d][0];
                int nj = j + dirs[d][1];
                if (nj >= 0 && nj < n && ni >= 0 && ni < m && !seen[ni][nj]) {
                    // 此时为未看到过的位置，且符合要求，则，距离 + 1；
                    dist[ni][nj] = dist[i][j] + 1;
                    // 加入队列
                    queue.offer(new int[]{ni, nj});
                    // 标志为 true 已经检查过，不需要重复检测
                    seen[ni][nj] = true;
                }
            }
        }

        return dist;
    }

    // 腐烂的橘子问题
    // 在给定的 m x n 网格 grid 中，每个单元格可以有以下三个值之一：
    //
    //值 0 代表空单元格；
    //值 1 代表新鲜橘子；
    //值 2 代表腐烂的橘子。
    //每分钟，腐烂的橘子周围 4 个方向上相邻的新鲜橘子都会腐烂。
    //
    //返回 直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回-1
    //
    int[] dr = new int[]{-1, 0, 1, 0};
    int[] dc = new int[]{0, -1, 0, 1};

    public int orangesRotting(int[][] grid) {
        // 如果没有～
        // 获取二维数组的行数row 和 列数 column
        int R = grid.length, C = grid[0].length;
        // 缓存的队列
        Queue<Integer> queue = new ArrayDeque<>();
        // 用于存储某个位置，腐烂的深度，即需要的腐烂时间
        Map<Integer, Integer> depth = new HashMap<>();
        // 先去寻找已经腐烂的橘子位置
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (grid[i][j] == 2) {
                    // 腐烂的橘子
                    // 计算出，该位置在数组中的位置，平面的位置
                    int code = i * C + j;
                    queue.add(code);
                    // 此时已经为腐烂的，0 分钟；
                    depth.put(code, 0);
                }
            }
        }
        // 设置要返回的目标值，初始值为 0
        int ans = 0;

        while (!queue.isEmpty()) {
            // 开始从队列中取值；
            int code = queue.remove();
            // 根据 code , 反向计算出原来的坐标：
            // 行
            int r = code / C;
            // 列
            int c = code % C;

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < R && nc >= 0 && nc < C && grid[nr][nc] == 1) {
                    grid[nr][nc] = 2;
                    int ncode = nr * C + nc;
                    queue.add(ncode);
                    depth.put(ncode, depth.get(code) + 1);
                    ans = depth.get(ncode);
                }
            }
        }

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (grid[i][j] == 1) {
                    return -1;
                }
            }
        }

        return ans;
    }


    // 给你两棵二叉树： root1 和 root2 。
    //
    //想象一下，当你将其中一棵覆盖到另一棵之上时，两棵树上的一些节点将会重叠（而另一些不会）。你需要将这两棵树合并成一棵新二叉树。合并的规则是：如果两个节点重叠，那么将这两个节点的值相加作为合并后节点的新值；否则，不为 null 的节点将直接作为新二叉树的节点。
    //
    //返回合并后的二叉树。
    //
    //注意: 合并过程必须从两个树的根节点开始
    // 需要使用递归，每次只合并跟节点，left 和 right， 节点，分别交由下一次处理
    private TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }

        TreeNode newNode = new TreeNode(root1.val + root2.val);
        newNode.left = mergeTrees(root1.left, root2.left);
        newNode.right = mergeTrees(root1.right, root2.right);
        return newNode;
    }

    private TreeNode mergeTrees2(TreeNode root1, TreeNode root2) {
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }

        TreeNode newNode = new TreeNode(root1.val + root2.val);
        // 用来存放头节点
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<TreeNode> queue2 = new LinkedList<>();

        Queue<TreeNode> queue3 = new LinkedList<>();

        // 加入队列
        queue.offer(newNode);
        queue2.offer(root1);
        queue3.offer(root2);

        while (!queue2.isEmpty() && !queue3.isEmpty()) {
            // 都不为空的情况下，进入循环；
            TreeNode node = queue.poll();
            TreeNode node2 = queue2.poll();
            TreeNode node3 = queue3.poll();
            TreeNode left2 = node2.left, left3 = node3.left, right2 = node2.right, right3 = node3.right;
            if (left2 != null && left3 != null) {
                // left 均不为空情况下，此时合并成新的 left
                TreeNode left = new TreeNode(left2.val + left3.val);
                // 指向新的节点；
                node.left = left;
                // 并把 left 加入到 queue 中，下次获取它；
                queue.offer(left);
                // 同时更新 queue2, queue3 的顶部 node 值
                queue2.offer(left2);
                queue3.offer(left3);
            } else if (left2 != null) {
                // 此时 left2 != null ; left3 == null
                node.left = left2;
            } else if (left3 != null) {
                node.left = left3;
            }

            if (right2 != null && right3 != null) {
                TreeNode right = new TreeNode(right2.val + right3.val);
                node.right = right;
                queue.offer(right);
                queue2.offer(right2);
                queue3.offer(right3);
            } else if (right2 != null) {
                node.right = right2;
            } else if (right3 != null) {
                node.right = right3;
            }

        }

        return newNode;
    }


    // 树的数据类
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    public Node connect(Node root) {
        if (root == null) {
            return null;
        }
        Node node = root;

        while (node.left != null) {
            // 如果 left 不为空，则需要补充
            Node head = node;
            while (head != null) {
                // 先设置 head 的 left;
                head.left.next = head.right;

                if (head.next != null) {
                    // 此时表示有对应 head 层级的右节点, 把 head.right 执行 与 head 平级的 left 节点；
                    head.right.next = head.next.left;
                }

                head = head.next;
            }

            node = node.left;
        }


        return root;
    }

    // 组合：
    // 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
    //你可以按 任何顺序 返回答案。

    public List<List<Integer>> combine(int n, int k) {
        // 如果 n < k ，此时不存在；返回 null
        if (n < k) {
            return null;
        }
        List<List<Integer>> target = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();

        for (int i = 1; i <= k; i++) {
            // 先补充前 1～k 的值到队列中
            temp.add(i);
        }
        temp.add(n + 1);
        int j = 0;
        while (j < k) {
            target.add(new ArrayList<Integer>(temp.subList(0, k)));
            // 把 j 再次设置为 0
            j = 0;
            while (j < k && temp.get(j) + 1 == temp.get(j + 1)) {
                // 如果此时 j 和下一个 j+1 对应的值，相差 1
                temp.set(j, j + 1);
                j++;
            }

            temp.set(j, temp.get(j) + 1);
        }

        return target;
    }

    // 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
    public List<List<Integer>> permute(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        // todo 尚未完成
        return null;
    }

    // 返回一个二叉树的深度；
    public int maxDeep(TreeNode treeNode) {

        if (treeNode == null) {
            return 0;
        }

        int left = maxDeep(treeNode.left) + 1;
        int right = maxDeep(treeNode.right) + 1;
        return Math.max(left, right);
    }

    /**
     * 返回一个 View 的层级深度
     */
    public int maxViewDeep(View view) {
        if (!(view instanceof ViewGroup)) {
            return 0;
        }

        if (((ViewGroup) view).getChildCount() == 0) {
            return 0;
        }

        int max = 0;
        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
            int deep = maxViewDeep(((ViewGroup) view).getChildAt(i)) + 1;
            max = Math.max(max, deep);
        }

        return max;
    }

    // 遍历一个view所有层级子view和父view的个数
    public int maxViewCount(View view) {
        if (!(view instanceof ViewGroup)) {
            return 0;
        }
        int childCount = ((ViewGroup) view).getChildCount();

        for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
            childCount += maxViewCount(((ViewGroup) view).getChildAt(i));
        }

        return childCount;
    }

    // 动态股票问题： 可以有多次交易；
    public int maxProfit(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }

        int[][] dp = new int[prices.length][2];
        // dp[x][0] 表示第 x 天，不持有股票的收益，即卖出股票；
        // dp[x][1] 表示第 x 天，持有股票的收益；

        dp[0][0] = 0;
        // 如果第 0 天买入股票，则收益是负的
        dp[0][1] = -prices[0];

        // 从第 2 天开始；对 dp 二维数组进行赋值
        for (int i = 1; i < prices.length; i++) {
            // 今天卖出的收益, 如果前一天持有了，则今天的收益可以增加 prices[i]
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            // 今天不持有的收益，如果昨天没有持有，今天买入，则收益减少 prices[i]; 如果昨天持有，今天仍然持有，则收益不变
            dp[i][1] = Math.max(dp[i - 1][0] - prices[i], dp[i - 1][1]);
        }
        // 返回最后一天不持有股票的收益
        return dp[prices.length - 1][0];
    }

    // 动态股票问题： 只有一次买和卖；
    // 只需要找到最小的那个值，以及这个值后面的最大的值
    public int maxProfit2(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }

        int minProfit = prices[0];
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < minProfit) {
                minProfit = prices[i];
            } else if (prices[i] - minProfit > maxProfit) {
                maxProfit = prices[i] - minProfit;
            }
        }

        return maxProfit;
    }

    /**
     * 双重检查单例
     */
    public static class DoubleCheckInstance {
        // 使用 volatile 来防止在 instance = new DoubleCheckInstance() 过程中的代码重排序；
        private volatile static DoubleCheckInstance instance;

        private DoubleCheckInstance() {
        }

        public DoubleCheckInstance getInstance() {
            // 第一个 if， 防止进入加锁的判断，如果已经不为 null, 则直接 return
            if (instance == null) {
                // 对整个 DoubleCheckInstance 类文件进行加锁
                synchronized (DoubleCheckInstance.class) {
                    // 第二个 if, 防止多次创建; 线程 A,B 可能都通过了第一个 if 的判断，然后 A 线程先获得锁，创建一个实例，退出，释放锁；
                    // 此时线程 B 获得锁，进入，如果不加第二个 if, 则会再次创建一个实例；
                    if (instance == null) {
                        instance = new DoubleCheckInstance();
                        // instance = new DoubleCheckInstance() 不是原子操作，分为 3 步完成；
                        // 1. memory =  allocate(); 1. 分配一块内存空间；
                        // 2. instance(memory); 2. 对内存空间的内部进行数据的初始化；
                        // 3. instance = memory; 3. 把 instance 执行刚才分配的内存地址，这个走完后 instance != null 了
                    }
                }
            }
            return instance;
        }
    }

    // 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
    //
    //你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
    //
    //你可以按任意顺序返回答案
    //
    private int[] twoSumNew(int[] nums, int target) {
        if (nums.length == 0) {
            return new int[]{0, 0};
        }
        int[] indexTarget = new int[2];
        for (int i = 0; i < nums.length; i++) {

            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    indexTarget[0] = i;
                    indexTarget[1] = j;
                    return indexTarget;
                }
            }

        }
        return indexTarget;
    }

    // 优化的方法
    private int[] twoSumNew2(int[] nums, int target) {
        Map<Integer, Integer> hashNum = new HashMap<Integer, Integer>();

        for (int i = 0; i < nums.length; i++) {
            if (hashNum.containsKey(target - nums[i])) {
                // 如果已经存在 在 hashMap 中，则返回，找到了对应的数据；
                return new int[]{hashNum.get(target - nums[i]), i};
            }
            hashNum.put(nums[i], i);
        }
        // 返回空数组
        return new int[0];
    }


    // 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
    //
    //请你将两个数相加，并以相同形式返回一个表示和的链表。
    //
    //你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
    // 输入：l1 = [2,4,3], l2 = [5,6,4]
    //输出：[7,0,8]
    //解释：342 + 465 = 807.

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null;
        ListNode tail = null;
        // 上一次的进位的临时存储
        int temp = 0;
        // 为了避免对于同一个链表的连续进位，统一放在 while 中进行进位
        while (l1 != null || l2 != null) {
            // 如果 l1 存在，则返回它的值
            int n1 = l1 != null ? l1.val : 0;
            // 如果 l2 存在，则返回它的值
            int n2 = l2 != null ? l2.val : 0;
            int sum = n1 + n2 + temp;
            if (head == null) {
                // 第一次;
                // 对 10 取余；
                head = tail = new ListNode(sum % 10);
            } else {
                // 每次更新 tail；
                tail.next = new ListNode(sum % 10);
                tail = tail.next;
            }
            temp = sum / 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        // 如果最后一个也有进位，则增加一个 listNode;
        if (temp > 0) {
            // 其实大部分情况下 temp = 1 , 不会大于 1 的
            tail.next = new ListNode(temp);
        }
        return head;
    }

    //
    //给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
    //
    //算法的时间复杂度应该为 O(log (m+n)) 。
    //
    //来源：力扣（LeetCode）
    //链接：https://leetcode.cn/problems/median-of-two-sorted-arrays
    //著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

    // 先合并数组，合并后的数组，如果 length 为偶数，/2 后得到的 index, 中位数为 (nums[index - 1] + nums[index]) / 2;
    // 如果为奇数， 则 length / 2 得到的整数，即为中位数的下标
    // todo 这个有点难
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] sum = new int[nums1.length + nums2.length];


        return 0;
    }

    // 合并两个有序数组
    public int[] twoSumNew(int[] nums1, int[] nums2) {
        int[] sum = new int[nums1.length + nums2.length];


        return new int[0];
    }


    // 给你一个字符串 s，找到 s 中最长的回文子串。
    public String longestPalindrome(String s) {
        int length = s.length();
        if (length < 2) {
            // 如果字符串大小小于 2 ，则一定是回文子串
            return s;
        }

        int maxLen = 1;
        int begin = 0;
        // 使用dp[i][j] 是否为 true,来表示从 s[i..j] 是否为回文子串
        boolean[][] dp = new boolean[length][length];
        // 对二维数组进行初始化，
        for (int i = 0; i < length; i++) {
            dp[i][i] = true;
        }

        char[] charArray = s.toCharArray();
        // 先枚举子串长度
        for (int L = 2; L <= length; L++) {
            // 枚举左边界
            for (int i = 0; i < length; i++) {
                // j 表示右边界；
                int j = L + i - 1;
                if (j >= length) {
                    // 如果 右边界已经大于了 length ， 则内部 for 循环结束，外层 for 循环进行下一个；
                    break;
                }

                if (charArray[i] != charArray[j]) {
                    // 如果不等，则为 false
                    dp[i][j] = false;
                } else {
                    // 相等的情况下
                    if (j - i < 3) {
                        // 间距不超过 3， 表示是 aa 这种情况，个数比较少的情况
                        dp[i][j] = true;
                    } else {
                        // 差的比较比较多的情况下，就得看它的缩小版是否为回文子串；如果为 true, 则本次也为 true; 如果为 false, 表示 i - j 也不是回文子串；
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                if (dp[i][j] && j - i + 1 > maxLen) {
                    // 如果找到了一个回文子串，并且 j-i 的长度，要大于 maxLen; 则更新 maxLen;
                    maxLen = j - i + 1;
                    begin = i;
                }

            }
        }
        // 整个 for 循环下来，找到最大的长度：maxLen, 以及这个回文子串的开始位置： begin, 最后的位置是：maxLen{不需要 -1 因为 subString 不包含最后一个位置的字符}
        return s.substring(begin, begin + maxLen);
    }

    // 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
    //
    //找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
    //
    //返回容器可以储存的最大水量。
    //
    //说明：你不能倾斜容器。
    //
    //来源：力扣（LeetCode）
    //链接：https://leetcode.cn/problems/container-with-most-water
    //著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

    public int maxArea(int[] height) {
//        if (height.length == 0) {
//            return 0;
//        }
//
//        int maxSum = 0;
//        for (int i  = 0; i < height.length; i++) {
//            for (int j = i + 1; j < height.length; j++) {
//                int w = j - i;
//                int h = Math.min(height[i], height[j]);
//                int temp = w * h;
//                if (temp > maxSum) {
//                    maxSum = temp;
//                }
//            }
//        }
//
//        return maxSum;

        // 双指针实现

        if (height.length == 0) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int max = Math.min(height[left], height[right]) * (right - left);
        while (left <= right) {
            int temp = Math.min(height[left], height[right]) * (right - left);
            if (temp > max) {
                max = temp;
            }
            // 准备移动
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return max;
    }

    // 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
    //
    //注意：答案中不可以包含重复的三元组。
    //
    //来源：力扣（LeetCode）
    //链接：https://leetcode.cn/problems/3sum
    //著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

    public List<List<Integer>> threeSum(int[] nums) {
        //
        if (nums.length < 3) {
            return null;
        }

        int length = nums.length;
        // 为了去重；以及后续的优化，先对数组进行排序；
        Arrays.sort(nums);
        List<List<Integer>> target = new ArrayList<List<Integer>>();
        for (int first = 0; first < length; first++) {

            if (nums[first] > 0) {
                // 如果第一个数已经大于 0 了，因为是已经排序过的数组，则没必要进行下面的循环了，不存在
                break;
            }

            // 如果 first 不是第一个数了，并且它的下一个和当前是一样的，则跳过，进入下一个循环;
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            // 第三个数 index 从数组的后面开始，
            int third = length - 1;
            // 第二个数，从 first + 1 开始进入第二轮循环
            for (int second = first + 1; second < length; second++) {
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    // 跳过本次的循环，进入下一个；目的：寻找和 second 不同的值；
                    continue;
                }
                //
                while (third > second && nums[second] + nums[third] > -nums[first]) {
                    // 只有 nums[second] + nums[third] > -nums[first] 了，后续才有可能找到 我们需要的值
                    third--;
                }
                // 表示 while 循环的退出是因为 third == second； 可返回了，因为没找到；
                if (second == third) {
                    break;
                }

                if (nums[second] + nums[third] + nums[first] == 0) {
                    // 说明我们找到了；
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    target.add(list);
                }
            }
        }

        return target;

    }

    // 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
    //
    //给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。; 2 对应字母：abc
    //
    //来源：力扣（LeetCode）
    //链接：https://leetcode.cn/problems/letter-combinations-of-a-phone-number
    //著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

    public List<String> letterCombinations(String digits) {

        List<String> combinations = new ArrayList<String>();
        if (digits.length() == 0) {
            return combinations;
        }
        Map<Character, String> phoneMap = new HashMap<Character, String>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};


        return null;
    }

    private void backtrack(List<String> combinations, Map<Character, String> phoneMap, String digits, int index, StringBuffer combination) {

        if (index == digits.length()) {
            combinations.add(combination.toString());
        } else {
            // 拿到数组对应的第 index 对应的数字
            char digit = digits.charAt(index);
            // 根据数字找到对应的 string 字母串
            String letters = phoneMap.get(digit);
            int length = letters.length();
            for (int i = 0; i < length; i++) {
                combination.append(letters.charAt(i));
                backtrack(combinations, phoneMap, digits, index + 1, combination);
                // 为什么要删除
                combination.deleteCharAt(index);
            }
        }

    }

    // 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
    //
    //有效字符串需满足：
    //
    //左括号必须用相同类型的右括号闭合。
    //左括号必须以正确的顺序闭合。
    //
    //来源：力扣（LeetCode）
    //链接：https://leetcode.cn/problems/valid-parentheses
    //著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

    public boolean isValid(String s) {

        // 奇数的话，则直接返回 false;
        if (s.length() % 2 == 1) {
            return false;
        }

        Map<Character, Character> map = new HashMap<>();
        map.put('}', '{');
        map.put(')', '(');
        map.put(']', '[');

        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char temp = s.charAt(i);
            if (map.containsKey(temp)) {
                // 如果包含上面的元素 key
                if (stack.isEmpty() || stack.peek() != map.get(temp)) {
                    // 如果此时栈为空，则表示在前面没有碰到与它配对的括号，返回 false;
                    // 如果栈不为空，且栈顶的值不等于 key 对应的 value 括号，则返回 false;
                    return false;
                }
                // 如果在栈里找到对应的 value 括号值，则把栈里面的值移除，通过了本次判断，进入下一轮；
                stack.pop();
            } else {
                // 如果不包含上面的元素，则加入进来栈里；
                stack.push(temp);
            }
        }

        return stack.isEmpty();
    }

    // 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
    public List<String> generateParenthesis(int n) {
        if (n <= 0) {
            return null;
        }

        List<String> list = new ArrayList<>();

        // 左括号，右括号，的最大数量为 n
        backBuild(list, "", n, n);

        return list;

    }

    private void backBuild(List<String> list, String str, int left, int right) {
        if (left == 0 && right == 0) {
            // 此时增加到了尽头，可加入列表中；
            list.add(str);
            return;
        }

        if (left == right) {
            // 剩余的左括号数量 == 右括号数量，则只能添加 (;
            backBuild(list, str + "(", left - 1, right);
        } else if (left < right) {
            //剩余左括号小于右括号，下一个可以用左括号也可以用右括号
            if (left > 0) {
                backBuild(list, str + "(", left - 1, right);
            }
            backBuild(list, str + ")", left, right - 1);
        }
    }

    // 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
    //
    //如果数组中不存在目标值 target，返回 [-1, -1]。
    //
    //你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
    //
    //来源：力扣（LeetCode）
    //链接：https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array
    //著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

    public int[] searchRange(int[] nums, int target) {
        int leftIndex = binarySearch(nums, target, true);
        int rightIndex = binarySearch(nums, target, false) - 1;

        if (leftIndex <= rightIndex && rightIndex < nums.length && nums[leftIndex] == nums[rightIndex] && nums[leftIndex] == target) {
            return new int[]{leftIndex, rightIndex};
        }

        return new int[]{-1, -1};
    }


    /**
     * @param nums
     * @param target
     * @param lower: 表示寻找小的那个数；
     * @return: 0
     */
    private int binarySearch(int[] nums, int target, boolean lower) {
        int left = 0;
        int right = nums.length - 1;
        int ans = nums.length;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] > target || (lower && nums[mid] >= target)) {
                right = mid - 1;
                ans = mid;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    // 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
    //
    //每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
    // 第 n 个台阶的方法数 =  第 n-1 个台阶的方法数 + 第 n-2 个台阶的方法数

    public int climbStairs(int n) {
        int n_1 = 0;
        int n_2 = 0;
        int n_target = 1;

        for (int i = 1; i <= n; i++) {
            n_2 = n_1;
            n_1 = n_target;
            n_target = n_1 + n_2;
        }

        return n_target;
    }

    //  给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        inOrder(root, list);
        return list;
    }

    private void inOrder(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }

        inOrder(root.left, res);
        res.add(root.val);
        inOrder(root.right, res);
    }

    // 定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
    //
    //你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
    //
    //
    //
    //来源：力扣（LeetCode）
    //链接：https://leetcode.cn/problems/rotate-image
    //著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

    public void rotate(int[][] matrix) {
        int n = matrix.length;
        // 分为两部分，先把前 n/2 行 和 后面的 n/2 行对称翻转；
        int temp = 0;
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < n; j++) {
                temp = matrix[i][j];
                matrix[i][j] = matrix[n - i - 1][j];
                matrix[n - i - 1][j] = temp;
            }
        }

        // 再按照对角线翻转；
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                matrix[i][j] = matrix[j][i] + matrix[i][j];
                matrix[j][i] = matrix[i][j] - matrix[j][i];
                matrix[i][j] = matrix[i][j] - matrix[j][i];
            }
        }
    }

//    public void zigzagLevelOrder(TreeNode root) {
//        ArrayList<Integer> list = new ArrayList<Integer>();
//        List<List<Integer>> list2 = new ArrayList<List<Integer>>();
//        Queue<TreeNode> queue = new LinkedList<TreeNode>();
//        Queue<TreeNode> queue2 = new LinkedList<TreeNode>();
//        if (root == null) {
//            return;
//        }
//        queue.offer(root);
//        boolean tag = true;
//        while(!queue.isEmpty() || !queue2.isEmpty()) {
//            if (queue.isEmpty()) {
//                tag = true;
//            }
//
//            if (queue2.isEmpty()) {
//                tag = false;
//            }
//            TreeNode node;
//            if (tag) {
//                node = queue2.poll();
//            } else {
//                node = queue.poll();
//            }
//            if(node == null){
//                break;
//            }
//
//            if (node != null) {
//                list.add(node.val);
////                temp.add(node.val);
//            }
//
//            if (tag) {
//                if (node.left != null) {
//                    queue.offer(node.left);
//                }
//                if (node.right != null) {
//                    queue.offer(node.right);
//                }
//            } else {
//                if (node.right != null) {
//                    queue2.offer(node.right);
//                }
//                if (node.left != null) {
//                    queue2.offer(node.left);
//                }
//            }
//        }
//
//
//
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
//    }


    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        // 使用两个 queue 是实现不了的；当遍历第一个队列时，第二个队列的加入顺序是会出问题的；
        // 可以使用 Deque 来实现；
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        if (root == null) {
            return list;
        }
        queue.offer(root);
        int indexLevel = 0;
        int size = 0;
        TreeNode temp;
        while(!queue.isEmpty()) {
            size = queue.size();
            // 这里的数据保存列表使用 Deque 来实现；让是
            Deque<Integer> levelList = new LinkedList<Integer>();
            for (int i = 0; i < size; i++) {
                temp = queue.poll();
                // 这里 书节点的 queue 中，总是从左到右出数据；
                // 而存放数据的队列,是按照 FIFO 的方式出队列的，因此，这里要把它先加入，才能保持从左到右出数据
                if (indexLevel % 2 == 0) {
                    //  添加到后面；
                    levelList.offerLast(temp.val);
                } else {
                    // 添加到前面
                    levelList.offerFirst(temp.val);
                }
                if (temp.left != null) {
                    queue.offer(temp.left);
                }
                if (temp.right != null) {
                    queue.offer(temp.right);
                }
            }
            indexLevel++;
            list.add(new LinkedList<Integer>(levelList));
        }

        return list;
    }


    public static void main(String[] args) {
        MainAlgo algo = new MainAlgo();

//        TreeNode root = new TreeNode(1);
//        TreeNode left1 = new TreeNode(2);
//        TreeNode right1 = new TreeNode(3);
//        TreeNode left2 = new TreeNode(4);
//        TreeNode left3 = new TreeNode(5);
////        TreeNode right2 = new TreeNode(6);
//        TreeNode right3 = new TreeNode(7);
//        root.right =  right1;
//        root.left = left1;
//        right1.left = left3;
//        right1.right = right3;
//        left1.left = left2;
//        algo.zigzagLevelOrder(root);
//        String test = "enene";
//        // 大写
//        String.valueOf(test.charAt(0)).toUpperCase();
//        // 小写
//        String.valueOf(test.charAt(0)).toLowerCase();

        System.out.println("结果为：" + algo.testString("cololo", "xxxxxx"));
        System.out.println("结果为：" + algo.testString("cololo", "abcbcb"));
        System.out.println("结果为：" + algo.testString("cololo", "derere"));
        System.out.println("结果为：" + algo.testString("cololo", "cololo"));
        System.out.println("结果为：" + algo.testString("cololo", "abcene"));
        System.out.println("结果为：" + algo.testString("xxxxxx", "cololo"));
        // 反馈
    }

    public boolean testString(String targetString, String testString) {
        if (targetString == null) {
            return testString == null;
        }
        if (testString == null) {
            return false;
        }
        if (targetString.length() != testString.length()) {
            return false;
        }
        char[] targetList = targetString.toCharArray();
        char[] tempList = testString.toCharArray();
        HashMap<Character, Character> hashMap = new HashMap<Character, Character>();
        for (int i = 0; i < targetList.length; i++) {
            if (hashMap.containsKey(targetList[i])) {
                char temp = hashMap.get(targetList[i]);
                if (temp != tempList[i]) {
                    return false;
                }
            } else {
                if (hashMap.containsValue(tempList[i])) {
                    return false;
                } else {
                    hashMap.put(targetList[i], tempList[i]);
                }
            }
        }
        return true;
    }


    public String test(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
//        s.toLowerCase();
//        s.toUpperCase();
        // 存放每个字符的次数；
        HashMap<Character, Integer> target = new HashMap();
        char[] charList = s.toCharArray();
        for (int i = 0; i < charList.length; i++) {
            if (target.containsKey(charList[i])) {
                target.put(charList[i], target.get(charList[i]) + 1);
            } else {
                target.put(charList[i], 1);
            }
        }

        int[] test2 = new int[24];
        String[] test3 = new String[12];

        // 排序；
        for (int i = 0; i < charList.length; i++) {
            // i 比后面的小；
            int right = charList.length - 1;
            while (right >= 0 && right < charList.length) {
                if (charList[i] - charList[right] > 0) {
                    char temp = charList[right];
                    charList[right] = charList[i];
                    charList[i] = temp;
                }
                right--;
            }
        }
        int sortSize = 0;
        for (int i = 0; i < charList.length - 1; i++) {
            if (charList[i] == charList[i + 1]) {
                break;
            }
            sortSize++;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sortSize; i++) {
            builder.append(charList[i] + target.get(charList[i]));
        }

        return builder.toString();
    }

    public int testIP(String address) {

        String[] list = address.split("\\.");

        return 0;
    }


}
