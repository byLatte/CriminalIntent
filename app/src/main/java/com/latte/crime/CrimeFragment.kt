package com.latte.crime

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import java.util.*


private const val ARG_CRIME_ID = "crime_id"
private const val DIALOG_DATE = "dialogDate"
private const val REQUEST_DATE = 0
// 디테일 뷰
class CrimeFragment: Fragment(), DatePickerFragment.Callbacks{

    private lateinit var crime: Crime
    private lateinit var titleField: EditText
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox: CheckBox

    private val crimeDetailViewModel: CrimeDetailViewModel by lazy{
        ViewModelProvider(this).get(CrimeDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()
        var crimeId: UUID = arguments?.getSerializable(ARG_CRIME_ID) as UUID

        crimeDetailViewModel.loadCrime(crimeId)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime,container,false)

        titleField = view.findViewById(R.id.crime_title) as EditText // EditText 로 변환
        dateButton = view.findViewById(R.id.crime_date) as Button // EditText 로 변환
        solvedCheckBox = view.findViewById(R.id.crime_solved) as CheckBox

//        dateButton.apply {
//            text = crime.date.toString()
//            isEnabled = false // 버튼 비활성화
//        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeDetailViewModel.crimeLiveData.observe(
            viewLifecycleOwner,
            Observer {crime -> crime?.let {
                this.crime = crime
                updateUI()
            }}
        )
    }

    private fun updateUI() {
        titleField.setText(crime.title)
        dateButton.text = crime.date.toString()
//        solvedCheckBox.isChecked = crime.isSolved
        solvedCheckBox.apply{
            isChecked = crime.isSolved
            jumpDrawablesToCurrentState() //check 애니메이션 생략
        }
    }


    // textWatcher를 onStart에서 설정해줘야 뷰가 복원된 후 실행된다.
    // 그전인 onCreate,onCreateView에서 설정하면 뷰가 복원되기전 실행이 된다.
    override fun onStart() {
        super.onStart()
        val titleWatcher = object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                crime.title = sequence.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }

        titleField.addTextChangedListener(titleWatcher)

        solvedCheckBox.apply {
            setOnCheckedChangeListener {_, isChecked -> // '_' parameter를 사용할 필요가 없어서 생략.
                crime.isSolved = isChecked
            }
        }

        dateButton.setOnClickListener{
//            DatePickerFragment().apply {
            DatePickerFragment.newInstance(crime.date).apply {
                setTargetFragment(this@CrimeFragment, REQUEST_DATE) //appcompat 1.3이상에선 권장하지않는방법
                show(this@CrimeFragment.getParentFragmentManager(), DIALOG_DATE)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        crimeDetailViewModel.saveCrime(crime)
    }

    override fun onDateSelected(date: Date) {
        crime.date = date
        updateUI()
    }


    companion object{
        fun newInstance(crimeId: UUID): CrimeFragment{
            val args = Bundle().apply {
                putSerializable(ARG_CRIME_ID,crimeId)
            }
            return CrimeFragment().apply {
                arguments = args
            }
        }
    }



}