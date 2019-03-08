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

package org.geometerplus.android.fbreader;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.qreader.R;

import org.geometerplus.android.fbreader.api.FBReaderIntents;
import org.geometerplus.android.fbreader.popup.MainMenuWindow;
import org.geometerplus.android.fbreader.popup.ProgressPopup;
import org.geometerplus.android.fbreader.popup.SettingPopup;
import org.geometerplus.android.util.OrientationUtil;
import org.geometerplus.fbreader.bookmodel.TOCTree;
import org.geometerplus.fbreader.fbreader.ActionCode;
import org.geometerplus.fbreader.fbreader.FBReaderApp;
import org.geometerplus.zlibrary.core.application.ZLApplication;
import org.geometerplus.zlibrary.core.resources.ZLResource;
import org.geometerplus.zlibrary.text.view.ZLTextView;
import org.geometerplus.zlibrary.text.view.ZLTextWordCursor;

/**
 * 快速翻看
 */
public final class NavigationPopup extends ZLApplication.PopupPanel implements View.OnClickListener{

    public final static String ID = "NavigationPopup";

    private volatile NavigationWindow myWindow;
    private volatile MainMenuWindow myWindowTop;
    private volatile FBReader myActivity;
    private volatile RelativeLayout myRoot;
    private ZLTextWordCursor myStartPosition;
    private final FBReaderApp myKooReader;
    private volatile boolean myIsInProgress;
    private ZLTextView.PagePosition pagePosition;
    private TextView mTextViewToc, mTextViewProgress, mTextViewFonts;
    private ImageButton mButtonBookMark;
    private ImageButton mButtonBookShelf;
    private LinearLayout mButtonBack;

    public NavigationPopup(FBReaderApp fbReader) {
        super(fbReader);
        myKooReader = fbReader;
    }

    public void setPanelInfo(FBReader activity, RelativeLayout root) {
        myActivity = activity;
        myRoot = root;
    }

    public void runNavigation() {
        if (myWindow == null || myWindow.getVisibility() == View.GONE) {
            myIsInProgress = false;
            Application.showPopup(ID);
        }
    }

    @Override
    protected void show_() {
        setStatusBarVisibility(true);
        if (myActivity != null) {
            createPanel(myActivity, myRoot);
        }
        if (myWindow != null) {
            myWindow.show();
        }
        if (myWindowTop != null) {
            myWindowTop.show();
        }
    }

    @Override
    protected void hide_() {
        setStatusBarVisibility(false);
        if (myWindow != null) {
            myWindow.hide();
        }
        if (myWindowTop != null) {
            myWindowTop.hide();
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        if (visible) {
            myActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); // 设置状态栏
        } else {
            myActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    protected void update() {

    }

    private void createPanel(FBReader activity, RelativeLayout root) {
        if (myWindow != null && activity == myWindow.getContext() && myWindowTop != null) {
            return;
        }
        activity.getLayoutInflater().inflate(R.layout.navigation_panel, root);
        activity.getLayoutInflater().inflate(R.layout.mainmenu, root);
        myWindow = (NavigationWindow) root.findViewById(R.id.navigation_panel);
        myWindowTop = (MainMenuWindow) root.findViewById(R.id.mainmenuwindow);
        init();
        initClick();
    }

    private void initClick() {
        mTextViewToc.setOnClickListener(this);
        mTextViewProgress.setOnClickListener(this);
        mTextViewFonts.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
        mButtonBookMark.setOnClickListener(this);
//        mButtonBookShelf.setOnClickListener(this);
    }

    private void init() {
        mTextViewToc = (TextView) myWindow.findViewById(R.id.navigation_toc);//目录
        mTextViewFonts = (TextView) myWindow.findViewById(R.id.navigation_fonts);//设置
        mTextViewProgress = (TextView) myWindow.findViewById(R.id.navigation_progress);
        mButtonBookMark = (ImageButton) myWindowTop.findViewById(R.id.mark);
        mButtonBack = (LinearLayout) myWindowTop.findViewById(R.id.back);
        mButtonBookShelf = (ImageButton) myWindowTop.findViewById(R.id.bookshelf);
    }

    final void removeWindow(Activity activity) {

        if (myWindow != null && activity == myWindow.getContext()) {
            final ViewGroup root = (ViewGroup) myWindow.getParent();
            myWindow.hide();
            root.removeView(myWindow);
            myWindow = null;
        }
        if (myWindowTop != null && activity == myWindowTop.getContext()) {
            final ViewGroup root = (ViewGroup) myWindowTop.getParent();
            myWindowTop.hide();
            root.removeView(myWindowTop);
            myWindowTop = null;
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.navigation_toc) {
            toc();

        } else if (i == R.id.navigation_progress) {
            progress();

        } else if (i == R.id.navigation_fonts) {
            fonts();

        } else if (i == R.id.mark) {
            myKooReader.addBookMark();
            Toast.makeText(myActivity, "书签添加成功", Toast.LENGTH_SHORT).show();
        } else if (i == R.id.back) {
            myActivity.finish();
        }
//        else if (i == R.id.bookshelf) {
//            Toast.makeText(myActivity, "进行加入书架操作", Toast.LENGTH_SHORT).show();
//        }
    }

    private void toc() {
        Application.hideActivePopup();
        final Intent intent =
                new Intent(myActivity.getApplicationContext(), TOCActivity.class);
        FBReaderIntents.putBookExtra(intent, myKooReader.getCurrentBook());
        FBReaderIntents.putBookmarkExtra(intent, myKooReader.createBookmark(100, true));
        OrientationUtil.startActivity(myActivity, intent);
    }

    private void progress() {
        //隐藏其他的pop
        Application.hideActivePopup();
        //显示我们需要的进度pop
        ((ProgressPopup) myKooReader.getPopupById(ProgressPopup.ID)).runNavigation();
    }

    private void fonts() {
        Application.hideActivePopup();
        ((SettingPopup) myKooReader.getPopupById(SettingPopup.ID)).runNavigation();
    }

    private void mark() {
        Application.hideActivePopup();
        Application.runAction(ActionCode.SELECTION_BOOKMARK);
    }
}
