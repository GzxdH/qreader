/*
 * Copyright (C) 2009-2015 FBReader.ORG Limited <contact@fbreader.org>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.android.fbreader.preferences;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.administrator.qreader.R;

import org.geometerplus.android.fbreader.FBReader;
import org.geometerplus.android.fbreader.dict.DictionaryUtil;
import org.geometerplus.android.fbreader.libraryService.BookCollectionShadow;
import org.geometerplus.android.fbreader.network.auth.ActivityNetworkContext;
import org.geometerplus.android.fbreader.preferences.background.BackgroundPreference;
import org.geometerplus.android.fbreader.preferences.fileChooser.FileChooserCollection;
import org.geometerplus.android.fbreader.sync.SyncOperations;
import org.geometerplus.android.util.DeviceType;
import org.geometerplus.android.util.UIUtil;
import org.geometerplus.fbreader.Paths;
import org.geometerplus.fbreader.fbreader.ActionCode;
import org.geometerplus.fbreader.fbreader.FBReaderApp;
import org.geometerplus.fbreader.fbreader.FBView;
import org.geometerplus.fbreader.fbreader.options.*;
import org.geometerplus.fbreader.network.sync.SyncData;
import org.geometerplus.fbreader.network.sync.SyncUtil;
import org.geometerplus.fbreader.tips.TipsManager;
import org.geometerplus.zlibrary.core.application.ZLKeyBindings;
import org.geometerplus.zlibrary.core.language.Language;
import org.geometerplus.zlibrary.core.network.JsonRequest;
import org.geometerplus.zlibrary.core.network.ZLNetworkException;
import org.geometerplus.zlibrary.core.options.Config;
import org.geometerplus.zlibrary.core.options.ZLIntegerRangeOption;
import org.geometerplus.zlibrary.core.options.ZLStringOption;
import org.geometerplus.zlibrary.core.resources.ZLResource;
import org.geometerplus.zlibrary.text.view.style.ZLTextBaseStyle;
import org.geometerplus.zlibrary.text.view.style.ZLTextNGStyleDescription;
import org.geometerplus.zlibrary.text.view.style.ZLTextStyleCollection;
import org.geometerplus.zlibrary.ui.android.library.ZLAndroidLibrary;
import org.geometerplus.zlibrary.ui.android.view.ZLAndroidPaintContext;

import java.text.DecimalFormatSymbols;
import java.util.*;

public class PreferenceActivity extends ZLPreferenceActivity {

    public PreferenceActivity() {
        super("Preferences");
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getListView().setPadding(0, 0, 0, 0); // 666
        View view = View.inflate(this, R.layout.setting_head, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        getListView().addHeaderView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
    }

    @Override
    protected void init(Intent intent) {
        final ViewOptions viewOptions = new ViewOptions();
        final ZLTextStyleCollection collection = viewOptions.getTextStyleCollection();
//		 TODO: use user-defined locale, not the default one,
        final ZLTextBaseStyle baseStyle = collection.getBaseStyle();

        addPreference(new FontPreference( //y 字体
                this, Resource.getResource("text"),
                baseStyle.FontFamilyOption, false
        ));
        addPreference(new FontStylePreference( //y 字型
                this, Resource.getResource("fontStyle"),
                baseStyle.BoldOption, baseStyle.ItalicOption
        ));

        ZLTextNGStyleDescription description = collection.getDescriptionList().get(1);
        addPreference(new StringPreference(
                this, description.TextIndentOption, // 首行缩进
                StringPreference.Constraint.LENGTH,
                Resource, "firstLineIndent"
        ));
        addPreference(new StringPreference(
                this, description.MarginLeftOption,
                StringPreference.Constraint.LENGTH,
                Resource, "leftIndent"
        ));
        addPreference(new StringPreference(
                this, description.MarginRightOption,
                StringPreference.Constraint.LENGTH,
                Resource, "rightIndent"
        ));
        addPreference(new StringPreference(
                this, description.MarginTopOption, // 段前距
                StringPreference.Constraint.LENGTH,
                Resource, "spaceBefore"
        ));
        addPreference(new StringPreference( // 段后距
                this, description.MarginBottomOption,
                StringPreference.Constraint.LENGTH,
                Resource, "spaceAfter"
        ));

        final Screen cssScreen = createPreferenceScreen("css"); // 使用CSS样式
        cssScreen.addOption(baseStyle.UseCSSFontFamilyOption, "fontFamily");
        cssScreen.addOption(baseStyle.UseCSSFontSizeOption, "fontSize");
        cssScreen.addOption(baseStyle.UseCSSTextAlignmentOption, "textAlignment");
        cssScreen.addOption(baseStyle.UseCSSMarginsOption, "margins");
    }
}
