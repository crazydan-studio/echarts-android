package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.JSONable

/**
 * [说明文档](https://echarts.apache.org/en/option.html#xAxis)
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
@EChartsOption
class XAxis(
    private val holder: XAxisHolder = XAxisHolder(),
) : Axis(holder) {

}

/**
 * [说明文档](https://echarts.apache.org/en/option.html#yAxis)
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
@EChartsOption
class YAxis(
    private val holder: YAxisHolder = YAxisHolder(),
) : Axis(holder) {

}

open class Axis(
    private val holder: AxisHolder = AxisHolder(),
) : JSONable {

    enum class Type {
        Value, Category, Time, Log
    }

    override fun toJSON(): String = holder.toJSON()

    /** 是否显示 */
    fun show(value: Boolean) {
        holder.show = value
    }

    /** 坐标轴类型 */
    fun type(block: AxisType.() -> Unit) {
        AxisType(holder).apply(block)
    }
}

class XAxisHolder() : AxisHolder()
class YAxisHolder() : AxisHolder()

open class AxisHolder(
    var show: Boolean = true,

    var type: Axis.Type = Axis.Type.Category,
    var scale: Boolean = false,

    var data: List<AxisData>? = null,

    var axisTick: AxisTick? = null,
) : JSONable

@EChartsOption
class AxisType(
    val holder: AxisHolder
) {

    /** 数值轴，适用于连续数据 */
    fun value(block: (AxisTypeValue.() -> Unit)? = null) {
        holder.type = Axis.Type.Value

        block?.let {
            AxisTypeValue(holder).apply(it)
        }
    }

    /** 类目轴，适用于离散的类目数据 */
    fun category() {
        holder.type = Axis.Type.Category
    }

    /**
     * 时间轴，适用于连续的时序数据，与数值轴相比时间轴带有时间的格式化，
     * 在刻度计算上也有所不同，例如会根据跨度的范围来决定使用月，星期，日还是小时范围的刻度
     */
    fun time() {
        holder.type = Axis.Type.Time
    }

    /**
     * 对数轴。适用于对数数据。对数轴下的堆积柱状图或堆积折线图可能带来很大的视觉误差，
     * 并且在一定情况下可能存在非预期效果，应避免使用
     */
    fun log() {
        holder.type = Axis.Type.Log
    }
}

@EChartsOption
class AxisTypeValue(
    val holder: AxisHolder,
) {

    /**
     * 坐标轴数值是否从 0 值开始。设置成 `true` 后坐标刻度将强制包含零刻度。
     * 在双数值轴的散点图中比较有用
     */
    fun fromZero(value: Boolean) {
        holder.scale = !value
    }
}

data class AxisData(
    val value: String,
) : JSONable

/** 坐标轴刻度：https://echarts.apache.org/zh/option.html#xAxis.axisTick */
data class AxisTick(
    val show: Boolean = true,
    val alignWithLabel: Boolean = true,
) : JSONable