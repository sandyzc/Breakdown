package com.sandyzfeaklab.breakdown_app.activities.materials;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.mcdev.splitbuttonlibrary.SplitButton;
import com.mcdev.splitbuttonlibrary.SplitMenu;
import com.sandyzfeaklab.breakdown_app.R;
import com.sandyzfeaklab.breakdown_app.activities.MainActivity;
import com.tombayley.activitycircularreveal.CircularReveal;
import com.vivekkaushik.datepicker.DatePickerTimeline;

import java.util.List;

public class MaterialWithMachine extends AppCompatActivity {
    private Dialog dialog;

    private SplitButton splitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_with_machine);

        List<SplitMenu> splitMenus = null;


        splitMenus.add(new SplitMenu(0,"Fill",R.drawable.mech,null,null));
        splitMenus.add(new SplitMenu(0,"Fill-2",R.drawable.mech,null,null));
        splitMenus.add(new SplitMenu(0,"Fill-3",R.drawable.mech,null,null));

        splitButton= findViewById(R.id.split_btn);

        splitButton.setBgColor(R.color.white);
        splitButton.setTextColor(R.color.primary);
        splitButton.setMenuItems(splitMenus,0);



    }

    public void show_station_dialog() {


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.material_with_catergory);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_back));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;


        dialog.show();
    }

    public void clicked_on_fill(View view) {

        Bundle bundle =new Bundle();
        bundle.putString("m/c","FILL");

        CircularReveal.presentActivity(new CircularReveal.Builder(
                MaterialWithMachine.this,
                view,
                new Intent(MaterialWithMachine.this, Material_list_Activity.class).putExtras(bundle),1000
        ));



    }
}