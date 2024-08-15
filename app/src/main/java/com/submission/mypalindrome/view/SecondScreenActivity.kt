package com.submission.mypalindrome.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.submission.mypalindrome.databinding.ActivitySecondScreenBinding

class SecondScreenActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySecondScreenBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySecondScreenBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(viewBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.main) { view, insets ->
            val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarInsets.left, systemBarInsets.top, systemBarInsets.right, systemBarInsets.bottom)
            insets
        }

        initializeToolbar()
        initializeActions()
        registerActivityResultLauncher()
    }

    private fun initializeToolbar() {
        setSupportActionBar(viewBinding.toolbarSec)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initializeActions() {
        val userName = intent.getStringExtra("name")
        viewBinding.tvUsername.text = userName

        viewBinding.btnChooseUser.setOnClickListener {
            val nextActivityIntent = Intent(this, ThirdScreenActivity::class.java)
            activityResultLauncher.launch(nextActivityIntent)
        }
    }

    private fun registerActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedUser = result.data?.getStringExtra("selectedUserName")
                if (!selectedUser.isNullOrEmpty()) {
                    viewBinding.tvSelectedUser.text = selectedUser
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}