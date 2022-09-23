package com.sandyzfeaklab.breakdown_app.activities.materials

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mcdev.splitbuttonlibrary.OnButtonClickListener
import com.mcdev.splitbuttonlibrary.SplitButton
import com.mcdev.splitbuttonlibrary.SplitMenu
import com.sandyzfeaklab.breakdown_app.R

class Material_with_machin_kt : AppCompatActivity() {

    private lateinit var splitBtn: SplitButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_options_dialog)

        //TODO make butons with list of station

//        splitBtn = findViewById(R.id.split_btn)
//        splitBtn.apply {
//            splitBtn.setTextColor(R.color.black)
//            splitBtn.setIconColor(android.R.color.white)
//            splitBtn.setBgColor(android.R.color.holo_orange_light)
//            splitBtn.itemColor=R.color.primary
//            setMenuItems(R.menu.fill_mac_menu)
//            setOnButtonClickListener(object : OnButtonClickListener {
//                override fun onClick(itemId: Int, itemTitle: String?) {
//
//                    if (itemId == R.id.fill_menu_hammrer) {
//                        Toast.makeText(this@Material_with_machin_kt,"Hammer",Toast.LENGTH_LONG).show()
//
//                    }else if (itemId == R.id.fill_menu_swing_master) {
//
//                        Toast.makeText(this@Material_with_machin_kt,"Swing Master",Toast.LENGTH_LONG).show()
//
//                    } else if (itemId == R.id.fill_menu_bandsaw) {
//                        Toast.makeText(this@Material_with_machin_kt,"Bandsaw",Toast.LENGTH_LONG).show()
//
//                    }
//                    else if (itemId == R.id.fill_menu_robot) {
//                        Toast.makeText(this@Material_with_machin_kt,"robot",Toast.LENGTH_LONG).show()
//
//                    }
//                }
//            })
//        }


    }

}