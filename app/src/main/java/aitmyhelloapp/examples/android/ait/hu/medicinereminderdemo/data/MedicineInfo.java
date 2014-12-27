package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.data;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by xinyunxing on 12/9/2014.
 */
public class MedicineInfo extends SugarRecord<MedicineInfo> implements Serializable{


    public enum RepeatLength{
        first("10",0),
        second("15",1),
        third("30",2),
        fourth("45",3);

        private String StringValue;
        private int value;


        private RepeatLength(String toString, int value1){
            StringValue = toString;
            value = value1;
        }

        public static RepeatLength fromInt(int value) {
            for (RepeatLength c : RepeatLength.values()) {
                if (c.value == value) {
                    return c;
                }
            }
            return first;
        }

        public int getValue(){return value;}

        public String getStringValue() {
            return StringValue;
        }
    }

    private String medicineName;
    private Calendar takeTime;
    private RepeatLength length;
    private Boolean secondReminder;

    //private long id;

    /*empty constructor for SugarOrm*/
    public MedicineInfo()
    {


    }

    //constructor
    public MedicineInfo(String medicineName, Calendar takeTime, RepeatLength length, Boolean secondReminder )
    {
        this.medicineName = medicineName;
        this.takeTime= takeTime;
        this.length= length;
        this.secondReminder = secondReminder;
    }


    //some setter and getter methods
    public RepeatLength getLength() {
        return length;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public Calendar getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Calendar takeTime) {
        this.takeTime = takeTime;
    }

    public void setLength(RepeatLength length) {
        this.length = length;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public Boolean getSecondReminder() {
        return secondReminder;
    }

    public void setSecondReminder(Boolean secondReminder) {
        this.secondReminder = secondReminder;
    }
}
