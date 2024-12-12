package com.dicoding.sortify.data.local

import com.dicoding.sortify.R

data class Waste(
    val name: String,
    val imageResId: Int,
    val description: String,
    val funFact: String
)

object WasteData {
    val wasteList = listOf(
        Waste(
            name = "Plastic",
            imageResId = R.drawable.iv_plastic,
            description = "Plastic, a versatile material that has become an integral part of modern life. However, its durability is a major problem. Plastic takes hundreds of years to decompose and can pollute the environment, especially oceans. Microplastics, tiny plastic fragments, have even been found in the bodies of humans and animals.",
            funFact = "Did you know? One million plastic bottles are bought every minute worldwide! Imagine how much plastic waste is produced every day."
        ),
        Waste(
            name = "Glass",
            imageResId = R.drawable.iv_glass,
            description = "Glass, an elegant material often used for various products, from beverage bottles to windows. Although it can be recycled, its production process requires very high temperatures and a lot of energy.",
            funFact = "Did you know? A recycled glass bottle can become a new glass bottle in just 30 days!"
        ),
        Waste(
            name = "Paper",
            imageResId = R.drawable.iv_paper,
            description = "Paper, a material we use every day, from books and newspapers to food packaging. Paper production requires a lot of water and wood. Deforestation due to logging for paper production threatens the habitats of various animals and causes climate change.",
            funFact = "One tree can produce about 8,333 sheets of paper. Imagine how many trees have to be cut down to meet our paper needs every year."
        ),
        Waste(
            name = "Board",
            imageResId = R.drawable.iv_cardboard,
            description = "Cardboard, a material made from wood pulp and often used for packaging. Although it can be recycled, cardboard production also contributes to deforestation.",
            funFact = "Cardboard is one of the easiest materials to recycle and is widely reused in the packaging industry."
        ),
        Waste(
            name = "Metal",
            imageResId = R.drawable.iv_metal,
            description = "Metal, a strong and durable material. Metals such as aluminum and iron can be recycled many times without losing their quality. However, the process of mining new metals can damage the environment.",
            funFact = "Recycling one aluminum can saves enough energy to power a television for 3 hours!"
        ),
        Waste(
            name = "Residue",
            imageResId = R.drawable.iv_residue,
            description = "Residual waste is a type of waste that is difficult to recycle or reprocess. Usually, this waste consists of a mixture of various materials that are difficult to separate, such as plastic-coated food packaging, diapers, and medical waste.",
            funFact = "Residual waste takes a very long time to decompose and often ends up in landfills."
        )
    )
}