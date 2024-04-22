package com.example.myapplication.common;


import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;



public class ChatConst {

    public static final String RECYCLER_DATABASE_NAME = "recycler.db";
    public static final int SENDING = 0;
    public static final int COMPLETED = 1;
    public static final int SENDERROR = 2;

    @IntDef({SENDING, COMPLETED, SENDERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SendState {
    }
}
