package com.clubconnect.isayyuhh.clubconnect.enums;

import com.clubconnect.isayyuhh.clubconnect.R;

/**
 * SubInterest enum containing all subinterests' text and images
 */
public enum SubInterestEnum {

    // Academic
    ACADEMIC_MENTORSHIP("Academic Mentorship", R.drawable.academic_mentorship),
    MINORITY_MENTORSHIP("Minority Mentorship Groups", R.drawable.minority_mentorship),
    SCHOLARSHIP_GROUPS("Scholarship Groups", R.drawable.scholarship_groups),
    HONOR_SOCIETY("Honor Society", R.drawable.honor_society),
    TUTORING("Tutoring", R.drawable.tutoring),

    // Cultural & Identity
    AFRICAN_AMERICAN("African American", R.drawable.african_american),
    SOUTH_AMERICAN("South American", R.drawable.south_american),
    ASIAN_AMERICAN("Asian American", R.drawable.asian_american),
    INDIAN_AMERICAN("Indian American", R.drawable.indian_american),
    LGBQT("LGBQT", R.drawable.lgbqt),
    MIDDLE_EASTERN_AMERICAN("Middle Eastern American", R.drawable.middle_eastern),
    NATIVE_AMERICAN("Native American/ Indigenous", R.drawable.native_american_indenginous),
    INTERNATIONAL_STUDENTS("International Students", R.drawable.international_students),
    CHICANO_LATINO("Chicano/ Latino", R.drawable.chicano_latino),

    // Environmental & Sustainability
    ENVIRONMENTAL_AND_SUSTAINABILITY("Environmental & Sustainability", R.drawable.environment_and_sustainability),

    // Everything Else
    GAMING("Gaming", R.drawable.gaming),
    HEALTH_OF_MIND_AND_BODY("Health of the Mind and Body", R.drawable.health_of_the_mind_and_body),
    TECH("Tech", R.drawable.tech),
    DIY("DIY", R.drawable.diy),
    BACKPACKING("Backpacking", R.drawable.backpacking),
    RESIDENTIAL_LIFE("Residential Life", R.drawable.residential_life),
    JOURNALS("Journals", R.drawable.journals),
    STUDENT_GOV("Student Gov.", R.drawable.student_gov),
    OUT_OF_STATE("Out Of State", R.drawable.out_of_state),

    // Greek Letter Organization
    SORORITY("Sorority", R.drawable.sorority),
    FRATERNITY("Fraternity", R.drawable.fraternity),
    GREEK_CO_ED("Greek Co-ed", R.drawable.greek_co_ed),

    // Health and Wellness
    FITNESS("Fitness", R.drawable.fitness),
    SELF_IMPROVEMENT("Self Improvement", R.drawable.self_improvement),
    MEDITATION("Meditation", R.drawable.meditation),
    OUTDOOR("Outdoor", R.drawable.outdoor),

    // Performing & Visual Arts
    MUSIC("Music/ Singing/ Radio", R.drawable.music),
    DESIGN("Design", R.drawable.design),
    THEATER("Theater", R.drawable.theater),
    DANCE("Dance", R.drawable.dance),
    ARTS("Arts", R.drawable.arts),
    FILM("Film", R.drawable.film),
    MEDIA("Media", R.drawable.media),

    // Political & Avocacy
    ANIMAL_RIGHTS("Animal Rights", R.drawable.animal_rights),
    POLITICS("Politics", R.drawable.politics),
    HUMAN_RIGHTS("Human Rights", R.drawable.human_rights),
    ENVIRONMENTALISM("Environmentalism", R.drawable.environmentalism),
    SOCIAL_ACTIVISM("Social Activism", R.drawable.social_activism),

    // Professional
    MINORITY_GROUPS("Minority Groups", R.drawable.minority),
    MEDICAL("Medical", R.drawable.medical),
    STEM("STEM", R.drawable.stemsub),
    START_UPS("Start Ups", R.drawable.start_ups),
    CAREER_PLANNING("Career Planning", R.drawable.career_planning),
    BUSINESS("Business", R.drawable.business),
    LEADERSHIP_AND_PUBLIC_SPEAKING("Leadership & Public Speaking", R.drawable.leadership),
    LAW("Law", R.drawable.law),

    // Religious & Spiritual
    CHRISTIANITY("Christianity", R.drawable.christianity),
    ISLAM("Islam", R.drawable.islam),
    JUDAISM("Judaism", R.drawable.judaism),

    // STEM
    ENGINEERING("Engineering", R.drawable.engineering),
    SCIENCE("Science", R.drawable.science),
    TECHNOLOGY("Technology", R.drawable.technology),

    // Social
    FREE_FOOD("Free Food", R.drawable.free_food),
    DANCES("Dances", R.drawable.dances),
    MIXERS("Mixers", R.drawable.mixers),
    PARTIES("Parties", R.drawable.parties),

    // Sports
    TEAM("Team", R.drawable.team),
    INDIVIDUAL("Individual", R.drawable.individual),
    TRACK_AND_FIELD("Track & Field", R.drawable.track_and_field),
    COMPETITIVE("Competitive", R.drawable.competetive),
    NONCOMPETITIVE("Non- Competitive", R.drawable.non_competitive),
    // Dance
    WATER_SPORTS("Water Sports", R.drawable.swimming),
    STAMINA_BASED("Stamina Based", R.drawable.stamina),
    STRENGTH_BASED("Strength Based", R.drawable.strength),
    MARTIAL_ARTS("Martial Arts", R.drawable.martial_arts),

    // Volunteering
    VOLUNTEERING("Volunteering", R.drawable.volunteering),
    CHARITY("Charity", R.drawable.charity),
    COMMUNITY_SERVICE("Community Service", R.drawable.community_service);

    public String name;
    public int res;

    SubInterestEnum(String name, int res) {
        this.name = name;
        this.res = res;
    }
}

