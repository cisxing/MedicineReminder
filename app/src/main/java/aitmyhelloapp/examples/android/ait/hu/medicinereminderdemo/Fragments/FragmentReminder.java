package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.Adapter.MedicineAdapter;
import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.MainActivity;
import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.R;
import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.SetTimeActivity;
import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.data.MedicineInfo;

/**
 * Created by xinyunxing on 12/9/2014.
 */
public class FragmentReminder extends ListFragment {

    public static final int REQUEST_NEW_REMINDER_CODE = 100;
    private static final int CONTEXT_ACTION_DELETE = 10;
    private static final int CONTEXT_ACTION_EDIT = 11;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentReminder() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //List<MedicineInfo> infos = new ArrayList<MedicineInfo>();
        List<MedicineInfo> infos =MedicineInfo.listAll(MedicineInfo.class);
        //I am trying this. Someone said that be careful with this because this may result in null if the fragment is detached
        MedicineAdapter adapter = new MedicineAdapter(getActivity().getApplicationContext(), infos);
        //set the adapter of the list
        setListAdapter(adapter);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.reminder_action, menu);
        //this will need to be here instead of onCreate because content View is not set up there
        registerForContextMenu(getListView());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //need to find the id and change it, probably a button that is touched
        if (id == R.id.action_new_reminder) {
            Intent i = new Intent();
            i.setClass(this.getActivity(), SetTimeActivity.class);
            startActivityForResult(i, REQUEST_NEW_REMINDER_CODE);

            return true;
        }
        if (id == R.id.action_new_deleteAll) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            while (!getListAdapter().isEmpty()) {
                MedicineInfo medicineInfo = (MedicineInfo) getListAdapter().getItem(0);
                //medicineInfo.delete();
                ((MedicineAdapter) getListAdapter()).removeMedicine(0);
            }
            MedicineInfo.deleteAll(MedicineInfo.class);
            ((MedicineAdapter) getListAdapter()).notifyDataSetChanged();
            ((MainActivity)getActivity()).alarmService();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                //what does this serializable do? Very confusing
                MedicineInfo medicineInfo = (MedicineInfo) data.getSerializableExtra("KEY_PLACE");
                medicineInfo.save();
                ((MedicineAdapter) getListAdapter()).addMedicineInfo((MedicineInfo) data.getSerializableExtra("KEY_PLACE"));
                ((MedicineAdapter) getListAdapter()).notifyDataSetChanged();
                ((MainActivity)getActivity()).alarmService();
                break;
            case Activity.RESULT_CANCELED:
                //Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Menu");
        menu.add(0, CONTEXT_ACTION_DELETE, 0, "Delete");
        menu.add(0,CONTEXT_ACTION_EDIT,0,"Edit");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CONTEXT_ACTION_DELETE) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            MedicineInfo medicine = (MedicineInfo) getListAdapter().getItem(info.position);

            medicine.delete();
            ((MedicineAdapter) getListAdapter()).removeMedicine(info.position);
            ((MedicineAdapter) getListAdapter()).notifyDataSetChanged();
            ((MainActivity)getActivity()).alarmService();
        }


        else if (item.getItemId() == CONTEXT_ACTION_EDIT) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            MedicineInfo medicineInfo = (MedicineInfo) getListAdapter().getItem(info.position);
            Intent i = new Intent();
            //i.putExtra(DAY_CODE,mItem.id);
            //all these are for saving the status of the activity
            i.putExtra("medicine_name",medicineInfo.getMedicineName());
            i.putExtra("medicine_time",medicineInfo.getTakeTime().getTime());
            i.putExtra("medicine_length",medicineInfo.getLength().getValue());
            i.putExtra("medicine_secondReminder",medicineInfo.getSecondReminder());
            i.setClass(this.getActivity(), SetTimeActivity.class);
            startActivityForResult(i, REQUEST_NEW_REMINDER_CODE);
            //schedule.delete();
            medicineInfo.delete();
            ((MedicineAdapter) getListAdapter()).removeMedicine(info.position);
            ((MedicineAdapter) getListAdapter()).notifyDataSetChanged();
            ((MainActivity)getActivity()).alarmService();

        }else {
            return false;
        }
        return true;
    }
}
