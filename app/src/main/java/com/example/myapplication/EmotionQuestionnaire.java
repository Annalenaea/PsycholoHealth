package com.example.myapplication;import android.os.Bundle;import android.renderscript.Sampler;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.AdapterView;import android.widget.ArrayAdapter;import android.widget.Button;import android.widget.EditText;import android.widget.RadioButton;import android.widget.RadioGroup;import android.widget.SeekBar;import android.widget.Spinner;import android.widget.TextView;import android.widget.Toast;import androidx.annotation.NonNull;import androidx.fragment.app.Fragment;import androidx.navigation.NavController;import androidx.navigation.fragment.NavHostFragment;import com.example.myapplication.databinding.EmotionQuestionnaireBinding;import java.io.IOException;import java.util.Objects;public class EmotionQuestionnaire extends Fragment implements AdapterView.OnItemSelectedListener {    private EmotionQuestionnaireBinding binding;    private Button Finish2;    public TextView StressLevel;    public static Integer m_stressLevel = 0;    public Integer m_checkedPeriodRadioButtonID;    private SeekBar SBStress;    private EditText SleepDuration;    public static String  m_sleepDuration, m_alcohol, m_cigarettes;    public static String  m_feeling, m_feeling_phys, m_sleepQuality, m_medication;    public RadioGroup RGPeriod;    public Spinner spinner_feeling, spinner_sleep_quality, spinner_feeling_phys, spinner_smoking;    public Spinner spinner_medication, spinner_alcohol;    private NavController homeNavi2;    public static EmotionQuestionnaire EmotionQuestionnaire = new EmotionQuestionnaire();    @Override    public View onCreateView(            LayoutInflater inflater, ViewGroup container,            Bundle savedInstanceState    ) {//        SBStress = binding.seekBarStress;//        StressLevel = binding.textViewStressLevel2;        binding = EmotionQuestionnaireBinding.inflate(inflater, container, false);        SBStress = binding.seekBarStress;        StressLevel = binding.textViewStressLevel2;        Finish2 = binding.ButtonFinish2;        SleepDuration = binding.editTextNumberSleepDuration;        RGPeriod = binding.radioGroupPeriod;        spinner_feeling = binding.spinnerFeeling;        spinner_sleep_quality = binding.spinnerSleepQuality;        spinner_feeling_phys = binding.spinnerFeelingPhys;        spinner_medication = binding.spinnerMedication;        spinner_alcohol = binding.spinnerAlcohol;        spinner_smoking = binding.spinnerSmoking;        Finish2.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                m_checkedPeriodRadioButtonID = RGPeriod.getCheckedRadioButtonId();                findRadioButton(m_checkedPeriodRadioButtonID);                m_sleepDuration = SleepDuration.getText().toString();                m_feeling = spinner_feeling.getSelectedItem().toString();                m_feeling_phys = spinner_feeling_phys.getSelectedItem().toString();                m_sleepQuality = spinner_sleep_quality.getSelectedItem().toString();                m_alcohol = spinner_alcohol.getSelectedItem().toString();                m_medication = spinner_medication.getSelectedItem().toString();                m_cigarettes = spinner_smoking.getSelectedItem().toString();                MainActivity.m_todaysData.put(Globals.feeling, String.valueOf(m_feeling));                try {                    MainActivity.saveData();                } catch (IOException e) {                    e.printStackTrace();                }                MainActivity.m_todaysData.put(Globals.sleepQuality, String.valueOf(m_sleepQuality));                try {                    MainActivity.saveData();                } catch (IOException e) {                    e.printStackTrace();                }                MainActivity.m_todaysData.put(Globals.sleepDuration, String.valueOf(m_sleepDuration));                try {                    MainActivity.saveData();                } catch (IOException e) {                    e.printStackTrace();                }                MainActivity.m_todaysData.put(Globals.physicalFeeling, String.valueOf(m_feeling_phys));                try {                    MainActivity.saveData();                } catch (IOException e) {                    e.printStackTrace();                }                MainActivity.m_todaysData.put(Globals.alcohol, String.valueOf(m_alcohol));                try {                    MainActivity.saveData();                } catch (IOException e) {                    e.printStackTrace();                }                MainActivity.m_todaysData.put(Globals.medication, String.valueOf(m_medication));                try {                    MainActivity.saveData();                } catch (IOException e) {                    e.printStackTrace();                }                MainActivity.m_todaysData.put(Globals.cigarettes, String.valueOf(m_cigarettes));                try {                    MainActivity.saveData();                } catch (IOException e) {                    e.printStackTrace();                }                String text = "Saved";                Toast.makeText(EmotionQuestionnaire.getContext(),text, Toast.LENGTH_LONG).show();            }        });        return binding.getRoot();    }    private void findRadioButton(Integer m_checkedPeriodRadioButtonID) {        switch (m_checkedPeriodRadioButtonID)        {            case R.id.radioButton_no:                MainActivity.m_todaysData.put(Globals.period, String.valueOf("No"));                try {                    MainActivity.saveData();                } catch (IOException e) {                    e.printStackTrace();                }                break;            case R.id.radioButton_yes:                MainActivity.m_todaysData.put(Globals.period, String.valueOf("Yes"));                try {                    MainActivity.saveData();                } catch (IOException e) {                    e.printStackTrace();                }                break;        }    }    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {        super.onViewCreated(view, savedInstanceState);        // set initial stress level        if(Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today)).containsKey(Globals.stressLevel)){            m_stressLevel=Integer.parseInt(Objects.requireNonNull((Objects.requireNonNull(MainActivity.getEmotionData().get(MainActivity.m_today))).get(Globals.stressLevel)));            SBStress.setProgress(m_stressLevel);            if(m_stressLevel>0) {                StressLevel.setText("   " + m_stressLevel * 10 + "%");            }        }        SBStress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {            @Override            public void onStopTrackingTouch(SeekBar seekBar) {                // TODO Auto-generated method stub            }            @Override            public void onStartTrackingTouch(SeekBar seekBar) {                // TODO Auto-generated method stub            }            @Override            public void onProgressChanged(SeekBar seekBar, int progress,                                          boolean fromUser) {                // TODO Auto-generated method stub                m_stressLevel = SBStress.getProgress();                StressLevel.setText("   " + m_stressLevel*10 + "%");                seekBar.setMax(10);               MainActivity.m_todaysData.put(Globals.stressLevel, String.valueOf(m_stressLevel));                try {                    MainActivity.saveData();                } catch (IOException e) {                    e.printStackTrace();                }            }        });        //Create first spinner        //Spinner spinner_feeling = view.findViewById(R.id.spinner_feeling);        //Create adapter for first spinner        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(((EmotionQuestionnaire) this).getContext(), R.array.feeling, android.R.layout.simple_spinner_item);        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        spinner_feeling.setAdapter(adapter);        spinner_feeling.setOnItemSelectedListener(this);        //Create second spinner        //Spinner spinner_sleep_quality = view.findViewById(R.id.spinner_sleep_quality);        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(((EmotionQuestionnaire) this).getContext(), R.array.sleep_quality, android.R.layout.simple_spinner_item);        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        spinner_sleep_quality.setAdapter(adapter2);        spinner_sleep_quality.setOnItemSelectedListener(this);        //Create third spinner        //Spinner spinner_feeling_phys = view.findViewById(R.id.spinner_feeling_phys);        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(((EmotionQuestionnaire) this).getContext(), R.array.feeling_phys, android.R.layout.simple_spinner_item);        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        spinner_feeling_phys.setAdapter(adapter3);        spinner_feeling_phys.setOnItemSelectedListener(this);        //Create fourth spinner        //Spinner spinner_smoking = view.findViewById(R.id.spinner_smoking);        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(((EmotionQuestionnaire) this).getContext(), R.array.smoking, android.R.layout.simple_spinner_item);        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        spinner_smoking.setAdapter(adapter4);        spinner_smoking.setOnItemSelectedListener(this);        //Create fifth spinner        //Spinner spinner_medication = view.findViewById(R.id.spinner_medication);        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(((EmotionQuestionnaire) this).getContext(), R.array.medication, android.R.layout.simple_spinner_item);        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        spinner_medication.setAdapter(adapter5);        spinner_medication.setOnItemSelectedListener(this);        //Create sixth spinner        //Spinner spinner_alcohol = view.findViewById(R.id.spinner_alcohol);        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(((EmotionQuestionnaire) this).getContext(), R.array.alcohol, android.R.layout.simple_spinner_item);        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);        spinner_alcohol.setAdapter(adapter6);        spinner_alcohol.setOnItemSelectedListener(this);        homeNavi2 = NavHostFragment.findNavController(EmotionQuestionnaire.this);        EmotionQuestionnaire = EmotionQuestionnaire.this;        //Finish2.setOnClickListener(view -> homeNavi2.navigate(R.id.action_EmotionFragment_to_HomeFragment));    }    @Override    public void onDestroyView() {        super.onDestroyView();        binding = null;    }    @Override    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {        // String text = adapterView.getItemAtPosition(i).toString();        //Toast.makeText(adapterView.getContext(),text,Toast.LENGTH_SHORT).show();    }    @Override    public void onNothingSelected(AdapterView<?> adapterView) {    }}