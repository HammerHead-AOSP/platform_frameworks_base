/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.android.systemui.tuner;

import android.os.Bundle;

import android.support.v14.preference.PreferenceFragment;
import android.support.v7.preference.ListPreference;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;

import com.android.systemui.R;

import com.android.systemui.tuner.TunerService;
import com.android.systemui.tuner.TunerService.Tunable;

public class OtherPrefs extends PreferenceFragment implements 
        Preference.OnPreferenceChangeListener {

    private static final String KEY_SYSUI_QQS_COUNT = "sysui_qqs_count_key";
    public static final String NUM_QUICK_TILES = "sysui_qqs_count";

    private ListPreference mSysuiQqsCount;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.other_settings);

        mSysuiQqsCount = (ListPreference) findPreference(KEY_SYSUI_QQS_COUNT);
        if (mSysuiQqsCount != null) {
           mSysuiQqsCount.setOnPreferenceChangeListener(this);
           int SysuiQqsCount = TunerService.get(getContext()).getValue(NUM_QUICK_TILES, 5);
           mSysuiQqsCount.setValue(Integer.toString(SysuiQqsCount));
           mSysuiQqsCount.setSummary(mSysuiQqsCount.getEntry());
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mSysuiQqsCount) {
            String SysuiQqsCount = (String) newValue;
            int SysuiQqsCountValue = Integer.parseInt(SysuiQqsCount);
            TunerService.get(getContext()).setValue(NUM_QUICK_TILES, SysuiQqsCountValue);
            int SysuiQqsCountIndex = mSysuiQqsCount
                    .findIndexOfValue(SysuiQqsCount);
            mSysuiQqsCount
                    .setSummary(mSysuiQqsCount.getEntries()[SysuiQqsCountIndex]);
            return true;
          }

         return false;
     }
}