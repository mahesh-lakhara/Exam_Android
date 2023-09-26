package com.kevin.taskmanagement.Fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Delete
import androidx.room.Update
import com.kevin.taskmanagement.Adapter.TaskAdapter
import com.kevin.taskmanagement.Dao.TaskDao
import com.kevin.taskmanagement.Database.RoomDB
import com.kevin.taskmanagement.Enitiy.TaskEnitiy
import com.kevin.taskmanagement.R
import com.kevin.taskmanagement.databinding.FragmentHomeBinding
import com.kevin.taskmanagement.databinding.TodolistviewBinding
import java.util.Locale
import com.kevin.taskmanagement.databinding.UpdatedialogBinding
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.time.Duration.Companion.days

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: TaskAdapter
    var Tasklist = ArrayList<TaskEnitiy>()
    lateinit var db: RoomDB

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        db = RoomDB.init(context)

        initview()

        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun initview() {
        var list = db.task().GetTask()
        adapter = TaskAdapter(
            list as ArrayList<TaskEnitiy>, {
                Update(it)
            }, {
                Delete(it)
            })

        binding.rcvtasklist.layoutManager = LinearLayoutManager(context)
        binding.rcvtasklist.adapter = adapter

//        binding.sortby.setOnClickListener(fun (v: View?) {
//            var popupMenu = PopupMenu(context,view)
//            popupMenu.menuInflater.inflate(R.menu.sortby, popupMenu.menu)
//
//            popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
//                override fun onMenuItemClick(p0: MenuItem?): Boolean {
//
//                    if (p0?.itemId == R.id.oltola) {
////                        adapter.sortBy {
////                            it.date
//                        }
//                    }
//
//                    if (p0?.itemId == R.id.latool) {
//                        tempadapter.sortByDescending {
//                            it.date
//                        }
//                    }
//                    adapter.update(db.task().GetTask())
//                    return true
//                }
//            })
//            popupMenu.show()
//        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun Update(it: TaskEnitiy) {

        var dialog = Dialog(requireContext())
        var bind = UpdatedialogBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        bind.edtdate.setOnClickListener {

            var date = Date()

            var format1 = SimpleDateFormat("dd-MM-yy")
            var currentDate = format1.format(date)

            var dates = currentDate.split("-")
            bind.edtdate.text = currentDate

            var dialog =
                DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {

                        var Year = p1
                        var Month = p2 + 1
                        var Date = p3

                        var selectedDate = "$p3-${(p2 + 1)}-$p1"
                        bind.edtdate.text = selectedDate
                    }
                }, dates[2].toInt(), dates[1].toInt() - 1, dates[0].toInt())
            dialog.show()
        }

        bind.edttime.setOnClickListener {
            var date = Date()

            var format2 = SimpleDateFormat("hh:mm")
            var currentTime = format2.format(date)

            bind.edttime.text = currentTime
            var seleTime = currentTime
            var dialog1 =
                TimePickerDialog(context, object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                        var hour = p1
                        var minute = p2
                        var sdf = SimpleDateFormat("hh:mm", Locale.US)
                        var tme = "$hour:$minute "
                        bind.edttime.setText(tme)
                    }
                }, 10, 0, true)
            dialog1.show()
        }

        bind.btnsubmit.setOnClickListener {
            var title = bind.edtTask.text.toString()
            var text = bind.edtdescription.text.toString()
            var Date = bind.edtdate.text.toString()
            var Month = bind.edtdate.text.toString()
            var Year = bind.edtdate.text.toString()
            var hour = bind.edttime.text.toString()
            var minute = bind.edttime.text.toString()
            var format = SimpleDateFormat("dd-MM-yy hh:mm")
            var current = format.format(Date())
            var data = TaskEnitiy(title, text, Date, Month, Year, hour, minute)
            var tasks = db.task().GetTask()

            for (task in tasks) {
                task.title = title
                task.discription = text
                task.date = Date
                task.month = Month
                task.year = Year
                task.hour = hour
                task.minute = minute
                task.id

                if (title.isEmpty() || text.isEmpty() || Date.isEmpty() || Month.isEmpty() || Year.isEmpty() || hour.isEmpty() || minute.isEmpty()) {
                    Toast.makeText(context, "Please enter data", Toast.LENGTH_SHORT).show()
                } else {
                    bind.edtTask.setText("")
                    bind.edtdescription.setText("")
                    bind.edtdate.setText("__-__-__")
                    bind.edttime.setText("__:__")
                    var data = TaskEnitiy(title, text, Date, Month, Year, hour, minute)
                    db.task().UpdateTask(task)
                }
                adapter.update(db.task().GetTask())
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    fun Delete(it: Int) {
        db.task().DeleteTask(it)
        adapter.update(db.task().GetTask())
    }
}