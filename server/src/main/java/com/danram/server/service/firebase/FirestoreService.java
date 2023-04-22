package com.danram.server.service.firebase;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface FirestoreService {
    public String uploadFiles(MultipartFile file, String nameFile) throws IOException;
}
