package com.example.rma24projekat_19153

import com.google.gson.annotations.SerializedName

data class PlantResponse(
    @SerializedName("data") val data: PlantData?,
    @SerializedName("meta") val meta: Meta?
)


data class PlantData(
    @SerializedName("id") val id: Int?,
    @SerializedName("common_name") val commonName: String?,
    @SerializedName("slug") val slug: String?,
    @SerializedName("scientific_name") val scientificName: String?,
    @SerializedName("main_species_id") val mainSpeciesId: Int?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("year") val year: Int?,
    @SerializedName("bibliography") val bibliography: String?,
    @SerializedName("author") val author: String?,
    @SerializedName("family_common_name") val familyCommonName: String?,
    @SerializedName("genus_id") val genusId: Int?,
    @SerializedName("observations") val observations: String?,
    @SerializedName("vegetable") val vegetable: Boolean?,
    @SerializedName("links") val links: PlantLinks?,
    @SerializedName("main_species") val mainSpecies: MainSpecies?,
    @SerializedName("genus") val genus: Genus?,
    @SerializedName("family") val family: Family?,
    @SerializedName("species") val species: List<Any>?,
    @SerializedName("subspecies") val subspecies: List<Any>?,
    @SerializedName("varieties") val varieties: List<Any>?,
    @SerializedName("hybrids") val hybrids: List<Any>?,
    @SerializedName("forms") val forms: List<Any>?,
    @SerializedName("subvarieties") val subvarieties: List<Any>?,
    @SerializedName("sources") val sources: List<Any>?
)

/*data class PlantLinks(
    @SerializedName("self") val self: String?,
    @SerializedName("species") val species: String?,
    @SerializedName("genus") val genus: String?
)*/

data class MainSpecies(
    @SerializedName("id") val id: Int?,
    @SerializedName("common_name") val commonName: String?,
    @SerializedName("slug") val slug: String?,
    @SerializedName("scientific_name") val scientificName: String?,
    @SerializedName("year") val year: Int?,
    @SerializedName("bibliography") val bibliography: String?,
    @SerializedName("author") val author: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("rank") val rank: String?,
    @SerializedName("family_common_name") val familyCommonName: String?,
    @SerializedName("genus_id") val genusId: Int?,
    @SerializedName("observations") val observations: String?,
    @SerializedName("vegetable") val vegetable: Boolean?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("genus") val genus: String?,
    @SerializedName("family") val family: String?,
    @SerializedName("duration") val duration: String?,
    @SerializedName("edible_part") val ediblePart: String?,
    @SerializedName("edible") val edible: Boolean?,
    @SerializedName("images") val images: Any?,
    @SerializedName("common_names") val commonNames: Any?,
    @SerializedName("distribution") val distribution: Any?,
    @SerializedName("distributions") val distributions: Any?,
    @SerializedName("flower") val flower: Any?,
    @SerializedName("foliage") val foliage: Any?,
    @SerializedName("fruit_or_seed") val fruitOrSeed: Any?,
    @SerializedName("specifications") val specifications: Specifications?,
    @SerializedName("growth") val growth: Growth?,
    @SerializedName("links") val links: Any?,
    @SerializedName("synonyms") val synonyms: List<Any>?,
    @SerializedName("sources") val sources: List<Any>?
)

data class Specifications(
    @SerializedName("ligneous_type") val ligneousType: String?,
    @SerializedName("growth_form") val growthForm: String?,
    @SerializedName("growth_habit") val growthHabit: String?,
    @SerializedName("growth_rate") val growthRate: String?,
    @SerializedName("average_height") val averageHeight: Any?,
    @SerializedName("maximum_height") val maximumHeight: Any?,
    @SerializedName("nitrogen_fixation") val nitrogenFixation: String?,
    @SerializedName("shape_and_orientation") val shapeAndOrientation: String?,
    @SerializedName("toxicity") val toxicity: String?
)

data class Growth(
    @SerializedName("description") val description: String?,
    @SerializedName("sowing") val sowing: String?,
    @SerializedName("days_to_harvest") val daysToHarvest: String?,
    @SerializedName("row_spacing") val rowSpacing: Any?,
    @SerializedName("spread") val spread: Any?,
    @SerializedName("ph_maximum") val phMaximum: String?,
    @SerializedName("ph_minimum") val phMinimum: String?,
    @SerializedName("light") val light: Int?,
    @SerializedName("atmospheric_humidity") val atmosphericHumidity: Int?,
    @SerializedName("growth_months") val growthMonths: Any?,
    @SerializedName("bloom_months") val bloomMonths: Any?,
    @SerializedName("fruit_months") val fruitMonths: Any?,
    @SerializedName("minimum_precipitation") val minimumPrecipitation: Any?,
    @SerializedName("maximum_precipitation") val maximumPrecipitation: Any?,
    @SerializedName("minimum_root_depth") val minimumRootDepth: Any?,
    @SerializedName("minimum_temperature") val minimumTemperature: Any?,
    @SerializedName("maximum_temperature") val maximumTemperature: Any?,
    @SerializedName("soil_nutriments") val soilNutriments: String?,
    @SerializedName("soil_salinity") val soilSalinity: String?,
    @SerializedName("soil_texture") val soilTexture: String?,
    @SerializedName("soil_humidity") val soilHumidity: String?
)

data class Genus(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("slug") val slug: String?,
    @SerializedName("links") val links: Any?
)

data class Family(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("common_name") val commonName: String?,
    @SerializedName("slug") val slug: String?,
    @SerializedName("links") val links: Any?
)

/*data class Meta(
    @SerializedName("last_modified") val lastModified: String?
)*/