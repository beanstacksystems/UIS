package com.bss.uis.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonDTO {
    public String personid;
    public String entitytype;
    public String prefix;
    public String name;
    public String suffix;
    public String dateofbirth;
    public String gender;
    public List<ImageDTO> imagelist;
    public String relationwithpatient;
    public String incomeperyear;
    public String occupation;
    public String isactive;
    public AddressDTO addressdto;
    public IdproofDTO idproofdto;
    public List<ContactTypeDTO> contactlist;
    public String createddate;
    public String updatedate;
}
