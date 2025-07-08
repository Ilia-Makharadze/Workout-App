package com.example.mobileappfinal

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class UserDAO(context: Context) {
    private val dbHelper = UserDatabaseHelper(context)

    // რეგისტრაცია
    fun registerUser(username: String, password: String): Boolean {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("username", username)
            put("password", password)
        }
        return try {
            db.insertOrThrow("users", null, values)
            true
        } catch (e: Exception) {
            false
        } finally {
            db.close()
        }
    }

    // ლოგინი
    fun loginUser(username: String, password: String): Boolean {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM users WHERE username=? AND password=?",
            arrayOf(username, password)
        )
        val success = cursor.count > 0
        cursor.close()
        db.close()
        return success
    }
}
