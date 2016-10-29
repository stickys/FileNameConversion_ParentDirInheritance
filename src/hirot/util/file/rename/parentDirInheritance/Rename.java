package hirot.util.file.rename.parentDirInheritance;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Rename {

	public static void main(String[] args) {
		File _f = null;
		Rename _rename = new Rename();
		List<File> _files = null;
		if(args.length > 0 && (_f = new File(args[0])).getAbsoluteFile().isDirectory()){
			_files = _rename.getTargetFiles(_f);
			for(File _file:_files){
//				System.out.println(_file.getAbsolutePath() + " : " + _file.getName());
				_rename.rename(_file);
			}
		}else{
			System.out.println("You have to give a directory name as a first argument.");
			printUsage();
			System.exit(1);
		}
	}
	
	protected static void printUsage(){
		System.out.println("USAGE : java hirot.util.file.rename.parentDirInheritance.Rename <parentDirectory>");
	}
	
	protected List<File> getTargetFiles(File parentDirectory) {
		List<File> _resultFiles = new ArrayList<File>();
		File[] _children = parentDirectory.listFiles();
		for(File _child : _children){
			if(_child.isDirectory()){
				_resultFiles.addAll(getTargetFiles(_child));
			}else{
				if(_child.isFile() && _child.getName().toLowerCase().endsWith(".jpg")){
					_resultFiles.add(_child);
				}
			}
		}
		
		return _resultFiles;
	}
	
	protected void rename(File file){
		File _parentDir = file.getParentFile();
		String _originalAbsolutePath = file.getAbsolutePath();
		String _renamedAbsolutePath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(file.getName())) + _parentDir.getName() + "_" + file.getName();
		try{
			file.renameTo(new File(_renamedAbsolutePath));
			System.out.println(_originalAbsolutePath + " was successfully renamed to " + _renamedAbsolutePath);
		}catch(RuntimeException e){
			e.printStackTrace();
			System.out.println(_originalAbsolutePath + " was not renamed because of " + e.getCause());
		}
	}
}
