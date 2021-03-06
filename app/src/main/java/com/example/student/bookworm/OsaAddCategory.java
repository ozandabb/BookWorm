package com.example.student.bookworm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import Database.DBHandler;

public class OsaAddCategory extends AppCompatActivity implements View.OnClickListener {

    DBHandler db;
    EditText osuAddCat;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osa_add_category);

        osuAddCat = findViewById(R.id.osuAddCat);
        db = new DBHandler(this);


        LinearLayout linearLayout = findViewById(R.id.deleteosa);
        linearLayout.setOnClickListener(this);

    }

    public void addCatBook(View view){
        if(TextUtils.isEmpty(osuAddCat.getText()) ){
            osuAddCat.setError("Enter an Value!");
            osuAddCat.requestFocus();

        }else {
            name = osuAddCat.getText().toString();

            boolean result = db.addCategoryOsu(name);
            osuAddCat.getText().clear();


            if (result == true) {
                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, DashboardModernCategory.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void deleteUser(View view){
        name = osuAddCat.getText().toString();
        boolean result = db.deleteuser( name );

        if(result == true){
            Toast.makeText(getApplicationContext(),"Success!",Toast.LENGTH_LONG).show();
          Intent intent = new Intent(this, DashboardModernCategory.class);
           startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Failed!",Toast.LENGTH_LONG).show();
        }


    }

    public void update( View view){
        if(TextUtils.isEmpty(osuAddCat.getText()) ){
            osuAddCat.setError("Enter an Value!");
            osuAddCat.requestFocus();

        }else {
            name = osuAddCat.getText().toString();
            boolean result = db.userUpdate(name);
            if (result == true) {
                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, DashboardModernCategory.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_LONG).show();
            }
        }

    }


    public void goBack(View v){
        Intent myItent = new Intent(this,DashboardModernCategory.class);
        startActivity(myItent);
    }
    public void goModify(View v){
        Intent myItent = new Intent(this,DashboardModify.class);
        startActivity(myItent);
    }

    @Override
    public void onClick(View v) {



        if(TextUtils.isEmpty(osuAddCat.getText()) ) {
            osuAddCat.setError("Enter an Value!");
            osuAddCat.requestFocus();
        }
        else {
            switch (v.getId()) {
                case R.id.deleteosa:
                    alertDialog();
                    break;
            }
        }
    }

    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Are your sure want to delete ?");
        dialog.setTitle("Dialog Box");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        name = osuAddCat.getText().toString();
                        boolean result = db.deleteuser( name );

                        if(result == true){
                            Toast.makeText(getApplicationContext(),"Success!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(OsaAddCategory.this, DashboardModernCategory.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"Failed!",Toast.LENGTH_LONG).show();
                        }
//                        Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();

                    }

                });
        dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
}
