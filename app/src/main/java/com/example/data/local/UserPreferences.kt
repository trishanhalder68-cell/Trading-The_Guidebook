package com.example.data.local

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("trading_guidebook_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_REGION_CODE = "selected_region_code"
        private const val KEY_IS_VIP_PRO = "is_vip_pro_unlocked"
        private const val KEY_PROMO_CODE = "applied_promo_code"
        private const val KEY_LAST_QUOTE_DAY = "last_quote_day"
        private const val KEY_SAVINGS_TARGET = "savings_target_percent"
        private const val KEY_UNLOCKED_CHAPTERS = "unlocked_chapter_ids"
        private const val KEY_COMPLETED_STAGES = "completed_roadmap_stages"
        private const val KEY_ACTIVE_STAGE = "active_roadmap_stage"
        private const val KEY_COMPLETED_LESSONS = "completed_lesson_ids"

        // Dedicated Developer code that grants 100% Free Lifetime VIP & Unlocks All Courses
        const val VALID_DEVELOPER_CODE = "TRISH30"
        val VALID_VIP_CODES = setOf(VALID_DEVELOPER_CODE)
    }

    private val defaultUnlockedChapters = setOf("ch_1")

    var unlockedChapterIds: Set<String>
        get() = prefs.getStringSet(KEY_UNLOCKED_CHAPTERS, defaultUnlockedChapters) ?: defaultUnlockedChapters
        set(value) = prefs.edit().putStringSet(KEY_UNLOCKED_CHAPTERS, value).apply()

    fun isChapterUnlocked(chapterId: String): Boolean {
        if (isVipPro) return true
        return unlockedChapterIds.contains(chapterId)
    }

    fun unlockChapter(chapterId: String) {
        val current = unlockedChapterIds.toMutableSet()
        current.add(chapterId)
        unlockedChapterIds = current
    }

    private val defaultCompletedLessons = setOf("tree_lesson_1")

    var completedLessonIds: Set<String>
        get() = prefs.getStringSet(KEY_COMPLETED_LESSONS, defaultCompletedLessons) ?: defaultCompletedLessons
        set(value) = prefs.edit().putStringSet(KEY_COMPLETED_LESSONS, value).apply()

    fun markLessonCompleted(lessonId: String) {
        val current = completedLessonIds.toMutableSet()
        current.add(lessonId)
        completedLessonIds = current
    }

    fun isLessonCompleted(lessonId: String): Boolean {
        return completedLessonIds.contains(lessonId)
    }

    var completedStages: Set<Int>
        get() {
            val stringSet = prefs.getStringSet(KEY_COMPLETED_STAGES, emptySet()) ?: emptySet()
            return stringSet.mapNotNull { it.toIntOrNull() }.toSet()
        }
        set(value) {
            prefs.edit().putStringSet(KEY_COMPLETED_STAGES, value.map { it.toString() }.toSet()).apply()
        }

    fun markStageCompleted(stage: Int) {
        val current = completedStages.toMutableSet()
        current.add(stage)
        completedStages = current
    }

    var activeStage: Int
        get() = prefs.getInt(KEY_ACTIVE_STAGE, 1)
        set(value) = prefs.edit().putInt(KEY_ACTIVE_STAGE, value).apply()

    var selectedRegionCode: String
        get() = prefs.getString(KEY_REGION_CODE, "IN") ?: "IN"
        set(value) = prefs.edit().putString(KEY_REGION_CODE, value).apply()

    var isVipPro: Boolean
        get() = prefs.getBoolean(KEY_IS_VIP_PRO, false)
        set(value) = prefs.edit().putBoolean(KEY_IS_VIP_PRO, value).apply()

    var appliedPromoCode: String
        get() = prefs.getString(KEY_PROMO_CODE, "") ?: ""
        set(value) = prefs.edit().putString(KEY_PROMO_CODE, value).apply()

    var savingsTargetPercent: Int
        get() = prefs.getInt(KEY_SAVINGS_TARGET, 35)
        set(value) = prefs.edit().putInt(KEY_SAVINGS_TARGET, value).apply()

    fun unlockVipWithCode(code: String): Boolean {
        val cleanCode = code.trim().uppercase()
        return if (cleanCode == VALID_DEVELOPER_CODE) {
            isVipPro = true
            appliedPromoCode = cleanCode
            unlockedChapterIds = setOf("ch_1", "ch_2", "ch_3", "ch_4", "ch_5", "ch_6")
            completedStages = setOf(1, 2, 3, 4)
            true
        } else {
            false
        }
    }

    fun unlockVipPaid(method: String) {
        isVipPro = true
        appliedPromoCode = "PAID_$method"
    }

    fun resetVipStatus() {
        isVipPro = false
        appliedPromoCode = ""
    }
}
