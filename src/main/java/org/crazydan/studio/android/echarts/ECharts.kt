package org.crazydan.studio.android.echarts

import org.crazydan.studio.android.echarts.option.Grid
import org.crazydan.studio.android.echarts.option.Legend
import org.crazydan.studio.android.echarts.option.Tooltip
import org.crazydan.studio.android.echarts.option.XAxis
import org.crazydan.studio.android.echarts.option.YAxis

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-05
 */
@DslMarker
annotation class EChartsOption

interface ECharts {

    companion object : ECharts {

        fun option(block: Option.() -> Unit): Option = Option().apply(block)
    }

    @EChartsOption
    class Option : JSONable {
        private val holder = OptionHolder()

        override fun toJSON(): String = holder.toJSON()

        /** [提示框配置](https://echarts.apache.org/en/option.html#tooltip) */
        fun tooltip(block: Tooltip.() -> Unit) {
            holder.tooltip = Tooltip().apply(block)
        }

        /** [图例配置](https://echarts.apache.org/en/option.html#legend) */
        fun legend(block: Legend.() -> Unit) {
            holder.legend = Legend().apply(block)
        }

        /**
         * [Grid 配置](https://echarts.apache.org/en/option.html#grid)
         *
         * Grid 是一个矩形容器，用于绘制二维直角坐标系（`cartesian2d`）。
         * 一个 `cartesian2d` 坐标系由一个 `xAxis` 和一个 `yAxis` 构成。
         * 一个 Grid 中可以存在多个 `cartesian2d` 坐标系。
         * 即，一个 Grid 可以配置多个 `xAxis` 和多个 `yAxis`
         *
         * 可以在 Grid 中绘制例折线图、柱状图、散点图（气泡图）等
         */
        fun grid(block: Grid.() -> Unit) {
            holder.grid = Grid().apply(block)
        }

        /** [X 轴配置](https://echarts.apache.org/en/option.html#xAxis) */
        fun xAxis(block: XAxis.() -> Unit) {
            holder.xAxis = XAxis().apply(block)
        }

        /** [Y 轴配置](https://echarts.apache.org/en/option.html#xAxis) */
        fun yAxis(block: YAxis.() -> Unit) {
            holder.yAxis = YAxis().apply(block)
        }
    }
}

private data class OptionHolder(
    var tooltip: Tooltip? = null,
    var legend: Legend? = null,
    var grid: Grid? = null,
    var xAxis: XAxis? = null,
    var yAxis: YAxis? = null,
) : JSONable
