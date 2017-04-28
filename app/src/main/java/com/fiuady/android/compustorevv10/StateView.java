package com.fiuady.android.compustorevv10;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Manuel on 19/04/2017.
 */
public class StateView implements Parcelable {
    private String title;
    private boolean selected;

    public StateView(String title, boolean selected) {
        this.title = title;
        this.selected = selected;
    }

    private StateView(Parcel in){

        title = in.readString();
        selected = in.readByte()!=0;

    }
    public int describeContents(){
        return 0;
    }
    public void writeToParcel (Parcel out, int flags){

        out.writeString(title);
        out.writeByte((byte) (selected ? 1 : 0));
    }
    public static final Creator<StateView> CREATOR = new Creator<StateView>(){
        public StateView createFromParcel(Parcel in){
            return new StateView(in);
        }
        public StateView[] newArray(int size){
            return new StateView[size];
        }
    };


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
