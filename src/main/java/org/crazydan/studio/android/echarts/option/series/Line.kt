package org.crazydan.studio.android.echarts.option.series

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.option.Series
import org.crazydan.studio.android.echarts.option.SeriesAreaStyle
import org.crazydan.studio.android.echarts.option.SeriesHolder
import org.crazydan.studio.android.echarts.option.SeriesLineStyle

/**
 * 折线/面积图
 *
 * 折线图是用折线将各个数据点标志连接起来的图表，用于展现数据的变化趋势。可用于直角坐标系和极坐标系上
 *
 * [说明文档](https://echarts.apache.org/zh/option.html#series-line)
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-10
 */
@EChartsOption
class SeriesLine(
    private val holder: SeriesLineHolder = SeriesLineHolder(),
) : Series(holder) {

    /** 是否绘制平滑曲线 */
    fun smooth(value: Boolean) {
        holder.smooth = value
    }

    /** 是否连接空数据 */
    fun connectNulls(value: Boolean) {
        holder.connectNulls = value
    }

    /** 数据堆叠：同个类目轴上系列配置相同的 stack name 值可以堆叠放置 */
    fun stack(block: SeriesLineStack.() -> Unit) {
        SeriesLineStack(holder).block()
    }

    /** 线条样式配置 */
    fun lineStyle(block: SeriesLineStyle.() -> Unit) {
        holder.lineStyle = SeriesLineStyle().apply(block)
    }

    /** 区域填充样式配置。设置后显示成区域面积图 */
    fun areaStyle(block: SeriesAreaStyle.() -> Unit) {
        holder.areaStyle = SeriesAreaStyle().apply(block)
    }
}

class SeriesLineHolder(
    var connectNulls: Boolean = true,
    var smooth: Boolean = true,

    var stack: String? = null,
) : SeriesHolder(
    type = Series.Type.Line,
)

@EChartsOption
class SeriesLineStack(
    private val holder: SeriesLineHolder,
) {

    fun name(value: String) {
        holder.stack = value
    }
}