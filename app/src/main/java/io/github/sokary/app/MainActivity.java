package io.github.sokary.app;

import static io.github.sokary.app.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cn.sokary.archs.android.AppController;

public class MainActivity extends AppController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
    }
}