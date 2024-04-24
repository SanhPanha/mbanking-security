package co.istad.mobilebankingcstad.features.files;

import co.istad.mobilebankingcstad.features.files.dto.FileResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    FileResponse uploadSingleFile(MultipartFile file, HttpServletRequest request);
    List<String> uploadMultipleFiles(MultipartFile[] files);


    ResponseEntity<Resource> serveFile(String filename, HttpServletRequest request);
    void deleteFile(String filename);
}
