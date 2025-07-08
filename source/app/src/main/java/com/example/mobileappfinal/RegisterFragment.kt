package com.example.mobileappfinal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


class RegisterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val username = view.findViewById<EditText>(R.id.username_input)
        val password = view.findViewById<EditText>(R.id.password_input)
        val registerBtn = view.findViewById<Button>(R.id.register_button)
        val loginText = view.findViewById<TextView>(R.id.go_to_login)

        registerBtn.setOnClickListener {
            val dao = UserDAO(requireContext())
            val success = dao.registerUser(username.text.toString(), password.text.toString())
            if (success) {
                Toast.makeText(context, "რეგისტრაცია წარმატებულია", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(context, "მომხმარებელი უკვე არსებობს", Toast.LENGTH_SHORT).show()
            }
        }

        loginText.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }
}
