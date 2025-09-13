package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.JSONable
import org.crazydan.studio.android.echarts.SeriesListHolder
import org.crazydan.studio.android.echarts.option.marker.MarkArea
import org.crazydan.studio.android.echarts.option.marker.MarkLine
import org.crazydan.studio.android.echarts.option.marker.MarkPoint
import org.crazydan.studio.android.echarts.option.series.SeriesCandlestick
import org.crazydan.studio.android.echarts.option.series.SeriesLine

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-07
 */
@EChartsOption
class SeriesList(
    private val holder: SeriesListHolder
) {

    /** [折线图配置](https://echarts.apache.org/zh/option.html#series-line) */
    fun line(block: SeriesLine.() -> Unit) {
        val series = SeriesLine().apply(block)

        holder.addSeries(series)
    }

    /** [K 线图配置](https://echarts.apache.org/zh/option.html#series-candlestick) */
    fun candlestick(block: SeriesCandlestick.() -> Unit) {
        val series = SeriesCandlestick().apply(block)

        holder.addSeries(series)
    }
}

@EChartsOption
open class Series(
    private val holder: SeriesHolder,
) : JSONable {

    enum class Type {
        Candlestick, Line, Bar, Pie
    }

    enum class ColorBy {
        Series, Data
    }

    override fun toJSON(): String = holder.toJSON()

    /** 系列名称，用于 [Tooltip] 的显示，[Legend] 的图例筛选 */
    fun name(value: String) {
        holder.name = value
    }

    /** 从调色盘中取色的策略 */
    fun colorBy(block: SeriesColorByScope.() -> ColorBy) {
        holder.colorBy = SeriesColorByScope().block()
    }

    /** 系列的数据列表 */
    fun data(block: SeriesDataList.() -> Unit) {
        val items = mutableListOf<SeriesData>()
        SeriesDataList(
            holder = holder,
            items = items,
        ).apply(block)

        holder.data = items.toList()
    }

    /** 布局时所基于的 x 轴 的 id。当一个 ECharts 实例中存在多个 x 轴时，用其指定所使用的 x 轴 */
    fun xAxisId(value: String) {
        holder.xAxisId = value
    }

    /** 布局时所基于的 y 轴 的 id。当一个 ECharts 实例中存在多个 y轴时，用其指定所使用的 x 轴 */
    fun yAxisId(value: String) {
        holder.yAxisId = value
    }

    /** 标记线配置 */
    fun markLine(block: MarkLine.() -> Unit) {
        holder.markLine = MarkLine().apply(block)
    }

    /** 标记点配置 */
    fun markPoint(block: MarkPoint.() -> Unit) {
        holder.markPoint = MarkPoint().apply(block)
    }

    /** 标记区域配置 */
    fun markArea(block: MarkArea.() -> Unit) {
        holder.markArea = MarkArea().apply(block)
    }
}

open class SeriesHolder(
    val type: Series.Type,

    var name: String? = null,
    var colorBy: Series.ColorBy = Series.ColorBy.Series,
    var dimensions: List<SeriesDimension>? = null,
    var encode: SeriesEncode? = null,
    var data: List<SeriesData>? = null,

    var xAxisId: String? = null,
    var yAxisId: String? = null,

    var markLine: MarkLine? = null,
    var markArea: MarkArea? = null,
    var markPoint: MarkPoint? = null,
) : JSONable

data class SeriesDimension(
    val name: String,
    val displayName: String?,
) : JSONable

data class SeriesEncode(
    val x: List<String>? = null,
    val y: List<String>? = null,
    val tooltip: List<String>? = null,
) : JSONable

@EChartsOption
class SeriesDataList(
    private val holder: SeriesHolder,
    private val items: MutableList<SeriesData>
) {

    /** [item] 中每列数据的列配置 */
    fun dimension(vararg names: String, block: SeriesDataDimension.() -> Unit) {
        val dimHolder = SeriesDataDimensionHolder()
        SeriesDataDimension(dimHolder).block()

        holder.dimensions = names.map {
            SeriesDimension(
                name = it,
                displayName = dimHolder.tooltip?.get(it),
            )
        }
        holder.encode = SeriesEncode(
            x = dimHolder.x,
            y = dimHolder.y,
            tooltip = dimHolder.tooltip?.keys?.toList(),
        )
    }

    /** 各项数据的列值，其列及其总数必须与 [dimension] 定义的列相对应 */
    fun item(vararg value: Number?, block: SeriesData.() -> Unit) {
        val data = SeriesDataHolder(value.toList())
        items.add(
            SeriesData(data).apply(block)
        )
    }
}

@EChartsOption
class SeriesData(
    private val holder: SeriesDataHolder,
) : JSONable {
    override fun toJSON(): String = holder.toJSON()

    // fun itemStyle(...) {}
}

data class SeriesDataHolder(
    var value: List<Number?>,
    var itemStyle: Any? = null,
) : JSONable

@EChartsOption
class SeriesDataDimension(
    private val holder: SeriesDataDimensionHolder,
) {

    /** x 轴所映射的列名，可映射多列 */
    fun x(vararg names: String) {
        holder.x = names.toList()
    }

    /** y 轴所映射的列名，可映射多列 */
    fun y(vararg names: String) {
        holder.y = names.toList()
    }

    /**
     * 需要在 [Tooltip] 中显示的列，可映射多列
     *
     * @param pairs `"列名" to "列显示名"` 形式的键值对，如，`"open" to "开盘价"`
     */
    fun tooltip(vararg pairs: Pair<String, String>) {
        holder.tooltip = pairs.toMap()
    }
}

data class SeriesDataDimensionHolder(
    var x: List<String>? = null,
    var y: List<String>? = null,
    var tooltip: Map<String, String>? = null,
)

class SeriesColorByScope() {
    /** 按照系列分配调色盘中的颜色，同一系列中的所有数据都是用相同的颜色 */
    val series = Series.ColorBy.Series

    /** 按照数据项分配调色盘中的颜色，每个数据项都使用不同的颜色 */
    val data = Series.ColorBy.Data
}