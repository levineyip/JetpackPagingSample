package com.an.paginglibrary.sample.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Feedback implements Parcelable {

    private DataBean data;
    private int errorCode;
    private String errorMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    protected Feedback(Parcel in) {
        data = in.readParcelable(data.getClass().getClassLoader());
        errorCode = in.readInt();
        errorMsg = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(data, flags);
        dest.writeInt(errorCode);
        dest.writeString(errorMsg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Feedback> CREATOR = new Parcelable.Creator<Feedback>() {
        @Override
        public Feedback createFromParcel(Parcel in) {
            return new Feedback(in);
        }

        @Override
        public Feedback[] newArray(int size) {
            return new Feedback[size];
        }
    };
}
