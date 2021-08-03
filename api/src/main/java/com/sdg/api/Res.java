package com.sdg.api;

import androidx.annotation.Nullable;

public class Res<T> {
    /**
     * 状态吗
     */
    public int code;

    /**
     * 状态吗
     */
    public int Flag;

    /**
     * 数据
     */
    @Nullable
    public T ResultObj;

    public String Msg;

}