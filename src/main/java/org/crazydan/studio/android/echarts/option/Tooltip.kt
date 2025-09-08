package org.crazydan.studio.android.echarts.option

import androidx.compose.ui.graphics.Color
import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.EChartsOptions
import org.crazydan.studio.android.echarts.JSONable

/**
 * https://echarts.apache.org/en/option.html#tooltip
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
fun EChartsOptions.tooltip(
    show: Boolean = true,
    trigger: TooltipTrigger = TooltipTrigger.Item,
    axisPointer: TooltipAxisPointer? = null,

    /** https://echarts.apache.org/en/option.html#tooltip.formatter */
    formatter: String? = null,
    /** https://echarts.apache.org/en/option.html#tooltip.valueFormatter */
    valueFormatter: String? = null,

    backgroundColor: Color? = null,
    borderColor: Color? = null,
): EChartsOptions = this.add(
    TooltipOption(
        tooltip = Tooltip(
            show = show,
            trigger = trigger,
            axisPointer = axisPointer,
            formatter = formatter,
            valueFormatter = valueFormatter,
            backgroundColor = backgroundColor,
            borderColor = borderColor,
        )
    )
)

private data class TooltipOption(
    val tooltip: Tooltip,
) : EChartsOption

private data class Tooltip(
    val show: Boolean,
    val trigger: TooltipTrigger,
    val axisPointer: TooltipAxisPointer?,
    val formatter: String?,
    val valueFormatter: String?,
    val backgroundColor: Color?,
    val borderColor: Color?,
) : JSONable

/** https://echarts.apache.org/en/option.html#tooltip.trigger */
enum class TooltipTrigger() {
    Item, Axis, None
}

/** https://echarts.apache.org/en/option.html#tooltip.axisPointer */
data class TooltipAxisPointer(
    val type: Type = Type.Line,
) : JSONable {

    enum class Type() {
        Line, Cross, None, Shadow
    }
}