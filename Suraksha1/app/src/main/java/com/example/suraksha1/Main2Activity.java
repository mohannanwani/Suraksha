package com.example.suraksha1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

public class Main2Activity extends AppCompatActivity {

     DatabaseHelper myDB;
    EditText e1,e2;
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    private FusedLocationProviderClient m;
    private static final int REQ_LOC=1;
    String latitude,longitude;
    String latt,longg;
    private SensorManager sm;
    private float aceVal;
    private float aceLast;
    private float shake;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        myDB=new DatabaseHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        e1=(EditText)findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText2);
        b1=(Button)findViewById(R.id.button2);
        b2=(Button)findViewById(R.id.button5);
        b3=(Button)findViewById(R.id.button7);
        b4=(Button)findViewById(R.id.button6);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        aceVal = SensorManager.GRAVITY_EARTH;
        aceLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;
        AddData();
        viewAll();
        //deleteData();
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Integer deletedRows=myDB.delete(e2.getText().toString());
                if(deletedRows>0)
                {
                    Toast.makeText(Main2Activity.this,"Data deleted",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Main2Activity.this,"Data not deleted",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            aceLast = aceVal;
            aceVal = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = aceVal - aceLast;
            shake = shake * 0.9f + delta;
            if (shake >12 ) {

                Cursor cursor=myDB.getData();
                if(cursor.getCount()==0)
                {
                    Toast.makeText(getApplicationContext(),"No data",Toast.LENGTH_SHORT).show();
                }
                else
                {


                    while(cursor.moveToNext())
                    {
                        String n=cursor.getString(0);
                        String p=cursor.getString(1);

                        SmsManager sms=SmsManager.getDefault();
                        sms.sendTextMessage(p,null,"I am in danger\nMy current coordinates(location) are:\n"+Demo.abc+","+Demo.def,null,null);
                    }
                }



            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
   /* public void deleteData()
    {
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer deletedRows=myDB.delete(e1.getText().toString());
                if(deletedRows>0)
                {
                    Toast.makeText(Main2Activity.this,"Data deleted",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Main2Activity.this,"Data not deleted",Toast.LENGTH_LONG).show();
                }
            }
        });
    }*/


    public void AddData()
    {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              boolean isInserted=  myDB.insertData(e1.getText().toString(),e2.getText().toString());
              if(isInserted=true)
                  Toast.makeText(Main2Activity.this,"Data inserted",Toast.LENGTH_LONG).show();
              else
                  Toast.makeText(Main2Activity.this,"Data not inserted",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void viewAll()
    {
        b3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res=myDB.getData();
                        if(res.getCount()==0)
                        {
                            showMessage("Error","Nothing found");
                            return;
                        }
                        StringBuffer buffer=new StringBuffer();
                        while(res.moveToNext())
                        {
                            buffer.append("Name:"+res.getString(0)+"\n");
                            buffer.append("Phone:"+res.getString(1)+"\n");
                        }
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }
   /* public void show2()
    {
        Cursor cursor=myDB.getData();
        if(cursor.getCount()==0)
        {
            showMessage("Error","Nothing found");
        }
        StringBuffer buffer=new StringBuffer();
        while(cursor.moveToNext())
        {
            buffer.append("Name:"+cursor.getString(0)+"\n");
            buffer.append("Phone:"+cursor.getString(1)+"\n");
        }
        showMessage("Data",buffer.toString());
    }*/
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
    public void sendinfo()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest
                .permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this,Manifest.permission
                    .ACCESS_COARSE_LOCATION)){
                new AlertDialog.Builder(this).setTitle("Requires permission").setMessage("Give permission").
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(Main2Activity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQ_LOC);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();

            }
            else
            {
                ActivityCompat.requestPermissions(Main2Activity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQ_LOC);
            }
        }
        else {
            m.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null)
                    {
                        Double latittude=location.getLatitude();
                        Double longitude=location.getLongitude();

                    }
                }
            });

        }


    }
    public void send(View v)
    {





/*latt=getIntent().getStringExtra("HEY");
longg=getIntent().getStringExtra("HELLO");*/

        Cursor cursor=myDB.getData();
        if(cursor.getCount()==0)
        {
            Toast.makeText(getApplicationContext(),"No data",Toast.LENGTH_SHORT).show();
        }
        else
        {


            while(cursor.moveToNext())
            {
                String n=cursor.getString(0);
                String p=cursor.getString(1);

                SmsManager sms=SmsManager.getDefault();
                sms.sendTextMessage(p,null,"I am in danger\nMy current coordinates(location) are:\n"+Demo.abc+","+Demo.def,null,null);
            }
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQ_LOC)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {

            }else{

            }
        }
    }
}
