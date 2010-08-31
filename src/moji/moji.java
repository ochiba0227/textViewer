package moji;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class moji extends JFrame implements KeyListener{
	static JFrame app=new JFrame();
	static txtview view = new txtview();
	static txtlisten page = new txtlisten("ページ数を入力",JFrame.HIDE_ON_CLOSE);
	static txtfunc func;
	static Insets insets;
	static File selected=null;
	static FontMetrics nowfont;
	static FileDialog file = new FileDialog(app , "開きたいファイルを選択" , FileDialog.LOAD);
	private static String ver = "2.50";
	PrintWriter pw;

	moji(){
		func = new txtfunc("設定",JFrame.HIDE_ON_CLOSE);
		txtfunc.currentdir=start.cd;
		if(start.flag==false){
			file.setDirectory(start.cd.getAbsolutePath());
			file.setVisible(true);
			if(file.getFile()!=null){
				selected=new File(file.getDirectory()+file.getFile());
				start.filename=file.getFile();
			}
			try {
				pw = new PrintWriter(new BufferedWriter(new FileWriter("sysset.ini")));
				pw.println("cd:"+txtfunc.currentdir.getAbsolutePath());
				pw.println("lastopenenable:"+txtfunc.lastopenenable);
				if(file.getFile()!=null){
					pw.println("lastopen:"+selected);
					pw.println("lastfilename:"+file.getFile());
				}
				else{
					pw.println("lastopen:");
					pw.println("lastfilename:");
				}
			} catch (IOException e1) {
			}
			pw.close();
		}
		else{
			file.setDirectory(start.cd.getAbsolutePath());
			selected=start.selected;
		}
		app.addKeyListener(this);
		//タイトル設定
		if(selected!=null){
			app.setTitle("てきすとびゅ～わver."+ver+":"+start.filename);
		}
		else{
			app.setTitle("てきすとびゅ～わver."+ver);
		}
		//ウィンドウサイズ設定(タイトルや枠も含んだサイズ)
	    app.setSize(800, 600);
	    //ウィンドウメッセージ取得用設定
	    WindowListener e= new WindowListener();
	    app.addWindowListener(e);
	    //キャンバスを配置
	    app.add(view);
	    //ウィンドウ表示
	    app.setVisible(true);
	}

  public static final void main(final String[] args){
	  new start();
	  new moji(); 
  }
  
  	static class txtview extends JPanel{
		static int charlen;
		static int charhei;
		static int eof=10000;
		static int gyou=0;
		static int temp=0;
		static int line=0;
		static int sline;
		static int fontsize=15;
		static String str;
		String fontname=getFont().getName();
	 	public Graphics2D g2;
		double r;
		
		public void paint(Graphics g){
			char  ch[];
			int kazu=1;
			int nextchar=0;
			int off=0;
			int eos=0;
			g2 = (Graphics2D)g; 
			g2.setFont(new Font(fontname , Font.PLAIN , fontsize));
		    insets = app.getInsets();//枠の大きさを取得
		    filling(g2);//画面を白で塗りつぶす
			moji.nowfont=getFontMetrics(g2.getFont());
			charhei=moji.nowfont.getHeight();
			if(selected!=null&&selected.canRead()){ //ファイルが選択されているか
			r = 270.0;
			g2.rotate(3.1415926535 * r / 180.0,0,0);//＋270度回転して描画
			try{//文字列を画面に描画
				LineNumberReader br = new LineNumberReader(new FileReader(selected));
					while((str=br.readLine())!=null && gyou<app.getWidth()-insets.left-insets.right){
						charlen=moji.nowfont.stringWidth(str);
						if(charlen>(app.getHeight()-insets.top-insets.bottom)){
							ch = str.toCharArray();
							while(eos!=1){
								kazu=1;
									while((app.getHeight()-insets.top-insets.bottom)>moji.nowfont.charsWidth(ch,off,kazu)+nextchar&&ch.length>off+kazu){
										kazu+=1;
										if(ch.length>off+kazu){
											nextchar=moji.nowfont.charWidth(ch[off+kazu]);
										}
									}
									if(ch.length==off+kazu){
										eos=1;
									}
								str=String.valueOf (ch,off,kazu);
								g2.drawString(str, -(app.getHeight()-insets.top-insets.bottom), gyou+insets.top);
								off+=kazu;
								gyou+=charhei;
								br.setLineNumber(br.getLineNumber()+1);
							}
							eos=0;
							off=0;
						}
						else{
							g2.drawString(str, -(app.getHeight()-insets.top-insets.bottom), gyou+insets.top);
							gyou+=charhei;
						}						
					}
					if(line==0){
						sline=br.getLineNumber()-line;
					}
					if(str==null){
						eof=br.getLineNumber();
					}
					br.close();
				}catch(FileNotFoundException e){
				  System.out.println(e);
				}catch(IOException e){
				  System.out.println(e);
				}
				g2.rotate(-3.1415926535 * r / 180.0,0,0);//-270度回転して回転を元に戻す
				}
			else {
				if(selected==null){
					g2.drawString("ファイルが選択されていません。",50,50);
					}
				else if (selected.canRead()==false){
					g2.drawString("ファイルが存在しません。",50,50);
					}
				}
			}
		}
  	
  	static void filling(Graphics2D g){//塗りつぶし
  		g.setPaint(Color.WHITE);
		g.fill(new Rectangle2D.Double(0d, 0d, (app.getWidth()-insets.left-insets.right), (app.getHeight()-insets.top-insets.bottom)));
		g.setPaint(Color.BLACK);
	}
	
	//ウィンドウに関するクラス
	class WindowListener extends WindowAdapter{
	//ウィンドウの閉じるボタンをクリックされた
	  public void windowClosing(WindowEvent e){
	    System.exit(0);
	  }
	}

	public void keyPressed(KeyEvent e) {
		int key,count;
		key=e.getKeyCode();
		//ファイル選択
		if(key==KeyEvent.VK_O){
			File nowsl;
			String nowslf;
			nowsl=selected;
			nowslf=start.filename;
			file.setVisible(true);
			if (file.getFile() != null){
				selected=new File(file.getDirectory()+file.getFile());
				if(selected!=nowsl){
					txtview.eof=10000;
					txtview.line=0;
					txtview.temp=0;
					txtview.gyou=0;
				    app.setTitle("てきすとびゅ～わver."+ver+":"+file.getFile());
					view.repaint();
				}
			}
			try {
				pw = new PrintWriter(new BufferedWriter(new FileWriter("sysset.ini")));
				pw.println("cd:"+txtfunc.currentdir.getAbsolutePath());
				pw.println("lastopenenable:"+txtfunc.lastopenenable);
				if(file.getFile() != null){
					pw.println("lastopen:"+selected);
					pw.println("lastfilename:"+file.getFile());
				}
				else{
					pw.println("lastopen:"+nowsl);
					pw.println("lastfilename:"+nowslf);
				}
			} catch (IOException e1) {
			}
			pw.close();
		}
		//1行進める
		if(key==KeyEvent.VK_DOWN||key==KeyEvent.VK_RIGHT){
			if(txtview.eof==10000){
				if(txtview.eof>txtview.line){
					txtview.line+=1;
					txtview.temp-=txtview.charhei;
					txtview.gyou=txtview.temp;
					view.repaint();
				}
			}
			else{
				if(txtview.eof-(txtview.sline/2)>=txtview.line){
					txtview.line+=1;
					txtview.temp-=txtview.charhei;
					txtview.gyou=txtview.temp;
					view.repaint();
				}
			}
		}
		//1行戻す
		if(key==KeyEvent.VK_UP||key==KeyEvent.VK_LEFT){
			if(txtview.temp<0){
				if(txtview.line>0){
					txtview.line-=1;
				}
				txtview.temp+=txtview.charhei;
				txtview.gyou=txtview.temp;
				view.repaint();
			}
		}
		//10行送り
		if(key==KeyEvent.VK_ENTER){
			if(txtview.eof==10000){
				if(txtview.eof>txtview.line){
					count=0;
					while(txtview.eof>=txtview.line&&count<10){
						txtview.line+=1;
						txtview.temp-=txtview.charhei;
						count++;
					}
					txtview.gyou=txtview.temp;
					view.repaint();
				}
			}
			else{
				if(txtview.eof-(txtview.sline/2)>=txtview.line){
					count=0;
					while(txtview.eof>=txtview.line&&count<10){
						txtview.line+=1;
						txtview.temp-=txtview.charhei;
						count++;
					}
					txtview.gyou=txtview.temp;
					view.repaint();
				}
			}
		}
		//10行戻す
		if(key==KeyEvent.VK_BACK_SPACE){
			count=0;
			while(txtview.temp<0&&count<10){
				txtview.line-=1;
				txtview.temp+=txtview.charhei;
				count++;
			}
			txtview.gyou=txtview.temp;
			view.repaint();
		}
		//任意ページ送り
		if(key==KeyEvent.VK_P){
		    page.setVisible(true);
		}
		//フォントサイズ
		if(key==KeyEvent.VK_S){
			func.setVisible(true);
		}
		//フルスクリーン
		if(key==KeyEvent.VK_F){
			app.setExtendedState(JFrame.MAXIMIZED_BOTH);
			txtview.gyou=txtview.temp;
			view.repaint();
		}
		//標準サイズに
		if(key==KeyEvent.VK_N){
			app.setSize(800, 600);
			txtview.gyou=txtview.temp;
			view.repaint();
		}
		//終了
		if(key==KeyEvent.VK_ESCAPE){
			System.exit(0);
		}
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
	}
}