package com.dicoding.sortify.ui.waste

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.sortify.databinding.ActivityDetailWasteBinding

class DetailWasteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailWasteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailWasteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("NAME")
        val imageResId = intent.getIntExtra("IMAGE", -1)
        val description = intent.getStringExtra("DESC")
        val funfact = intent.getStringExtra("FACT")

        binding.tvTitle.text = name
        binding.ivImage.setImageResource(imageResId)
        binding.tvDescription.text = description
        binding.tvFact.text = funfact

        binding.btnBack.setOnClickListener { finish() }

        binding.fabShare.setOnClickListener {
            val shareText = """
                🌍♻️ Waste Information from Sortify app:
                
                $name Waste
                $funfact
                
                Download the Sortify app now to learn more about sorting waste and protecting our planet! 📱🌱
                #Sortify #GoGreen #WasteManagement
            """.trimIndent()


            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }

            startActivity(Intent.createChooser(shareIntent, "Share Waste Info via"))
        }
    }
}
