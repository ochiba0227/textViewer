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

    ftxt = new JLabel("�t�H���g�T�C�Y���w��(2���ȉ���)");//�t�H���g�T�C�Y�̐�����
    fsize = new JTextField("15", 2);//�t�H���g�T�C�Y���͐�
    cdtxt = new JLabel("���݂́h�J���h�I�����̕W���f�B���N�g��");//�J�����g�f�B���N�g���̐�����
    lutxt = new JLabel("�Ō�ɊJ�����t�@�C��������J�n���ɊJ��(�W��:�J���Ȃ�)");//�Ō�ɊJ�����t�@�C�����J�����̐�����
    if(lastopenenable){
    	lubutton = new JRadioButton("�J��",true);
    }
    else{
    	lubutton = new JRadioButton("�J���Ȃ�");
    }
    JButton fsbutton = new JButton("�W���f�B���N�g�����w��");//�J�����g�f�B���N�g���ݒ�p�{�^��
    JButton okbutton = new JButton("�ݒ�ۑ�");//�ۑ��{�^��
    JButton cbutton = new JButton("�L�����Z��");//�L�����Z���{�^��
    
    fsize.addActionListener(new okListener());
    fsbutton.addActionListener(new fsListener());
    okbutton.addActionListener(new okListener());
    cbutton.addActionListener(new cListener());
    lubutton.addChangeListener(new lListener());

    sizep.add(ftxt);
    sizep.add(fsize);
    cdp.add(cdtxt);
    if(start.cd!=null){
    	nowcd = new JTextField((start.cd.getPath()));//�J�����g�f�B���N�g�����͐�
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
			cb.setText("�J��");
		} else {
			cb.setText("�J���Ȃ�");
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
