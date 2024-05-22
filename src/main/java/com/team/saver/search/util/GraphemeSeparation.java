package com.team.saver.search.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GraphemeSeparation {

    // 초성 ( ㄱ ~ ㅎ ) /*ㄱ ㄲ ㄴ ㄷ ㄸ ㄹ ㅁ ㅂ ㅃ ㅅ ㅆ ㅇ ㅈ ㅉ ㅊ ㅋ ㅌ ㅍ ㅎ */
    private static final char[] initialConant = {0x3131, 0x3132, 0x3134, 0x3137, 0x3138, 0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148, 0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e};

    // 중성 ( ㅏ ~ ㅣ ) /*ㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅣ*/
    private static final char[] neutrality = {0x314f, 0x3150, 0x3151, 0x3152, 0x3153, 0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a, 0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160,	0x3161,	0x3162, 0x3163};

    // 종성 ( ㄱ ~ ㅎ )
    private static final char[] finality = {0x0000, 0x3131, 0x3132, 0x3133, 0x3134, 0x3135, 0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c, 0x313d, 0x313e, 0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145, 0x3146, 0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e};

    public static List<Map<String, Integer>> separation(String word) {
        List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();

        for (int i = 0; i < word.length(); i++) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            char test = word.charAt(i);

            if (test >= 0xAC00) {
                char uniVal = (char) (test - 0xAC00);

                char initialConant = (char) (((uniVal - (uniVal % 28)) / 28) / 21);
                char neutrality = (char) (((uniVal - (uniVal % 28)) / 28) % 21);
                char finality = (char) (uniVal % 28);

                map.put("initialConant", (int) initialConant);
                map.put("neutrality", (int) neutrality);
                map.put("finality", (int) finality);
                list.add(map);
            }
        }

        return list;
    }

    public static List<String> absorption(Map<String, Integer> map) {
        List<String> result = new ArrayList<>();

        int a = map.get("initialConant");
        int b = map.get("neutrality");
        int c = map.get("finality");

        result.add(String.valueOf((char) initialConant[a]));
        result.add(String.valueOf((char) (0xAC00 + 28 * 21 * (a) + 28 * (b))));

        if (c != 0x0000) {
            result.add(String.valueOf((char) (0xAC00 + 28 * 21 * (a) + 28 * (b) + (c))));
        }

        return result;
    }
}