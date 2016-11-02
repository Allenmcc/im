package com.starsea.im.biz.entity.e;

import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */
public class PageParam { //用于存放index页面中的列表
    private int currentPage;//当前页
    private int totalPage;//总页
    private int rowCount;//总记录数
    public static int pageSize=10;//页大小
    private List<MessageList> data;//数据

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        int totalPage=rowCount/pageSize;
        if(rowCount%pageSize>0){
            totalPage+=1;
        }
        setTotalPage(totalPage);
        this.rowCount = rowCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<MessageList> getData() {
        return data;
    }

    public void setData(List<MessageList> data) {
        this.data = data;
    }

}

