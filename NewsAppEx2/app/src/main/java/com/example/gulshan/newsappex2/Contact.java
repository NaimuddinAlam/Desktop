package com.example.gulshan.newsappex2;


public class Contact {

    //private variables
    int _id;
    String _title;
    String _imgurl;
    String _time;
    String _dis;
   // String _phone_number;


    // Empty constructor
    public Contact(){

    }
    // constructor
    public Contact(int id, String title, String imgurl, String time,String dis){
        this._id = id;
        this._title = title;
        this._imgurl= imgurl;
        this._time =  time;
        this._dis = dis;


//        this._fname = fname;
//        this._sname = sname;
//        this._phone_number = phone_number;
    }

    // constructor
    public Contact(String title, String imgurl, String time,String dis){
        this._title = title;
        this._imgurl= imgurl;
        this._time =  time;
        this._dis = dis;
    }

    // getting ID


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_imgurl() {
        return _imgurl;
    }

    public void set_imgurl(String _imgurl) {
        this._imgurl = _imgurl;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public String get_dis() {
        return _dis;
    }

    public void set_dis(String _dis) {
        this._dis = _dis;
    }
}

