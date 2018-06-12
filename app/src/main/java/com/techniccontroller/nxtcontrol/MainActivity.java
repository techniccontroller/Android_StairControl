package com.techniccontroller.nxtcontrol;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends ActionBarActivity {

    /*
     * Arduino Bluetooth-Modul. Android Test-Applikation www.frag-duino.de
     */

    // UUID fuer Kommunikation mit Seriellen Modulen
    private UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String LOG_TAG = "FRAGDUINO";

    private Button btnStop;
    private Button btnVor;
    private Button btnZuruck;
    private Button btnLinks;
    private Button btnRechts;
    private Button btnHochVorne;
    private Button btnRunterVorne;
    private Button btnHochHinten;
    private Button btnRunterHinten;

    // Variablen
    private BluetoothAdapter adapter = null;
    private BluetoothSocket socket = null;
    private OutputStream stream_out = null;
    private InputStream stream_in = null;
    private boolean is_connected = false;
    private static String mac_adresse; // MAC Adresse des Bluetooth Adapters

    // Befehle
    private byte stop = 0;
    private byte vor = 1;
    private byte zuruck = 2;
    private byte links = 3;
    private byte rechts = 4;
    private byte hochVorne = 5;
    private byte runterVorne = 6;
    private byte hochHinten = 7;
    private byte runterHinten = 8;
    private byte notstop = 10;
    private byte start = 15;
    private byte startpositionO = 22;
    private byte startpositionM = 21;
    private byte startpositionU = 20;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                // TODO Auto-generated method stub
                int action = arg1.getAction();
                if(action == MotionEvent.ACTION_DOWN) {
                    send(notstop);
                    Log.d(LOG_TAG,"Stop");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    send(stop);
                    Log.d(LOG_TAG,"Stop");
                    return true;
                }
                return false;
            }
        });

        btnVor = (Button) findViewById(R.id.btnVor);
        btnVor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                // TODO Auto-generated method stub
                int action = arg1.getAction();
                if(action == MotionEvent.ACTION_DOWN) {
                    send(vor);
                    Log.d(LOG_TAG,"Vor");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    send(stop);
                    Log.d(LOG_TAG,"Stop");
                    return true;
                }
                return false;
            }
        });

        btnZuruck = (Button) findViewById(R.id.btnZur);
        btnZuruck.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                // TODO Auto-generated method stub
                int action = arg1.getAction();
                if(action == MotionEvent.ACTION_DOWN) {
                    send(zuruck);
                    Log.d(LOG_TAG,"Zurück");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    send(stop);
                    Log.d(LOG_TAG,"Stop");
                    return true;
                }
                return false;
            }
        });

        btnLinks = (Button) findViewById(R.id.btnLinks);
        btnLinks.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                // TODO Auto-generated method stub
                int action = arg1.getAction();
                if(action == MotionEvent.ACTION_DOWN) {
                    send(links);
                    Log.d(LOG_TAG,"Links");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    send(stop);
                    Log.d(LOG_TAG, "Stop");
                    return true;
                }
                return false;
            }
        });

        btnRechts = (Button) findViewById(R.id.btnRechts);
        btnRechts.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                // TODO Auto-generated method stub
                int action = arg1.getAction();
                if(action == MotionEvent.ACTION_DOWN) {
                    send(rechts);
                    Log.d(LOG_TAG,"Rechts");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    send(stop);
                    Log.d(LOG_TAG,"Stop");
                    return true;
                }
                return false;
            }
        });

        btnHochVorne = (Button) findViewById(R.id.btnHochVorne);
        btnHochVorne.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                // TODO Auto-generated method stub
                int action = arg1.getAction();
                if(action == MotionEvent.ACTION_DOWN) {
                    send(hochVorne);
                    Log.d(LOG_TAG,"HochVorne");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    send(stop);
                    Log.d(LOG_TAG,"Stop");
                    return true;
                }
                return false;
            }
        });

        btnRunterVorne = (Button) findViewById(R.id.btnRunterVorne);
        btnRunterVorne.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                // TODO Auto-generated method stub
                int action = arg1.getAction();
                if(action == MotionEvent.ACTION_DOWN) {
                    send(runterVorne);
                    Log.d(LOG_TAG,"RunterVorne");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    send(stop);
                    Log.d(LOG_TAG,"Stop");
                    return true;
                }
                return false;
            }
        });

        btnHochHinten = (Button) findViewById(R.id.btnHochHinten);
        btnHochHinten.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                // TODO Auto-generated method stub
                int action = arg1.getAction();
                if(action == MotionEvent.ACTION_DOWN) {
                    send(hochHinten);
                    Log.d(LOG_TAG,"HochHinten");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    send(stop);
                    Log.d(LOG_TAG,"Stop");
                    return true;
                }
                return false;
            }
        });

        btnRunterHinten = (Button) findViewById(R.id.btnRunterHinten);
        btnRunterHinten.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {

                // TODO Auto-generated method stub
                int action = arg1.getAction();
                if(action == MotionEvent.ACTION_DOWN) {
                    send(runterHinten);
                    Log.d(LOG_TAG,"RunterHinten");
                    return true;
                } else if (action == MotionEvent.ACTION_UP) {
                    send(stop);
                    Log.d(LOG_TAG,"Stop");
                    return true;
                }
                return false;
            }
        });

        Log.d(LOG_TAG, "Bluetest: OnCreate");

        // Textfeld setzen
        ((TextView) findViewById(R.id.text_uuid)).setText("UUID: " + uuid);

        // Verbindung mit Bluetooth-Adapter herstellen
        adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null || !adapter.isEnabled()) {
            Toast.makeText(this, "Bitte Bluetooth aktivieren",
                    Toast.LENGTH_LONG).show();
            Log.d(LOG_TAG,
                    "onCreate: Bluetooth Fehler: Deaktiviert oder nicht vorhanden");
            finish();
            return;
        } else
            Log.d(LOG_TAG, "onCreate: Bluetooth-Adapter ist bereit");
    }
    public void start(View v){
        send(start);
        Log.d(LOG_TAG,"Start");
    }

    public void startposition(View v){
        Log.d(LOG_TAG, "startposition1");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.startposition);
        builder.setItems(R.array.startposition_array, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Log.d(LOG_TAG, "startposition2");
                switch (which) {
                    case 0:
                        send(startpositionO);
                        Log.d(LOG_TAG,"Startposition von OBEN");
                        break;
                    case 1:
                        send(startpositionM);
                        Log.d(LOG_TAG,"Startposition von MITTE");
                        break;
                    case 2:
                        send(startpositionU);
                        Log.d(LOG_TAG,"Startposition von UNTEN");
                        break;
                }
            }
        });
        Log.d(LOG_TAG, "startposition3");
        builder.show();
    }

    public void verbinden(View v) {
        mac_adresse = ((EditText) findViewById(R.id.text_adresse)).getText()
                .toString();
        Log.d(LOG_TAG, "Verbinde mit " + mac_adresse);

        BluetoothDevice remote_device = adapter.getRemoteDevice(mac_adresse);

        // Socket erstellen
        try {
            socket = remote_device
                    .createRfcommSocketToServiceRecord(uuid);
            Log.d(LOG_TAG, "Socket erstellt");
        } catch (Exception e) {
            Log.e(LOG_TAG, "Socket Erstellung fehlgeschlagen: " + e.toString());
        }

        adapter.cancelDiscovery();

        // Socket verbinden
        try {
            socket.connect();
            Log.d(LOG_TAG, "Socket verbunden");
            is_connected = true;
        } catch (IOException e) {
            is_connected = false;
            Log.e(LOG_TAG, "Socket kann nicht verbinden: " + e.toString());
        }

        // Socket beenden, falls nicht verbunden werden konnte
        if (!is_connected) {
            try {
                socket.close();
            } catch (Exception e) {
                Log.e(LOG_TAG,
                        "Socket kann nicht beendet werden: " + e.toString());
            }
        }

        // Outputstream erstellen:
        try {
            stream_out = socket.getOutputStream();
            Log.d(LOG_TAG, "OutputStream erstellt");
        } catch (IOException e) {
            Log.e(LOG_TAG, "OutputStream Fehler: " + e.toString());
            is_connected = false;
        }

        // Inputstream erstellen
        try {
            stream_in = socket.getInputStream();
            Log.d(LOG_TAG, "InputStream erstellt");
        } catch (IOException e) {
            Log.e(LOG_TAG, "InputStream Fehler: " + e.toString());
            is_connected = false;
        }

        if (is_connected) {
            Toast.makeText(this, "Verbunden mit " + mac_adresse,
                    Toast.LENGTH_LONG).show();
            ((Button) findViewById(R.id.bt_verbinden))
                    .setBackgroundColor(Color.GREEN);
        } else {
            Toast.makeText(this, "Verbindungsfehler mit " + mac_adresse,
                    Toast.LENGTH_LONG).show();
            ((Button) findViewById(R.id.bt_verbinden))
                    .setBackgroundColor(Color.RED);
        }
    }

    /*public void senden(View v) {
        String message = ((EditText) findViewById(R.id.text_eingabe)).getText()
                .toString();
        byte[] msgBuffer = message.getBytes();
        if (is_connected) {
            Log.d(LOG_TAG, "Sende Nachricht: " + message);
            try {
                stream_out.write(msgBuffer);
            } catch (IOException e) {
                Log.e(LOG_TAG,
                        "Bluetest: Exception beim Senden: " + e.toString());
            }
        }
    }*/

    public void send(byte code) {
        if (is_connected) {
            Log.d(LOG_TAG, "Sende Nachricht: " + code);
            try {
                stream_out.write(code);
            } catch (IOException e) {
                Log.e(LOG_TAG,
                        "Bluetest: Exception bei "+ code + " :  " + e.toString());
            }
        }
    }

    /*public void links() {

        byte code = 0b00000010;
        if (is_connected) {
            Log.d(LOG_TAG, "Sende Nachricht: links");
            try {
                stream_out.write(code);
            } catch (IOException e) {
                Log.e(LOG_TAG,
                        "Bluetest: Exception bei Links: " + e.toString());
            }
        }
    }

    public void stop(){
        byte code = 0b00000001;
        if (is_connected) {
            Log.d(LOG_TAG, "Sende Nachricht: stop");
            try {
                stream_out.write(code);
            } catch (IOException e) {
                Log.e(LOG_TAG,
                        "Bluetest: Exception beim Stoppen: " + e.toString());
            }
        }
    }*/

    public void empfangen(View v) {
        byte[] buffer = new byte[1024]; // Puffer
        int laenge; // Anzahl empf. Bytes
        String msg = "";
        try {
            if (stream_in.available() > 0) {
                laenge = stream_in.read(buffer);
                Log.d(LOG_TAG,
                        "Anzahl empfangender Bytes: " + String.valueOf(laenge));

                // Message zusammensetzen:
                for (int i = 0; i < laenge; i++)
                    msg += (char) buffer[i];

                Log.d(LOG_TAG, "Message: " + msg);
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

            } else
                Toast.makeText(this, "Nichts empfangen", Toast.LENGTH_LONG)
                        .show();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Fehler beim Empfangen: " + e.toString());
        }
    }

    public void trennen(View v) {
        if (is_connected && stream_out != null) {
            is_connected = false;
            ((Button) findViewById(R.id.bt_verbinden))
                    .setBackgroundColor(Color.RED);
            Log.d(LOG_TAG, "Trennen: Beende Verbindung");
            try {
                stream_out.flush();
                socket.close();
            } catch (IOException e) {
                Log.e(LOG_TAG,
                        "Fehler beim beenden des Streams und schliessen des Sockets: "
                                + e.toString());
            }
        } else
            Log.d(LOG_TAG, "Trennen: Keine Verbindung zum beenden");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy. Trenne Verbindung, falls vorhanden");
        trennen(null);
    }
}
