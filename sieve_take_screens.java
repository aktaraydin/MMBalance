package com.mining.mmbalance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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


public class sieve_take_screens extends AppCompatActivity {

    public static List<sieve_Sieve> sieveList;
    private ListView listView;
    private sieve_ListVieverAdapter_sieve adapter;
    static int sieveAmount_int;

    Button goWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sieve_take_screens);

        //how many sieves added
        sieveAmount_int = Integer.parseInt(sieve_analysis.sieveAmount.getText().toString());

        sieveList = new ArrayList<>(sieveAmount_int);

        for (int i = 0; i < sieveAmount_int; i++) {
            sieveList.add(new sieve_Sieve(""));
        }

        //Bind listview
        listView = findViewById(R.id.listview);

        //Create adapter and set it to listview
        adapter = new sieve_ListVieverAdapter_sieve(sieve_take_screens.this,sieveList);
        listView.setAdapter(adapter);

        //Set an item click listener to show toast
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
                showEditDialog(position);

            }

        });

        //go to result
        goWeight = findViewById(R.id.goWeight);
        goWeight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(sieveListTest() == true) {
                    showpopUp(goWeight);
                } else {
                    Intent i = new Intent(sieve_take_screens.this, sieve_take_weight.class);
                    startActivity(i);
                }

            }

        });
    }

    public boolean sieveListTest () {

        boolean check = false;

        for (int i = 0; i<sieveAmount_int; i++) {
            if (sieveList.get(i).getSieveName().length() == 0) {
                check = true;
            } else {
                check = false;
            }
        }
        return check;
    }

    private void showEditDialog(final int position){
        final Dialog dialog = new Dialog(sieve_take_screens.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sieve_edit_dialog);

        //Bind dialog views
        final EditText renameEdittext=dialog.findViewById(R.id.rename_edittext);
        final Button renameButton=dialog.findViewById(R.id.addWeight_button);

        //Set clicked album name to rename edittext
        renameEdittext.setText(sieveList.get(position).getSieveName());

        //When rename button is clicked, first rename edittext should be checked if it is empty
        //If it is not empty, data and listview item should be changed.
        renameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!renameEdittext.getText().toString().equals("")) {
                    sieveList.get(position).setSieveName(renameEdittext.getText().toString());
                    //Notify adapter about changing of model list
                    adapter.notifyDataSetChanged();
                    //Close dialog
                    dialog.dismiss();
                } else {
                    Toast.makeText(sieve_take_screens.this, getResources().getString(R.string.can_not_be_empty), Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();

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

class sieve_Sieve {

    String sieveName;


    public sieve_Sieve(String sieveName) { this.sieveName = sieveName; }

    public String getSieveName() {
        return sieveName;
    }

    public void setSieveName(String sieveName) {
        this.sieveName = sieveName;
    }

}

class sieve_ListVieverAdapter_sieve extends BaseAdapter {

    public static List <sieve_Sieve> sieveList;
    private LayoutInflater inflater;

    public sieve_ListVieverAdapter_sieve(Context context, List<sieve_Sieve> sieveList) {
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Adapter has to know your model list
        this.sieveList = sieveList;
    }


    @Override
    public int getCount() {
        //Return sieveList item count
        //It is the same with your model list size.
        return sieveList.size();

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
        holder.albumName.setText(sieveList.get(position).getSieveName());
        return convertView;


    }

    public class ViewHolder{
        TextView albumName;

    }



}
