/*
 *    Copyright (c) 2012 Hai Bison
 *
 *    See the file LICENSE at the root directory of this project for copying
 *    permission.
 */

package group.pals.android.lib.ui.filechooser.io.localfile;

import java.io.File;

/**
 * This is a wrapper for {@link File}.
 *
 * @author Hai Bison
 * @since v3.2
 */
public class ParentFile extends LocalFile {

    public static final String parentSecondName = "..";
    /**
     * Auto-generated by Eclipse.
     */
    private static final long serialVersionUID = 2068049445895497580L;

    public ParentFile(String pathname) {
        super(pathname);
    }// LocalFile()

    public ParentFile(File file) {
        this(file.getAbsolutePath());
    }// LocalFile()

    @Override
    public String getSecondName() {
        return parentSecondName;
    }// getSecondName()

}
