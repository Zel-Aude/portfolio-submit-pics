package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.PathConfig;
import model.RegistrationPictureBusinessLogic;
import model.UserInfoDto;


@MultipartConfig
public class ExecuteSelectPicture extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private enum FileExtensions {

		JPEG(".JPEG"),
		JPG(".JPG"),
		PNG(".PNG"),
		TIFF(".TIFF"),
		BMP(".BMP"),
		GIF(".GIF");

		private final String fileExtension;

		private FileExtensions(String fileExtension) {
			this.fileExtension = fileExtension;
		}

		@Override
		public String toString() {
			return this.fileExtension;
		}
	}

	private static final int  SELECT_MAX  = 3;
	private static final long UP_SIZE_MAX = 3 * 1024 * 1024;

	public ExecuteSelectPicture() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//リクエスト（受信データ）の文字コードを設定
		request.setCharacterEncoding("UTF-8");

		HttpSession session            = request.getSession();
		UserInfoDto userInfoOnSession = (UserInfoDto)session.getAttribute("USER_INFO");

		String forwardDestination = "";

		if (userInfoOnSession != null) {
			//ログイン済
			Collection<Part> parts          = request.getParts();
			String           userId         = userInfoOnSession.getUserId();
			List<String>     upPictureList = new ArrayList<String>();

			if(countWithSizeIsMaxUnderIs(parts)) {

				Path userPath          = PathConfig.PICTURES_DIR_PATH.getPath().resolve(userId);
				Path userAbsolutePath = userPath.toRealPath();

				for(Part part : parts) {

					String fileName      = part.getSubmittedFileName();
					String uploadPicture = userAbsolutePath.resolve(fileName).toString();

					part.write(uploadPicture);
					upPictureList.add(uploadPicture);
				}
			}

			if(upPictureList.isEmpty()) {
				//画像データが保存できなかった場合
				forwardDestination = "/WEB-INF/view/failed_registration_picture.jsp";

			} else {
				//画像データが保存できた場合

				//「user_pictures」テーブルに画像データのpathを登録する
				RegistrationPictureBusinessLogic registrationPictureLogic =
						new RegistrationPictureBusinessLogic();

				registrationPictureLogic.executeRegistrationPicture(userId, upPictureList);

				forwardDestination = "/WEB-INF/view/completed_registration_picture.jsp";
			}

		} else {
			//未ログイン
			forwardDestination = "/WEB-INF/view/user_login.jsp";

		}

		RequestDispatcher dispatch = request.getRequestDispatcher(forwardDestination);
		dispatch.forward(request, response);
	}

	//アップロードされた画像データの、数と容量をチェックするメソッド
	private boolean countWithSizeIsMaxUnderIs(Collection<Part> parts) {

		boolean selectCountResult = false;
		long    sumFileSize        = 0L;

		if(!parts.isEmpty() && parts.size() <= SELECT_MAX) {

			for(Part part : parts) {

				selectCountResult = false;

				String fileName = part.getSubmittedFileName();
				sumFileSize += part.getSize();

				if(!(fileName == null || fileName.isEmpty())) {

					if(isPictureFileExtension(fileName)) {

						if(isPicture(part)) {
							selectCountResult = true;

						} else {
							break;
						}
					} else {
						break;
					}
				} else {
					break;
				}
			}
		}

		boolean sumSizeResult = false;

		if(selectCountResult && sumFileSize <= UP_SIZE_MAX) {
			sumSizeResult = true;
		}

		return sumSizeResult;
	}

	//アップロードされた画像データの拡張子をチェックするメソッド
	private boolean isPictureFileExtension(String fileName) {

		String   nameUppered          = fileName.toUpperCase();
		boolean fileExtensionResult = false;

		for(FileExtensions fe : FileExtensions.values()) {

			if(nameUppered.endsWith(fe.toString())) {
				fileExtensionResult = true;
				break;
			}
		}

		return fileExtensionResult;
	}

	//アップロードされた画像データが本当に画像データかチェックするメソッド
	private boolean isPicture(Part part) {

		boolean pictureResult = false;

		try(InputStream is = part.getInputStream()) {

			Optional<BufferedImage> optBi = Optional.ofNullable(ImageIO.read(is));
			pictureResult = optBi.isPresent();

		} catch(IOException e) {
			e.printStackTrace();
		}

		return pictureResult;
	}
}
