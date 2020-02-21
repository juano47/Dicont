package dicont.dicont;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    //usamos LocalBroadcast al ser una transmisión (broadcast) interna a la app
    //no necesita ser declarada en el manifest
    private LocalBroadcastManager broadcaster;

    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            if(remoteMessage.getData().get("preferenceId")!=null) {
                Intent intent = new Intent("preferenceIdMercadoPagoData");
                intent.putExtra("preferenceId", remoteMessage.getData().get("preferenceId"));
                Log.e("firebase service", "llega");
                broadcaster.sendBroadcast(intent);
            }

            if (remoteMessage.getData().get("aviso_titulo")!=null
                    && remoteMessage.getData().get("aviso_mensaje")!=null
                    && remoteMessage.getData().get("aviso_tipo")!=null){

                showNotification(remoteMessage.getData().get("aviso_titulo"),
                        remoteMessage.getData().get("aviso_mensaje"),
                        remoteMessage.getData().get("aviso_tipo"));
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

        }
    }

    private void showNotification(String titulo, String mensaje, String tipo) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        switch (tipo){
            case "promocion":
                notificationBuilder.setSmallIcon(R.drawable.ic_promocion);
                break;
            case "vencimiento":
                notificationBuilder.setSmallIcon(R.drawable.ic_vencimiento);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
