package com.zju.edu.gcs.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RecognizeDTO {
    private Integer patientId;
    private String patientName;
    private String infraImage;
}
