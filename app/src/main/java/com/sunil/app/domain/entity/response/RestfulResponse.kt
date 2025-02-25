package com.sunil.app.domain.entity.response

import com.google.gson.annotations.SerializedName

class GetAllDataResponse : ArrayList<ResponseItem>()


data class ResponseItem(
    @SerializedName("data")
    val `data`: ResponseData? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null
)

data class ResponseData(
    @SerializedName("CPU model")
    val cPUModel: String? = null,
    @SerializedName("capacity")
    val capacity: String? = null,
    @SerializedName("Capacity")
    val capacity1: String? = null,
    @SerializedName("capacity GB")
    val capacityGB: Int? = null,
    @SerializedName("Case Size")
    val caseSize: String? = null,
    @SerializedName("color")
    val color: String? = null,
    @SerializedName("Color")
    val color1: String? = null,
    @SerializedName("Description")
    val description: String? = null,
    @SerializedName("generation")
    val generation: String? = null,
    @SerializedName("Generation")
    val generation1: String? = null,
    @SerializedName("Hard disk size")
    val hardDiskSize: String? = null,
    @SerializedName("price")
    val price: Double? = null,
    @SerializedName("Price")
    val price1: String? = null,
    @SerializedName("Screen size")
    val screenSize: Double? = null,
    @SerializedName("Strap Colour")
    val strapColour: String? = null,
    @SerializedName("year")
    val year: Int? = null
)
