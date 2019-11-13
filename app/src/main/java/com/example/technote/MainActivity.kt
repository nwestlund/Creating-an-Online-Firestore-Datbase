package com.example.technote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

private val db = FirebaseFirestore.getInstance()

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val saveStudent = findViewById<Button>(R.id.saveStudent)
    val deleteStudent = findViewById<Button>(R.id.deleteStudent)
    val updateStudent = findViewById<Button>(R.id.updateStudent)
    val searchStudent = findViewById<Button>(R.id.searchStudentButton)

    saveStudent.setOnClickListener {
        val student = hashMapOf(
            "studentGPA" to studentGPA.text.toString().toDouble(),
            "studentMajor" to studentMajor.text.toString(),
            "studentGraduationDate" to studentGraduationDate.text.toString(),
            "studentName" to studentName.text.toString()
        )
        db.collection("Student")
            .document(studentName.text.toString())
            .set(student)
    }

    deleteStudent.setOnClickListener {
        db.collection("Student")
            .document(studentName.text.toString()).delete()
    }

    updateStudent.setOnClickListener {
        val studentToUpdate = db.collection("Student")
            .document(studentName.text.toString())

        val studentGPACheck = studentGPA.text.toString()
        val studentMajorCheck = studentMajor.text.toString()
        val studentGraduationCheck = studentGraduationDate.text.toString()
        val studentNameCheck = studentName.text.toString()

        if (studentGPACheck.isNotEmpty()) {
            studentToUpdate
                .update("studentGPA", studentGPA.text.toString().toDouble())
        }
        if (studentMajorCheck.isNotEmpty()) {
            studentToUpdate
                .update("studentMajor", studentMajor.text.toString())
        }
        if (studentGraduationCheck.isNotEmpty()) {
            studentToUpdate
                .update("studentGraduationDate", studentGraduationDate.text.toString())
        }
        if (studentNameCheck.isNotEmpty()) {
            studentToUpdate
                .update("studentName", studentName.text.toString())
        }
    }

    searchStudent.setOnClickListener {
        val studentToSearch = db.collection("Student")
            .document(studentName.text.toString())

        studentToSearch.get()
            .addOnSuccessListener { documentSnapshot ->
                val studentName = documentSnapshot.getString("studentName")
                val studentMajor = documentSnapshot.getString("studentMajor")
                val studentGPA = documentSnapshot.getDouble("studentGPA")
                val studentGraduationDate = documentSnapshot.getString("studentGraduationDate")

                searchStudentName.text = studentName
                searchStudentMajor.text = studentMajor
                searchStudentGPA.text = studentGPA.toString()
                searchGraduationDate.text = studentGraduationDate

                searchStudentName.visibility = View.VISIBLE
                searchStudentMajor.visibility = View.VISIBLE
                searchStudentGPA.visibility = View.VISIBLE
                searchGraduationDate.visibility = View.VISIBLE
                searchResults.visibility = View.VISIBLE
            }
    }
}
}
