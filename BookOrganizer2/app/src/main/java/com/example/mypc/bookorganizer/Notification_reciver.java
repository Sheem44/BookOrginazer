package com.example.mypc.bookorganizer;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Random;

public  class Notification_reciver extends BroadcastReceiver {
    private final String CHANEL_ID = "read_notify";
    private final int NOTIFICATION_ID = 001;
    private Context context = this.context;
    private String [] sents={"Today Reader tomorrow Leader, Do not forget reading today ",
            "A reader lives a thousand lives before he dies . . .Do not forget reading today",
            "Reading a good books is like conversation with the finest (people) of the past centuries,Do not forget reading today ",
            "Books are a uniquely portable magic, Do not forget reading today" ,
            "Reading a book is a free ticket to anywhere, What about getting one now!!",
            "If you feel alon try to read, Reading brings us unknown friends"};
    /* private String name ;

    public String getName() {
        name= MainActivity.prefConfig.readName();
        if ( name.equals(null) )
            name=".";
        return name;
    }*/

    public static String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent notificationIntent = new Intent(context,MainActivity.class );
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);




            NotificationCompat.Builder builder;
            builder = new NotificationCompat.Builder(context, CHANEL_ID);
            builder.setSmallIcon(R.drawable.book_organizer_logo)
                    .setContentTitle("Hello " + MainActivity.prefConfig.readName() + "=)")
                    .setContentText(getRandom(sents))
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true);


            // Add as notification
            NotificationManagerCompat manager = NotificationManagerCompat.from(context);
            manager.notify(NOTIFICATION_ID, builder.build());


    }

}
