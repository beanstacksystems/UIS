package com.bss.uis.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDTO {

    public String bloodgroup;
    public int attendantrelationtypeid;
    public List<PersonDTO> personDTOList;
    public int attendantid;
    public int registrarid;
    public int referrerid;
    public int sponsorid;
    public int approverid;
    public MedicalDetailsDTO medicalRecord;
    public AddressDTO address;
    public String remarks;
}
