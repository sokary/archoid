package cn.sokary.archs.android;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


public class AppEntity implements Serializable {
    public Integer id;

    @PrimaryKey
    @NonNull
    public String guid;

    public String name;
    public String no;
    public String createdBy;
    public String updatedBy;
    public String createdAt;
    public String updatedAt;
    public int status;
    public boolean isdel;
    public Integer seq;
    public int uploadStatus;

//    public String getCurrentTime(){ return AppFormatter.getCurrentTime(); }

    public String getLabel(){ return name; }

}
