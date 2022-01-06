package com.yapp.project.common.util;

import java.util.List;
import java.util.StringTokenizer;

public class PositionParser {
    public static int[] parse(String positionCodes, String delimiter) {
        var st = new StringTokenizer(positionCodes, delimiter);
        int size = st.countTokens();
        int[] splitedPositions = new int[size];

        int i = 0;
        while (st.hasMoreTokens()) {
            splitedPositions[i++] = Integer.parseInt(st.nextToken());
        }

        return splitedPositions;
    }

    public static String join(List<String> positionCodes, String delimiter) {
        var sb = new StringBuilder();
        sb.append(delimiter);

        for (var positionCode : positionCodes) {
            sb.append(positionCode).append(delimiter);
        }

        return sb.toString();
    }
}
