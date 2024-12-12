package com.dicoding.sortify.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dicoding.sortify.data.AuthViewModelFactory
import com.dicoding.sortify.databinding.FragmentProfileBinding
import com.dicoding.sortify.databinding.LogoutAuthDialogBinding
import com.dicoding.sortify.ui.about.AboutActivity
import com.dicoding.sortify.ui.login.LoginActivity
import com.dicoding.sortify.ui.profile.email.EditEmailActivity
import com.dicoding.sortify.ui.profile.fullname.EditNameActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels {
        AuthViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.editProfile.setOnClickListener {
            val intent = Intent(requireContext(), EditNameActivity::class.java)
            startActivity(intent)
        }

        binding.editEmail.setOnClickListener {
            val intent = Intent(requireContext(), EditEmailActivity::class.java)
            startActivity(intent)
        }

        binding.changePassword.setOnClickListener {
            val intent = Intent(requireContext(), ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        binding.layoutAbout.setOnClickListener {
            val intent = Intent(requireContext(), AboutActivity::class.java)
            startActivity(intent)
        }

        profileViewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                binding.tvNameUser.text = user.fullname
            }
        }
        setupAction()

        return binding.root

    }

    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            val dialogBinding = LogoutAuthDialogBinding.inflate(layoutInflater)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogBinding.root)
                .setCancelable(false)
                .create()

            dialogBinding.btnConfirmYes.setOnClickListener {
                dialog.dismiss()
                profileViewModel.logout()
                Toast.makeText(requireContext(), "You’ve successfully logged out", Toast.LENGTH_SHORT).show()
                navigateToLogin()
            }




            dialogBinding.btnConfirmNo.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
