package moji;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class start {
	static File cd,selected;//カレントディレクトリ,最後に選択されたファイル
	static String filename;//最後に選択されたファイルの名前
	static PrintWriter pw = null;//sysset.ini書き込み用
	static boolean flag=false;//前回開いたファイルを保存するか
	start(){
		String str;
		BufferedReader br=null;
		try {//sysset.iniの存在確認
			br = new BufferedReader(new FileReader("sysset.ini"));
			try {//sysset.iniをロード
				while((str=br.readLine())!=null){
					loadsys(str);
				}
			} catch (IOException e) {
			}
		} catch (FileNotFoundException e) {
			cd = new File(".").getAbsoluteFile().getParentFile();//カレントディレクトリを取得
			try {//無い時sysset.iniを作成
				pw = new PrintWriter(new BufferedWriter(new FileWriter("sysset.ini")));
				pw.println("cd:"+cd.getAbsolutePath());
				pw.println("lastopenenable:false");
				pw.println("lastopen:");
				pw.println("lastfilename:");
			} catch (IOException e1) {
			}
			pw.close();//sysset.ini作成後閉じる
		}
		if(br!=null){
			try {
				br.close();//sysset.iniロード後閉じる
			} catch (IOException e) {
			}
		}
	}
	public void loadsys(String str){
		int end;
		Matcher m = Pattern.compile("^cd:").matcher(str);
		if(m.find()){
			end=m.end();
			cd = new File(str.substring(end));
		}
		m = Pattern.compile("^lastopenenable:").matcher(str);
		if(m.find()){
			end=m.end();
			flag=Boolean.valueOf(str.substring(end));
		}
		if(flag){
			m = Pattern.compile("^lastopen:").matcher(str);
			if(m.find()){
				end=m.end();
				selected = new File(str.substring(end));
			}
			m = Pattern.compile("^lastfilename:").matcher(str);
			if(m.find()){
				end=m.end();
				filename = str.substring(end);
			}
		}
	}
}