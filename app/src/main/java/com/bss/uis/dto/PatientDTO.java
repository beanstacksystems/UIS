package com.bss.uis.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDTO {
    public String patientid;
    public String bloodgroup;
    public List<String> attendanttypeids;
    public List<PersonDTO> personlist;
    public String registrarid;
    public String referrerid;
    public String sponsorid;
    public String approverid;
    public String isactive;
    public String isapproved;
    public List<String> illnesstypes;
    public String followupcounsellerid;
    public MedicalDetailsDTO medicaldetails;
    public String remarks;
    public String createddate;
    public String updatedate;
}
