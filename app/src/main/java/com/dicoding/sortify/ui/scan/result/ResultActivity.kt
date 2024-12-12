package com.dicoding.sortify.ui.scan.result

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.sortify.adapter.RecommendationsAdapter
import com.dicoding.sortify.data.local.database.HistoryDatabase
import com.dicoding.sortify.data.local.database.entity.HistoryEntity
import com.dicoding.sortify.data.local.database.entity.RecommendationItemEntity
import com.dicoding.sortify.data.remote.response.RecommendationsItem
import com.dicoding.sortify.databinding.ActivityResultBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        val imageUri = intent.getStringExtra(EXTRA_IMAGE_URI)
        val wasteClass = intent.getStringExtra(EXTRA_WASTE_CLASS)
        val confidence = intent.getFloatExtra(EXTRA_CONFIDENCE, 0.0f)
        val confidencePercentage = NumberFormat.getPercentInstance().format(confidence)
        val recommendations = intent.getParcelableArrayListExtra<RecommendationsItem>(EXTRA_RECOMMENDATIONS)

        binding.tvWasteClass.text = wasteClass
        binding.tvConfidenceScore.text = confidencePercentage.toString()
        binding.previewImageView.setImageURI(imageUri?.toUri())

        saveClassificationToHistory(wasteClass, confidencePercentage, imageUri, recommendations)


        val adapter = RecommendationsAdapter(recommendations ?: emptyList())
        binding.rvRecommendations.layoutManager = LinearLayoutManager(this)
        binding.rvRecommendations.adapter = adapter
    }

    private fun saveClassificationToHistory(
        wasteClass: String?,
        confidencePercentage: String?,
        imageUri: String?,
        recommendations: List<RecommendationsItem>?
    ) {
        val date = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss",
            Locale.getDefault()
        ).format(Date())

        val history = HistoryEntity(
            wasteClass = wasteClass,
            confidenceScore = confidencePercentage,
            imageUri = imageUri,
            date = date
        )

        val historyDao = HistoryDatabase.getDatabase(this).historyDao()

        val recommendationItems = recommendations?.map { recommendation ->
            RecommendationItemEntity(
                title = recommendation.title,
                link = recommendation.link,
                historyId = 0
            )
        } ?: emptyList()

        CoroutineScope(Dispatchers.IO).launch {
            val historyId = historyDao.insertHistory(history)

            val updatedRecommendationItems = recommendationItems.map {
                it.copy(historyId = historyId)
            }
            updatedRecommendationItems.forEach { recommendation ->
                val recommendationDao = HistoryDatabase.getDatabase(this@ResultActivity).historyDao()
                recommendationDao.insertRecommendation(recommendation)
            }
        }
    }

    companion object {
        const val EXTRA_WASTE_CLASS = "extra_waste_class"
        const val EXTRA_CONFIDENCE = "extra_confidence"
        const val EXTRA_RECOMMENDATIONS = "extra_recommendations"
        const val EXTRA_IMAGE_URI = "extra_image_uri"

    }
}
