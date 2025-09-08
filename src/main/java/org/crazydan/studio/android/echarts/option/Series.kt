package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.EChartsOptions
import org.crazydan.studio.android.echarts.JSONable
import org.crazydan.studio.android.echarts.listToJSON
import org.crazydan.studio.android.echarts.mapToJSON
import org.crazydan.studio.android.echarts.objToJSON
import org.crazydan.studio.android.echarts.objToMap

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-07
 */
fun EChartsOptions.series(
    series: List<Series>,
): EChartsOptions = this.add(
    SeriesOption(
        series = series,
    )
)

private data class SeriesOption(
    val series: List<Series>,
) : EChartsOption

enum class SeriesType {
    Candlestick, Line, Bar, Pie
}

sealed class Series(
    val type: SeriesType,
    val smooth: Boolean = true,
) : JSONable {

    data class Line(
        val name: String,
        val data: List<Data?>,
        val connectNulls: Boolean = true,

        val xAxisIndex: Int = 0,
        val yAxisIndex: Int = 0,
    ) : Series(type = SeriesType.Line) {

        data class Data(
            val name: String? = null,
            val value: Number?,
        ) : JSONable
    }

    data class Candlestick(
        val name: String,
        val data: List<Data?>,

        val dimensions: List<String>? = null,

        val markLine: SeriesMarkLine? = null,
        val markArea: SeriesMarkArea? = null,
        val markPoint: SeriesMarkPoint? = null,

        val xAxisIndex: Int = 0,
        val yAxisIndex: Int = 0,
    ) : Series(type = SeriesType.Candlestick) {

        data class Data(
            val name: String? = null,
            val value: List<Number?>,
        ) : JSONable

        override fun toJSON(): String {
            val map = objToMap(this).toMutableMap()

            if (dimensions != null) {
                // Note: 第一个维度必须为 date
                map.put("dimensions", mutableListOf("date").also {
                    it.addAll(dimensions)
                })
                map.put("encode", mutableMapOf("tooltip" to (1..dimensions.size).toList()))
            }

            return mapToJSON(map as Map<Any, Any?>)
        }
    }
}

data class SeriesLabel(
    val show: Boolean = true,
    /** https://echarts.apache.org/en/option.html#series-line.markArea.label */
    val formatter: String? = null,
) : JSONable

data class SeriesMarkLine(
    val silent: Boolean = true,
    val label: SeriesLabel? = null,
    val data: List<Range>,
) : JSONable {

    data class Range(
        val from: SeriesMarkData,
        val to: SeriesMarkData? = null,
    ) : JSONable {
        override fun toJSON(): String =
            if (to != null) {
                listToJSON(
                    listOf(from, to)
                )
            } else {
                objToJSON(from)
            }
    }
}

data class SeriesMarkArea(
    val silent: Boolean = true,
    val label: SeriesLabel? = null,
    val data: List<Range>,
) : JSONable {

    data class Range(
        val from: Number,
        val to: Number,
    ) : JSONable {
        override fun toJSON(): String =
            listToJSON(
                listOf(from, to)
            )
    }
}

data class SeriesMarkPoint(
    val silent: Boolean = true,
    val label: SeriesLabel? = null,
    val data: List<SeriesMarkData>,
) : JSONable

data class SeriesMarkData(
    val type: Type = Type.None,
    val valueIndex: Number? = null,
    val valueDim: String? = null,
    val symbol: Symbol? = null,
    val symbolSize: Int? = null,
    val label: SeriesLabel? = null,
) : JSONable {

    enum class Type {
        Min, Max, Average, Median, None
    }

    enum class Symbol {
        Circle, Rect, RoundRect, Triangle, Diamond, Pin, Arrow, None
    }
}