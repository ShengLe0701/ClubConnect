package com.clubconnect.isayyuhh.clubconnect.datas;

/**
 * Created by Tan on 9/14/2016.
 */
public class StudentSignUpData {

    public String m_strSchool = null;
    public String m_strEmail = null;
    public String m_strPassword = null;
    public String m_strName = null;
    public String m_strPhone = null;
    public String m_strProfileImagePath = null;
    public int m_strProfileImageWidth = 0;
    public int m_strProfileImageHeight = 0;

    private static StudentSignUpData mData = null;

    public static StudentSignUpData getInstance() {
        if (mData == null) {
            mData = new StudentSignUpData();
            return mData;
        } else {
            return mData;
        }
    }

}
