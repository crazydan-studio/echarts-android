package org.crazydan.studio.android.echarts.option

import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.EChartsOptions
import org.crazydan.studio.android.echarts.JSONable

/**
 * https://echarts.apache.org/en/option.html#title
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-06
 */
fun EChartsOptions.title(
    show: Boolean = true,
    text: String,
    subtext: String? = null,
    textAlign: TextAlign? = null,
): EChartsOptions = this.add(
    TitleOption(
        title = Title(
            show = show,
            text = text,
            subtext = subtext,
            textAlign = textAlign,
        )
    )
)

private data class TitleOption(
    val title: Title,
) : EChartsOption

private data class Title(
    val show: Boolean,
    val text: String,
    val subtext: String?,
    val textAlign: TextAlign?,
) : JSONable

enum class TextAlign() {
    Auto, Left, Right, Center
}