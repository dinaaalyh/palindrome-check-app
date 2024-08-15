package com.submission.mypalindrome.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.mypalindrome.data.UserAdapter
import com.submission.mypalindrome.data.UserViewModel
import com.submission.mypalindrome.data.remote.response.DataItem
import com.submission.mypalindrome.databinding.ActivityThirdScreenBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ThirdScreenActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityThirdScreenBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.main) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemInsets.left, systemInsets.top, systemInsets.right, systemInsets.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        initializeRecyclerView()
        initializeToolbar()
        initializeSwipeRefresh()

        lifecycleScope.launch {
            viewModel.userList.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun initializeRecyclerView() {
        adapter = UserAdapter(object : UserAdapter.OnItemClickListener {
            override fun onItemClick(user: DataItem) {
                val resultIntent = Intent().apply {
                    putExtra("selectedUserName", "${user.firstName} ${user.lastName}")
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        })

        viewBinding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@ThirdScreenActivity)
            adapter = this@ThirdScreenActivity.adapter
        }
    }

    private fun initializeSwipeRefresh() {
        viewBinding.refresh.setOnRefreshListener {
            adapter.refresh()
            viewBinding.refresh.isRefreshing = false
        }
    }

    private fun initializeToolbar() {
        setSupportActionBar(viewBinding.toolbarThd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}