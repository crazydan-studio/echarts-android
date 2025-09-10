package org.crazydan.studio.android.echarts

import androidx.compose.ui.graphics.Color
import org.crazydan.studio.android.echarts.option.DataZoom
import org.crazydan.studio.android.echarts.option.DataZoomList
import org.crazydan.studio.android.echarts.option.Grid
import org.crazydan.studio.android.echarts.option.GridXAxis
import org.crazydan.studio.android.echarts.option.GridYAxis
import org.crazydan.studio.android.echarts.option.Legend
import org.crazydan.studio.android.echarts.option.Theme
import org.crazydan.studio.android.echarts.option.Tooltip

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

        /** 主题配置 */
        fun theme(block: Theme.() -> Unit) {
            Theme(holder).apply(block)
        }

        /** [提示框配置](https://echarts.apache.org/en/option.html#tooltip) */
        fun tooltip(block: Tooltip.() -> Unit) {
            holder.tooltip = Tooltip().apply(block)
        }

        /** [图例配置](https://echarts.apache.org/en/option.html#legend) */
        fun legend(block: Legend.() -> Unit) {
            holder.legend = Legend().apply(block)
        }

        /** [数据区域缩放配置](https://echarts.apache.org/en/option.html#dataZoom) */
        fun dataZoom(block: DataZoomList.() -> Unit) {
            val list = mutableListOf<DataZoom>()
            DataZoomList(holder = list).apply(block)

            holder.dataZoom = list.toList()
        }

        /** [Grid 配置](https://echarts.apache.org/en/option.html#grid) */
        fun grid(id: String? = null, block: Grid.() -> Unit) {
            if (holder.grid.isNotEmpty() && id.isNullOrBlank()) {
                throw IllegalArgumentException("grid id can not be null or blank when multiple grids are configured")
            }

            val grid = Grid(id, holder).apply(block)
            holder.grid.add(grid)
        }
    }
}

class OptionHolder(
    var backgroundColor: Color? = null,

    var tooltip: Tooltip? = null,
    var legend: Legend? = null,
    var dataZoom: List<DataZoom>? = null,

    // Grid 坐标系
    val grid: MutableList<Grid> = mutableListOf(),
    val xAxis: MutableList<GridXAxis> = mutableListOf(),
    val yAxis: MutableList<GridYAxis> = mutableListOf(),

    // 其他坐标系 ...
) : JSONable, ThemeHolder, GridAxisHolder {

    override fun backgroundColor(value: Color) {
        backgroundColor = value
    }

    override fun addXAxis(value: GridXAxis) {
        xAxis.add(value)
    }

    override fun addYAxis(value: GridYAxis) {
        yAxis.add(value)
    }
}

interface ThemeHolder {
    fun backgroundColor(value: Color)
}

interface GridAxisHolder {
    fun addXAxis(value: GridXAxis)
    fun addYAxis(value: GridYAxis)
}