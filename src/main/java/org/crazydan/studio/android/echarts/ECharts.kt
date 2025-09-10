package org.crazydan.studio.android.echarts

import org.crazydan.studio.android.echarts.option.Grid
import org.crazydan.studio.android.echarts.option.Legend
import org.crazydan.studio.android.echarts.option.Tooltip
import org.crazydan.studio.android.echarts.option.GridXAxis
import org.crazydan.studio.android.echarts.option.GridYAxis

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

        /** [Grid 配置](https://echarts.apache.org/en/option.html#grid) */
        fun grid(id: String? = null, block: Grid.() -> Unit) {
            holder.grid = Grid(id, holder).apply(block)
        }
    }
}

class OptionHolder(
    var tooltip: Tooltip? = null,
    var legend: Legend? = null,

    var grid: Grid? = null,
) : GridAxisHolder()

open class GridAxisHolder(
    var xAxis: GridXAxis? = null,
    var yAxis: GridYAxis? = null,
) : JSONable