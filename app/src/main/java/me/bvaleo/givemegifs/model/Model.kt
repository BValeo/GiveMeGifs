package me.bvaleo.givemegifs.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GifData(@Expose var data: MutableList<ResponseGif>, @Expose var pagination: Pagination)

class Pagination(@SerializedName("total_count") @Expose var totalCount: Int,
                 @Expose var count: Int,
                 @Expose var offset: Int)

class ResponseGif(@Expose var images: Images)

class GifInfo(@Expose var url: String) {
    init {
        url = url.replace("\\", "")
    }
}

class Images(@SerializedName("fixed_height_downsampled") @Expose var info: GifInfo)