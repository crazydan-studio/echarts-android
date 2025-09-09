package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.JSONable

/**
 * [说明文档](https://echarts.apache.org/en/option.html#grid)
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
@EChartsOption
class Grid : JSONable {
    private val holder = GridHolder()

    override fun toJSON(): String = holder.toJSON()

    /** 是否显示直角坐标系网格的边框 */
    fun showBorder(value: Boolean) {
        holder.show = value
    }

    /** 与容器周边的间隔 */
    fun margin(block: Margin.() -> Unit) {
        Margin(holder).apply(block)
    }
}

private data class GridHolder(
    var show: Boolean = false,
) : MarginHolder()