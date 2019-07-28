package com.lilliemountain.ticketbookingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.lilliemountain.ticketbookingapp.R;

public class Event implements Parcelable {

	String eventid,name,time,date,price,venue,description;

    protected Event(Parcel in) {
        eventid = in.readString();
        name = in.readString();
        time = in.readString();
        date = in.readString();
        price = in.readString();
        venue = in.readString();
        description = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };


    public Event() {
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Event(String eventid, String name, String time, String date, String price, String venue, String description) {
        this.eventid = eventid;
        this.name = name;
        this.time = time;
        this.date = date;
        this.price = price;
        this.venue = venue;
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventid);
        dest.writeString(name);
        dest.writeString(time);
        dest.writeString(date);
        dest.writeString(price);
        dest.writeString(venue);
        dest.writeString(description);
    }
}
