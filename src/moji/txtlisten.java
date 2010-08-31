package moji;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
class txtlisten extends JFrame implements ActionListener{
	JPanel p = new JPanel();
	JTextField text1;
	public static int pages;
	
  txtlisten(String title, int close){
    setTitle(title);
    setBounds(100, 100, 300,100);
    setDefaultCloseOperation(close);

    text1 = new JTextField("", 20);
    JButton button = new JButton("Žæ“¾");
    text1.addActionListener(this);
    button.addActionListener(this);
    
    p.add(text1);
    p.add(button);

    Container contentPane = getContentPane();
    contentPane.add(p, BorderLayout.CENTER);
  }

  public void actionPerformed(ActionEvent e){
    pages=Integer.parseInt(text1.getText());
    moji.txtview.temp-=((moji.app.getWidth()-moji.insets.left-moji.insets.right)-20)*txtlisten.pages;
	moji.txtview.gyou=moji.txtview.temp;
    moji.view.repaint();
    setVisible(false);
  }
}
