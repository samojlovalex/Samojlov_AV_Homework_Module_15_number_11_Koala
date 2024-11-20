package com.example.samojlov_av_homework_module_15_number_11_koala.models

import com.example.samojlov_av_homework_module_15_number_11_koala.R

data class Song(val description: String, val song: Int) {

    companion object {
        val listSongs = mutableListOf(
            Song("UNREAL: Демон", R.raw.unreal_demon),
            Song("Кипелов: Непокорённый", R.raw.kipelov_nepokorjonnyjj),
            Song("Глеб Корнилов: Преодолей", R.raw.gleb_kornilov_preodolejj),
            Song("Radio Tapok: Гвардия Петра",R.raw.radio_tapok_gvardiya_petra),
            Song("Radio Tapok: Ночные ведьмы",R.raw.radio_tapok_nochnye_vedmy),
            Song("Александр Градский: Как молоды мы были",R.raw.aleksandr_gradskijj_kak_molody_my_byli),
            Song("Plamenev: Русский не побеждён",R.raw.pavel_plamenev_russkijj_ne_pobezhdjon),
            Song("Вячеслав Мясников: Дети",R.raw.vyacheslav_myasnikov_deti)
        )
    }
}