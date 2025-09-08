package org.crazydan.studio.android.echarts.option

import androidx.compose.ui.graphics.Color
import org.crazydan.studio.android.echarts.EChartsOption
import org.crazydan.studio.android.echarts.EChartsOptions

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-08
 */
fun EChartsOptions.theme(
    backgroundColor: Color? = null,
): EChartsOptions = this.add(
    Theme(
        backgroundColor = backgroundColor,
    )
)

private data class Theme(
    val backgroundColor: Color?,
) : EChartsOption
