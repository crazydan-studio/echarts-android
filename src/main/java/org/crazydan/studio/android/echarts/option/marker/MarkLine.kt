package org.crazydan.studio.android.echarts.option.marker

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.JSONable
import org.crazydan.studio.android.echarts.option.SeriesLineStyle

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-12
 */
typealias MarkLineByAxisFactory = MarkLineByAxis.() -> Unit

@EChartsOption
class MarkLine() : JSONable {
    private val holder = MarkLineHolder()

    init {
        holder.symbol = MarkWithSymbol.Shape.None
    }

    override fun toJSON(): String = holder.toJSON()

    /** 图形是否不响应和触发鼠标事件 */
    fun silent(value: Boolean) {
        holder.silent = value
    }

    /** 线样式 */
    fun style(block: SeriesLineStyle.() -> Unit) {
        holder.lineStyle = SeriesLineStyle().apply(block)
    }

    /** 连接数据的标记线配置 */
    fun byData(block: MarkLineByPoint.() -> Unit) {
        val start = MarkDataPointHolder()
        val end = MarkDataPointHolder()
        val common = MarkWithNameSymbolHolder()

        MarkLineByPoint(
            start = start, end = end, common = common,
        ).block()

        start.name = common.name
        start.label = common.label
        end.name = null
        end.label = null
        mergeSymbol(start, common)
        mergeSymbol(end, common)

        holder.data.add(
            listOf(
                start, end,
            )
        )
    }

    /** 与 x 轴垂直的标记线配置 */
    fun byXAxis(block: MarkLineByAxisFactory) {
        val p = MarkDataPointHolder()
        MarkLineByAxis(true, p).block()

        holder.data.add(p)
    }

    /** 与 y 轴垂直的标记线配置 */
    fun byYAxis(block: MarkLineByAxisFactory) {
        val p = MarkDataPointHolder()
        MarkLineByAxis(false, p).block()

        holder.data.add(p)
    }
}

class MarkLineHolder(
    var silent: Boolean = true,

    val data: MutableList<Any> = mutableListOf(),
) : MarkWithSymbolHolder()

@EChartsOption
open class MarkLineByXxx(
    private val holder: MarkWithNameSymbolHolder,
) : MarkWithNameSymbol(holder) {

    /** 标签配置 */
    fun label(block: MarkLineLabel.() -> Unit) {
        holder.label = MarkLineLabel().apply(block)
    }

    /** 样式 */
    fun style(block: SeriesLineStyle.() -> Unit) {
        holder.lineStyle = SeriesLineStyle().apply(block)
    }
}

@EChartsOption
class MarkLineByPoint(
    private val start: MarkDataPointHolder,
    private val end: MarkDataPointHolder,
    common: MarkWithNameSymbolHolder,
) : MarkLineByXxx(common) {

    /** 连线起点配置 */
    fun start(block: MarkDataPointWithSymbol.() -> Unit) {
        MarkDataPointWithSymbol(start).block()
    }

    /** 连线终点配置 */
    fun end(block: MarkDataPointWithSymbol.() -> Unit) {
        MarkDataPointWithSymbol(end).block()
    }
}

@EChartsOption
class MarkLineByAxis(
    private val forXAxis: Boolean,
    private val holder: MarkDataPointHolder,
) : MarkLineByXxx(holder) {

    /** 坐标轴序号或值 */
    fun value(value: Number) {
        if (forXAxis) {
            holder.xAxis = value
        } else {
            holder.yAxis = value
        }
    }

    /** 坐标轴序号或值 */
    fun value(value: String) {
        if (forXAxis) {
            holder.xAxis = value
        } else {
            holder.yAxis = value
        }
    }
}

private fun mergeSymbol(target: MarkWithSymbolHolder, source: MarkWithSymbolHolder) {
    if (target.symbol == null) {
        target.symbol = source.symbol
    }
    if (target.symbolSize == null) {
        target.symbolSize = source.symbolSize
    }
    if (target.symbolRotate == null) {
        target.symbolRotate = source.symbolRotate
    }
}