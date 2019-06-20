package c.gingdev.a3way.model

data class TrafficDataModel(
    val trafficType: Int,
    val lane: TrafficTypeInfo,
    val stationCount: Int,
    val sectionTime: Int,
    val startID: Int,
    val startName: String,
    val endID: Int,
    val endNAme: String
)

data class TrafficTypeInfo(
    val TrafficNo: String,
    val CodeORType: Int,
    val CodeOrID: Int
)