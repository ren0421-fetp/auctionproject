package org.fujitsu.training.codes.service;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private static final String BASE_UPLOAD_DIR = "C:/auction_uploads/";

    public String saveFile(MultipartFile file, String subFolder, String prefix) throws IOException {
        if (file == null || file.isEmpty()) return null;

        // 1. Setup Directory
        String uploadDirPath = BASE_UPLOAD_DIR + subFolder + "/";
        File dir = new File(uploadDirPath);
        if (!dir.exists()) dir.mkdirs();

        // 2. Prepare Filename
        String originalName = file.getOriginalFilename();
        String safeName = (originalName == null ? "file.jpg" : originalName).replaceAll("[^a-zA-Z0-9._-]", "_");
        String fileName = prefix + "_" + System.currentTimeMillis() + "_" + safeName;
        
        // 3. Physical Save
        File destination = new File(dir, fileName);
        file.transferTo(destination);

        // 4. Return the /app/ relative path for your context mapping
        return "/app/" + subFolder + "/" + fileName;
    }
}