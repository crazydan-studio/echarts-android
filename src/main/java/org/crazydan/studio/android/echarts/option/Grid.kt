package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.GridAxisHolder
import org.crazydan.studio.android.echarts.JSONable

/**
 * 直角坐标系 Grid
 *
 * Grid 是一个矩形容器，用于绘制二维直角坐标系（`cartesian2d`）。
 * 一个 `cartesian2d` 坐标系由一个 [xAxis] 和一个 [yAxis] 构成。
 * 一个 Grid 中可以存在多个 `cartesian2d` 坐标系。
 * 即，一个 Grid 可以配置多个 [xAxis] 和多个 [yAxis]
 *
 * 可以在 Grid 中绘制例折线图、柱状图、散点图（气泡图）等
 *
 * [说明文档](https://echarts.apache.org/en/option.html#grid)
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
@EChartsOption
class Grid(
    private val id: String?,
    private val axisHolder: GridAxisHolder,
) : JSONable {
    private val holder = GridHolder(id = id)

    override fun toJSON(): String = holder.toJSON()

    /** 是否显示直角坐标系网格的边框 */
    fun showBorder(value: Boolean) {
        holder.show = value
    }

    /** 与容器周边的间隔 */
    fun margin(block: Margin.() -> Unit) {
        Margin(holder).apply(block)
    }

    /** [x 轴配置](https://echarts.apache.org/en/option.html#xAxis) */
    fun xAxis(id: String? = null, block: GridXAxis.() -> Unit) {
        axisHolder.xAxis = GridXAxis(
            id = id,
            gridId = holder.id
        ).apply(block)
    }

    /** [y 轴配置](https://echarts.apache.org/en/option.html#xAxis) */
    fun yAxis(id: String? = null, block: GridYAxis.() -> Unit) {
        axisHolder.yAxis = GridYAxis(
            id = id,
            gridId = holder.id
        ).apply(block)
    }
}

private data class GridHolder(
    val id: String?,
    var show: Boolean = false,
) : MarginHolder()