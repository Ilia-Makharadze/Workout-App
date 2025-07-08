package com.example.mobileappfinal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.content.Context

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val username = view.findViewById<EditText>(R.id.username_input)
        val password = view.findViewById<EditText>(R.id.password_input)
        val loginBtn = view.findViewById<Button>(R.id.login_button)
        val registerText = view.findViewById<TextView>(R.id.go_to_register)

        // 🟢 აიძულე focus და კლავიატურის გამოჩენა username-ზე
        username.requestFocus()
        username.post {
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(username, InputMethodManager.SHOW_IMPLICIT)
        }

        loginBtn.setOnClickListener {
            val dao = UserDAO(requireContext())
            val success = dao.loginUser(username.text.toString(), password.text.toString())
            if (success) {
                (activity as MainActivity).showWebView()
            } else {
                Toast.makeText(context, "მცდარი მონაცემები", Toast.LENGTH_SHORT).show()
            }
        }

        registerText.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}
