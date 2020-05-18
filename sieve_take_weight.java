package com.mining.mmbalance;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Dialog;;
import java.util.ArrayList;
import java.util.List;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;




public class sieve_take_weight extends AppCompatActivity {

    protected static List<sieve_Weight> weightList;
    private ListView weightView;
    private sieve_ListVieverAdapter_weight adapter_weight;

    Button goResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sieve_take_weight);
        weightList = new ArrayList<>();

        int i = 0;
        for (i = 0; i < sieve_take_screens.sieveAmount_int; i++) {
            weightList.add(new sieve_Weight(""));

        }
        //Bind listview
        weightView = findViewById(R.id.weightview);

        //Create adapter and set it to listview
        adapter_weight = new sieve_ListVieverAdapter_weight(sieve_take_weight.this, weightList);
        weightView.setAdapter(adapter_weight);

        //Set an item click listener to show toast
        weightView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showEditDialog(position);

            }

        });

        goResult = findViewById(R.id.goResult);
        goResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weightListTest()) {
                    showpopUp(goResult);
                } else {
                    Intent i = new Intent(sieve_take_weight.this, sieve_calculation.class);
                    startActivity(i);
                }
            }
        });

    }

    private void showEditDialog(final int position){
        final Dialog dialog = new Dialog(sieve_take_weight.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sieve_edit_dialog);

        //Bind dialog views
        final EditText renameEdittext=(EditText)dialog.findViewById(R.id.rename_edittext);
        final Button renameButton=(Button)dialog.findViewById(R.id.addWeight_button);


        //Set clicked album name to rename edittext
        renameEdittext.setText(weightList.get(position).getWeightName());


        //When rename button is clicked, first rename edittext should be checked if it is empty
        //If it is not empty, data and listview item should be changed.
        renameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!renameEdittext.getText().toString().equals("")) {
                    weightList.get(position).setWeightName(renameEdittext.getText().toString());
                    //Notify adapter about changing of model list
                    adapter_weight.notifyDataSetChanged();
                    //Close dialog
                    dialog.dismiss();
                } else {
                    Toast.makeText(sieve_take_weight.this, getResources().getString(R.string.can_not_be_empty), Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    public boolean weightListTest () {

        boolean check = false;

        for (int i = 0; i<sieve_take_screens.sieveAmount_int; i++) {
            if (weightList.get(i).getWeightName().length() == 0) {
                check = true;
            } else {
                check = false;
            }
        }
        return check;
    }


    public void showpopUp(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }



}

class sieve_Weight {


    private String weightName;

    public sieve_Weight(String weightName) { this.weightName = weightName; }

    public String getWeightName() {
        return weightName;
    }

    public void setWeightName(String weightName) {
        this.weightName = weightName;
    }

}
class sieve_ListVieverAdapter_weight extends BaseAdapter {

    private List <sieve_Weight> weightList;
    private LayoutInflater inflater;

    public sieve_ListVieverAdapter_weight(Context context, List<sieve_Weight> weightList) {
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Adapter has to know your model list
        this.weightList = weightList;
    }


    @Override
    public int getCount() {
        //Return sieveList item count
        //It is the same with your model list size.
        return weightList.size();


    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // View view=inflater.inflate(R.layout.listview_item,null,false);
        if(convertView==null){
            convertView=inflater.inflate(R.layout.listview_item,null,false);
            //Create a holder object and bind its views
            ViewHolder viewHolder=new ViewHolder();
            viewHolder.albumName=(TextView)convertView.findViewById(R.id.album_name);

            //Attach it to convertView
            convertView.setTag(viewHolder);
        }
        //Get holder object from convertView
        ViewHolder holder = (ViewHolder)convertView.getTag();
        holder.albumName.setText(weightList.get(position).getWeightName());
        return convertView;


    }

    public class ViewHolder{
        TextView albumName;

    }

}





