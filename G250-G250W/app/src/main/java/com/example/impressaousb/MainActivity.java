package com.example.impressaousb;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.tcp.TcpConnection;
import com.dantsu.escposprinter.connection.usb.UsbConnection;
import com.dantsu.escposprinter.connection.usb.UsbPrintersConnections;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
import com.example.impressaousb.async.AsyncEscPosPrint;
import com.example.impressaousb.async.AsyncEscPosPrinter;
import com.example.impressaousb.async.AsyncTcpEscPosPrint;
import com.example.impressaousb.async.AsyncUsbEscPosPrint;
import com.example.impressaousb.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Configuração de imprimir via USB
        binding.btnImprimirUSB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printUsb();
            }
        });

        //Configuração de imprimir via TCP
        binding.btnImprimirIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printTcp();
            }
        });

    }

    //Configuração da conexão USB
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (MainActivity.ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (usbManager != null && usbDevice != null) {
                            new AsyncUsbEscPosPrint(
                                    context,
                                    new AsyncEscPosPrint.OnPrintFinished() {
                                        @Override
                                        public void onError(AsyncEscPosPrinter asyncEscPosPrinter, int codeException) {
                                            Log.e("Async.OnPrintFinished", "AsyncEscPosPrint.OnPrintFinished : An error occurred !");
                                        }

                                        @Override
                                        public void onSuccess(AsyncEscPosPrinter asyncEscPosPrinter) {
                                            Log.i("Async.OnPrintFinished", "AsyncEscPosPrint.OnPrintFinished : Print is finished !");
                                        }
                                    }
                            )
                                    .execute(getAsyncEscPosPrinter(new UsbConnection(usbManager, usbDevice)));
                        }
                    }
                }
            }
        }
    };
    public void printUsb() {
        UsbConnection usbConnection = UsbPrintersConnections.selectFirstConnected(this);
        UsbManager usbManager = (UsbManager) this.getSystemService(Context.USB_SERVICE);
        if (usbConnection != null && usbManager != null) {
            PendingIntent permissionIntent = PendingIntent.getBroadcast(
                    this,
                    0,
                    new Intent(MainActivity.ACTION_USB_PERMISSION),
                    android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S ? PendingIntent.FLAG_MUTABLE : 0
            );
            IntentFilter filter = new IntentFilter(MainActivity.ACTION_USB_PERMISSION);
            registerReceiver(this.usbReceiver, filter);
            usbManager.requestPermission(usbConnection.getDevice(), permissionIntent);
        }
    }

    //Configuração da conexão TCP
    public void printTcp() {
        final EditText ipAddress = (EditText) this.findViewById(R.id.editIP);
        final EditText portAddress = (EditText) this.findViewById(R.id.editPorta);

        try {
            new AsyncTcpEscPosPrint(
                    this,
                    new AsyncEscPosPrint.OnPrintFinished() {
                        @Override
                        public void onError(AsyncEscPosPrinter asyncEscPosPrinter, int codeException) {
                            Log.e("Async.OnPrintFinished", "AsyncEscPosPrint.OnPrintFinished : An error occurred !");
                        }

                        @Override
                        public void onSuccess(AsyncEscPosPrinter asyncEscPosPrinter) {
                            Log.i("Async.OnPrintFinished", "AsyncEscPosPrint.OnPrintFinished : Print is finished !");
                        }
                    }
            )
                    .execute(
                            this.getAsyncEscPosPrinter(
                                    new TcpConnection(
                                            ipAddress.getText().toString(),
                                            Integer.parseInt(portAddress.getText().toString())
                                    )
                            )
                    );
        } catch (NumberFormatException e) {
            new AlertDialog.Builder(this)
                    .setTitle("Endereço de porta TCP inválido")
                    .setMessage("Port field must be an integer.")
                    .show();
            e.printStackTrace();
        }
    }

    // TEXTO PARA IMPRESSÃO
    @SuppressLint("SimpleDateFormat")
    public AsyncEscPosPrinter getAsyncEscPosPrinter(DeviceConnection printerConnection) {
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 203, 80f, 32);

        String html;
        String String;
        return printer.addTextToPrint(
                "[C]<qrcode size='30'>https://gertec.com.br/</qrcode>\n" +
                        "[L]|\n"
        );
    }
}