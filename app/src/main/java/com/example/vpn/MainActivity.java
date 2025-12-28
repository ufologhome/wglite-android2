package com.example.vpn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.net.VpnService;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOn = findViewById(R.id.btn_on);
        Button btnOff = findViewById(R.id.btn_off);

        btnOn.setOnClickListener(v -> {
            Intent intent = VpnService.prepare(this);
            if (intent != null) startActivityForResult(intent, 0);
            else startService(new Intent(this, MyVpnService.class));
        });

        btnOff.setOnClickListener(v -> stopService(new Intent(this, MyVpnService.class)));
    }
}
