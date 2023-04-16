package com.example.kholidays.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kholidays.ui.theme.KHolidaysTheme
import com.memeze.minimalcalendar.ui.MinimalCalendar
import java.time.format.DateTimeFormatter


class MainActivity : ComponentActivity() {

    private val viewModel: CalendarViewModel by viewModels()

    private var wasSelected = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val today = remember{mutableStateOf("")}
            viewModel.holidaysSLE.observe(this) {
                if (!wasSelected) {
                    wasSelected = true
                    today.value=it
                } else{
                    navigateToHolidaysActivity(it)
                }

            }
            KHolidaysTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var selectDate by remember { mutableStateOf("") }
                    Column(
                        modifier = Modifier.padding(top=15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally){


                    MinimalCalendar(
                        onSelectDate = { date ->
                            viewModel.loadHolidays(
                                year = date.year,
                                month = date.month.value,
                                date.dayOfMonth
                            )
                            selectDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                        }
                    )
                        Text(
                            text=today.value,
                            fontSize = 26.sp,
                            modifier = Modifier.padding(top=30.dp)
                        )
                    }
                }
            }
        }
    }

    private fun navigateToHolidaysActivity(it: String) {
        val intent = Intent(this, HolidaysActivity::class.java)
        intent.putExtra(INTENT_DAYS_NAME, it)
        startActivity(intent)
    }


    companion object {
        const val INTENT_DAYS_NAME = "INTENT_DAYS_NAME"
    }
}

