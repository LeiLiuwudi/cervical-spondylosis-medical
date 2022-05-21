package com.zju.edu.gcs.service;

import com.zju.edu.gcs.common.exception.NirException;
import com.zju.edu.gcs.common.exception.NirExceptionEnum;
import com.zju.edu.gcs.common.util.TextComparison;
import com.zju.edu.gcs.dto.PatientDTO;
import com.zju.edu.gcs.dto.QueryPatientDTO;
import com.zju.edu.gcs.dto.TextComparisonDTO;
import com.zju.edu.gcs.model.Patient;
import com.zju.edu.gcs.repository.PatientRepository;
import com.zju.edu.gcs.vo.ComparisonVO;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public void addPatient(PatientDTO patientDTO){
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDTO, patient);
        patient.setCreateTime(new Date());
        patientRepository.saveAndFlush(patient);
    }

    public List<Patient> queryPatient(QueryPatientDTO queryPatientDTO) {
        String name = queryPatientDTO.getName();
        Integer doctorId = queryPatientDTO.getDoctorId();
        String initialDiagnosis = queryPatientDTO.getInitialDiagnosis();
        return patientRepository.findByQuery(name, doctorId, initialDiagnosis);
    }

    public void updatePatient(PatientDTO patientDTO) {
        if(patientDTO.getId() == null){
            throw new NirException(NirExceptionEnum.PARAMETER_ERROR);
        }else{
            Patient patient = new Patient();
            BeanUtils.copyProperties(patientDTO, patient);
            patient.setCreateTime(new Date());
            patientRepository.saveAndFlush(patient);
        }
    }

    public void deletePatient(Integer id) {
        if(id == null){
            throw new NirException(NirExceptionEnum.PARAMETER_ERROR);
        }else{
            patientRepository.deleteById(id);
        }
    }

    public List<Patient> textComparison(TextComparisonDTO textComparisonDTO) {
        Patient basePatient = patientRepository.findById(textComparisonDTO.getId()).get();
        Patient comparePatient = patientRepository.findById(textComparisonDTO.getSimilarId()).get();
        Instant start = Instant.now();
//        主诉字段颜色渲染
        TextComparison textComparison = new TextComparison(basePatient.getChiefComplaint(), comparePatient.getChiefComplaint());
        textComparison.runAnalysis();
        textComparison.traceback();
        ComparisonVO comparisonVO = getColorString(2, textComparison.getString1(), textComparison.getString2());
        basePatient.setChiefComplaint(comparisonVO.getBase());
        comparePatient.setChiefComplaint(comparisonVO.getCompare());
//        现病史字段颜色渲染
        textComparison = new TextComparison(basePatient.getPresentHistory(), comparePatient.getPresentHistory());
        textComparison.runAnalysis();
        textComparison.traceback();
        comparisonVO = getColorString(2, textComparison.getString1(), textComparison.getString2());
        basePatient.setPresentHistory(comparisonVO.getBase());
        comparePatient.setPresentHistory(comparisonVO.getCompare());
//        既往史字段颜色渲染
        textComparison = new TextComparison(basePatient.getPastHistory(), comparePatient.getPastHistory());
        textComparison.runAnalysis();
        textComparison.traceback();
        comparisonVO = getColorString(2, textComparison.getString1(), textComparison.getString2());
        basePatient.setPastHistory(comparisonVO.getBase());
        comparePatient.setPastHistory(comparisonVO.getCompare());
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println(timeElapsed);
        return Arrays.asList(basePatient,comparePatient);
    }

    public ComparisonVO getColorString(Integer a, String str1, String str2){
        char[] s = str1.toCharArray();
        char[] p = str2.toCharArray();
        int length = str1.length();
        String base = "";
        String compare = "";
        if (a ==2){
                    for(int i=0;i<length;i++){
            if(s[i]=='-'){
                compare += p[i];
            }else if(p[i]=='-'){
                base += s[i];
            }else if(s[i] == p[i]){
                base += "<span style='color:red'>" + s[i] + "</span>";
                compare += "<span style='color:red'>" + p[i] + "</span>";

            }else if(s[i] != p[i]){
                base += s[i];
                compare += p[i];
            }
        }
        }
        else if( a==0){
            base += "外伤";
            base += "<span style='color:red'>致四肢</span>";
            base += "麻木";
            base += "<span style='color:red'>乏力</span>";
            base += 1;
            base += "<span style='color:red'>小时</span>";

            compare += "车祸";
            compare += "<span style='color:red'>致四肢</span>";
            compare += "<span style='color:red'>乏力</span>";
            compare += "，颈椎疼痛2";
            compare += "<span style='color:red'>小时</span>";
            compare += "余";
        }
        else {
            base += "<span style='color:red'>患者</span>";
            base += 1;
            base += "<span style='color:red'>小时前</span>";
            base += "不慎摔倒，后背及后脑着地,";
            base += "<span style='color:red'>诉四肢</span>";
            base += "感觉麻木";
            base += "<span style='color:red'>乏力</span>";
            base += "，病程中有";
            base += "<span style='color:red'>昏迷史</span>";
            base += "，无头晕头痛，";
            base += "<span style='color:red'>无恶心呕吐, 无气急胸闷，无腹痛</span>";
            base += "腹胀，";
            base += "<span style='color:red'>无大小便失禁，受伤</span>";
            base += "过程能够";
            base += "<span style='color:red'>回忆</span>";
            base += "，受伤后即来";
            base += "<span style='color:red'>我院急诊</span>";
            base += "就诊，查";
            base += "<span style='color:red'>颈</span>";
            base += "椎以及胸";
            base += "<span style='color:red'>椎核磁共振：1、C</span>";
            base += "37及T89及T34";
            base += "<span style='color:red'>椎间盘后突出</span>";
            base += ";";
            base += "<span style='color:red'>2、部分椎间盘变性</span>";
            base += ";3、颈胸椎退行性改变；4、平3、4颈髓少许异常信号，请结合临床。骨科会诊后考虑颈部脊髓损伤，";
            base += "<span style='color:red'>收住入院进一步诊治，</span>";
            base += "<span style='color:red'>患者</span>";
            base += "近期无咳嗽、发热，";
            base += "<span style='color:red'>神志清</span>";
            base += "，纳可，";
            base += "<span style='color:red'>大小便</span>";
            base += "正常";
            compare += "<span style='color:red'>患者</span>";
            compare += 2;
            compare += "<span style='color:red'>小时</span>";
            compare += "余";
            compare += "<span style='color:red'>前</span>";
            compare += "因车祸外伤致伤颈椎和头面部，额部皮肤挫裂伤口疼痛、流血，";
            compare += "<span style='color:red'>诉四肢乏力</span>";
            compare += "，颈椎疼痛。伤后有原发性";
            compare += "<span style='color:red'>昏迷史</span>";
            compare += "，";
            compare += "<span style='color:red'>无恶心呕吐，无胸闷气急，无</span>";
            compare += "胸";
            compare += "<span style='color:red'>腹</span>";
            compare += "部疼";
            compare += "<span style='color:red'>痛</span>";
            compare += "，无肢体麻木、偏瘫，";
            compare +=  "<span style='color:red'>无大小便失禁</span>";
            compare += "能";
            compare +=  "<span style='color:red'>回忆受伤</span>";
            compare += "经过。至";
            compare += "<span style='color:red'>我院急诊</span>";
            compare += "，检查头颅CT：颅内未见外伤改变，";
            compare += "<span style='color:red'>颈椎核磁共振：1、C</span>";
            compare += "56";
            compare += "<span style='color:red'>椎间盘后突出</span>";
            compare += "伴颈髓损伤。";
            compare += "<span style='color:red'>2、部分椎间盘变性</span>";
            compare += "。予急诊清创缝合，为求";
            compare += "<span style='color:red'>进一步诊治，收住入院。患者</span>";
            compare += "伤来";
            compare += "<span style='color:red'>神志清</span>";
            compare += "，未进食，";
            compare += "<span style='color:red'>大小便</span>";
            compare += "未解";
        }



        ComparisonVO comparisonVO = new ComparisonVO();
        comparisonVO.setBase(base);
        comparisonVO.setCompare(compare);
        return comparisonVO;
    }
}
