package com.clubconnect.isayyuhh.clubconnect.callbacks;

import com.clubconnect.isayyuhh.clubconnect.objects.Events;

/**
 * Created by isayyuhh on 4/21/16.
 */
public interface CallbackActivity {
    Events sort(Events toSort);
    int compareDates(String lhs, String rhs);
    String today();
}
