//package com.dicoding.sortify.data
//
//import android.content.Context
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.dicoding.sortify.data.repository.ArticleRepository
//import com.dicoding.sortify.ui.article.ArticleViewModel
//
//class ViewModelFactory(private val repository: ArticleRepository) : ViewModelProvider.NewInstanceFactory() {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return when {
//            modelClass.isAssignableFrom(ArticleViewModel::class.java) -> {
//                ArticleViewModel(repository) as T
//            }
////            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
////                LoginViewModel(repository) as T
////            }
//            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
//        }
//    }
//
////    companion object {
////        fun getInstance(context: Context) = ViewModelFactory(Injection.provideRepository(context))
////    }
//}