package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.EChartsOptions
import org.crazydan.studio.android.echarts.JSONable

/**
 * 单个图表可以有上下两条 x 轴：https://echarts.apache.org/en/option.html#xAxis
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
fun EChartsOptions.xAxis(
    show: Boolean = true,
    type: AxisType? = AxisType.Value,
    scale: Boolean = false,
    data: List<AxisData>? = null,
    axisLabel: AxisLabel? = null,
): EChartsOptions = this.multiAdd(
    XAxisOption(
        xAxis = Axis(
            show = show,
            type = type,
            scale = scale,
            data = data,
            axisLabel = axisLabel,
        )
    )
)

/** 单个图表可以有左右两条 y 轴 */
fun EChartsOptions.yAxis(
    show: Boolean = true,
    type: AxisType? = AxisType.Value,
    scale: Boolean = false,
    data: List<AxisData>? = null,
    axisLabel: AxisLabel? = null,
): EChartsOptions = this.multiAdd(
    YAxisOption(
        yAxis = Axis(
            show = show,
            type = type,
            scale = scale,
            data = data,
            axisLabel = axisLabel,
        )
    )
)

private data class XAxisOption(
    val xAxis: Axis,
) : EChartsOption

private data class YAxisOption(
    val yAxis: Axis,
) : EChartsOption

private data class Axis(
    val show: Boolean,
    val type: AxisType?,
    val scale: Boolean,
    val data: List<AxisData>?,
    val axisLabel: AxisLabel?,
) : JSONable

data class AxisData(
    val value: String,
) : JSONable

enum class AxisType {
    Value, Category, Time, Log
}

/** https://echarts.apache.org/en/option.html#xAxis.axisLabel */
data class AxisLabel(
    val show: Boolean = true,

    /** https://echarts.apache.org/en/option.html#xAxis.axisLabel.formatter */
    val formatter: String? = null,
) : JSONable