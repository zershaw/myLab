package com.cbsys.saleexplore.profile;


import java.util.Comparator;

/**
 * @author Xiangnan Ren
 * @project discountserver
 */
public class UserProfileComparator implements Comparator<String> {

    @Override
    public int compare(String p1, String p2) {
        int delPos1 = p1.indexOf(":"), delPos2 = p2.indexOf(":");

        int score1 = Integer.parseInt(p1.substring(delPos1 + 1));
        int score2 = Integer.parseInt(p2.substring(delPos2 + 1));

        return Integer.compare(score2, score1);
    }
}
