package io.nuls.nulsswitch.demo.controller;

import org.apache.commons.lang.WordUtils;

/**
 * <pre>
 *
 * </pre>

 */
public class MainTest {
    public static void main(String[] args) {

        String s1 = "test_aaaa";
        String s2 = "test_aaaa_bbbb";
        String s3 = "testAaaa";
        String s4 = "testAaaaBbbb";
        String s5 = "test-aaaa";
        String s6 = "test-aaaa_bbbb";

        System.out.println(WordUtils.capitalize(s1));

        System.out.println(WordUtils.capitalize(s1, new char[]{'_','-'}));
        System.out.println(WordUtils.capitalize(s6, new char[]{'_','-'}));


    }
}
