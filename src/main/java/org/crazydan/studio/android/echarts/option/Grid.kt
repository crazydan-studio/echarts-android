package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.EChartsOptions
import org.crazydan.studio.android.echarts.JSONable

/**
 * https://echarts.apache.org/en/option.html#grid
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
fun EChartsOptions.grid(
    show: Boolean = true,
    left: Size? = null,
    right: Size? = null,
    top: Size? = null,
    bottom: Size? = null,
): EChartsOptions = this.add(
    GridOption(
        grid = Grid(
            show = show,
            left = left,
            right = right,
            top = top,
            bottom = bottom,
        )
    )
)

private data class GridOption(
    val grid: Grid,
) : EChartsOption

private data class Grid(
    val show: Boolean,
    val left: Size?,
    val right: Size?,
    val top: Size?,
    val bottom: Size?,
) : JSONable