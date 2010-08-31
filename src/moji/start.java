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
	static File cd,selected;//�J�����g�f�B���N�g��,�Ō�ɑI�����ꂽ�t�@�C��
	static String filename;//�Ō�ɑI�����ꂽ�t�@�C���̖��O
	static PrintWriter pw = null;//sysset.ini�������ݗp
	static boolean flag=false;//�O��J�����t�@�C����ۑ����邩
	start(){
		String str;
		BufferedReader br=null;
		try {//sysset.ini�̑��݊m�F
			br = new BufferedReader(new FileReader("sysset.ini"));
			try {//sysset.ini�����[�h
				while((str=br.readLine())!=null){
					loadsys(str);
				}
			} catch (IOException e) {
			}
		} catch (FileNotFoundException e) {
			cd = new File(".").getAbsoluteFile().getParentFile();//�J�����g�f�B���N�g�����擾
			try {//������sysset.ini���쐬
				pw = new PrintWriter(new BufferedWriter(new FileWriter("sysset.ini")));
				pw.println("cd:"+cd.getAbsolutePath());
				pw.println("lastopenenable:false");
				pw.println("lastopen:");
				pw.println("lastfilename:");
			} catch (IOException e1) {
			}
			pw.close();//sysset.ini�쐬�����
		}
		if(br!=null){
			try {
				br.close();//sysset.ini���[�h�����
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