package com.vymo.assignment.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pullrequest implements Parcelable {
    private String patch_url;


    protected Pullrequest(Parcel in) {
        patch_url = in.readString();
    }

    public static final Creator<Pullrequest> CREATOR = new Creator<Pullrequest>() {
        @Override
        public Pullrequest createFromParcel(Parcel in) {
            return new Pullrequest(in);
        }

        @Override
        public Pullrequest[] newArray(int size) {
            return new Pullrequest[size];
        }
    };

    public String getPatch_url() {
        return patch_url;
    }

    public void setPatch_url(String patch_url) {
        this.patch_url = patch_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(patch_url);
    }
}
