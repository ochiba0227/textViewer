package moji;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import moji.moji.txtview;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@SuppressWarnings("serial")
class txtfunc extends JFrame{
	static JFrame app=new JFrame();
	JPanel p = new JPanel();
	JPanel northp = new JPanel();
	JPanel southp = new JPanel();
	JPanel sizep = new JPanel();
	JPanel cdp = new JPanel();
	JPanel lup = new JPanel();
	JPanel fsp = new JPanel();
	JPanel fspe = new JPanel();
	JTextField fsize,nowcd;
	JLabel ftxt,cdtxt,lutxt;
	int size;
	static boolean lastopenenable=start.flag;
	static File currentdir;
	boolean open;
  txtfunc(String title, int close){
	JRadioButton lubutton;
    setTitle(title);
    setBounds(100,100,600,200);
    setDefaultCloseOperation(close);
    p.setLayout(new BorderLayout());
    northp.setLayout(new GridLayout(4, 1));
    cdp.setLayout(new GridLayout(1, 1));

    ftxt = new JLabel("フォントサイズを指定(2桁以下で)");//フォントサイズの説明文
    fsize = new JTextField("15", 2);//フォントサイズ入力先
    cdtxt = new JLabel("現在の”開く”選択時の標準ディレクトリ");//カレントディレクトリの説明文
    lutxt = new JLabel("最後に開いたファイルを次回開始時に開く(標準:開かない)");//最後に開いたファイルを開くかの説明文
    if(lastopenenable){
    	lubutton = new JRadioButton("開く",true);
    }
    else{
    	lubutton = new JRadioButton("開かない");
    }
    JButton fsbutton = new JButton("標準ディレクトリを指定");//カレントディレクトリ設定用ボタン
    JButton okbutton = new JButton("設定保存");//保存ボタン
    JButton cbutton = new JButton("キャンセル");//キャンセルボタン
    
    fsize.addActionListener(new okListener());
    fsbutton.addActionListener(new fsListener());
    okbutton.addActionListener(new okListener());
    cbutton.addActionListener(new cListener());
    lubutton.addChangeListener(new lListener());

    sizep.add(ftxt);
    sizep.add(fsize);
    cdp.add(cdtxt);
    if(start.cd!=null){
    	nowcd = new JTextField((start.cd.getPath()));//カレントディレクトリ入力先
    	nowcd.addActionListener(new okListener());
        cdp.add(nowcd);
    }
    fsp.add(fsbutton);
    lup.add(lutxt);
    lup.add(lubutton);
    northp.add(sizep);
    northp.add(cdp);
    northp.add(fsp);
    northp.add(lup);
    southp.add(okbutton);
    southp.add(cbutton);
    p.add("North",northp);
    p.add("South",southp);

    Container contentPane = getContentPane();
    contentPane.add(p, BorderLayout.CENTER);
  }
  public class fsListener implements ActionListener{
	  public void actionPerformed(ActionEvent e){
		  new dirsel();
		  nowcd.setText(dirsel.path);
	  }
  }
  public class lListener implements ChangeListener{
	public void stateChanged(ChangeEvent e) {
		JRadioButton cb = (JRadioButton)e.getSource();
		if (open=cb.isSelected()) {
			cb.setText("開く");
		} else {
			cb.setText("開かない");
		}
	}
  }
  public class okListener implements ActionListener{
	  PrintWriter pw;
	  public void actionPerformed(ActionEvent e){
		  size=txtview.fontsize;
		  if(fsize.getText().length()>2){
			  moji.txtview.fontsize=Integer.parseInt(fsize.getText().substring(0,2));
		  }
		  else{
			  moji.txtview.fontsize=Integer.parseInt(fsize.getText());
		  }
		  fsize.setText(String.valueOf(txtview.fontsize));
		  txtview.temp+=(txtview.fontsize-size);
		  txtview.gyou=txtview.temp;
		  moji.view.repaint();
		  lastopenenable=open;
		  if(dirsel.path!=null){
			  currentdir=new File(dirsel.path);
		  }
		  setVisible(false);
		  try {
			  pw = new PrintWriter(new BufferedWriter(new FileWriter("sysset.ini")));
				pw.println("cd:"+currentdir.getAbsolutePath());
				pw.println("lastopenenable:"+lastopenenable);
				if(moji.selected!=null){
					pw.println("lastopen:"+moji.selected);
					pw.println("lastfilename:"+moji.file.getFile());
				}
				else{
					pw.println("lastopen:");
					pw.println("lastfilename:");
				}
			} catch (IOException e1) {
			}
			pw.close();
		  moji.file.setDirectory(currentdir.getAbsolutePath());
	  }
  }
  public class cListener implements ActionListener{
	  public void actionPerformed(ActionEvent e){
		  setVisible(false);
	  }
  }
}
