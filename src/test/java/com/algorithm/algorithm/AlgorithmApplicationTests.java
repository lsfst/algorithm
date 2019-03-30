package com.algorithm.algorithm;

import com.algorithm.skill.FindRadius475;
import com.algorithm.skill.HasPathSum112;
import com.algorithm.skill.MyLinkedList;
import com.algorithm.skill.NextGreaterElement;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith( SpringRunner.class )
@SpringBootTest
public class AlgorithmApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Test
    public void findRadius() {
        int[] houses= { 25921153,510616708 };
        int[] heaters={771515668,357571490,44788124,927702196,952509530};
        FindRadius475.findRadius( houses,heaters );

    }

    @Test
    public void nextGreaterElement() {
        int[] nums1= { 4,1,2 };
        int[] nums2={1,3,4,2,1,2,4,3,2};
        NextGreaterElement.nextGreaterElement( nums1,nums2 );

    }

    @Test
    public void findRelativeRanks() {
        int[] nums=new int[]{5,4,3,2,1};
        NextGreaterElement.findRelativeRanks( nums );

    }

    @Test
    public void detectCapitalUse() {
        NextGreaterElement.detectCapitalUse( "USA" );
    }

    @Test
    public void MyLinkedList() {
        MyLinkedList list=new MyLinkedList();
        list.addAtHead( 1 );
        list.addAtIndex( 1,2 );
        list.get( 1 );
        list.get( 0 );
        list.get( 2 );
    }

    @Test
    public void shortestCompletingWord() {
        HasPathSum112.shortestCompletingWord("1s3 PSt",new String[]{"step","steps","stripe","stepple"});
    }

    @Test
    public void reachNumber() {
        HasPathSum112.reachNumber(999);
    }
    @Test
    public void countPrimeSetBits() {
        HasPathSum112.countPrimeSetBits(1,999);
    }


    @Test
    public void isToeplitzMatrix() {
        int[][] arr={
                {1,2,3,4},
                {5,1,2,3},
                {9,5,1,2}
        };
        HasPathSum112.isToeplitzMatrix(arr);
    }

    @Test
    public void mostCommonWord() {
        HasPathSum112.mostCommonWord("Bob hit a ball, the hit BALL flew far after it was hit.",new String[]{"hit"});
    }

    @Test
    public void largeGroupPositions() {
        HasPathSum112.largeGroupPositions("abcdddeeeeaabbbcd");
    }

    @Test
    public void numUniqueEmails() {
        HasPathSum112.numUniqueEmails(new String[]{"testemail@leetcode.com","testemail1@leetcode.com","testemail+david@lee.tcode.com"});
    }

    @Test
    public void lengthOfLongestSubstring() {
        HasPathSum112.lengthOfLongestSubstring("aab");
    }

    @Test
    public void longestPalindrome() {
        HasPathSum112.longestPalindrome("babad");
    }

    @Test
    public void convert() {
        HasPathSum112.convert("A",1);
    }

    @Test
    public void threeSum() {
        int [] arr={-1,0,1,2,-1,-4};
        HasPathSum112.threeSum(arr);
    }

    @Test
    public void threeSumClosest() {
        int [] arr={-3,-2,-5,3,-4};
        HasPathSum112.threeSumClosest(arr,-1);
    }
    @Test
    public void divide() {
        HasPathSum112.divide(-2147483648,2);
    }

    @Test
    public void findSubstring() {
        String[] words={"a"};
        HasPathSum112.findSubstring4("a",words);
    }

    @Test
    public void longestValidParentheses() {
        HasPathSum112.longestValidParentheses( "()(()");
    }

    @Test
    public void search() {
        HasPathSum112.searchRange( new int[]{1},1);
    }

    @Test
    public void combinationSum() {
        HasPathSum112.combinationSum( new int[]{2,3,6,7},7);
    }

    @Test
    public void firstMissingPositive() {
        HasPathSum112.firstMissingPositive( new int[]{-1,4,2,1,9,10});
    }

    @Test
    public void multiply() {
        HasPathSum112.multiply( "1231","0");
    }


    @Test
    public void groupAnagrams() {
        HasPathSum112.groupAnagrams( new String[]{"",""});
    }

    @Test
    public void getPermutation() {
        HasPathSum112.getPermutation( 3,3);
    }

    @Test
    public void uniquePathsWithObstacles() {
        int[][] arr = {
                { 0, 0, 0 },
                { 0, 1, 0 },
                { 0, 0, 0 } };
        HasPathSum112.uniquePathsWithObstacles( arr);
    }

    @Test
    public void fullJustify() {
        HasPathSum112.fullJustify( new String[]{"This", "is", "an", "example", "of", "text", "justification."},16);
    }

    @Test
    public void searchMatrix() {
        int[][] arr={
                {-8,-7,-5,-3,-3,-1,1},{2,2,2,3,3,5,7},{8,9,11,11,13,15,17},{18,18,18,20,20,20,21},{23,24,26,26,26,27,27},{28,29,29,30,32,32,34}
        };
        HasPathSum112.searchMatrix( arr,-5);
    }

    @Test
    public void minWindow() {
        HasPathSum112.minWindow( "ADOBECODEBANC"
                ,"ABC");
    }
}
