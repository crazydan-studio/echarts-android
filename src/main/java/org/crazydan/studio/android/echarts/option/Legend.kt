package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.EChartsOptions
import org.crazydan.studio.android.echarts.JSONable

/**
 * https://echarts.apache.org/en/option.html#legend
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
fun EChartsOptions.legend(
    show: Boolean = true,
    type: LegendType = LegendType.Plain,

    /** https://echarts.apache.org/en/option.html#legend.data */
    data: List<LegendData>? = null,

    /** https://echarts.apache.org/en/option.html#legend.formatter */
    formatter: String? = null,

    left: Size? = null,
    right: Size? = null,
    top: Size? = null,
    bottom: Size? = null,
): EChartsOptions = this.add(
    LegendOption(
        legend = Legend(
            show = show,
            type = type,
            data = data,
            formatter = formatter,
            left = left,
            right = right,
            top = top,
            bottom = bottom,
        )
    )
)

private data class LegendOption(
    val legend: Legend,
) : EChartsOption

private data class Legend(
    val show: Boolean,
    val type: LegendType,
    val data: List<LegendData>?,
    val formatter: String?,
    val left: Size?,
    val right: Size?,
    val top: Size?,
    val bottom: Size?,
) : JSONable

/** https://echarts.apache.org/en/option.html#legend.data */
data class LegendData(
    val name: String,
) : JSONable

/** https://echarts.apache.org/en/option.html#legend.type */
enum class LegendType() {
    Plain, Scroll
}