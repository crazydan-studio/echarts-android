package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.EChartsOptions
import org.crazydan.studio.android.echarts.JSONable

/**
 * 可多次调用以组装 [dataZoom] 数组：https://echarts.apache.org/en/option.html#dataZoom
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
fun EChartsOptions.dataZoom(
    show: Boolean = true,
    type: DataZoomType,
    filterMode: DataZoomFilterMode? = DataZoomFilterMode.Filter,
    startValueIndex: Int? = null,
    endValueIndex: Int? = null,

    /** 对应的 x 轴的序号：单个图表可以有上下两条 x 轴 */
    xAxisIndex: List<Int>? = null,
    /** 对应的 y 轴的序号：单个图表可以有左右两条 y 轴 */
    yAxisIndex: List<Int>? = null,

    left: Size? = null,
    right: Size? = null,
    top: Size? = null,
    bottom: Size? = null,
): EChartsOptions = this.multiAdd(
    DataZoomOption(
        dataZoom = DataZoom(
            show = show,
            type = type,
            startValue = startValueIndex,
            endValue = endValueIndex,
            filterMode = filterMode,
            xAxisIndex = xAxisIndex,
            yAxisIndex = yAxisIndex,
            left = left,
            right = right,
            top = top,
            bottom = bottom,
        ),
    )
)

private data class DataZoomOption(
    val dataZoom: DataZoom,
) : EChartsOption

private data class DataZoom(
    val show: Boolean,
    val type: DataZoomType,
    val startValue: Int?,
    val endValue: Int?,
    val filterMode: DataZoomFilterMode?,
    val xAxisIndex: List<Int>?,
    val yAxisIndex: List<Int>?,
    val left: Size?,
    val right: Size?,
    val top: Size?,
    val bottom: Size?,
) : JSONable

enum class DataZoomType {
    Inside, Slider
}

/** https://echarts.apache.org/en/option.html#dataZoom-slider.filterMode */
enum class DataZoomFilterMode {
    Filter, WeakFilter, Empty, None
}
