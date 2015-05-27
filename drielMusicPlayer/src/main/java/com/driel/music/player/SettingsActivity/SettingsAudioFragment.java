/*
 * Copyright (C) 2014 Saravan Pantham
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.driel.music.player.SettingsActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.driel.music.player.EqualizerActivity.EqualizerActivity;
import com.driel.music.player.R;

/**
 * @author Saravan Pantham
 */
public class SettingsAudioFragment extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Thread timer = new Thread() {
            public void run() {
                try {
                    // sleep(R.integer.SplashActivityTime);
                    sleep(1000);
                } catch (InterruptedException iEx) {
                    iEx.printStackTrace();
                } finally {
                    Intent mainActivity = new Intent(SettingsAudioFragment.this,
                            EqualizerActivity.class);

                    startActivity(mainActivity);
                    finish();
                }
            }
        };
        timer.start();
    }
}