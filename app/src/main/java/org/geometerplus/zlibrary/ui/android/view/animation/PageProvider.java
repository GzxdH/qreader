package org.geometerplus.zlibrary.ui.android.view.animation;

import org.geometerplus.zlibrary.core.view.ZLView;
import org.geometerplus.zlibrary.ui.android.curl.CurlPage;

/**
 * ******************************************
 * 作    者 ：  杨越
 * 版    本 ：  1.0
 * 创建日期 ：  2016/3/28 ${time}
 * 描    述 ：
 * 修订历史 ：
 * ******************************************
 */

public interface PageProvider
{
    boolean hasNextPage();

    boolean hasPreviousPage();

    // CURL_LEFT = 1;
//     CURL_NONE = 0;
//     CURL_RIGHT = 2;
    void shift(int param);

    void updatePage(CurlPage paramhz, int paramInt1, int paramInt2, ZLView.PageIndex paramPageIndex);
}