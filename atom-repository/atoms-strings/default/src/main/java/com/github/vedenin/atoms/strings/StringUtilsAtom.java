package com.github.vedenin.atoms.strings;

import com.github.vedenin.atom.annotations.AtomTest;
import com.github.vedenin.atom.annotations.AtomUtils;
import com.github.vedenin.atoms.collections.ListAtom;
import com.github.vedenin.atoms.collections.SetAtom;
import org.apache.commons.lang3.StringUtils;

/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 * <p>
 * Created by Slava Vedenin on 12/16/2016.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
@AtomUtils({StringUtils.class})
@AtomTest("StringUtilsAtomTest")
public class StringUtilsAtom {

    public static String substringBeforeLast(String str, String separator) {
        return StringUtils.substringBeforeLast(str, separator);
    }

    public static String substringBefore(String str, String separator) {
        return StringUtils.substringBefore(str, separator);
    }


    public static String substringAfterLast(String str, String separator) {
        return StringUtils.substringAfterLast(str, separator);
    }

    public static ListAtom<String> split(String str, String separatorChars) {
        return ListAtom.create(StringUtils.split(str, separatorChars));
    }

    public static String getLongestSubstring(SetAtom<String> set) {
        String presious = null;
        String result = null;
        for(String str: set) {
            if(presious == null) {
                presious = str;
            } else {
                String common = getLongestSubstring(str, presious);
                if(result == null) {
                    result = common;
                } else {
                    if(!result.equals(common)) {
                        if(common.length() > result.length()) {
                            System.out.print("common more"); // todo change to LogAtom
                        } else {
                            System.out.print("common less"); // todo change to LogAtom
                        }
                    }
                }
            }
        }
        return result;
    }

    public static String getLongestSubstring(String string1, String string2) {
        if (string1 == null || string2 == null || string1.length() == 0 || string2.length() == 0) {
            return "";
        }

        if (string1.equals(string2)) {
            return string1;
        }

        int[][] matrix = new int[string1.length()][];

        int maxLength = 0;
        int maxI = 0;

        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new int[string2.length()];
            for (int j = 0; j < matrix[i].length; j++) {
                if (string1.charAt(i) == string2.charAt(j)) {
                    if (i != 0 && j != 0) {
                        matrix[i][j] = matrix[i - 1][j - 1] + 1;
                    } else {
                        matrix[i][j] = 1;
                    }
                    if (matrix[i][j] > maxLength) {
                        maxLength = matrix[i][j];
                        maxI = i;
                    }
                }
            }
        }
        return string1.substring(maxI - maxLength + 1, maxI + 1);
    }
}
