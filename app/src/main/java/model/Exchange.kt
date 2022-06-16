package model

data class Exchange(
    val Ccy: String,
    val CcyNm_EN: String,
    val CcyNm_RU: String,
    val CcyNm_UZ: String,
    val CcyNm_UZC: String,
    val Code: String,
    val Date: String,
    val Diff: Double,
    val Nominal: String,
    val Rate: String,
    val id: Int,

)