package org.fujitsu.training.codes.service;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

	private static final String BASE_UPLOAD_DIR = "C:/auction_uploads/";

	public String saveFile(MultipartFile file, String subFolder, String prefix) throws IOException {
		if (file == null || file.isEmpty())
			return null;

		String uploadDirPath = BASE_UPLOAD_DIR + subFolder + "/";
		File dir = new File(uploadDirPath);
		if (!dir.exists())
			dir.mkdirs();

		String originalName = file.getOriginalFilename();
		String safeName = (originalName == null ? "file.jpg" : originalName).replaceAll("[^a-zA-Z0-9._-]", "_");
		String fileName = prefix + "_" + System.currentTimeMillis() + "_" + safeName;

		File destination = new File(dir, fileName);
		file.transferTo(destination);

		return "/app/" + subFolder + "/" + fileName;
	}
}