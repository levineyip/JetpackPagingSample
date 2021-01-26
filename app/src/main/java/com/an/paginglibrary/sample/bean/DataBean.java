package com.an.paginglibrary.sample.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class DataBean implements Parcelable {

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Article> getDatas() {
        return datas;
    }

    public void setDatas(List<Article> datas) {
        this.datas = datas;
    }

    private int curPage;
    private int pageCount;
    private int size;
    private int total;
    private List<Article> datas;

    protected DataBean(Parcel in) {
        curPage = in.readInt();
        pageCount = in.readInt();
        size = in.readInt();
        total = in.readInt();
        in.readList(datas, getClass().getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(curPage);
        dest.writeInt(pageCount);
        dest.writeInt(size);
        dest.writeInt(total);
        dest.writeList(datas);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
        @Override
        public DataBean createFromParcel(Parcel in) {
            return new DataBean(in);
        }

        @Override
        public DataBean[] newArray(int size) {
            return new DataBean[size];
        }
    };
}
