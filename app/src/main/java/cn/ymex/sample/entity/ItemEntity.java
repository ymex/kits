package cn.ymex.sample.entity;

import android.view.View;

/**
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * <p>
 * Email:ymex@foxmail.com  (www.ymex.cn)
 *
 * @author ymex
 * date: 16/4/24
 */
public class ItemEntity {
    private String title;
    private String detail;
    private View.OnClickListener onClickListener;

    public ItemEntity() {
    }

    public ItemEntity(String title, String detail) {
        this(title, detail, null);
    }

    public ItemEntity(String title, String detail, View.OnClickListener onClickListener) {
        this.title = title;
        this.detail = detail;
        this.onClickListener = onClickListener;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


    public void click() {
        if (onClickListener != null) {
            onClickListener.onClick(null);
        }
    }
}
