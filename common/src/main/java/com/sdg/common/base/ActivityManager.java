package com.sdg.common.base;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class ActivityManager {

    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {
    }

    public static ActivityManager getAppManager() {
        if(instance == null) {
            instance = new ActivityManager();
        }

        return instance;
    }

    public void addActivity(Activity activity) {
        if(activityStack == null) {
            activityStack = new Stack();
        }

        if(activityStack.size() > 0) {
            Activity act = (Activity)activityStack.lastElement();
            Log.i("appmanager", "******************" + act.getClass());
            if(act != null && act.getClass().equals(activity.getClass())) {
                this.finishLastActivity();
            }
        }

        activityStack.add(activity);
    }

    public Activity currentActivity() {
        Activity activity = (Activity)activityStack.lastElement();
        return activity;
    }

    public void finishActivity() {
        Activity activity = (Activity)activityStack.lastElement();
        this.finishActivity(activity);
    }

    public void finishActivity(Activity activity) {
        if(activity != null) {
            this.removeActivityStack(activity);
            activity.finish();
            activity = null;
        }

    }

    public void removeActivityStack(Activity activity) {
        activityStack.remove(activity);
    }

    public void finishLastActivity() {
        Activity activity = (Activity)activityStack.lastElement();
        if(activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }

    }

    public void finishActivity(Class<?> cls) {
        Iterator i$ = activityStack.iterator();

        while(i$.hasNext()) {
            Activity activity = (Activity)i$.next();
            if(activity.getClass().equals(cls)) {
                this.finishActivity(activity);
            }
        }

    }

    public void finishAllActivity() {
        int i = 0;
        for(int size = activityStack.size(); i < size; ++i) {
            if(null != activityStack.get(i)) {
                ((Activity)activityStack.get(i)).finish();
            }
        }
        activityStack.clear();
    }


    public void getActivity(Activity activity) {
        if(activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }

    }

    public Activity getActivity(Class<?> cls) {
        Iterator i$ = activityStack.iterator();

        Activity activity;
        do {
            if(!i$.hasNext()) {
                return null;
            }

            activity = (Activity)i$.next();
        } while(!activity.getClass().equals(cls));

        return activity;
    }

    public void goToActivity(Activity activity) {
        int flag = -1;
        ArrayList activities = new ArrayList();
        Iterator i = activityStack.iterator();

        Activity act;
        while(i.hasNext()) {
            act = (Activity)i.next();
            if(flag == 0) {
                ++flag;
            }

            if(flag > 0) {
                activities.add(act);
            }

            if(flag == -1 && act.getClass().equals(activity.getClass())) {
                flag = 0;
            }
        }

        for(int var6 = activities.size() - 1; var6 > -1; --var6) {
            act = (Activity)activities.get(var6);
            activityStack.remove(act);
            act.finish();
            act = null;
        }

    }

    public void goToActivity(Class<?> cls) {
        int flag = -1;
        ArrayList activities = new ArrayList();
        Iterator i = activityStack.iterator();

        Activity act;
        while(i.hasNext()) {
            act = (Activity)i.next();
            Log.i("goToActivity","goToActivity=="+act.getClass());
            if(flag >= 0) {
                ++flag;
            }

            if(flag > 0) {
                activities.add(act);
            }

            if(flag == -1) {
                Log.i("activity.getClass()", act.getClass().toString());
                if(act.getClass().equals(cls)) {
                    flag = 0;
                }
            }
        }

        for(int var6 = activities.size() - 1; var6 > -1; --var6) {
            act = (Activity)activities.get(var6);
            Log.i("activity.getClass() => ", act.getClass().toString());
            activityStack.remove(act);
            act.finish();
            act = null;
        }

    }

    public int getActivityCount() {
        return activityStack.size();
    }
}
