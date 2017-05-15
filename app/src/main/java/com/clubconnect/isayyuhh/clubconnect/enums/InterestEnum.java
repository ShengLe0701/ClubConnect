package com.clubconnect.isayyuhh.clubconnect.enums;

import com.clubconnect.isayyuhh.clubconnect.R;

/**
 * Created by isayyuhh on 5/3/16.
 */
public enum InterestEnum {
    ACADEMIC("Academic", R.drawable.academic),
    CULTURAL_AND_IDENTITY("Cultural & Identity", R.drawable.cultural_and_identity),
    ENVIRONMENTAL_AND_SUSTAINABILITY("Environmental & Sustainability",
            R.drawable.environment_and_sustainability),
    EVERYTHING_ELSE("Everything Else", R.drawable.everything_else),
    GREEK_LETTER_ORG("Greek Letter Organization", R.drawable.greek_letter_organization),
    HEALTH_AND_WELLNESS("Health and Wellness", R.drawable.health_and_wellness),
    PERFORMING_AND_VISUAL_ARTS("Performing & Visual Arts", R.drawable.performing_and_visual_arts),
    POLITICAL_AND_ADVOCACY("Political & Advocacy", R.drawable.political_and_advocacy),
    PROFESSIONAL("Professional", R.drawable.professional),
    RELIGIOUS_AND_SPIRITUAL("Religious & Spiritual", R.drawable.religous_and_spiritual),
    STEM("STEM", R.drawable.stem),
    SOCIAL("Social", R.drawable.social),
    SPORTS("Sports", R.drawable.sports),
    VOLUNTEERING("Volunteering", R.drawable.volunteering);

    public String name;
    public int res;

    InterestEnum(String name, int res) {
        this.name = name;
        this.res = res;
    }
}
