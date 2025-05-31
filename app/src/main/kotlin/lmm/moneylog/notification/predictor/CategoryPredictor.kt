package lmm.moneylog.notification.predictor

object CategoryPredictor {
    private val merchantCategories =
        mapOf(
            "IFD*" to 0
        )

    fun predictCategory(place: String): Int? {
        val normalizedPlace = place.lowercase().trim()

        return merchantCategories.entries.find { (merchant, _) ->
            normalizedPlace.contains(merchant.lowercase())
        }?.value
    }
}
