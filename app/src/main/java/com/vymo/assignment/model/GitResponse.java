package com.vymo.assignment.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GitResponse implements Parcelable {

    private int number;
    private String title;
    private UserModel user;
    private Pullrequest pull_request;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Pullrequest getPull_request() {
        return pull_request;
    }

    public void setPull_request(Pullrequest pull_request) {
        this.pull_request = pull_request;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.number);
        dest.writeString(this.title);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.pull_request, flags);
    }

    public GitResponse() {
    }

    protected GitResponse(Parcel in) {
        this.number = in.readInt();
        this.title = in.readString();
        this.user = in.readParcelable(UserModel.class.getClassLoader());
        this.pull_request = in.readParcelable(Pullrequest.class.getClassLoader());
    }

    public static final Parcelable.Creator<GitResponse> CREATOR = new Parcelable.Creator<GitResponse>() {
        @Override
        public GitResponse createFromParcel(Parcel source) {
            return new GitResponse(source);
        }

        @Override
        public GitResponse[] newArray(int size) {
            return new GitResponse[size];
        }
    };
}
