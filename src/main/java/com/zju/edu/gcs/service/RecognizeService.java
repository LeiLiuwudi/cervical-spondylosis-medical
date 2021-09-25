package com.zju.edu.gcs.service;

import com.zju.edu.gcs.common.exception.NirException;
import com.zju.edu.gcs.common.exception.NirExceptionEnum;
import com.zju.edu.gcs.common.util.Base64DecodeMultipartFile;
import com.zju.edu.gcs.dto.RecognizeDTO;
import com.zju.edu.gcs.model.Recognize;
import com.zju.edu.gcs.repository.RecognizeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class RecognizeService {
    @Autowired
    private RecognizeRepository recognizeRepository;
    @Value("${file.location}")
    private String BASE_PATH;

    public List<Recognize> getRecognizeList(Integer patientId) {
        if(patientId == null){
            throw new NirException(NirExceptionEnum.PARAMETER_ERROR);
        }else{
            return recognizeRepository.findAllByPatientIdOrderByCreateTime(patientId);
        }
    }

    public void uploadImage(RecognizeDTO recognizeDTO) throws IOException {
        Recognize recognize = new Recognize();
        try {
            MultipartFile file = Base64DecodeMultipartFile.base64Convert(recognizeDTO.getInfraImage());
            String infraredPath = addFile(file);
            BeanUtils.copyProperties(recognizeDTO, recognize);
            recognize.setCreateTime(new Date());
            recognize.setInfraredPath(infraredPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        recognizeRepository.saveAndFlush(recognize);
    }

    public String addFile(MultipartFile file) throws IOException {
        if (file == null) {
            return "";
        }
        String fileName = file.getOriginalFilename();
        String fullPath = BASE_PATH + File.separator + fileName;
        System.out.println(fullPath);
        File newFile = new File(fullPath);
        if(!newFile.getParentFile().exists()){
            newFile.getParentFile().mkdirs();
        }
        file.transferTo(newFile);
        return fileName;
    }


}
