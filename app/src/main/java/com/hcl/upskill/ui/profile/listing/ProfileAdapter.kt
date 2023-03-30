package com.hcl.upskill.ui.profile.listing

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hcl.upskill.R
import com.hcl.upskill.databinding.AdapterProfileBinding
import com.hcl.upskill.model.response.UserDataResponseModel

class ProfileAdapter(private val context: Context?,
                     private val list : MutableList<UserDataResponseModel?>?) : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    override fun getItemCount(): Int = list?.size?:0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProfileViewHolder(AdapterProfileBinding.inflate(LayoutInflater.from(context),parent,false))


    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.binding.model = list?.get(position)
        holder.binding.executePendingBindings()
    }

    inner class ProfileViewHolder(var binding: AdapterProfileBinding)
        : RecyclerView.ViewHolder(binding.root) {
            init {
                binding.cvMain.setOnClickListener{
                    (context as? AppCompatActivity)?.findNavController(R.id.fragmentContainerView)?.
                    navigate(ProfilesFragmentDirections.actionProfilesFragmentToProfileDetailFragment())
                }
            }

    }


}