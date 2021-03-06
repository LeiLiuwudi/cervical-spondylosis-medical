package com.zju.edu.gcs.service;

import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.zju.edu.gcs.common.enums.AnalysisResultEnum;
import com.zju.edu.gcs.common.exception.NirException;
import com.zju.edu.gcs.common.exception.NirExceptionEnum;
import com.zju.edu.gcs.common.util.Base64DecodeMultipartFile;
import com.zju.edu.gcs.common.util.ChartUtil;
import com.zju.edu.gcs.dto.EvaluationResultDTO;
import com.zju.edu.gcs.dto.MedicalRecordDTO;
import com.zju.edu.gcs.dto.RecognizeDTO;
import com.zju.edu.gcs.model.Analysis;
import com.zju.edu.gcs.model.Patient;
import com.zju.edu.gcs.model.Recognize;
import com.zju.edu.gcs.repository.AnalysisRepository;
import com.zju.edu.gcs.repository.PatientRepository;
import com.zju.edu.gcs.repository.RecognizeRepository;
import org.jfree.chart.ChartUtils;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service
public class RecognizeService {
    @Autowired
    private RecognizeRepository recognizeRepository;
    @Autowired
    private AnalysisRepository analysisRepository;
    @Autowired
    private PatientRepository patientRepository;

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

    public MedicalRecordDTO generateMedicalRecord(Integer id) {
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        Analysis analysis = analysisRepository.findAnalysisById(id);
        EvaluationResultDTO evaluationResultDTO = JSONObject.parseObject(analysis.getEvaluationResult(), EvaluationResultDTO.class);
        medicalRecordDTO.setEvaluationResultDTO(evaluationResultDTO);
        medicalRecordDTO.setCreatedAt(analysis.getCreateTime());
        medicalRecordDTO.setId(id);
        Patient patient = patientRepository.findById(analysis.getPatientId()).get();
        medicalRecordDTO.setPatientName(patient.getName());
        medicalRecordDTO.setDoctorName(patient.getDoctorName());
        medicalRecordDTO.setGender(patient.getGender());
        medicalRecordDTO.setBirthday(patient.getBirthday());
        medicalRecordDTO.setProfession(patient.getProfession());
        medicalRecordDTO.setChiefComplaint(patient.getChiefComplaint());
        medicalRecordDTO.setPresentHistory(patient.getPresentHistory());
        medicalRecordDTO.setPastHistory(patient.getPastHistory());
        medicalRecordDTO.setInitialDiagnosis(patient.getInitialDiagnosis());
        medicalRecordDTO.setDiagnoseBasis(patient.getDiagnoseBasis());
        medicalRecordDTO.setEvaluationDesc(AnalysisResultEnum.getDescByTrend(evaluationResultDTO.getTrend()).getDesc());
        return medicalRecordDTO;
    }

    public void exportPDF(HttpServletResponse response, MedicalRecordDTO medicalRecordDTO) throws DocumentException, IOException {
        // ???????????????????????????????????????????????????
        response.setHeader("content-Type", "application/pdf");
        // ???????????????????????????
        response.setHeader("Content-Disposition", "attachment;filename=user.pdf");
        BaseFont bfChinese = BaseFont.createFont("STSong-Light",
                "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font FontTitle = new Font(bfChinese, 12, Font.BOLD);
        Font FontCell = new Font(bfChinese, 10, Font.NORMAL);
        Font FontCell1 = new Font(bfChinese, 10, Font.BOLD);
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        //?????????????????????????????????
        Paragraph paragraph = new Paragraph("????????????",FontTitle);
        paragraph.setAlignment(1);
        document.add(paragraph);
        //????????????
        document.add(new Paragraph("\n"));
        //??????????????????
        PdfPTable table = new PdfPTable(6);
        //?????????
        table.addCell(getCell("??????ID",FontCell1));
        table.addCell(getCell(String.valueOf(medicalRecordDTO.getId()),FontCell));
        table.addCell(getCell("??????",FontCell1));
        table.addCell(getCell(medicalRecordDTO.getPatientName(),FontCell));
        table.addCell(getCell("??????",FontCell1));
        table.addCell(getCell(medicalRecordDTO.getGender()==0?"???":"???",FontCell));
        //?????????
        table.addCell(getCell("??????",FontCell1));
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        cal.setTime(medicalRecordDTO.getBirthday());
        int selectYear = cal.get(Calendar.YEAR);
        int age = yearNow-selectYear;
        table.addCell(getCell(String.valueOf(age),FontCell));
        table.addCell(getCell("????????????",FontCell1));
        table.addCell(getCell(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(medicalRecordDTO.getCreatedAt()),FontCell));
        table.addCell(getCell("????????????",FontCell1));
        table.addCell(getCell(medicalRecordDTO.getDoctorName(),FontCell));
        //?????????
        table.addCell(getCell("????????????",FontCell1));
        String disease = medicalRecordDTO.getInitialDiagnosis();
        table.addCell(getCell(disease,FontCell));
        table.addCell(getCell("????????????",FontCell1));
        table.addCell(getCell(medicalRecordDTO.getChiefComplaint(),FontCell));
        table.addCell(getCell("?????????",FontCell1));
        table.addCell(getCell(medicalRecordDTO.getPastHistory(), FontCell));
        //?????????
        table.addCell(getCell("?????????",FontCell1));
        table.addCell(getCell(medicalRecordDTO.getPresentHistory(),FontCell, 5));
        document.add(table);
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("????????????????????????", FontCell1));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Double> evaluationResult = medicalRecordDTO.getEvaluationResultDTO().getResult();
        for (int i = 0; i < evaluationResult.size(); i++) {
            dataset.addValue(evaluationResult.get(i), "", "???" + (i+1) + "?????????");
        }
        ChartUtils.writeChartAsJPEG(bos, ChartUtil.lineChart("??????????????????????????????????????????????????????", "", "", dataset), 850, 440);
        Image image = Image.getInstance(bos.toByteArray());
        image.scalePercent(60);
        document.add(image);
        document.add(new Paragraph("????????????????????????", FontCell1));
        document.add(new Paragraph(medicalRecordDTO.getEvaluationDesc(), FontCell));
        document.close();

    }

    private PdfPCell getCell(String element, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(element,font));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    private PdfPCell getCell(String element, Font font, int width) {
        PdfPCell cell = new PdfPCell(new Paragraph(element,font));
        cell.setColspan(width);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }


}
