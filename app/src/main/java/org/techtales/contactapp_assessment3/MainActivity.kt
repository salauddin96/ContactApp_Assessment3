package org.techtales.contactapp_assessment3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.techtales.contactapp_assessment3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var studentDatabase: PersonDatabase
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        studentDatabase = PersonDatabase.getDatabase(this)
        binding.save.setOnClickListener {
            saveData()
        }
        binding.search.setOnClickListener {
            searchData()
        }
        binding.deleteAll.setOnClickListener {
            GlobalScope.launch {
                studentDatabase.ContactDao().deleteAll()
            }
        }


    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun searchData() {
        val rollNo = binding.searchEtx.text.toString()
        if (rollNo.isNotEmpty()) {
            lateinit var student: Person
            GlobalScope.launch {
                student = (studentDatabase.ContactDao().findByRoll(rollNo.toInt()))
                if (studentDatabase.ContactDao().isEmpty())
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(
                            this@MainActivity,
                            "Database have no data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                else {
                    displayData(student)
                }
            }
        }
    }

    private suspend fun displayData(student: Person) {
        withContext(Dispatchers.Main) {
            binding.firstName.setText(student.firstName.toString())
            binding.lastName.setText(student.lastName.toString())
            binding.rollNbr.setText(student.rollNo.toString())

        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun saveData() {
        val firstName = binding.firstName.text.toString()
        val lastName = binding.lastName.text.toString()
        val rollNo = binding.rollNbr.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && rollNo.isNotEmpty()) {
            val student = Person(null, firstName, lastName, rollNo.toInt())
            GlobalScope.launch(Dispatchers.IO) {
                PersonDatabase.C.insert((student))
            }
            binding.firstName.text.clear()
            binding.lastName.text.clear()
            binding.rollNbr.text.clear()

            Toast.makeText(this@MainActivity, "Data saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MainActivity, "Please enter all the data", Toast.LENGTH_SHORT)
                .show()
        }
    }
}