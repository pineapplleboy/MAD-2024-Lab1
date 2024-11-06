package com.example.moviecatalog.app.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalog.R
import com.example.moviecatalog.app.app.AppComponent
import com.example.moviecatalog.app.presentation.adapter.FriendAdapter
import com.example.moviecatalog.app.presentation.adapter.MovieListAdapter
import com.example.moviecatalog.app.presentation.viewmodel.FriendsViewModel
import com.example.moviecatalog.databinding.ActivityFriendsBinding

class FriendsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFriendsBinding
    private lateinit var appComponent: AppComponent
    private lateinit var vm: FriendsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appComponent = AppComponent(this)
        vm = appComponent.provideFriendsViewModel()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        vm.getFriends()

        vm.friends.observe(this) {
            (binding.friendsRecyclerView.adapter as FriendAdapter).submitList(it)
        }

        setupRecyclerView()

        binding.returnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val friendAdapter = FriendAdapter(vm)
        binding.friendsRecyclerView.apply {
            layoutManager = GridLayoutManager(this.context, 3)
            adapter = friendAdapter
        }
    }
}