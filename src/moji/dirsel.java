package moji;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

public class dirsel {
	static String path;
	dirsel(){
	    Display display = new Display ();
	    Shell shell = new Shell(display,SWT.ON_TOP);//��ɍőO�ʕ\��
	    // �E�B���h�E�̃T�C�Y���w��
	    shell.setSize(0,0);
	    shell.open();
	    DirectoryDialog dialog = new DirectoryDialog(shell);
	    path = dialog.open();
	    shell.dispose();
	    display.dispose ();
	}
}
