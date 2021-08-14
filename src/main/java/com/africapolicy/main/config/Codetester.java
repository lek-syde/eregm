package com.africapolicy.main.config;

import java.util.Random;

/**
 * @author Olalekan Folayan
 */
public class Codetester {

//    public static void main(String[] args){
//        CodeConfig config2 = CodeConfig.length(2).withCharset(CodeConfig.Charset.ALPHABETIC);
//        String postfi=generate(config2);
//
//        CodeConfig config3 = CodeConfig.length(2).withCharset(CodeConfig.Charset.ALPHABETIC);
//        String postfiat=generate(config3);
//
//        CodeConfig config = CodeConfig.length(8).withPrefix("NG-"+postfiat).withCharset(CodeConfig.Charset.NUMBERS).withPostfix(postfi);
//
//        // when
//        String code = generate(config);
//        System.out.println(code);
//    }


    private static final Random RND = new Random(System.currentTimeMillis());
    public static String generate(CodeConfig config) {
        StringBuilder sb = new StringBuilder();
        char[] chars = config.getCharset().toCharArray();
        char[] pattern = config.getPattern().toCharArray();

        if (config.getPrefix() != null) {
            sb.append(config.getPrefix());
        }

        for (int i = 0; i < pattern.length; i++) {
            if (pattern[i] == CodeConfig.PATTERN_PLACEHOLDER) {
                sb.append(chars[RND.nextInt(chars.length)]);
            } else {
                sb.append(pattern[i]);
            }
        }

        if (config.getPostfix() != null) {
            sb.append(config.getPostfix());
        }

        return sb.toString();
    }
}
