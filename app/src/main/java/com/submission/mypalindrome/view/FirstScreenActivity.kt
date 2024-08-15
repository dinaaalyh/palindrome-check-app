package com.submission.mypalindrome.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.submission.mypalindrome.R
import com.submission.mypalindrome.databinding.ActivityFirstScreenBinding

class FirstScreenActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityFirstScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFirstScreenBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(viewBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.main) { view, insets ->
            val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarInsets.left, systemBarInsets.top, systemBarInsets.right, systemBarInsets.bottom)
            insets
        }
        initiateAnimation()
        configureActions()
    }

    private fun configureActions() {
        viewBinding.btnCheck.setOnClickListener {
            val userName = viewBinding.tvName.text.toString().trim()
            val inputPalindrome = viewBinding.tvPalindrome.text.toString().trim()

            if (userName.isEmpty()) {
                viewBinding.tvName.error = getString(R.string.name_cannot_be_empty)
                return@setOnClickListener
            }

            if (inputPalindrome.isEmpty()) {
                viewBinding.tvPalindrome.error = getString(R.string.palindrome_input_cannot_be_empty)
                return@setOnClickListener
            }

            val isPalindrome = inputPalindrome.replace(" ", "").equals(
                inputPalindrome.replace(" ", "").reversed(), ignoreCase = true
            )

            val resultMessage =
                if (isPalindrome) getString(R.string.is_palindrome) else getString(R.string.not_palindrome)

            AlertDialog.Builder(this)
                .setMessage(resultMessage)
                .setPositiveButton(getString(R.string.ok), null)
                .show()
        }

        viewBinding.btnNext.setOnClickListener {
            val nextScreenIntent = Intent(this, SecondScreenActivity::class.java)
            nextScreenIntent.putExtra("name", viewBinding.tvName.text.toString().trim())
            startActivity(nextScreenIntent)
        }
    }

    private fun initiateAnimation() {
        val imageAnimator = ObjectAnimator.ofFloat(viewBinding.Image, View.ALPHA, 1f).setDuration(100)
        val nameAnimator = ObjectAnimator.ofFloat(viewBinding.tvName, View.ALPHA, 1f).setDuration(100)
        val palindromeAnimator = ObjectAnimator.ofFloat(viewBinding.tvPalindrome, View.ALPHA, 1f).setDuration(100)
        val checkAnimator = ObjectAnimator.ofFloat(viewBinding.btnCheck, View.ALPHA, 1f).setDuration(100)
        val nextAnimator = ObjectAnimator.ofFloat(viewBinding.btnNext, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                imageAnimator,
                nameAnimator,
                palindromeAnimator,
                checkAnimator,
                nextAnimator
            )
            startDelay = 100
        }.start()
    }
}