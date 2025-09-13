package org.crazydan.studio.android.echarts.option.marker

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.JSONable

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-12
 */
@EChartsOption
class MarkArea() : JSONable {
    private val holder = MarkAreaHolder()

    override fun toJSON(): String = holder.toJSON()

    /** 图形是否不响应和触发鼠标事件 */
    fun silent(value: Boolean) {
        holder.silent = value
    }

    /** 连接数据的标记线配置 */
    fun byData(block: MarkAreaByPoint.() -> Unit) {
        val start = MarkDataPointHolder()
        val end = MarkDataPointHolder()
        val common = MarkWithNameSymbolHolder()

        MarkAreaByPoint(
            start = start, end = end, common = common,
        ).block()

        start.name = common.name
        start.label = common.label
        end.name = null
        end.label = null

        holder.data.add(
            listOf(
                start, end,
            )
        )
    }

    /** 与 x 轴垂直的标域配置 */
    fun byXAxis(block: MarkAreaByAxis.() -> Unit) {
        val value = mutableListOf<Any>()
        val start = MarkDataPointHolder()
        val end = MarkDataPointHolder()

        MarkAreaByAxis(value, start).block()

        start.xAxis = value.first()
        end.xAxis = value.last()

        holder.data.add(
            listOf(
                start, end,
            )
        )
    }

    /** 与 y 轴垂直的标域配置 */
    fun byYAxis(block: MarkAreaByAxis.() -> Unit) {
        val value = mutableListOf<Any>()
        val start = MarkDataPointHolder()
        val end = MarkDataPointHolder()

        MarkAreaByAxis(value, start).block()

        start.yAxis = value.first()
        end.yAxis = value.last()

        holder.data.add(
            listOf(
                start, end,
            )
        )
    }
}

private class MarkAreaHolder(
    var silent: Boolean = true,

    val data: MutableList<List<MarkDataPointHolder>> = mutableListOf(),
) : MarkWithLabelHolder()

@EChartsOption
open class MarkAreaByXxx(
    private val holder: MarkWithNameSymbolHolder,
) {

    /** 名字 */
    fun name(value: String) {
        holder.name = value
    }

    /** 标签配置 */
    fun label(block: MarkPointLabel.() -> Unit) {
        holder.label = MarkPointLabel().apply(block)
    }
}

@EChartsOption
class MarkAreaByPoint(
    private val start: MarkDataPointHolder,
    private val end: MarkDataPointHolder,
    common: MarkWithNameSymbolHolder,
) : MarkAreaByXxx(common) {

    /** 连线起点配置 */
    fun start(block: MarkDataPoint.() -> Unit) {
        MarkDataPoint(start).block()
    }

    /** 连线终点配置 */
    fun end(block: MarkDataPoint.() -> Unit) {
        MarkDataPoint(end).block()
    }
}

@EChartsOption
class MarkAreaByAxis(
    private val value: MutableList<Any>,
    holder: MarkDataPointHolder,
) : MarkAreaByXxx(holder) {

    /** 坐标轴序号或值 */
    fun value(start: Number, end: Number) {
        value.add(start)
        value.add(end)
    }

    /** 坐标轴序号或值 */
    fun value(start: String, end: String) {
        value.add(start)
        value.add(end)
    }
}