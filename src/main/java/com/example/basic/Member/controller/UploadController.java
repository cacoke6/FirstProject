package com.example.basic.Member.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.List;

@Controller
public class UploadController {
    @GetMapping("/upload")
    public String upload6() {
        return "upload";
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload6Post(
            MultipartHttpServletRequest mRequest) {
        String result = "";
        Iterator<String> fileNames = mRequest.getFileNames();
        while (fileNames.hasNext()) {
            String fileName = fileNames.next();
            List<MultipartFile> mFiles = mRequest.getFiles(fileName);
            for (MultipartFile mFile : mFiles) {
                String oName = mFile.getOriginalFilename();
                long size = mFile.getSize();
                result += oName + " : " + size + "<br>";
            }
        }
        return result;
    }

}
