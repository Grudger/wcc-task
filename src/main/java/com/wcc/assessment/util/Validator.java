package com.wcc.assessment.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final String POSTCODE_REGEX = "^([A-PR-UWYZ][A-HK-Y]?[0-9][ABEHMNPRVWXY0-9]? {1,2}[0-9][ABD-HJLNP-UW-Z]{2}|GIR 0AA)$";
    private static final Pattern POSTCODE_PATTERN = Pattern.compile(POSTCODE_REGEX);

    // validate postcodes
    public static boolean isValidPostCode(String postcode) {
        Matcher matcher = POSTCODE_PATTERN.matcher(postcode);
        return matcher.matches();
    }

}
