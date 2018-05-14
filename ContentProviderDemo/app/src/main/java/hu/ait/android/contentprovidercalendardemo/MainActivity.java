package hu.ait.android.contentprovidercalendardemo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnInsertCalendar = (Button) findViewById(R.id.btnInsertCalendar);
        btnInsertCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    ContentResolver cr = getContentResolver();

                    ContentValues values = new ContentValues();
                    values.put(CalendarContract.Events.DTSTART, System.currentTimeMillis());
                    values.put(CalendarContract.Events.DTEND, System.currentTimeMillis() + 60000);
                    values.put(CalendarContract.Events.TITLE, "End");
                    values.put(CalendarContract.Events.DESCRIPTION, "We should have a break now");
                    values.put(CalendarContract.Events.CALENDAR_ID, 1);
                    values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

                    Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

                    //getContentResolver().delete(CalendarContract.Events.CONTENT_URI, CalendarContract.Events._ID+"=599", null);

                    Log.d("KEY_LOG", uri.toString());
                    //showCallLog();
                } catch (SecurityException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button btnQueryPhonebook = (Button) findViewById(R.id.btnQueryPhonebook);
        btnQueryPhonebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Cursor c = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                ContactsContract.CommonDataKinds.Phone.NUMBER},
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" LIKE '%Tamás%'",
                        //null,
                        null,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " DESC");

                //Toast.makeText(MainActivity.this, ""+c.getCount(), Toast.LENGTH_LONG).show();

                while (c.moveToNext()) {
                    String name = c.getString(c.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    Log.d("KEY_LOG", name);
                    Toast.makeText(MainActivity.this, name, Toast.LENGTH_LONG).show();
            }
            }
        });

        requestNeededPermission();
    }

    public void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_CALENDAR)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.WRITE_CALENDAR},
                    101);
        } else {
            // we are ok
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permissions granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Permissions are NOT granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
