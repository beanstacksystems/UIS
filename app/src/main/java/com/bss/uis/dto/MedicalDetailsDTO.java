package com.bss.uis.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalDetailsDTO {

    public String medicalrecordid;
    public String patientid;
    public String illnesstypeid;
    public String illnessstageid;
    public String diagnosisdetails;
    public List<ImageDTO> imagelist;
    public String diagnosisdate;
    public String otherremarks;
    public String isactive;
    public String createddate;
    public String updatedate;
}
