package moji;

import java.io.File;
import java.io.FilenameFilter;

public class txtfilter implements FilenameFilter {
	// �t�B���^�Ώە�����
		private final String filter = ".txt";
	txtfilter(){
	}
public boolean accept(File dir, String name){
	// File�N���X�̃I�u�W�F�N�g����
	File file = new File(name);

	// �f�B���N�g���Ȃ��false��ԋp(���X�g�ɒǉ����Ȃ�)
	if(file.isDirectory()){
		return false;
	}

	// �t�@�C�����̖�����".txt"�Ȃ��true��ԋp(���X�g�ɒǉ�)
	// �����łȂ����false��ԋp(���X�g�ɒǉ����Ȃ�)
	return (name.endsWith(filter));
	}
}
