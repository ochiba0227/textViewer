package moji;

import java.io.File;
import java.io.FilenameFilter;

public class txtfilter implements FilenameFilter {
	// フィルタ対象文字列
		private final String filter = ".txt";
	txtfilter(){
	}
public boolean accept(File dir, String name){
	// Fileクラスのオブジェクト生成
	File file = new File(name);

	// ディレクトリならばfalseを返却(リストに追加しない)
	if(file.isDirectory()){
		return false;
	}

	// ファイル名の末尾が".txt"ならばtrueを返却(リストに追加)
	// そうでなければfalseを返却(リストに追加しない)
	return (name.endsWith(filter));
	}
}
