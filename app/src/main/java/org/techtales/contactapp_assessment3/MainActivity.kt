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
            binding.nameEtxt.setText(student.name.toString())
            binding.phoneEtxt.setText(student.phone.toString())
            binding.emailEtxt.setText(student.email.toString())

        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun saveData() {
        val name = binding.nameEtxt.text.toString()
        val phone = binding.phoneEtxt.text.toString()
        val email = binding.emailEtxt.text.toString()

        if (name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()) {
            val student = Person(null, name, phone.toInt(), email.toString())
            GlobalScope.launch(Dispatchers.IO) {
                PersonDatabase.ContactDao.insert((student))
            }
            binding.nameEtxt.text.clear()
            binding.phoneEtxt.text.clear()
            binding.emailEtxt.text.clear()

            Toast.makeText(this@MainActivity, "Data saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MainActivity, "Please enter all the data", Toast.LENGTH_SHORT)
                .show()
        }
    }
}