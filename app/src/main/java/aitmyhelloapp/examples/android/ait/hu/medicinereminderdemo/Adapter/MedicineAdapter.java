package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.R;
import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.data.MedicineInfo;

/**
 * Created by xinyunxing on 12/9/2014.
 */
public class MedicineAdapter extends BaseAdapter {

    private Context context;
    private List<MedicineInfo> medicineInfos;

    public MedicineAdapter(Context context, List<MedicineInfo> medicineInfos) {
        this.context = context;
        this.medicineInfos = medicineInfos;
    }

    @Override
    public int getCount() {
        return medicineInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return medicineInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<MedicineInfo> getClassSchedule() {
        return medicineInfos;
    }

    public void addMedicineInfo(MedicineInfo Item) {
        medicineInfos.add(Item);
    }

    public void removeMedicine(int index) {
        medicineInfos.remove(index);
    }


    static class ViewHolder {
        TextView tvMedicineName;
        TextView tvMedicineTime;
        TextView tvLength;
        TextView tvSecondReminder;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v== null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.row_reminder, null);
            ViewHolder holder = new ViewHolder();
            holder.tvMedicineName = (TextView) v.findViewById(R.id.tvMedicineName);
            holder.tvMedicineTime= (TextView) v.findViewById(R.id.tvMedicineTime);
            holder.tvLength = (TextView) v.findViewById(R.id.tvLength);
            holder.tvSecondReminder = (TextView) v.findViewById(R.id.tvSecondReminder);
            v.setTag(holder);
        }

        final MedicineInfo medicineInfo = medicineInfos.get(position);
        if(medicineInfo != null)
        {
            final ViewHolder holder = (ViewHolder) v.getTag();
            holder.tvMedicineName.setText("Medicine Name: "+medicineInfo.getMedicineName());
            Calendar c = medicineInfo.getTakeTime();

            holder.tvMedicineTime.setText("Time to take Medicine: "+new StringBuilder()
                    .append(pad(c.get(Calendar.HOUR))).append(":")
                    .append(pad(c.get(Calendar.MINUTE))));
            holder.tvSecondReminder.setText("Second reminder required: "+ medicineInfo.getSecondReminder());
            holder.tvLength.setText("Snooze Frequency: "+medicineInfo.getLength().getStringValue()+"minutes");

        }
        return v;
    }

    /** Add padding to numbers less than ten */
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

}
