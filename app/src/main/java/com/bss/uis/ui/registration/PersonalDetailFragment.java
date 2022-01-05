package com.bss.uis.ui.registration;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;

import com.beanstack.utility.listener.TextInputLayoutFocusChangeListener;
import com.beanstack.utility.validators.AutoCompleteTextValidtor;
import com.beanstack.utility.validators.CustomTextValidator;
import com.bss.uis.R;
import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.database.entity.Patient;
import com.bss.uis.database.relation.PatientDetailData;
import com.bss.uis.ui.UIUtil;
import com.bss.uis.ui.image.ImageCaptureFragment;
import com.bss.uis.ui.image.ProfileImageFragment;
import com.bss.uis.util.AppUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;


public class PersonalDetailFragment extends BaseFragment{

    TextInputEditText name,email,contact,dob,panadhar,income,gender;
    TextInputLayout nameInputLayout,eMailInputLayout,contactInputLayout,incomeInputLayout,
            dobInputLayout,genderLayout,salutationLayout,occupationLayout,panadhartxtLayout;
    AutoCompleteTextView salutation,occupation;
    DatePickerDialog picker;
    String fragmentTitle;
    CircleImageView profile_image;
    ImageCaptureFragment imageCaptureFragmentIdProof;
    ProfileImageFragment profileImageFragment;
    public PersonalDetailFragment() {
        // Required empty public constructor
    }

    public static PersonalDetailFragment newInstance(String fragmentTitle,String progressState) {
        PersonalDetailFragment fragment = new PersonalDetailFragment();
        fragment.fragmentTitle = fragmentTitle;
        fragment.setProgressState(progressState);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_personal_detail, container, false);
        initView(fragmentView);
        return fragmentView;
    }


    @Override
    public String getFragmentTitle() {
        return fragmentTitle;
    }

    private void initView(View fragmentView) {
        nameInputLayout = fragmentView.findViewById(R.id.personNameLayout);
        eMailInputLayout = fragmentView.findViewById(R.id.Email_etLayout);
        contactInputLayout = fragmentView.findViewById(R.id.contactInputLayout);
        incomeInputLayout = fragmentView.findViewById(R.id.incomeInputLayout);
        name = fragmentView.findViewById(R.id.personName);
        name.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (nameInputLayout,"Name cannot be empty"));
        name.addTextChangedListener(new CustomTextValidator(name) {
            @Override
            public void validate(TextView textView, String text) {
                nameInputLayout.setError(null);
                if(null == text || text.isEmpty())
                    nameInputLayout.setError("Name cannot be empty");
                else if(!UIUtil.isContainsValidCharacter(text))
                    nameInputLayout.setError("Only alphanumeric characters allowed");
            }
        });
        email = fragmentView.findViewById(R.id.Email_et);
        email.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (eMailInputLayout,"Email cannot be empty"));
        email.addTextChangedListener(new CustomTextValidator(email) {
            @Override
            public void validate(TextView textView, String text) {
                eMailInputLayout.setError(null);
                if(null == text || text.isEmpty())
                    eMailInputLayout.setError("Email cannot be empty");
                else if(!UIUtil.isEmailValid(text))
                    eMailInputLayout.setError("Invalid E-Mail Id");
            }
        });
        contact = fragmentView.findViewById(R.id.contact_et);
        contact.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (contactInputLayout,"Mobile no cannot be empty"));
        contact.addTextChangedListener(new CustomTextValidator(contact) {
            @Override
            public void validate(TextView textView, String text) {
                contactInputLayout.setError(null);
                if(null == text || text.isEmpty())
                    contactInputLayout.setError("Mobile no cannot be empty");
                else if(text.length()< 10)
                    contactInputLayout.setError("Mobile number is not Correct");
            }
        });
        income = fragmentView.findViewById(R.id.income_et);
        income.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (incomeInputLayout,"Income cannot be empty"));
        income.addTextChangedListener(new CustomTextValidator(income) {
            @Override
            public void validate(TextView textView, String text) {
                incomeInputLayout.setError(null);
                if(null == text || text.isEmpty())
                    incomeInputLayout.setError("Income cannot be empty");
            }
        });
        panadhar = fragmentView.findViewById(R.id.panAdhar);
        panadhartxtLayout = fragmentView.findViewById(R.id.idprooftxtLayout);
        panadhar.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (panadhartxtLayout,"Pan/Aadhar no cannot be empty"));
        panadhar.addTextChangedListener(new CustomTextValidator(panadhar) {
            @Override
            public void validate(TextView textView, String text) {
                panadhartxtLayout.setError(null);
                if(null == text || text.isEmpty())
                    panadhartxtLayout.setError("Pan/Aadhar no cannot be empty");
                else if(text.length()< 10)
                    panadhartxtLayout.setError("Pan/Aadhar is not Correct");
            }
        });
        profile_image = (CircleImageView)fragmentView.findViewById(R.id.profile_image);
        profileImageFragment = getProfileImageFragment(false,R.id.profile_image_edit_layout);
        initDOB(fragmentView);
        initGenderView(fragmentView);
        initSalutationView(fragmentView);
        initOccupationView(fragmentView);
        imageCaptureFragmentIdProof = getFragment(false,R.id.id_proof_imageview_Layout);
    }
    private void initDOB(View fragmentView) {
        dob = fragmentView.findViewById(R.id.dateOfBirth);
        dobInputLayout = fragmentView.findViewById(R.id.dateOfBirthLayout);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        dob.addTextChangedListener(new CustomTextValidator(dob) {
            @Override
            public void validate(TextView textView, String text) {
                dobInputLayout.setError(null);
                if(null == text || text.isEmpty())
                    dobInputLayout.setError("Date Of Birth cannot be empty");
            }
        });
        dob.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (dobInputLayout,"Date Of Birth cannot be empty"));
    }

    private void initGenderView(View fragmentView)
    {
        genderLayout = fragmentView.findViewById(R.id.spinner_gender_layout);
        gender = fragmentView.findViewById(R.id.text_gender);
        ArrayList<String> genderValue = AppUtil.getMasterByType("gender");
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = UIUtil.getSelectPopupDialog(getActivity(),
                        "Gender",genderValue.toArray(new String[genderValue.size()]),gender);
                dialog.show();
                }
        });
    }
    private void initSalutationView(View fragmentView)
    {
        salutationLayout = fragmentView.findViewById(R.id.spinner_salutation_layout);
        salutation = fragmentView.findViewById(R.id.spinner_salutation);
        ArrayList<String> salutationValue = AppUtil.getMasterByType("salutation");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, salutationValue);
        salutation.setAdapter(adapter);
        salutation.setValidator(new AutoCompleteTextValidtor(salutationLayout,salutationValue));
    }
    private void initOccupationView(View fragmentView)
    {
        occupationLayout = fragmentView.findViewById(R.id.spinner_occupation_layout);
        occupation = fragmentView.findViewById(R.id.spinner_occupation);
        ArrayList<String> occupationValue = AppUtil.getMasterByType("occupationtype");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, occupationValue);
        occupation.setAdapter(adapter);
        occupation.setValidator(new AutoCompleteTextValidtor(occupationLayout,occupationValue));
    }
    @Override
    public boolean isValidDetails() {
        String profileImagePath = profileImageFragment.getSelectedImagePath();
        if(imageCaptureFragmentIdProof.getImageCount() != imageCaptureFragmentIdProof.getSelectedImage().size() ||
                     null ==  profileImagePath || profileImagePath.isEmpty()) {
            Toast.makeText(UISApplicationContext.getInstance().getContext(),
                    getResources().getString(R.string.fillImage), Toast.LENGTH_LONG).show();
            return false;
        }
        String nameTxt = name.getText().toString();
        String emailTxt = email.getText().toString();
        String contactTxt = contact.getText().toString();
        String incomeTxt = income.getText().toString();
        String dobTxt = dob.getText().toString();
        String panadharTxt = panadhar.getText().toString();
        String genderTxt = gender.getText().toString();
        String salTxt = salutation.getText().toString();
        String occupationTxt = occupation.getText().toString();
        if(null == nameTxt ||nameTxt.isEmpty()|| null == emailTxt ||emailTxt.isEmpty()
                || null == contactTxt || contactTxt.isEmpty()
                || null == incomeTxt || incomeTxt.isEmpty()
                 || null == dobTxt || dobTxt.isEmpty()
                   ||   null == panadharTxt || panadharTxt.isEmpty()
                 || null == genderTxt || genderTxt.isEmpty()
                || null == salTxt || salTxt.isEmpty()
                || null == occupationTxt || occupationTxt.isEmpty()){
            Toast.makeText(UISApplicationContext.getInstance().getContext(),
                    getResources().getString(R.string.fillvalue),Toast.LENGTH_LONG).show();
            return false;
        }

        if(null != nameInputLayout.getError()||null != eMailInputLayout.getError()
                ||null != contactInputLayout.getError()
                ||null != dobInputLayout.getError()||null != genderLayout.getError()
                ||null != salutationLayout.getError() ||null != occupationLayout.getError())
            return false;
        return true;
    }

    @Override
    public void updateDetails(PatientDetailData patientDetailData) {
         Patient patient = patientDetailData.getPatient();
        patient.setName(name.getText().toString());
        patient.setEmailId(email.getText().toString());
        patient.setContact(contact.getText().toString());
        patient.setDob(dob.getText().toString());
        patient.setGender(gender.getText().toString());
        patient.setIdproof(panadhar.getText().toString());
        patientDetailData.getImagesList().add(AppUtil.getPatientImage("profileImage",
                profileImageFragment.getSelectedImagePath())) ;
        patientDetailData.getImagesList().addAll(AppUtil.getPatientImageList("idproof",
                imageCaptureFragmentIdProof.getSelectedImage())) ;
    }
    public ImageCaptureFragment getFragment(Boolean bool, int id) {
        ImageCaptureFragment fragment = ImageCaptureFragment.newInstance("idproof",1);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        if (bool)
            transaction.addToBackStack(null);
        transaction.commit();
        return fragment;
    }
    public ProfileImageFragment getProfileImageFragment(Boolean bool, int id) {
        ProfileImageFragment fragment = ProfileImageFragment.newInstance(profile_image);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        if (bool)
            transaction.addToBackStack(null);
        transaction.commit();
        return fragment;
    }

}