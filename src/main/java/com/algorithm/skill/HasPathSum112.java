/**
 * algorithm 版权声明
 * Copyright (c) 2019, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20190211    gaoyouan     初始版本
 */
package com.algorithm.skill;

import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @brief
 *    TODO 类功能作用及实现逻辑说明
 *路径节点和
 * @see
 *
 */
public class HasPathSum112 {
    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }

    public boolean hasPathSum(TreeNode root, int sum) {
        if(root==null){
            return false;
        }
        if(root.val==sum && root.left==null && root.right==null){
            return true;
        }
        return hasPathSum( root.left,sum-root.val ) || hasPathSum( root.right,sum-root.val );
    }


    /**
     * 杨辉三角
     * @param numRows
     * @return
     */
    public List< List<Integer> > generate( int numRows) {
        List< List<Integer> >  res=new ArrayList<>();
        for(int i=0;i<numRows;i++){
            List<Integer> list=new ArrayList<>();
            int len=i+1;
            list.add( 1 );
            if(i>1){
                List<Integer> preList=res.get( i-1 );
                for ( int j=1;j<len-1;j++ ){
                    list.add( preList.get( j-1 )+preList.get( j ) );
                }
            }
            if(i>0){
                list.add( 1 );
            }
            res.add( list );
        }
        return res;
    }

    public char nextGreatestLetter(char[] letters, char target) {
        int len=letters.length;
        if(letters[len-1]<=target){
            return letters[0];
        }
        int start=0,end=len-1;
        int mid=0;
        while ( start<end ){
            mid=(start+end)/2;
            if(letters[mid]>target){
                end=mid-1;
            }else if(letters[mid]==target){
                //寻找到下一个字符
                for(int i=mid+1;i<end;i++){
                    if(letters[i]>target){
                        return letters[i];
                    }
                }
                start=mid+1;
            }else {
                start=mid+1;
            }
        }
        return letters[mid];
    }

    public static String shortestCompletingWord( String licensePlate, String[] words ) {
        //提取英文字母
        licensePlate = licensePlate.replaceAll( "[^a-zA-Z]", "" );
        //map存储字母个数
        Map< Character, Integer > map = transferMap( licensePlate.toLowerCase() );
        int len = 15, index = -1;
        //需要先排序
        Arrays.sort( words, new Comparator< String >() {
            @Override
            public int compare( String o1, String o2 ) {
                return o1.length()==o2.length() ? 0:(o1.length()>o2.length()?1:-1);
            }
        } );
        c:for ( int i = 0; i < words.length; i++ ) {
            if(words[i].length()>=licensePlate.length()){
                Map< Character, Integer > mps = transferMap( words[ i ] );
                //对比
                for ( Character c : map.keySet() ) {
                    if ( map.get( c ) > mps.getOrDefault( c, 0 ) ) {
                        continue c;
                    }
                }
                index = i;
                break c;
            }

        }
        return words[ index ];
    }

    public static Map<Character,Integer> transferMap(String str){
        char[] chars=str.toCharArray();
        Map<Character,Integer> map=new HashMap<>(  );
        for(int i=0;i<chars.length;i++){
            map.put( chars[i],map.getOrDefault(chars[i],0)+1 );
        }
        return map;
    }


    public static int reachNumber(int target) {
        //一直右移，三种情况
        //刚好到
        //差一点到:一左一右，两次移动前进一
        //刚好过：一右一左，两次移动后退一

        //不对
        //一左一右效率太低了，其实可以计算多出来的值rest，如果是偶数，那只需要将先前的rest/2反向；如果是奇数(rest-1)/2反向并多做一次一              //左一右操作也是可以的，但这样不一定最优，如果再走一步，这时候rest为偶数，那就可以将之前reset/2反向即可，多走一步
        //正数还是负数都一样
        target=Math.abs(target);
        int res=0;
        int sum=0;
        while(sum<target){
            res++;
            sum+=res;
        }
        if(sum==target){
            return res;
        }
        int rest=sum-target;
        if((rest & 1)==0){
            return res;
        }else{
            //分情况:
            if((res & 1) ==0){
                return res+1;
            }
            return res+2;
        }
    }

    /**
     * 给定两个整数 L 和 R ，找到闭区间 [L, R] 范围内，计算置位位数为质数的整数个数。
     *
     * （注意，计算置位代表二进制表示中1的个数。例如 21 的二进制表示 10101 有 3 个计算置位。还有，1 不是质数。）
     * 注意:
     *
     * L, R 是 L <= R 且在 [1, 10^6] 中的整数。
     * R - L 的最大值为 10000。
     * @param L
     * @param R
     * @return   my shadiao friend
     */
    public static int countPrimeSetBits(int L, int R) {
        //为了尽量少进行质数判断，将出现的位数判断结果放进数组存起来，0-为需要判断，1-质数，-1-非质数
        int arr[]=new int[32];
        arr[0]=arr[1]=-1;
        arr[2]=arr[3]=1;
        int res=0;
        for(int i=L;i<=R;i++){
            int len=0;
            int temp=i;
            while ( temp>0 ){
                if((temp & 1)==1){
                    len++;
                }
                temp>>=1;
            }
            if(arr[len]==1){
                res++;
            }else if(arr[len]==0){
                if(isPrime( len )){
                    arr[len]=1;
                    res++;
                }else {
                    arr[len]=-1;
                }
            }
        }
        return res;
    }

    //>3的数进行才进行判断
    public static boolean isPrime(int i){
        int temp=(int ) Math.floor( Math.sqrt( i ));
        for(int j=2;j<=temp;j++){
            if(i%j==0){
                return false;
            }
        }
        return true;
    }

    /**
     * 如果一个矩阵的每一方向由左上到右下的对角线上具有相同元素，那么这个矩阵是托普利茨矩阵。
     *
     * 给定一个 M x N 的矩阵，当且仅当它是托普利茨矩阵时返回 True。
     *
     * 示例 1:
     *
     * 输入:
     * matrix = [
     *   [1,2,3,4],
     *   [5,1,2,3],
     *   [9,5,1,2]
     * ]
     * 输出: True
     * 解释:
     * 在上述矩阵中, 其对角线为:
     * "[9]", "[5, 5]", "[1, 1, 1]", "[2, 2, 2]", "[3, 3]", "[4]"。
     * 各条对角线上的所有元素均相同, 因此答案是True。
     *
     * 说明:
     *
     *  matrix 是一个包含整数的二维数组。
     * matrix 的行数和列数均在 [1, 20]范围内。
     * matrix[i][j] 包含的整数在 [0, 99]范围内。
     * 进阶:
     *
     * 如果矩阵存储在磁盘上，并且磁盘内存是有限的，因此一次最多只能将一行矩阵加载到内存中，该怎么办？
     * 如果矩阵太大以至于只能一次将部分行加载到内存中，该怎么办？
     * @param matrix
     * @return
     */
    public static boolean isToeplitzMatrix(int[][] matrix) {
        //一行一行进行遍历:W*H 最多有E+H-1 对角线
        int H=matrix.length;
        int W=matrix[0].length;
        int [] arr=new int[W+H-1];
        for(int i=0;i<H;i++){
            if(i==0){
                for(int j=0;j<W;j++){
                    arr[j+H-1]=matrix[i][j];
                }
            }else {
                //先比较从索引1开始的的数是否与上一层对的上
                for(int j=1;j<W;j++){
                    if(arr[j-i+H-1]!=matrix[i][j]){
                        return false;
                    }
                }
                //再将最左的值加入arr
                arr[H-1-i]=matrix[i][0];
            }
        }
        return true;
    }

    /**
     * 一个字符串S，通过将字符串S中的每个字母转变大小写，我们可以获得一个新的字符串。返回所有可能得到的字符串集合。
     * S 的长度不超过12。
     * S 仅由数字和字母组成。
     * @param S
     * @return
     */
    public List<String> letterCasePermutation(String S) {
        /**
         回溯法, 遇到字母分大小写情况回溯, 遇到非字母直接回溯
         **/
        List<String> ret = new ArrayList<>();
        bktrace(S.toCharArray(), ret, new StringBuilder(), 0);
        return ret;
    }

    private void bktrace(char[] s, List<String> ret, StringBuilder sb, int i) {
        if(i == s.length) {
            ret.add(sb.toString());
            return;
        }
        char c = s[i];
        if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            sb.append(Character.toLowerCase(c));
            bktrace(s, ret, sb, i+1);
            sb.deleteCharAt(sb.length()-1);

            sb.append(Character.toUpperCase(c));
            bktrace(s, ret, sb, i+1);
            sb.deleteCharAt(sb.length()-1);
        }
        else {
            sb.append(c);
            bktrace(s, ret, sb, i+1);
            sb.deleteCharAt(sb.length()-1);
        }
    }

    /**
     * 我们称一个数 X 为好数, 如果它的每位数字逐个地被旋转 180 度后，我们仍可以得到一个有效的，且和 X 不同的数。要求每位数字都要被旋转。
     *
     * 如果一个数的每位数字被旋转以后仍然还是一个数字， 则这个数是有效的。0, 1, 和 8 被旋转后仍然是它们自己；2 和 5 可以互相旋转成对方；6 和 9 同理，除了这些以外其他的数字旋转以后都不再是有效的数字。
     * 0,1,2,5,6,8,9  1.存在3,4,7直接false 2.存在2，5，6，9 直接true
     * 现在我们有一个正整数 N, 计算从 1 到 N 中有多少个数 X 是好数？
     *
     * 示例:
     * 输入: 10
     * 输出: 4
     * 解释:
     * 在[1, 10]中有四个好数： 2, 5, 6, 9。
     * 注意 1 和 10 不是好数, 因为他们在旋转之后不变。
     * 注意:
     *
     * N 的取值范围是 [1, 10000]。
     * @param N
     * @return
     */
    public int rotatedDigits(int N) {
        int res=0;
        for ( int i=1;i<=N;i++ ){
            String str=String.valueOf( i );
            if(str.indexOf( "3" )>-1 || str.indexOf( "4" )>-1 || str.indexOf( "7" )>-1 ){
                continue;
            }
            if(str.indexOf( "2" )>-1 || str.indexOf( "5" )>-1 || str.indexOf( "6" )>-1 || str.indexOf( "9" )>-1){
                res++;
            }
        }
        return res;
    }

    /**
     * 给定两个字符串, A 和 B。
     *
     * A 的旋转操作就是将 A 最左边的字符移动到最右边。 例如, 若 A = 'abcde'，在移动一次之后结果就是'bcdea' 。如果在若干次旋转操作之后，A 能变成B，那么返回True。
     *
     * 示例 1:
     * 输入: A = 'abcde', B = 'cdeab'
     * 输出: true
     *
     * 示例 2:
     * 输入: A = 'abcde', B = 'abced'
     * 输出: false
     * 注意：
     *
     * A 和 B 长度不超过 100。
     * @param A
     * @param B
     * @return
     */
    public boolean rotateString(String A, String B) {
        return A.length()==B.length() && (A+A).indexOf( B )>-1;
    }

    /**
     * 我们要把给定的字符串 S 从左到右写到每一行上，每一行的最大宽度为100个单位，如果我们在写某个字母的时候会使这行超过了100 个单位，那么我们应该把这个字母写到下一行。我们给定了一个数组 widths ，这个数组 widths[0] 代表 'a' 需要的单位， widths[1] 代表 'b' 需要的单位，...， widths[25] 代表 'z' 需要的单位。
     *
     * 现在回答两个问题：至少多少行能放下S，以及最后一行使用的宽度是多少个单位？将你的答案作为长度为2的整数列表返回。
     *
     * 示例 1:
     * 输入:
     * widths = [10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10]
     * S = "abcdefghijklmnopqrstuvwxyz"
     * 输出: [3, 60]
     * 解释:
     * 所有的字符拥有相同的占用单位10。所以书写所有的26个字母，
     * 我们需要2个整行和占用60个单位的一行。
     * 示例 2:
     * 输入:
     * widths = [4,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10]
     * S = "bbbcccdddaaa"
     * 输出: [2, 4]
     * 解释:
     * 除去字母'a'所有的字符都是相同的单位10，并且字符串 "bbbcccdddaa" 将会覆盖 9 * 10 + 2 * 4 = 98 个单位.
     * 最后一个字母 'a' 将会被写到第二行，因为第一行只剩下2个单位了。
     * 所以，这个答案是2行，第二行有4个单位宽度。
     *
     *
     * 注:
     *
     * 字符串 S 的长度在 [1, 1000] 的范围。
     * S 只包含小写字母。
     * widths 是长度为 26的数组。
     * widths[i] 值的范围在 [2, 10]。
     * @param widths
     * @param S
     * @return
     */
     public int[] numberOfLines(int[] widths, String S) {
        int res=0;
        int sum=0;
        for(int i=0;i<S.length();i++){
            if(sum+widths[S.charAt( i )-'a']>100){
                res++;
                sum=0;
            }
            sum+=widths[S.charAt( i )-'a'];
        }
        return new int[]{res+1,sum};
     }

    /**
     * 一个网站域名，如"discuss.leetcode.com"，包含了多个子域名。作为顶级域名，常用的有"com"，下一级则有"leetcode.com"，最低的一级为"discuss.leetcode.com"。当我们访问域名"discuss.leetcode.com"时，也同时访问了其父域名"leetcode.com"以及顶级域名 "com"。
     *
     * 给定一个带访问次数和域名的组合，要求分别计算每个域名被访问的次数。其格式为访问次数+空格+地址，例如："9001 discuss.leetcode.com"。
     *
     * 接下来会给出一组访问次数和域名组合的列表cpdomains 。要求解析出所有域名的访问次数，输出格式和输入格式相同，不限定先后顺序。
     *
     * 示例 1:
     * 输入:
     * ["9001 discuss.leetcode.com"]
     * 输出:
     * ["9001 discuss.leetcode.com", "9001 leetcode.com", "9001 com"]
     * 说明:
     * 例子中仅包含一个网站域名："discuss.leetcode.com"。按照前文假设，子域名"leetcode.com"和"com"都会被访问，所以它们都被访问了9001次。
     * 示例 2
     * 输入:
     * ["900 google.mail.com", "50 yahoo.com", "1 intel.mail.com", "5 wiki.org"]
     * 输出:
     * ["901 mail.com","50 yahoo.com","900 google.mail.com","5 wiki.org","5 org","1 intel.mail.com","951 com"]
     * 说明:
     * 按照假设，会访问"google.mail.com" 900次，"yahoo.com" 50次，"intel.mail.com" 1次，"wiki.org" 5次。
     * 而对于父域名，会访问"mail.com" 900+1 = 901次，"com" 900 + 50 + 1 = 951次，和 "org" 5 次。
     * 注意事项：
     *
     *  cpdomains 的长度小于 100。
     * 每个域名的长度小于100。
     * 每个域名地址包含一个或两个"."符号。
     * 输入中任意一个域名的访问次数都小于10000。
     * HashMap存储域名及次数
     * @param cpdomains
     * @return
     */
    public List<String> subdomainVisits(String[] cpdomains) {
        HashMap<String,Integer> map=new HashMap<>(  );
        for(String string:cpdomains){
            String[] strs=string.split( " " );
            int count=Integer.valueOf( strs[0] );
            String str=strs[1];
            map.put( str,map.getOrDefault( str , 0)+count );
            if(str.indexOf( "." )>-1){
                str=str.substring( str.indexOf( "." ) + 1 );
                map.put( str,map.getOrDefault( str , 0)+count );
                if(str.indexOf( "." )>-1){
                    str=str.substring( str.indexOf( "." ) + 1 );
                    map.put( str,map.getOrDefault( str , 0)+count );
                }
            }
        }
        List<String> res=new ArrayList<>(  );
        for(String key:map.keySet()){
            res.add( ""+map.get( key )+" "+key );
        }
        return res;
    }

    /**
     * 给定包含多个点的集合，从其中取三个点组成三角形，返回能组成的最大三角形的面积。
     *
     * 示例:
     * 输入: points = [[0,0],[0,1],[1,0],[0,2],[2,0]]
     * 输出: 2
     * 解释:
     * 这五个点如下图所示。组成的橙色三角形是最大的，面积为2。
     *
     *
     * 注意:
     *
     * 3 <= points.length <= 50.
     * 不存在重复的点。
     *  -50 <= points[i][j] <= 50.
     * 结果误差值在 10^-6 以内都认为是正确答案。
     * 坐标系中的三角形公式
     * Sabc=Saob+Saoc+Sboc=(XaYb+XbYc+XcYa-XaYc-XbYa-XcYb)/2
     * @param points
     * @return
     */
    public double largestTriangleArea(int[][] points) {
        double area = 0;
        for (int[] a : points) {
            for (int[] b : points) {
                for (int[] c : points) {
                    area = Math.max(area, 0.5 * Math.abs(a[0] * b[1] + b[0] * c[1] + c[0] * a[1] - a[0] * c[1] - b[0] * a[1] - c[0] * b[1]));
                }
            }
        }
        return area;
    }

    /**
     * 给定一个段落 (paragraph) 和一个禁用单词列表 (banned)。返回出现次数最多，同时不在禁用列表中的单词。题目保证至少有一个词不在禁用列表中，而且答案唯一。
     *
     * 禁用列表中的单词用小写字母表示，不含标点符号。段落中的单词不区分大小写。答案都是小写字母。
     *
     *
     *
     * 示例：
     *
     * 输入:
     * paragraph = "Bob hit a ball, the hit BALL flew far after it was hit."
     * banned = ["hit"]
     * 输出: "ball"
     * 解释:
     * "hit" 出现了3次，但它是一个禁用的单词。
     * "ball" 出现了2次 (同时没有其他单词出现2次)，所以它是段落里出现次数最多的，且不在禁用列表中的单词。
     * 注意，所有这些单词在段落里不区分大小写，标点符号需要忽略（即使是紧挨着单词也忽略， 比如 "ball,"），
     * "hit"不是最终的答案，虽然它出现次数更多，但它在禁用单词列表中。
     *
     *
     * 说明：
     *
     * 1 <= 段落长度 <= 1000.
     * 1 <= 禁用单词个数 <= 100.
     * 1 <= 禁用单词长度 <= 10.
     * 答案是唯一的, 且都是小写字母 (即使在 paragraph 里是大写的，即使是一些特定的名词，答案都是小写的。)
     * paragraph 只包含字母、空格和下列标点符号!?',;.
     * 不存在没有连字符或者带有连字符的单词。
     * 单词里只包含字母，不会出现省略号或者其他标点符号。
     * @param paragraph
     * @param banned
     * @return
     */
    public static String mostCommonWord(String paragraph, String[] banned) {
        Set< String > set = new HashSet<>();
        Map< String, Integer > map = new HashMap<>();
        for ( String str : banned ) {
            set.add( str );
        }
        String[] strs =paragraph.toLowerCase().split(" |!|\\?|'|,|;|\\.");
        int count = 0;
        String res = "";
        for ( String str : strs ) {
            if ( str.length() > 0 && !set.contains( str ) ) {
                map.put( str, map.getOrDefault( str, 0 ) + 1 );
                if ( map.get( str ) > count ) {
                    count = map.get( str );
                    res = str;
                }
            }
        }
        return res;
    }

    /**
     * 给定一个字符串 S 和一个字符 C。返回一个代表字符串 S 中每个字符到字符串 S 中的字符 C 的最短距离的数组。
     *
     * 示例 1:
     *
     * 输入: S = "loveleetcode", C = 'e'
     * 输出: [3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0]
     * 说明:
     *
     * 字符串 S 的长度范围为 [1, 10000]。
     * C 是一个单字符，且保证是字符串 S 里的字符。
     * S 和 C 中的所有字母均为小写字母。
     * @param S
     * @param C
     * @return
     */
    public int[] shortestToChar(String S, char C) {
        //先找到C的索引
        char[] chars=S.toCharArray();
        List<Integer> list=new ArrayList<>(  );
        for ( int i=0;i<chars.length;i++ ){
            if(chars[i]==C){
                list.add( i );
            }
        }
        int[] res=new int[S.length()];
        int index=0,maxIndex=list.size();
        for(int i=0;i<S.length();i++){
            if(i>list.get( index ) && index<maxIndex){
                index++;
            }
            res[i]=Math.min( Math.abs( i-list.get( index ) ),index>0?Math.abs( i-list.get( index-1 ) ):Integer.MAX_VALUE );
        }
        return res;
    }

    /**
     * 给定一个由空格分割单词的句子 S。每个单词只包含大写或小写字母。
     *
     * 我们要将句子转换为 “Goat Latin”（一种类似于 猪拉丁文 - Pig Latin 的虚构语言）。
     *
     * 山羊拉丁文的规则如下：
     *
     * 如果单词以元音开头（a, e, i, o, u），在单词后添加"ma"。
     * 例如，单词"apple"变为"applema"。
     *
     * 如果单词以辅音字母开头（即非元音字母），移除第一个字符并将它放到末尾，之后再添加"ma"。
     * 例如，单词"goat"变为"oatgma"。
     *
     * 根据单词在句子中的索引，在单词最后添加与索引相同数量的字母'a'，索引从1开始。
     * 例如，在第一个单词后添加"a"，在第二个单词后添加"aa"，以此类推。
     * 返回将 S 转换为山羊拉丁文后的句子。
     *
     * 示例 1:
     *
     * 输入: "I speak Goat Latin"
     * 输出: "Imaa peaksmaaa oatGmaaaa atinLmaaaaa"
     * 示例 2:
     *
     * 输入: "The quick brown fox jumped over the lazy dog"
     * 输出: "heTmaa uickqmaaa rownbmaaaa oxfmaaaaa umpedjmaaaaaa overmaaaaaaa hetmaaaaaaaa azylmaaaaaaaaa ogdmaaaaaaaaaa"
     *
     * @param S
     * @return
     */
    public String toGoatLatin(String S) {
        String[] strs=S.split( " " );
        StringBuffer sb=new StringBuffer(  );
        for(int i=0;i<strs.length;i++){
            if(strs[i].startsWith( "a" ) || strs[i].startsWith( "e" ) ||strs[i].startsWith( "i" ) ||
                    strs[i].startsWith( "o" ) ||strs[i].startsWith( "u" ) ||
                    strs[i].startsWith( "A" ) || strs[i].startsWith( "E" ) ||strs[i].startsWith( "I" ) ||
                    strs[i].startsWith( "O" ) ||strs[i].startsWith( "U" )){
                sb.append( strs[i] );
            }else {
                sb.append( strs[i].substring( 1 ) ).append( strs[i].charAt( 0 ) );
            }
            sb.append( "ma" );
            for(int j=-1;j<i;j++){
                sb.append( "a" );
            }
            sb.append( " " );
        }
        return sb.deleteCharAt( sb.length()-1 ).toString();
    }

    /**
     * 在一个由小写字母构成的字符串 S 中，包含由一些连续的相同字符所构成的分组。
     *
     * 例如，在字符串 S = "abbxxxxzyy" 中，就含有 "a", "bb", "xxxx", "z" 和 "yy" 这样的一些分组。
     *
     * 我们称所有包含大于或等于三个连续字符的分组为较大分组。找到每一个较大分组的起始和终止位置。
     *
     * 最终结果按照字典顺序输出。
     * 要么看不上，要么攀不上
     * 示例 1:
     *
     * 输入: "abbxxxxzzy"
     * 输出: [[3,6]]
     * 解释: "xxxx" 是一个起始于 3 且终止于 6 的较大分组。
     * 示例 2:
     *
     * 输入: "abc"
     * 输出: []
     * 解释: "a","b" 和 "c" 均不是符合要求的较大分组。
     * 示例 3:
     *
     * 输入: "abcdddeeeeaabbbcd"
     * 输出: [[3,5],[6,9],[12,14]]
     * 说明:  1 <= S.length <= 1000
     * @param S
     * @return
     */
    public static List<List<Integer>> largeGroupPositions(String S) {
        List<List<Integer>> res=new ArrayList<>(  );
        char[] chars=S.toCharArray();
        int count=1;

        for(int i=1;i<S.length();i++){
                if(chars[i]==chars[i-1]){
                    count++;
                    if(i==S.length()-1){
                        if(count>=3){
                            List<Integer> list=new ArrayList<>(  );
                            list.add( i-count+1 );
                            list.add( i );
                            res.add( list );
                        }
                    }
                }else {
                    if(count>=2){
                        List<Integer> list=new ArrayList<>(  );
                        list.add( i-count-1 );
                        list.add( i-1 );
                        res.add( list );
                    }
                    count=1;
                }

        }
        return res;
    }

    /**
     * 给定一个二进制矩阵 A，我们想先水平翻转图像，然后反转图像并返回结果。
     *
     * 水平翻转图片就是将图片的每一行都进行翻转，即逆序。例如，水平翻转 [1, 1, 0] 的结果是 [0, 1, 1]。
     *
     * 反转图片的意思是图片中的 0 全部被 1 替换， 1 全部被 0 替换。例如，反转 [0, 1, 1] 的结果是 [1, 0, 0]。
     *
     * 示例 1:
     *
     * 输入: [[1,1,0],[1,0,1],[0,0,0]]
     * 输出: [[1,0,0],[0,1,0],[1,1,1]]
     * 解释: 首先翻转每一行: [[0,1,1],[1,0,1],[0,0,0]]；
     *      然后反转图片: [[1,0,0],[0,1,0],[1,1,1]]
     * 示例 2:
     *
     * 输入: [[1,1,0,0],[1,0,0,1],[0,1,1,1],[1,0,1,0]]
     * 输出: [[1,1,0,0],[0,1,1,0],[0,0,0,1],[1,0,1,0]]
     * 解释: 首先翻转每一行: [[0,0,1,1],[1,0,0,1],[1,1,1,0],[0,1,0,1]]；
     *      然后反转图片: [[1,1,0,0],[0,1,1,0],[0,0,0,1],[1,0,1,0]]
     * 说明:
     *
     * 1 <= A.length = A[0].length <= 20
     * 0 <= A[i][j] <= 1
     * @param A
     * @return
     */
    public int[][] flipAndInvertImage(int[][] A) {
        for(int i=0;i<A.length;i++){
            reverseArray(A[i]);
        }
        return A;
    }

    public void reverseArray(int[] arr){
        int start=0,end=arr.length-1;
        while ( start<end ){
            int temp=arr[start];
            arr[start]=arr[end];
            arr[end]=temp;
            start++;
            end--;
        }
        for(int i=0;i<arr.length;i++){
            if(arr[i]==0){
                arr[i]=1;
            }else {
                arr[i]=0;
            }
        }
    }

    /**
     * 给定 S 和 T 两个字符串，当它们分别被输入到空白的文本编辑器后，判断二者是否相等，并返回结果。 # 代表退格字符。
     *
     *
     *
     * 示例 1：
     *
     * 输入：S = "ab#c", T = "ad#c"
     * 输出：true
     * 解释：S 和 T 都会变成 “ac”。
     * 示例 2：
     *
     * 输入：S = "ab##", T = "c#d#"
     * 输出：true
     * 解释：S 和 T 都会变成 “”。
     * 示例 3：
     *
     * 输入：S = "a##c", T = "#a#c"
     * 输出：true
     * 解释：S 和 T 都会变成 “c”。
     * 示例 4：
     *
     * 输入：S = "a#c", T = "b"
     * 输出：false
     * 解释：S 会变成 “c”，但 T 仍然是 “b”。
     *
     *
     * 提示：
     *
     * 1 <= S.length <= 200
     * 1 <= T.length <= 200
     * S 和 T 只含有小写字母以及字符 '#'。
     * @param S
     * @param T
     * @return
     */
     public boolean backspaceCompare(String S, String T) {
        //用栈存储
         return transferStr(S).equals( transferStr( T ) );
     }

     public String transferStr(String str){
         char[] chars=str.toCharArray();
         Stack<Character> stack=new Stack<>();
         for(int i=0;i<chars.length;i++){
             if(chars[i]=='#'){
                 if(!stack.isEmpty()){
                     stack.pop();
                 }
             }else {
                 stack.push( chars[i] );
             }
         }
         StringBuffer sb=new StringBuffer(  );
         while ( !stack.isEmpty() ){
             sb.append( stack.pop() );
         }
         return sb.toString();
     }

    /**
     * 在一排座位（ seats）中，1 代表有人坐在座位上，0 代表座位上是空的。
     *
     * 至少有一个空座位，且至少有一人坐在座位上。
     *
     * 亚历克斯希望坐在一个能够使他与离他最近的人之间的距离达到最大化的座位上。
     *
     * 返回他到离他最近的人的最大距离。
     *
     * 示例 1：
     *
     * 输入：[1,0,0,0,1,0,1]
     * 输出：2
     * 解释：
     * 如果亚历克斯坐在第二个空位（seats[2]）上，他到离他最近的人的距离为 2 。
     * 如果亚历克斯坐在其它任何一个空位上，他到离他最近的人的距离为 1 。
     * 因此，他到离他最近的人的最大距离是 2 。
     * 示例 2：
     *
     * 输入：[1,0,0,0]
     * 输出：3
     * 解释：
     * 如果亚历克斯坐在最后一个座位上，他离最近的人有 3 个座位远。
     * 这是可能的最大距离，所以答案是 3 。
     * 提示：
     *
     * 1 <= seats.length <= 20000
     * seats 中只含有 0 和 1，至少有一个 0，且至少有一个 1。
     *
     *
     * 解法1：挨个计算大小
     * 解法2：找出最长连续0的数目，注意首尾和中间
     * @param seats
     * @return
     */
    public int maxDistToClosest(int[] seats) {
        //先找出所有1的索引
        List<Integer> list=new ArrayList<>(  );
        for(int i=0;i<seats.length;i++){
            if(seats[i]==1){
                list.add( i );
            }
        }
        int curIndex=0,maxIndex=list.size()-1;
        int res=Integer.MIN_VALUE;
        for(int i=0;i<seats.length;i++){
            if(i<list.get( 0 )){
                res=Math.max( res,list.get( 0 ) );
                continue;
            }
            if(i>list.get( curIndex ) &&curIndex<maxIndex){
                curIndex++;
            }
            res=Math.max( res,curIndex>0?Math.min(Math.abs( list.get( curIndex )-i ),Math.abs( list.get( curIndex-1 )-i )):Math.abs( list.get( curIndex )-i ));
        }
        return res;
    }

    /**
     * 给定两个由小写字母构成的字符串 A 和 B ，只要我们可以通过交换 A 中的两个字母得到与 B 相等的结果，就返回 true ；否则返回 false 。
     * 示例 1：
     *
     * 输入： A = "ab", B = "ba"
     * 输出： true
     * 示例 2：
     *
     * 输入： A = "ab", B = "ab"
     * 输出： false
     * 示例 3:
     *
     * 输入： A = "aa", B = "aa"
     * 输出： true
     * 示例 4：
     *
     * 输入： A = "aaaaaaabc", B = "aaaaaaacb"
     * 输出： true
     * 示例 5：
     *
     * 输入： A = "", B = "aa"
     * 输出： false
     *
     *
     * 提示：
     *
     * 0 <= A.length <= 20000
     * 0 <= B.length <= 20000
     * A 和 B 仅由小写字母构成。
     *
     * 直接比较找出不同的字符索引，数目不等于2 直接false，再交换索引对应值
     * @param A
     * @param B
     * @return
     */
    public boolean buddyStrings(String A, String B) {
        if(A.length()!=B.length()){
            return false;
        }
        //这破题意思是必须进行一次字符交换，所以如果俩字符串相同，则需要找出是否存在重复字符
        if(A.equals( B )){
            int[] chs=new int[26];
            for(int i=0;i<A.length();i++){
                chs[A.charAt( i )-'a']++;
            }
            for(int i=0;i<chs.length;i++){
                if(chs[i]>1){
                    return true;
                }
            }
        }else {
            int count=0,leftIndex=0,rightIndex=0;

            for(int i=0;i<A.length();i++){
                if(A.charAt( i )!=B.charAt( i )){
                    count++;
                    if(count>2){
                        return false;
                    }
                    if(count==1){
                        leftIndex=i;
                    }
                    if(count==2){
                        rightIndex=i;
                    }
                }
            }
            if(count!=2){
                return false;
            }
            if(A.charAt( leftIndex )==B.charAt( rightIndex ) && A.charAt( rightIndex )==B.charAt( leftIndex )){
                return true;
            }
        }

        return false;
    }

    /**
     * 在柠檬水摊上，每一杯柠檬水的售价为 5 美元。
     *
     * 顾客排队购买你的产品，（按账单 bills 支付的顺序）一次购买一杯。
     *
     * 每位顾客只买一杯柠檬水，然后向你付 5 美元、10 美元或 20 美元。你必须给每个顾客正确找零，也就是说净交易是每位顾客向你支付 5 美元。
     *
     * 注意，一开始你手头没有任何零钱。
     *
     * 如果你能给每位顾客正确找零，返回 true ，否则返回 false 。
     *
     * 示例 1：
     *
     * 输入：[5,5,5,10,20]
     * 输出：true
     * 解释：
     * 前 3 位顾客那里，我们按顺序收取 3 张 5 美元的钞票。
     * 第 4 位顾客那里，我们收取一张 10 美元的钞票，并返还 5 美元。
     * 第 5 位顾客那里，我们找还一张 10 美元的钞票和一张 5 美元的钞票。
     * 由于所有客户都得到了正确的找零，所以我们输出 true。
     * 示例 2：
     *
     * 输入：[5,5,10]
     * 输出：true
     * 示例 3：
     *
     * 输入：[10,10]
     * 输出：false
     * 示例 4：
     *
     * 输入：[5,5,10,10,20]
     * 输出：false
     * 解释：
     * 前 2 位顾客那里，我们按顺序收取 2 张 5 美元的钞票。
     * 对于接下来的 2 位顾客，我们收取一张 10 美元的钞票，然后返还 5 美元。
     * 对于最后一位顾客，我们无法退回 15 美元，因为我们现在只有两张 10 美元的钞票。
     * 由于不是每位顾客都得到了正确的找零，所以答案是 false。
     *
     *
     * 提示：
     *
     * 0 <= bills.length <= 10000
     * bills[i] 不是 5 就是 10 或是 20
     * @param bills
     * @return
     */
    public boolean lemonadeChange(int[] bills) {
        int count5=0,count10=0;
        for(int i=0;i<bills.length;i++){
            if(bills[i]==5){
                count5++;
            }else if(bills[i]==10){
                if(count5<1){
                    return false;
                }else {
                    count5--;
                    count10++;
                }
            }else {
                if(count5*5+count10*10<15 || count5<1){
                    return false;
                }else {
                    //优先找出10快
                    if(count10>0){
                        count10--;
                        count5--;
                    }else {
                        count5-=3;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 给定一个矩阵 A， 返回 A 的转置矩阵。
     *
     * 矩阵的转置是指将矩阵的主对角线翻转，交换矩阵的行索引与列索引。
     *
     *
     *
     * 示例 1：
     *
     * 输入：[[1,2,3],
     *       [4,5,6],
     *       [7,8,9]]
     * 输出：[[1,4,7],
     *       [2,5,8],
     *       [3,6,9]]
     * 示例 2：
     *
     * 输入：[[1,2,3],
     *       [4,5,6]]
     * 输出：[[1,4],
     *       [2,5],
     *       [3,6]]
     *
     *
     * 提示：
     *
     * 1 <= A.length <= 1000
     * 1 <= A[0].length <= 1000
     *
     * res[i][j] = A[j][i]
     *
     * @param A
     * @return
     */
    public int[][] transpose(int[][] A) {
        int[][] res=new int[A[0].length][A.length];
        for(int i=0;i<res.length;i++){
            for(int j=0;j<res[0].length;j++){
                res[i][j]=A[j][i];
            }
        }
        return res;
    }

    /**
     * 874. 模拟行走机器人
     *
     * 机器人在一个无限大小的网格上行走，从点 (0, 0) 处开始出发，面向北方。该机器人可以接收以下三种类型的命令：
     *
     * -2：向左转 90 度
     * -1：向右转 90 度
     * 1 <= x <= 9：向前移动 x 个单位长度
     * 在网格上有一些格子被视为障碍物。
     *
     * 第 i 个障碍物位于网格点  (obstacles[i][0], obstacles[i][1])
     *
     * 如果机器人试图走到障碍物上方，那么它将停留在障碍物的前一个网格方块上，但仍然可以继续该路线的其余部分。
     *
     * 返回从原点到机器人的最大欧式距离的平方。
     *
     *
     *
     * 示例 1：
     *
     * 输入: commands = [4,-1,3], obstacles = []
     * 输出: 25
     * 解释: 机器人将会到达 (3, 4)
     * 示例 2：
     *
     * 输入: commands = [4,-1,4,-2,4], obstacles = [[2,4]]
     * 输出: 65
     * 解释: 机器人在左转走到 (1, 8) 之前将被困在 (1, 4) 处
     *
     *
     * 提示：
     *
     * 0 <= commands.length <= 10000
     * 0 <= obstacles.length <= 10000
     * -30000 <= obstacle[i][0] <= 30000
     * -30000 <= obstacle[i][1] <= 30000
     * 答案保证小于 2 ^ 31
     *
     * 每步之前都需要检查路径上是否存在障碍点
     * 需要注意(0,0)为障碍的情况：从（0,0）出发时是可以的，但后面在经过时则不行
     * 这个距离指的是过程中的最大距离
     *
     *
     * @param commands
     * @param obstacles
     * @return
     */
    public int robotSim(int[] commands, int[][] obstacles) {
        Map<Integer,List<Integer>> map=new HashMap<>(  );
        for(int[] ints:obstacles){
            List<Integer> list=map.getOrDefault( ints[0],new ArrayList<>(  ) );
            list.add( ints[1] );
            map.put( ints[0],list );
        }
        int curx=0,cury=0;
        int directionx=0,directiony=1;
        int res=0;
        for(int i:commands){
            if(i==-2){
                if(directionx == 0 && directiony == 1){
                    directionx=-1;
                    directiony=0;
                }else if(directionx == -1 && directiony == 0){
                    directionx=0;
                    directiony=-1;
                }else if(directionx == 0 && directiony == -1){
                    directionx=1;
                    directiony=0;
                }else {
                    directionx=0;
                    directiony=1;
                }
            }else if(i==-1){
                if(directionx == 0 && directiony == 1){
                    directionx=1;
                    directiony=0;
                }else if(directionx == 1 && directiony == 0){
                    directionx=0;
                    directiony=-1;
                }else if(directionx == 0 && directiony == -1){
                    directionx=-1;
                    directiony=0;
                }else {
                    directionx=0;
                    directiony=1;
                }
            }else {
                while ( i>0 ){
                    i--;
                    curx+=directionx;
                    cury+=directiony;

                    if(map.get( curx )!=null && map.get( curx ).contains( cury )){
                        curx-=directionx;
                        cury-=directiony;
                        break;
                    }
                    res=Math.max( res,curx*curx+cury*cury );
                }
            }
        }
        return res;
    }

    /**
     * 给定两个句子 A 和 B 。 （句子是一串由空格分隔的单词。每个单词仅由小写字母组成。）
     *
     * 如果一个单词在其中一个句子中只出现一次，在另一个句子中却没有出现，那么这个单词就是不常见的。
     *
     * 返回所有不常用单词的列表。
     *
     * 您可以按任何顺序返回列表。
     *
     *
     *
     * 示例 1：
     *
     * 输入：A = "this apple is sweet", B = "this apple is sour"
     * 输出：["sweet","sour"]
     * 示例 2：
     *
     * 输入：A = "apple apple", B = "banana"
     * 输出：["banana"]
     *
     *
     * 提示：
     *
     * 0 <= A.length <= 200
     * 0 <= B.length <= 200
     * A 和 B 都只包含空格和小写字母。
     *
     * 可以理解成拼接字符串A+B，然后返回拼接后的字符串中只出现过一次的单词
     * @param A
     * @param B
     * @return
     */
    public String[] uncommonFromSentences(String A, String B) {
        Map<String,Integer> map = new HashMap<>();
        List<String> r = new ArrayList<>();
        for(String s:A.split(" ")){
            map.put(s,map.containsKey(s) ? map.get(s) + 1 : 1);
        }
        for(String s:B.split(" ")){
            map.put(s,map.containsKey(s) ? map.get(s) + 1 : 1);
        }
        for(String key:map.keySet()){
            if(map.get(key) == 1) r.add(key);
        }
        String[] ss = new String[r.size()];
        return r.toArray(ss);
    }

    /**
     * 爱丽丝和鲍勃有不同大小的糖果棒：A[i] 是爱丽丝拥有的第 i 块糖的大小，B[j] 是鲍勃拥有的第 j 块糖的大小。
     *
     * 因为他们是朋友，所以他们想交换一个糖果棒，这样交换后，他们都有相同的糖果总量。（一个人拥有的糖果总量是他们拥有的糖果棒大小的总和。）
     *
     * 返回一个整数数组 ans，其中 ans[0] 是爱丽丝必须交换的糖果棒的大小，ans[1] 是 Bob 必须交换的糖果棒的大小。
     *
     * 如果有多个答案，你可以返回其中任何一个。保证答案存在。
     *
     * 提示：
     *
     * 1 <= A.length <= 10000
     * 1 <= B.length <= 10000
     * 1 <= A[i] <= 100000
     * 1 <= B[i] <= 100000
     * 保证爱丽丝与鲍勃的糖果总量不同。
     * 答案肯定存在。
     * @param A
     * @param B
     * @return
     */
    public int[] fairCandySwap(int[] A, int[] B) {
        int sub=0;
        int[] res=new int[2];
        for ( int i:A ){
            sub+=i;
        }
        Set<Integer> setB=new HashSet<>(  );
        for ( int i:B ){
            setB.add( i );
            sub-=i;
        }
        for(int i:A){
            if(setB.contains( i-sub/2 )){
                res[0]=i;
                res[1]=i-sub/2;
                break;
            }
        }
       return res;
    }

    /**
     * 你将得到一个字符串数组 A。
     *
     * 如果经过任意次数的移动，S == T，那么两个字符串 S 和 T 是特殊等价的。
     *
     * 一次移动包括选择两个索引 i 和 j，且 i％2 == j％2，并且交换 S[j] 和 S [i]。
     *
     * s.length==t.length(),字符相同
     *
     * 现在规定，A 中的特殊等价字符串组是 A 的非空子集 S，这样不在 S 中的任何字符串与 S 中的任何字符串都不是特殊等价的。
     *
     * 返回 A 中特殊等价字符串组的数量。
     *
     * 示例 1：
     *
     * 输入：["a","b","c","a","c","c"]
     * 输出：3
     * 解释：3 组 ["a","a"]，["b"]，["c","c","c"]
     * 示例 2：
     *
     * 输入：["aa","bb","ab","ba"]
     * 输出：4
     * 解释：4 组 ["aa"]，["bb"]，["ab"]，["ba"]
     * 示例 3：
     *
     * 输入：["abc","acb","bac","bca","cab","cba"]
     * 输出：3
     * 解释：3 组 ["abc","cba"]，["acb","bca"]，["bac","cab"]
     * 示例 4：
     *
     * 输入：["abcd","cdab","adcb","cbad"]
     * 输出：1
     * 解释：1 组 ["abcd","cdab","adcb","cbad"]
     *
     * 提示：
     *
     * 1 <= A.length <= 1000
     * 1 <= A[i].length <= 20
     * 所有 A[i] 都具有相同的长度。
     * 所有 A[i] 都只由小写字母组成。
     *
     * 实际上是比较字符串各自奇数位上的字符Map 和奇数位上的字符Map是否相同
     * 把偶数位置 和 奇数位置的 chars 分别组成string，再排序，再组成新的String，存进set，返回set size即可
     * @param A
     * @return
     */
    public int numSpecialEquivGroups(String[] A) {
        Set<String> set=new HashSet<>(  );
        StringBuffer oddStr=new StringBuffer(  );
        StringBuffer evenStr=new StringBuffer(  );
        for(String str:A){
            oddStr.setLength( 0 );
            evenStr.setLength( 0 );
            for(int i=0;i<str.length();i++){
                if((i & 1) ==0 ){
                    evenStr.append( str.charAt( i ) );
                }else {
                    oddStr.append( str.charAt( i ) );
                }
            }
            String newStr= sortStr(evenStr.toString())+sortStr( oddStr.toString() ) ;
            set.add( newStr );
        }
        return set.size();
    }

    public String sortStr(String str){
        //按照统一规则对字符串排序
        char [] chars=str.toCharArray();
        Arrays.sort(chars);
        return new String( chars );
    }

    /**
     * 给定一个非负整数数组 A，返回一个由 A 的所有偶数元素组成的数组，后面跟 A 的所有奇数元素。
     *
     * 你可以返回满足此条件的任何数组作为答案。
     *
     * 示例：
     *
     * 输入：[3,1,2,4]
     * 输出：[2,4,3,1]
     * 输出 [4,2,3,1]，[2,4,1,3] 和 [4,2,1,3] 也会被接受。
     *
     * 提示：
     *
     * 1 <= A.length <= 5000
     * 0 <= A[i] <= 5000
     * @param A
     * @return
     */
    public int[] sortArrayByParity(int[] A) {
        int[] res=new int[A.length];
        int start=0,end=A.length-1;
        for(int i=0;i<A.length;i++){
            if((A[i] & 1) == 1 ){
                res[start]=A[i];
                start++;
            }else {
                res[end]=A[i];
                end--;
            }
        }
        return res;
    }

    public int[] sortArrayByParity2(int[] A) {
        //逐个交换：i增长的比index快
        int index = 0;
        for (int i = 0; i < A.length; i++) {
            if ((A[i] & 1) == 0) {
                int tmp = A[i];
                A[i] = A[index];
                A[index++] = tmp;
            }
        }
        return A;
    }

    /**
     * 给定一副牌，每张牌上都写着一个整数。
     *
     * 此时，你需要选定一个数字 X，使我们可以将整副牌按下述规则分成 1 组或更多组：
     *
     * 每组都有 X 张牌。
     * 组内所有的牌上都写着相同的整数。
     * 仅当你可选的 X >= 2 时返回 true。
     *
     *
     *
     * 示例 1：
     *
     * 输入：[1,2,3,4,4,3,2,1]
     * 输出：true
     * 解释：可行的分组是 [1,1]，[2,2]，[3,3]，[4,4]
     * 示例 2：
     *
     * 输入：[1,1,1,2,2,2,3,3]
     * 输出：false
     * 解释：没有满足要求的分组。
     * 示例 3：
     *
     * 输入：[1]
     * 输出：false
     * 解释：没有满足要求的分组。
     * 示例 4：
     *
     * 输入：[1,1]
     * 输出：true
     * 解释：可行的分组是 [1,1]
     * 示例 5：
     *
     * 输入：[1,1,2,2,2,2]
     * 输出：true
     * 解释：可行的分组是 [1,1]，[2,2]，[2,2]
     *
     * 提示：
     *
     * 1 <= deck.length <= 10000
     * 0 <= deck[i] < 10000
     *
     * 求公约数的问题
     * @param deck
     * @return
     */
    public boolean hasGroupsSizeX(int[] deck) {
        Map<Integer,Integer> map=new HashMap<>(  );
        for(int i=0;i<deck.length;i++){
            map.put( deck[i],map.getOrDefault( deck[i],0 )+1 );
        }
        int[] arr=new int[map.size()];
        int index=0;
        for(Integer i:map.keySet()){
            arr[index++]=map.get( i );
        }
        //求多个数的最大公约数:辗转相除法的扩展
        //设一组数a1，a2,a3,a4,a5..
        //
        //（1）对这一组数进行排序（从大到小）
        //
        //（2）对每两个相邻的两个数进行如下操作：
        //
        //　　　　设相邻的两个数为A和B（A在前，因为已经排序，所以A>B）,如果A=n*B（n为整数）,也就是A能够被B整除，那么就令A=B；如果A不能被B整除则令A=A%B。
        //
        //（3）重复（1）、（2）知道数组中每个数都相等，则最大公约数就为这个数。
        while ( !isSame( arr ) ){
            Arrays.sort( arr );
            for(int i=1;i<arr.length;i++){
                if(arr[i]%arr[i-1]==0){
                    arr[i]=arr[i-1];
                }else {
                    arr[i]=arr[i]%arr[i-1];
                }
            }
        }
        if(arr[0]>1){
            return true;
        }else {
            return false;
        }

    }

    public boolean isSame(int[] arr){
        for(int i=0;i<arr.length-1;i++){
            if(arr[i]==arr[i+1]){
                continue;
            }else {
                return false;
            }
        }
        return true;
    }


    /**
     * 给定一个字符串 S，返回 “反转后的” 字符串，其中不是字母的字符都保留在原地，而所有字母的位置发生反转。
     *
     * 示例 1：
     *
     * 输入："ab-cd"
     * 输出："dc-ba"
     * 示例 2：
     *
     * 输入："a-bC-dEf-ghIj"
     * 输出："j-Ih-gfE-dCba"
     * 示例 3：
     *
     * 输入："Test1ng-Leet=code-Q!"
     * 输出："Qedo1ct-eeLg=ntse-T!"
     *
     * 提示：
     *
     * S.length <= 100
     * 33 <= S[i].ASCIIcode <= 122
     * S 中不包含 \ or "
     *
     * @param S
     * @return
     */
    public String reverseOnlyLetters(String S) {
        //双指针交换
        char[] chars=S.toCharArray();
        int start=0,end=S.length()-1;
        while ( end>start ){
            if(chars[start]<65 ||  chars[start]>122 || (chars[start]>90 && chars[start]<97) ){
                start++;
                continue;
            }

            if(chars[end]<65 ||  chars[end]>122 || (chars[end]>90 && chars[end]<97) ){
                end--;
                continue;
            }
            char temp=chars[start];
            chars[start]=chars[end];
            chars[end]=temp;
            start++;
            end--;
        }
        return new String( chars );
    }

    /**
     * 给定一个非负整数数组 A， A 中一半整数是奇数，一半整数是偶数。
     *
     * 对数组进行排序，以便当 A[i] 为奇数时，i 也是奇数；当 A[i] 为偶数时， i 也是偶数。
     *
     * 你可以返回任何满足上述条件的数组作为答案。
     *
     * 示例：
     *
     * 输入：[4,2,5,7]
     * 输出：[4,5,2,7]
     * 解释：[4,7,2,5]，[2,5,4,7]，[2,7,4,5] 也会被接受。
     *
     * 提示：
     *
     * 2 <= A.length <= 20000
     * A.length % 2 == 0
     * 0 <= A[i] <= 1000
     *
     * 这个排序，并不是按照大小排序，只是让奇数索引对应奇数值，偶数索引对应欧数值
     * @param A
     * @return
     */
    public int[] sortArrayByParityII(int[] A) {
        int j = 1;
        for (int i = 0; i < A.length - 1; i = i + 2) {
            if ((A[i] & 1) != 0) {
                while ((A[j] & 1) != 0) {
                    j = j + 2;
                }
                int tmp = A[i];
                A[i] = A[j];
                A[j] = tmp;
            }
        }
        return A;
    }

    /**
     * 你的朋友正在使用键盘输入他的名字 name。偶尔，在键入字符 c 时，按键可能会被长按，而字符可能被输入 1 次或多次。
     *
     * 你将会检查键盘输入的字符 typed。如果它对应的可能是你的朋友的名字（其中一些字符可能被长按），那么就返回 True。
     *
     *
     *
     * 示例 1：
     *
     * 输入：name = "alex", typed = "aaleex"
     * 输出：true
     * 解释：'alex' 中的 'a' 和 'e' 被长按。
     * 示例 2：
     *
     * 输入：name = "saeed", typed = "ssaaedd"
     * 输出：false
     * 解释：'e' 一定需要被键入两次，但在 typed 的输出中不是这样。
     * 示例 3：
     *
     * 输入：name = "leelee", typed = "lleeelee"
     * 输出：true
     * 示例 4：
     *
     * 输入：name = "laiden", typed = "laiden"
     * 输出：true
     * 解释：长按名字中的字符并不是必要的。
     *
     *
     * 提示：
     *
     * name.length <= 1000
     * typed.length <= 1000
     * name 和 typed 的字符都是小写字母。
     * @param name
     * @param typed
     * @return
     */
    public boolean isLongPressedName(String name, String typed) {
        int typeIndex=0;
        for(int i=0;i<name.length();){
            //首字符不等直接pass
            if(i==0 && name.charAt( i )!=typed.charAt( typeIndex )){
                return false;
            }
            //typed到头但name未到头
            if(typeIndex>typed.length()-1){
                return false;
            }
            //name到头但typed未到头时
            if(name.charAt( i )==typed.charAt( typeIndex )){
                typeIndex++;
                i++;
            }else {
                //比较typed当前字符与前一个是否一样
                if(typed.charAt( typeIndex )!=typed.charAt( typeIndex-1 )){
                    return false;
                }else {
                    typeIndex++;
                }
            }
        }
        if(typeIndex<typed.length()){
            //判断最后的部分字符一样
            for(int i=typeIndex;i<typed.length();i++){
                if(typed.charAt( typeIndex )!=typed.charAt( typeIndex-1 )){
                    return false;
                }
            }
        }
        return true;
    }

    public static int numUniqueEmails(String[] emails) {
        Set<String> set=new HashSet<>();
        for(String email:emails){
            //本地名称
            String name=email.substring(0,email.indexOf("@"));
            //域名
            String domain=email.substring(email.indexOf("@"));
            //根据指定规则解析后的本地名称，先按加号切割字符串，然后替换'.'
            if(name.indexOf("+")>-1){
                name=name.substring(0,name.indexOf("+"));
            }
            name=name.replace(".","");
            //使用HashSet去重
            set.add(name+domain);
        }
        return set.size();
    }

    /**
     * 你有一个日志数组 logs。每条日志都是以空格分隔的字串。
     *
     * 对于每条日志，其第一个字为字母数字标识符。然后，要么：
     *
     * 标识符后面的每个字将仅由小写字母组成，或；
     * 标识符后面的每个字将仅由数字组成。
     * 我们将这两种日志分别称为字母日志和数字日志。保证每个日志在其标识符后面至少有一个字。
     *
     * 将日志重新排序，使得所有字母日志都排在数字日志之前。字母日志按字母顺序排序，忽略标识符，标识符仅用于表示关系。数字日志应该按原来的顺序排列。
     *
     * 返回日志的最终顺序。
     *
     *
     *
     * 示例 ：
     *
     * 输入：["a1 9 2 3 1","g1 act car","zo4 4 7","ab1 off key dog","a8 act zoo"]
     * 输出：["g1 act car","a8 act zoo","ab1 off key dog","a1 9 2 3 1","zo4 4 7"]
     *
     *
     * 提示：
     *
     * 0 <= logs.length <= 100
     * 3 <= logs[i].length <= 100
     * logs[i] 保证有一个标识符，并且标识符后面有一个字。
     * @param logs
     * @return
     */
    public String[] reorderLogFiles(String[] logs) {
        //直接重写comparator
        Arrays.sort(logs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int index1 = o1.indexOf(' ');
                String s1 = o1.substring(index1+1);
                int index2 = o2.indexOf(' ');
                String s2 = o2.substring(index2+1);
                char c1 = s1.charAt(0);
                char c2 = s2.charAt(0);
                if ( Character.isLetter(c1) && Character.isLetter(c2)) {
                    return s1.compareTo(s2);
                }else if (Character.isLetter(c1)) {
                    return -1;
                }else if (Character.isLetter(c2)) {
                    return 1;
                }else {
                    return 0;
                }
            }
        });
        return logs;
    }

    /**
     * 给定只含 "I"（增大）或 "D"（减小）的字符串 S ，令 N = S.length。
     *
     * 返回 [0, 1, ..., N] 的任意排列 A 使得对于所有 i = 0, ..., N-1，都有：
     *
     * 如果 S[i] == "I"，那么 A[i] < A[i+1]
     * 如果 S[i] == "D"，那么 A[i] > A[i+1]
     *
     *
     * 示例 1：
     *
     * 输出："IDID"
     * 输出：[0,4,1,3,2]
     * 示例 2：
     *
     * 输出："III"
     * 输出：[0,1,2,3]
     * 示例 3：
     *
     * 输出："DDI"
     * 输出：[3,2,0,1]
     *
     *
     * 提示：
     *
     * 1 <= S.length <= 1000
     * S 只包含字符 "I" 或 "D"。
     * @param S
     * @return
     */
    public int[] diStringMatch(String S) {
        //I增D减，增则从0开始，减则从N开始
        int start=0,end=S.length();
        int[] res=new int[end];
        for(int i=0;i<S.length();i++){
            if(S.charAt( i )== 'I'){
                res[i]=start++;
            }else {
                res[i]=end--;
            }
        }
        return res;
    }

    /**
     * 字符串的全排列
     */
    Set<String> set=new HashSet<>(  );
    public void fullPermutations(char arr[],int index){
        if(index==arr.length-1){
            set.add( new String( arr ) );
        }else {
            for(int i=index;i<arr.length;i++){
                //交换i和index:固定第 i 个字符
                char tmp = arr[i];
                arr[i] = arr[index];
                arr[index] = tmp;
                fullPermutations(arr,index+1);
                //复位
                char tmp1 = arr[i];
                arr[i] = arr[index];
                arr[index] = tmp;
            }
        }
    }


    /**
     * 给定一个由 4 位数字组成的数组，返回可以设置的符合 24 小时制的最大时间。
     *
     * 最小的 24 小时制时间是 00:00，而最大的是 23:59。从 00:00 （午夜）开始算起，过得越久，时间越大。
     *
     * 以长度为 5 的字符串返回答案。如果不能确定有效时间，则返回空字符串
     *
     * 示例 1：
     *
     * 输入：[1,2,3,4]
     * 输出："23:41"
     * 示例 2：
     *
     * 输入：[5,5,5,5]
     * 输出：""
     *
     *
     * 提示：
     *
     * A.length == 4
     * 0 <= A[i] <= 9
     * @param A
     * @return
     */
    public Set<String> sets=new HashSet<>(  );
    public String largestTimeFromDigits(int[] A) {
        //暴力if解法太复杂了，直接求出所有的组合，剔除非法的时间，再比较最大的
        fullPermutations( A,0 );
        String res="";
        for(String str:set){
            if(str.substring( 0,2 ).compareTo( "24" )<0 && str.substring( 2 ).compareTo( "60" )<0 ){
                res=res.compareTo( str )>0?res:str;
            }
        }
        return res;
    }

    public void fullPermutations(int arr[],int index){
        if(arr==null||index<0 ||index>arr.length){//1
            return;
        }

        StringBuffer sb=new StringBuffer(  );
        if(index==arr.length-1){
            for(int i=0;i<arr.length;i++){
                sb.append( arr[i] );
            }
            sets.add( sb.toString() );
        }else {
            for(int i=index;i<arr.length;i++){
                //交换i和index:固定第 i 个字符
                int tmp = arr[i];
                arr[i] = arr[index];
                arr[index] = tmp;
                fullPermutations(arr,index+1);
                //复位
                int tmp1 = arr[i];
                arr[i] = arr[index];
                arr[index] = tmp1;
            }
        }
    }

    /**
     * 某种外星语也使用英文小写字母，但可能顺序 order 不同。字母表的顺序（order）是一些小写字母的排列。
     *
     * 给定一组用外星语书写的单词 words，以及其字母表的顺序 order，只有当给定的单词在这种外星语中按字典序排列时，返回 true；否则，返回 false。
     *
     *
     *
     * 示例 1：
     *
     * 输入：words = ["hello","leetcode"], order = "hlabcdefgijkmnopqrstuvwxyz"
     * 输出：true
     * 解释：在该语言的字母表中，'h' 位于 'l' 之前，所以单词序列是按字典序排列的。
     * 示例 2：
     *
     * 输入：words = ["word","world","row"], order = "worldabcefghijkmnpqstuvxyz"
     * 输出：false
     * 解释：在该语言的字母表中，'d' 位于 'l' 之后，那么 words[0] > words[1]，因此单词序列不是按字典序排列的。
     * 示例 3：
     *
     * 输入：words = ["apple","app"], order = "abcdefghijklmnopqrstuvwxyz"
     * 输出：false
     * 解释：当前三个字符 "app" 匹配时，第二个字符串相对短一些，然后根据词典编纂规则 "apple" > "app"，因为 'l' > '∅'，其中 '∅' 是空白字符，定义为比任何其他字符都小（更多信息）。
     *
     *
     * 提示：
     *
     * 1 <= words.length <= 100
     * 1 <= words[i].length <= 20
     * order.length == 26
     * 在 words[i] 和 order 中的所有字符都是英文小写字母。
     *
     * 实际上就是重写comparator
     * 为了便于查找直接用hash表存下个字母对应索引
     * @param words
     * @param order
     * @return
     */
    public boolean isAlienSorted(String[] words, String order) {
        if(words.length==1){
            return true;
        }
        Map<Character,Integer> map=new HashMap<>(  );
        for(int i=0;i<order.length();i++){
            map.put( order.charAt( i ),i );
        }
        //纵向对比相同索引下的字符大小<=
        //其他部分相同的情况下，首个字符串必定是最短的
        for(int i=1;i<words.length;i++){
            for(int j=0;j<words[i].length();j++){
                if(j<words[i-1].length() && map.get( words[i].charAt( j ) )<map.get( words[i-1].charAt( j ) )){
                    return false;
                }else if(j<words[i-1].length() && map.get( words[i].charAt( j ) )>map.get( words[i-1].charAt( j ) )){
                    break;
                }else if(j<words[i-1].length() && map.get( words[i].charAt( j ) )==map.get( words[i-1].charAt( j ) )){
                    //i-1短
                    if(j==words[i-1].length()-1){
                        break;
                    }
                    if(j==words[i].length()-1){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 我们有一个由平面上的点组成的列表 points。需要从中找出 K 个距离原点 (0, 0) 最近的点。
     *
     * （这里，平面上两点之间的距离是欧几里德距离。）
     *
     * 你可以按任何顺序返回答案。除了点坐标的顺序之外，答案确保是唯一的。
     * @param points
     * @param K
     * @return
     */
    public int[][] kClosest(int[][] points, int K) {
        //重写comparator
        int [][] res=new int[K][];
        Arrays.sort(points, new Comparator() {
            @Override
            public int compare( Object o1, Object o2 ) {
                int[] arr1=(int [])o1;
                int[] arr2=(int [])o2;
                return (arr1[0]*arr1[0] + arr1[1]*arr1[1])-(arr2[0]*arr2[0] + arr2[1]*arr2[1]);
            }
        } );
        for(int i=0;i<K;i++){
            res[K]=points[K];
        }
        return res;
    }


    /**
     * 给定由一些正数（代表长度）组成的数组 A，返回由其中三个长度组成的、面积不为零的三角形的最大周长。
     *
     * 如果不能形成任何面积不为零的三角形，返回 0。
     *
     * 3 <= A.length <= 10000
     * 1 <= A[i] <= 10^6
     *
     * @param A
     * @return
     */
    public int largestPerimeter(int[] A) {
        //先从大到小排序，再滑动窗口判断能否组成三角形
        Arrays.sort( A );
        for(int i=A.length-1;i>1;i--){
            //判断能否组成三角形：最小边与次小边之和最大边
            if(A[i]<A[i-1]+A[i-2]){
                return A[i]+A[i-1]+A[i-2];
            }
        }
        return 0;
    }

    /**
     * 给定两个整数 A 和 B，返回任意字符串 S，要求满足：
     *
     * S 的长度为 A + B，且正好包含 A 个 'a' 字母与 B 个 'b' 字母；
     * 子串 'aaa' 没有出现在 S 中；
     * 子串 'bbb' 没有出现在 S 中。
     *
     *
     * 示例 1：
     *
     * 输入：A = 1, B = 2
     * 输出："abb"
     * 解释："abb", "bab" 和 "bba" 都是正确答案。
     * 示例 2：
     *
     * 输入：A = 4, B = 1
     * 输出："aabaa"
     *
     *
     * 提示：
     *
     * 0 <= A <= 100
     * 0 <= B <= 100
     * 对于给定的 A 和 B，保证存在满足要求的 S。
     * @param A
     * @param B
     * @return
     */
    public String strWithout3a3b(int A, int B) {
        int max = Math.max( A, B );
        int min = Math.min( A, B );
        char maxchar = A > B ? 'a' : 'b';
        char minchar = A <= B ? 'a' : 'b';
        //计算大小比例：A=B，交替即可
        //A=2*B.交替
        //A-2 剩下的可以组成aabaabaab aa
        //A-2-B>0 ,aa开头，则其值即为组成baa的个数，剩下的则为(2B-A+2)个ab
        //A-2-B<0，则只能交叉
        StringBuffer sb = new StringBuffer();
        if(max==min){
            for(int i=0;i<min;i++){
                sb.append(maxchar).append(minchar);
            }
        }else {
            if ( max - min - 2 < 0 ) {
                sb.append( maxchar );
                for ( int i = 0; i < B; i++ ) {
                    sb.append( minchar ).append( maxchar );
                }
            } else if ( max - min - 2 == 0 ) {
                sb.append( maxchar ).append( maxchar );
                for ( int i = 0; i < B; i++ ) {
                    sb.append( minchar ).append( maxchar );
                }
            } else {
                int countAAB = max - 2 - min;
                int countAB = min*2-max+2;
                sb.append( maxchar ).append( maxchar );
                for(int i=0;i<countAAB;i++){
                    sb.append( minchar ).append( maxchar ).append( maxchar );
                }
                for(int i=0;i<countAB;i++){
                    sb.append( minchar ).append( maxchar );
                }
            }
        }

        return sb.toString();

    }

    /**
     * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
     *
     * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
     *
     * 你可以假设 nums1 和 nums2 不会同时为空。
     *
     * 示例 1:
     *
     * nums1 = [1, 3]
     * nums2 = [2]
     *
     * 则中位数是 2.0
     * 示例 2:
     *
     * nums1 = [1, 2]
     * nums2 = [3, 4]
     *
     * 则中位数是 (2 + 3)/2 = 2.5
     *
     * 这个时间复杂度只能二分查找
     * 还不能遍历
     *
     * 其实可以一次遍历进行排序:(m+n)/2时间复杂度
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        //TODO
        return 0;
    }

    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     *
     * 示例 1:
     *
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     *
     * 输入: "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     *
     * 输入: "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     *
     *      遇到重复的直接重置并在前一个索引处开始，需要一个count计数，一个索引开始值res
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        if(s.length()==0){
            return 1;
        }
        int count=1,pre=0,res=0;
        for(int i=1;i<s.length();i++){
            if(s.substring( pre,i ).indexOf( s.charAt( i ) )==-1){
                count++;
                if(i==s.length()-1){
                    res=Math.max(res,count);
                    break;
                }
                continue;
            }else {
                res=Math.max( count, res);
                count-=s.substring( pre,i ).indexOf( s.charAt( i ) );
                pre+=s.substring( pre,i ).indexOf( s.charAt( i ) )+1;
            }
        }
        return res;
    }

    /**
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     *
     * 示例 1：
     *
     * 输入: "babad"
     * 输出: "bab"
     * 注意: "aba" 也是一个有效答案。
     * 示例 2：
     *
     * 输入: "cbbd"
     * 输出: "bb"
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        //两种情况：偶数长度，先找到相同连续俩节点，再比较左右字符是否相等，直到不等
        //奇数长度
        if(s.length()==0){
            return s;
        }
        int count=1,start=0,end=1;
        for(int i=1;i<s.length()-1;i++){
            int len=1;
            //需要注意临界条件
            while ( i-len>=0 && i+len<s.length() ){
                if(s.charAt( i-len )==s.charAt( i+len )){
                    //及时更新长度，否则字符走到末尾会丢失判断
                    if(2*len+1>count){
                        count=2*len+1;
                        start=i-len;
                        end=i+len+1;
                    }
                }else {
                    break;
                }
                len++;
            }

        }
        for(int i=1;i<s.length();i++){
            if(s.charAt( i )==s.charAt( i-1 )){
                //偶数的初始长度（2）会比奇数（1）长，更新操作从len=0开始
                int len=0;
                while ( i-1-len>=0 && i+len<s.length() ){
                    if(s.charAt( i-1-len )==s.charAt( i+len )){
                        if(2*len+2>count){
                            count=2*len+2;
                            start=i-1-len;
                            end=i+1+len;
                        }
                    }else {
                        break;
                    }
                    len++;
                }
            }
        }
        return s.substring( start,end );
    }

    /**
     * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
     *
     * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
     *
     * L   C   I   R
     * E T O E S I I G
     * E   D   H   N
     * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。
     *
     * 请你实现这个将字符串进行指定行数变换的函数：
     *
     * string convert(string s, int numRows);
     * 示例 1:
     *
     * 输入: s = "LEETCODEISHIRING", numRows = 3
     * 输出: "LCIRETOESIIGEDHN"
     * 示例 2:
     *
     * 输入: s = "LEETCODEISHIRING", numRows = 4
     * 输出: "LDREOEIIECIHNTSG"
     * 解释:
     *
     * L     D     R
     * E   O E   I I
     * E C   I H   N
     * T     S     G
     *
     * 2(numRows-1)*0                                  2(numRows-1)*1
     * 2(numRows-1)*0+1                         numRows+2
     * 2(numRows-1)*0+2           numRows+1
     * '''''
     * 2(numRows-1)*0+numRows-1
     * @param s
     * @param numRows
     * @return
     */
    public static String convert(String s, int numRows) {
        //一次遍历，生成row行
        if(numRows==1){
            return s;
        }
        numRows=Math.min( s.length(),numRows );
        List<StringBuffer> list=new ArrayList<>();
        for(int i=0;i<numRows;i++){
            list.add( new StringBuffer(  ) );
        }
        int curRow=0;
        boolean goingDown = false;

        for (char c : s.toCharArray()) {
            list.get(curRow).append(c);
            if (curRow == 0 || curRow == numRows - 1) goingDown = !goingDown;
            curRow += goingDown ? 1 : -1;
        }

        StringBuffer res=new StringBuffer(  );
         for(StringBuffer sb:list){
             res.append( sb );
         }
         return res.toString();
    }

    public static String convert2(String s, int numRows) {
//        一行一行生成
//        行 0 中的字符位于索引k(2⋅numRows−2) 处;
//        行 numRows−1 中的字符位于索引 k(2⋅numRows−2)+numRows−1 处;
//        内部的 行 i 中的字符位于索引    k(2⋅numRows−2)+i 以及 (k+1)(2⋅numRows−2)−i 处
        if ( numRows == 1 ) return s;

        StringBuilder ret = new StringBuilder();
        int n = s.length();
        int cycleLen = 2 * numRows - 2;

        for ( int i = 0; i < numRows; i++ ) {
            for ( int j = 0; j + i < n; j += cycleLen ) {
                ret.append( s.charAt( j + i ) );
                if ( i != 0 && i != numRows - 1 && j + cycleLen - i < n )
                    ret.append( s.charAt( j + cycleLen - i ) );
            }
        }
        return ret.toString();
    }


    /**
     * 请你来实现一个 atoi 函数，使其能将字符串转换成整数。
     *
     * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
     *
     * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
     *
     * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
     *
     * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
     *
     * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
     *
     * 说明：
     *
     * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，qing返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
     *
     * 示例 1:
     *
     * 输入: "42"
     * 输出: 42
     * 示例 2:
     *
     * 输入: "   -42"
     * 输出: -42
     * 解释: 第一个非空白字符为 '-', 它是一个负号。
     *      我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
     * 示例 3:
     *
     * 输入: "4193 with words"
     * 输出: 4193
     * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
     * 示例 4:
     *
     * 输入: "words and 987"
     * 输出: 0
     * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
     *      因此无法执行有效的转换。
     * 示例 5:
     *
     * 输入: "-91283472332"
     * 输出: -2147483648
     * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
     *      因此返回 INT_MIN (−231) 。
     *
     * @param str
     * @return
     */
    public int myAtoi(String str) {
        if (str.length() < 1){
            return 0;
        }

        //过滤字符串开头空格
        int pos = 0;
        while (pos < str.length() && str.charAt(pos) == ' '){
            pos++;
        }
        str = str.substring(pos, str.length());

        //过滤空串
        if ("".equals(str)) {
            return 0;
        }

        //数据为正或负 true为正
        boolean operator = true;

        String numString;

        //字符串开头非数字情况
        if (str.charAt(0) < '0' || str.charAt(0) > '9') {
            //非+或者-不合法返回0
            if (str.charAt(0) != '+' && str.charAt(0) != '-') {
                return 0;
            }
            //数字为负数
            if (str.charAt(0) == '-') {
                operator = false;
            }
            //获取数值
            numString = getNumStr(str.substring(1));
        } else {
            numString = getNumStr(str);
        }


        //过滤数据部分为空串的情况
        if ("".equals(numString)) {
            return 0;
        }

        //过滤掉数据超出long的情况
        if (numString.length() > 10) {
            if (operator) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        }

        //转化数值
        long num = Long.parseLong(numString);
        if (!operator) {
            num = -num;
        }

        //根据结果范围返回数值
        if (num < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        if (num > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }

        return (int) num;

    }

    /**
     * 截取字符串的开头数值
     *
     * @param s 字符串
     * @return 数值字符串
     */
    private String getNumStr(String s) {
        StringBuilder num = new StringBuilder();
        boolean isStart = true;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                if (isStart && s.charAt(i) == '0') {
                    continue;
                }
                isStart = false;
                num.append(s.charAt(i));
            } else {
                break;
            }
        }

        return num.toString();
    }


    /**
     * 给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
     *
     * 说明：你不能倾斜容器，且 n 的值至少为 2。
     *
     * 图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
     *
     *
     *
     * 示例:
     *
     * 输入: [1,8,6,2,5,4,8,3,7]
     * 输出: 49
     *
     *
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        //求Math.min(height[i] , height[j])*(Math.abs(i-j))的最大值
        //暴力法
        int max=0;
        for(int i=0;i<height.length;i++){
            for(int j=i+1;j<height.length;j++){
                max=Math.max(max,Math.min(height[i] , height[j])*(Math.abs(i-j)));
            }
        }
        return max;
    }


    public int maxArea1(int[] height) {
        //双指针
        int maxarea = 0, l = 0, r = height.length - 1;
        while (l < r) {
            maxarea = Math.max(maxarea, Math.min(height[l], height[r]) * (r - l));
            if (height[l] < height[r])
                l++;
            else
                r--;
        }
        return maxarea;
    }

    /**
     * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
     *
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
     *
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
     *
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
     *
     * 示例 1:
     *
     * 输入: 3
     * 输出: "III"
     * 示例 2:
     *
     * 输入: 4
     * 输出: "IV"
     * 示例 3:
     *
     * 输入: 9
     * 输出: "IX"
     * 示例 4:
     *
     * 输入: 58
     * 输出: "LVIII"
     * 解释: L = 50, V = 5, III = 3.
     * 示例 5:
     *
     * 输入: 1994
     * 输出: "MCMXCIV"
     * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
     * @param num
     * @return
     */
    public String intToRoman(int num) {
        StringBuffer sb=new StringBuffer(  );
        while ( num>=1000 ){
            int countM=num/1000;
            while ( countM>0 ){
                sb.append( "M" );
                countM--;
            }
            num=num%1000;
        }
        if ( num>=900 ){
            sb.append( "CM" );
            num-=900;
        }
        if ( num>=500 ){
            sb.append( "D" );
            num-=500;
            if(num>=100){
                int countC=num/100;
                while ( countC>0 ){
                    sb.append( "C" );
                    countC--;
                }
                num%=100;
            }
        }else {
            if(num>=400){
                sb.append( "CD" );
                num-=400;
            }else  if(num>=100){
                int countC=num/100;
                while ( countC>0 ){
                    sb.append( "C" );
                    countC--;
                }
                num%=100;
            }
        }
        //100一下
        if(num>=90){
            sb.append( "XC" );
            num-=90;
        }else if(num>=50){
            sb.append( "L" );
            num-=50;
            if(num>=10){
                int countX=num/10;
                while ( countX>0 ){
                    sb.append( "X" );
                    countX--;
                }
                num%=10;
            }
        }else if(num>=40){
            sb.append( "XL" );
            num-=40;
        }else if(num>=10){
            int countX=num/10;
            while ( countX>0 ){
                countX--;
                sb.append( "X" );
            }
            num%=10;
        }
        //处理个位数
        if(num==9){
            sb.append( "IX" );
        }else if(num>=5){
            sb.append( "V" );
            int countI=num-5;
            while ( countI>0 ){
                countI--;
                sb.append( "I" );
            }
        }else if(num==4){
            sb.append( "IV" );
        }else {
            while ( num>0 ){
                num--;
                sb.append( "I" );
            }
        }
        return sb.toString();
    }


    /**
     * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
     *
     * 注意：答案中不可以包含重复的三元组。
     *
     * 例如, 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
     *
     * 满足要求的三元组集合为：
     * [
     *   [-1, 0, 1],
     *   [-1, -1, 2]
     * ]
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        if(nums.length<3){
            return new ArrayList<>();
        }

        //暴力法+HashSet(字符操作，超时了)
        Set<String> set=new HashSet<>();
        int len=nums.length;
        for(int i=0;i<len;i++){
            for(int j=i+1;j<len;j++){
                for(int k=j+1;k<len;k++){
                    if(nums[i]+nums[j]+nums[k]==0){
                        set.add( formatTriNum(nums[i],nums[j],nums[k]) );
                    }
                }
            }
        }
        List<List<Integer>> list=new ArrayList<>();
        for(String str:set){
            String[] strs=str.split( "," );
            List<Integer> list1=new ArrayList<>(  );
            for(int i=0;i<strs.length;i++){
                if(!strs[i].equals(",")){
                    if(strs[i].startsWith( "-" )){
                        list1.add( 0-Integer.valueOf( strs[i].substring( 1 ) ) );
                    }else {
                        list1.add( Integer.valueOf( strs[i] ) );
                    }

                }
            }
            list.add( list1 );
        }
        return list;

    }

    public static String formatTriNum(int a,int b,int c){
        int max=Math.max( a,Math.max( b,c ) );
        int min=Math.min( a,Math.min(b,c) );
        int mid=(a+b+c)-(max+min);
        StringBuffer sb=new StringBuffer(  );
        sb.append( max ).append( "," ).append( mid ).append( "," ).append( min );
        return sb.toString();
    }


    public List< List< Integer > > threeSum2( int[] nums ) {
        List< List< Integer > > result = new ArrayList<>();
        if ( nums.length < 3 ) return result;
        Arrays.sort( nums );
        final int target = 0;

        for ( int i = 0; i < nums.length - 2; ++i ) {
            if ( i > 0 && nums[ i ] == nums[ i - 1 ] ) continue;
            int j = i + 1;
            int k = nums.length - 1;
            while ( j < k ) {//要
                if ( nums[ i ] + nums[ j ] + nums[ k ] < target ) {
                    ++j;
                    while ( nums[ j ] == nums[ j - 1 ] && j < k ) ++j;
                } else if ( nums[ i ] + nums[ j ] + nums[ k ] > target ) {
                    --k;
                    while ( nums[ k ] == nums[ k + 1 ] && j < k ) --k;
                } else {
                    result.add( Arrays.asList( nums[ i ], nums[ j ], nums[ k ] ) );
                    ++j;
                    --k;
                    while ( nums[ j ] == nums[ j - 1 ] && j < k ) ++j;
                    while ( nums[ k ] == nums[ k + 1 ] && j < k ) --k;
                }
            }
        }
        return result;
    }

    /**
     * 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和。假定每组输入只存在唯一答案。
     *
     * 例如，给定数组 nums = [-1，2，1，-4], 和 target = 1.
     *
     * 与 target 最接近的三个数的和为 2. (-1 + 2 + 1 = 2).
     *
     * 基本同上，需要解决重复问题
     * @param nums
     * @param target
     * @return
     */
    public static int threeSumClosest(int[] nums, int target) {
        if(nums.length<3){
            return 0;
        }
        Arrays.sort( nums );
        int res=nums[0]+nums[1]+nums[2];

        for(int i=0;i<nums.length-2;i++){
            if ( i>0 && nums[i]==nums[i-1]){
                continue;
            }
            int j=i+1;
            int k=nums.length-1;
            while ( k>j ){
                if(nums[i]+nums[j]+nums[k]>target){
                    if(Math.abs(nums[i]+nums[j]+nums[k]-target)<Math.abs( res-target )){
                        res=nums[i]+nums[j]+nums[k];
                    }
                    k--;
                    while ( k>j && nums[k]==nums[k+1] ){
                        k--;
                    }
                }else if(nums[i]+nums[j]+nums[k]<target){
                    if(Math.abs(target-nums[i]-nums[j]-nums[k])<Math.abs( res-target )){
                        res=nums[i]+nums[j]+nums[k];
                    }
                    j++;
                    while ( j<k  && nums[j]==nums[j-1] ){
                        j++;
                    }
                }else {
                    res=target;
                    return res;
                }
            }
        }
        return res;
    }

    /**
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
     *
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     * 2-abc
     * 3-def
     * 4-ghi
     * 5-jkl
     * 6-mno
     * 7-pqrs
     * 8-tuv
     * 9-wxyz
     *
     * 示例:
     *
     * 输入："23"
     * 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
     *
     * @param digits
     * @return
     */
    public  static Map<Character,String> map=new HashMap<>(  );
    static {
        map.put( '2',"abc" );
        map.put( '3',"def" );
        map.put( '4',"ghi" );
        map.put( '5',"jkl" );
        map.put( '6',"mno" );
        map.put( '7',"pqrs" );
        map.put( '8',"tuv" );
        map.put( '9',"wxyz" );
    }


    public List<String> letterCombinations(String digits) {
        //回溯法
        List<String> list=new ArrayList<>(  );
        if(digits.length()==0){
            return list;
        }
        StringBuffer sb=new StringBuffer(  );
        comb(digits,0,sb,list);
        return list;
    }

    public void comb(String digits,int index,StringBuffer sb,List<String> list){
        if(index==digits.length()){
            list.add( sb.toString() );
        }else {
            String str=map.get( digits.charAt( index ) );
            int length=sb.length();
            for(int i=0;i<str.length();i++){
                sb.append( str.charAt( i ) );
                comb( digits,index+1,sb,list );
                //恢复
                sb.delete( length+1,sb.length() );
            }
        }
    }

    /**
     * 给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？找出所有满足条件且不重复的四元组。
     *
     * 注意：
     *
     * 答案中不可以包含重复的四元组。
     *
     * 示例：
     *
     * 给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。
     *
     * 满足要求的四元组集合为：
     * [
     *   [-1,  0, 0, 1],
     *   [-2, -1, 1, 2],
     *   [-2,  0, 0, 2]
     * ]
     *
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        //先排序
        Arrays.sort( nums );

        int len=nums.length;
        List<List<Integer>> lists=new ArrayList<>(  );
        for(int i=0;i<len-3;i++){
            //i去重
            if ( i>0 && nums[i]==nums[i-1]){
                continue;
            }
            for(int j=i+1;j<len-2;j++){
                //j去重
                if(j>i+1 && nums[j]==nums[j-1]){
                    continue;
                }
                //双指针
                int k=j+1;
                int l=len-1;
                while ( k<l ){
                    if ( nums[i]+nums[j]+nums[k]+nums[l]>target ){
                        l--;
                        while ( l>k && nums[l]==nums[l+1] ){
                            l--;
                        }
                    }else if(nums[i]+nums[j]+nums[k]+nums[l]<target){
                        k++;
                        while ( k<l && nums[k]==nums[k-1] ){
                            k++;
                        }
                    }else {
                        lists.add( Arrays.asList( nums[i],nums[j],nums[k],nums[l] ) );
                        l--;
                        k++;
                        while ( l>k && nums[l]==nums[l+1] ){
                            l--;
                        }
                        while ( k<l && nums[k]==nums[k-1] ){
                            k++;
                        }
                    }
                }
            }
        }
        return lists;
    }

    /**
     * 给出 n 代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。
     *
     *
     * [
     *   "(((())))",
     *   "((()()))",
     *   "((())())",
     *   "(()(()))",
     *   "(()()())"
     * ]
     *
     * 例如，给出 n = 3，生成结果为：
     *
     * [
     *   "((()))",
     *   "(()())",
     *   "(())()",
     *   "()(())",
     *   "()()()"
     * ]
     *
     * [
     *   "(())",
     *   "()()",
     * ]
     *
     * f(n+1)与f(n)的区别:
     * 最外面括号包住f(n):f(n)
     * 左/右括号与n并列:2*f(n)
     * n+1个括号并列：1
     * 其中后两种情况有覆盖的:f(n)+2*(f(n)-1)+1=3*f(n) -1
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        //外面有1-n个括号,但不生成
//        List<String> list=new ArrayList<>(  );
//        if(n==0){
//            return list;
//        }
//        list.add( "()" );
//        List<String> lst=new ArrayList<>(  );
//        for(int i=0;i<n;i++){
//
//            for(String str:list){
//                lst.add( "(" +str+")");
//                lst.add( "()"+str );
//                lst.add( str+ "()" );
//            }
//            list=lst;
//            lst.clear();
//        }
//        Set<String> set=new HashSet<>(  );
//        for(String str:list){
//            set.add( str );
//        }
//        list.clear();
//        for(String ste:set){
//            list.add( ste );
//        }
//        return list;

//        暴力法:可以生成所有 2^(2n)个 '(' 和 ')' 字符构成的序列。然后检查每一个是否有效。
            List<String> list=new ArrayList<>(  );
            generateAll( new char[2*n],0,list );
            return list;

    }

    public void generateAll(char[] chars,int index,List<String> list){
        if(index==chars.length){
            if(verify( chars )){
                list.add( new String( chars ) );
            }
        }else {
            chars[index]='(';
            generateAll( chars,index+1,list );
            chars[index]=')';
            generateAll( chars,index+1,list );
        }
    }

    public boolean verify(char[] chars) {
        int balance=0;
        for(char ch:chars){
            if(ch=='('){
                balance++;
            }else {
                balance--;
            }
            if(balance<0){
                return false;
            }
        }
        return balance==0;
    }

    public List<String> generateParenthesis2(int n) {
        List<String> ans = new ArrayList();
        backtrack(ans, "", 0, 0, n);
        return ans;
    }

    public void backtrack(List<String> ans, String cur, int open, int close, int max){

        if (cur.length() == max * 2) {
            ans.add(cur);
            return;
        }

        if (open < max)
            backtrack(ans, cur+"(", open+1, close, max);
        if (close < open)
            backtrack(ans, cur+")", open, close+1, max);
    }

    /**
     * 给定两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
     *
     * 返回被除数 dividend 除以除数 divisor 得到的商。
     *
     * 示例 1:
     *
     * 输入: dividend = 10, divisor = 3
     * 输出: 3
     * 示例 2:
     *
     * 输入: dividend = 7, divisor = -3
     * 输出: -2
     * 说明:
     *
     * 被除数和除数均为 32 位有符号整数。
     * 除数不为 0。
     * 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−231,  231 − 1]。本题中，如果除法结果溢出，则返回 231 − 1。
     *
     * @param dividend
     * @param divisor
     * @return
     */
    public  static  int divide(int dividend, int divisor) {
        //1.处理异常
        //2.处理溢出值
        //3.处理符号
        //4.位移操作
        if(divisor==0){
            return 0;
        }
        if(dividend==Integer.MAX_VALUE){
            if(divisor==1){
                return Integer.MAX_VALUE;
            }else if(divisor==-1){
                return Integer.MIN_VALUE;
            }

        }
        if(dividend==Integer.MIN_VALUE){
            if(divisor==1){
                return Integer.MIN_VALUE;
            }else if(divisor==-1){
                return Integer.MAX_VALUE;
            }
        }
        boolean isPositive=true;
        long res=0;
        long first=dividend,second=divisor;
        if(first<0){
            first=-first;
            if(second<0){
                second=-second;
            }else {
                isPositive=false;
            }
        }else if(second<0){
            second=-second;
            isPositive=false;
        }
        while ( first>=second ){
            long cnt=1;
            long second0=second;
            while ( first>=second0 ){
                second0<<=1;
                cnt<<=1;
            }
            second0>>=1;
            cnt>>=1;
            first-=second0;
            res+=cnt;
        }
        return isPositive?(int)res:(int)-res;
    }

    /**
     * 给定一个字符串 s 和一些长度相同的单词 words。找出 s 中恰好可以由 words 中所有单词串联形成的子串的起始位置。
     *
     * 注意子串要与 words 中的单词完全匹配，中间不能有其他字符，但不需要考虑 words 中单词串联的顺序。
     *
     *
     *
     * 示例 1：
     *
     * 输入：
     *   s = "barfoothefoobarman",
     *   words = ["foo","bar"]
     * 输出：[0,9]
     * 解释：
     * 从索引 0 和 9 开始的子串分别是 "barfoor" 和 "foobar" 。
     * 输出的顺序不重要, [9,0] 也是有效答案。
     * 示例 2：
     *
     * 输入：
     *   s = "wordgoodgoodgoodbestword",
     *   words = ["word","good","best","word"]
     * 输出：[]
     *
     *
     * @param s
     * @param words
     * @return
     */
    public static List<Integer> findSubstring(String s, String[] words) {
        //构建滑动窗口，长度=所有words总长度；为了加快查询速度，将words存进map
        //窗口滑动距离为单个word长度，遇到map中不存在的直接移动到下一段并清空之前的记录重新开始；遇到子串数目超出的直接丢弃前一个子串前的数据
        //这样一共出现words.length()个窗口
        //TODO 应该是可以继续优化的，这样子跟暴力没多大区别
        Map<String ,Integer> map=new HashMap<>(  );
        for(String str:words){
            map.put( str,map.getOrDefault( str,0 ) +1);
        }
        List<Integer> list=new ArrayList<>();
        int windowLen=words.length*(words[1].length());
        int step=words[0].length();
        if(s.length()<windowLen){
            return list;
        }
        Map<String ,Integer> resMap=new HashMap<>(  );
        for(int i=0;i<step;i++){
            int start=i,end=start+windowLen;
            a:while ( end<=s.length()){
                String windowStr=s.substring( start,end );
                //验证子串是否满足条件
                 int left=start,right=start+step;
                 b:while ( right<=end ){
                     String temp=s.substring( left,right );
                     if(map.get( temp )==null){
                         start=right;
                         end=start+windowLen;
                         resMap.clear();
                         break;
                     }else {
                         if(resMap.getOrDefault( temp,0 )>=map.get( temp )){
                             //说明数量超出来了,窗口跳跃
                             resMap.clear();

                             start=start+windowStr.indexOf( temp )+step;
                             end=start+windowLen;
                             break ;
                         }else {
                             resMap.put(temp,resMap.getOrDefault( temp,0 )+1);
                             left+=step;
                             right=left+step;
                             if(right>end){
                                 //通过验证
                                 list.add( start );
                                 resMap.clear();
                                 start+=step;
                                 end+=step;
                                 break;
                             }
                         }
                     }
                 }

            }
        }
        return list;
    }

    //暴力法
    public List<Integer> findSubstring2(String s, String[] words) {
        List<Integer> result = new ArrayList<Integer>();
        if (s.length() == 0 || s == null || words.length == 0 || words == null){
            return result;
        }
        int size = words[0].length();
        int length = words.length;
        // 截取字符串时，取左不取右，所以这里的for循环中i的最大值可以取等号
        for (int i = 0; i <= s.length() - size * length; i++){
            HashMap<String, Integer> map = new HashMap<>();
            for (String word : words){
                map.put(word, map.getOrDefault(word, 0) + 1);
            }
            if (check(s,i,map,size)){
                result.add(i);
            }
        }
        return result;
    }

    private boolean check(String s, int i, HashMap<String, Integer> map, int size) {
        if (map.size() == 0){
            return true;
        }
        if (i > s.length() || i + size > s.length()){
            return false;
        }
        String word = s.substring(i, i + size);
        if (!map.containsKey(word)){
            return false;
        }else {
            Integer num = map.get(word);
            if (num <= 1){
                map.remove(word);
            }else {
                map.put(word, num - 1);
            }

            return check(s, i + size, map, size);
        }
    }

    //滑动窗口
    public List<Integer> findSubstring3(String s, String[] words) {
        List<Integer> result = new ArrayList<Integer>();
        // 如果s，或者是words为空，那么也返回一个空的列表
        if (s.length() == 0 || s == null || words.length == 0 || words == null){
            return result;
        }
        int size = words[0].length(), length = words.length;

        // 把字符串数组中的的字符串全部插入HashMap中
        HashMap<String, Integer> map = generate(words);
        // 窗口的不同的起点，有size个不同的起点
        for (int i = 0; i < size; i++){
            HashMap<String, Integer> window= new HashMap<>();  // 一个滑动的窗口
            int left,right;
            left = right = i;
            while (right <= s.length() - size && left <= s.length() - length * size){
                String word = s.substring(right, right + size);
                incr(window, word);
                if (!map.containsKey(word)){
                    window.clear();
                    right += size;
                    left = right;
                    continue;
                }
                while (window.get(word) > map.get(word)){
                    String w = s.substring(left, left + size);
                    decr(window, w);
                    left += size;
                }
                right += size;
                if (right - left == size * length){
                    result.add(left);
                }
            }
        }
        return result;
    }
    private HashMap<String, Integer> generate(String[] strs){
        HashMap<String, Integer> map = new HashMap<>();
        for (String str : strs){
            incr(map, str);
        }
        return map;
    }

    private void incr(HashMap<String, Integer> map, String str) {
        map.put(str, map.getOrDefault(str,0) + 1);
    }
    private void decr(HashMap<String, Integer> map, String str) {
        Integer num = map.get(str);
        if (num <= 1){
            map.remove(str);
        }else {
            map.put(str, num - 1);
        }
    }


    //滑动窗口
    public static List<Integer> findSubstring4(String s, String[] words) {
        List< Integer > result = new ArrayList< Integer >();
        // 如果s，或者是words为空，那么也返回一个空的列表
        if ( s.length() == 0 || s == null || words.length == 0 || words == null ) {
            return result;
        }
        Map<String,Integer> map=new HashMap<>(  );
        for(String str: words){
            map.put( str,map.getOrDefault( str,0 )+1 );
        }
        int step=words[0].length(),len=step*words.length;
        for(int i=0;i<step;i++){
            int start=i,end=i;
            Map<String,Integer> window=new HashMap<>(  );
            while ( start<=s.length()-len  && end<=s.length()-step ){
                String str=s.substring( end,end+step );
                window.put( str,window.getOrDefault( str,0 )+1 );
                if(map.get( str )==null){
                    window.clear();
                    end+=step;
                    start=end;
                    continue;
                }
                while (map.get( str )<window.getOrDefault( str,0 )){
                    //存在多于元素:左边界不断右移
                    String lstr=s.substring( start,start+step );
                    if(window.get( lstr )==1){
                        window.remove( lstr );
                    }else {
                        window.put( lstr,window.get( lstr )-1 );
                    }
                    start+=step;
                }
                end+=step;
                if(end-start==len){
                    result.add( start );
                }
            }
        }
        return result;

    }

    /**
     * 实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
     *
     * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
     *
     * 必须原地修改，只允许使用额外常数空间。
     *
     * 以下是一些例子，输入位于左侧列，其相应输出位于右侧列。
     * 1,2,3 → 1,3,2
     * 3,2,1 → 1,2,3
     * 1,1,5 → 1,5,1
     *
     * 题目是真的读不懂
     * 其实就是从数组倒着查找，找到nums[i] 比nums[i+1]小的时候，
     * 就将nums[i]跟nums[i+1]到nums[nums.length - 1]当中找到一个最小的比nums[i]大的元素交换。
     * 交换后，再把nums[i+1]到nums[nums.length-1]排序，就ok了
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        //i右侧递减
        while (i >= 0 && nums[i + 1] <= nums[i]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            //找到最小的比nums[i]大的值，交换
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);
    }

    private void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     *给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
     *
     * 示例 1:
     *
     * 输入: "(()"
     * 输出: 2
     * 解释: 最长有效括号子串为 "()"
     * 示例 2:
     *
     * 输入: ")()())"
     * 输出: 4
     * 解释: 最长有效括号子串为 "()()"
     * @param s
     * @return
     */
    public static int longestValidParentheses(String s) {
        //failed方法
        //移动窗口,同时计算(和)的数量，一旦right>left，窗口左侧收缩知道right=left
        //当right==left时，计算一次最大值
        char[] chars=s.toCharArray();
        int res=0;
        int left=0,right=0;
        int countL=0,countR=0;
        //还是应该先找到第一个"("再开始操作
        for(;left<chars.length;left++){
            if(chars[left]=='('){
                countL=1;
                break;
            }
        }
        if(left==chars.length-1){
            return 0;
        }
        right=left+1;
        while ( right<chars.length ){
            if(chars[right]=='('){
                countL++;
            }else {
                countR++;
            }
            if(countR==countL){
                res=Math.max( res,right-left+1 );
            }
            if(right==chars.length-1){
                //到头了
                if(countR<countL){

                    while (countR<=countL && left<right){
                        //左移直到countR=countL,
                        //还需要考虑括号的开闭问题，一旦左移，最前的(可能变成)
                        if(chars[left]=='('){
                            countL--;
                        }else {
                            countR--;
                        }
                        left++;
                        if(countR==countL && chars[left]=='('){
                            break;
                        }
                    }
                    res=Math.max( res,right-left+1 );
                }

            }
            //right必定为)
            if(countL<countR){
                left=right;
                while (  left<chars.length && chars[left]!='(' ){
                    left++;
                }
                countL=1;
                if(left==chars.length-1){
                    break;
                }else {
                    right=left+1;
                }
            }else {
                right++;
            }

        }
        return res;
    }

    public int longestValidParentheses2(String s) {
        char[] chars = s.toCharArray();
        //对字符串遍历，进行括弧有效性验证，记录最大的有效长度。同样的方式，倒序再来一次，取最大值
        return Math.max(calc(chars, 0, 1, chars.length, '('), calc(chars, chars.length -1, -1, -1, ')'));
    }
    private static int calc(char[] chars , int i ,  int flag,int end, char cTem){
        int max = 0, sum = 0, currLen = 0,validLen = 0;
        for (;i != end; i += flag) {
            sum += (chars[i] == cTem ? 1 : -1);
            currLen ++;
            if(sum < 0){
                max = max > validLen ? max : validLen;
                sum = 0;
                currLen = 0;
                validLen = 0;
            }else if(sum == 0){
                validLen = currLen;
            }
        }
        return max > validLen ? max : validLen;
    }

    public int longestValidParentheses3(String s) {
        //需有一个变量start记录有效括号子串的起始下标，max表示最长有效括号子串长度，初始值均为0
        //
        //2.遍历给字符串中的所有字符
        //
        //    2.1若当前字符s[index]为左括号'('，将当前字符下标index入栈（下标稍后有其他用处），处理下一字符
        //
        //    2.2若当前字符s[index]为右括号')'，判断当前栈是否为空
        //
        //        2.2.1若栈为空，则start = index + 1，处理下一字符（当前字符右括号下标不入栈）
        //
        //        2.2.2若栈不为空，则出栈（由于仅左括号入栈，则出栈元素对应的字符一定为左括号，可与当前字符右括号配对），判断栈是否为空
        //
        //            2.2.2.1若栈为空，则max = max(max, index-start+1)
        //
        //            2.2.2.2若栈不为空，则max = max(max, index-栈顶元素值)

        int max = 0, start = 0;
        if(null == s) return 0;

        int len = s.length();

        Stack<Integer> stack = new Stack<>();
        for(int index = 0; index < len; index++){
            //遇左括号(，压栈(栈中元素为当前位置所处的下标)
            if('(' == s.charAt(index)){
                stack.push(index);
                continue;
            } else {
                if(stack.isEmpty()){
                    start = index+1;
                    continue;
                } else {
                    stack.pop();
                    if(stack.isEmpty()){
                        max = Math.max(max, index-start+1);
                    } else {
                        max = Math.max(max, index-stack.peek());
                    }
                }
            }
        }

        return max;
    }

    public int longestValidParentheses4(String s) {
        //动态规划需用到辅助数组d[s.length()]，表示从当前字符开始，到字符串结尾的最长有效括号子串长度（当前字符需为有效括号子串的第一个字符）
        //
        //解题思路：从字符串结尾往前处理，求辅助数组d[]
        //
        //当前字符下标为index，若当前字符为左括号'('，判断index+1+d[index+1]位置的字符是否为右括号')'，若为右括号，则d[index] = d[index+1]+2，并且判断index+1+d[index+1]+1位置的元素是否存在，若存在，则d[index] += d[index+1+d[index+1]+1]（解决上述两个有效括号子串直接相邻的情况）

        if(null == s) return 0;

        int len = s.length(), max = 0;
        int[] d = new int[len];

        for(int index = len-2; index >= 0; index--){
            int symIndex = index+1+d[index+1];
            if('(' == s.charAt(index) && symIndex < len && ')' == s.charAt(symIndex)){
                d[index] = d[index+1]+2;
                if(symIndex+1 < len){
                    d[index] += d[symIndex+1];
                }
            }

            max = Math.max(max, d[index]);
        }
        return max;
    }

    /**
     * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
     *
     * ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
     *
     * 搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
     *
     * 你可以假设数组中不存在重复的元素。
     *
     * 你的算法时间复杂度必须是 O(log n) 级别。
     *
     * 示例 1:
     *
     * 输入: nums = [4,5,6,7,0,1,2], target = 0
     * 输出: 4
     * 示例 2:
     *
     * 输入: nums = [4,5,6,7,0,1,2], target = 3
     * 输出: -1
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        return search(nums, 0, nums.length - 1, target);
    }

    private int search(int[] nums, int low, int high, int target) {
        if (low > high)
            return -1;
        int mid = (low + high) / 2;
        if (nums[mid] == target)
            return mid;
        if (nums[mid] < nums[high]) {
            //当前必然处于连续区间
            if (nums[mid] < target && target <= nums[high])
                return search(nums, mid + 1, high, target);
            else
                return search(nums, low, mid - 1, target);
        } else {
            //非连续区间：一半连续，一半不连续
            if (nums[low] <= target && target < nums[mid])
                return search(nums, low, mid - 1, target);
            else
                return search(nums, mid + 1, high, target);
        }
    }

    /**
     *
     *给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
     *
     * 你的算法时间复杂度必须是 O(log n) 级别。
     *
     * 如果数组中不存在目标值，返回 [-1, -1]。
     *
     * 示例 1:
     *
     * 输入: nums = [5,7,7,8,8,10], target = 8
     * 输出: [3,4]
     * 示例 2:
     *
     * 输入: nums = [5,7,7,8,8,10], target = 6
     * 输出: [-1,-1]
     *
     * 二分法找到target，再左右遍历
     * @param nums
     * @param target
     * @return
     */
    public static int[] searchRange(int[] nums, int target) {
        int [] res=new int[]{-1,-1};
        if(nums.length==0){
            return res;
        }

        int left=0,right=nums.length-1,mid=0;
        boolean isExist=false;
        while ( right>left ){
            mid=(left+right)/2;
            if(nums[mid]==target){
                isExist=true;
                break;
            }else if(nums[mid]>target){
                right=mid-1;
            }else {
                left=mid+1;
            }

        }
        if(isExist){
            for(int i=mid;i<=nums.length-1;i++){
                if(nums[i]!=target){
                    res[1]=i-1;
                    break;
                }
                if(i==nums.length-1){
                    res[i]=i;
                }
            }
            for(int i=mid;i>=0;i--){
                if(nums[i]!=target){
                    res[0]=i+1;
                    break;
                }
                if(i==0){
                    res[i]=i;
                }
            }
        }
        return res;
    }


    public int[] searchRange2(int[] nums, int target) {
        //两次二分法，效率更高
        if (nums.length == 0)
            return new int[]{-1, -1};

        int left = findLeft(nums, 0, nums.length-1, target);
        int right = -1;
        if (left != -1)
            right = findRight(nums, 0, nums.length-1, target);
        return new int[]{left, right};
    }


    private int findLeft(int[] nums, int l, int r, int target) {
        if (l > r || (l == r && nums[l] != target))
            return -1;
        else if (l == r) {
            return l;
        }
        int mid = (l + r) / 2;
        if (nums[mid] > target) {
            return findLeft(nums, l, mid-1, target);
        }
        else if (nums[mid] < target) {
            return findLeft(nums, mid+1, r, target);
        }
        else {
            return findLeft(nums, l, mid, target);
        }
    }
    private int findRight(int[] nums, int l, int r, int target) {
        if (l > r || (l == r && nums[l] != target))
            return -1;
        else if (l == r)
            return r;
        int mid = (l + r + 1) / 2;
        if (nums[mid] > target) {
            return findRight(nums, l, mid-1, target);
        }
        else if (nums[mid] < target) {
            return findRight(nums, mid+1, r, target);
        }
        else {
            return findRight(nums, mid, r, target);
        }
    }

    /**
     * 判断一个 9x9 的数独是否有效。只需要根据以下规则，验证已经填入的数字是否有效即可。
     *
     * 数字 1-9 在每一行只能出现一次。
     * 数字 1-9 在每一列只能出现一次。
     * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
     *
     *
     * 上图是一个部分填充的有效的数独。
     *
     * 数独部分空格内已填入了数字，空白格用 '.' 表示。
     *
     * 示例 1:
     *
     * 输入:
     * [
     *   ["5","3",".",".","7",".",".",".","."],
     *   ["6",".",".","1","9","5",".",".","."],
     *   [".","9","8",".",".",".",".","6","."],
     *   ["8",".",".",".","6",".",".",".","3"],
     *   ["4",".",".","8",".","3",".",".","1"],
     *   ["7",".",".",".","2",".",".",".","6"],
     *   [".","6",".",".",".",".","2","8","."],
     *   [".",".",".","4","1","9",".",".","5"],
     *   [".",".",".",".","8",".",".","7","9"]
     * ]
     * 输出: true
     * 示例 2:
     *
     * 输入:
     * [
     *   ["8","3",".",".","7",".",".",".","."],
     *   ["6",".",".","1","9","5",".",".","."],
     *   [".","9","8",".",".",".",".","6","."],
     *   ["8",".",".",".","6",".",".",".","3"],
     *   ["4",".",".","8",".","3",".",".","1"],
     *   ["7",".",".",".","2",".",".",".","6"],
     *   [".","6",".",".",".",".","2","8","."],
     *   [".",".",".","4","1","9",".",".","5"],
     *   [".",".",".",".","8",".",".","7","9"]
     * ]
     * 输出: false
     * 解释: 除了第一行的第一个数字从 5 改为 8 以外，空格内其他数字均与 示例1 相同。
     *      但由于位于左上角的 3x3 宫内有两个 8 存在, 因此这个数独是无效的。
     * 说明:
     *
     * 一个有效的数独（部分已被填充）不一定是可解的。
     * 只需要根据以上规则，验证已经填入的数字是否有效即可。
     * 给定数独序列只包含数字 1-9 和字符 '.' 。
     * 给定数独永远是 9x9 形式的。
     *
     * @param board
     * @return
     */
    public boolean isValidSudoku(char[][] board) {
        //最容易想到的是的是对数独遍历三遍：确认每行，每列，每个子数独无重复
        //但实际上一次遍历即可找到结果
        HashMap<Integer, Integer> [] rows = new HashMap[9];
        HashMap<Integer, Integer> [] columns = new HashMap[9];
        //小方格的索引可以由(行/3)*3+列得到
        HashMap<Integer, Integer> [] boxes = new HashMap[9];
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                //行数为i,列数为j
                if(board[i][j]!='.'){
                    int num=(int)board[i][j];
                    if(rows[i]==null){
                        rows[i]=new HashMap<>(  );
                    }
                    if(rows[i].getOrDefault( num,0 )>0){
                        return false;
                    }else {
                        rows[i].put( num,1 );
                    }
                    if(columns[j]==null){
                        columns[j]=new HashMap<>(  );
                    }
                    if(columns[j].getOrDefault( num,0 )>0){
                        return false;
                    }else {
                        rows[j].put( num,1 );
                    }
                    int boxIndex=i/3*3+j/3;
                    if(boxes[boxIndex]==null){
                        boxes[boxIndex]=new HashMap<>(  );
                    }
                    if(boxes[boxIndex].getOrDefault( num ,0)>0){
                        return false;
                    }else {
                        boxes[boxIndex].put( num,1 );
                    }
                }
            }
        }
        return true;
    }

    public boolean isValidSudoku2(char[][] board) {
        // 记录某行，某位数字是否已经被摆放
        boolean[][] row = new boolean[9][10];
        // 记录某列，某位数字是否已经被摆放
        boolean[][] col = new boolean[9][10];
        // 记录某 3x3 宫格内，某位数字是否已经被摆放
        boolean[][] block = new boolean[9][10];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '0';
                    if (row[i][num] || col[j][num] || block[i / 3 * 3 + j / 3][num]) {
                        return false;
                    } else {
                        row[i][num] = true;
                        col[j][num] = true;
                        block[i / 3 * 3 + j / 3][num] = true;
                    }
                }
            }
        }
        return true;
    }


    /**
     * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     *
     * candidates 中的数字可以无限制重复被选取。
     *
     * 说明：
     *
     * 所有数字（包括 target）都是正整数。
     * 解集不能包含重复的组合。
     * 示例 1:
     *
     * 输入: candidates = [2,3,6,7], target = 7,
     * 所求解集为:
     * [
     *   [7],
     *   [2,2,3]
     * ]
     * 示例 2:
     *
     * 输入: candidates = [2,3,5], target = 8,
     * 所求解集为:
     * [
     *   [2,2,2,2],
     *   [2,3,3],
     *   [3,5]
     * ]
     * @param candidates
     * @param target
     * @return
     */
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res=new ArrayList<>(  );
        List<Integer> list=new ArrayList<>(  );
        combinationSumBackTrace( res,list,candidates,target,0 );
        return res;
    }

    public static void combinationSumBackTrace(List<List<Integer>> res,List<Integer> list,int[] candidates,int remain,int index){
        //回溯法
        if(remain==0){
            res.add( new ArrayList<>( list ) );
        }else if(remain<0){
            return;
        }else {
            for(int i=index;i<candidates.length;i++){
                list.add( candidates[i] );
                //注意这里不能改变remain值
                combinationSumBackTrace( res,list,candidates,remain-candidates[i],i );
                list.remove( list.size()-1 );
            }

        }
    }


    /**
     * 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     *
     * candidates 中的每个数字在每个组合中只能使用一次。
     *
     * 说明：
     *
     * 所有数字（包括目标数）都是正整数。
     * 解集不能包含重复的组合。
     * 示例 1:
     *
     * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
     * 所求解集为:
     * [
     *   [1, 7],
     *   [1, 2, 5],
     *   [2, 6],
     *   [1, 1, 6]
     * ]
     * 示例 2:
     *
     * 输入: candidates = [2,5,2,1,2], target = 5,
     * 所求解集为:
     * [
     *   [1,2,2],
     *   [5]
     * ]
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort( candidates );
        List<List<Integer>> res=new ArrayList<>(  );
        List<Integer> list=new ArrayList<>(  );
        combinationSumBackTrace2( res,list,candidates,target,0 );
        return res;
    }

    public static void combinationSumBackTrace2(List<List<Integer>> res,List<Integer> list,int[] candidates,int remain,int index){
        //回溯法
        if(remain==0){
            res.add( new ArrayList<>( list ) );
        }else if(remain<0){
            return;
        }else {
            for(int i=index;i<candidates.length;i++){

                list.add( candidates[i] );
                combinationSumBackTrace2( res,list,candidates,remain-candidates[i],i+1 );
                list.remove( list.size()-1 );

                //可能存在值同 但顺序不同的情况，所以在进行一次回溯后需要跳过相同值
                while ( i+1<candidates.length ){
                    if(candidates[i+1]==candidates[i]){
                        i++;
                    }else {
                        break;
                    }
                }
            }

        }
    }


    /**
     * 给定一个未排序的整数数组，找出其中没有出现的最小的正整数。
     *
     * 示例 1:
     *
     * 输入: [1,2,0]
     * 输出: 3
     * 示例 2:
     *
     * 输入: [3,4,-1,1]
     * 输出: 2
     * 示例 3:
     *
     * 输入: [7,8,9,11,12]
     * 输出: 1
     * 说明:
     *
     * 你的算法的时间复杂度应为O(n)，并且只能使用常数级别的空间。
     * @param nums
     * @return
     */
    public static int firstMissingPositive(int[] nums) {
        //利用索引:假设n个值，占满0-(n-1)，则n为所求值；无法占满，则最小值即为最小的无法占满值
        if(nums.length == 0)
            return 1;
        //第i位存放i+1的数值
        for(int i = 0; i < nums.length;i++){
            if(nums[i] > 0){//nums[i]为正数，放在i+1位置
                //假设交换的数据还是大于0且<i+1，则放在合适的位置,且数据不相等，避免死循环
                //这个while是关键，其它都是没有难度的
                while(nums[i] > 0 && nums[i] < i+1 && nums[i] != nums[nums[i] -1]){
                    int temp = nums[nums[i]-1];//交换数据
                    nums[nums[i]-1] = nums[i];
                    nums[i] = temp;
                }
            }
        }
        //循环寻找不符合要求的数据，返回
        for(int i = 0; i < nums.length;i++){
            if(nums[i] != i+1){
                return i+1;
            }
        }
        //假设都符合要求，则返回长度+1的值
        return nums.length + 1;
    }


    /**
     * 接雨水
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     *
     *
     *
     * 上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 感谢 Marcos 贡献此图。
     *
     * 示例:
     *
     * 输入: [0,1,0,2,1,0,1,3,2,1,2,1]
     * 输出: 6
     *
     * 找到最高点，将数组一分为二；再分别求和
     * @param height
     * @return
     */
    public int trap(int[] height) {
        int len=height.length;
        if(len<3){
            return 0;
        }
        int maxIndex=0,maxLeft=0,maxRight=0;
        for(int i=0;i<len;i++){
            maxIndex=height[maxIndex]>height[i]?maxIndex:i;
        }
        int res=0;
        for(int i=0;i<maxIndex;i++){
            if(maxLeft<=height[i]){
                maxLeft=height[i];
            }else {
                res+=maxLeft-height[i];
            }
        }
        for(int i=len-1;i>maxIndex;i--){
            if(maxRight<=height[i]){
                maxRight=height[i];
            }else {
                res+=maxRight-height[i];
            }
        }
        return res;
    }

    public int trap2(int[] height) {
        //用栈可以做:构建自底到顶递减的栈，如果为空或当前值小于等于栈顶值则入栈，当前值大于栈顶值则栈顶连续出栈直到栈顶值大于当前值
        //效率并不高
        Stack< int[] > stack=new Stack<>();
        int res=0;
        for(int i=0;i<height.length;i++){
            if(stack.isEmpty()){
                stack.add( new int[]{ height[i],i } );
            }else{
                if(stack.peek()[0]>=height[i]){
                    stack.add( new int[]{ height[i],i } );
                }else {
                    while ( !stack.isEmpty() && stack.peek()[0]<height[i] ){
                       int[] pari=stack.pop();
                        if(!stack.isEmpty()){
                            res+=(i-1-stack.peek()[1])*(Math.min(height[i],stack.peek()[0]) - pari[0] );
                        }
                    }
                    stack.add( new int[]{ height[i],i }  );
                }

            }
        }
        return res;
    }

    public int trap3(int[] height) {
        //双指针左右逼近，记录左右两端高度最高值，那么对于这两端最高值中间部分，如果高度低于两端最高值，能接的雨水取决于两端最高值中的最小值。
        int len=height.length,left=0,right=len-1;
        int maxLeft=0,maxRight=0,res=0;
        while ( left<right ){
            if(height[left]<height[right]){
                if(maxLeft<height[left]){
                    maxLeft=height[left];
                }else {
                    res+=maxLeft-height[left];
                }
                left++;
            }else {
                if(maxRight<height[right]){
                    maxRight=height[right];
                }else {
                    res+=maxRight-height[right];
                }
                right--;
            }
        }
        return res;
    }


    /**
     * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
     *
     * 示例 1:
     *
     * 输入: num1 = "2", num2 = "3"
     * 输出: "6"
     * 示例 2:
     *
     * 输入: num1 = "123", num2 = "456"
     * 输出: "56088"
     * 说明：
     *
     * num1 和 num2 的长度小于110。
     * num1 和 num2 只包含数字 0-9。
     * num1 和 num2 均不以零开头，除非是数字 0 本身。
     * 不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理
     *
     * char数组做乘法和加法
     * @param num1
     * @param num2
     * @return
     */
    public static String multiply(String num1, String num2) {
        //这个本方法太慢了
        if(num1.equals("0") || num2.equals("0")){
            return "0";
        }
        char[] chars=num1.toCharArray();
        List<Integer[]> reslist=new ArrayList<>(  );
        int len2=num2.length();
        for(int i=len2-1;i>=0;i--){
            multi( chars,num2.charAt( i )-'0',len2-i-1,reslist );
        }
        if(reslist.size()==0){
            return "";
        }
        Integer[] res=reslist.get( 0 );
        for(int i=1;i<reslist.size();i++){
           res=addSum( res,reslist.get( i ) );
        }
        StringBuffer sb=new StringBuffer(  );
        for(int i:res){
            sb.append( i );
        }
        return sb.toString();
    }

    public static void multi(char[] chars,int num,int power, List<Integer[]> reslist){
        if(num==0){
            reslist.add( new Integer[]{0} ) ;
            return;
        }
        List<Integer> list=new ArrayList<>(  );
        int res=0;
        while ( power>0 ){
            list.add( 0 );
            power--;
        }
        for(int i=chars.length-1;i>=0;i--){
            int k=chars[i]-'0';
            k=res+k*num;
            res=k/10;
            k=k%10;
            list.add( k );
        }
        if(res>0){
            list.add( res );
        }
        Integer[] arr=new Integer[list.size()];
        for(int i=0;i<arr.length;i++){
            arr[i]=list.get( arr.length-1-i );
        }
        reslist.add( arr );
    }

    public static Integer[] addSum(Integer[] arr1,Integer[] arr2){
        //数组相加
        int len=Math.min(arr1.length,arr2.length);
        int len1=arr1.length,len2=arr2.length;
        int res=0;
        List<Integer> list=new ArrayList<>(  );
        for(int i=0;i<len;i++){
            int k=arr1[len1-i-1]+arr2[len2-i-1]+res;
            list.add( k%10 );
            res=k/10;
        }
        if ( arr1.length>len ){
            for(int i=arr1.length-len-1;i>=0;i--){
                int k=arr1[i]+res;
                list.add( k%10 );
                res=k/10;
            }
        }
        if ( arr2.length>len ){
            for(int i=arr2.length-len-1;i>=0;i--){
                int k=arr2[i]+res;
                list.add( k%10 );
                res=k/10;
            }
        }
        if(res>0){
            list.add( res );
        }
        Integer[] arr=new Integer[list.size()];
        for(int i=0;i<arr.length;i++){
            arr[i]=list.get( arr.length-1-i );
        }
        return arr;
    }



    public String multiply2(String num1, String num2) {
//        根据数字乘法的计算规则，从一个数个位开始依次求出与另一个数的乘积并逐位相加。下面以“98”和“99”的乘法计算来说明算法思想。
//
//首先考虑乘积的总位数，两个数相乘的最大位数为两数的位数之和，所以先申请一个结果字符串位数为4，并且每一位都初始化为‘0’
//从第一个数的个位数‘8’开始，依次与“99”相乘。在乘法过程中首先初始化每一位置的进位add为0，然后计算出对应单个位的乘积mul，比如第一位8x9=72，然后取其个位与当前位置的数字以及前一位置的进位add相加得到sum，此时sum为2+0+0=2，所以结果字符串的个位数字就为‘2’。当前位置的进位add更新为mul的十位数与sum十位数之和，此时进位add为7+0=7.
//计算完一次单个位置的乘法后，最后将当前乘积的前一位更新为add，具体来说8x99=792，但遍历完99后结果只记录了最后两位“92”，此时进位add为7，所以要将前一位更新为7
//计算完结果后要判断输出的总位数，因为可能出现结果字符串前几位都是0的情况，找到第一位不是0的数字然后返回之后的字符串。
        char[] value = new char[num1.length() + num2.length()];
        for (int i = num1.length() - 1; i >= 0; i--) {
            for (int j = num2.length() - 1; j >= 0; j--) {
                value[i + j + 1] += (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
            }
        }
        // 处理进位
        int carry = 0;
        for (int i = value.length - 1; i >= 0; i--) {
            value[i] += carry;
            carry = value[i] / 10;
            value[i] %= 10;
        }
        int beginIndex = 0;
        while (beginIndex < value.length - 1 && value[beginIndex] == 0) {
            beginIndex++;
        }
        for (int i = beginIndex; i < value.length; i++) {
            value[i] += '0';
        }
        return new String(value, beginIndex, value.length - beginIndex);
    }

    /**
     * 通配符匹配
     *
     * 给定一个字符串 (s) 和一个字符模式 (p) ，实现一个支持 '?' 和 '*' 的通配符匹配。
     *
     * '?' 可以匹配任何单个字符。
     * '*' 可以匹配任意字符串（包括空字符串）。
     * 两个字符串完全匹配才算匹配成功。
     *
     * 说明:
     *
     * s 可能为空，且只包含从 a-z 的小写字母。
     * p 可能为空，且只包含从 a-z 的小写字母，以及字符 ? 和 *。
     * 示例 1:
     *
     * 输入:
     * s = "aa"
     * p = "a"
     * 输出: false
     * 解释: "a" 无法匹配 "aa" 整个字符串。
     * 示例 2:
     *
     * 输入:
     * s = "aa"
     * p = "*"
     * 输出: true
     * 解释: '*' 可以匹配任意字符串。
     * 示例 3:
     *
     * 输入:
     * s = "cb"
     * p = "?a"
     * 输出: false
     * 解释: '?' 可以匹配 'c', 但第二个 'a' 无法匹配 'b'。
     * 示例 4:
     *
     * 输入:
     * s = "adceb"
     * p = "*a*b"
     * 输出: true
     * 解释: 第一个 '*' 可以匹配空字符串, 第二个 '*' 可以匹配字符串 "dce".
     * 示例 5:
     *
     * 输入:
     * s = "acdcb"
     * p = "a*c?b"
     * 输入: false
     *
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch(String s, String p) {
        //动态规划
        boolean[][] value = new boolean[p.length()+1][s.length()+1];
        value[0][0] = true;
        for(int i = 1;i <= s.length();i++){
            value[0][i] = false;
        }
        for(int i = 1;i <= p.length(); i++){
            if(p.charAt(i-1) == '*'){
                value[i][0] = value[i-1][0];
                for(int j = 1;j <= s.length(); j++){
                    value[i][j] = (value[i][j-1] || value[i-1][j] || value[i-1][j-1]);
                }
            }else if(p.charAt(i-1) == '?'){
                value[i][0] = false;
                for(int j = 1;j <= s.length(); j++){
                    value[i][j] = value[i-1][j-1];
                }
            }else {
                value[i][0] = false;
                for(int j = 1;j <= s.length(); j++){
                    value[i][j] = s.charAt(j-1) == p.charAt(i-1) && value[i-1][j-1];
                }
            }
        }
        return value[p.length()][s.length()];
    }


    /**
     * 给定一个没有重复数字的序列，返回其所有可能的全排列。
     *
     * 示例:
     *
     * 输入: [1,2,3]
     * 输出:
     * [
     *   [1,2,3],
     *   [1,3,2],
     *   [2,1,3],
     *   [2,3,1],
     *   [3,1,2],
     *   [3,2,1]
     * ]
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        //回溯法:效率弟弟
        List<List<Integer>> res=new ArrayList<>(  );
        fullPermutation( nums,res,0 );
        return res;
    }

    public void fullPermutation(int[] nums,List<List<Integer>> res,int index){
        if(index==nums.length-1){
            res.add( Arrays.stream( nums ).boxed().collect( Collectors.toList()) );
        }else {
            //
            for(int i=index;i<nums.length;i++){
                //交换i与index
                int temp=nums[i];
                nums[i]=nums[index];
                nums[index]=temp;
                fullPermutation( nums,res,index+1 );
                //归位
                int temp0=nums[i];
                nums[i]=nums[index];
                nums[index]=temp0;
            }
        }
    }

    public List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> l1 = new ArrayList<Integer>();
        l1.add(nums[0]);
        result.add(l1);
        for(int i = 1;i<nums.length;i++){
            List<List<Integer>> resultx = new ArrayList<List<Integer>>();
            for(List<Integer> list:result){
                for(int j =0;j<=list.size();j++ ){
                    List<Integer> list2 = new ArrayList<Integer>();
                    list2.addAll(list);
                    list2.add(j,nums[i]) ;
                    resultx.add(list2);
                }
            }
            result = resultx;
        }
        return result;
    }

    public List<List<Integer>> permute3(int[] nums) {
        List<List<Integer>> res=new ArrayList<List<Integer>>();
        List<Integer> l=new ArrayList<Integer>();
        digui(nums,l,res);
        return res;
    }
    public static void digui(int[] a,List<Integer> l,List<List<Integer>> ll){
        //递归:这个无法过滤有重复的:除非时刻记录list中各元素的数量，加个map试试
        if(l.size()==a.length){
            //注意，不能是ll.add(l);
            ll.add(new ArrayList<Integer>(l));
        }else{
            for(int i=0;i<a.length;i++){
                if(l.contains(a[i])) continue;
                l.add(a[i]);
                digui(a,l,ll);
                //递归完删除list最后一项
                l.remove(l.size()-1);

            }
        }
    }

    /**
     * 给定一个可包含重复数字的序列，返回所有不重复的全排列。
     *
     * 示例:
     *
     * 输入: [1,1,2]
     * 输出:
     * [
     *   [1,1,2],
     *   [1,2,1],
     *   [2,1,1]
     * ]
     *
     * @param nums
     * @return
     */

    public List<List<Integer>> permuteUnique(int[] nums) {
        //回溯法:效率弟弟
        List<List<Integer>> res=new ArrayList<>(  );
        fullPermutationUnique( nums,res,0 );
        return res;
    }

    public void fullPermutationUnique(int[] nums,List<List<Integer>> res,int index){
        if(index==nums.length-1){
            res.add( Arrays.stream( nums ).boxed().collect( Collectors.toList()) );
        }else {
            Set<Integer> set=new HashSet<>(  );
            for(int i=index;i<nums.length;i++){
                if(set.contains(nums[i])){
                    continue;
                }
                set.add( nums[i] );
                //交换i与index
                int temp=nums[i];
                nums[i]=nums[index];
                nums[index]=temp;
                fullPermutationUnique( nums,res,index+1 );
                //归位
                int temp0=nums[i];
                nums[i]=nums[index];
                nums[index]=temp0;
            }
        }
    }

    Map<Integer,Integer> countMap=new HashMap<>(  );
    public List<List<Integer>> permuteUnique2(int[] nums) {
        //性能不错
        List<List<Integer>> res=new ArrayList<List<Integer>>();
        List<Integer> l=new ArrayList<Integer>();
        Map<Integer,Integer> map=new HashMap<>(  );
        for(int i=0;i<nums.length;i++){
            map.put( nums[i],map.getOrDefault( nums[i],0 )+1 );
        }
        fullPermutationUnique2(nums,l,res,map);
        return res;
    }
    public  void fullPermutationUnique2(int[] a,List<Integer> l,List<List<Integer>> ll, Map<Integer,Integer> map){
        //递归:这个无法过滤有重复的:除非时刻记录list中各元素的数量，加个map试试
        if(l.size()==a.length){
            //注意，不能是ll.add(l);
            ll.add(new ArrayList<Integer>(l));
        }else{
            Set<Integer> set=new HashSet<>();
            for(int i=0;i<a.length;i++){
                //set过滤掉同一位置出现相同字符的情况，map保证字符数控制在有效范围
                if(set.contains(a[i])){
                    continue;
                }
                if(map.get(a[i])==countMap.getOrDefault( a[i],0 )) continue;
                set.add( a[i] );
                l.add(a[i]);
                countMap.put( a[i],countMap.getOrDefault(a[i],0   ) +1);
                fullPermutationUnique2(a,l,ll,map);
                //递归完删除list最后一项
                countMap.put( a[i],countMap.get(a[i]) -1);
                l.remove(l.size()-1);

            }
        }
    }

    /**
     * 48. 旋转图像
     *
     * 给定一个 n × n 的二维矩阵表示一个图像。
     *
     * 将图像顺时针旋转 90 度。
     *
     * 说明：
     *
     * 你必须在原地旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要使用另一个矩阵来旋转图像。
     *
     * 示例 1:
     *
     * 给定 matrix =
     * [
     *   [1,2,3],
     *   [4,5,6],
     *   [7,8,9]
     * ],
     *
     * 原地旋转输入矩阵，使其变为:
     * [
     *   [7,4,1],
     *   [8,5,2],
     *   [9,6,3]
     * ]
     * 示例 2:
     *
     * 给定 matrix =
     * [
     *   [ 5, 1, 9,11],
     *   [ 2, 4, 8,10],
     *   [13, 3, 6, 7],
     *   [15,14,12,16]
     * ],
     *
     * 原地旋转输入矩阵，使其变为:
     * [
     *   [15,13, 2, 5],
     *   [14, 3, 4, 1],
     *   [12, 6, 8, 9],
     *   [16, 7,10,11]
     * ]
     *
     * 先沿左下-右上对角线翻折，再沿中间行翻折
     * 先沿左上-右下对角线翻折，再沿中间列翻折
     * y=-x+(len-1)
     * (x+x0)/2+(y+y0)/2=len-1
     * x+x0+y+y0=2(len-1)
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        int len=matrix.length;
        //对角线对折
        for(int i=0;i<len;i++){
            for(int j=i+1;j<len;j++){
                int temp=matrix[i][j];
                matrix[i][j]=matrix[j][i];
                matrix[j][i]=temp;
            }
        }
        //中间列对折
        for(int i=0;i<len;i++){
            for(int j=0;j<len/2;j++){
                int temp=matrix[i][j];
                matrix[i][j]=matrix[i][len-1-j];
                matrix[i][len-i-j]=temp;
            }

        }
    }

    /**
     * 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
     *
     * 示例:
     *
     * 输入: ["eat", "tea", "tan", "ate", "nat", "bat"],
     * 输出:
     * [
     *   ["ate","eat","tea"],
     *   ["nat","tan"],
     *   ["bat"]
     * ]
     * 说明：
     *
     * 所有输入均为小写字母。
     * 不考虑答案输出的顺序。
     * @param strs
     * @return
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        //先排序
        Map<String,List<String>> map=new HashMap<>(  );
        for(String str:strs){
            String sortStr=getSortStr(str);
            if(map.get( sortStr )!=null){
                //这里并不要求去重
                List<String> list=map.get( sortStr );
                list.add( str );
                map.put( sortStr,list );
            }else {
                List<String> list=new ArrayList<>(  );
                list.add( str );
                map.put( sortStr,list );
            }
        }
        List<List<String>> res=new ArrayList<>(  );
        for(String key:map.keySet()){
            res.add( map.get( key ) );
        }
        return res;
    }

    public static String getSortStr(String str){
        char [] chs=str.toCharArray();
        Arrays.sort( chs );
        return new String( chs );
    }

    /**
     * 实现 pow(x, n) ，即计算 x 的 n 次幂函数。
     *
     * 示例 1:
     *
     * 输入: 2.00000, 10
     * 输出: 1024.00000
     * 示例 2:
     *
     * 输入: 2.10000, 3
     * 输出: 9.26100
     * 示例 3:
     *
     * 输入: 2.00000, -2
     * 输出: 0.25000
     * 解释: 2-2 = 1/22 = 1/4 = 0.25
     * 说明:
     *
     * -100.0 < x < 100.0
     * n 是 32 位有符号整数，其数值范围是 [−231, 231 − 1] 。
     *
     * 需要区分正数还是负数
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        if(n>=0){
            return pow(x,n);
        }else {
            return 1/pow(x,-n);
        }
    }

    public double pow(double x,int n){
        if(n==0){
            return 1;
        }
        double res=pow( x,n/2 );
        res=res*res;
        if((n & 1)==1){
            //奇数
            res*=x;
        }
        return res;
    }

    /**
     * 51. N皇后
     * n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
     *
     *
     *
     * 上图为 8 皇后问题的一种解法。
     *
     * 给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。
     *
     * 每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
     *
     * 示例:
     *
     * 输入: 4
     * 输出: [
     *  [".Q..",  // 解法 1
     *   "...Q",
     *   "Q...",
     *   "..Q."],
     *
     *  ["..Q.",  // 解法 2
     *   "Q...",
     *   "...Q",
     *   ".Q.."]
     * ]
     * 解释: 4 皇后问题存在两个不同的解法。
     *
     * 实际上类似数独，每一行，每一列只能有一个数
     * 并且不能处于同一条对角线
     * 简化成求0-n-1的全排列，添加一个对角线检查即可
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens(int n) {
        List<Integer> list=new ArrayList<>(  );
        List<List<String>> res=new ArrayList<>(  );
        solveNQueensBacktrack( n,list,res );
        return res;
    }

    public void solveNQueensBacktrack(int n,List<Integer> list,List<List<String>> res){
        if(list.size()==n){
            List<String> strings=new ArrayList<>(  );
            StringBuffer sb=new StringBuffer(  );
            for(int i=0;i<n;i++){
                int val=list.get( i );
                sb.setLength( 0 );
                for(int j=0;j<n;j++){
                    if(j==val){
                        sb.append( "Q" );
                    }else {
                        sb.append( "." );
                    }
                }
                strings.add( sb.toString() );
            }
            res.add( strings );
        }else {
            for(int i=0;i<n;i++){
                //过滤重复
                if(list.contains( i )){
                    continue;
                }
                //检查对角线
                if(!checkDiagonal(list,i)){
                    continue;
                }
                list.add( i );
                solveNQueensBacktrack( n,list,res );
                list.remove( list.size()-1 );
            }
        }
    }

    public boolean checkDiagonal(List<Integer> list,int num){
        if(list.size()>0){
            int len=list.size();
            for(int i=0;i<len;i++){
                if(Math.abs( len-i )==Math.abs( num-list.get( i ) )){
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 52. N皇后 II
     *
     * 给定一个整数 n，返回 n 皇后不同的解决方案的数量。
     *
     * 示例:
     *
     * 输入: 4
     * 输出: 2
     * 解释: 4 皇后问题存在如下两个不同的解法。
     * [
     *  [".Q..",  // 解法 1
     *   "...Q",
     *   "Q...",
     *   "..Q."],
     *
     *  ["..Q.",  // 解法 2
     *   "Q...",
     *   "...Q",
     *   ".Q.."]
     * ]
     *
     * 问题I的简化版，统计数量即可
     * @param n
     * @return
     */
    public int totalNQueens(int n) {
        List<Integer> list=new ArrayList<>(  );
        Map<String,Integer> res=new HashMap<>(  );
        res.put( "count",0 );
        totalNQueensBacktrack( n,list,res );
        return res.get( "count" );
    }

    public void totalNQueensBacktrack(int n,List<Integer> list,  Map<String,Integer> res){
        if(list.size()==n){
            res.put( "count",res.get( "count" )+1 );;
        }else {
            for(int i=0;i<n;i++){
                //过滤重复
                if(list.contains( i )){
                    continue;
                }
                //检查对角线
                if(!checkDiagonal(list,i)){
                    continue;
                }
                list.add( i );
                totalNQueensBacktrack( n,list,res );
                list.remove( list.size()-1 );
            }
        }
    }


    /**
     * 54. 螺旋矩阵
     *
     * 给定一个包含 m x n 个元素的矩阵（m 行, n 列），请按照顺时针螺旋顺序，返回矩阵中的所有元素。
     *
     * 示例 1:
     *
     * 输入:
     * [
     *  [ 1, 2, 3 ],
     *  [ 4, 5, 6 ],
     *  [ 7, 8, 9 ]
     * ]
     * 输出: [1,2,3,6,9,8,7,4,5]
     * 示例 2:
     * 构造 一个相同的矩阵：记录走过的路径
     * * → ↓ ← ↑
     * 输入:
     * [
     *   [1, 2, 3, 4],
     *   [5, 6, 7, 8],
     *   [9,10,11,12]
     * ]
     * 输出: [1,2,3,4,8,12,11,10,9,5,6,7]
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder(int[][] matrix) {

        List<Integer> res=new ArrayList<>(  );
        if(matrix.length==0 ||matrix[0].length==0){
            return res;
        }
        int[][] placeholder=new int[matrix.length][matrix[0].length];
        int x=0,y=0;
        res.add( matrix[0][0] );
        placeholder[0][0]=1;
        int[][] directions=new int[4][];
        directions[0]=new int[]{0,1};
        directions[1]=new int[]{1,0};
        directions[2]=new int[]{0,-1};
        directions[3]=new int[]{-1,0};
        //计算新方向
        int[] curDirection=getDirection( placeholder,x,y,directions );
        while ( curDirection!=null ){
            //检查
            while ( check( placeholder,x,y,curDirection ) ){
                x+=curDirection[0];
                y+=curDirection[1];
                res.add( matrix[x][y] );
                placeholder[x][y]=1;
            }
            curDirection=getDirection( placeholder,x,y,directions);
        }
        return res;
    }

    public boolean check(int[][] placeholder,int x,int y,int[] direction){
            x+=direction[0];
            y+=direction[1];
            if(x<placeholder.length && x>=0 && y<placeholder[0].length && y>=0 && placeholder[x][y]==0 ){
                return true;
            }
        return false;
    }

    public  int[] getDirection(int[][] placeholder,int x,int y,int[][] directions){
        for(int i=0;i<directions.length;i++){
            x+=directions[i][0];
            y+=directions[i][1];
            if(x<placeholder.length && x>=0 && y<placeholder[0].length && y>=0 && placeholder[x][y]==0 ){
                return directions[i];
            }
            x-=directions[i][0];
            y-=directions[i][1];
        }
        return null;
    }

    /**
     * 55. 跳跃游戏
     *
     * 给定一个非负整数数组，你最初位于数组的第一个位置。
     *
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     *
     * 判断你是否能够到达最后一个位置。
     *
     * 示例 1:
     *
     * 输入: [2,3,1,1,4]
     * 输出: true
     * 解释: 从位置 0 到 1 跳 1 步, 然后跳 3 步到达最后一个位置。
     * 示例 2:
     *
     * 输入: [3,2,1,0,4]
     * 输出: false
     * 解释: 无论怎样，你总会到达索引为 3 的位置。但该位置的最大跳跃长度是 0 ， 所以你永远不可能到达最后一个位置。
     *
     * 从终点倒序寻找可以直接到达终点的点，存进set,继续寻找可以直接到达set中元素的点
     *
     * 实际上这个问题的步数是最大步数，所以只需要能达到最靠前的位置即可
     *
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        if(nums.length==0){
            return false;
        }
        int len=nums.length-1;
        int i=nums.length-2;
        for(;i>=0;i--){
            if(nums[i]+i>=len){
                len=nums[i];
            }else if(i==0){
                return false;
            }
        }
        return true;
    }


    /**
     * 56.合并区间
     * 给出一个区间的集合，请合并所有重叠的区间。
     *
     * 示例 1:
     *
     * 输入: [[1,3],[2,6],[8,10],[15,18]]
     * 输出: [[1,6],[8,10],[15,18]]
     * 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
     * 示例 2:
     *
     * 输入: [[1,4],[4,5]]
     * 输出: [[1,5]]
     * 解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。
     *
     *
     *
     * @param intervals
     * @return
     */

    public List<Interval> merge(List<Interval> intervals) {
        List<Interval> res=new LinkedList<>(  );
        if(intervals.size()==0){
            return res;
        }
        Collections.sort( intervals, new Comparator< Interval >() {
            @Override
            public int compare( Interval o1, Interval o2 ) {
                return o1.start-o2.start;
            }
        } );

        Interval cur=intervals.get( 0 );
        for(int i=1;i<intervals.size();i++){
            Interval interval=intervals.get( i );
            if(cur.end<interval.start){
                res.add( new Interval( cur.start,cur.end ) );
                cur=interval;
            }else if(cur.end>=interval.start && cur.end<=interval.end){
                cur= new Interval( cur.start,interval.end );
            }
        }
        res.add( cur );
        return res;
    }

    public class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }


    /**
     * 57. 插入区间
     *
     * 给出一个无重叠的 ，按照区间起始端点排序的区间列表。
     *
     * 在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
     *
     * 示例 1:
     *
     * 输入: intervals = [[1,3],[6,9]], newInterval = [2,5]
     * 输出: [[1,5],[6,9]]
     * 示例 2:
     *
     * 输入: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
     * 输出: [[1,2],[3,10],[12,16]]
     * 解释: 这是因为新的区间 [4,8] 与 [3,5],[6,7],[8,10] 重叠。
     * @param intervals
     * @param newInterval
     * @return
     */
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        intervals.add( newInterval );
        List<Interval> res=new LinkedList<>(  );
        Collections.sort( intervals, new Comparator< Interval >() {
            @Override
            public int compare( Interval o1, Interval o2 ) {
                return o1.start-o2.start;
            }
        } );

        Interval cur=intervals.get( 0 );
        for(int i=1;i<intervals.size();i++){
            Interval interval=intervals.get( i );
            if(cur.end<interval.start){
                res.add( new Interval( cur.start,cur.end ) );
                cur=interval;
            }else if(cur.end>=interval.start && cur.end<=interval.end){
                cur= new Interval( cur.start,interval.end );
            }
        }
        res.add( cur );
        return res;
    }


    /**
     * 59. 螺旋矩阵 II
     *
     * 给定一个正整数 n，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的正方形矩阵。
     *
     * 示例:
     *
     * 输入: 3
     * 输出:
     * [
     *  [ 1, 2, 3 ],
     *  [ 8, 9, 4 ],
     *  [ 7, 6, 5 ]
     * ]
     *
     * @param n
     * @return
     */
    public int[][] generateMatrix(int n) {
        int[][] placeholder=new int[n][n];
        if(n==0){
            return placeholder;
        }
        int x=0,y=0;
        placeholder[0][0]=1;
        int[][] directions=new int[4][];
        directions[0]=new int[]{0,1};
        directions[1]=new int[]{1,0};
        directions[2]=new int[]{0,-1};
        directions[3]=new int[]{-1,0};
        //计算新方向
        int[] curDirection=getDirection( placeholder,x,y,directions );
        int k=1;
        while ( curDirection!=null ){
            //检查
            while ( check( placeholder,x,y,curDirection ) ){
                x+=curDirection[0];
                y+=curDirection[1];
                k++;
                placeholder[x][y]=k;
            }
            curDirection=getDirection( placeholder,x,y,directions);
        }
        return placeholder;
    }

    /**
     * 60. 第k个排列
     *
     * 给出集合 [1,2,3,…,n]，其所有元素共有 n! 种排列。
     *
     * 按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
     *
     * "123"
     * "132"
     * "213"
     * "231"
     * "312"
     * "321"
     * 给定 n 和 k，返回第 k 个排列。
     *
     * 1234
     * 1243
     * 1324
     * 1342
     * 1423
     * 1432
     *
     * 2134
     * 2143
     * 23
     * 说明：
     *
     * 给定 n 的范围是 [1, 9]。
     * 给定 k 的范围是[1,  n!]。
     * 示例 1:
     *
     * 输入: n = 3, k = 3
     * 输出: "213"
     * 示例 2:
     *
     * 输入: n = 4, k = 9
     * 输出: "2314"
     * @param n
     * @param k
     * @return
     */
    public static String getPermutation(int n, int k) {
        //可以一步一步找出k所在位置
        //分n层
        int[] res=new int[n-1];
        int[] counts=new int[n-1];
        int count=1;
        for(int i=0;i<n-1;i++){
            count*=(i+1);
            counts[n-i-2]=count;
        }
        k=k-1;
        for(int i=0;i<n-1;i++){
            res[i]=(k-1)/counts[i];
            k%=counts[i];
        }
        List<Integer> list=new ArrayList<>(  );
        for(int i=0;i<n;i++){
            list.add( i+1 );
        }
        StringBuffer sb=new StringBuffer(  );
        for(int i=0;i<n-1;i++){
            sb.append( list.get( res[i] ) );
            list.remove( res[i] );
        }
        sb.append( list.get( 0 ) );
        return sb.toString();
    }
    /**
     * 62. 不同路径
     *
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
     *
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
     *
     * 问总共有多少条不同的路径？
     *
     *
     *
     * 例如，上图是一个7 x 3 的网格。有多少可能的路径？
     *
     * 说明：m 和 n 的值均不超过 100。
     *
     * 示例 1:
     *
     * 输入: m = 3, n = 2
     * 输出: 3
     * 解释:
     * 从左上角开始，总共有 3 条路径可以到达右下角。
     * 1. 向右 -> 向右 -> 向下
     * 2. 向右 -> 向下 -> 向右
     * 3. 向下 -> 向右 -> 向右
     * 示例 2:
     *
     * 输入: m = 7, n = 3
     * 输出: 28
     *
     * 一共走 m+n-2 步
     * m-1 , n-1
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0)
                    dp[i][j] = 1;
                else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * 63. 不同路径 II
     *
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
     *
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
     *
     * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
     *
     *
     *
     * 网格中的障碍物和空位置分别用 1 和 0 来表示。
     *
     * 说明：m 和 n 的值均不超过 100。
     *
     * 示例 1:
     *
     * 输入:
     * [
     *   [0,0,0],
     *   [0,1,0],
     *   [0,0,0]
     * ]
     * 输出: 2
     * 解释:
     * 3x3 网格的正中间有一个障碍物。
     * 从左上角到右下角一共有 2 条不同的路径：
     * 1. 向右 -> 向右 -> 向下 -> 向下
     * 2. 向下 -> 向下 -> 向右 -> 向右
     *
     * 只需要处理下第一行/列：障碍物后面的全部置0
     * 其他行只要在障碍处置0即可
     * @param obstacleGrid
     * @return
     */
    public static int uniquePathsWithObstacles( int[][] obstacleGrid ) {
        int m = obstacleGrid.length, n = obstacleGrid[ 0 ].length;
        int[][] dp = new int[ m ][ n ];
        for ( int i = 0; i < m; i++ ) {
            for ( int j = 0; j < n; j++ ) {
                if ( i == 0 || j == 0 ) {
                    if ( obstacleGrid[ i ][ j ] == 1
                            || ( i == 0 && j > 0 && dp[ i ][ j - 1 ] == 0 )
                            || ( j == 0 && i > 0 && dp[ i - 1 ][ j ] == 0 ) ) {
                        dp[ i ][ j ] = 0;
                    } else
                        dp[ i ][ j ] = 1;
                } else {
                    if ( obstacleGrid[ i ][ j ] == 1 ) {
                        dp[ i ][ j ] = 0;
                    } else {
                        dp[ i ][ j ] = dp[ i - 1 ][ j ] + dp[ i ][ j - 1 ];
                    }
                }
            }
        }
        return dp[ m - 1 ][ n - 1 ];
    }

    /**
     * 64. 最小路径和
     *
     * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     *
     * 说明：每次只能向下或者向右移动一步。
     *
     * 示例:
     *
     * 输入:
     * [
     *   [1,3,1],
     *   [1,5,1],
     *   [4,2,1]
     * ]
     * 输出: 7
     * 解释: 因为路径 1→3→1→1→1 的总和最小。
     *
     * 1.求出所有路径，计算和
     *
     * (m-1)个0和(n-1)个1的全排列问题
     *
     * 2.动态规划
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int lenth=grid.length;
        int width=grid[0].length;

        int[][] pathSums=new int[lenth][width];
        pathSums[0][0]=grid[0][0];
        for(int i=0;i<lenth;i++){
            for(int j=0;j<width;j++){
                if(i==0 && j==0){

                }else if(i==0){
                    pathSums[i][j]=pathSums[i][j-1]+grid[i][j];
                }else if(j==0){
                    pathSums[i][j]=pathSums[i-1][j]+grid[i][j];
                }else {
                    pathSums[i][j]=Math.min( pathSums[i][j-1],pathSums[i-1][j] )+grid[i][j];
                }
            }
        }
        return pathSums[lenth-1][width-1];
    }

    /**
     * 68. 文本左右对齐
     *
     * 给定一个单词数组和一个长度 maxWidth，重新排版单词，使其成为每行恰好有 maxWidth 个字符，且左右两端对齐的文本。
     *
     * 你应该使用“贪心算法”来放置给定的单词；也就是说，尽可能多地往每行中放置单词。必要时可用空格 ' ' 填充，使得每行恰好有 maxWidth 个字符。
     *
     * 要求尽可能均匀分配单词间的空格数量。如果某一行单词间的空格不能均匀分配，则左侧放置的空格数要多于右侧的空格数。
     *
     * 文本的最后一行应为左对齐，且单词之间不插入额外的空格。
     *
     * 说明:
     *
     * 单词是指由非空格字符组成的字符序列。
     * 每个单词的长度大于 0，小于等于 maxWidth。
     * 输入单词数组 words 至少包含一个单词。
     * 示例:
     *
     * 输入:
     * words = ["This", "is", "an", "example", "of", "text", "justification."]
     * maxWidth = 16
     * 输出:
     * [
     *    "This    is    an",
     *    "example  of text",
     *    "justification.  "
     * ]
     * 示例 2:
     *
     * 输入:
     * words = ["What","must","be","acknowledgment","shall","be"]
     * maxWidth = 16
     * 输出:
     * [
     *   "What   must   be",
     *   "acknowledgment  ",
     *   "shall be        "
     * ]
     * 解释: 注意最后一行的格式应为 "shall be    " 而不是 "shall     be",
     *      因为最后一行应为左对齐，而不是左右两端对齐。
     *      第二行同样为左对齐，这是因为这行只包含一个单词。
     * 示例 3:
     *
     * 输入:
     * words = ["Science","is","what","we","understand","well","enough","to","explain",
     *          "to","a","computer.","Art","is","everything","else","we","do"]
     * maxWidth = 20
     * 输出:
     * [
     *   "Science  is  what we",
     *   "understand      well",
     *   "enough to explain to",
     *   "a  computer.  Art is",
     *   "everything  else  we",
     *   "do                  "
     * ]
     *
     * 耐心
     * 性能上没有啥优化空间
     * @param words
     * @param maxWidth
     * @return
     */
    public static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res=new ArrayList<>(  );
        if(words.length==0){
            return res;
        }
        StringBuffer sb=new StringBuffer(  );
        int len=0,start=0,end=0;
        for(int i=0;i<words.length;i++){
            if(len>0){
                if(len+(end-start)+words[i].length()>=maxWidth){
                    if(end-start==0){
                        sb.append( words[end] );
                        for(int j=0;j<maxWidth-words[end].length();j++){
                            sb.append( " " );
                        }
                    }else {
                        int spaceCount=(maxWidth-len)/(end-start);
                        int spareCount=(maxWidth-len)%(end-start);
                        for(int j=start;j<=end;j++){
                            sb.append( words[j] );
                            if(j!=end){
                                for(int k=0;k<spaceCount;k++){
                                    sb.append( " " );
                                }
                                if(spareCount>0){
                                    sb.append( " " );
                                    spareCount--;
                                }
                            }

                        }
                    }

                    //关键在于如何填充空格
                    res.add( sb.toString() );
                    sb.setLength( 0 );
                    start=end=i;
                    len=words[i].length();
                }else {
                    len+=words[i].length();
                    end=i;
                }

            }else {
                start=i;
                end=i;
                len+=words[i].length();
            }
        }
        if(len>0){
            for(int j=start;j<=end;j++){
                sb.append( words[j] );
                if(j!=end){
                    sb.append( " " );
                }

            }
            for(int j=sb.length();j<maxWidth;j++){
                sb.append( " " );
            }
            res.add( sb.toString() );
        }
        return res;

    }


    /**
     * 71. 简化路径
     *
     * 以 Unix 风格给出一个文件的绝对路径，你需要简化它。或者换句话说，将其转换为规范路径。
     *
     * 在 Unix 风格的文件系统中，一个点（.）表示当前目录本身；此外，两个点 （..） 表示将目录切换到上一级（指向父目录）；两者都可以是复杂相对路径的组成部分。更多信息请参阅：Linux / Unix中的绝对路径 vs 相对路径
     *
     * 请注意，返回的规范路径必须始终以斜杠 / 开头，并且两个目录名之间必须只有一个斜杠 /。最后一个目录名（如果存在）不能以 / 结尾。此外，规范路径必须是表示绝对路径的最短字符串。
     *
     *
     *
     * 示例 1：
     *
     * 输入："/home/"
     * 输出："/home"
     * 解释：注意，最后一个目录名后面没有斜杠。
     * 示例 2：
     *
     * 输入："/../"
     * 输出："/"
     * 解释：从根目录向上一级是不可行的，因为根是你可以到达的最高级。
     * 示例 3：
     *
     * 输入："/home//foo/"
     * 输出："/home/foo"
     * 解释：在规范路径中，多个连续斜杠需要用一个斜杠替换。
     * 示例 4：
     *
     * 输入："/a/./b/../../c/"
     * 输出："/c"
     * 示例 5：
     *
     * 输入："/a/../../b/../c//.//"
     * 输出："/c"
     * 示例 6：
     *
     * 输入："/a//b////c/d//././/.."
     * 输出："/a/b/c"
     *
     * 多个/合并成一个
     * 去掉最后一个/
     * 单个.直接忽略
     * ..返回上级目录
     *
     * @param path
     * @return
     */
    public String simplifyPath(String path) {
        // 利用栈或队列来解决
        LinkedList<String> stack=new LinkedList<String>(  );
       String[] strs=path.split( "/" );
       for(String str:strs){
           if(str.equals( "" ) || str.equals( "." )){
               continue;
           }else if(str.equals( ".." )){
               if(!stack.isEmpty()){
                   stack.removeLast();
               }
           }else {
               stack.add( str );
           }
       }
       if(stack.isEmpty()){
           return "/";
       }else {
           StringBuffer sb=new StringBuffer(  );
           while ( !stack.isEmpty() ){
               sb.append( "/" ).append( stack.removeFirst() );
           }
           return sb.toString();
       }
    }


    /**
     *
     * 72. 编辑距离
     *
     * 给定两个单词 word1 和 word2，计算出将 word1 转换成 word2 所使用的最少操作数 。
     *
     * 你可以对一个单词进行如下三种操作：
     *
     * 插入一个字符
     * 删除一个字符
     * 替换一个字符
     * 示例 1:
     *
     * 输入: word1 = "horse", word2 = "ros"
     * 输出: 3
     * 解释:
     * horse -> rorse (将 'h' 替换为 'r')
     * rorse -> rose (删除 'r')
     * rose -> ros (删除 'e')
     * 示例 2:
     *
     * 输入: word1 = "intention", word2 = "execution"
     * 输出: 5
     * 解释:
     * intention -> inention (删除 't')
     * inention -> enention (将 'i' 替换为 'e')
     * enention -> exention (将 'n' 替换为 'x')
     * exention -> exection (将 'n' 替换为 'c')
     * exection -> execution (插入 'u')
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        /**
         dp[i][j] 表示 word1 的第 [1~i] 位和 word2 的 [1~j] 位相同时
         所需的最少操作数. 则递推式为:
         if(word1[i] == word2[j])
         dp[i][j] = dp[i-1][j-1]
         else
         dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1
         分别对应在 word1 的第 i 个位置插入、删除和替换字符操作

         **/
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m+1][n+1];
        // 边界情况, 将一个字符串转换成空字符串的操作
        for(int i = 1; i <= m; i++) dp[i][0] = i;
        for(int i = 1; i <= n; i++) dp[0][i] = i;

        for(int i = 1; i <= m; ++i) {
            for(int j = 1; j <= n; ++j) {
                if(word1.charAt(i-1) == word2.charAt(j-1))
                    dp[i][j] = dp[i-1][j-1];
                else
                    dp[i][j] = Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1])) + 1;
            }
        }

        return dp[m][n];
    }


    /**
     * 73. 矩阵置零
     *
     * 给定一个 m x n 的矩阵，如果一个元素为 0，则将其所在行和列的所有元素都设为 0。请使用原地算法。
     *
     * 示例 1:
     *
     * 输入:
     * [
     *   [1,1,1],
     *   [1,0,1],
     *   [1,1,1]
     * ]
     * 输出:
     * [
     *   [1,0,1],
     *   [0,0,0],
     *   [1,0,1]
     * ]
     * 示例 2:
     *
     * 输入:
     * [
     *   [0,1,2,0],
     *   [3,4,5,2],
     *   [1,3,1,5]
     * ]
     * 输出:
     * [
     *   [0,0,0,0],
     *   [0,4,5,0],
     *   [0,3,1,0]
     * ]
     * 进阶:
     *
     * 一个直接的解决方案是使用  O(mn) 的额外空间，但这并不是一个好的解决方案。
     * 一个简单的改进方案是使用 O(m + n) 的额外空间，但这仍然不是最好的解决方案。
     * 你能想出一个常数空间的解决方案吗？
     * @param matrix
     */
    public void setZeroes(int[][] matrix) {
        //m+n空间
        int m=matrix.length,n=matrix[0].length;
        boolean [] mFlags=new boolean[m];
        boolean [] nFlags=new boolean[n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(matrix[i][j]==0){
                    mFlags[i]=true;
                    nFlags[j]=true;
                }
            }
        }
        for(int i=0;i<m;i++){
            if(mFlags[i]){
                Arrays.fill(matrix[i],0);
            }
        }
        for(int i=0;i<n;i++){
            if(nFlags[i]){
                for(int j=0;j<m;j++){
                    matrix[j][i]=0;
                }
            }
        }
    }

    public void setZeroes_2(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        // 用于判断第一行和第一列原本是否有 0
        boolean mFlag = false, nFlag = false;
        for (int i = 0; i < n; i++) {
            if (matrix[0][i] == 0) {
                mFlag = true;
                break;
            }
        }
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                nFlag = true;
                break;
            }
        }
        // 将所有的 0 映射到第一行和第一列
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        // 参照第一行和第一列，置换当前位置的元素
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        // 若第一行和第一列原本有 0，则也需要将第一行和第一列的元素全部置换为 0
        if (mFlag) {
            for (int i = 0; i < n; i++) {
                matrix[0][i] = 0;
            }
        }
        if (nFlag) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    /**
     * 编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：
     *
     * 每行中的整数从左到右按升序排列。
     * 每行的第一个整数大于前一行的最后一个整数。
     * 示例 1:
     *
     * 输入:
     * matrix = [
     *   [1,   3,  5,  7],
     *   [10, 11, 16, 20],
     *   [23, 30, 34, 50]
     * ]
     * target = 3
     * 输出: true
     * 示例 2:
     *
     * 输入:
     * matrix = [
     *   [1,   3,  5,  7],
     *   [10, 11, 16, 20],
     *   [23, 30, 34, 50]
     * ]
     * target = 13
     * 输出: false
     * @param matrix
     * @param target
     * @return
     */
    public static boolean searchMatrix(int[][] matrix, int target) {
        //先找到行，再二分查找,性能会更好
//        int row=-1;
//        if(matrix.length==0 || matrix[0].length==0){
//            return false;
//        }
//        int m=matrix.length,n=matrix[0].length;
//        for(int i=0;i<m;i++){
//            if(target==matrix[i][n-1]){
//                return true;
//            }else if(target<matrix[i][n-1]){
//
//                if(target<matrix[i][0]){
//                    return false;
//                }else if(target==matrix[i][0]){
//                   return true;
//                }else {
//                    row=i;
//                    break;
//                }
//            }
//        }
//        if(row==-1){
//            return false;
//        }
//        //二分查找
//        int start=0,end=n-1;
//        int mid=0;
//        while ( start<=end ){
//            mid=(start+end)/2;
//            if(matrix[row][mid]==target){
//                return true;
//            }else if(matrix[row][mid]>target){
//                end=mid-1;
//            }else {
//                start=mid+1;
//            }
//        }
//        return false;
        if(matrix.length == 0)
            return false;
        int row = 0, col = matrix[0].length-1;
        while(row < matrix.length && col >= 0){
            if(matrix[row][col] < target)
                row++;
            else if(matrix[row][col] > target)
                col--;
            else
                return true;
        }
        return false;
    }

    /**
     * 75. 颜色分类
     *
     * 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
     *
     * 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
     *
     * 注意:
     * 不能使用代码库中的排序函数来解决这道题。
     *
     * 示例:
     *
     * 输入: [2,0,2,1,1,0]
     * 输出: [0,0,1,1,2,2]
     * 进阶：
     *
     * 一个直观的解决方案是使用计数排序的两趟扫描算法。
     * 首先，迭代计算出0、1 和 2 元素的个数，然后按照0、1、2的排序，重写当前数组。
     * 你能想出一个仅使用常数空间的一趟扫描算法吗？
     * @param nums
     */
    public void sortColors(int[] nums) {
        //维护一个0的索引和2的索引,遍历数组，0则与index0交换，2则与index2交换，1不动，直到index=index0
        int left=0;
        int index0=0,index2=nums.length-1;
        while ( left<=index2 ){
            if(nums[left]==0){
                if(left>index0){
                    swaps(nums,left,index0);
                }
                left++;
                index0++;
            }else if(nums[left]==2){
                swaps(nums,left,index2);
                index2--;
            }else {
                left++;
            }
        }

    }

    public void swaps(int [] nums,int i,int j){
        int temp = nums[i];
        nums[i]=nums[j];
        nums[j]=temp;
    }

    /**
     * 给定一个字符串 S 和一个字符串 T，请在 S 中找出包含 T 所有字母的最小子串。
     *
     * 示例：
     *
     * 输入: S = "ADOBECODEBANC", T = "ABC"
     * 输出: "BANC"
     * 说明：
     *
     * 如果 S 中不存这样的子串，则返回空字符串 ""。
     * 如果 S 中存在这样的子串，我们保证它是唯一的答案。
     * @param s
     * @param t
     * @return
     */
//    public static String minWindow(String s, String t) {
        //仅仅只是找出了第一个符合条件的子串，需要继续查找，操作三个map也显得笨重
//        Map<Character,Integer> mapt = new HashMap<>(  );
//        for(int i=0;i<t.length();i++){
//            mapt.put( t.charAt( i ),mapt.getOrDefault( t.charAt( i ),0 ) + 1 );
//        }
//        int left=0,right=0;
//        //先确定right是否存在
//        Map<Character,Integer> integerMap= new HashMap<>( mapt );
//        Map<Character,Integer> maps= new HashMap<>( );
//        while ( !integerMap.isEmpty() && right<s.length() ){
//            if(mapt.containsKey( s.charAt( right ) )){
//                if(integerMap.containsKey( s.charAt(right) )){
//                    integerMap.put( s.charAt( right ),integerMap.get( s.charAt( right ) )-1 );
//                    if(integerMap.get( s.charAt( right ) )==0){
//                        integerMap.remove( s.charAt( right ) );
//                    }
//                }
//                maps.put( s.charAt( right ),maps.getOrDefault( s.charAt( right ),0 )+1 );
//            }
//            right++;
//        }
//        if(!integerMap.isEmpty()){
//            return "";
//        }
//        for(;left<right;left++){
//            if(maps.containsKey( s.charAt( left ) )){
//                if(maps.get( s.charAt( left ) )>mapt.get( s.charAt( left ) )){
//                    maps.put( s.charAt( left ),maps.get( s.charAt( left ) )-1 );
//                }else {
//                    break;
//                }
//            }
//        }
//        return s.substring( left,right );
//    }

    public static String minWindow(String s, String t) {
        //没有复用，效率偏低
        int begin=0,end=0,res=s.length()+1;
        int l=0,r=-1;
        while ( l<s.length() ){
            if(r<s.length()-1 && !includes( s,t,l,r )){
                r++;
            }else {
                if(includes( s,t,l,r ) && res>r-l+1){
                    res=r-l+1;
                    begin=l;
                    end=r;
                }
                //
                l++;
            }
        }
        if(res>s.length()){
            return "";
        }
        return s.substring( begin,end+1 );
    }

    public static boolean includes(String s, String t, int l, int r) {//[l,r]
        if(r-l+1<t.length()){
            return false;
        }
        int [] arr = new int[128];
        for(int i = l;i<=r;i++){
            arr[s.charAt( i )]++;
        }
        for(int i = 0;i< t.length();i++){
            arr[t.charAt( i )]--;
        }
        for(int i = 0;i<128;i++){
            if(arr[i]<0){
                return false;
            }
        }
        return true;
    }

    public String minWindow2(String s, String t) {
        int l = 0, r = -1;
        int res = s.length()+1;
        int begin = 0,end = 0;
        while(l < s.length()) { // s=[l,r]
            if (r+1<s.length() && include(s,t,l,r) == -1) { // 如果不包含T
                r ++;
            } else { // 包含T
                if (include(s,t,l,r) == 1 && res > r-l+1) {
                    begin = l;
                    end = r;
                    res = r-l+1;
                }
                l ++;
            }
        }
        if (res == s.length()+1)
            return "";
        return s.substring(begin,end+1);
    }

    // 判断s[l,r]中是否包含t
    public int include(String s, String t, int l, int r) {
        if (r == -1 || r-l+1 < t.length())
            return -1;
        int[] temp = new int[256];
        String subS = s.substring(l,r+1);
        for(int i = 0 ; i < subS.length() ; i++) {
            temp[(int)subS.charAt(i)] += 1;
        }
        for(int i = 0 ; i < t.length() ; i++) {
            temp[(int)t.charAt(i)] -= 1;
        }
        for (int i = 0 ; i < temp.length ; i++) {
            if (temp[i] < 0)
                return -1;
        }
        return 1;
    }

    public String minWindow3(String s, String t) {
        //性能好两个档次
        String string = "";

        //hashmap来统计t字符串中各个字母需要出现的次数
        HashMap<Character,Integer> map = new HashMap<>();
        for (char c : t.toCharArray())
            map.put( c, map.containsKey(c) ? map.get(c)+1 : 1);

        //用来计数 判断当前子字符串中是否包含了t中全部字符
        int count = 0;
        //记录当前子字符串的左右下标值
        int left = 0;
        int right = 0;
        //记录当前最小子字符串的大小以及第一最后字符的下标值
        int min = Integer.MAX_VALUE;
        int minLeft = 0;
        int minRight = 0;

        for (; right < s.length() ; right++) {
            char temp = s.charAt(right);
            if (map.containsKey(temp)){//向后遍历出所包含t的字符串
                count = map.get(temp) > 0 ? count+1 : count;
                map.put(temp,map.get(temp)-1);
            }
            while (count == t.length()){//得出一个符合条件的子字符串
                if (right-left < min){//更新min minLeft minRight 信息
                    min = right - left;
                    minLeft = left;
                    minRight = right;
                }
                char c = s.charAt(left);
                if (map.containsKey(c)){//向左收缩 判断所删减的字符是否在map中
                    if (map.get(c) >= 0)count --;//count--时需要判断一下是否需要--
                    map.put(c,map.get(c)+1);
                }
                left++;
            }
        }
        return min == Integer.MAX_VALUE ?  "" : s.substring(minLeft,minRight+1);
    }


    /**
     * 77. 组合
     *
     * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
     *
     * 示例:
     *
     * 输入: n = 4, k = 2
     * 输出:
     * [
     *   [2,4],
     *   [3,4],
     *   [2,3],
     *   [1,2],
     *   [1,3],
     *   [1,4],
     * ]
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine(int n, int k) {
        //全排列
        List<List<Integer>> lists=new ArrayList<>(  );
        if(k>n || k<=0){
            return lists;
        }
        //先选出k个数，再进行全排列:效率贼差
        getKNumbers(n,k,lists,new ArrayList<>(  ),1);
        return lists;
    }

    public void getKNumbers(int n,int k,List<List<Integer>> lists,List<Integer> list,int index){
        if ( list.size() == k) {
            sortKNumbers( list, new ArrayList<>(  ),lists,k,0);
        } else if(index<=n){

            getKNumbers( n,k, lists,list,index+1);

            list.add( index );
            getKNumbers( n,k, lists,list,index+1);
            list.remove( list.size()-1 );
        }
    }

    public void   sortKNumbers(List<Integer> numbers,List<Integer> list,List<List<Integer>> lists,int k,int index){
        //求k个数全排列
        if(list.size()==k){
            lists.add( new ArrayList<>( list ) );
        }else if(index<numbers.size()){
            sortKNumbers(numbers,list,lists,k,index+1);

            list.add( numbers.get( index ) );
            sortKNumbers(numbers,list,lists,k,index+1);
            list.remove( list.size()-1 );
        }

    }


    public List<List<Integer>> combine2(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();

        List<Integer> cur = new ArrayList<>();

        dfs(n, k, 0, cur, ans);
        return ans;
    }

    private void dfs(int n, int k, int last, List<Integer> cur, List<List<Integer>> ans) {
        if (k == 0) {
            ans.add(new ArrayList<>(cur));
            return;
        }
        for (Integer i = last + 1; i <= n; i++) {
            cur.add(i);
            dfs(n, k - 1, i, cur, ans);
            cur.remove(i);
        }
    }


    /**
     * 78. 子集
     *
     * 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
     *
     * 说明：解集不能包含重复的子集。
     *
     * 示例:
     *
     * 输入: nums = [1,2,3]
     * 输出:
     * [
     *   [3],
     *   [1],
     *   [2],
     *   [1,2,3],
     *   [1,3],
     *   [2,3],
     *   [1,2],
     *   []
     * ]
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res=new ArrayList<>(  );
        if(nums.length==0){
            return res;
        }
        subsetsdfs( nums,res,new ArrayList<>(  ),0 );
        return res;

    }

    public void subsetsdfs( int[] nums, List< List< Integer > > lists, List< Integer > list, int index ) {
        lists.add( new ArrayList<>( list ) );
        for ( int i = index; i < nums.length; i++ ) {
            if(list.contains( nums[i] )){
                continue;
            }
            list.add( nums[ i ] );
            subsetsdfs( nums, lists, list, i + 1 );
            list.remove( list.size() - 1 );
        }
    }

    public List<List<Integer>> subsets2(int[] nums) {
        //位运算
        int len = nums.length;
        List<List<Integer>> results = new ArrayList<>(1<<len);
        for(int i = 0; i < 1<<len; ++i){
            ArrayList<Integer> result = new ArrayList<>();
            for(int j = 0; j < len; ++j){
                if((i & 1<<j) != 0)
                    result.add(nums[j]);
            }
            results.add(result);
        }
        return results;
    }

    /**
     * 79. 单词搜索
     *
     * 给定一个二维网格和一个单词，找出该单词是否存在于网格中。
     *
     * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
     *
     * 示例:
     *
     * board =
     * [
     *   ['A','B','C','E'],
     *   ['S','F','C','S'],
     *   ['A','D','E','E']
     * ]
     *
     * 给定 word = "ABCCED", 返回 true.
     * 给定 word = "SEE", 返回 true.
     * 给定 word = "ABCB", 返回 false.
     * @param board
     * @param word
     * @return
     */
    int[][] directions={
            {0,1},
            {1,0},
            {0,-1},
            {-1,0}
    };
    public boolean exist(char[][] board, String word) {
        //回溯法
        if(board.length==0 || board[0].length==0){
            return false;
        }
        int rows=board.length,cols=board[0].length;
        boolean[][] visited=new boolean[rows][cols];
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(dfs( board,word,i,j,0,visited )){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dfs(char[][] board,String world,int x,int y,int i,boolean[][] visited){
        if(x<0 || y<0 || x>=board.length || y>=board[0].length ||board[x][y]!=world.charAt( i ) || visited[x][y]){
            return false;
        }
        if(i==world.length()-1){
            return true;
        }
        visited[x][y]=true;
        for(int j=0;j<directions.length;j++){
            x+=directions[j][0];
            y+=directions[j][1];
            if(dfs( board,world,x,y,i+1,visited )){
                return true;
            }
            x-=directions[j][0];
            y-=directions[j][1];
        }
        visited[x][y]=false;
        return false;
    }

    /**
     * 80. 删除排序数组中的重复项 II
     * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素最多出现两次，返回移除后数组的新长度。
     *
     * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
     *
     * 示例 1:
     *
     * 给定 nums = [1,1,1,2,2,3],
     *
     * 函数应返回新长度 length = 5, 并且原数组的前五个元素被修改为 1, 1, 2, 2, 3 。
     *
     * 你不需要考虑数组中超出新长度后面的元素。
     * 示例 2:
     *
     * 给定 nums = [0,0,1,1,1,1,2,3,3],
     *
     * 函数应返回新长度 length = 7, 并且原数组的前五个元素被修改为 0, 0, 1, 1, 2, 3, 3 。
     *
     * 你不需要考虑数组中超出新长度后面的元素。
     * 说明:
     *
     * 为什么返回数值是整数，但输出的答案是数组呢?
     *
     * 请注意，输入数组是以“引用”方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
     *
     * 你可以想象内部操作如下:
     *
     * // nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
     * int len = removeDuplicates(nums);
     *
     * // 在函数里修改输入数组对于调用者是可见的。
     * // 根据你的函数返回的长度, 它会打印出数组中该长度范围内的所有元素。
     * for (int i = 0; i < len; i++) {
     *     print(nums[i]);
     * }
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        //动态平移数组：效率一般
        int start=0,end=0;
        int res=nums.length;
        for(int i=1;i<res;i++){
            if ( nums[ i ] == nums[ i - 1 ] ) {
                end++;
                if(i==res-1){
                    if(end-start>1){
                        //去重
                        i-=end-start-1;
                        res-=end-start-1;
//                        move(nums,end+1,end-start-1);
                    }
                }
            } else {
                if ( end - start > 1 ) {
                    //去重处理
                    i-=end-start-1;
                    res-=end-start-1;
                    move(nums,end,end-start-1);
                    start=end=i;
                }else {
                    start=i;
                    end=i;
                }
            }
        }
        return res;
    }
    public void move(int [] nums,int start,int distance){
        for(int i=start;i<nums.length;i++){
            nums[i-distance]=nums[i];
        }
    }

    public int removeDuplicates2(int[] nums) {
        //绝了
        int i = 0;
        for (int n : nums)
            if (i < 2 || n > nums[i-2])
                nums[i++] = n;
        return i;
    }

    /**
     * 81. 搜索旋转排序数组 II
     *
     * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
     *
     * ( 例如，数组 [0,0,1,2,2,5,6] 可能变为 [2,5,6,0,0,1,2] )。
     *
     * 编写一个函数来判断给定的目标值是否存在于数组中。若存在返回 true，否则返回 false。
     *
     * 示例 1:
     *
     * 输入: nums = [2,5,6,0,0,1,2], target = 0
     * 输出: true
     * 示例 2:
     *
     * 输入: nums = [2,5,6,0,0,1,2], target = 3
     * 输出: false
     * 进阶:
     *
     * 这是 搜索旋转排序数组 的延伸题目，本题中的 nums  可能包含重复元素。
     * 这会影响到程序的时间复杂度吗？会有怎样的影响，为什么？
     *
     * @param nums
     * @param target
     * @return
     */
    public boolean search1(int[] nums, int target) {
        return search1(nums, 0, nums.length - 1, target);
    }

    private boolean search1(int[] nums, int low, int high, int target) {
        if (low > high)
            return false;
        int mid = (low + high) / 2;
        if (nums[mid] == target)
            return true;
        if (nums[mid] < nums[high]) {
            if (nums[mid] < target && target <= nums[high])
                return search1(nums, mid + 1, high, target);
            else
                return search1(nums, low, mid - 1, target);
        } else if(nums[mid] > nums[high]){
            if (nums[low] <= target && target < nums[mid])
                return search1(nums, low, mid - 1, target);
            else
                return search1(nums, mid + 1, high, target);
        }else{
            return search1(nums, low, high - 1, target);
        }
    }

    /**
     * 84. 柱状图中最大的矩形
     *
     * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
     *
     * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        if(heights==null || heights.length==0){
            return 0;
        }
        //维护一个从底到顶递增的栈，遇到比栈顶小的值就将比它大的值都出栈再入栈，同时计算矩形值
        Stack<Integer> stack=new Stack<>();
        //问题是如何计算width，如果stack存的是数组的值，通过循环来计数会漏掉元素，应该存储索引
        int len=heights.length;
        int res=0;
        for(int i=0;i<=len;i++){
            int curHeigh=i==len?-1:heights[i];
            while (!stack.isEmpty() && heights[stack.peek()]>=curHeigh){
                int heigh=heights[stack.pop()];
                int width=stack.isEmpty()?i:i-stack.peek()-1;
                res=Math.max( res,heigh*width );
            }
            stack.push( i );
        }
        return res;
    }


    /**
     * 85. 最大矩形
     *
     * 给定一个仅包含 0 和 1 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
     *
     * 示例:
     *
     * 输入:
     * [
     *   ["1","0","1","0","0"],
     *   ["1","0","1","1","1"],
     *   ["1","1","1","1","1"],
     *   ["1","0","0","1","0"]
     * ]
     * 输出: 6
     *
     * @param matrix
     * @return
     */
    public int maximalRectangle(char[][] matrix) {
        //求每一层的高度，再求柱状图中最大的矩形
        if(matrix==null || matrix.length==0 ||matrix[0].length==0){
            return 0;
        }
        int res=0;
        for(int i=0;i<matrix.length;i++){
            int[] arr=new int[matrix[0].length];
            for(int k=0;k<matrix[0].length;k++){
                for(int j=i;j>=0;j--){
                    if(matrix[j][k]==0){
//                        break;
                    }else {
                        arr[k]++;
                    }
                }
            }
            res=Math.max(res,largestRectangleArea(arr));
        }
        return res;
    }


    /**
     * 89. 格雷编码
     *
     * 格雷编码是一个二进制数字系统，在该系统中，两个连续的数值仅有一个位数的差异。
     *
     * 给定一个代表编码总位数的非负整数 n，打印其格雷编码序列。格雷编码序列必须以 0 开头。
     *
     * 示例 1:
     *
     * 输入: 2
     * 输出: [0,1,3,2]
     * 解释:
     * 00 - 0
     * 01 - 1
     *
     * ']/】
     * 11 - 3
     * 10 - 2
     *
     * 对于给定的 n，其格雷编码序列并不唯一。
     * 例如，[0,2,3,1] 也是一个有效的格雷编码序列。
     *
     * 00 - 0
     * 10 - 2
     * 11 - 3
     * 01 - 1
     * 示例 2:
     *
     * 输入: 0
     * 输出: [0]
     * 解释: 我们定义格雷编码序列必须以 0 开头。
     *      给定编码总位数为 n 的格雷编码序列，其长度为 2n。当 n = 0 时，长度为 20 = 1。
     *      因此，当 n = 0 时，其格雷编码序列为 [0]
     *
     * @param n
     * @return
     */
    public List<Integer> grayCode(int n) {
        List<Integer> list = new ArrayList<>();
        list.add(0);                                //n=0 {0}
        if(n == 0) return list;
        int increase = 1;
        for(int i = 0;i<n;i++){                     //              n=2
            int len = list.size();                  //              len = 2
            for(int j = len-1;j>=0;j--){            //              {0,1,3,2}
                list.add(list.get(j)+increase);     //n=1 {0,1}
            }
            increase *= 2;
        }
        return list;

    }

    public List<Integer> grayCode2(int n) {
        List<Integer> ret = new ArrayList<>();
        for(int i = 0; i < 1<<n; ++i)
            ret.add(i ^ i>>1);
        return ret;
    }


    /**
     * 90. 子集 II
     *
     * 给定一个可能包含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
     *
     * 说明：解集不能包含重复的子集。
     *
     * 示例:
     *
     * 输入: [1,2,2]
     * 输出:
     * [
     *   [2],
     *   [1],
     *   [1,2,2],
     *   [2,2],
     *   [1,2],
     *   []
     * ]
     * @param nums
     * @return
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        Arrays.sort(nums);
        List<Integer> levelList = new ArrayList<>();
        result.add(new ArrayList<>(levelList));
        subsetsWithDupDFS(result, levelList, 0, nums);
        return result;
    }

    private void subsetsWithDupDFS(List<List<Integer>> result, List<Integer> levelList, int depth, int[] nums) {
        for ( int i = depth; i < nums.length; i++ ) {
            if ( i > depth && nums[ i - 1 ] == nums[ i ] ) {
                continue;
            }
            levelList.add( nums[ i ] );
            result.add( new ArrayList<>( levelList ) );
            subsetsWithDupDFS( result, levelList, i + 1, nums );
            levelList.remove( levelList.size() - 1 );
        }
    }

    /**
     * 91. 解码方法
     *
     * 一条包含字母 A-Z 的消息通过以下方式进行了编码：
     *
     * 'A' -> 1
     * 'B' -> 2
     * ...
     * 'Z' -> 26
     * 给定一个只包含数字的非空字符串，请计算解码方法的总数。
     *
     * 示例 1:
     *
     * 输入: "12"
     * 输出: 2
     * 解释: 它可以解码为 "AB"（1 2）或者 "L"（12）。
     * 示例 2:
     *
     * 输入: "226"
     * 输出: 3
     * 解释: 它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。
     * @param s
     * @return
     */
//    public int numDecodings(String s) {
//
//    }



}