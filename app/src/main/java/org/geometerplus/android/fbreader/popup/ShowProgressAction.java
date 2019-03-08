package org.geometerplus.android.fbreader.popup;


import org.geometerplus.android.fbreader.FBAndroidAction;
import org.geometerplus.android.fbreader.FBReader;
import org.geometerplus.fbreader.fbreader.FBReaderApp;
import org.geometerplus.zlibrary.text.model.ZLTextModel;
import org.geometerplus.zlibrary.text.view.ZLTextView;

/**
 * Created by Administrator on 2016/8/23.
 */
public class ShowProgressAction extends FBAndroidAction {

    ShowProgressAction(FBReader baseActivity, FBReaderApp fbreader) {
        super(baseActivity, fbreader);
    }

    @Override
    public boolean isVisible() {
        final ZLTextView view = (ZLTextView)Reader.getCurrentView();
        final ZLTextModel textModel = view.getModel();
        return textModel != null && textModel.getParagraphsNumber() != 0;
    }
    @Override
    protected void run(Object... params) {
        BaseActivity.progress();
    }
}
