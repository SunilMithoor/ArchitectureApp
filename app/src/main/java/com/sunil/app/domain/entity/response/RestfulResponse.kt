package com.sunil.app.domain.entity.response

import com.google.gson.annotations.SerializedName

/**
 *Represents a list of ResponseItem objects.
 * This class is used to hold the response data from the API.
 */
class GetAllDataResponse : ArrayList<ResponseItem>()

/**
 * Represents a single item in the response data.
 *
 * @property data The detailed data associated with this item.* @property id The unique identifier of the item.
 * @property name The name of the item.
 */
data class ResponseItem(
    @SerializedName("data")
    val `data`: ResponseData? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null
)

/**
 * Represents the detailed data associated with a ResponseItem.
 *
 * @property cpuModel The CPU model of the device.
 * @property capacity The capacity of the device (general).
 * @property capacityGb The capacity of the device in GB.
 * @property caseSize The case size of the device.
 * @property color The color of the device.
 * @property description A description of the device.
 * @property generation The generation of the device.
 * @property hardDiskSize The hard disk size of the device.
 * @property price The price of the device.
 * @property screenSize The screen size of the device.
 * @property strapColor The strap color of the device (if applicable).
 * @property year The year the device was released.
 */
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
