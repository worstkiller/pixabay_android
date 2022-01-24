package com.vikas.pixabayandroid.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import com.vikas.pixabayandroid.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var viewModel: PixabayImageViewModel

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    private lateinit var mainBinding: ActivityMainBinding

    private val adapter: PixaPagedAdapter by lazy {
        PixaPagedAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        setUpView()
        getData()
    }

    private fun getData() {
        //calling api to get images
        viewModel.getImages {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
    }

    private fun setUpView() {
        //setting up ui related stuff here
        mainBinding.rvGrid.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            setHasFixedSize(true)
            adapter = this@MainActivity.adapter
        }
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}