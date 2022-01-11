package com.example.suraksha1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
Button b;
Button b1;
TextView t;
    LocationManager locationManager;
    String latitude,longitude;
    private FusedLocationProviderClient m;
    public double longitude1,latittude1;
    public String s,s1;
    public String regStatement="Save me";
    public String regStatement1="save me";
    public String regStatement2="Saveme";
    public String regStatement3="saveme";
    public String regStatement4="Save Me";
    public String regStatement5="save Me";
    public String regStatement6="SaveMe";
    public String regStatement7="saveMe";

private static final String[] SMS_PER={Manifest.permission.SEND_SMS};
    private static final String[] LOC_PER={Manifest.permission.ACCESS_FINE_LOCATION};
private static final int REQ_LOC=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verify();
        myDB=new DatabaseHelper(this);


        //verify1();
        b=(Button)findViewById(R.id.button);
        m = LocationServices.getFusedLocationProviderClient(this);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
fetch();
                Intent i=new Intent(MainActivity.this,Main2Activity.class);
/*i.putExtra("HEY",s);
i.putExtra("HELLO",s1);*/
                startActivity(i);
            }
        });


    }

    private void fetch() {

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest
                .permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission
                    .ACCESS_COARSE_LOCATION)){
                new AlertDialog.Builder(this).setTitle("Requires permission").setMessage("Give permission").
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQ_LOC);
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
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQ_LOC);
            }
        }
        else {
            m.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null)
                    {
                         latittude1=location.getLatitude();
                         longitude1=location.getLongitude();
Demo.abc=String.valueOf(latittude1);
Demo.def=String.valueOf(longitude1);
/*getIntent().putExtra("HEY",s);
getIntent().putExtra("HELLO",s1);
*/



                    }
                }
            });

        }
    }

   /* private void getLocation()
    {
        if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED
         && ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQ_LOC);
        }
        else
        {
            Location locationGps=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNet=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location lop=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if(locationGps!=null)
            {
                double lat=locationGps.getLatitude();
                double lon=locationGps.getLongitude();
                latitude=String.valueOf(lat);
                longitude=String.valueOf(lon);
                t.setText(""+longitude);
                Main2Activity mnc=new Main2Activity();
                mnc.getLoc(latitude,longitude);
            }
            else if(locationNet!=null)
            {
                double lat=locationNet.getLatitude();
                double lon=locationNet.getLongitude();
                latitude=String.valueOf(lat);
                longitude=String.valueOf(lon);
                t.setText(""+longitude);
                Main2Activity mnc=new Main2Activity();
                mnc.getLoc(latitude,longitude);
            }
            else if(lop!=null)
            {
                double lat=lop.getLatitude();
                double lon=lop.getLongitude();
                latitude=String.valueOf(lat);
                longitude=String.valueOf(lon);
                t.setText(""+longitude);
                Main2Activity mnc=new Main2Activity();
                mnc.getLoc(latitude,longitude);
            }
            else
            {
                Toast.makeText(this,"Can't get your location",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onGPS() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }*/

    private void verify() {
        /*int pS= ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SEND_SMS);
        if(pS!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,SMS_PER,1);
        }
        getPer();*/
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) + ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.SEND_SMS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION}, REQ_LOC);
            }
        }


    }
   /* private void getPer()
    {
        if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_LOC);
        }
    }*/
   /* private void verify1()
    {
        int pS= ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION);
        if(pS!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_LOC);
        }
    }*/
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       if(requestCode==REQ_LOC)
       {
           if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
           {

           }else{

           }
       }
   }
    public void senddata(String namee,String phonee)
    {

    }

    public void getSpeechInput(View view) {
       Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
       intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
       intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
       if(intent.resolveActivity(getPackageManager())!=null) {
           startActivityForResult(intent, 10);
       }
       else{
           Toast.makeText(this,"Your device doesn't support this feature",Toast.LENGTH_SHORT).show();
       }

    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        switch(requestCode)
        {
            case 10:
                if(resultCode==RESULT_OK && data!=null)
                {
                    ArrayList<String> result =data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(result.get(0).equals(regStatement) || result.get(0).equals(regStatement1) || result.get(0).equals(regStatement2) || result.get(0).equals(regStatement3) || result.get(0).equals(regStatement4) || result.get(0).equals(regStatement5) || result.get(0).equals(regStatement6) || result.get(0).equals(regStatement7))
                    {
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
                break;
        }
    }
}
