package candybar.lib.adapters.dialog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.CompoundButtonCompat;

import com.danimahardhika.android.helpers.core.ColorHelper;

import java.util.List;

import candybar.lib.R;
import candybar.lib.items.InAppBilling;

/*
 * CandyBar - Material Dashboard
 *
 * Copyright (c) 2014-2016 Dani Mahardhika
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class InAppBillingAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<InAppBilling> mInAppBillings;

    private int mSelectedPosition = 0;

    public InAppBillingAdapter(@NonNull Context context, @NonNull List<InAppBilling> inAppBillings) {
        mContext = context;
        mInAppBillings = inAppBillings;
    }

    @Override
    public int getCount() {
        return mInAppBillings.size();
    }

    @Override
    public InAppBilling getItem(int position) {
        return mInAppBillings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.fragment_inapp_dialog_item_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (position >= 0 && position < mInAppBillings.size()) {
            final InAppBilling inAppBilling = mInAppBillings.get(position);
            if (inAppBilling != null) {
                holder.radio.setChecked(mSelectedPosition == position);

                // Set radio button colors
                int accentColor = ColorHelper.getAttributeColor(mContext, R.attr.cb_colorAccent);
                int[][] states = new int[][] {
                    new int[] { android.R.attr.state_checked },
                    new int[] { -android.R.attr.state_checked }
                };
                int[] colors = new int[] {
                    accentColor,
                    ColorHelper.setColorAlpha(accentColor, 0.5f)
                };
                CompoundButtonCompat.setButtonTintList(holder.radio, new ColorStateList(states, colors));

                String product = inAppBilling.getPrice() + " - " +
                        inAppBilling.getProductName();
                holder.name.setText(product);

                // Fix text color for dark theme
                if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.R) {
                    boolean isDarkMode = (mContext.getResources().getConfiguration().uiMode 
                        & android.content.res.Configuration.UI_MODE_NIGHT_MASK) 
                        == android.content.res.Configuration.UI_MODE_NIGHT_YES;
                    holder.name.setTextColor(isDarkMode ? android.graphics.Color.WHITE : android.graphics.Color.BLACK);
                }

                holder.container.setOnClickListener(v -> {
                    mSelectedPosition = position;
                    notifyDataSetChanged();
                });
            }
        }
        return view;
    }

    private static class ViewHolder {

        private final RadioButton radio;
        private final TextView name;
        private final LinearLayout container;

        ViewHolder(View view) {
            radio = view.findViewById(R.id.radio);
            name = view.findViewById(R.id.name);
            container = view.findViewById(R.id.container);
        }
    }

    public InAppBilling getSelectedProduct() {
        return mInAppBillings.get(mSelectedPosition);
    }
}
