package edu.orangecoastcollege.cs273.kfrederick5tmorrissey1ischenck.occstudentsuccesscenter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents a paired day and time. The day is in written format (Ex. Tuesday)
 * and the time is in 24 hour format with a colon separating hours and minutes (Ex. 13:45)
 * Created by Travis on 11/17/2016.
 */

public class DayTime implements Parcelable{
    private int id;
    private String day;
    private float time;

    /**
     * Default Constructor
     */
    public DayTime() {
        id = -1;
        day = "";
        time = 0.0f;
    }

    /**
     * Constructor.
     * @param day String the day in written format.
     * @param time String the time in 24 hour format.
     */
    public DayTime(String day, float time) {
        this.day = day;
        this.time = time;
    }

    public DayTime(int id, String day, float time){
        this.id = id;
        this.day = day;
        this.time = time;
    }

    protected DayTime(Parcel in) {
        id = in.readInt();
        day = in.readString();
        time = in.readFloat();
    }

    public static final Creator<DayTime> CREATOR = new Creator<DayTime>() {
        @Override
        public DayTime createFromParcel(Parcel in) {
            return new DayTime(in);
        }

        @Override
        public DayTime[] newArray(int size) {
            return new DayTime[size];
        }
    };

    /**
     * Gets id.
     * @return int id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id
     * @param id int id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the day.
     * @return String the day.
     */
    public String getDay() {
        return day;
    }

    /**
     * Sets the new day.
     * @param day String the new day.
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * Gets the time.
     * @return String the time in 24 hour format.
     */
    public Float getTime() {
        return time;
    }

    /**
     * Sets the new time.
     * @param time String the new time.
     */
    public void setTime(Float time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DayTime) {
            DayTime otherDayTime = (DayTime) obj;
            return this.id == otherDayTime.id && this.day.equals(otherDayTime.day) &&
                    this.time == otherDayTime.time;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(day);
        parcel.writeFloat(time);
    }
}
