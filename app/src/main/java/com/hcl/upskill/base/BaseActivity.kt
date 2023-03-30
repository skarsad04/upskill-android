package com.hcl.upskill.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import javax.inject.Inject

abstract class BaseActivity<B: ViewBinding>: AppCompatActivity() {

    protected lateinit var binding : B

    @Inject
    protected lateinit var dispatcher: BaseDispatcher


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = binding()
        setContentView(binding.root)
        init()
        initCtrl()
        observer()
    }

    abstract fun binding() : B
    abstract fun init()
    abstract fun initCtrl()
    abstract fun observer()
}