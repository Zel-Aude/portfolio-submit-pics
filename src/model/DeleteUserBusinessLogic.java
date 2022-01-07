package model;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DeleteUserBusinessLogic {

	public DeleteUserBusinessLogic() {}

	//選択したユーザーとユーザーの登録画像パスを、DAOに削除依頼するメソッド
	public boolean executeDeleteUser(List<String> targetList) {

		boolean succesDelete = false;

		RegistrationAndDeletionDao dao = new RegistrationAndDeletionDao(
				TestDBConnection.generateConnection());

		succesDelete = dao.deleteUser(targetList);

		return succesDelete;
	}

	//全てのユーザーとユーザーの登録画像パスを、DAOに削除依頼するメソッド
	public boolean executeClearUser() {

		boolean succesClear = false;

		RegistrationAndDeletionDao dao = new RegistrationAndDeletionDao(
				TestDBConnection.generateConnection());

		succesClear = dao.truncateUser();

		return succesClear;
	}

	//選択したユーザーディレクトリを削除するメソッド
	public void deleteUserDirectory(List<String> targetList) {

		for(String tgt : targetList) {
			Path targetDir = PathConfig.PICTURES_DIR_PATH.getPath().resolve(tgt);

			if(Files.exists(targetDir)) {

			    try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(targetDir)) {

			    	for(Path file : dirStream) {
			    		Files.deleteIfExists(file);
			    	}

			    	Files.deleteIfExists(targetDir);

			    } catch (IOException e) {
			    	e.printStackTrace();
			    }
			}
		}
	}

	//全てのユーザーディレクトリを削除するメソッド
	public void clearUserDirectory(Path dirPath) {

	    try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(dirPath)) {

	    	Path protectDir            = PathConfig.PICTURES_DIR_PATH.getPath();
	    	int  protectFileHierarchy = protectDir.getNameCount() + 1;

	    	for(Path file : dirStream) {

	    		if(Files.isDirectory(file)) {
	    			clearUserDirectory(file);

	    		} else {

	    			int clearFileHierarchy = file.getNameCount();

	    			if(clearFileHierarchy != protectFileHierarchy) {
	    				Files.deleteIfExists(file);
	    			}
	    		}
	    	}

	    	if(!dirPath.equals(protectDir)) {
	    		Files.deleteIfExists(dirPath);
	    	}

	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
}